package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_EditOrder extends AppCompatActivity {


    private TextView title, cancel, save, warning;
    private Spinner s;
    private EditText jobTitle, budget, street, suburb, city, description;
    private ImageButton addPhoto;
    ClientPendingDetailViewModel jsm;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    String[] photoAddress;
    int[] photoId;
    private ImageView[] photo;
    ImageUnity imageUnity = new ImageUnity();
    private static final int REQUESTCODE=3;
    Context context = this;
    Loading_Dialog myLoading;




    public void getData() {
        DataTransmitController c = new DataTransmitController() {
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                ClientPendingDetailDataConvert clientPendingDetailDataConvert = new ClientPendingDetailDataConvert();
                jsm = clientPendingDetailDataConvert.convertJsonToModel(result);

                if(jsm.getServicePhotoUrl().length != 0) {

                    photoId = new int[5];
                    photoAddress = new String[jsm.getServicePhotoUrl().length];
                    photo = new ImageView[jsm.getServicePhotoUrl().length];

                    photoId[0] = R.id.imageView_OP_photo1;
                    photoId[1] = R.id.imageView_OP_photo2;
                    photoId[2] = R.id.imageView_OP_photo3;
                    photoId[3] = R.id.imageView_OP_photo4;
                    photoId[4] = R.id.imageView_OP_photo5;
                    photoAddress = jsm.getServicePhotoUrl();

                    for(int i=0; i<jsm.getServicePhotoUrl().length; i++) {
                        imageUnity.setImage(context, photo[i] = (ImageView) findViewById(photoId[i]), "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[i]);
                        photo[i].setDrawingCacheEnabled(true);
                        bitmapArray.add(photo[i].getDrawingCache());
                    }
                }

                btnFunction();
                myLoading.CloseLoadingDialog();
            }


        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");
    }


    public boolean isBudget (String number){

        Pattern p = Pattern.compile("\\d*\\.?\\d+");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public void sendImage(Bitmap newImg,int username) {

        SendImageController controller = new SendImageController() {
            @Override
            public void onResponse(Boolean result) {
                super.onResponse(result);
                if (result) {
                    System.out.println("yyyyyyyyyyyyyes");
                } else {
                    System.out.println("nnnnnnnnnnno");
                }
            }
        };
        controller.setBitmap(newImg);
        controller.execute("http://para.co.nz/api/JobService/UploadImage/"+username);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                String returnedResult = data.getStringExtra("test_String");
                Bitmap bitmapFromPW = (Bitmap) data.getParcelableExtra("BitmapImage");
                System.out.println("here: "+ bitmapFromPW);

                UploadPhoto(bitmapFromPW);
            }
        }
    }

    private void UploadPhoto(Bitmap bitmapFromPW)
    {
        bitmapArray.add(bitmapFromPW);

        photoId = new int[5];
        photoAddress = new String[bitmapArray.size()];
        photo = new ImageView[bitmapArray.size()];

        photoId[0] = R.id.imageView_OP_photo1;
        photoId[1] = R.id.imageView_OP_photo2;
        photoId[2] = R.id.imageView_OP_photo3;
        photoId[3] = R.id.imageView_OP_photo4;
        photoId[4] = R.id.imageView_OP_photo5;

        if(bitmapArray.size() < 5) {
            for (int i = 0; i < bitmapArray.size(); i++) {
                photo[i] = (ImageView) findViewById(photoId[i]);
                if (bitmapArray.get(i) != null)
                    photo[i].setImageBitmap(bitmapArray.get(i));
            }
        } else{
            for (int i = 0; i < 5; i++) {
                photo[i] = (ImageView) findViewById(photoId[i]);
                if (bitmapArray.get(i) != null)
                    photo[i].setImageBitmap(bitmapArray.get(i));
            }
        }
    }

    public void btnFunction(){

        title = (TextView) findViewById(R.id.profile_placeOrder_title);
        cancel = (TextView) findViewById(R.id.textView_cancel_placeOrder);
        save = (TextView) findViewById(R.id.textView_save_placeOrder);
        warning = (TextView) findViewById(R.id.textView_PO_error);
        jobTitle = (EditText) findViewById(R.id.editText_PO_workTitle);
        budget = (EditText) findViewById(R.id.editText_PO_budget);
        street = (EditText) findViewById(R.id.editText_PO_street);
        suburb = (EditText) findViewById(R.id.editText_PO_suburb);
        city = (EditText) findViewById(R.id.editText_PO_city);
        description = (EditText) findViewById(R.id.editText_PO_description);
        addPhoto = (ImageButton) findViewById(R.id.imageButton_addPhoto);
        s = (Spinner) findViewById(R.id.spinner_OP);

        warning.setVisibility(View.INVISIBLE);
        title.setText("Edit task");

        jobTitle.setText(ValueMessager.edit_workTitle);
        budget.setText(ValueMessager.edit_budget);
        street.setText(ValueMessager.edit_street);
        suburb.setText(ValueMessager.edit_subrub);
        city.setText(ValueMessager.edit_city);
        description.setText(ValueMessager.edit_description);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_EditOrder.this, Client_Confirm.class);
                startActivity(intent);
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent uploadImage = new Intent(Client_EditOrder.this, SelectPicPopupWindowUploadImage.class);
                startActivityForResult(uploadImage,REQUESTCODE);


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ClientUpdateServiceViewModel model=new ClientUpdateServiceViewModel();
                ClientUpdateServiceDataConvert convert = new ClientUpdateServiceDataConvert();

                DataGetIntController c = new DataGetIntController(){
                    @Override
                    public void onResponse(Integer result) {
                        super.onResponse(result);


                        for(int i=0; i<bitmapArray.size(); i++){
                            sendImage(bitmapArray.get(i),ValueMessengerTaskInfo.id);
                            bitmapArray.remove(i);
                        }
                        myLoading.CloseLoadingDialog();
                    }
                };

                if(jobTitle.getText().toString().equals(""))
                {
                    jobTitle.setHint("Title can not be empty!");
                    jobTitle.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Title can not be empty!");
                }
                else if (!isBudget(budget.getText().toString())){
                    budget.setHint("Budget allows numbers only!");
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Budget allows numbers only!");
                }

                else if(street.getText().toString().equals(""))
                {
                    street.setHint("Address can not be empty!");
                    street.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Address can not be empty!");
                }

                else if(suburb.getText().toString().equals(""))
                {
                    suburb.setHint("Address can not be empty!");
                    suburb.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Address can not be empty!");
                }

                else if(city.getText().toString().equals(""))
                {
                    city.setHint("Address can not be empty!");
                    city.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Address can not be empty!");
                }

                else if(description.getText().toString().equals(""))
                {
                    description.setHint("Description can not be empty!");
                    description.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Description can not be empty!");
                }

                if(jobTitle.getText().toString().equals("")||street.getText().toString().equals("")||suburb.getText().toString().equals("")
                        ||city.getText().toString().equals("")||description.getText().toString().equals("") || !isBudget(budget.getText().toString())){
                    return;
                }
                else {

                    model.setTitle(jobTitle.getText().toString());
                    model.setServiceId(ValueMessengerTaskInfo.id);
                    model.setType(s.getSelectedItem().toString());
                    if (budget.getText().toString().equals("")) {
                        budget.setText("0");
                    }

                    model.setBudget(Double.parseDouble(budget.getText().toString()));
//                    model.set(street.getText().toString());
//                    model.setSuburb(suburb.getText().toString());
//                    model.setCity(city.getText().toString());
                    model.setDescription(description.getText().toString());

                    DataSendController controller = new DataSendController(){
                        @Override
                        public void onResponse(Boolean result) {
                            super.onResponse(result);
                        }
                    };

                    AddressModel addressModel=new AddressModel();
                    addressModel.setId(jsm.getServiceAddressId());
                    addressModel.setStreet(street.getText().toString());
                    addressModel.setSuburb(suburb.getText().toString());
                    addressModel.setCity(city.getText().toString());
                    String data2= AddressDataConvert.ModelToJson(addressModel);
                    myLoading.ShowLoadingDialog();
                    controller.execute("http://para.co.nz/api/Address/UpdateAddress", data2, "PUT");


                    String data = convert.ModelToJson(model);
                    c.execute("http://para.co.nz/api/ClientJobService/updatejobservice", data, "PUT");

                    Intent intent = new Intent(Client_EditOrder.this, Client_Confirm.class);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_place_order);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        getData();


    }
}

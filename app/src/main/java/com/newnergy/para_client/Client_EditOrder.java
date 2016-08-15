package com.newnergy.para_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_EditOrder extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_EXTERNAL_STORAGE_RESULT = 1;
    private TextView title, cancel, save, warning;
    private Spinner s;
    private EditText jobTitle, budget, street, suburb, city, description;
    private ImageButton addPhoto;
    private Bitmap bitmap;
    ClientPendingDetailViewModel jsm;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    String[] photoAddress;
    int[] photoId;
    private ImageView[] photo;


    public void getImageData(String profilePhotoUrl, final ImageView imageView) {

        GetImageController controller = new GetImageController() {
            @Override
            public void onResponse(Bitmap mBitmap) {
                super.onResponse(mBitmap);
                if (mBitmap == null) {
                    return;
                }
                imageView.setImageBitmap(mBitmap);
                bitmapArray.add(mBitmap);
            }
        };
        controller.execute("http://para.co.nz/api/JobService/GetServiceImage/"+ profilePhotoUrl, "","POST");
    }

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

                    for(int i=0; i<jsm.getServicePhotoUrl().length; i++)
                        getImageData(photoAddress[i], photo[i] = (ImageView) findViewById(photoId[i]));
                }

                btnFunction();
            }


        };
        c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");
    }


    public boolean isBudget (String email){

        Pattern p = Pattern.compile("\\d*\\.?\\d+");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public void sendImage(Bitmap newImg,int username) {
//        Bitmap good = ((BitmapDrawable) newImg.getDrawable()).getBitmap();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            //URI address on SD card
            Uri selectedImage = data.getData();
            // Declare a stream to read the image data
            InputStream inputStream;
            try {

                //getting an input stream from the image data
                inputStream = getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);

                bitmapArray.add(bitmap);

                    photoId = new int[5];
                    photoAddress = new String[bitmapArray.size()];
                    photo = new ImageView[bitmapArray.size()];

                    photoId[0] = R.id.imageView_OP_photo1;
                    photoId[1] = R.id.imageView_OP_photo2;
                    photoId[2] = R.id.imageView_OP_photo3;
                    photoId[3] = R.id.imageView_OP_photo4;
                    photoId[4] = R.id.imageView_OP_photo5;
                    //photoAddress = bitmapArray.size();

                    for(int i=0; i<bitmapArray.size(); i++) {
                        photo[i] = (ImageView) findViewById(photoId[i]);
                        photo[i].setImageBitmap(bitmapArray.get(i));
                    }





                //sendImage(roundImage.getBitmap(), ValueMessager.email.toString() );

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to load image", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == RESULT_EXTERNAL_STORAGE_RESULT){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //invoke image gallery by intent
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //where do we get the data
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                //URI representation
                Uri data = Uri.parse(pictureDirectoryPath);

                //set data and type, get all image type
                galleryIntent.setDataAndType(data,"image/*");

                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
            }
            else{
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RESULT_EXTERNAL_STORAGE_RESULT);
                }

                else{
                    //invoke image gallery by intent
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //where do we get the data
                    File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String pictureDirectoryPath = pictureDirectory.getPath();
                    //URI representation
                    Uri data = Uri.parse(pictureDirectoryPath);

                    //set data and type, get all image type
                    galleryIntent.setDataAndType(data,"image/*");

                    startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                }
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
                        }
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

        getData();

    }
}

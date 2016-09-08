package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_PlaceOrder extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_EXTERNAL_STORAGE_RESULT = 1;
    private TextView cancel, save, error;
    private EditText jobTitle, budget, street, suburb, city, description;
    private ImageButton addPhoto;
    private Bitmap bitmap;
    private ImageView photo1, photo2, photo3, photo4, photo5;
    private Spinner spinner;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    private static final int REQUESTCODE=3;
    ImageUnity imageUnity = new ImageUnity();
    Context context = this;
    Loading_Dialog myLoading;


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

    public String readData(String openFileName){
        try {

            FileInputStream fileInputStream = openFileInput(openFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            ValueMessager.readDataBuffer = bufferedReader.readLine();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ValueMessager.readDataBuffer.toString();
    }

    public void btnFunction(){


        cancel = (TextView) findViewById(R.id.textView_cancel_placeOrder);
        error = (TextView) findViewById(R.id.textView_PO_error);
        save = (TextView) findViewById(R.id.textView_save_placeOrder);
        jobTitle = (EditText) findViewById(R.id.editText_PO_workTitle);
        budget = (EditText) findViewById(R.id.editText_PO_budget);
        street = (EditText) findViewById(R.id.editText_PO_street);
        suburb = (EditText) findViewById(R.id.editText_PO_suburb);
        city = (EditText) findViewById(R.id.editText_PO_city);
        description = (EditText) findViewById(R.id.editText_PO_description);
        addPhoto = (ImageButton) findViewById(R.id.imageButton_addPhoto);
        photo1 = (ImageView) findViewById(R.id.imageView_OP_photo1);
        photo2 = (ImageView) findViewById(R.id.imageView_OP_photo2);
        photo3 = (ImageView) findViewById(R.id.imageView_OP_photo3);
        photo4 = (ImageView) findViewById(R.id.imageView_OP_photo4);
        photo5 = (ImageView) findViewById(R.id.imageView_OP_photo5);
        spinner = (Spinner) findViewById(R.id.spinner_OP);

        photo1.setVisibility(View.INVISIBLE);
        photo2.setVisibility(View.INVISIBLE);
        photo3.setVisibility(View.INVISIBLE);
        photo4.setVisibility(View.INVISIBLE);
        photo5.setVisibility(View.INVISIBLE);

        error.setVisibility(View.INVISIBLE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_History = new Intent(Client_PlaceOrder.this, Client_Incoming_Services.class);
                startActivity(nextPage_History);
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadImage = new Intent(Client_PlaceOrder.this, SelectPicPopupWindowUploadImage.class);
                startActivityForResult(uploadImage,REQUESTCODE);

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlaceOrderServiceViewModel placeOrderServiceViewModel=new PlaceOrderServiceViewModel();
                PlaceServiceDataConvert placeServiceDataConvert = new PlaceServiceDataConvert();

                DataGetIntController c = new DataGetIntController(){
                    @Override
                    public void onResponse(Integer result) {
                        super.onResponse(result);

                        for(int i=0; i<bitmapArray.size(); i++){
                           sendImage(bitmapArray.get(i),result);
                        }
                        myLoading.CloseLoadingDialog();
                    }
                };

                if(jobTitle.getText().toString().equals(""))
                {
                    jobTitle.setHint("Title can not be empty!");
                    jobTitle.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Title can not be empty!");
                }
                else if (!isBudget(budget.getText().toString())){
                    budget.setHint("Budget allows numbers only!");
                    error.setVisibility(View.VISIBLE);
                    error.setText("Budget allows numbers only!");
                }

                else if(street.getText().toString().equals(""))
                {
                    street.setHint("Address can not be empty!");
                    street.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Address can not be empty!");
                }

                else if(suburb.getText().toString().equals(""))
                {
                    suburb.setHint("Address can not be empty!");
                    suburb.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Address can not be empty!");
                }

                else if(city.getText().toString().equals(""))
                {
                    city.setHint("Address can not be empty!");
                    city.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Address can not be empty!");
                }

                else if(description.getText().toString().equals(""))
                {
                    description.setHint("Description can not be empty!");
                    description.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Description can not be empty!");
                }

                if(jobTitle.getText().toString().equals("")||street.getText().toString().equals("")||suburb.getText().toString().equals("")
                        ||city.getText().toString().equals("")||description.getText().toString().equals("") || !isBudget(budget.getText().toString())){
                    return;
                }
                else {
                    placeOrderServiceViewModel.setClientEmail(readData("userEmail"));
                    placeOrderServiceViewModel.setTitle(jobTitle.getText().toString());
                    placeOrderServiceViewModel.setType(spinner.getSelectedItem().toString());
                    if (budget.getText().toString().equals("")) {
                        budget.setText("0");
                    }
                    placeOrderServiceViewModel.setBudget(Double.parseDouble(budget.getText().toString()));
                    placeOrderServiceViewModel.setStreet(street.getText().toString());
                    placeOrderServiceViewModel.setSuburb(suburb.getText().toString());
                    placeOrderServiceViewModel.setCity(city.getText().toString());
                    placeOrderServiceViewModel.setDescription(description.getText().toString());

                    String data = placeServiceDataConvert.convertModelToJson(placeOrderServiceViewModel);
                    myLoading.ShowLoadingDialog();
                    c.execute("http://para.co.nz/api/ClientJobService/AddService", data, "POST");

                    Intent nextPage_History = new Intent(Client_PlaceOrder.this, Client_Incoming_Services.class);
                    startActivity(nextPage_History);
                }
            }
        });

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
        if(photo1.getVisibility() == View.INVISIBLE)
        {
            photo1.setImageBitmap(bitmapFromPW);
            photo1.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW) ;
        }
        else if(photo1.getVisibility() == View.VISIBLE && photo2.getVisibility() == View.INVISIBLE )
        {
            photo2.setImageBitmap(bitmapFromPW);
            photo2.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW);
        }
        else if(photo2.getVisibility() == View.VISIBLE && photo3.getVisibility() == View.INVISIBLE  )
        {
            photo3.setImageBitmap(bitmapFromPW);
            photo3.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW);
        }
        else if(photo3.getVisibility() == View.VISIBLE && photo4.getVisibility() == View.INVISIBLE  )
        {
            photo4.setImageBitmap(bitmapFromPW);
            photo4.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW);
        }
        else if(photo4.getVisibility() == View.VISIBLE && photo5.getVisibility() == View.INVISIBLE  )
        {
            photo5.setImageBitmap(bitmapFromPW);
            photo5.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_place_order);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        btnFunction();

    }
}

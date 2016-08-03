package com.newnergy.para_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Client_PlaceOrder extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_EXTERNAL_STORAGE_RESULT = 1;
    private TextView cancel, save;
    private EditText jobTitle, budget, street, suburb, city, description;
    private ImageButton addPhoto;
    private Bitmap bitmap;
    private ImageView photo1, photo2, photo3, photo4, photo5;
    private Spinner s;
    private Bitmap[] bitmapArray = new Bitmap[4];



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

                if(photo1.getVisibility() == View.INVISIBLE)
                {
                    photo1.setImageBitmap(bitmap);
                    photo1.setVisibility(View.VISIBLE);
                }
                else if(photo1.getVisibility() == View.VISIBLE && photo2.getVisibility() == View.INVISIBLE )
                {
                    photo2.setImageBitmap(bitmap);
                    photo2.setVisibility(View.VISIBLE);
                }
                else if(photo2.getVisibility() == View.VISIBLE && photo3.getVisibility() == View.INVISIBLE  )
                {
                    photo3.setImageBitmap(bitmap);
                    photo3.setVisibility(View.VISIBLE);
                }
                else if(photo3.getVisibility() == View.VISIBLE && photo4.getVisibility() == View.INVISIBLE  )
                {
                    photo4.setImageBitmap(bitmap);
                    photo4.setVisibility(View.VISIBLE);
                }
                else if(photo4.getVisibility() == View.VISIBLE && photo5.getVisibility() == View.INVISIBLE  )
                {
                    photo5.setImageBitmap(bitmap);
                    photo5.setVisibility(View.VISIBLE);
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
        s = (Spinner) findViewById(R.id.spinner_OP);

        photo1.setVisibility(View.INVISIBLE);
        photo2.setVisibility(View.INVISIBLE);
        photo3.setVisibility(View.INVISIBLE);
        photo4.setVisibility(View.INVISIBLE);
        photo5.setVisibility(View.INVISIBLE);

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

                PlaceOrderServiceViewModel placeOrderServiceViewModel=new PlaceOrderServiceViewModel();
                PlaceServiceDataConvert placeServiceDataConvert = new PlaceServiceDataConvert();

                DataSendController c = new DataSendController(){
                    @Override
                    public void onResponse(Boolean result) {
                        super.onResponse(result);
                    }
                };

                placeOrderServiceViewModel.setClientEmail(readData("userEmail"));
                placeOrderServiceViewModel.setTitle(jobTitle.getText().toString());
                placeOrderServiceViewModel.setType(s.getSelectedItem().toString());
                if(budget.getText().toString().equals("")){
                    budget.setText("0");
                }
                placeOrderServiceViewModel.setBudget(Double.parseDouble(budget.getText().toString()));
                placeOrderServiceViewModel.setStreet(street.getText().toString());
                placeOrderServiceViewModel.setSuburb(suburb.getText().toString());
                placeOrderServiceViewModel.setCity(city.getText().toString());
                placeOrderServiceViewModel.setDescription(description.getText().toString());

                String data = placeServiceDataConvert.convertModelToJson(placeOrderServiceViewModel);
                c.execute("http://para.co.nz/api/JobService/AddService", data, "POST");


                Intent nextPage_History = new Intent(Client_PlaceOrder.this, Client_Incoming_Services.class);
                startActivity(nextPage_History);
            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_place_order);

        btnFunction();

    }
}

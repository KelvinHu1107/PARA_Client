package com.newnergy.para_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_OfferJob extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int RESULT_EXTERNAL_STORAGE_RESULT = 1;
    private TextView cancel, save, error;
    private EditText jobTitle, budget, street, suburb, city, description;
    private ImageButton addPhoto;
    private Bitmap bitmap;
    private ImageView photo1, photo2, photo3, photo4, photo5;
    private Spinner s;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    ImageUnity imageUnity = new ImageUnity();


    public boolean isBudget (String email){

        Pattern p = Pattern.compile("\\d*");
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
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inJustDecodeBounds = false;
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmap = BitmapFactory.decodeStream(inputStream, null, opt);

                if(photo1.getVisibility() == View.INVISIBLE)
                {
                    photo1.setImageBitmap(bitmap);
                    photo1.setVisibility(View.VISIBLE);
                    bitmapArray.add(bitmap) ;
                }
                else if(photo1.getVisibility() == View.VISIBLE && photo2.getVisibility() == View.INVISIBLE )
                {
                    photo2.setImageBitmap(bitmap);
                    photo2.setVisibility(View.VISIBLE);
                    bitmapArray.add(bitmap);
                }
                else if(photo2.getVisibility() == View.VISIBLE && photo3.getVisibility() == View.INVISIBLE  )
                {
                    photo3.setImageBitmap(bitmap);
                    photo3.setVisibility(View.VISIBLE);
                    bitmapArray.add(bitmap);
                }
                else if(photo3.getVisibility() == View.VISIBLE && photo4.getVisibility() == View.INVISIBLE  )
                {
                    photo4.setImageBitmap(bitmap);
                    photo4.setVisibility(View.VISIBLE);
                    bitmapArray.add(bitmap);
                }
                else if(photo4.getVisibility() == View.VISIBLE && photo5.getVisibility() == View.INVISIBLE  )
                {
                    photo5.setImageBitmap(bitmap);
                    photo5.setVisibility(View.VISIBLE);
                    bitmapArray.add(bitmap);
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


        cancel = (TextView) findViewById(R.id.textView_cancel_offerJob);
        error = (TextView) findViewById(R.id.textView_offerJob_error);
        save = (TextView) findViewById(R.id.textView_save_offerJob);
        jobTitle = (EditText) findViewById(R.id.editText_offerJob_workTitle);
        budget = (EditText) findViewById(R.id.editText_offerJob_budget);
        street = (EditText) findViewById(R.id.editText_offerJob_street);
        suburb = (EditText) findViewById(R.id.editText_offerJob_suburb);
        city = (EditText) findViewById(R.id.editText_offerJob_city);
        description = (EditText) findViewById(R.id.editText_offerJob_description);
        addPhoto = (ImageButton) findViewById(R.id.imageButton_addPhoto_offerJob);
        photo1 = (ImageView) findViewById(R.id.imageView_offerJob_photo1);
        photo2 = (ImageView) findViewById(R.id.imageView_offerJob_photo2);
        photo3 = (ImageView) findViewById(R.id.imageView_offerJob_photo3);
        photo4 = (ImageView) findViewById(R.id.imageView_offerJob_photo4);
        photo5 = (ImageView) findViewById(R.id.imageView_offerJob_photo5);
        s = (Spinner) findViewById(R.id.spinner_offerJob);

        photo1.setVisibility(View.INVISIBLE);
        photo2.setVisibility(View.INVISIBLE);
        photo3.setVisibility(View.INVISIBLE);
        photo4.setVisibility(View.INVISIBLE);
        photo5.setVisibility(View.INVISIBLE);

        error.setVisibility(View.INVISIBLE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_History = new Intent(Client_OfferJob.this, Client_Further_Info.class);
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

                DataGetIntController c = new DataGetIntController(){
                    @Override
                    public void onResponse(Integer result) {
                        super.onResponse(result);

                        DataSendController controller = new DataSendController(){
                            @Override
                            public void onResponse(Boolean r) {
                                super.onResponse(r);
                            }
                        };

                        JobServiceStatusViewModel model = new JobServiceStatusViewModel();

                        model.setStatus(2);
                        model.setProviderUsername(ValueMessagerFurtherInfo.providerUserName.toString());

                        String data2= new JobServiceStatusDataConvert().ModelToJson(model);



                        controller.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+result, data2, "PUT");

                        for(int i=0; i<bitmapArray.size(); i++){
                            sendImage(bitmapArray.get(i),result);
                        }
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
                    placeOrderServiceViewModel.setType(s.getSelectedItem().toString());
                    if (budget.getText().toString().equals("")) {
                        budget.setText("0");
                    }
                    placeOrderServiceViewModel.setBudget(Double.parseDouble(budget.getText().toString()));
                    placeOrderServiceViewModel.setStreet(street.getText().toString());
                    placeOrderServiceViewModel.setSuburb(suburb.getText().toString());
                    placeOrderServiceViewModel.setCity(city.getText().toString());
                    placeOrderServiceViewModel.setDescription(description.getText().toString());

                    String data = placeServiceDataConvert.convertModelToJson(placeOrderServiceViewModel);
                    c.execute("http://para.co.nz/api/ClientJobService/AddService", data, "POST");

                    Intent nextPage_History = new Intent(Client_OfferJob.this, Client_Incoming_Services.class);
                    startActivity(nextPage_History);
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_offer_job);

        btnFunction();

    }

}

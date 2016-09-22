package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Client_Profile extends AppCompatActivity{

    public LinearLayout changePassword,changeName,changePhone,changeAddress;
    public TextView name,email,phone,address, title, back;
    public CircleImageView profilePicture;
    public Button button;
    private ClientProfileViewModel list;
    Loading_Dialog myLoading;
    Context context = this;




    public void getData(){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                System.out.println("yyyyyyy"+ result);

                list = ClientDataConvert.convertJsonToModel(result);
                btnFunction();
                refreshData();

            }
        };

        c.execute("http://para.co.nz/api/ClientProfile/getClientDetail/"+ValueMessager.email,"","GET");
        myLoading.ShowLoadingDialog();
    }

    public void refreshData() {


        ValueMessager.phone = list.getCellPhone();
        ValueMessager.email= list.getUsername();

        ValueMessager.address_id = list.getClientAddressId();
        if(ValueMessager.address_id != null) {
            ValueMessager.address_street = list.getClientAddressStreet();
            ValueMessager.address_suburb = list.getClientAddressSuburb();
            ValueMessager.address_city = list.getClientAddressCity();
        }

        //setup profile value from value messenger


        profilePicture.setImageBitmap(ValueMessager.userProfileBitmap);

        name.setText(ValueMessager.userFirstName + " " + ValueMessager.userLastName);

        email.setText(ValueMessager.email);

        if(ValueMessager.phone != null)
            phone.setText(ValueMessager.phone);

        address.setText(ValueMessager.address_street +", "+ValueMessager.address_suburb+", "+ValueMessager.address_city);


        myLoading.CloseLoadingDialog();
    }



    public void btnFunction() {

        changeName = (LinearLayout) findViewById(R.id.profile_name_bar);
        changePhone = (LinearLayout) findViewById(R.id.profile_phone_bar);
        changeAddress = (LinearLayout) findViewById(R.id.profile_address_bar);
        changePassword = (LinearLayout) findViewById(R.id.profile_changePassword_bar);
        name = (TextView) findViewById(R.id.textView_profileFirstName);
        email = (TextView) findViewById(R.id.textView_profileEmail);
        phone = (TextView) findViewById(R.id.textView_profilePhone);
        address = (TextView) findViewById(R.id.textView_profileAddress);
        title = (TextView) findViewById(R.id.profile_change_notification_title);
        back = (TextView) findViewById(R.id.textView_cancel_notification);
        profilePicture = (CircleImageView) findViewById(R.id.imageView_profile_profileImage);

        title.setText("Account");
        back.setText("< Back");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_Profile.this, Client_Setting.class);
                startActivity(intent);
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangeName = new Intent(Client_Profile.this, profile_changeName.class);
                startActivity(nextPage_ChangeName);
            }
        });

        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangePhoneNumber = new Intent(Client_Profile.this, profile_changePhone.class);
                startActivity(nextPage_ChangePhoneNumber);
            }
        });

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangeAddress = new Intent(Client_Profile.this, profile_changeAddress.class);
                startActivity(nextPage_ChangeAddress);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Client_Profile.this,SelectPicPopupWindow.class);
                SelectPicPopupWindow.targetControl(profilePicture);
                startActivity(intent);
            }
        });
    }

    //write data into internal storage

    public void writeData(Bitmap image){

        try {
            FileOutputStream fileOutputStream = openFileOutput("profile_data_picture",MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        getData();

    }
}

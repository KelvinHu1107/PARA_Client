package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Client_Setting extends AppCompatActivity {

    public ImageButton pending,message,payment,main;
    public LinearLayout paymentBar,historyBar,aboutBar,logOutBar,skill;
    public TextView name;
    public CircleImageView profilePicture;
    public Button button;

    Loading_Dialog myLoading;
    Context context = this;



    public void refreshData() {

        //setup profile value from value messenger

        if(ValueMessager.userProfileBitmap != null)
        profilePicture.setImageBitmap(ValueMessager.userProfileBitmap);


            name.setText(ValueMessager.userFirstName+" "+ValueMessager.userLastName);

    }



    public void btnFunction() {
        pending = (ImageButton) findViewById(R.id.imageButton_ProfileToPending);
        message = (ImageButton) findViewById(R.id.imageButton_ProfileToMessage);
        payment = (ImageButton) findViewById(R.id.imageButton_ProfileToPayment);
        main = (ImageButton) findViewById(R.id.imageButton_ProfileToMain);
        name = (TextView) findViewById(R.id.textView_setting_name);
        paymentBar = (LinearLayout) findViewById(R.id.setting_payment_bar);
        historyBar = (LinearLayout) findViewById(R.id.setting_history_bar);
        aboutBar = (LinearLayout) findViewById(R.id.setting_about_bar);
        logOutBar = (LinearLayout) findViewById(R.id.setting_logOut_bar);
        profilePicture = (CircleImageView) findViewById(R.id.imageView_setting_profileImage);


        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Main = new Intent(Client_Setting.this, Client_Incoming_Services.class);
                startActivity(nextPage_Main);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Message = new Intent(Client_Setting.this, Client_PlaceOrder.class);
                startActivity(nextPage_Message);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Pending = new Intent(Client_Setting.this, Client_Message.class);
                startActivity(nextPage_Pending);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Payment = new Intent(Client_Setting.this, Client_Pending.class);
                startActivity(nextPage_Payment);
            }
        });

        paymentBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Client_Setting.this, Client_PaymentMethod.class);
                startActivity(intent);
            }
        });

        historyBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Client_Setting.this, Client_History.class);
                startActivity(intent);
            }
        });

        aboutBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangeCompanyName = new Intent(Client_Setting.this, Client_AboutUs.class);
                startActivity(nextPage_ChangeCompanyName);
            }
        });

        logOutBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangeCompanyAddress = new Intent(Client_Setting.this, Client_PopUp_LogOut.class);
                startActivity(nextPage_ChangeCompanyAddress);
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_Setting.this, Client_Profile.class);
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
        setContentView(R.layout.content_setting);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        btnFunction();
        refreshData();

    }
}

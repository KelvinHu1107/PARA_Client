package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Client_AboutUs extends AppCompatActivity {

    private TextView cancel, terns, aboutCompany;


    public void btnFunction(){


        cancel = (TextView) findViewById(R.id.textView_cancel_aboutUs);
        terns = (TextView) findViewById(R.id.textView_terns);
        aboutCompany = (TextView) findViewById(R.id.textView_aboutCompany);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (ValueMessager.aboutUsLastPage) {

                    case 1:
                        Intent nextPage_IncomingServices = new Intent(Client_AboutUs.this, Client_Incoming_Services.class);
                        startActivity(nextPage_IncomingServices);
                        break;
                    case 2:
                        Intent nextPage_Message = new Intent(Client_AboutUs.this, Client_Message.class);
                        startActivity(nextPage_Message);
                        break;
                    case 3:
                        Intent nextPage_Pending = new Intent(Client_AboutUs.this, Client_Pending.class);
                        startActivity(nextPage_Pending);
                        break;
                    case 4:
                        Intent nextPage_Profile = new Intent(Client_AboutUs.this, Client_Profile.class);
                        startActivity(nextPage_Profile);
                        break;
                }
            }
        });


        terns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Terns = new Intent(Client_AboutUs.this, Client_Terns.class);
                startActivity(nextPage_Terns);
            }
        });

        aboutCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_AboutCompany = new Intent(Client_AboutUs.this, Client_AboutCompany.class);
                startActivity(nextPage_AboutCompany);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_about_us);

        btnFunction();

    }
}

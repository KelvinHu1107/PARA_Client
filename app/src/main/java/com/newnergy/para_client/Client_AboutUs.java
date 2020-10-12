package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Client_AboutUs extends AppCompatActivity {

    private TextView title, terns, aboutCompany;
    ImageView back, save;


    public void btnFunction(){


        save = (ImageView) findViewById(R.id.imageView_ok);
        back = (ImageView) findViewById(R.id.imageView_back);
        title = (TextView) findViewById(R.id.tree_field_title);
        terns = (TextView) findViewById(R.id.textView_terns);
        aboutCompany = (TextView) findViewById(R.id.textView_aboutCompany);

        title.setText("About us");
        save.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent nextPage_IncomingServices = new Intent(Client_AboutUs.this, Client_Setting.class);
                        startActivity(nextPage_IncomingServices);
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

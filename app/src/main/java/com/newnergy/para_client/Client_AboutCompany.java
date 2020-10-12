package com.newnergy.para_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Client_AboutCompany extends AppCompatActivity {

    private TextView cancel;


    public void btnFunction(){


        cancel = (TextView) findViewById(R.id.textView_cancel_aboutCompany);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CreditCard = new Intent(Client_AboutCompany.this, Client_AboutUs.class);
                startActivity(nextPage_CreditCard);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_about_company);

        btnFunction();

    }
}

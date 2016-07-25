package com.newnergy.para_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Client_Terns extends AppCompatActivity {


    private TextView cancel, save;


    public void btnFunction(){


        cancel = (TextView) findViewById(R.id.textView_cancel_terns);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CreditCard = new Intent(Client_Terns.this, Client_AboutUs.class);
                startActivity(nextPage_CreditCard);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_terns);

        btnFunction();
    }
}

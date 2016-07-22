package com.newnergy.para_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Client_ExpireDate extends AppCompatActivity {


    private TextView cancel, save;


    public void btnFunction(){


        cancel = (TextView) findViewById(R.id.textView_cancel_expire);
        save = (TextView) findViewById(R.id.textView_save_expire);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CreditCard = new Intent(Client_ExpireDate.this, Client_CreditCard.class);
                startActivity(nextPage_CreditCard);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_expire_date);

        btnFunction();
    }
}

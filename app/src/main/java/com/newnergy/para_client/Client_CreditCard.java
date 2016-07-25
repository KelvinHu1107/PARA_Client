package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Client_CreditCard extends AppCompatActivity {

    private TextView back;
    private LinearLayout number, holder, expire, securityCode, address, postCode;


    public void btnFunction(){

        back  = (TextView) findViewById(R.id.textView_creditCard_back);
        holder = (LinearLayout) findViewById(R.id.linearLayout_cardHolder);
        expire = (LinearLayout) findViewById(R.id.linearLayout_expire);
        securityCode = (LinearLayout) findViewById(R.id.linearLayout_security);
        address = (LinearLayout) findViewById(R.id.linearLayout_card_address);
        number = (LinearLayout) findViewById(R.id.linearLayout_cardNum);
        postCode = (LinearLayout) findViewById(R.id.linearLayout_postCode);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Setting = new Intent(Client_CreditCard.this, Client_SlidingMenu_Setting.class);
                startActivity(nextPage_Setting);
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CardNum = new Intent(Client_CreditCard.this, Client_CreditCardNum.class);
                startActivity(nextPage_CardNum);
            }
        });

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CardHolder = new Intent(Client_CreditCard.this, Client_CreditCardNum.class);
                startActivity(nextPage_CardHolder);
            }
        });

        expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Expire = new Intent(Client_CreditCard.this, Client_CreditCardNum.class);
                startActivity(nextPage_Expire);
            }
        });

        securityCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_SecurityCode = new Intent(Client_CreditCard.this, Client_CreditCardNum.class);
                startActivity(nextPage_SecurityCode);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CardAddress = new Intent(Client_CreditCard.this, Client_CardAddress.class);
                startActivity(nextPage_CardAddress);
            }
        });

        postCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_PostCode = new Intent(Client_CreditCard.this, Client_CardAddress.class);
                startActivity(nextPage_PostCode);
            }
        });



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_menu_credit_card);

        btnFunction();
    }
}

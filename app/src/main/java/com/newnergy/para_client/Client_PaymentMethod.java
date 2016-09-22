package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Client_PaymentMethod extends AppCompatActivity {

    private LinearLayout changeCreditCard, changeNotification;
    private TextView back;


    public void btnFunction() {

        changeCreditCard = (LinearLayout) findViewById(R.id.linearLayout_changeCreditCard);
        changeNotification = (LinearLayout) findViewById(R.id.linearLayout_changeNotification);
        back = (TextView) findViewById(R.id.textView_setting_back);

        changeCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_ChangeCreditCard = new Intent(Client_PaymentMethod.this, Client_CreditCard.class);
                startActivity(nextPage_ChangeCreditCard);
            }
        });

        changeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_ChangeNotification = new Intent(Client_PaymentMethod.this, Client_Setting_Notification.class);
                startActivity(nextPage_ChangeNotification);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       Intent intent = new Intent(Client_PaymentMethod.this, Client_Setting.class);
                       startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_payment_method);

        btnFunction();

    }
}

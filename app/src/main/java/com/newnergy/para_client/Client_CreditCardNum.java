package com.newnergy.para_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Client_CreditCardNum extends AppCompatActivity {

    private TextView cancel, save;
    private EditText num1, num2, num3, num4, holderFirstName, holderLastName, expireMonth, expireYear, securityCode;


    public void btnFunction(){


        cancel = (TextView) findViewById(R.id.textView_cancel_cardNum);
        save = (TextView) findViewById(R.id.textView_save_cardHolder);
        num1 = (EditText) findViewById(R.id.editText_cardNum1);
        num2 = (EditText) findViewById(R.id.editText_cardNum2);
        num3 = (EditText) findViewById(R.id.editText_cardNum3);
        num4 = (EditText) findViewById(R.id.editText_cardNum4);
        expireMonth = (EditText) findViewById(R.id.editText_expireMonth);
        expireYear = (EditText) findViewById(R.id.editText_expireYear);
        securityCode = (EditText) findViewById(R.id.editText_securityCode);
        holderFirstName = (EditText) findViewById(R.id.editText_cardFirstName);
        holderLastName = (EditText) findViewById(R.id.editText_cardLastName);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CreditCard = new Intent(Client_CreditCardNum.this, Client_CreditCard.class);
                startActivity(nextPage_CreditCard);
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_card_number);

        btnFunction();
    }
}

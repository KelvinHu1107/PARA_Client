package com.newnergy.para_client;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client_CreditCardNum extends AppCompatActivity {

    private TextView cancel, save;
    private EditText num1, num2, num3, num4, holderFirstName, holderLastName, securityCode;
    private Spinner expireMonth, expireYear;


    public String readData(String openFileName){
        try {

            FileInputStream fileInputStream = openFileInput(openFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            ValueMessager.readDataBuffer = bufferedReader.readLine();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ValueMessager.readDataBuffer == null){
            return "";
        }
        else {
            return ValueMessager.readDataBuffer.toString();
        }
    }

    public void writeData(String fileName, String writeData){

        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName,MODE_PRIVATE);
            fileOutputStream.write(writeData.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnFunction(){


        cancel = (TextView) findViewById(R.id.textView_cancel_cardNum);
        save = (TextView) findViewById(R.id.textView_save_cardNum);
        num1 = (EditText) findViewById(R.id.editText_cardNum1);
        num2 = (EditText) findViewById(R.id.editText_cardNum2);
        num3 = (EditText) findViewById(R.id.editText_cardNum3);
        num4 = (EditText) findViewById(R.id.editText_cardNum4);
        expireMonth = (Spinner) findViewById(R.id.spinner_expireMonth);
        expireYear = (Spinner) findViewById(R.id.spinner_expireYear);
        securityCode = (EditText) findViewById(R.id.editText_securityCode);
        holderFirstName = (EditText) findViewById(R.id.editText_cardFirstName);
        holderLastName = (EditText) findViewById(R.id.editText_cardLastName);

        if(readData("cardNum1") != null) {
            num1.setHintTextColor(Color.parseColor("#666666"));
            num1.setHint(readData("cardNum1"));
        }
        else{
            num1.setHintTextColor(Color.parseColor("#666666"));
            num1.setHint("Type here");
        }

        if(readData("cardNum2") != null) {
            num2.setHintTextColor(Color.parseColor("#666666"));
            num2.setHint(readData("cardNum2"));
        }
        else{
            num2.setHintTextColor(Color.parseColor("#666666"));
            num2.setHint("Type here");
        }

        if(readData("cardNum3") != null) {
            num3.setHintTextColor(Color.parseColor("#666666"));
            num3.setHint(readData("cardNum3"));
        }
        else{
            num3.setHintTextColor(Color.parseColor("#666666"));
            num3.setHint("Type here");
        }

        if(readData("cardNum4") != null) {
            num4.setHintTextColor(Color.parseColor("#666666"));
            num4.setHint(readData("cardNum4"));
        }
        else{
            num4.setHintTextColor(Color.parseColor("#666666"));
            num4.setHint("Type here");
        }

        if(readData("expireMonth") != null) {
            expireMonth.setPrompt(readData("expireMonth"));
        }
        if(readData("expireYear") != null) {
            expireYear.setPrompt(readData("expireYear"));
        }
        if(readData("securityCode") != null) {
            securityCode.setHintTextColor(Color.parseColor("#666666"));
            securityCode.setHint(readData("securityCode"));
        }
        else{
            securityCode.setHintTextColor(Color.parseColor("#666666"));
            securityCode.setHint("Type here");
        }

        if(readData("holderFirstName") != null) {
            holderFirstName.setHintTextColor(Color.parseColor("#666666"));
            holderFirstName.setHint(readData("holderFirstName"));
        }
        else{
            holderFirstName.setHintTextColor(Color.parseColor("#666666"));
            holderFirstName.setHint("Type here");
        }

        if(readData("holderLastName") != null) {
            holderLastName.setHintTextColor(Color.parseColor("#666666"));
            holderLastName.setHint(readData("holderLastName"));
        }
        else{
            holderLastName.setHintTextColor(Color.parseColor("#666666"));
            holderLastName.setHint("Type here");
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CreditCard = new Intent(Client_CreditCardNum.this, Client_CreditCard.class);
                startActivity(nextPage_CreditCard);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (num1.getText().toString() != "")
                writeData("cardNum1",num1.getText().toString() );
                if (num2.getText().toString() != "")
                writeData("cardNum2",num2.getText().toString() );
                if (num3.getText().toString() != "")
                writeData("cardNum3",num3.getText().toString() );
                if (num4.getText().toString() != "")
                writeData("cardNum4",num4.getText().toString() );
                if (expireMonth.getSelectedItem().toString() != "")
                writeData("expireMonth",expireMonth.getSelectedItem().toString() );
                if (expireYear.getSelectedItem().toString() != "")
                writeData("expireYear",expireYear.getSelectedItem().toString() );
                if (securityCode.getText().toString() != "")
                writeData("securityCode",securityCode.getText().toString() );
                if (holderFirstName.getText().toString() != "")
                writeData("holderFirstName",holderFirstName.getText().toString() );
                if (holderLastName.getText().toString() != "")
                writeData("holderLastName",holderLastName.getText().toString() );

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

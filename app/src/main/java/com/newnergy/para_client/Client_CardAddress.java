package com.newnergy.para_client;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client_CardAddress extends AppCompatActivity {

    private TextView cancel, save;
    private EditText street, suburb, city, country, postCode;

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

        cancel = (TextView) findViewById(R.id.textView_cancel_card_address);
        save = (TextView) findViewById(R.id.textView_save_cardAddress);
        street = (EditText) findViewById(R.id.edit_cardAddress_street);
        suburb = (EditText) findViewById(R.id.editText_cardAddress_suburb);
        city = (EditText) findViewById(R.id.editText_cardAddress_city);
        country = (EditText) findViewById(R.id.edit_cardAddress_country);
        postCode = (EditText) findViewById(R.id.editText_postCode);

        if(readData("creditCardStreet") != null) {
            street.setHintTextColor(Color.parseColor("#666666"));
            street.setHint(readData("creditCardStreet"));
        }
        else{
            street.setHintTextColor(Color.parseColor("#666666"));
            street.setHint("Type here");
        }


        if(readData("creditCardSuburb") != null) {
            suburb.setHintTextColor(Color.parseColor("#666666"));
            suburb.setHint(readData("creditCardSuburb"));
        }
        else{
            street.setHintTextColor(Color.parseColor("#666666"));
            street.setHint("Type here");
        }

        if(readData("creditCardCity") != null) {
            city.setHintTextColor(Color.parseColor("#666666"));
            city.setHint(readData("creditCardCity"));
        }
        else{
            street.setHintTextColor(Color.parseColor("#666666"));
            street.setHint("Type here");
        }

        if(readData("creditCardCountry") != null) {
            country.setHintTextColor(Color.parseColor("#666666"));
            country.setHint(readData("creditCardCountry"));
        }
        else{
            street.setHintTextColor(Color.parseColor("#666666"));
            street.setHint("Type here");
        }

        if(readData("creditCardPostCode") != null) {
            postCode.setHintTextColor(Color.parseColor("#666666"));
            postCode.setHint(readData("creditCardPostCode"));
        }
        else{
            street.setHintTextColor(Color.parseColor("#666666"));
            street.setHint("Type here");
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_CreditCard = new Intent(Client_CardAddress.this, Client_CreditCard.class);
                startActivity(nextPage_CreditCard);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                writeData("creditCardStreet",street.getText().toString());
                writeData("creditCardSuburb",suburb.getText().toString());
                writeData("creditCardCity",city.getText().toString());
                writeData("creditCardCountry",country.getText().toString());
                writeData("creditCardPostCode",postCode.getText().toString());

                Intent nextPage_CreditCard = new Intent(Client_CardAddress.this, Client_CreditCard.class);
                startActivity(nextPage_CreditCard);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_card_address);

        btnFunction();

    }
}

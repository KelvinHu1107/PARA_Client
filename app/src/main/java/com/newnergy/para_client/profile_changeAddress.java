package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.string.cancel;

public class profile_changeAddress extends AppCompatActivity {
    public TextView title;
    public ImageView back, save;
    public EditText editAddress_street, editAddress_suburb, editAddress_city;

    public void btnFunction(){
        save = (ImageView) findViewById(R.id.imageView_ok);
        back = (ImageView) findViewById(R.id.imageView_back);
        title = (TextView) findViewById(R.id.tree_field_title);
        editAddress_street = (EditText) findViewById(R.id.edit_address_street);
        editAddress_suburb = (EditText) findViewById(R.id.edit_address_suburb);
        editAddress_city = (EditText) findViewById(R.id.edit_address_city);

        title.setText("Change address");

        editAddress_street.setText(ValueMessager.address_street);
        editAddress_suburb.setText(ValueMessager.address_suburb);
        editAddress_city.setText(ValueMessager.address_city);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Profile = new Intent(profile_changeAddress.this, Client_Profile.class);
                startActivity(nextPage_Profile);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueMessager.address_street = editAddress_street.getText();
                ValueMessager.address_suburb = editAddress_suburb.getText();
                ValueMessager.address_city = editAddress_city.getText();

                DataSendController c = new DataSendController(){
                    @Override
                    public void onResponse(Boolean result) {
                        super.onResponse(result);
                    }
                };

                AddressModel model=new AddressModel();
                model.setId(ValueMessager.address_id);
                model.setStreet(ValueMessager.address_street.toString());
                model.setSuburb(ValueMessager.address_suburb.toString());
                model.setCity(ValueMessager.address_city.toString());
                String data= AddressDataConvert.ModelToJson(model);
                c.execute("http://para.co.nz/api/Address/UpdateAddress", data, "PUT");

                Intent nextPage_Profile = new Intent(profile_changeAddress.this, Client_Profile.class);
                startActivity(nextPage_Profile);
            }
        });
    }
    // assigning buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_change_address);

        btnFunction();
    }
}
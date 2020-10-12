package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class profile_changeName extends AppCompatActivity {

    public TextView title;
    public ImageView back, save;
    public EditText editFirstName, editLastName;

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
        return ValueMessager.readDataBuffer.toString();
    }

    public void writeData(){

        try {
            FileOutputStream fileOutputStream = openFileOutput("profile_data_name",MODE_PRIVATE);
            fileOutputStream.write(ValueMessager.name.toString().getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // assigning buttons
    public void btnFunction(){
        save = (ImageView) findViewById(R.id.imageView_ok);
        back = (ImageView) findViewById(R.id.imageView_back);
        title = (TextView) findViewById(R.id.tree_field_title);
        editFirstName = (EditText) findViewById(R.id.edit_name_first);
        editLastName = (EditText) findViewById(R.id.edit_name_last);

        title.setText("Change name");
        editFirstName.setText(ValueMessager.userFirstName);
        editLastName.setText(ValueMessager.userLastName);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Profile = new Intent(profile_changeName.this, Client_Profile.class);
                startActivity(nextPage_Profile);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataSendController c = new DataSendController(){
                    @Override
                    public void onResponse(Boolean result) {
                        super.onResponse(result);
                    }
                };

                ValueMessager.userFirstName = editFirstName.getText().toString();
                ValueMessager.userLastName = editLastName.getText().toString();
                ClientProfileViewModel model=new ClientProfileViewModel();
                model.setUsername(readData("userEmail"));
                model.setFirstName(ValueMessager.userFirstName);
                model.setLastName(ValueMessager.userLastName);
                String data= ClientProfileDataConvert.ModelToJson(model);
                c.execute("http://para.co.nz/api/ClientProfile/UpdateClient", data, "PUT");

                Intent nextPage_Profile = new Intent(profile_changeName.this, Client_Profile.class);
                startActivity(nextPage_Profile);
            }
        });
    }
    // assigning buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_change_name);

        btnFunction();
    }
}

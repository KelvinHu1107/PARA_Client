package com.newnergy.para_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Client_Setting_Notification extends AppCompatActivity {

    private TextView cancel, on, off;


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


        cancel = (TextView) findViewById(R.id.textView_cancel_notification);
        on = (TextView) findViewById(R.id.button_notification_on);
        off = (TextView) findViewById(R.id.button_NotificationOff);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Setting = new Intent(Client_Setting_Notification.this, Client_SlidingMenu_Setting.class);
                startActivity(nextPage_Setting);
            }
        });

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueMessager.notificationSwitch = true;

                writeData("notification","On");
                Intent nextPage_Setting = new Intent(Client_Setting_Notification.this, Client_SlidingMenu_Setting.class);
                startActivity(nextPage_Setting);
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueMessager.notificationSwitch = false;

                writeData("notification","Off");
                Intent nextPage_Setting = new Intent(Client_Setting_Notification.this, Client_SlidingMenu_Setting.class);
                startActivity(nextPage_Setting);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_notificaiton);

        btnFunction();
    }
}

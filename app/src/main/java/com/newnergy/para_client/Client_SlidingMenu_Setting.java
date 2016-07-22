package com.newnergy.para_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Client_SlidingMenu_Setting extends AppCompatActivity {

    private LinearLayout changeCreditCard, changeNotification;
    private TextView back;


    public void btnFunction() {

        changeCreditCard = (LinearLayout) findViewById(R.id.linearLayout_changeCreditCard);
        changeNotification = (LinearLayout) findViewById(R.id.linearLayout_changeNotification);
        back = (TextView) findViewById(R.id.textView_setting_back);

        changeCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_ChangeCreditCard = new Intent(Client_SlidingMenu_Setting.this, Client_CreditCard.class);
                startActivity(nextPage_ChangeCreditCard);
            }
        });

        changeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_ChangeNotification = new Intent(Client_SlidingMenu_Setting.this, Client_Setting_Notification.class);
                startActivity(nextPage_ChangeNotification);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               switch (ValueMessager.settingLastPage) {

                   case 1:
                   Intent nextPage_IncomingServices = new Intent(Client_SlidingMenu_Setting.this, Client_Incoming_Services.class);
                   startActivity(nextPage_IncomingServices);
                       break;
                   case 2:
                       Intent nextPage_Message = new Intent(Client_SlidingMenu_Setting.this, Client_Message.class);
                       startActivity(nextPage_Message);
                       break;
                   case 3:
                       Intent nextPage_Pending = new Intent(Client_SlidingMenu_Setting.this, Client_Pending.class);
                       startActivity(nextPage_Pending);
                       break;
                   case 4:
                       Intent nextPage_Profile = new Intent(Client_SlidingMenu_Setting.this, Client_Profile.class);
                       startActivity(nextPage_Profile);
                       break;
               }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_menu_setting);

        btnFunction();

    }
}

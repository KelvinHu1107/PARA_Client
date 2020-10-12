package com.newnergy.para_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

public class Client_PopUp_Version extends Activity {

    TextView name;
    Button yes;

    public void disableNotification(String username) {
        DataSendController controller = new DataSendController() {
            @Override
            public void onResponse(Boolean s) {
                Intent intent = new Intent(Client_PopUp_Version.this, Client_LoginActivity.class);
                startActivity(intent);
            }
        };
        String token = FirebaseInstanceId.getInstance().getToken();
        String data="{\"Username\":\"" + username + "\","
                +"\"NotificationToken\":\"" + token + "\"}";
        System.out.println(data);
        controller.execute("http://para.co.nz/api/NoticeAndroid/ClientRemoveToken",data,"POST");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_popup_version);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width,height);

        yes = (Button) findViewById(R.id.button_popUpLogOut_yes);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableNotification(ValueMessager.email.toString());
            }
        });

    }
}
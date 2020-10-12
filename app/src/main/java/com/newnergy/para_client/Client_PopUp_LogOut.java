package com.newnergy.para_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import static com.newnergy.para_client.ValueMessager.intent;

public class Client_PopUp_LogOut extends Activity {

    CircleImageView img;
    TextView name;
    Button yes, no;

    public void disableNotification(String username) {
        DataSendController controller = new DataSendController() {
            @Override
            public void onResponse(Boolean s) {

                ValueMessager.notificationPending = 0;
                ValueMessager.notificationMessage = 0;

                //Intent intent = new Intent(Client_PopUp_LogOut.this, Client_LoginActivity.class);
                Intent intent = new Intent(Client_PopUp_LogOut.this, Client_SplashScreen.class);
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
        setContentView(R.layout.client_popup_logout);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width,height);

        img = (CircleImageView) findViewById(R.id.imageView_logOut_pic);
        name = (TextView) findViewById(R.id.textView_logOut_name);
        yes = (Button) findViewById(R.id.button_popUpLogOut_yes);
        no = (Button) findViewById(R.id.button_popUpLogOut_no);

        if(ValueMessager.userProfileBitmap != null)
        img.setImageBitmap(ValueMessager.userProfileBitmap);

        name.setText(ValueMessager.userFirstName+" "+ ValueMessager.userLastName);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableNotification(ValueMessager.email.toString());
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_PopUp_LogOut.this, Client_Setting.class);
                startActivity(intent);
            }
        });

    }
}

package com.newnergy.para_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class Client_Confirming_SecuredFund extends Activity {

    CircleImageView circleImageView;
    TextView name, continueTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_confirming_secured_fund);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width,height);

        circleImageView = (CircleImageView) findViewById(R.id.imageView_confirming_pic);
        name = (TextView) findViewById(R.id.textView_confirmming_name);
        continueTv = (TextView) findViewById(R.id.textView_continue);

        circleImageView.setImageBitmap(ValueMessager.userProfileBitmap);
        name.setText(ValueMessager.userFirstName+" "+ValueMessager.userLastName);

        continueTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_Confirming_SecuredFund.this, Client_AddFund.class);
                startActivity(intent);
            }
        });
    }
}

package com.newnergy.para_client.ResetPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.newnergy.para_client.Client_LoginActivity;
import com.newnergy.para_client.R;
import com.newnergy.para_client.ValueMessager;

public class Client_ResetPassword2 extends AppCompatActivity {

    TextView title, email, confirm;
    ImageView back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_forget_password2);

        back = (ImageView) findViewById(R.id.imageView_back);
        next = (ImageView) findViewById(R.id.imageView_ok);
        title = (TextView) findViewById(R.id.tree_field_title);
        email = (TextView) findViewById(R.id.textView_email);
        confirm = (TextView) findViewById(R.id.textView_confirm);

        back.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        title.setText("Reset Password");
        email.setText(ValueMessager.userEmailBuffer);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_ResetPassword2.this, Client_LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
package com.newnergy.para_client.ResetPassword;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newnergy.para_client.Client_LoginActivity;
import com.newnergy.para_client.Client_LoginController;
import com.newnergy.para_client.Loading_Dialog;
import com.newnergy.para_client.R;
import com.newnergy.para_client.ValueMessager;

public class Client_ResetPassword extends AppCompatActivity {

    EditText editText;
    TextView confirm, title, error;
    ImageView back, next;
    Context context = this;
    Loading_Dialog myLoading;

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        RefreshTokenController controller = new RefreshTokenController(){
//            @Override
//            public void response(boolean result) {
//
//                DataTransmitController c = new DataTransmitController() {
//                    @Override
//                    public void onResponse(String result) {
//                        super.onResponse(result);
//                        myLoading.CloseLoadingDialog();
//
//                        String outSide[] = result.trim().split("\"");
//
//                        String info1[] = ValueMessager.currentVersion.trim().split("\\.");
//                        String info2[] = outSide[1].trim().split("\\.");
//
//                        if(!info1[0].equals(info2[0])){
//                            Intent intent = new Intent(Client_ResetPassword.this, Client_PopUp_Version.class);
//                            startActivity(intent);
//                        }
//                    }
//                };
//                c.execute("http://para.co.nz/api/version/getversion", "", "GET");
//            }
//        };
//
//        myLoading.ShowLoadingDialog();
//        controller.refreshToken(ValueMessager.email.toString(), ValueMessager.refreshToken);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_forget_password);
        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        editText = (EditText) findViewById(R.id.editText);
        confirm = (TextView) findViewById(R.id.textView_confirm);
        title = (TextView) findViewById(R.id.tree_field_title);
        error = (TextView) findViewById(R.id.textView_error);
        back = (ImageView) findViewById(R.id.imageView_back);
        next = (ImageView) findViewById(R.id.imageView_ok);

        title.setText("Reset password");
        next.setVisibility(View.INVISIBLE);
        error.setVisibility(View.INVISIBLE);
        editText.setHint("Email address...");
        editText.setHintTextColor(Color.parseColor("#666666"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_ResetPassword.this, Client_LoginActivity.class);
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().equals("")){
                    error.setVisibility(View.VISIBLE);
                }else{

                    Client_LoginController controller = new Client_LoginController() {
                        @Override
                        public void onResponse(Boolean s) {
                            super.onResponse(s);
                            if (s) {

                                Client_LoginController controller = new Client_LoginController() {
                                    @Override
                                    public void onResponse(Boolean s) {
                                        super.onResponse(s);

                                        ValueMessager.userEmailBuffer = editText.getText().toString();
                                        myLoading.CloseLoadingDialog();
                                        Intent intent = new Intent(Client_ResetPassword.this, Client_ResetPassword2.class);
                                        startActivity(intent);
                                    }
                                };

                                controller.execute("http://para.co.nz/api/ClientAccount/ResetPassword/", "{'username':'" + editText.getText() + "'}", "PUT");

                            } else {
                                error.setVisibility(View.VISIBLE);
                                myLoading.CloseLoadingDialog();
                            }

                        }
                    };
                    myLoading.ShowLoadingDialog();
                    controller.execute("http://para.co.nz/api/ClientAccount/CheckDuplicateUsername", "{'username':'" + editText.getText() + "'}", "POST");
                }
            }
        });

    }
}
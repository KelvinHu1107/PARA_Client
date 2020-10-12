package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Client_ChangePassword extends AppCompatActivity {
    TextView title, error;
    ImageView next,back;
    EditText currentPassword, newPassword, confirmPassword;
    Loading_Dialog myLoading;
    Context context = this;

    @Override
    public void onResume()
    {
        super.onResume();
        RefreshTokenController controller = new RefreshTokenController(){
            @Override
            public void response(boolean result) {

                DataTransmitController c = new DataTransmitController() {
                    @Override
                    public void onResponse(String result) {
                        super.onResponse(result);
                        myLoading.CloseLoadingDialog();

                        String outSide[] = result.trim().split("\"");

                        String info1[] = ValueMessager.currentVersion.trim().split("\\.");
                        String info2[] = outSide[1].trim().split("\\.");

                        if(!info1[0].equals(info2[0])){
                            Intent intent = new Intent(Client_ChangePassword.this, Client_PopUp_Version.class);
                            startActivity(intent);
                        }
                    }
                };
                c.execute("http://para.co.nz/api/version/getversion", "", "GET");
            }
        };

        myLoading.ShowLoadingDialog();
        controller.refreshToken(ValueMessager.email.toString(), ValueMessager.refreshToken);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_change_password);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        title = (TextView) findViewById(R.id.tree_field_title);
        error = (TextView) findViewById(R.id.textView_error);
        next = (ImageView) findViewById(R.id.imageView_ok);
        back = (ImageView) findViewById(R.id.imageView_back);
        currentPassword = (EditText) findViewById(R.id.editText_current);
        newPassword = (EditText) findViewById(R.id.editText_new);
        confirmPassword = (EditText) findViewById(R.id.editText_confirm);

        title.setText("Change password");

        error.setText("");
        currentPassword.setHint("Enter current password");
        newPassword.setHint("Enter new password");
        confirmPassword.setHint("Confirm new password");
        currentPassword.setHintTextColor(Color.parseColor("#999999"));
        newPassword.setHintTextColor(Color.parseColor("#999999"));
        confirmPassword.setHintTextColor(Color.parseColor("#999999"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_ChangePassword.this, Client_Profile.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentPassword.getText().toString().equals("")){
                    error.setText("Current password can not be empty!");
                }else {


                    LoginController controller = new LoginController() {
                        @Override
                        public void onResponse(String s ) {
                            super.onResponse(s);

                            AccountDataConvert convert=new AccountDataConvert();
                            AccountViewModel model=convert.convertJsonToModel(s);
                            if (model.getSuccess()) {

                                if(newPassword.getText().toString().equals("")){
                                    error.setText("Set up a new password!");
                                    myLoading.CloseLoadingDialog();
                                }else if(confirmPassword.getText().equals("")){
                                    error.setText("Confirm new password!");
                                    myLoading.CloseLoadingDialog();
                                }else if(!newPassword.getText().toString().equals(confirmPassword.getText().toString())){
                                    error.setText("The new password does not match confirming password!");
                                    myLoading.CloseLoadingDialog();
                                }else{

                                    Client_LoginController controller = new Client_LoginController() {
                                        @Override
                                        public void onResponse(Boolean s) {
                                            super.onResponse(s);
                                            if (s) {
                                                myLoading.CloseLoadingDialog();
                                                Intent intent = new Intent(Client_ChangePassword.this, Client_Profile.class);
                                                startActivity(intent);
                                            } else {
                                                myLoading.CloseLoadingDialog();
                                                Toast.makeText(Client_ChangePassword.this, "Change password failed!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    };
                                    myLoading.ShowLoadingDialog();
                                    controller.execute("http://para.co.nz/api/ClientAccount/UpdatePassword", "{'Username':'" + ValueMessager.email + "'," +
                                            "'Password':'" + newPassword.getText().toString() + "'}", "PUT");

                                }

                            } else {
                                myLoading.CloseLoadingDialog();
                                Toast.makeText(Client_ChangePassword.this, "Invalid password", Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    myLoading.ShowLoadingDialog();
                    controller.execute("http://para.co.nz/api/ClientAccount/ValidateAccount", "{'username':'" + ValueMessager.email + "'," +
                            "'password':'" + currentPassword.getText().toString() + "'}", "POST");
                }
            }
        });
    }
}
package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GaoxinHuang on 2016/6/2.
 */
public class Client_RegisterActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText EtEmail;
    private EditText EtPassword;
    private EditText EtConfirmPassword;
    private EditText EtPhoneNumber;
    private EditText EtFirstName;
    private EditText EtLastName;
    private TextView tvToolbarNext;
    private TextView tvToolbarBack;
    private TextView tvEmailLeft;
    private TextView tvPasswordLeft;
    private TextView tvConfirmLeft;
    private TextView tvFirstNameLeft;
    private TextView tvLastNameLeft;
    private TextView tvPhoneLeft;
    private TextView tvWarningMessage;
    JSONObject response;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_register);

        initComponent();
        tvWarningMessage.setVisibility(View.INVISIBLE);
        setToolbarComponent();

    }

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

    public void initComponent() {

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata");

        toolbar = (Toolbar) findViewById(R.id.toolbar_register_template);
        EtEmail = (EditText) findViewById(R.id.et_register_email);
        EtPassword = (EditText) findViewById(R.id.et_register_password);
        EtConfirmPassword = (EditText) findViewById(R.id.et_register_confirm_password);
        EtPhoneNumber = (EditText) findViewById(R.id.et_register_phone);
        EtFirstName = (EditText) findViewById(R.id.et_register_first_name);
        EtLastName = (EditText) findViewById(R.id.et_register_last_name);
        tvToolbarNext = (TextView) findViewById(R.id.toolbar_register_next);
        tvToolbarBack = (TextView) findViewById(R.id.toolbar_register_back);
        tvWarningMessage = (TextView) findViewById(R.id.textView_register_warning);
        tvEmailLeft = (TextView) findViewById(R.id.textView_email_left);
        tvPasswordLeft = (TextView) findViewById(R.id.textView_password_left);
        tvConfirmLeft = (TextView) findViewById(R.id.textView_confirm_left);
        tvFirstNameLeft = (TextView) findViewById(R.id.textView_firstName_left);
        tvLastNameLeft = (TextView) findViewById(R.id.textView_lastName_left);
        tvPhoneLeft = (TextView) findViewById(R.id.textView_phone_left);

        try {

            if(ValueMessager.registerByFb) {

                ValueMessager.email = readData("userEmail");

                response = new JSONObject(jsondata);
                String names[] = response.get("name").toString().split(" ");
                EtEmail.setText(readData("userEmail"));
                EtFirstName.setText(names[0]);
                EtLastName.setText(names[1]);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setToolbarComponent() {
        setSupportActionBar(toolbar);
        tvToolbarNext.setOnClickListener(signUp(tvToolbarNext));
        tvToolbarBack.setOnClickListener(onClickBack(tvToolbarBack));
    }

    public View.OnClickListener signUp(View v) {
        View.OnClickListener event = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = EtPassword.getText().toString();
                final String confirmPassword = EtConfirmPassword.getText().toString();
                final String phoneNumber = EtPhoneNumber.getText().toString();
                final String emailAddress = EtEmail.getText().toString();
                final String firstName = EtFirstName.getText().toString();
                final String lastName = EtLastName.getText().toString();

                tvWarningMessage.setVisibility(View.INVISIBLE);


                if(password.equals("")){

                    tvWarningMessage.setVisibility(View.VISIBLE);
                    tvWarningMessage.setText("Warning, password cant not be empty!");
                    tvPasswordLeft.setTextColor(Color.parseColor("#f3736f"));

                }
                else if (confirmPassword.equals("")){
                    tvWarningMessage.setVisibility(View.VISIBLE);
                    tvWarningMessage.setText("Warning, password must be confirmed!");
                    tvConfirmLeft.setTextColor(Color.parseColor("#f3736f"));
                }
                else if(phoneNumber.equals("")){

                    tvWarningMessage.setVisibility(View.VISIBLE);
                    tvWarningMessage.setText("Warning, phone number cant not be empty!");
                    tvPhoneLeft.setTextColor(Color.parseColor("#f3736f"));
                }
                else if(emailAddress.equals("")){

                    tvWarningMessage.setVisibility(View.VISIBLE);
                    tvWarningMessage.setText("Warning, email address cant not be empty!");
                    tvEmailLeft.setTextColor(Color.parseColor("#f3736f"));
                }

                else if(!isEmail(emailAddress)){
                    tvWarningMessage.setVisibility(View.VISIBLE);
                    tvWarningMessage.setText("Warning, the email address contains illegal characters or format!");
                    tvEmailLeft.setTextColor(Color.parseColor("#f3736f"));
                }

               else if(firstName.equals("")) {

                    tvWarningMessage.setVisibility(View.VISIBLE);
                    tvWarningMessage.setText("Warning, first name cant not be empty!");
                    tvPasswordLeft.setTextColor(Color.parseColor("#f3736f"));
                }
                else if(lastName.equals("")){

                    tvWarningMessage.setVisibility(View.VISIBLE);
                    tvWarningMessage.setText("Warning, last name cant not be empty!");
                    tvPasswordLeft.setTextColor(Color.parseColor("#f3736f"));
                }
                else{
                    tvWarningMessage.setVisibility(View.INVISIBLE);
                }

                if (password.equals("") || confirmPassword.equals("") || phoneNumber.equals("") || emailAddress.equals("") || firstName.equals("") || lastName.equals("") || !isEmail(emailAddress)) {
                    Toast.makeText(Client_RegisterActivity.this, "Sorry, every column must be filled", Toast.LENGTH_LONG).show();
                } else {
                    if (password.equals(confirmPassword)) {
                        Client_RegisterController checkDuplicateUsername = new Client_RegisterController() {
                            @Override
                            public void onResponse(Boolean result) {
                                if (result) {
                                    tvWarningMessage.setVisibility(View.VISIBLE);
                                    tvWarningMessage.setText("Warning, this account has been registered!");
                                    tvPasswordLeft.setTextColor(Color.parseColor("#f3736f"));

                                } else {
                                    Client_RegisterController controller = new Client_RegisterController() {
                                        @Override
                                        public void onResponse(Boolean result) {
                                            super.onResponse(result);
                                            if (result) {

                                                writeData("userEmail",EtEmail.getText().toString());
                                                ValueMessager.email = EtEmail.getText().toString();

                                                SignalRHubConnection signalRHubConnection = new SignalRHubConnection(context);
                                                signalRHubConnection.startSignalR();

                                                Intent intent = new Intent(Client_RegisterActivity.this, Client_Incoming_Services.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(Client_RegisterActivity.this, "Unsuccessful ", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    };
                                    controller.execute("http://para.co.nz/api/ClientAccount/Post", "{'username':'" + emailAddress + "'," +
                                            "'password':'" + password + "'," +
                                            "'CellPhone':'" + phoneNumber + "'," +
                                            "'firstName':'" + firstName + "'," +
                                            "'lastName':'" + lastName + "'}", "POST");
                                }
                            }
                        };
                        checkDuplicateUsername.execute("http://para.co.nz/api/ClientAccount/CheckDuplicateUsername", "{'username':'" + emailAddress + "'}", "POST");

                    } else {
                        Toast.makeText(Client_RegisterActivity.this, "Password does not match ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        return event;
    }

    public boolean isEmail(String email){

        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
        //Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public View.OnClickListener onClickBack(View v) {
        View.OnClickListener event = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_RegisterActivity.this, Client_LoginActivity.class);
                startActivity(intent);
            }
        };
        return event;
    }
}

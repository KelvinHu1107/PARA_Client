package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Client_LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText EtEmail;
    private EditText EtPassword;
    private TextView TvToolbarDone;
    private TextView tvWarningMessage;
    private TextView tvEmail;
    private TextView tvPassword;
    private LoginButton facebookLogIn;
    private CallbackManager callbackManager = null;
    private AccessTokenTracker mtracker = null;
    private ProfileTracker mprofileTracker = null;
    Intent intent;
    ImageView vx;
    ImageView yx;
    Context context;


    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            Profile profile = Profile.getCurrentProfile();
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            Log.v("LoginActivity", response.toString());

                            try {
                                ValueMessager.userEmailBuffer = object.getString("email");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            intent = new Intent(Client_LoginActivity.this, Client_RegisterActivity.class);
                            intent.putExtra("jsondata",object.toString());


                                Client_LoginController controller = new Client_LoginController() {
                                    @Override
                                    public void onResponse(Boolean s) {
                                        super.onResponse(s);
                                        if (s) {
                                            ValueMessager.userLogInByFb = true;
                                            ValueMessager.email = ValueMessager.userEmailBuffer;
                                            writeData("userEmail", ValueMessager.email.toString());
                                            SignalRHubConnection signalRHubConnection = new SignalRHubConnection(context);
                                            signalRHubConnection.startSignalR();

                                            Intent nextPage_IncomingServices = new Intent(Client_LoginActivity.this, Client_Incoming_Services.class);
                                            startActivity(nextPage_IncomingServices);

                                        } else {

                                            startActivity(intent);
                                        }
                                    }
                                };
                            try {
                                controller.execute("http://para.co.nz/api/ClientAccount/CheckDuplicateUsername", "{'username':'" + object.getString("email") + "'}", "POST");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

            Bundle parameters=new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
            Profile.getCurrentProfile();


        }

        @Override
        public void onCancel() {
            //Toast.makeText(,"Login Cancelled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(FacebookException error) {

        }
    };


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ValueMessager.userLogInByFb = false;
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.client_login);


        callbackManager = CallbackManager.Factory.create();

        mtracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                Log.v("AccessTokenTracker", "oldAccessToken=" + oldAccessToken + "||" + "CurrentAccessToken" + currentAccessToken);
            }
        };

        mprofileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                Log.v("Session Tracker", "oldProfile=" + oldProfile + "||" + "currentProfile" + currentProfile);

            }
        };

        mtracker.startTracking();
        mprofileTracker.startTracking();

        initComponent();
        EtEmail.setHint("");
        EtEmail.setText(readData("userEmail"));
        EtPassword.setHint("");
        tvWarningMessage.setVisibility(View.INVISIBLE);
        setToolbarComponent();

    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isLoggedIn()) {
            LoginManager.getInstance().logOut();

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void initComponent() {
        EtEmail = (EditText) findViewById(R.id.et_login_email);
        EtPassword = (EditText) findViewById(R.id.et_login_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar_login_template);
        TvToolbarDone = (TextView) findViewById(R.id.toolbar_done);
        tvWarningMessage = (TextView) findViewById(R.id.textView_logIn_warning);
        tvEmail = (TextView) findViewById(R.id.textView_login_emailLeft);
        tvPassword = (TextView) findViewById(R.id.textView_login_passwordLeft);
        facebookLogIn = (LoginButton) findViewById(R.id.imageButton_faceBook_logIn);

        facebookLogIn.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        facebookLogIn.registerCallback(callbackManager, callback);

    }


    private void setToolbarComponent() {
        setSupportActionBar(toolbar);
        TvToolbarDone.setOnClickListener(login(TvToolbarDone));
    }

    public View.OnClickListener login(View v) {
        View.OnClickListener event = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EtEmail.getText().toString();
                String password = EtPassword.getText().toString();
                if (password.equals("") && email.equals("")) {

                    tvWarningMessage.setText("* Email address and password can not be empty");
                    tvWarningMessage.setVisibility(View.VISIBLE);
                    EtPassword.setHint("Can not be empty");
                    EtEmail.setHint("Can not be empty");
                    tvEmail.setTextColor(Color.parseColor("#f3736f"));
                    tvPassword.setTextColor(Color.parseColor("#f3736f"));

                }

                else if (password.equals("") ) {

                    tvWarningMessage.setText("* Password can not be empty");
                    tvWarningMessage.setVisibility(View.VISIBLE);
                    EtPassword.setHint("Can not be empty");
                    tvPassword.setTextColor(Color.parseColor("#f3736f"));
                }

                else if (email.equals("")) {

                    tvWarningMessage.setText("* Email can not be empty");
                    tvWarningMessage.setVisibility(View.VISIBLE);
                    EtEmail.setHint("Can not be empty");
                    tvEmail.setTextColor(Color.parseColor("#f3736f"));
                }

                else {
                    Client_LoginController controller = new Client_LoginController() {
                        @Override
                        public void onResponse(Boolean s) {
                            super.onResponse(s);
                            if (s) {
                                writeData("userEmail",EtEmail.getText().toString());
                                ValueMessager.email = EtEmail.getText().toString();

                                SignalRHubConnection signalRHubConnection = new SignalRHubConnection(context);
                                signalRHubConnection.startSignalR();

                                //Intent nextPage_IncomingServices = new Intent(Client_LoginActivity.this, Client_Chat.class);
                                Intent nextPage_IncomingServices = new Intent(Client_LoginActivity.this, Client_Incoming_Services.class);
                                startActivity(nextPage_IncomingServices);

                            } else {
                                Toast.makeText(Client_LoginActivity.this, "Invalid user name", Toast.LENGTH_LONG).show();
                            }
                        }
                    };
                    controller.execute("http://para.co.nz/api/ClientAccount/validateAccount", "{'username':'" + email + "'," +
                            "'password':'" + password + "'}", "POST");
                }
            }
        };
        return event;
    }

    public void onClickRegisterTv(View v) {
        Intent intent = new Intent(Client_LoginActivity.this, Client_RegisterActivity.class);
        startActivity(intent);
    }

    public void changeImage(View v) {

        GetImageController controller = new GetImageController() {
            @Override
            public void onResponse(Bitmap bitmap) {
                super.onResponse(bitmap);
                if (bitmap == null) {

                }
                vx.setImageBitmap(bitmap);
            }
        };
        controller.execute("http://para.co.nz/api/Image/GetImage/asp.net.png", "", "POST");
    }

    public void sendImage(View v) {
        Bitmap xxx = ((BitmapDrawable) yx.getDrawable()).getBitmap();
        SendImageController controller = new SendImageController() {
            @Override
            public void onResponse(Boolean result) {
                super.onResponse(result);
                if (result) {
                    Toast.makeText(Client_LoginActivity.this, "success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Client_LoginActivity.this, "Unsuccessful ", Toast.LENGTH_LONG).show();

                }

            }
        };
        controller.setBitmap(xxx);
        controller.execute("http://para.co.nz/api/image/uploadImage");
    }

}

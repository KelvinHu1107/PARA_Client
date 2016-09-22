package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private TextView tvWarningMessage;
    private LinearLayout linearLayout;
    private LoginButton facebookLogIn;
    private CallbackManager callbackManager = null;
    private AccessTokenTracker mtracker = null;
    private ProfileTracker mprofileTracker = null;
    private ClientProfileViewModel list;
    private ImageUnity imageUnity = new ImageUnity();
    Intent intent;
    ImageView vx;
    ImageView yx;
    Context context = this;
    Loading_Dialog myLoading;


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
                                            ValueMessager.registerByFb = false;
                                            ValueMessager.email = ValueMessager.userEmailBuffer;
                                            writeData("userEmail", ValueMessager.email.toString());
                                            SignalRHubConnection signalRHubConnection = new SignalRHubConnection();
                                            signalRHubConnection.startSignalR();

                                            getData(ValueMessager.email.toString());


                                        } else {

                                            ValueMessager.registerByFb = true;
                                            myLoading.CloseLoadingDialog();
                                            startActivity(intent);
                                        }
                                    }
                                };
                            try {
                                myLoading.ShowLoadingDialog();
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


    public void getProfileImageData() {
        GetImageController controller = new GetImageController() {
            @Override
            public void onResponse(Bitmap bitmap) {
                super.onResponse(bitmap);
                if (bitmap == null) {
                    myLoading.CloseLoadingDialog();
                    Intent nextPage_IncomingServices = new Intent(Client_LoginActivity.this, Client_Incoming_Services.class);
                    startActivity(nextPage_IncomingServices);
                    return;
                }

                ValueMessager.userProfileBitmap = imageUnity.toRoundBitmap(bitmap);

                myLoading.CloseLoadingDialog();

                Intent nextPage_IncomingServices = new Intent(Client_LoginActivity.this, Client_Incoming_Services.class);
                startActivity(nextPage_IncomingServices);
            }
        };
        controller.execute("http://para.co.nz/api/ClientProfile/GetClientProfileImage/"+ list.getProfilePicture(), "","GET");
    }

    public void getData(String userName){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                list = ClientDataConvert.convertJsonToModel(result);

                ValueMessager.userFirstName = list.getFirstName();
                ValueMessager.userLastName = list.getLastName();

                getProfileImageData();

            }
        };

        c.execute("http://para.co.nz/api/ClientProfile/getClientDetail/"+userName,"","GET");

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
        setContentView(R.layout.client_login_page);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        int width = metric.widthPixels;
        int height = metric.heightPixels;

        if(width == 720)
            ValueMessager.resolution1080x720 = true;
        else if(width == 480)
            ValueMessager.resolution800x480 = true;
        //else if(width == 1080)
        else
            ValueMessager.resolution1920x1080 = true;



        System.out.println("xxxxxxxxxx"+ width+"yyy===="+height);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

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
        tvWarningMessage.setVisibility(View.INVISIBLE);
        setToolbarComponent();

        if(readData("userEmail")!="" && readData("userEmail") != null && readData("userEmail") != "On") {
            EtEmail.setHint("");
            EtEmail.setText(readData("userEmail"));
        }
        else{
            EtEmail.setText("");
        }

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
        tvWarningMessage = (TextView) findViewById(R.id.textView_logIn_warning);
        facebookLogIn = (LoginButton) findViewById(R.id.imageButton_faceBook_logIn);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_logIn);

        facebookLogIn.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        facebookLogIn.registerCallback(callbackManager, callback);

    }


    private void setToolbarComponent() {
        setSupportActionBar(toolbar);
        linearLayout.setOnClickListener(login(linearLayout));
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

                }

                else if (password.equals("") ) {

                    tvWarningMessage.setText("* Password can not be empty");
                    tvWarningMessage.setVisibility(View.VISIBLE);
                    EtPassword.setHint("Can not be empty");

                }

                else if (email.equals("")) {

                    tvWarningMessage.setText("* Email can not be empty");
                    tvWarningMessage.setVisibility(View.VISIBLE);
                    EtEmail.setHint("Can not be empty");

                }

                else {
                    Client_LoginController controller = new Client_LoginController() {
                        @Override
                        public void onResponse(Boolean s) {
                            super.onResponse(s);
                            if (s) {
                                writeData("userEmail",EtEmail.getText().toString());
                                ValueMessager.email = EtEmail.getText().toString();

                                SignalRHubConnection signalRHubConnection = new SignalRHubConnection();
                                signalRHubConnection.startSignalR();

                                getData(EtEmail.getText().toString());


                            } else {
                                Toast.makeText(Client_LoginActivity.this, "Invalid user name", Toast.LENGTH_LONG).show();
                                myLoading.CloseLoadingDialog();
                            }
                        }
                    };
                    myLoading.ShowLoadingDialog();
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

}

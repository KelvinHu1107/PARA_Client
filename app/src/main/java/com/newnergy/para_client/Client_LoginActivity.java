package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.newnergy.para_client.Chat.Chat_SingleChat_db;
import com.newnergy.para_client.Chat.Chat_User_db;
import com.newnergy.para_client.Image_package.ImageUnity;
import com.newnergy.para_client.ResetPassword.Client_ResetPassword;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import io.fabric.sdk.android.Fabric;

import static java.lang.System.currentTimeMillis;

public class Client_LoginActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "b2tHb2a4fF6yuRgj4xkAqy7U1";
    private static final String TWITTER_SECRET = "123";

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
    LinearLayout main, resetPassword;
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

                            intent = new Intent(Client_LoginActivity.this, Client_RegisterFaceBook.class);
                            intent.putExtra("jsondata",object.toString());

                            LoginController controller = new LoginController() {
                                    @Override
                                    public void onResponse(String s) {
                                        super.onResponse(s);
                                        AccountDataConvert convert=new AccountDataConvert();
                                        AccountViewModel model=convert.convertJsonToModel(s);
                                        if (model.getSuccess()) {

                                                writeData("userEmail",ValueMessager.email.toString());

                                                ValueMessager.chat_db_Global =new Chat_SingleChat_db(Client_LoginActivity.this);
                                                ValueMessager.chat_User_db_Global=new Chat_User_db(Client_LoginActivity.this);
                                                SignalRHubConnection signalRHubConnection = new SignalRHubConnection();
                                                signalRHubConnection.startSignalR();

                                                ValueMessager.accessToken = model.getAccessToken();
                                                ValueMessager.tokenDueTime = model.getExpiresIn() + currentTimeMillis();
                                                ValueMessager.refreshToken = model.getRefreshToken();

                                                getData(ValueMessager.email.toString());
                                        } else {
                                            ValueMessager.registerByFb = true;
                                            writeData("userEmail", ValueMessager.email.toString());
                                            myLoading.CloseLoadingDialog();
                                            startActivity(intent);
                                        }
                                    }
                                };
                            try {
                                myLoading.ShowLoadingDialog();
                                ValueMessager.email = object.getString("email");
                                controller.execute("http://para.co.nz/api/ClientAccount/IsFaceBook"+"/"+object.getString("email"),"", "POST");

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
                    connect(ValueMessager.email.toString());
                    getNotificationId();
                    myLoading.CloseLoadingDialog();
                    Intent nextPage_IncomingServices = new Intent(Client_LoginActivity.this, Client_Incoming_Services.class);
                    startActivity(nextPage_IncomingServices);
                    return;
                }

                ValueMessager.userProfileBitmap = imageUnity.toRoundBitmap(bitmap);
                connect(ValueMessager.email.toString());
                getNotificationId();
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

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);

        int width = metric.widthPixels;
        int height = metric.heightPixels;

        if(width == 720)
            ValueMessager.resolution1080x720 = true;
        else if(width == 480)
            ValueMessager.resolution800x480 = true;
        else if(width == 1080)
            ValueMessager.resolution1920x1080 = true;
        else if(width == 540)
            ValueMessager.resolution960x540 = true;
        else
            ValueMessager.resolution1080x720 = true;

        System.out.println("xxxxxxxxxx"+ width+"yyy===="+height);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        ValueMessager.userLogInByFb = false;
        FacebookSdk.sdkInitialize(getApplicationContext());

        if(ValueMessager.resolution1080x720)
            setContentView(R.layout.client_login_page720x1080);
        else if(ValueMessager.resolution800x480)
            setContentView(R.layout.client_login_page480x800);
        else if(ValueMessager.resolution1920x1080)
            setContentView(R.layout.client_login_page1080x1920);
        else if(ValueMessager.resolution960x540)
            setContentView(R.layout.client_login_page1080x1920);
        else
            setContentView(R.layout.client_login_page720x1080);

        System.out.println("ttttttttt:"+ FirebaseInstanceId.getInstance().getToken());

        ValueMessager.is_chat=false;

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
        main = (LinearLayout) findViewById(R.id.linearLayout_main);
        resetPassword = (LinearLayout) findViewById(R.id.linearLayout_resetPassword);

        facebookLogIn.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        facebookLogIn.registerCallback(callbackManager, callback);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()) {
                    Intent intent = new Intent(Client_LoginActivity.this, Client_ResetPassword.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Client_LoginActivity.this, "No network connection available.", Toast.LENGTH_LONG).show();
                }
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(context.INPUT_METHOD_SERVICE);
                boolean isOpen=imm.isActive();

                if(isOpen){
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });

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

                    if (isConnected()) {

                        LoginController controller = new LoginController() {
                            @Override
                            public void onResponse(String s) {
                                super.onResponse(s);
                                AccountDataConvert convert = new AccountDataConvert();
                                AccountViewModel model = convert.convertJsonToModel(s);
                                if (model.getSuccess()) {
                                    writeData("userEmail", EtEmail.getText().toString());
                                    ValueMessager.email = EtEmail.getText().toString();

                                    ValueMessager.chat_db_Global = new Chat_SingleChat_db(Client_LoginActivity.this);
                                    ValueMessager.chat_User_db_Global = new Chat_User_db(Client_LoginActivity.this);
                                    SignalRHubConnection signalRHubConnection = new SignalRHubConnection();
                                    signalRHubConnection.startSignalR();

                                    ValueMessager.accessToken = model.getAccessToken();
                                    ValueMessager.tokenDueTime = model.getExpiresIn() + currentTimeMillis();
                                    ValueMessager.refreshToken = model.getRefreshToken();

                                    getData(EtEmail.getText().toString());
                                } else {
                                    Toast.makeText(Client_LoginActivity.this, "Invalid user name", Toast.LENGTH_LONG).show();
                                    myLoading.CloseLoadingDialog();
                                }
                            }
                        };
                        myLoading.ShowLoadingDialog();
                        ValueMessager.email = EtEmail.getText().toString();
                        controller.execute("http://para.co.nz/api/ClientAccount/validateAccount", "{'username':'" + email + "'," +
                                "'password':'" + password + "'}", "POST");
                    }else{
                        Toast.makeText(Client_LoginActivity.this, "No network connection available.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        return event;
    }

    public void getNotificationId(){
        DataTransmitController c = new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    JSONArray notificationId = new JSONArray(object.getString("data"));

                    for (int i = 0; i < notificationId.length(); i++) {
                        JSONObject singleObject = new JSONObject(notificationId.get(i).toString());
                        if(singleObject.getString("Type").equalsIgnoreCase("message")) {

                            ValueMessager.listMessageId.add(singleObject.getString("MessageId"));
                            String[] info = singleObject.getString("body").split("-");
                            ValueMessager.listMessageFromUserName.add(info[1]);

                        }else if(singleObject.getString("Type").equalsIgnoreCase("pending")){

                            ValueMessager.listPendingId.add(singleObject.getString("MessageId"));
                            ValueMessager.listPendingServiceId.add(singleObject.getString("MessageId"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ValueMessager.notificationMessage = ValueMessager.listMessageId.size();
                ValueMessager.notificationPending = ValueMessager.listPendingId.size();
            }
        };
        c.execute("http://para.co.nz/api/NoticeAndroid/GetClientUnreadMessage/"+ ValueMessager.email,"","GET");
    }

    public void connect(String username) {
        DataSendController controller = new DataSendController() {
            @Override
            public void onResponse(Boolean s) {
                System.out.println();
            }
        };
        String token = FirebaseInstanceId.getInstance().getToken();
        String data="{\"Username\":\"" + username + "\","
                +"\"NotificationToken\":\"" + token + "\"}";

        controller.execute("http://para.co.nz/api/NoticeAndroid/AddNotificationClient",data,"POST");
    }


    public void onClickRegisterTv(View v) {
        if(isConnected()) {
            Intent intent = new Intent(Client_LoginActivity.this, Client_RegisterActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(Client_LoginActivity.this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
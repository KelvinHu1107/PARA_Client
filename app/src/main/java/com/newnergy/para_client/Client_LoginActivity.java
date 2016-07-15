package com.newnergy.para_client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Client_LoginActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText EtEmail;
    private EditText EtPassword;
    private TextView TvToolbarDone;
    ImageView vx;
    ImageView yx;
    private ArrayList<JobServiceViewModel> allJobServiceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_login);
        initComponent();
        setToolbarComponent();
    }

    private void initComponent() {
        EtEmail = (EditText) findViewById(R.id.et_login_email);
        EtPassword = (EditText) findViewById(R.id.et_login_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar_login_template);
        TvToolbarDone = (TextView) findViewById(R.id.toolbar_done);

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
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(Client_LoginActivity.this, "Neither email address nor password can be empty", Toast.LENGTH_LONG).show();
                } else {
                    Client_LoginController controller = new Client_LoginController() {
                        @Override
                        public void onResponse(Boolean s) {
                            super.onResponse(s);
                            if (s) {
                                Toast.makeText(Client_LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                                Intent nextPage_IncomingServices = new Intent(Client_LoginActivity.this, Client_Incoming_Services.class);
                                startActivity(nextPage_IncomingServices);
                            } else {
                                Toast.makeText(Client_LoginActivity.this, "Unsuccessful ", Toast.LENGTH_SHORT).show();
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

    private ArrayList getJavaCollection(String jsons) throws JSONException {

        JSONArray json = new JSONArray(jsons);

//        if(jsonArray!=null){
//            objs=new ArrayList<T>();
//            List list=(List)JSONSerializer.toJava(jsonArray);
//            for(Object o:list){
//                JSONObject jsonObject=JSONObject.fromObject(o);
//                T obj=(T) JSONObject.toBean(jsonObject, clazz.getClass());
//                objs.add(obj);
//            }
//        }
        return null;
    }
}

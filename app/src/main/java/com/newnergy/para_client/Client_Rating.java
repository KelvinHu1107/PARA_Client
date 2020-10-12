package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client_Rating extends AppCompatActivity {

    private TextView name, title;
    private RatingBar ratingBar;
    private EditText comment;
    private ImageButton confirm;
    private ImageView cancel, save, pic;
    Context context = this;
    Loading_Dialog myLoading;
    LinearLayout main;

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
                            Intent intent = new Intent(Client_Rating.this, Client_PopUp_Version.class);
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

    public void btnFunction() {

        cancel = (ImageView) findViewById(R.id.imageView_back);
        save = (ImageView) findViewById(R.id.imageView_ok);
        pic = (ImageView) findViewById(R.id.imageView_pic);
        name = (TextView) findViewById(R.id.textView_name);
        title = (TextView) findViewById(R.id.tree_field_title);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_rating);
        comment = (EditText) findViewById(R.id.editText_rating_comment);
        confirm = (ImageButton) findViewById(R.id.imageButton_rating_confirm);
        main = (LinearLayout) findViewById(R.id.linearLayout_main);

        save.setVisibility(View.INVISIBLE);
        title.setText("Rating");
        name.setText(ValueMessengerTaskInfo.providerFirstName+" "+ValueMessengerTaskInfo.providerLastName);
        Picasso.with(context).load(ValueMessengerTaskInfo.providerProfilePicUrl).into(pic);


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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_Rating.this, Client_Confirm2.class);
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddRatingDataConvert convert = new AddRatingDataConvert();
                AddRatingViewModel addRatingViewModel = new AddRatingViewModel();

                Client_RegisterController controller = new Client_RegisterController() {
                    @Override
                    public void onResponse(Boolean result) {
                        super.onResponse(result);

                            DataSendController c = new DataSendController(){
                                @Override
                                public void onResponse(Boolean result) {
                                    super.onResponse(result);

                                    if(result){
                                        myLoading.CloseLoadingDialog();

                                        Intent intent = new Intent(Client_Rating.this, Client_Confirm2.class);
                                        startActivity(intent);
                                    }
                                }
                            };
                            JobServiceStatusViewModel model = new JobServiceStatusViewModel();
                            model.setProviderUsername(ValueMessagerFurtherInfo.userName.toString());
                            model.setStatus(9);

                            String data2= new JobServiceStatusDataConvert().ModelToJson(model);
                            c.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+ValueMessengerTaskInfo.id, data2, "PUT");
                    }
                };

                addRatingViewModel.setProviderUsername(ValueMessagerFurtherInfo.userName.toString());
                addRatingViewModel.setComment(comment.getText().toString());
                addRatingViewModel.setRating(Double.parseDouble(Float.toString(ratingBar.getRating())));
                addRatingViewModel.setClientUsername(readData("userEmail"));
                addRatingViewModel.setServiceId(ValueMessengerTaskInfo.id);

                String data = convert.ModelToJson(addRatingViewModel);
                myLoading.ShowLoadingDialog();
                controller.execute("http://para.co.nz/api/ProviderRating/AddRating", data, "POST");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ValueMessager.resolution800x480){
            setContentView(R.layout.client_rating_480x800);
        }
        else {
            setContentView(R.layout.client_rating);
        }

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        btnFunction();
    }
}
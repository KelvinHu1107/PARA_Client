package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newnergy.para_client.Chat.Chat_SingleChat_db;
import com.newnergy.para_client.Chat.Chat_User_db;
import com.newnergy.para_client.Chat.Client_Message;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_Setting extends AppCompatActivity {

    public ImageButton placeOrder,main;
    public LinearLayout paymentBar,historyBar,aboutBar,logOutBar,share, delete, mainContainer, changePassword;
    public TextView name;
    public CircleImageView profilePicture;
    public Button button;

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
                            Intent intent = new Intent(Client_Setting.this, Client_PopUp_Version.class);
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

    public boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
    }

    public void messageNotification()
    {
        if(ValueMessager.notificationMessage>0) {
            ValueMessager.textMessage.setVisibility(View.VISIBLE);
            if(ValueMessager.notificationMessage > 0 && ValueMessager.notificationMessage < 10){
                ValueMessager.textMessage.setText(" "+ValueMessager.notificationMessage+" ");
            }else if(ValueMessager.notificationMessage > 9){
                if(isNumeric(String.valueOf(ValueMessager.notificationMessage))) {
                    ValueMessager.textMessage.setText(String.valueOf(ValueMessager.notificationMessage));
                }
            }else if(ValueMessager.notificationMessage>99){
                ValueMessager.textMessage.setText("99+");
            }
        }else{
            ValueMessager.textMessage.setVisibility(View.INVISIBLE);
        }

        if(ValueMessager.notificationPending>0) {
            ValueMessager.textPending.setVisibility(View.VISIBLE);
            if (ValueMessager.notificationPending > 0 && ValueMessager.notificationPending < 10) {
                ValueMessager.textPending.setText(" " + ValueMessager.notificationPending + " ");
            } else if (ValueMessager.notificationPending > 9) {
                if(isNumeric(String.valueOf(ValueMessager.notificationPending))) {
                    ValueMessager.textPending.setText(String.valueOf(ValueMessager.notificationPending));
                }
            } else if (ValueMessager.notificationPending > 99) {
                ValueMessager.textPending.setText("99+");
            }
        }else{
            ValueMessager.textPending.setVisibility(View.INVISIBLE);
        }
    }

    public void refreshData() {
        if(ValueMessager.userProfileBitmap != null)
        profilePicture.setImageBitmap(ValueMessager.userProfileBitmap);
        name.setText(ValueMessager.userFirstName+" "+ValueMessager.userLastName);
    }

    public void btnFunction() {
        ValueMessager.footerPending = (ImageButton) findViewById(R.id.imageButton_ProfileToPending);
        ValueMessager.footerMessage = (ImageButton) findViewById(R.id.imageButton_ProfileToMessage);
        ValueMessager.textMessage = (TextView) findViewById(R.id.textView_footerMessage);
        ValueMessager.textPending = (TextView) findViewById(R.id.textView_footerPending);
        placeOrder = (ImageButton) findViewById(R.id.imageButton_ProfileToPlaceOrder);
        main = (ImageButton) findViewById(R.id.imageButton_ProfileToMain);
        name = (TextView) findViewById(R.id.textView_setting_name);
        paymentBar = (LinearLayout) findViewById(R.id.setting_payment_bar);
        historyBar = (LinearLayout) findViewById(R.id.setting_history_bar);
        aboutBar = (LinearLayout) findViewById(R.id.setting_about_bar);
        logOutBar = (LinearLayout) findViewById(R.id.setting_logOut_bar);
        share = (LinearLayout) findViewById(R.id.linearLayout_share);
        delete = (LinearLayout) findViewById(R.id.linearLayout_delete);
        mainContainer = (LinearLayout) findViewById(R.id.linearLayout_main);
        profilePicture = (CircleImageView) findViewById(R.id.imageView_setting_profileImage);

        mainContainer.removeView(paymentBar);

        messageNotification();

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Main = new Intent(Client_Setting.this, Client_Incoming_Services.class);
                startActivity(nextPage_Main);
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ValueMessager.chat_db_Global!=null){
                    ValueMessager.chat_db_Global.setAlreadyRead(ValueMessager.email.toString());
                }

                Intent nextPage_Message = new Intent(Client_Setting.this, Client_PlaceOrder.class);
                startActivity(nextPage_Message);
            }
        });

        ValueMessager.footerMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Pending = new Intent(Client_Setting.this, Client_Message.class);
                startActivity(nextPage_Pending);
            }
        });

        ValueMessager.footerPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Payment = new Intent(Client_Setting.this, Client_Pending.class);
                startActivity(nextPage_Payment);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            ValueMessager.chat_User_db_Global.deleteAllChat();
            ValueMessager.chat_db_Global.deleteAllChat();

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Sharing para app");
                share.putExtra(Intent.EXTRA_TEXT, "This is good app, try it: http://newnergy.co.nz/");
                //share.putExtra(Intent.EXTRA_TEXT, Uri.parse("http://newnergy.co.nz/"));

                startActivity(Intent.createChooser(share, "Other share way"));
            }
        });

        paymentBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Client_Setting.this, Client_PaymentMethod.class);
                startActivity(intent);
            }
        });

        historyBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Client_Setting.this, Client_History.class);
                startActivity(intent);
            }
        });

        aboutBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangeCompanyName = new Intent(Client_Setting.this, Client_AboutUs.class);
                startActivity(nextPage_ChangeCompanyName);
            }
        });

        logOutBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_ChangeCompanyAddress = new Intent(Client_Setting.this, Client_PopUp_LogOut.class);
                startActivity(nextPage_ChangeCompanyAddress);
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_Setting.this, Client_Profile.class);
                startActivity(intent);
            }
        });
    }

    //write data into internal storage

    public void writeData(Bitmap image){

        try {
            FileOutputStream fileOutputStream = openFileOutput("profile_data_picture",MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

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
        setContentView(R.layout.content_setting);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);
        ValueMessager.chat_db_Global =new Chat_SingleChat_db(Client_Setting.this);
        ValueMessager.chat_User_db_Global=new Chat_User_db(Client_Setting.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        btnFunction();
        refreshData();
    }
}
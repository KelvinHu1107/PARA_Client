package com.newnergy.para_client.Chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.newnergy.para_client.ClientData;
import com.newnergy.para_client.ClientProfileViewModel;
import com.newnergy.para_client.Client_Incoming_Services;
import com.newnergy.para_client.Client_LoginController;
import com.newnergy.para_client.Client_Pending;
import com.newnergy.para_client.Client_PlaceOrder;
import com.newnergy.para_client.Client_PopUp_Version;
import com.newnergy.para_client.Client_Profile;
import com.newnergy.para_client.DataTransmitController;
import com.newnergy.para_client.JobServiceDataConvert;
import com.newnergy.para_client.JobServiceViewModel;
import com.newnergy.para_client.Loading_Dialog;
import com.newnergy.para_client.R;
import com.newnergy.para_client.RefreshTokenController;
import com.newnergy.para_client.ValueMessager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_Message extends AppCompatActivity {

    public ImageButton main,profile,post;
    ListAdapter_Message adapter;
    ListView listView;
    private  String[] objectName ;
    ClientData[] dataList;
    Context context = this;
    Loading_Dialog myLoading;


    private String[] fromEmail;
    private String[] toEmail;
    private String[] firstName;
    private String[] lastName;
    private String[] profileUrl;
    private String[] message;
    private int[] messageType;
    private String[] date;

    private List<JobServiceViewModel> list;
    private ClientProfileViewModel profileList;
    private ImageView profilePicture;
    private TextView profileName;
    List<String> listAllProviderEmail=new ArrayList<String>(), listAllSelfEmail=new ArrayList<String>();
    List<ProviderUserDate> listTemp=new ArrayList<ProviderUserDate>(), listTemp2=new ArrayList<ProviderUserDate>();
    List<ProviderUserDate> listProvider=new ArrayList<ProviderUserDate>();//all data

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
                            Intent intent = new Intent(Client_Message.this, Client_PopUp_Version.class);
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

    private void returnReadMessage(String returnId){

        Client_LoginController controller = new Client_LoginController() {
            @Override
            public void onResponse(Boolean s) {
                super.onResponse(s);

            }
        };
        String data= "{\"MessageId\":\"" + returnId +"\"}";
        controller.execute("http://para.co.nz/api/NoticeAndroid/ClientReadNoticeMessage", data, "PUT");
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

    public void getData(){
        DataTransmitController c=new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                list=   JobServiceDataConvert.convertJsonToArrayList(result);
                initList();
            }
        };
        c.execute("http://para.co.nz/api/JobService/getalljobservices","","GET");
    }

    //init listview
    public void initList(){

        dataList = new ClientData[listProvider.size()];
        objectName = new String[listProvider.size()];
        firstName = new String[listProvider.size()];
        lastName = new String[listProvider.size()];
        toEmail = new String[listProvider.size()];
        fromEmail = new String[listProvider.size()];
        messageType = new int[listProvider.size()];
        profileUrl = new String[listProvider.size()];
        message = new String[listProvider.size()];
        date = new String[listProvider.size()];


        for(int i = 0; i<listProvider.size(); i++){

            objectName[i] = listProvider.get(i).get_lastName();
            firstName[i] = listProvider.get(i).get_firstName();
            lastName[i] = listProvider.get(i).get_lastName();
            toEmail[i] = listProvider.get(i).get_toEmail();
            fromEmail[i] = listProvider.get(i).get_fromEmail();
            messageType[i] = listProvider.get(i).get_messageType();
            date[i] = listProvider.get(i).get_date();
            message[i] = listProvider.get(i).get_message();
            profileUrl[i] = listProvider.get(i).get_profileUrl();
        }

        adapter = new ListAdapter_Message(this, objectName, firstName, lastName, messageType, date, message, profileUrl, fromEmail, toEmail);
        listView.setAdapter(adapter);

    }
    //init listview

    public void btnFunction(){
        main = (ImageButton) findViewById(R.id.imageButton_message_to_main);
        ValueMessager.footerPending = (ImageButton) findViewById(R.id.imageButton_message_to_pending);
        profile = (ImageButton) findViewById(R.id.imageButton_message_to_profile);
        post = (ImageButton) findViewById(R.id.imageButton_post_message);
        ValueMessager.textPending = (TextView) findViewById(R.id.textView_footerPending);

        messageNotification();

        main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ValueMessager.is_chat = false;
                ValueMessager.notificationMessage = 0;
                ValueMessager.listMessageId.clear();
                Intent nextPage_main = new Intent(Client_Message.this, Client_Incoming_Services.class);
                startActivity(nextPage_main);
            }
        });

        post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ValueMessager.is_chat = false;
                ValueMessager.notificationMessage = 0;
                ValueMessager.listMessageId.clear();
                Intent intent = new Intent(Client_Message.this, Client_PlaceOrder.class);
                startActivity(intent);
            }
        });

        ValueMessager.footerPending.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ValueMessager.is_chat = false;
                ValueMessager.notificationMessage = 0;
                ValueMessager.listMessageId.clear();
                Intent nextPage_main = new Intent(Client_Message.this, Client_Pending.class);
                startActivity(nextPage_main);
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ValueMessager.is_chat = false;
                ValueMessager.notificationMessage = 0;
                ValueMessager.listMessageId.clear();
                Intent nextPage_main = new Intent(Client_Message.this, Client_Profile.class);
                startActivity(nextPage_main);
            }
        });


    }
    // assigning buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_message);

        myLoading = new Loading_Dialog();
        myLoading.getContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView_message);

        btnFunction();
        listAllProviderEmail=ValueMessager.chat_db_Global.getProviderEmail(ValueMessager.email.toString());//all provider email
        listAllSelfEmail=ValueMessager.chat_db_Global.getSelfEmail(ValueMessager.email.toString());

        for (int i=0;i<listAllProviderEmail.size();i++)
        {
            //System.out.println("test provider email"+listAllProvider.get(i));

            listTemp= ValueMessager.chat_User_db_Global.getUserData(listAllProviderEmail.get(i),
                    ValueMessager.email.toString());

            for (int j=0;j<listTemp.size();j++)
            {
                listProvider.add(listTemp.get(j));

            }

        }

        for (int i=0;i<listAllSelfEmail.size();i++)
        {
            //System.out.println("test provider email"+listAllProvider.get(i));

            listTemp2 = ValueMessager.chat_User_db_Global.getUserData(ValueMessager.email.toString(),
                    listAllSelfEmail.get(i));

            for (int j=0;j<listTemp2.size();j++)
            {
                listProvider.add(listTemp2.get(j));
            }

        }

        //kelvinkelvin add sending message database into message list and merge
        for(int i=0;i<listProvider.size();i++)
        {
            for(int j=0; j<listProvider.size();j++){
                if(listProvider.get(i).get_fromEmail().equals(listProvider.get(j).get_toEmail())&&listProvider.get(i).get_toEmail().equals(listProvider.get(j).get_fromEmail())){
                    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aa");

                    try {
                        if((Long)format.parse(listProvider.get(i).get_date()).getTime()>(Long)format.parse(listProvider.get(j).get_date()).getTime()){
                            if(listProvider.get(i).get_toEmail().equals(ValueMessager.email.toString())){
                                listProvider.get(j).set_messageType(9);
                            }else if(listProvider.get(i).get_fromEmail().equals(ValueMessager.email.toString())){
                                listProvider.get(j).set_date(listProvider.get(i).get_date());
                                listProvider.get(j).set_message(listProvider.get(i).get_message());

                                listProvider.get(i).set_messageType(9);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for(int i=0; i<listProvider.size(); i++){
            if(listProvider.get(i).get_messageType() == 9){
                listProvider.remove(i);
            }else if(listProvider.get(i).get_fromEmail().equals(ValueMessager.email)){
                String string = new String();

                string = listProvider.get(i).get_toEmail();
                listProvider.get(i).set_toEmail(ValueMessager.email.toString());
                listProvider.get(i).set_fromEmail(string);
            }

        }
        //kelvinkelvin

        initList();

        for(int i =0; i<ValueMessager.listMessageId.size(); i++){
            returnReadMessage(ValueMessager.listMessageId.get(i));
        }

    }
}
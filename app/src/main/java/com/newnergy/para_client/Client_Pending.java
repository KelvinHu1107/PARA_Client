package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.newnergy.para_client.Chat.Client_Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_Pending extends AppCompatActivity {

    public ImageButton main,profile,post;
    ListAdapter_Pending adapter;
    ListView listView;
    TextView textView;
    int[] serviceId;
    Integer[] providerId, status;
    CharSequence[] profilePhoto, providerPhoto;
    CharSequence[] getTitle, createDate;
    private  String[] objectName ;
    ClientData[] dataList;
    private List<ClientPendingListViewModel> list;
    private ClientProfileViewModel profileList;
    private Double[] budget;
    Context context = this;
    Loading_Dialog myLoading;

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
                            Intent intent = new Intent(Client_Pending.this, Client_PopUp_Version.class);
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

    }

    public void getData(){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                ClientPendingDataConvert convert = new ClientPendingDataConvert();
                list = convert.convertJsonToArrayList(result);

                initList();
                textView.setText(""+list.size());

                for(int i =0; i<ValueMessager.listPendingId.size(); i++){
                    returnReadMessage(ValueMessager.listPendingId.get(i));
                }

                myLoading.CloseLoadingDialog();
            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/ClientGetPendingJobList/"+ValueMessager.email,"","GET");
    }

    public void initList(){

        dataList = new ClientData[list.size()];
        objectName = new String[list.size()];
        serviceId = new int[list.size()];
        providerId = new Integer[list.size()];
        profilePhoto = new CharSequence[list.size()];
        getTitle = new CharSequence[list.size()];
        providerPhoto = new CharSequence[list.size()];
        createDate = new CharSequence[list.size()];
        status = new Integer[list.size()];
        budget = new Double[list.size()];


        for(int i=0; i< list.size() ; i++) {
            objectName[i] = list.get(i).getTitle();
            serviceId[i] = list.get(i).getServiceId();
            providerId[i] = list.get(i).getProviderId();
            providerPhoto[i] = list.get(i).getProviderProfilePhoto();
            getTitle[i] = list.get(i).getTitle();
            status[i] = list.get(i).getStatus();
            createDate[i] = list.get(i).getCreateDate();
            budget[i] = list.get(i).getBudget();
        }

        for (int i = 0; i < list.size(); i++)
            dataList[i] = new ClientData();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (int i = 0; i < list.size(); i++) {

            dataList[i].profilePhoto = providerPhoto[i];
            dataList[i].getTitle = getTitle[i];
            dataList[i].providerId = providerId[i];
            dataList[i].serviceId = serviceId[i];
            dataList[i].createDate = createDate[i];
            dataList[i].status = status[i];
            dataList[i].budget = budget[i];

            try {
                dataList[i].date = format.parse(createDate[i].toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ClientData bufferList = new ClientData();

        for (int i = 0; i < list.size()-1; i++) {
            for (int j = 0; j < list.size()-1; j++) {
                if (dataList[j].date.getTime() < dataList[j + 1].date.getTime()) {
                    bufferList = dataList[j];
                    dataList[j] = dataList[j + 1];
                    dataList[j + 1] = bufferList;
                }
            }
        }

        //swap
        for (int i = 0; i < list.size(); i++) {

            createDate[i] = dataList[i].createDate;
            serviceId[i] = dataList[i].serviceId;
            providerId[i] = dataList[i].providerId;
            providerPhoto[i] = dataList[i].profilePhoto;
            getTitle[i] = dataList[i].getTitle;
            status[i] = dataList[i].status;
            budget[i] = dataList[i].budget;
        }
        //swap
        adapter = new ListAdapter_Pending(this, objectName, createDate, serviceId, providerId, providerPhoto, getTitle, status, budget);
        listView.setAdapter(adapter);

    }

    public void btnFunction(){
        main = (ImageButton) findViewById(R.id.imageButton_pending_to_main);
        ValueMessager.footerMessage = (ImageButton) findViewById(R.id.imageButton_pending_to_message);
        profile = (ImageButton) findViewById(R.id.imageButton_pending_to_profile);
        post = (ImageButton) findViewById(R.id.imageButton_post_pending);
        textView = (TextView) findViewById(R.id.textView_pending_number);
        ValueMessager.textMessage = (TextView) findViewById(R.id.textView_footerMessage);

        messageNotification();

        main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ValueMessager.is_pending = false;
                ValueMessager.notificationPending = 0;
                ValueMessager.listPendingId.clear();
                Intent nextPage_main = new Intent(Client_Pending.this, Client_Incoming_Services.class);
                startActivity(nextPage_main);
            }
        });

        post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ValueMessager.is_pending = false;
                ValueMessager.notificationPending = 0;
                ValueMessager.listPendingId.clear();
                Intent intent = new Intent(Client_Pending.this, Client_PlaceOrder.class);
                startActivity(intent);
            }
        });

        ValueMessager.footerMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(ValueMessager.chat_db_Global!=null){
                    ValueMessager.chat_db_Global.setAlreadyRead(ValueMessager.email.toString());
                }

                ValueMessager.is_pending = false;
                ValueMessager.notificationPending = 0;
                ValueMessager.listPendingId.clear();
                Intent nextPage_Pending = new Intent(Client_Pending.this, Client_Message.class);
                startActivity(nextPage_Pending);
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ValueMessager.is_pending = false;
                ValueMessager.notificationPending = 0;
                ValueMessager.listPendingId.clear();
                Intent nextPage_profile = new Intent(Client_Pending.this, Client_Setting.class);
                startActivity(nextPage_profile);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pending);

        ValueMessager.is_pending = true;
        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        listView = (ListView) findViewById(R.id.listView_pending);
        btnFunction();
        getData();
    }
}
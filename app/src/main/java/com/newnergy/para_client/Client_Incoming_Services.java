package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.newnergy.para_client.Chat.Client_Message;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_Incoming_Services extends AppCompatActivity
        implements OnRefreshListener {

    private int ListSize = 40;
    Adapter adapter;
    RefreshListView lv;
    private List<ProviderProfileViewModel> list;
    private String[] objectName;
    ClientData[] dataList;
    CharSequence[] name;
    CharSequence[] createDate;
    int[] ProviderId;
    int[] id;
    CharSequence[] clientDueDate, type;
    CharSequence[] street, suburb, city;
    CharSequence[] profilePhoto;
    CharSequence[] getTitle;
    CharSequence[] userName;
    CharSequence[] companyPhone;
    Double[] rating;
    Context context = this;
    Loading_Dialog myLoading;

    public ImageButton  profile, sortByDate, sortByType, sortByLocation, post;
    private Handler mHandler = new Handler(){

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initList();
                    break;
                case 2:
                    UpdateAdapter();
                    break;
                case 3:
                    //messageNotification();
                    break;
            }
        };
    };

    public String readData(String openFileName) {
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
                                Intent intent = new Intent(Client_Incoming_Services.this, Client_PopUp_Version.class);
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

    public void getData() {
        DataTransmitController c = new DataTransmitController() {
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                list = ProviderProfileDataConvert.convertJsonToArrayList(result);
                if(list.size() < ListSize){
                    ListSize = list.size();
                }
                initList();
                btnFunction();//button activity
            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ProviderProfile/GetAllProviders", "", "GET");
    }

    public void btnFunction() {
        ValueMessager.footerPending = (ImageButton) findViewById(R.id.imageButton_pending_main);
        ValueMessager.textMessage = (TextView) findViewById(R.id.textView_footerMessage);
        ValueMessager.textPending = (TextView) findViewById(R.id.textView_footerPending);
        profile = (ImageButton) findViewById(R.id.imageButton_profile_main);
        sortByDate = (ImageButton) findViewById(R.id.imageButton_sort_by_date);
        sortByType = (ImageButton) findViewById(R.id.imageButton_sort_by_type);
        post = (ImageButton) findViewById(R.id.imageButton_Post_Main);
        //sortByLocation = (ImageButton) findViewById(R.id.imageButton_sort_by_location);
        ValueMessager.footerMessage = (ImageButton) findViewById(R.id.imageButton_message_main);




        messageNotification();

        sortByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByType.setImageResource(R.drawable.p_type01);
                sortByDate.setImageResource(R.drawable.c_rate02);

                sortByRateFunction();

            }
        });

        sortByType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByType.setImageResource(R.drawable.c_type);
                sortByDate.setImageResource(R.drawable.c_rate01);

                setSortByType();
            }
        });

        ValueMessager.footerPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Pending = new Intent(Client_Incoming_Services.this, Client_Pending.class);
                startActivity(nextPage_Pending);

            }
        });

        ValueMessager.footerMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(Client_Incoming_Services.this, Client_Share.class);
//                startActivity(intent);
                if(ValueMessager.chat_db_Global!=null){
                    ValueMessager.chat_db_Global.setAlreadyRead(ValueMessager.email.toString());
                }

                Intent nextPage_Pending = new Intent(Client_Incoming_Services.this, Client_Message.class);
                startActivity(nextPage_Pending);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Profile = new Intent(Client_Incoming_Services.this, Client_Setting.class);
                startActivity(nextPage_Profile);

            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_Incoming_Services.this, Client_PlaceOrder.class);
                startActivity(intent);
            }
        });

        myLoading.CloseLoadingDialog();
    }
    // assigning buttons

    public void LoadList()
    {
        //
    }

    public void initList() {


        for (int i = 0; i < ListSize + 1; i++) {
            dataList = new ClientData[i];
            objectName = new String[i];
            name = new CharSequence[i];
            createDate = new CharSequence[i];
            ProviderId = new int[i];
            id = new int[i];
            clientDueDate = new CharSequence[i];
            suburb = new CharSequence[i];
            street = new CharSequence[i];
            city = new CharSequence[i];
            profilePhoto = new CharSequence[i];
            getTitle = new CharSequence[i];
            userName = new CharSequence[i];
            companyPhone = new CharSequence[i];
            type = new CharSequence[i];
            rating = new Double[i];
        }

        for (int i = 0; i < ListSize; i++) {
            objectName[i] = list.get(i).getFirstName() + " " + list.get(i).getLastName();
            name[i] = list.get(i).getFirstName() + " " + list.get(i).getLastName();
            id[i] = list.get(i).getId();
            suburb[i] = list.get(i).getProviderAddressSuburb();
            street[i] = list.get(i).getProviderAddressStreet();
            city[i] = list.get(i).getProviderAddressCity();
            profilePhoto[i] = list.get(i).getProfilePicture();
            userName[i] = list.get(i).getUsername();
            companyPhone[i] = list.get(i).getCompanyPhone();
            rating[i] = list.get(i).getRating();
            type[i] = list.get(i).getProviderType();

        }

        for (int i = 0; i < ListSize; i++)
            dataList[i] = new ClientData();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (int i = 0; i < ListSize; i++) {
            dataList[i].objectName = objectName[i];
            dataList[i].name = name[i];
            dataList[i].id = id[i];
            dataList[i].suburb = suburb[i];
            dataList[i].street = street[i];
            dataList[i].city = city[i];
            dataList[i].profilePhoto = profilePhoto[i];
            dataList[i].getTitle = getTitle[i];
            dataList[i].email = userName[i];
            dataList[i].companyPhone = companyPhone[i];
            dataList[i].rating = rating[i];
            dataList[i].type = type[i];
        }
        listReflash();
    }

    public void swapLists() {
        //swap
        for (int i = 0; i < ListSize; i++) {
            objectName[i] = dataList[i].objectName.toString();
            name[i] = dataList[i].name;
            createDate[i] = dataList[i].createDate;
            clientDueDate[i] = dataList[i].clientDueDate;
            id[i] = dataList[i].id;
            suburb[i] = dataList[i].suburb;
            street[i] = dataList[i].street;
            city[i] = dataList[i].city;
            profilePhoto[i] = dataList[i].profilePhoto;
            getTitle[i] = dataList[i].getTitle;
            userName[i] = dataList[i].email;
            companyPhone[i] = dataList[i].companyPhone;
            rating[i] = dataList[i].rating;
            type[i] = dataList[i].type;
        }
        //swap
    }

    public void sortByRateFunction() {

        ClientData bufferList = new ClientData();

        //sort
        for (int i = 0; i < ListSize - 1; i++) {
            for (int j = 0; j < ListSize - 1; j++) {
                if (dataList[j].rating < dataList[j + 1].rating) {
                    bufferList = dataList[j];
                    dataList[j] = dataList[j + 1];
                    dataList[j + 1] = bufferList;
                }
            }
        }
        //sort

        swapLists();
        listReflash();
    }

    public void setSortByType(){

        ClientData bufferList = new ClientData();
        String sortString = new String();

        switch (ValueMessager.sortCounter%6){
            case 0:
                sortString = "Plumber";
                break;
            case 1:
                sortString = "Electrician";
                break;
            case 2:
                sortString = "Builder";
                break;
            case 3:
                sortString = "Painter";
                break;
            case 4:
                sortString = "Cleaner";
                break;
            case 5:
                sortString = "Others";
                break;
        }

        for (int i = 0; i < ListSize - 1; i++) {
            for (int j = 0; j < ListSize - 1; j++) {
                if (!dataList[j].type.equals(sortString) && dataList[j+1].type.equals(sortString))  {
                    bufferList = dataList[j];
                    dataList[j] = dataList[j + 1];
                    dataList[j + 1] = bufferList;
                }
            }
        }

        swapLists();
        listReflash();

        ValueMessager.sortCounter += 1;

    }

    public void listReflash() {
        adapter = new Adapter(this, objectName, name, createDate, clientDueDate, id, street, suburb, city, profilePhoto, getTitle, userName, companyPhone, rating, type);
        lv.setAdapter(adapter);
        lv.setOnRefreshListener(this);
    }

    public void messageNotification()
    {
        if(ValueMessager.notificationMessage>0) {
            ValueMessager.textMessage.setVisibility(View.VISIBLE);
            if(ValueMessager.notificationMessage > 0 && ValueMessager.notificationMessage < 10){
                ValueMessager.textMessage.setText(" "+ValueMessager.notificationMessage+" ");
            }else if(ValueMessager.notificationMessage > 9){
                if(isNumeric(String.valueOf(ValueMessager.notificationPending))) {
                    ValueMessager.textPending.setText(String.valueOf(ValueMessager.notificationPending));
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
                ValueMessager.textPending.setText(String.valueOf(ValueMessager.notificationPending));
            } else if (ValueMessager.notificationPending > 99) {
                ValueMessager.textPending.setText("99+");
            }
        }else{
            ValueMessager.textPending.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_incoming_services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myLoading = new Loading_Dialog();
        myLoading.getContext(this);

        ValueMessager.sortCounter = 0;
        ValueMessager.imageCounter = 0;
        lv = (RefreshListView) findViewById(R.id.listView2);

        RefreshTokenController c = new RefreshTokenController(){
            @Override
            public void response(boolean result ) {

                getData();
                if(result){
                    System.out.println("ddddddddddddd fuck u gaoxin");
                }else {
                    System.out.println("failllllllllllllllll");
                }
            }
        };

        c.refreshToken(ValueMessager.email.toString(),"1a38a27a10134ac3a28cc15b90f9b24d");

        //getData();
        scrollMyListViewToBottom();
    }

    private void UpdateAdapter() {


        DataTransmitController c = new DataTransmitController() {
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                list = ProviderProfileDataConvert.convertJsonToArrayList(result);
                initList();
                btnFunction();//button activity
                myLoading.CloseLoadingDialog();
            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ProviderProfile/GetAllProviders", "", "GET");
    }

    @Override
    public void onDownPullRefresh() {
        System.out.println("test1");

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                if(ListSize<list.size()) {

                    Message message = new Message();
                    message.what = 2;
                    mHandler.sendMessage(message);

                }

                //sortByDateFunction();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                lv.hideHeaderView();
                lv.hideFooterView();
            }
        }.execute(new Void[]{});

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

    @Override
    public void onLoadingMore() {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                Message message = new Message();
                message.what = 1;
                ListSize += 40;

                if(ListSize<list.size()) {
                    mHandler.sendMessage(message);
                }
                else
                {
                    ListSize=list.size();
                    mHandler.sendMessage(message);
                }

                //list.add(11,ProviderProfileDataConvert);


                System.out.println("test2");
                scrollMyListViewToBottom();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                lv.hideHeaderView();
                lv.hideFooterView();
            }
        }.execute(new Void[] {});
    }

    private void scrollMyListViewToBottom() {
        lv.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                lv.setSelection(lv.getCount() - 1);
            }
        });
    }
}
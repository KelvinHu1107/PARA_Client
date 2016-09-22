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
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Client_Incoming_Services extends AppCompatActivity
        implements OnRefreshListener {

    private int ListSize = 10;
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

    public ImageButton pending, message, profile, imageButtonSearch, sortByDate, sortByType, sortByLocation, post;
    public Button searchButton;
    private Handler mHandler = new Handler(){

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                   initList();
                    break;
                case 2:
                    UpdateAdapter();
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

    public void getData() {
        DataTransmitController c = new DataTransmitController() {
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                list = ProviderProfileDataConvert.convertJsonToArrayList(result);
                initList();
                btnFunction();//button activity
            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ProviderProfile/GetAllProviders", "", "GET");
    }

    public void btnFunction() {
        pending = (ImageButton) findViewById(R.id.imageButton_pending_main);
        message = (ImageButton) findViewById(R.id.imageButton_message_main);
        profile = (ImageButton) findViewById(R.id.imageButton_profile_main);
        imageButtonSearch = (ImageButton) findViewById(R.id.imageButtonToSearchPage);
        sortByDate = (ImageButton) findViewById(R.id.imageButton_sort_by_date);
        sortByType = (ImageButton) findViewById(R.id.imageButton_sort_by_type);
        post = (ImageButton) findViewById(R.id.imageButton_Post_Main);
        sortByLocation = (ImageButton) findViewById(R.id.imageButton_sort_by_location);
        searchButton = (Button) findViewById(R.id.buttonToSearchPage);





        sortByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByType.setImageResource(R.drawable.type_1_xh_720x1280);
                sortByDate.setImageResource(R.drawable.button_category_date_1_xh_720x1280);
                sortByLocation.setImageResource(R.drawable.location_0_xh_720x1280);
                //lv.removeViewsInLayout(0,1);
                sortByDateFunction();

            }
        });

        sortByType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByType.setImageResource(R.drawable.type_0_xh_720x1280);
                sortByDate.setImageResource(R.drawable.button_category_date_0_xh_720x1280);
                sortByLocation.setImageResource(R.drawable.location_0_xh_720x1280);
                //sortByTypeFunction();
            }
        });

        sortByLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByType.setImageResource(R.drawable.type_0_xh_720x1280);
                sortByDate.setImageResource(R.drawable.button_category_date_0_xh_720x1280);
                sortByLocation.setImageResource(R.drawable.location_1_xh_720x1280);
                //sortByLocationFunction();
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Pending = new Intent(Client_Incoming_Services.this, Client_Pending.class);
                startActivity(nextPage_Pending);

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Search = new Intent(Client_Incoming_Services.this, Client_search.class);
                startActivity(nextPage_Search);
            }
        });

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Search = new Intent(Client_Incoming_Services.this, Client_search.class);
                startActivity(nextPage_Search);
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
            //createDate[i] = list.get(i).get
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

//        for (int i = 0; i < list.size(); i++)
//            dataList[i] = new ClientData();

        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

//        for (int i = 0; i < list.size(); i++) {
//            dataList[i].name = name[i];
//            dataList[i].createDate = createDate[i];
//            dataList[i].clientDueDate = clientDueDate[i];
//            dataList[i].id = id[i];
//            dataList[i].suburb = suburb[i];
//            dataList[i].street = street[i];
//            dataList[i].city = city[i];
//            dataList[i].profilePhoto = profilePhoto[i];
//            dataList[i].getTitle = getTitle[i];
//            dataList[i].email = userName[i];
//            dataList[i].companyPhone = companyPhone[i];
//            dataList[i].rating = rating[i];
//            dataList[i].type = type[i];

//            try {
//                dataList[i].date = format.parse(createDate[i].toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
        listReflash();
    }

    public void swapLists() {
        //swap
        for (int i = 0; i < ListSize; i++) {
            name[i] = dataList[i].name;
            createDate[i] = dataList[i].createDate;
            //clientId[i] = dataList[i].clientId;
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

    public void sortByDateFunction() {

        ClientData bufferList = new ClientData();

        //sort
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1; j++) {
                if (dataList[j].date.getTime() < dataList[j + 1].date.getTime()) {
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

    /*
        public void sortByLocationFunction() {

            ClientData bufferList = new ClientData();

            //sort
            for (int i = 0; i <= 5; i++) {
                for (int j = 0; j <= 5; j++) {
                    if (dataList[j].distance > dataList[j + 1].distance) {
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
    */


    public void listReflash() {
        adapter = new Adapter(this, objectName, name, createDate, clientDueDate, id, street, suburb, city, profilePhoto, getTitle, userName, companyPhone, rating, type);
        lv.setAdapter(adapter);
        lv.setOnRefreshListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_incoming_services);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myLoading = new Loading_Dialog();
        myLoading.getContext(this);

        ValueMessager.imageCounter = 0;
        lv = (RefreshListView) findViewById(R.id.listView2);

        getData();
        scrollMyListViewToBottom();

    }


    //////////////////
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


    ////////////////////////////////////

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

    @Override
    public void onLoadingMore() {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                Message message = new Message();
                message.what = 1;
                ListSize+=5;

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


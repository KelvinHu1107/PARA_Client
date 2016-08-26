package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class Client_History extends AppCompatActivity {


    ListAdapter_History adapter;
    ListView listView;
    TextView back;
    int[] serviceId;
    Integer[] providerId, status;
    CharSequence[] profilePhoto, providerPhoto;
    CharSequence[] getTitle, createDate;
    private  String[] objectName ;
    ClientData[] dataList;
    private List<ClientPendingListViewModel> list;
    private ClientProfileViewModel profileList;
    private ImageView profilePicture;
    private RoundImage roundImage;
    private TextView profileName;
    private Double[] budget;

    public void getData(){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                ClientPendingDataConvert convert = new ClientPendingDataConvert();
                list = convert.convertJsonToArrayList(result);

                initList();


            }
        };
        c.execute("http://para.co.nz/api/ClientJobService/ClientGetHistory/"+ValueMessager.email,"","GET");
    }

    //init listview
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



        adapter = new ListAdapter_History(this, objectName, createDate, serviceId, providerId, providerPhoto, getTitle, status, budget);
        listView.setAdapter(adapter);

    }
    //init listView

    // assigning buttons
    public void btnFunction(){

        back = (TextView) findViewById(R.id.textView_cancel_history);
        listView = (ListView) findViewById(R.id.listView_history);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (ValueMessager.historyLastPage) {

                    case 1:
                        Intent nextPage_IncomingServices = new Intent(Client_History.this, Client_Incoming_Services.class);
                        startActivity(nextPage_IncomingServices);
                        break;
                    case 2:
                        Intent nextPage_Message = new Intent(Client_History.this, Client_Message.class);
                        startActivity(nextPage_Message);
                        break;
                    case 3:
                        Intent nextPage_Pending = new Intent(Client_History.this, Client_Pending.class);
                        startActivity(nextPage_Pending);
                        break;
                    case 4:
                        Intent nextPage_Profile = new Intent(Client_History.this, Client_Profile.class);
                        startActivity(nextPage_Profile);
                        break;
                }

            }
        });
    }
    // assigning buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_history);

        listView = (ListView) findViewById(R.id.listView_history);
        btnFunction();
        getData();
        //getProfileData();
    }
}

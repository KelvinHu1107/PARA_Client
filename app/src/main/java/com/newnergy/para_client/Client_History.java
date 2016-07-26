package com.newnergy.para_client;

import android.content.Intent;
import android.graphics.Bitmap;
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
    CharSequence[] startDate;
    int[] clientId;
    int[]id;
    CharSequence[] clientDueDate;
    CharSequence[] jobSurburb;
    CharSequence[] profilePhoto;
    CharSequence[] getTitle;
    private  String[] objectName ;
    ClientData[] dataList;
    CharSequence[] name ;
    private List<PendingJobViewModel> list;
    private ClientProfileViewModel profileList;
    private ImageView profilePicture;
    private RoundImage roundImage;
    private TextView profileName;

    public void getProfileData(){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                //profileList = ClientDataConvert.convertJsonToModel(result);
                //profilePicture = (ImageView) findViewById(R.id.imageView_sideMenu_pic);
                //profileName = (TextView) findViewById(R.id.textView_slidingMenu_name);
                //profileName.setText(profileList.getFirstName()+" "+profileList.getLastName());
                getProfileImageData();
            }
        };
        c.execute("http://para.co.nz/api/ClientProfile/getClientDetail/gaoxin","","GET");

    }

    public void getProfileImageData() {
        GetImageController controller = new GetImageController() {
            @Override
            public void onResponse(Bitmap bitmap) {
                super.onResponse(bitmap);
                if (bitmap == null) {
                }
                roundImage = new RoundImage(bitmap);
                profilePicture.setImageDrawable(roundImage);
            }
        };
        controller.execute("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+ profileList.getProfilePicture(), "","POST");
    }


    public void getData(){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                list = PendingJobDataConvert.convertJsonToArrayList(result);

                initList();

            }
        };
        c.execute("http://para.co.nz/api/PendingJob/GetAllPendingJobs/gaoxin","","GET");
    }

    //init listview
    public void initList(){

        dataList = new ClientData[list.size()];
        objectName = new String[list.size()];
        name = new CharSequence[list.size()];
        startDate = new CharSequence[list.size()];
        clientId = new int[list.size()];
        id = new int[list.size()];
        clientDueDate = new CharSequence[list.size()];
        jobSurburb = new CharSequence[list.size()];
        profilePhoto = new CharSequence[list.size()];
        getTitle = new CharSequence[list.size()];

        for(int i=0; i< list.size() ; i++) {
            objectName[i] = list.get(i).getClientName();
            name[i] = list.get(i).getClientName();
            clientId[i] = list.get(i).getClientId();
            clientDueDate[i] = list.get(i).getDueDate();
            startDate[i] = list.get(i).getStartDate();
            id[i] = list.get(i).getId();
            profilePhoto[i] = list.get(i).getProfilePhoto();
            getTitle[i] = list.get(i).getTitle();

        }

        for (int i = 0; i < list.size(); i++)
            dataList[i] = new ClientData();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        for (int i = 0; i < list.size(); i++) {
            dataList[i].name = name[i];
            dataList[i].createDate = startDate[i];
            dataList[i].clientId = clientId[i];
            dataList[i].clientDueDate = clientDueDate[i];
            dataList[i].id = id[i];
            dataList[i].jobSurburb = jobSurburb[i];
            dataList[i].profilePhoto = profilePhoto[i];
            dataList[i].getTitle = getTitle[i];
            try {
                dataList[i].date = format.parse(startDate[i].toString());
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
            name[i] = dataList[i].name;
            startDate[i] = dataList[i].createDate;
            clientId[i] = dataList[i].clientId;
            clientDueDate[i] = dataList[i].clientDueDate;
            id[i] = dataList[i].id;
            jobSurburb[i] = dataList[i].jobSurburb;
            profilePhoto[i] = dataList[i].profilePhoto;
            getTitle[i] = dataList[i].getTitle;

        }
        //swap



        adapter = new ListAdapter_History(this, objectName, name, startDate, clientId, clientDueDate, id, jobSurburb, profilePhoto, getTitle);
        listView.setAdapter(adapter);

    }
    //init listView

    // assigning buttons
    public void btnFunction(){

        back = (TextView) findViewById(R.id.textView_cancel_history);

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

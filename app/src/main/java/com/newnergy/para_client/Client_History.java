package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Client_History extends AppCompatActivity {

    ListAdapter_History adapter;
    ListView listView;
    TextView title;
    ImageView back, save;
    int[] serviceId;
    Integer[] providerId, status;
    CharSequence[] profilePhoto, providerPhoto;
    CharSequence[] getTitle, createDate;
    private  String[] objectName, address ;
    ClientData[] dataList;
    private List<ClientPendingListViewModel> list;
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
                            Intent intent = new Intent(Client_History.this, Client_PopUp_Version.class);
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

    public void getData(){
        DataTransmitController c = new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                ClientPendingDataConvert convert = new ClientPendingDataConvert();
                list = convert.convertJsonToArrayList(result);
                btnFunction();
                initList();

                myLoading.CloseLoadingDialog();
            }
        };
        myLoading.ShowLoadingDialog();
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
        address = new String[list.size()];
        budget = new Double[list.size()];
        createDate = new CharSequence[list.size()];


        for(int i=0; i< list.size() ; i++) {
            objectName[i] = list.get(i).getTitle();
            serviceId[i] = list.get(i).getServiceId();
            providerId[i] = list.get(i).getProviderId();
            providerPhoto[i] = list.get(i).getProviderProfilePhoto();
            getTitle[i] = list.get(i).getTitle();
            address[i] = list.get(i).getAddress();
            createDate[i] = list.get(i).getCompleteDate();
            budget[i] = list.get(i).getBudget();

        }

        adapter = new ListAdapter_History(this, objectName, createDate, serviceId, providerId, providerPhoto, getTitle, address, budget);
        listView.setAdapter(adapter);




    }
    //init listView

    // assigning buttons
    public void btnFunction(){

        save = (ImageView) findViewById(R.id.imageView_ok);
        back = (ImageView) findViewById(R.id.imageView_back);
        title = (TextView) findViewById(R.id.tree_field_title);
        listView = (ListView) findViewById(R.id.listView_history);

        title.setText("History");
        save.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent nextPage_IncomingServices = new Intent(Client_History.this, Client_Setting.class);
                        startActivity(nextPage_IncomingServices);
            }
        });
    }
    // assigning buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_history);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        listView = (ListView) findViewById(R.id.listView_history);

        getData();
    }
}
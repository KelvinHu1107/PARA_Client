package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Client_CommentPage extends AppCompatActivity {

    ListAdapter_Comment adapter;
    private TextView back, title, number;
    ListView listView;
    List<RatingListViewModel> list;
    CharSequence[] firstName, lastName, createDate, comment;
    String[] objectName;
    Double[] rating;


    public void getData(){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                RatingListDataConvert convert = new RatingListDataConvert();
                list = convert.convertJsonToArrayList(result);

                btnFunction();
                initList();
                number.setText(" "+list.size());
            }
        };
        c.execute("http://para.co.nz/api/ProviderRating/GetRatingListByUsername/"+ValueMessengerTaskInfo.providerUserName,"","GET");
    }

    public void btnFunction(){
        back = (TextView) findViewById(R.id.textView_cancel_rating);
        title = (TextView) findViewById(R.id.profile_rating_title);
        number = (TextView) findViewById(R.id.textView_commentPage_number);
        listView = (ListView) findViewById(R.id.listView_commentPage);

        back.setText("Back");
        title.setText("Comments");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (ValueMessager.commentLastPage) {
                    case 0 :
                        Intent nextPage_IncomingList = new Intent(Client_CommentPage.this, Client_Incoming_Services.class);
                        startActivity(nextPage_IncomingList);
                        break;

                    case 1 :
                        Intent intent = new Intent(Client_CommentPage.this, Client_Pending.class);
                        startActivity(intent);
                        break;

                }
            }
        });

    }

    public void initList(){

        objectName = new String[list.size()];
        firstName = new CharSequence[list.size()];
        createDate = new CharSequence[list.size()];
        lastName = new CharSequence[list.size()];
        comment = new CharSequence[list.size()];
        rating = new Double[list.size()];

        for(int i=0; i< list.size() ; i++) {
            //objectName[i] = list.get(i).getClientFirstname().toString();
            firstName[i] = list.get(i).getClientFirstname();
            lastName[i] = list.get(i).getClientLastname();
            comment[i] = list.get(i).getComment();
            rating[i] = list.get(i).getRating();
            createDate[i] = list.get(i).getCreateDate();

        }

        adapter = new ListAdapter_Comment(this, objectName, firstName, lastName, comment, createDate, rating);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_comment_page);

        getData();

    }
}

package com.newnergy.para_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Client_search extends AppCompatActivity {

    public ImageButton cancelbtn;

    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;

    public void btnFunction(){
        cancelbtn = (ImageButton) findViewById(R.id.imageButton_cancel);

        cancelbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nextPage_Main = new Intent(Client_search.this, Client_Incoming_Services.class);
                startActivity(nextPage_Main);
            }
        });

    }

    public void initList(){
        items = new String[]{"Pretty Girl","John Key","James Bond","Lee Sin","Jack Black","Justin Timberland","Kobe Bryan"};
        listItems = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this, R.layout.search_items, R.id.txtitem,listItems);
        listView.setAdapter(adapter);
    }

    public void searchItem(String textToSearch){
        for(String item:items){
            if(!item.contains(textToSearch)){
                listItems.remove(item);

            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_search_page);

        listView = (ListView) findViewById(R.id.listView_Search);
        editText = (EditText) findViewById(R.id.txtsearch);
        btnFunction();
        initList();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals("")){
                    //reset listview
                    initList();
                }
                else{
                    //perform search
                    initList();
                    searchItem(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

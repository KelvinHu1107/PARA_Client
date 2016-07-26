package com.newnergy.para_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Client_HistoryFurther extends AppCompatActivity {

    private TextView cancel;


    public void btnFunction(){


        cancel = (TextView) findViewById(R.id.textView_cancel_historyFurther);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_History = new Intent(Client_HistoryFurther.this, Client_History.class);
                startActivity(nextPage_History);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_history_further);

        btnFunction();
    }
}

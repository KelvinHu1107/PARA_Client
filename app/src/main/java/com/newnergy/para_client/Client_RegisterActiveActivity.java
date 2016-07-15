package com.newnergy.para_client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Client_RegisterActiveActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvToolbarNext;
    private TextView tvToolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_register_active);
        initComponent();
        setToolbarComponent();


    }

    public void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_register_active_template);
        tvToolbarNext = (TextView) findViewById(R.id.toolbar_register_active_next);
        tvToolbarBack = (TextView) findViewById(R.id.toolbar_register_active_title);
        tvToolbarNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    private void setToolbarComponent() {
        setSupportActionBar(toolbar);
    }
}

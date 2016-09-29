package com.newnergy.para_client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Client_Calender extends Activity {

    DatePicker datePicker;
    LinearLayout confirm, cancel;
    int day, month, year;
    TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_calender);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width,height);

        datePicker = (DatePicker) findViewById(R.id.datePicker_calender);
        confirm = (LinearLayout) findViewById(R.id.linearLayout_calender_popUp_comfirm);
        cancel = (LinearLayout) findViewById(R.id.linearLayout_calender_popUp_cancel);
        warning = (TextView) findViewById(R.id.textView_warning);

        warning.setVisibility(View.INVISIBLE);

        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth();
        year = datePicker.getYear();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(year > datePicker.getYear() || ((year == datePicker.getYear()) && (month > datePicker.getMonth())) || ((year == datePicker.getYear()) &&
                        (month == datePicker.getMonth()) && (day > datePicker.getDayOfMonth() ))){
                    warning.setVisibility(View.VISIBLE);
                    return;
                }

                ValueMessager.selectedDate = String.valueOf(datePicker.getYear())+"-"+String.valueOf(datePicker.getMonth())+"-"+String.valueOf(datePicker.getDayOfMonth());

                ValueMessager.selectedDay = String.valueOf(datePicker.getDayOfMonth());
                ValueMessager.selectedMonth = String.valueOf(datePicker.getMonth());
                ValueMessager.selectedYear = String.valueOf(datePicker.getYear());

                Intent intent = new Intent(Client_Calender.this, Client_PlaceOrder.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Client_Calender.this, Client_PlaceOrder.class);
                startActivity(intent);

            }
        });


    }
}

package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_AddFund extends AppCompatActivity {

    CircleImageView circleImageView;
    EditText editText;
    TextView title, name, previousNum, warning;
    ClientPendingDetailViewModel jsm;
    ImageView back, save;
    Context context = this;
    Loading_Dialog myLoading;

    public boolean isBudget (String number){

        Pattern p = Pattern.compile("\\d*\\.?\\d+");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public void getData(){
        DataTransmitController c=new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                ClientPendingDetailDataConvert clientPendingDetailDataConvert = new ClientPendingDetailDataConvert();
                jsm = clientPendingDetailDataConvert.convertJsonToModel(result);

                previousNum.setText(jsm.getExtraDeposit().toString());

                btnFunctions();
                myLoading.CloseLoadingDialog();
            }
        };

        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");

    }

    public void btnFunctions(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_AddFund.this, Client_Confirm.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            final ClientUpdateServiceViewModel model=new ClientUpdateServiceViewModel();
            ClientUpdateServiceDataConvert convert = new ClientUpdateServiceDataConvert();

            @Override
            public void onClick(View v) {

                if(isBudget(editText.getText().toString())){

                    if((Double.parseDouble(editText.getText().toString())+jsm.getDeposit()) > (jsm.getBudget()-(jsm.getBudget()/10))){

                        warning.setText("Higher than budget");
                        warning.setHintTextColor(Color.parseColor("#f3736f"));
                        return;

                    }
                    else if(Double.parseDouble(editText.getText().toString()) == 0){

                        warning.setText("Can not be zero");
                        warning.setHintTextColor(Color.parseColor("#f3736f"));
                        return;
                                            }else {

                        DataGetIntController c = new DataGetIntController() {
                            @Override
                            public void onResponse(Integer result) {
                                super.onResponse(result);

                                Intent intent = new Intent(Client_AddFund.this, Client_Confirm.class);
                                startActivity(intent);
                            }
                        };

                        model.setServiceId(ValueMessengerTaskInfo.id);
                        model.setIsSecure(true);
                        model.setDeposit(Double.parseDouble(editText.getText().toString()) + jsm.getExtraDeposit());// Modified, set "Extra Deposit"!!! by getting extra deposit.
                        String data = convert.ModelToJson(model);

                        c.execute("http://para.co.nz/api/ClientJobService/updatejobservice", data, "PUT");

                    }
                }
                else {
                    editText.setHint("Invalid value");
                    editText.setHintTextColor(Color.parseColor("#f3736f"));
                    return;
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_add_fund);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        circleImageView = (CircleImageView) findViewById(R.id.imageView_addFund_pic);
        name = (TextView) findViewById(R.id.textView_addFund_name);
        back = (ImageView) findViewById(R.id.imageView_back);
        title = (TextView) findViewById(R.id.tree_field_title);
        save = (ImageView) findViewById(R.id.imageView_ok);
        warning = (TextView) findViewById(R.id.textView_addFund_warning);
        previousNum = (TextView) findViewById(R.id.textView_addFund_previous);
        editText = (EditText) findViewById(R.id.editText_addFund);

        circleImageView.setImageBitmap(ValueMessager.userProfileBitmap);
        name.setText(ValueMessager.userFirstName+", "+ValueMessager.userLastName);
        title.setText("Add fund");

        getData();
    }
}
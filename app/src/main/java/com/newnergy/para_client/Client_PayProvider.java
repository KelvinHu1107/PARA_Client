package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class Client_PayProvider extends AppCompatActivity {

    private CircleImageView circleImageView;
    private TextView name, budget, deposit, total, title, confirm;
    private LinearLayout creditCard, others;
    private ImageView creditCardPic, othersPic, cancel, save;
    private boolean isPayByCredit = true;
    private Spinner spinner;
    ClientPendingDetailViewModel jsm;
    Context context = this;
    Loading_Dialog myLoading;

    public void getData(){
        DataTransmitController c=new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                ClientPendingDetailDataConvert clientPendingDetailDataConvert = new ClientPendingDetailDataConvert();
                jsm = clientPendingDetailDataConvert.convertJsonToModel(result);

                cancel = (ImageView) findViewById(R.id.imageView_back);
                save = (ImageView) findViewById(R.id.imageView_ok);
                title = (TextView) findViewById(R.id.tree_field_title);
                circleImageView = (CircleImageView) findViewById(R.id.imageView_pic);
                name = (TextView) findViewById(R.id.textView_name);
                budget = (TextView) findViewById(R.id.textView_budget);
                deposit = (TextView) findViewById(R.id.textView_deposit);
                total = (TextView) findViewById(R.id.textView_total);
                confirm = (TextView) findViewById(R.id.textView_confirm);
                creditCard = (LinearLayout) findViewById(R.id.linearLayout_creditCard);
                others = (LinearLayout) findViewById(R.id.linearLayout_others);
                creditCardPic = (ImageView) findViewById(R.id.imageView_creditCard);
                othersPic = (ImageView) findViewById(R.id.imageView_others);
                spinner = (Spinner) findViewById(R.id.spinner_choose);

                save.setVisibility(View.INVISIBLE);
                title.setText("Payment method");

                circleImageView.setImageBitmap(ValueMessengerTaskInfo.providerProfilePhoto);
                name.setText(ValueMessengerTaskInfo.providerFirstName+" "+ValueMessengerTaskInfo.providerLastName);
                budget.setText("$"+String.valueOf(jsm.getBudget()));
                deposit.setText("$"+String.valueOf(jsm.getDeposit()));
                total.setText(String.valueOf(jsm.getBudget()-jsm.getDeposit()));
                ValueMessager.edit_budget = jsm.getBudget().toString();

                creditCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        creditCardPic.setImageResource(R.drawable.dot);
                        othersPic.setImageResource(R.drawable.circle);
//                        spinner.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//                                return true;
//                            }
//                        });
                        isPayByCredit = true;
                    }
                });

                others.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        creditCardPic.setImageResource(R.drawable.circle);
                        othersPic.setImageResource(R.drawable.dot);
//                        spinner.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//                                return false;
//                            }
//                        });
                        isPayByCredit = false;
                    }
                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Client_PayProvider.this, Client_Rating.class);
                        startActivity(intent);
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Client_PayProvider.this, Client_Confirm2.class);
                        startActivity(intent);
                    }
                });

                myLoading.CloseLoadingDialog();

            }
        };

        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_choose_method);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        getData();
    }
}

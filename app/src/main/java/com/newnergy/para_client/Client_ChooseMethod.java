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
import com.squareup.picasso.Picasso;

public class Client_ChooseMethod extends AppCompatActivity {

    private CircleImageView pic;
    private TextView name, budget, securedPayment, balance, creditCard, confirm, title;
    private LinearLayout creditContainer, other;
    private ImageView save, back;
    private Spinner spinner;
    private boolean isCreditCard = true;
    ClientPendingDetailViewModel jsm;
    Context context = this;
    Loading_Dialog myLoading;

    public void getData(){
        DataTransmitController c=new DataTransmitController() {
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                ClientPendingDetailDataConvert clientPendingDetailDataConvert = new ClientPendingDetailDataConvert();
                jsm = clientPendingDetailDataConvert.convertJsonToModel(result);

                if(jsm.getProviderProfilePhoto().equals("") || jsm.getProviderProfilePhoto().equals(null)){
                    pic.setImageResource(R.drawable.photo_client);
                }else {
                    Picasso.with(context).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + jsm.getProviderProfilePhoto()).into(pic);
                }
                name.setText(jsm.getProviderFirstname()+""+jsm.getProviderLastname());
                //budget.setText("$"+jsm.getBudget());
                //securedPayment.setText("-$"+jsm.getDeposit());
                balance.setText("$"+String.valueOf(jsm.getBudget()-jsm.getDeposit()));

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Client_ChooseMethod.this, Client_DealingBill.class);
                        startActivity(intent);
                    }
                });

//                creditContainer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        creditPic.setImageResource(R.drawable.dot);
//                        otherPic.setImageResource(R.drawable.circle);
//                        isCreditCard = true;
//                    }
//                });

//                other.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        creditPic.setImageResource(R.drawable.circle);
//                        otherPic.setImageResource(R.drawable.dot);
//                        isCreditCard = false;
//                    }
//                });

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final ClientUpdateServiceViewModel model=new ClientUpdateServiceViewModel();
                        ClientUpdateServiceDataConvert convert = new ClientUpdateServiceDataConvert();

                        DataGetIntController c = new DataGetIntController(){
                            @Override
                            public void onResponse(Integer result) {
                                super.onResponse(result);

                                DataSendController status = new DataSendController(){
                                    @Override
                                    public void onResponse(Boolean result) {
                                        super.onResponse(result);
                                        myLoading.CloseLoadingDialog();

                                        Intent nextPage = new Intent(Client_ChooseMethod.this, Client_DealingBill.class);
                                        startActivity(nextPage);
                                    }
                                };

                                JobServiceStatusViewModel model = new JobServiceStatusViewModel();

//                                if(isCreditCard) {
//                                    model.setStatus(8);
//                                }
//                                else if(!isCreditCard){
                                    model.setStatus(6);
                                //
                                // }
                                model.setProviderUsername(jsm.getProviderUsername());

                                String data= new JobServiceStatusDataConvert().ModelToJson(model);

                                myLoading.ShowLoadingDialog();
                                status.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+ValueMessengerTaskInfo.id, data, "PUT");
                            }
                        };

                        model.setServiceId(ValueMessengerTaskInfo.id);
//                        if(isCreditCard){
//                            model.setPaymentMethod("Credit Card");
//                        }
//                        else if(!isCreditCard) {
                            model.setPaymentMethod(spinner.getSelectedItem().toString());
//                        }
                        String data2 = convert.ModelToJson(model);
                        c.execute("http://para.co.nz/api/ClientJobService/updatejobservice", data2, "PUT");
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

        pic = (CircleImageView) findViewById(R.id.imageView_pic);
        name = (TextView) findViewById(R.id.textView_name);
//        budget = (TextView) findViewById(R.id.textView_budget);
//        securedPayment = (TextView) findViewById(R.id.textView_deposit);
        balance = (TextView) findViewById(R.id.textView_total);
        //creditCard = (TextView) findViewById(R.id.textView_creditCard);
        confirm = (TextView) findViewById(R.id.textView_confirm);
//        creditPic = (ImageView) findViewById(R.id.imageView_creditCard);
//        otherPic = (ImageView) findViewById(R.id.imageView_others);
//        creditContainer = (LinearLayout) findViewById(R.id.linearLayout_creditCard);
        other = (LinearLayout) findViewById(R.id.linearLayout_others);
        save = (ImageView) findViewById(R.id.imageView_ok);
        back = (ImageView) findViewById(R.id.imageView_back);
        title = (TextView) findViewById(R.id.tree_field_title);
        spinner = (Spinner) findViewById(R.id.spinner_choose);

        title.setText("Payment method");
        save.setVisibility(View.INVISIBLE);

        getData();
    }
}
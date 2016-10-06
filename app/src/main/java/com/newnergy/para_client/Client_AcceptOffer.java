package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Client_AcceptOffer extends AppCompatActivity {

    Context context = this;
    Loading_Dialog myLoading;
    TextView title, name, amount;
    CircleImageView pic;
    ImageView back, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_accept_offer);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        title = (TextView) findViewById(R.id.profile_rating_title);
        back = (ImageView) findViewById(R.id.imageView_back);
        confirm = (ImageView) findViewById(R.id.imageView_ok);
        amount = (TextView) findViewById(R.id.textView_amount);
        name = (TextView) findViewById(R.id.textView_name);
        pic = (CircleImageView) findViewById(R.id.imageView_pic);

        title.setText("Accept offer");

        name.setText(ValueMessengerTaskInfo.providerFirstName+" "+ValueMessengerTaskInfo.providerLastName);
        pic.setImageBitmap(ValueMessengerTaskInfo.providerProfilePhoto);
        amount.setText("$"+ValueMessengerTaskInfo.providerOfferedPrice);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSendController c = new DataSendController(){
                    @Override
                    public void onResponse(Boolean result) {
                        super.onResponse(result);
                        myLoading.CloseLoadingDialog();
                    }
                };

                JobServiceStatusViewModel model = new JobServiceStatusViewModel();

                model.setStatus(4);

                model.setPrice(ValueMessengerTaskInfo.providerOfferedPrice);
                model.setProviderUsername(ValueMessagerFurtherInfo.userName.toString());

                String data= new JobServiceStatusDataConvert().ModelToJson(model);

                myLoading.ShowLoadingDialog();
                c.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+ValueMessengerTaskInfo.id, data, "PUT");

                Intent nextPage = new Intent(Client_AcceptOffer.this, Client_Confirm2.class);
                startActivity(nextPage);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_AcceptOffer.this, Client_Confirm.class);
                startActivity(intent);
            }
        });

    }
}

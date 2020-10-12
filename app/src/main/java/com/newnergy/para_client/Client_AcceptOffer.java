package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Client_AcceptOffer extends AppCompatActivity {

    Context context = this;
    Loading_Dialog myLoading;
    TextView title, name, amount, confirm;
    CircleImageView pic;
    ImageView back, save, isSecured;
    LinearLayout main, secure;

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
                            Intent intent = new Intent(Client_AcceptOffer.this, Client_PopUp_Version.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_accept_offer);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        title = (TextView) findViewById(R.id.tree_field_title);
        back = (ImageView) findViewById(R.id.imageView_back);
        save = (ImageView) findViewById(R.id.imageView_ok);
        //isSecured = (ImageView) findViewById(R.id.imageView_isSecured);
        amount = (TextView) findViewById(R.id.textView_amount);
        name = (TextView) findViewById(R.id.textView_name);
        confirm = (TextView) findViewById(R.id.textView_confirm);
        pic = (CircleImageView) findViewById(R.id.imageView_pic);
        main = (LinearLayout) findViewById(R.id.linearLayout_main);
        secure = (LinearLayout) findViewById(R.id.linearLayout_secure);

        main.removeView(secure);
        title.setText("Accept offer");
        name.setText(ValueMessengerTaskInfo.providerFirstName+" "+ValueMessengerTaskInfo.providerLastName);
        pic.setImageBitmap(ValueMessengerTaskInfo.providerProfilePhoto);

        if(!ValueMessengerTaskInfo.providerProfilePicUrl.equals("")){
            Picasso.with(context).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+ValueMessengerTaskInfo.providerProfilePicUrl).into(pic);
        }else{
            pic.setImageResource(R.drawable.client_photo_round);
        }

        amount.setText("$"+ValueMessengerTaskInfo.providerOfferedPrice);
        save.setVisibility(View.INVISIBLE);

//        if(ValueMessengerTaskInfo.providerDeposit > 0){
//            isSecured.setImageResource(R.drawable.approved);
//        }
//        else{
//            isSecured.setImageResource(R.drawable.no_funds);
//        }

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

                                Intent nextPage = new Intent(Client_AcceptOffer.this, Client_DealingBill.class);
                                startActivity(nextPage);
                            }
                        };

                        JobServiceStatusViewModel model = new JobServiceStatusViewModel();

                        model.setStatus(4);
                        model.setProviderUsername(ValueMessengerTaskInfo.providerUserName.toString());
                        model.setPrice(ValueMessengerTaskInfo.providerOfferedPrice);

                        String data= new JobServiceStatusDataConvert().ModelToJson(model);

                        myLoading.ShowLoadingDialog();
                        status.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+ValueMessengerTaskInfo.id, data, "PUT");
                    }
                };

                model.setServiceId(ValueMessengerTaskInfo.id);

                String data2 = convert.ModelToJson(model);
                c.execute("http://para.co.nz/api/ClientJobService/updatejobservice", data2, "PUT");
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
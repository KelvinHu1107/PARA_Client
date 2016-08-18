package com.newnergy.para_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Client_PopUpWindow extends AppCompatActivity {
    private Button yes, no;
    private TextView price, name;
    private ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_popup_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(width,height);

        yes = (Button) findViewById(R.id.button_popup_yes);
        no = (Button) findViewById(R.id.button_popUp_no);
        price = (TextView) findViewById(R.id.textView_popUpWindow_price);
        name = (TextView) findViewById(R.id.textView_popUpWindow_name);
        photo = (ImageView) findViewById(R.id.imageView_popUpWindow_photo);


        price.setText("$ "+ValueMessengerTaskInfo.providerOfferedPrice.toString());
        name.setText(ValueMessengerTaskInfo.providerFirstName+" "+ValueMessengerTaskInfo.providerLastName);
        if(ValueMessengerTaskInfo.providerProfilePhoto != null)
        photo.setImageBitmap(ValueMessengerTaskInfo.providerProfilePhoto);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataSendController c = new DataSendController(){
                    @Override
                    public void onResponse(Boolean result) {
                        super.onResponse(result);
                    }
                };

                JobServiceStatusViewModel model = new JobServiceStatusViewModel();

                model.setStatus(3);
                model.setPrice(ValueMessengerTaskInfo.providerOfferedPrice);
                model.setProviderUsername(ValueMessagerFurtherInfo.userName.toString());

                String data= new JobServiceStatusDataConvert().ModelToJson(model);

                c.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+ValueMessengerTaskInfo.id, data, "PUT");

                Intent nextPage = new Intent(Client_PopUpWindow.this, Client_Confirm2.class);
                startActivity(nextPage);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage = new Intent(Client_PopUpWindow.this, Client_Confirm.class);
                startActivity(nextPage);

            }
        });

    }
}

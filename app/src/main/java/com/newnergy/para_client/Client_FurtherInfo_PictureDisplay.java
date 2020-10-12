package com.newnergy.para_client;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Client_FurtherInfo_PictureDisplay extends AppCompatActivity {

    public ImageView imageView;
    private Animator mCurrentAnimator;
    private int mLongAnimationDuration;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_furtherinfo_picturedisplay);

        imageView = (ImageView) findViewById(R.id.imageView_furtherInfo_display);

        Picasso.with(context).load(ValueMessager.bitmapUrlBuffer).into(imageView);

        mLongAnimationDuration = getResources().getInteger(android.R.integer.config_longAnimTime);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //kelvinkelvin

                switch (ValueMessager.lastPageViewPic){
                    case "FurtherInfo":
                        Intent nextPage_FurtherInfo = new Intent(Client_FurtherInfo_PictureDisplay.this, Client_Further_Info.class);
                        Client_FurtherInfo_PictureDisplay.this.startActivity(nextPage_FurtherInfo);
                        break;

                    case "Confirm":
                        Intent intent = new Intent(Client_FurtherInfo_PictureDisplay.this, Client_Confirm.class);
                        Client_FurtherInfo_PictureDisplay.this.startActivity(intent);
                        break;

                    case "DealingBill":
                        Intent intent2 = new Intent(Client_FurtherInfo_PictureDisplay.this, Client_DealingBill.class);
                        Client_FurtherInfo_PictureDisplay.this.startActivity(intent2);
                        break;

                    case "Confirm2":
                        Intent intent3 = new Intent(Client_FurtherInfo_PictureDisplay.this, Client_Confirm2.class);
                        Client_FurtherInfo_PictureDisplay.this.startActivity(intent3);
                        break;

                    case "providerinfo":
                        Intent intent4 = new Intent(Client_FurtherInfo_PictureDisplay.this, Client_dealt_providerInfo.class);
                        Client_FurtherInfo_PictureDisplay.this.startActivity(intent4);
                        break;
                }
            }
        });

    }

}

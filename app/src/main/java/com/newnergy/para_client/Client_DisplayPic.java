package com.newnergy.para_client;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.newnergy.para_client.Image_package.ImageUnity;
import com.squareup.picasso.Picasso;

public class Client_DisplayPic extends Activity {

    private ImageView imageView;
    private Context context = this;
    private ImageUnity imageUnity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_display);

        imageView = (ImageView) findViewById(R.id.imageView_display);

        Picasso.with(context).load("file://"+ValueMessager.displayPicUrl).into(imageView);
        //imageUnity.setImage(context,imageView,"file://"+ValueMessager.displayPicUrl);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
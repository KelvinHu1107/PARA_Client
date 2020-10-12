package com.newnergy.para_client;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.plus.PlusShare;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class Client_Share extends AppCompatActivity {

    private ShareDialog shareDialog;
    CallbackManager callbackManager;
    private Button FBButton,GooglePlusButton,TwitterButton,EmailSharerButton,MoreSharerButton;
//TODO:logout all share account



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.client_share);
        initialization();
        AllListener();
    }

    public void initialization()
    {
        FBButton=(Button) findViewById(R.id.button_facebook);
        GooglePlusButton=(Button) findViewById(R.id.button_googlePlus);
        TwitterButton=(Button) findViewById(R.id.button_Twitter);
        EmailSharerButton=(Button) findViewById(R.id.button_email);
        MoreSharerButton=(Button) findViewById(R.id.button_More_share);


        shareDialog = new ShareDialog(this);

        callbackManager = CallbackManager.Factory.create();
    }

    //Listener for each
    public void AllListener() {
        //-----------------------facebook Listener-----------------------
        FBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Test Facebook share function android")

                            .setImageUrl(Uri.parse("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQUpBLh7aqnTj0AVuAXL_PyqCCkD_Tw-9P-5PMACBTHvfiP6sZi"))
                            .setContentDescription("Hello world")
                            .setContentUrl(Uri.parse("www.google.com"))
                            .build();

                    shareDialog.show(linkContent);
                    LoginManager.getInstance().logOut();
                }
            }
        });
        //-----------------------facebook Listener-----------------------

        //-----------------------google Listener-----------------------
        GooglePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    // Make the call to GoogleApiClient
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("text/plain");
//                    intent.putExtra(Intent.EXTRA_TEXT,
//                            "Just testing, check this out: http://stackoverflow.com/questions/28212490/");
//                    filterByPackageName(Share.this, intent, "com.google.android.apps.plus");
//                    startActivity(intent);

                    Intent shareIntent = new PlusShare.Builder(Client_Share.this)
                            .setType("text/plain")
                            .setText("Hello Android!")
                            .setContentUrl(Uri.parse("http://newnergy.co.nz/"))
                            .getIntent();
                    startActivityForResult(shareIntent, 0);

                }
            }
        });
        //--------------------end google Listener-----------------------

        //--------------------Twitter Listener-----------------------
        TwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Uri myImageUri = Uri.parse("android.resource://newnergy.para_client/"+R.drawable.button_sidemenu);


                    File myImageFile = new File("android.resource://drawable/button_sidemenu.png");
                    Uri myImageUri = Uri.fromFile(myImageFile);

                    URL url = new URL("http://www.google.com");

                    TweetComposer.Builder builder = new TweetComposer.Builder(Client_Share.this)
                            .text("good app, try it: ")
                            .url(url)
                            .image(myImageUri);
                    builder.show();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        });

        //--------------------end Twitter Listener-----------------------


//--------------------More Sharer Button Listener----------------------------
        EmailSharerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("plain/text");
                share.putExtra(Intent.EXTRA_SUBJECT, "Share para app");
                share.putExtra(Intent.EXTRA_TEXT, "This is good app, try it: http://newnergy.co.nz/");
                //share.putExtra(Intent.EXTRA_TEXT, Uri.parse("http://newnergy.co.nz/"));

                startActivity(Intent.createChooser(share, "Other share way"));

            }
//--------------------end More Sharer Button Listener-----------------------

        });

//--------------------More Sharer Button Listener----------------------------
        MoreSharerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Share para app");
                share.putExtra(Intent.EXTRA_TEXT, "This is good app, try it: http://newnergy.co.nz/");
                //share.putExtra(Intent.EXTRA_TEXT, Uri.parse("http://newnergy.co.nz/"));

                startActivity(Intent.createChooser(share, "Other share way"));

            }
        });




//--------------------end More Sharer Button Listener-----------------------


    //end all Listener
    }

//end share class
}




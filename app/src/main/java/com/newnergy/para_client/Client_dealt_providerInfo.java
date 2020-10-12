package com.newnergy.para_client;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newnergy.para_client.Chat.Client_Chat;
import com.newnergy.para_client.Image_package.ImageUnity;
import com.squareup.picasso.Picasso;

public class Client_dealt_providerInfo extends AppCompatActivity {


    ViewPager viewPager;
    CustomPageAdapter customPageAdapter;
    ProviderProfileViewModel jsm;
    Context context = this;
    String text;
    TextView name ;
    TextView address ;
    TextView phoneNo, offerJob ;
    TextView email ;
    TextView descriptionInFurtherInfo ;
    TextView title, skills;
    ImageView profilePicture, back, save, dial, chat, location, star1, star2, star3, star4, star5;
    LinearLayout button, buttonContainer, detail, phone, emailContainer, addressContainer, starContainer;
    ImageUnity imageUnity = new ImageUnity();
    Loading_Dialog myLoading=new Loading_Dialog();

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
                            Intent intent = new Intent(Client_dealt_providerInfo.this, Client_PopUp_Version.class);
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

    private boolean checkCallPermission(){
        String permission = "android.permission.CALL_PHONE";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
        //return true;
    }


    public void getData(){
        DataTransmitController c=new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                jsm = ProviderProfileDataConvert.convertJsonToModel(result);

                name.setText(jsm.getFirstName()+" "+jsm.getLastName());
                ValueMessager.providerFirstName = jsm.getFirstName();
                ValueMessager.providerLastName = jsm.getLastName();
                address.setText(jsm.getProviderAddressStreet()+", "+jsm.getProviderAddressSuburb()+", "+jsm.getProviderAddressCity());
                phoneNo.setText(jsm.getCellPhone());
                email.setText(jsm.getUsername());

                ValueMessager.providerRating = jsm.getRating();

                if(ValueMessager.providerRating>=0.0 && ValueMessager.providerRating<0.5){
                    star1.setImageResource(R.drawable.median_empty_star);
                    star2.setImageResource(R.drawable.median_empty_star);
                    star3.setImageResource(R.drawable.median_empty_star);
                    star4.setImageResource(R.drawable.median_empty_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=0.5 && ValueMessager.providerRating<1.0){
                    star1.setImageResource(R.drawable.median_half_star);
                    star2.setImageResource(R.drawable.median_empty_star);
                    star3.setImageResource(R.drawable.median_empty_star);
                    star4.setImageResource(R.drawable.median_empty_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=1.0 && ValueMessager.providerRating<1.5){
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_empty_star);
                    star3.setImageResource(R.drawable.median_empty_star);
                    star4.setImageResource(R.drawable.median_empty_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=1.5 && ValueMessager.providerRating<2.0){
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_half_star);
                    star3.setImageResource(R.drawable.median_empty_star);
                    star4.setImageResource(R.drawable.median_empty_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=2.0 && ValueMessager.providerRating<2.5){
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_full_star);
                    star3.setImageResource(R.drawable.median_empty_star);
                    star4.setImageResource(R.drawable.median_empty_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=2.5 && ValueMessager.providerRating<3.0){
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_full_star);
                    star3.setImageResource(R.drawable.median_half_star);
                    star4.setImageResource(R.drawable.median_empty_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=3.0 && ValueMessager.providerRating<3.5){
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_full_star);
                    star3.setImageResource(R.drawable.median_full_star);
                    star4.setImageResource(R.drawable.median_empty_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=3.5 && ValueMessager.providerRating<4.0){
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_full_star);
                    star3.setImageResource(R.drawable.median_full_star);
                    star4.setImageResource(R.drawable.median_half_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=4.0 && ValueMessager.providerRating<4.5){
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_full_star);
                    star3.setImageResource(R.drawable.median_full_star);
                    star4.setImageResource(R.drawable.median_full_star);
                    star5.setImageResource(R.drawable.median_empty_star);
                }else if(ValueMessager.providerRating>=4.5 && ValueMessager.providerRating<5.0){
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_full_star);
                    star3.setImageResource(R.drawable.median_full_star);
                    star4.setImageResource(R.drawable.median_full_star);
                    star5.setImageResource(R.drawable.median_half_star);
                }else{
                    star1.setImageResource(R.drawable.median_full_star);
                    star2.setImageResource(R.drawable.median_full_star);
                    star3.setImageResource(R.drawable.median_full_star);
                    star4.setImageResource(R.drawable.median_full_star);
                    star5.setImageResource(R.drawable.median_full_star);
                }

                ValueMessagerFurtherInfo.providerUserName = jsm.getUsername();
                title.setText(jsm.getFirstName()+" "+jsm.getLastName());
                Picasso.with(context).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+jsm.getProfilePicture()).into(profilePicture);

                ValueMessagerFurtherInfo.ServicePhotoUrl = jsm.getProviderPhotos();
                customPageAdapter = new CustomPageAdapter(context, "http://para.co.nz/api/ProviderProfile/GetProviderPhotoImage/","providerinfo");
                customPageAdapter.setImageUrls(jsm.getProviderPhotos());
                viewPager.setAdapter(customPageAdapter);

                DataTransmitController skill = new DataTransmitController(){
                    @Override
                    public void onResponse(String result) {
                        super.onResponse(result);
                        UpdateProviderSkillDataConvert convert = new UpdateProviderSkillDataConvert();
                        UpdateProviderSkillViewModel model;
                        model = convert.convertJsonToModel(result);

                        //text = model.getSkillName();
                        text = "";

//                        for (String s : model.getSkillName())
//                        {
//                            text += s + "\t";
//                        }
//
//                        skills.setText(text);
                        myLoading.CloseLoadingDialog();
                    }
                };

                skill.execute("http://para.co.nz/api/providerskill/GetAllProviderSkills/"+ValueMessager.email,"","GET");

            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ProviderProfile/GetProviderDetail/"+ ValueMessagerFurtherInfo.userName,"","GET");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ValueMessager.resolution1080x720)
            setContentView(R.layout.client_provider_info720x1080);
        else if(ValueMessager.resolution800x480)
            setContentView(R.layout.client_provider_info480x800);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        name = (TextView) findViewById(R.id.textView_furtherInfo_name);
        address = (TextView) findViewById(R.id.textView_furtherInfo_address);
        phoneNo = (TextView) findViewById(R.id.textView_furtherInfo_phoneNo);
        email = (TextView) findViewById(R.id.textView_furtherInfo_email);
        skills = (TextView) findViewById(R.id.textView_skills);
        back = (ImageView) findViewById(R.id.imageView_back);
        save = (ImageView) findViewById(R.id.imageView_ok);
        descriptionInFurtherInfo = (TextView) findViewById(R.id.textView_furtherInfo_description);
        title = (TextView) findViewById(R.id.tree_field_title);
        profilePicture = (ImageView) findViewById(R.id.imageView_furtherInfo_profilePicture);
        dial = (ImageView) findViewById(R.id.imageButton_furtherInfo_dial);
        chat = (ImageView) findViewById(R.id.imageButton_chat_furtherInfo);
        offerJob = (TextView) findViewById(R.id.button_furtherInfo_offerJob);
        location = (ImageView) findViewById(R.id.imageButton_location_furtherInfo);
        viewPager = (ViewPager) findViewById(R.id.viewpager_furtherinfo);
        button = (LinearLayout) findViewById(R.id.bottons);
        buttonContainer = (LinearLayout) findViewById(R.id.buttons_container);
        detail = (LinearLayout) findViewById(R.id.detail);
        phone = (LinearLayout) findViewById(R.id.phone);
        emailContainer = (LinearLayout) findViewById(R.id.email);
        addressContainer = (LinearLayout) findViewById(R.id.address);
        starContainer = (LinearLayout) findViewById(R.id.linearLayout_starContainer);
        star1 = (ImageView) findViewById(R.id.imageView_star1);
        star2 = (ImageView) findViewById(R.id.imageView_star2);
        star3 = (ImageView) findViewById(R.id.imageView_star3);
        star4 = (ImageView) findViewById(R.id.imageView_star4);
        star5 = (ImageView) findViewById(R.id.imageView_star5);

        offerJob.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        getData();

        starContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ValueMessager.commentLastPage = 0;

                Intent intent = new Intent(Client_dealt_providerInfo.this,Client_CommentPage.class);
                startActivity(intent);

                return true;
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueMessager.lastPageMap = "Dealt";
                String name= jsm.getFirstName()+" "+jsm.getLastName();
                boolean isDetails=true;
                String address=jsm.getProviderAddressStreet()+" "+jsm.getProviderAddressSuburb()+" "+jsm.getProviderAddressCity();
                Intent intent = new Intent(Client_dealt_providerInfo.this ,MapsActivity.class);
                String providerType=jsm.getProviderType();
                intent.putExtra("type",providerType);
                intent.putExtra("mapData",address);
                intent.putExtra("state",isDetails);
                intent.putExtra("username",name);

                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueMessager.chatLastPage = "Details";
                ValueMessager.providerEmail = jsm.getUsername();

                profilePicture.setDrawingCacheEnabled(true);
                ValueMessager.providerProfileBitmap = profilePicture.getDrawingCache();

                ValueMessager.is_chat = false;

                Intent intent = new Intent(Client_dealt_providerInfo.this, Client_Chat.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent nextPage_IncomingList = new Intent(Client_dealt_providerInfo.this, Client_DealingBill.class);
                        startActivity(nextPage_IncomingList);
            }
        });

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCallPermission()) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + jsm.getCellPhone()));
                    startActivity(callIntent);
                }
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},100);
                    }
                }
            }
        });

    }
}

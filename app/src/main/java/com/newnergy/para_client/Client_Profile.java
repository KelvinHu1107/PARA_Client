package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Client_Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public RoundImage roundImage;
    public ImageButton pending,message,main;
    public LinearLayout changeName,changePhone,changeAddress;
    public TextView name,email,phone,address;
    public ImageView profilePicture;
    public Button button;
    private ClientProfileViewModel list;
    private ImageView profilePictureSlidingMenu;
    private TextView profileName;
    ImageUnity imageUnity = new ImageUnity();
    Context context = this;
    Loading_Dialog myLoading=new Loading_Dialog();

    public void getData(){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                list = ClientDataConvert.convertJsonToModel(result);
                btnFunction();
                profileName.setText(list.getFirstName()+" "+list.getLastName());
                refreshData();

            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientProfile/getClientDetail/"+ValueMessager.email,"","GET");


    }

    public void refreshData() {

        ValueMessager.firstName = list.getFirstName();
        ValueMessager.lastName = list.getLastName();
        ValueMessager.phone = list.getCellPhone();

        ValueMessager.address_id = list.getClientAddressId();
        if(ValueMessager.address_id != null) {
            ValueMessager.address_street = list.getClientAddressStreet();
            ValueMessager.address_suburb = list.getClientAddressSuburb();
            ValueMessager.address_city = list.getClientAddressCity();
        }

        //setup profile value from value messenger

            name.setText(ValueMessager.firstName+" "+ ValueMessager.lastName);
            email.setText(ValueMessager.email);
            phone.setText(ValueMessager.phone);
            address.setText(ValueMessager.address_street+", "+ ValueMessager.address_suburb+", "+ ValueMessager.address_city);

//        if(ValueMessager.companyName != null)
//            companyName.setText(ValueMessager.companyName);
//
//        if(ValueMessager.companyPhone != null)
//            companyPhone.setText(ValueMessager.companyPhone);
//
//
//        companyAddress.setText(ValueMessager.companyAddress_street+", "+ ValueMessager.companyAddress_suburb+", "+ ValueMessager.companyAddress_city);
//
//        if(ValueMessager.licenseNo != null)
//            licenseNo.setText(ValueMessager.licenseNo);
//
//        if(ValueMessager.description != null)
//            description.setText(ValueMessager.description);
        //setup profile value from value messenger

        myLoading.CloseLoadingDialog();
    }



    public void btnFunction() {
        pending = (ImageButton) findViewById(R.id.imageButton_ProfileToPending);
        message = (ImageButton) findViewById(R.id.imageButton_ProfileToMessage);
        main = (ImageButton) findViewById(R.id.imageButton_ProfileToMain);
        changeName = (LinearLayout) findViewById(R.id.profile_name_bar);
        changePhone = (LinearLayout) findViewById(R.id.profile_phone_bar);
        changeAddress = (LinearLayout) findViewById(R.id.profile_address_bar);
        name = (TextView) findViewById(R.id.textView_profileName);
        email = (TextView) findViewById(R.id.textView_profileEmail);
        phone = (TextView) findViewById(R.id.textView_profilePhone);
        address = (TextView) findViewById(R.id.textView_profileAddress);
        profilePicture = (ImageView) findViewById(R.id.imageView_profile_profileImage);
        profilePictureSlidingMenu = (ImageView) findViewById(R.id.imageView_sideMenu_pic);
        profileName = (TextView) findViewById(R.id.textView_slidingMenu_name);

        if(ValueMessager.userProfileBitmap != null) {
            ValueMessager.userProfileBitmap = imageUnity.toRoundBitmap(ValueMessager.userProfileBitmap);
            profilePictureSlidingMenu.setImageBitmap(ValueMessager.userProfileBitmap);
            profilePicture.setImageBitmap(ValueMessager.userProfileBitmap);
        }
        if(ValueMessager.userFirstName != null && ValueMessager.userLastName != null)
            profileName.setText(ValueMessager.userFirstName+" "+ValueMessager.userLastName);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Main = new Intent(Client_Profile.this, Client_Incoming_Services.class);
                startActivity(nextPage_Main);
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Message = new Intent(Client_Profile.this, Client_Message.class);
                startActivity(nextPage_Message);
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextPage_Pending = new Intent(Client_Profile.this, Client_Pending.class);
                startActivity(nextPage_Pending);
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangeName = new Intent(Client_Profile.this, profile_changeName.class);
                startActivity(nextPage_ChangeName);
            }
        });

        changePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangePhoneNumber = new Intent(Client_Profile.this, profile_changePhone.class);
                startActivity(nextPage_ChangePhoneNumber);
            }
        });

        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent nextPage_ChangeAddress = new Intent(Client_Profile.this, profile_changeAddress.class);
                startActivity(nextPage_ChangeAddress);
            }
        });


        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Client_Profile.this,SelectPicPopupWindow.class);
                SelectPicPopupWindow.targetControl(profilePicture,profilePictureSlidingMenu);

                startActivity(intent);


            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_pending);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            ValueMessager.historyLastPage = 4;

            Intent nextPage_History = new Intent(Client_Profile.this, Client_History.class);
            startActivity(nextPage_History);
        } else if (id == R.id.nav_gallery) {

            ValueMessager.settingLastPage = 4;

            Intent nextPage_Setting = new Intent(Client_Profile.this, Client_SlidingMenu_Setting.class);
            startActivity(nextPage_Setting);

        } else if (id == R.id.nav_manage) {

            ValueMessager.aboutUsLastPage = 4;

            Intent nextPage_AboutUs = new Intent(Client_Profile.this, Client_AboutUs.class);
            startActivity(nextPage_AboutUs);

        } else if (id == R.id.nav_logOut){
        Intent intent = new Intent(Client_Profile.this, Client_LoginActivity.class);
        startActivity(intent);
    }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profile);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_profile);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profile);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_profile);
        navigationView.setNavigationItemSelectedListener(this);

        getData();

    }
}

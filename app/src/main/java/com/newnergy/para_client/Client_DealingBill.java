package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.newnergy.para_client.Image_package.ImageUnity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client_DealingBill extends AppCompatActivity {

    private TextView fundSecured, bottomText, taskDue, viewBill;
    private TextView title, jobTitle, date, budgetTv, description, address, city, providerDeposit, providerName, providerBotSecuredFund;
    private ImageView[] photo;
    private ImageView progressBar, cancel, save, clientPic, providerPic, providerLock, clientLock;
    private LinearLayout main, container, providerContainer, clientBox, providerBox, topSideContainer, securityContainer, viewPagerContainer;
    private CircleImageView pic;
    ClientPendingDetailViewModel jsm;
    Date dateData;
    ListView list;
    String[] objectName;
    Integer[] providerId;
    int[] serviceId, status, photoId;
    String[] photoAddress;
    Context context = this;
    Loading_Dialog myLoading;
    ImageUnity imageUnity;
    ViewPager viewPager;
    CustomPageAdapter customPageAdapter;

    public void getImageData(String profilePhotoUrl, final ImageView imageView, int lenth, int position) {

        Picasso.with(context).load("http://para.co.nz/api/JobService/GetServiceImage/"+ profilePhotoUrl).into(imageView);
    }

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
                            Intent intent = new Intent(Client_DealingBill.this, Client_PopUp_Version.class);
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

    public void getData(){
        DataTransmitController c = new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                ClientPendingDetailDataConvert clientPendingDetailDataConvert = new ClientPendingDetailDataConvert();
                jsm = clientPendingDetailDataConvert.convertJsonToModel(result);

                cancel = (ImageView) findViewById(R.id.imageView_back);
//                clientPic = (ImageView) findViewById(R.id.imageView_clientPic);
//                providerPic = (ImageView) findViewById(R.id.imageView_providerPic);
//                providerLock = (ImageView) findViewById(R.id.imageView_providerLock);
//                clientLock = (ImageView) findViewById(R.id.imageView_clientLock);
                title = (TextView) findViewById(R.id.tree_field_title);
                jobTitle = (TextView) findViewById(R.id.textView_comfirm_jobTitle);
                save = (ImageView) findViewById(R.id.imageView_ok);
                date = (TextView) findViewById(R.id.textView_comfirm_postDate);
                budgetTv = (TextView) findViewById(R.id.textView_comfirm_budget);
                description = (TextView) findViewById(R.id.textView_comfirm_description);
//                fundSecured = (TextView) findViewById(R.id.textView_pendingInfo_fund);
                address = (TextView) findViewById(R.id.textView_comfirm_address);
                //providerBotSecuredFund =(TextView) findViewById(R.id.providerBotSecuredFund);
                city = (TextView) findViewById(R.id.textView_city);
                bottomText = (TextView) findViewById(R.id.textView_bottom);
                providerName = (TextView) findViewById(R.id.textView_name);
//                providerDeposit = (TextView) findViewById(R.id.textView_provider_fund);
                progressBar = (ImageView) findViewById(R.id.imageView_progressBar);
                pic = (CircleImageView) findViewById(R.id.imageView_confirm_pic);
                main = (LinearLayout) findViewById(R.id.linearLayout_main);
//                clientBox = (LinearLayout) findViewById(R.id.linearLayout_clientBox);
//                providerBox = (LinearLayout) findViewById(R.id.linearLayout_providerBox);
                container = (LinearLayout) findViewById(R.id.linearLayout_container);
                providerContainer = (LinearLayout) findViewById(R.id.linearLayout_providerContainer);
                topSideContainer = (LinearLayout) findViewById(R.id.linearLayout_topSideContainer);
//                securityContainer = (LinearLayout) findViewById(R.id.linearLayout_securityContainer);
                viewPagerContainer = (LinearLayout) findViewById(R.id.linearLayout_viewPager);
                taskDue = (TextView) findViewById(R.id.textView_taskDue);
                viewBill = (TextView) findViewById(R.id.textView_viewBill);
                viewPager = (ViewPager) findViewById(R.id.viewpager);

                save.setVisibility(View.INVISIBLE);
                providerName.setText(jsm.getProviderFirstname()+" "+jsm.getProviderLastname());
                topSideContainer.removeView(securityContainer);

//                clientPic.setImageBitmap(ValueMessager.userProfileBitmap);

//                if(jsm.getProviderProfilePhoto().equals("")){
//                    providerPic.setImageResource(R.drawable.client_photo_round);
//                }
//                else {
//                    Picasso.with(context).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + jsm.getProviderProfilePhoto()).into(providerPic);
//                }
//
                if(jsm.getProviderProfilePhoto().toString().equals("")){
                    pic.setImageResource(R.drawable.client_photo_round);
                }
                else {
                    Picasso.with(context).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + jsm.getProviderProfilePhoto()).into(pic);
                }

//                if(jsm.getProviderDeposit()>0) {
//                    providerDeposit.setText("$" + jsm.getProviderDeposit() + " prepayment secured");
//                    providerLock.setImageResource(R.drawable.lock);
//                }
//                else{
//                    providerDeposit.setText("Prepayment not secured");
//                    providerBox.setBackgroundResource(R.drawable.border_small_radius_greyb0b0b0);
//                    providerLock.setImageResource(R.drawable.unlock);
//                }

                if(jsm.getStatus() == 4){
                    bottomText.setText("Task assigned");
                }

                if(jsm.getStatus() == 5){
                    bottomText.setText("Payment sent");
                }

                if(jsm.getStatus() == 6){
                    bottomText.setText("Wait for confirming");
                }

                if(jsm.getStatus() != 7){
                    main.removeView(container);
                }

                //can't view bill set it disabled
                if(jsm.getStatus() != 5 && jsm.getStatus() != 7){
                    viewBill.setBackgroundResource(R.drawable.border_radius_disable_blue);
                    viewBill.setText("Awaiting");
                }

//                if(jsm.getIsSecure()&& jsm.getDeposit()>0){
//                    fundSecured.setText("$"+jsm.getDeposit()+" Prepayment secured");
//                    clientLock.setImageResource(R.drawable.lock);
//                }else{
//                    fundSecured.setText("Prepayment not secured");
//                    clientBox.setBackgroundResource(R.drawable.border_small_radius_greyb0b0b0);
//                    clientLock.setImageResource(R.drawable.unlock);
//                }

                if(jsm.getTitle() != null) {
                    jobTitle.setText(jsm.getTitle());
                    ValueMessager.edit_workTitle = jsm.getTitle();
                }

                String calculatedDate = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat finalFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    dateData = format.parse(jsm.getCreateDate().toString());
                    calculatedDate = finalFormat.format(dateData);
                    date.setText(calculatedDate);

                    taskDue.setText(jsm.getDueDate());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                ValueMessager.edit_workType = jsm.getType();
                if(jsm.getBudget()<=0){
                    budgetTv.setText("Tender");
                }
                else if(jsm.getBudget()>0) {
                    budgetTv.setText(jsm.getBudget().toString());
                }

                ValueMessager.edit_budget = jsm.getBudget().toString();
                address.setText(jsm.getServiceStreet());
                city.setText(jsm.getServiceSuburb()+", "+jsm.getServiceCity());
                ValueMessager.edit_street = jsm.getServiceStreet();
                ValueMessager.edit_subrub = jsm.getServiceSuburb();
                ValueMessager.edit_city = jsm.getServiceCity();
                description.setText(jsm.getDescription());
                ValueMessager.edit_description = jsm.getDescription();
                ValueMessengerTaskInfo.id = jsm.getServiceId();
                ValueMessengerTaskInfo.itemStatus = jsm.getStatus();
                ValueMessager.deposit = jsm.getDeposit();

                progressBar.setImageResource(R.drawable.client_select_03);

                if(jsm.getStatus() != 5 || jsm.getStatus() != 7){

                    //view bill color
                }

                viewBill.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(jsm.getStatus() == 5 || jsm.getStatus() == 7){
                            Intent intent = new Intent(Client_DealingBill.this, Client_ChooseMethod.class);
                            startActivity(intent);
                        }
                    }
                });

                if(jsm.getServicePhotoUrl().length != 0) {

                    ValueMessagerFurtherInfo.ServicePhotoUrl = jsm.getServicePhotoUrl();
                    customPageAdapter = new CustomPageAdapter(context, "http://para.co.nz/api/JobService/GetServiceImage/", "DealingBill");
                    customPageAdapter.setImageUrls(jsm.getServicePhotoUrl());
                    viewPager.setAdapter(customPageAdapter);

                }else{
                    viewPagerContainer.removeView(viewPager);
                }

                myLoading.CloseLoadingDialog();

                if(ValueMessengerTaskInfo.providerId < 0){

                }
                else{
                    ValueMessengerTaskInfo.providerId = jsm.getProviderId();
                }

                if(ValueMessengerTaskInfo.itemStatus == 4 || ValueMessengerTaskInfo.itemStatus == 5){
                    title.setText("Assigned");
                    save.setVisibility(View.INVISIBLE);
                }

                if(ValueMessengerTaskInfo.itemStatus == 6){
                    title.setText("Confirming payment");
                    save.setVisibility(View.INVISIBLE);
                }

                if(ValueMessengerTaskInfo.itemStatus == 7){
                    title.setText("Payment rejected");
                    save.setVisibility(View.INVISIBLE);
                }

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nextPage_CreditCard = new Intent(Client_DealingBill.this, Client_Pending.class);
                        startActivity(nextPage_CreditCard);
                    }
                });

                providerContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ValueMessagerFurtherInfo.userName = jsm.getProviderUsername();

                        Intent intent = new Intent(Client_DealingBill.this, Client_dealt_providerInfo.class);
                        startActivity(intent);
                    }
                });

            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_pending_info_wait_bill);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        getData();
    }
}
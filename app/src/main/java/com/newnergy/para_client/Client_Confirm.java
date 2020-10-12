package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.newnergy.para_client.Image_package.ImageUnity;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client_Confirm extends AppCompatActivity {

    private TextView fundSecured, bottomText, taskDue;
    private TextView title, jobTitle, date, budgetTv, description, address, city;
    private ImageView[] photo;
    private ImageView progressBar, addFund, deleteBtn, cancel, save, clientPic, clientLock;
    private LinearLayout main, container, mainMain, clientSecured, securityContainer, mainContainer, viewPagerContainer;
    ClientPendingDetailViewModel jsm;
    List<ServicePendingProviderViewModel> listData = new ArrayList();
    Date dateData;
    ListView list;
    ListAdapter_Selecting adapter;
    CharSequence[] firstName, acceptTime;
    CharSequence[] lastName;
    CharSequence[] providerPhoto;
    String[] objectName;
    Integer[] providerId;
    int[] serviceId, status, photoId;
    CharSequence[] providerUserName;
    String[] photoAddress;
    Double[] budget, providerRating, providerDeposit;
    Context context = this;
    Loading_Dialog myLoading;
    ImageUnity imageUnity;
    ViewPager viewPager;
    CustomPageAdapter customPageAdapter;

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
                            Intent intent = new Intent(Client_Confirm.this, Client_PopUp_Version.class);
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

    public void delete(int id){
        DataTransmitController c =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                myLoading.CloseLoadingDialog();

                Intent intent = new Intent(Client_Confirm.this, Client_Pending.class);
                startActivity(intent);

            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/DeleteJobService/"+id,"","DELETE");
    }

    public static Bitmap readBitMap(Context context, int resId){

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    public void getImageData(String profilePhotoUrl, final ImageView imageView, int lenth, int position) {

//        GetImageController controller = new GetImageController() {
//            @Override
//            public void onResponse(Bitmap mBitmap) {
//                super.onResponse(mBitmap);
//                if (mBitmap == null) {
//                    return;
//                }
//                imageView.setImageBitmap(mBitmap);
//            }
//        };
//        controller.execute("http://para.co.nz/api/JobService/GetServiceImage/"+ profilePhotoUrl, "","GET");
        Picasso.with(context).load("http://para.co.nz/api/JobService/GetServiceImage/"+ profilePhotoUrl).into(imageView);
    }

    public void initList() {

        DataTransmitController c = new DataTransmitController() {
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                ServicePendingProviderDataConvert convert = new ServicePendingProviderDataConvert();
                listData = convert.convertJsonToArrayList(result);

                providerUserName = new CharSequence[listData.size()];
                firstName = new CharSequence[listData.size()];
                lastName = new CharSequence[listData.size()];
                serviceId = new int[listData.size()];
                budget = new Double[listData.size()];
                providerPhoto = new CharSequence[listData.size()];
                acceptTime = new CharSequence[listData.size()];
                providerId = new Integer[listData.size()];
                providerRating = new Double[listData.size()];
                status = new int[listData.size()];
                objectName = new String[listData.size()];
                providerDeposit = new Double[listData.size()];

                for(int i=0; i< listData.size() ; i++) {
                    providerUserName[i] = listData.get(i).getProviderUsername();
                    objectName[i] = providerUserName[i].toString();
                    firstName[i] = listData.get(i).getProviderFirstname();
                    lastName[i] = listData.get(i).getProivderLastname();
                    serviceId[i] = listData.get(i).getServiceId();
                    budget[i] = listData.get(i).getPrice();
                    providerPhoto[i] = listData.get(i).getProviderProfilePhoto();
                    acceptTime[i] = listData.get(i).getAcceptTime();
                    providerId[i] = listData.get(i).getProviderId();
                    providerRating[i] = listData.get(i).getProviderRating();
                    status[i] = listData.get(i).getStatus();
                    providerDeposit[i] = listData.get(i).getProviderDeposit();
                }

                adapter = new ListAdapter_Selecting(Client_Confirm.this, objectName, firstName, lastName, providerPhoto, providerUserName,
                        budget, acceptTime, providerId, serviceId, providerRating, status, providerDeposit);

                if(listData.size() == 0){
                    mainMain.removeView(list);
                }
                else{
                    ViewGroup.LayoutParams params = list.getLayoutParams();
                    if(ValueMessager.resolution1080x720 || ValueMessager.resolution800x480) {
                        params.height = 250 * listData.size();
                    }
                    else {
                        params.height = 280 * listData.size();
                    }

                    list.setLayoutParams(params);
                    list.requestLayout();

                    list.setAdapter(adapter);
                }
            }
        };
        c.execute("http://para.co.nz/api/ClientServiceProvider/ClientGetPendingJobList/"+jsm.getServiceId(),"","GET");
    }

    public void getData(){
        DataTransmitController c=new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                ClientPendingDetailDataConvert clientPendingDetailDataConvert = new ClientPendingDetailDataConvert();
                jsm = clientPendingDetailDataConvert.convertJsonToModel(result);

                cancel = (ImageView) findViewById(R.id.imageView_back);
                title = (TextView) findViewById(R.id.tree_field_title);
                jobTitle = (TextView) findViewById(R.id.textView_comfirm_jobTitle);
                save = (ImageView) findViewById(R.id.imageView_ok);
                date = (TextView) findViewById(R.id.textView_comfirm_postDate);
                budgetTv = (TextView) findViewById(R.id.textView_comfirm_budget);
                description = (TextView) findViewById(R.id.textView_comfirm_description);
//                fundSecured = (TextView) findViewById(R.id.textView_pendingInfo_fund);
                address = (TextView) findViewById(R.id.textView_comfirm_address);
                city = (TextView) findViewById(R.id.textView_city);
                taskDue = (TextView) findViewById(R.id.textView_taskDue);
                bottomText = (TextView) findViewById(R.id.textView_bottom);
                list = (ListView) findViewById(R.id.listView_selecting);
                progressBar = (ImageView) findViewById(R.id.imageView_progressBar);
//                addFund = (ImageView) findViewById(R.id.imageView_comfirm_addFund);
                deleteBtn = (ImageView) findViewById(R.id.imageView_delete);
//                clientPic = (ImageView) findViewById(R.id.imageView_clientPic);
//                clientLock = (ImageView) findViewById(R.id.imageView_clientLock);
                main = (LinearLayout) findViewById(R.id.linearLayout_main);
                mainMain = (LinearLayout) findViewById(R.id.linearLayout_mainmain);
                container = (LinearLayout) findViewById(R.id.linearLayout_container);
//                clientSecured = (LinearLayout) findViewById(R.id.linearLayout_client_container);
//                securityContainer = (LinearLayout) findViewById(R.id.linearLayout_securityContainer);
                mainContainer = (LinearLayout) findViewById(R.id.linearLayout_mainContainer);
                viewPager = (ViewPager) findViewById(R.id.viewpager_confirm);
                viewPagerContainer = (LinearLayout) findViewById(R.id.linearLayout_viewPager);

                //clientPic.setImageBitmap(ValueMessager.userProfileBitmap);
                mainContainer.removeView(securityContainer);

                if(jsm.getStatus() != 6){
                    main.removeView(container);
                }

                if(jsm.getStatus() == 1) {
                    save.setImageResource(R.drawable.post_edit);
                    bottomText.setText("Incoming offers");
                }
                else
                save.setVisibility(View.INVISIBLE);

                if(jsm.getStatus() == 2){
                    bottomText.setText("Offers Received");
                }

                deleteBtn.setVisibility(View.INVISIBLE);
                if(jsm.getStatus() < 2){
                    deleteBtn.setVisibility(View.VISIBLE);

                    deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delete(ValueMessengerTaskInfo.id);
                        }
                    });
                }

//                if(jsm.getIsSecure()){
//                    fundSecured.setText("Prepayment secured");
//                    clientLock.setImageResource(R.drawable.lock);
//                }
//
//                if(!jsm.getIsSecure()){
//                    fundSecured.setText("Prepayment not secured");
//                    clientSecured.setBackgroundResource(R.drawable.border_small_radius_greyb0b0b0);
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
                    taskDue.setText(jsm.getDueDate().toString());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                ValueMessager.edit_workType = jsm.getType();
                if(jsm.getBudget() <= 0){
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

                if(jsm.getStatus() == 1){
                    progressBar.setImageResource(R.drawable.client_select_01 );
                }
                else if(jsm.getStatus() == 2){
                    progressBar.setImageResource(R.drawable.client_select_02 );
                }

                initList();

                if(jsm.getServicePhotoUrl().length != 0) {

                    ValueMessagerFurtherInfo.ServicePhotoUrl = jsm.getServicePhotoUrl();
                    customPageAdapter = new CustomPageAdapter(context, "http://para.co.nz/api/JobService/GetServiceImage/", "Confirm");
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

                if(ValueMessengerTaskInfo.itemStatus == 1){
                    title.setText("Open");
                    save.setImageResource(R.drawable.post_edit);
                }

                if(ValueMessengerTaskInfo.itemStatus == 2){
                    title.setText("Selecting");
                    save.setVisibility(View.INVISIBLE);
                }

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ValueMessengerTaskInfo.itemStatus == 1){
                            Intent intent = new Intent(Client_Confirm.this, Client_EditOrder.class);
                            startActivity(intent);
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent nextPage_CreditCard = new Intent(Client_Confirm.this, Client_Pending.class);
                        startActivity(nextPage_CreditCard);
                    }
                });

//                addFund.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(jsm.getIsSecure()) {
//                            Intent intent = new Intent(Client_Confirm.this, Client_AddFund.class);
//                            startActivity(intent);
//                        }
//                        else{
//                            Intent intent = new Intent(Client_Confirm.this, Client_Confirming_SecuredFund.class);
//                            startActivity(intent);
//                        }
//                    }
//                });
            }
        };

        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.client_pending_info);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        getData();
    }
}
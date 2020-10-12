package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.newnergy.para_client.Image_package.ImageUnity;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.newnergy.para_client.ValueMessagerFurtherInfo.userName;

public class Client_Confirm2 extends AppCompatActivity {

    private TextView providerNameB4, providerName, rateNum, otherPayment;
    private TextView title, jobTitle, date, budgetTv, description, address, city;
    private ImageView[] photo;
    private ImageView progressBar, providerPicB4, providerPic, cancel, save;
    private Button jobDone;
    ClientPendingDetailViewModel jsm;
    Date dateData;
    String[] objectName;
    int[] serviceId, status, photoId;
    String[] photoAddress;
    RatingBar ratingBar;
    ImageUnity imageUnity = new ImageUnity();
    Context context = this;
    Loading_Dialog myLoading;
    LinearLayout container, b4Rate, rate, topSideContainer, securityContainer, viewPagerContainer;
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
                            Intent intent = new Intent(Client_Confirm2.this, Client_PopUp_Version.class);
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

    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
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
                jobTitle = (TextView) findViewById(R.id.textView_comfirm2_jobTitle);
                save = (ImageView) findViewById(R.id.imageView_ok);
                date = (TextView) findViewById(R.id.textView_comfirm2_postDate);
                budgetTv = (TextView) findViewById(R.id.textView_comfirm2_budget);
                description = (TextView) findViewById(R.id.textView_comfirm2_description);
                providerName = (TextView) findViewById(R.id.textView_confirm2_name);
                otherPayment = (TextView) findViewById(R.id.textView_otherPayment);
                providerNameB4 = (TextView) findViewById(R.id.textView_confirm2_nameb4Rate);
                address = (TextView) findViewById(R.id.textView_comfirm2_address);
                city = (TextView) findViewById(R.id.textView_comfirm2_address_city);
                rateNum = (TextView) findViewById(R.id.textView_rateNum);
                progressBar = (ImageView) findViewById(R.id.imageView_progressBar_confirm2);
                providerPic = (ImageView) findViewById(R.id.imageView_confirm2_pic);
                providerPicB4 = (ImageView) findViewById(R.id.imageView_confirm2_picB4Rate);
                ratingBar = (RatingBar) findViewById(R.id.ratingBar_confirm2);
                container = (LinearLayout) findViewById(R.id.linearLayout_botContainer);
                b4Rate = (LinearLayout) findViewById(R.id.linearLayout_b4Rated);
                rate = (LinearLayout) findViewById(R.id.linearLayout_rated);
                jobDone = (Button) findViewById(R.id.button_pay);
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPagerContainer = (LinearLayout) findViewById(R.id.linearLayout_viewPager);

                save.setVisibility(View.INVISIBLE);
                jobTitle.setText(jsm.getTitle());
                ValueMessager.edit_workTitle = jsm.getTitle();

                String calculatedDate = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat finalFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    dateData = format.parse(jsm.getCompleteDate().toString());
                    calculatedDate = finalFormat.format(dateData);
                    date.setText(calculatedDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                ValueMessager.edit_workType = jsm.getType();
                if(jsm.getPaymentMethod().equals("Credit Card")) {
                    budgetTv.setText("$"+jsm.getBudget().toString());
                    otherPayment.setText("$0");
                }
                else{
                    otherPayment.setText("$"+jsm.getBudget().toString());
                    budgetTv.setText("$0");
                }

                if(jsm.getStatus() == 8){
                    container.removeView(rate);
                    if(jsm.getProviderProfilePhoto().equals("")){
                        providerPicB4.setImageResource(R.drawable.client_photo_round);
                    }else {
                        Picasso.with(context).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + jsm.getProviderProfilePhoto()).into(providerPicB4);
                    }
                    providerNameB4.setText(jsm.getProviderFirstname()+" "+jsm.getProviderLastname());
                    jobDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userName = jsm.getProviderUsername();
                            ValueMessengerTaskInfo.providerId = jsm.getProviderId();
                            ValueMessengerTaskInfo.providerProfilePicUrl = "http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+jsm.getProviderProfilePhoto();
                            ValueMessengerTaskInfo.providerFirstName = jsm.getProviderFirstname();
                            ValueMessengerTaskInfo.providerLastName = jsm.getProviderLastname();

                            Intent intent = new Intent(Client_Confirm2.this, Client_Rating.class);
                            startActivity(intent);
                        }
                    });

                }
                else if(jsm.getStatus() == 9){
                    container.removeView(b4Rate);

                    if(jsm.getProviderProfilePhoto().equals("")){
                        providerPic.setImageResource(R.drawable.client_photo_round);
                    }else {
                        Picasso.with(context).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + jsm.getProviderProfilePhoto()).into(providerPic);
                    }

                    providerName.setText(jsm.getProviderFirstname()+" "+jsm.getProviderLastname());
                    ratingBar.setRating(Float.parseFloat(jsm.getServiceRating().toString()));
                    rateNum.setText(jsm.getServiceRating().toString());
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

                rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ValueMessagerFurtherInfo.userName = jsm.getProviderUsername();
                        providerPic.setDrawingCacheEnabled(true);
                        ValueMessager.providerProfileBitmap = providerPic.getDrawingCache();
                        ValueMessagerFurtherInfo.lastPage = "Confirm2";
                        ValueMessager.providerRating = jsm.getRating();

                        Intent nextPage_FurtherInfo = new Intent(Client_Confirm2.this, Client_Further_Info.class);
                        startActivity(nextPage_FurtherInfo);

                    }
                });

                ratingBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                progressBar.setImageBitmap(readBitMap(context,R.drawable.client_select_04 ));


                if(jsm.getServicePhotoUrl().length != 0) {

                    ValueMessagerFurtherInfo.ServicePhotoUrl = jsm.getServicePhotoUrl();
                    customPageAdapter = new CustomPageAdapter(context, "http://para.co.nz/api/JobService/GetServiceImage/", "Confirm2");
                    customPageAdapter.setImageUrls(jsm.getServicePhotoUrl());
                    viewPager.setAdapter(customPageAdapter);

                }else{
                    viewPagerContainer.removeView(viewPager);
                }

                if(ValueMessengerTaskInfo.providerId < 0){

                }
                else{
                    ValueMessengerTaskInfo.providerId = jsm.getProviderId();
                }

                title.setText("Completed");

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch(ValueMessager.lastPageConfirm2){
                            case 0:{
                                Intent nextPage_CreditCard = new Intent(Client_Confirm2.this, Client_History.class);
                                startActivity(nextPage_CreditCard);
                                break;
                            }

                            case 1:{
                                Intent nextPage_CreditCard = new Intent(Client_Confirm2.this, Client_Pending.class);
                                startActivity(nextPage_CreditCard);
                                break;
                            }
                        }
                    }
                });
                myLoading.CloseLoadingDialog();
            }
        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_pending_info_completed);
        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        getData();
    }
}

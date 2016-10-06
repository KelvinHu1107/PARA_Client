package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client_Confirm2 extends AppCompatActivity {

    private TextView providerName;
    private TextView title, jobTitle, date, budgetTv, description, address, city;
    private ImageView[] photo;
    private ImageView progressBar, providerPic, cancel, save;
    private Button pay;
    ClientPendingDetailViewModel jsm;
    Date dateData;
    String[] objectName;
    int[] serviceId, status, photoId;
    String[] photoAddress;
    RatingBar ratingBar;
    Button jobDone;
    ImageUnity imageUnity = new ImageUnity();
    Context context = this;
    Loading_Dialog myLoading;

    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }

    public void getProviderImageData(String profilePhotoUrl, final ImageView imageView) {

        GetImageController controller = new GetImageController() {
            @Override
            public void onResponse(Bitmap mBitmap) {
                super.onResponse(mBitmap);
                if (mBitmap == null) {
                    imageView.setImageBitmap(readBitMap(context,R.drawable.client_photo_round));
                }
                imageView.setImageBitmap(imageUnity.toRoundBitmap(mBitmap));
                ValueMessengerTaskInfo.providerProfilePhoto = imageUnity.toRoundBitmap(mBitmap);

            }
        };
        controller.execute("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+ profilePhotoUrl, "","POST");
    }


    public void getImageData(String profilePhotoUrl, final ImageView imageView) {

        GetImageController controller = new GetImageController() {
            @Override
            public void onResponse(Bitmap mBitmap) {
                super.onResponse(mBitmap);
                if (mBitmap == null) {
                    return;
                }
                imageView.setImageBitmap(mBitmap);
            }
        };
        controller.execute("http://para.co.nz/api/JobService/GetServiceImage/"+ profilePhotoUrl, "","POST");
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
                address = (TextView) findViewById(R.id.textView_comfirm2_address);
                city = (TextView) findViewById(R.id.textView_comfirm2_address_city);
                progressBar = (ImageView) findViewById(R.id.imageView_progressBar_confirm2);
                providerPic = (ImageView) findViewById(R.id.imageView_confirm2_pic);
                ratingBar = (RatingBar) findViewById(R.id.ratingBar_confirm2);
                pay = (Button) findViewById(R.id.button_pay);

                save.setVisibility(View.INVISIBLE);

//                jobDone.setVisibility(View.VISIBLE);
//                if(jsm.getStatus() == 2 || jsm.getStatus() == 4)
//                    jobDone.setVisibility(View.INVISIBLE);

                jobTitle.setText(jsm.getTitle());
                ValueMessager.edit_workTitle = jsm.getTitle();

                String calculatedDate = null;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                SimpleDateFormat finalFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    dateData = format.parse(jsm.getCreateDate().toString());
                    calculatedDate = finalFormat.format(dateData);
                    date.setText(calculatedDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                ValueMessager.edit_workType = jsm.getType();
                budgetTv.setText(jsm.getBudget().toString());
                ratingBar.setRating(Float.parseFloat(jsm.getRating().toString()));

                providerName.setText(jsm.getProviderFirstname()+" ,"+jsm.getProviderLastname());
                ValueMessager.edit_budget = jsm.getBudget().toString();
                address.setText(jsm.getServiceStreet()+", "+jsm.getServiceSuburb());
                city.setText(jsm.getServiceCity());
                ValueMessager.edit_street = jsm.getServiceStreet();
                ValueMessager.edit_subrub = jsm.getServiceSuburb();
                ValueMessager.edit_city = jsm.getServiceCity();
                description.setText(jsm.getDescription());
                ValueMessager.edit_description = jsm.getDescription();
                ValueMessengerTaskInfo.id = jsm.getServiceId();

                ValueMessengerTaskInfo.itemStatus = jsm.getStatus();



                //getProviderImageData(jsm.getProviderProfilePhoto(), providerPic);
                imageUnity.setImage(context, providerPic, "http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+jsm.getProviderProfilePhoto());

                ratingBar.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });

                if(jsm.getStatus() == 0){
                    progressBar.setImageBitmap(readBitMap(context,R.drawable.client_select_01 ));
                }
                else if(jsm.getStatus() == 1){
                    progressBar.setImageBitmap(readBitMap(context,R.drawable.client_select_02 ));
                }
                else if(jsm.getStatus() == 2){
                    progressBar.setImageBitmap(readBitMap(context,R.drawable.client_select_02 ));
                }
                else if(jsm.getStatus() == 3){
                    progressBar.setImageBitmap(readBitMap(context,R.drawable.client_select_03 ));
                }
                else if(jsm.getStatus() == 4){
                    progressBar.setImageBitmap(readBitMap(context,R.drawable.client_select_04 ));
                }

                if(jsm.getServicePhotoUrl().length != 0) {

                    photoId = new int[5];
                    photoAddress = new String[jsm.getServicePhotoUrl().length];
                    photo = new ImageView[jsm.getServicePhotoUrl().length];

                    photoId[0] = R.id.imageView_confirm_photo1;
                    photoId[1] = R.id.imageView_confirm_photo2;
                    photoId[2] = R.id.imageView_confirm_photo3;
                    photoId[3] = R.id.imageView_confirm_photo4;
                    photoId[4] = R.id.imageView_confirm_photo5;
                    photoAddress = jsm.getServicePhotoUrl();

                    System.out.println("qqqqqqqqqqqqqqqqqq"+context+"ccccccccccccc"+photo[0]+photoAddress[0]);

                    for(int i=0; i<jsm.getServicePhotoUrl().length; i++) {
                        //getImageData(photoAddress[i], photo[i] = (ImageView) findViewById(photoId[i]));
                        imageUnity.setImage(context, photo[i] = (ImageView) findViewById(photoId[i]), "http://para.co.nz/api/JobService/GetServiceImage/"+photoAddress[i]);
                    }
                }

                if(ValueMessengerTaskInfo.providerId < 0){

                }
                else{
                    ValueMessengerTaskInfo.providerId = jsm.getProviderId();
                }

                if(ValueMessengerTaskInfo.itemStatus == 3){
                    title.setText("Assigned");
                }

                if(ValueMessengerTaskInfo.itemStatus == 4){
                    title.setText("Completed");
                }
//                jobDone.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if(jsm.getStatus() == 3)
//                        {
//
//                            ValueMessagerFurtherInfo.userName = jsm.getProviderUsername();
//                            ValueMessengerTaskInfo.providerId = jsm.getProviderId();
//
//                            Intent intent = new Intent(Client_Confirm2.this, Client_Rating.class);
//                            startActivity(intent);
//                        }
//                    }
//                });

                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        providerPic.setDrawingCacheEnabled(true);
                        ValueMessengerTaskInfo.providerProfilePhoto = providerPic.getDrawingCache();
                        ValueMessagerFurtherInfo.userName = jsm.getProviderUsername();
                        ValueMessengerTaskInfo.providerId = jsm.getProviderId();
                        ValueMessengerTaskInfo.providerFirstName = jsm.getProviderFirstname();
                        ValueMessengerTaskInfo.providerLastName = jsm.getProviderLastname();
                        Intent intent = new Intent(Client_Confirm2.this, Client_PayProvider.class);
                        startActivity(intent);
                    }
                });

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

package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.newnergy.para_client.Image_package.ImageUnity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_EditOrder extends AppCompatActivity {


    private TextView title, warning;
    private Spinner s, taskDue;
    private EditText jobTitle, budget, street, suburb, city, description;
    private ImageButton addPhoto;
    private LinearLayout yes, no, main, budgetMain, budgetContainer, budgetYes, budgetNo, notice, secure;
    private ImageView picYes, picNo, cancel, save, send, priceYes, priceNo, photo1, photo2, photo3, photo4, photo5;
    private boolean isFundAdded = true, isBudgetKnown = false;
    ClientPendingDetailViewModel jsm;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    String[] photoAddress;
    int[] photoId;
    private ImageView[] photo = new ImageView[5];
    ImageUnity imageUnity = new ImageUnity();
    private static final int REQUESTCODE=3;
    Context context = this;
    Loading_Dialog myLoading;

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
                            Intent intent = new Intent(Client_EditOrder.this, Client_PopUp_Version.class);
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

    public void getData() {
        DataTransmitController c = new DataTransmitController() {
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                ClientPendingDetailDataConvert clientPendingDetailDataConvert = new ClientPendingDetailDataConvert();
                jsm = clientPendingDetailDataConvert.convertJsonToModel(result);

                ValueMessager.serviceId = jsm.getServiceId();
                photo1 = (ImageView) findViewById(R.id.imageView_OP_photo1);
                photo2 = (ImageView) findViewById(R.id.imageView_OP_photo2);
                photo3 = (ImageView) findViewById(R.id.imageView_OP_photo3);
                photo4 = (ImageView) findViewById(R.id.imageView_OP_photo4);
                photo5 = (ImageView) findViewById(R.id.imageView_OP_photo5);

                photo1.setVisibility(View.INVISIBLE);
                photo2.setVisibility(View.INVISIBLE);
                photo3.setVisibility(View.INVISIBLE);
                photo4.setVisibility(View.INVISIBLE);
                photo5.setVisibility(View.INVISIBLE);


                if(jsm.getServicePhotoUrl().length != 0 && ValueMessager.isSettingDate == false) {

                    ValueMessager.counter = jsm.getServicePhotoUrl().length;

                    photoId = new int[5];
                    photo = new ImageView[jsm.getServicePhotoUrl().length];
                    photoAddress = new String[jsm.getServicePhotoUrl().length];
                    photoAddress = jsm.getServicePhotoUrl();

                    if(ValueMessager.counter == 1){
                        imageUnity.setImage(context, photo1, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[0]);
                        photo1.setVisibility(View.VISIBLE);
                    }
                    if(ValueMessager.counter == 2){
                        imageUnity.setImage(context, photo1, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[0]);
                        imageUnity.setImage(context, photo2, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[1]);
                        photo1.setVisibility(View.VISIBLE);
                        photo2.setVisibility(View.VISIBLE);
                    }
                    if(ValueMessager.counter == 3){
                        imageUnity.setImage(context, photo1, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[0]);
                        imageUnity.setImage(context, photo2, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[1]);
                        imageUnity.setImage(context, photo3, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[2]);
                        photo1.setVisibility(View.VISIBLE);
                        photo2.setVisibility(View.VISIBLE);
                        photo3.setVisibility(View.VISIBLE);
                    }
                    if(ValueMessager.counter == 4){
                        imageUnity.setImage(context, photo1, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[0]);
                        imageUnity.setImage(context, photo2, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[1]);
                        imageUnity.setImage(context, photo3, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[2]);
                        imageUnity.setImage(context, photo4, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[3]);
                        photo1.setVisibility(View.VISIBLE);
                        photo2.setVisibility(View.VISIBLE);
                        photo3.setVisibility(View.VISIBLE);
                        photo4.setVisibility(View.VISIBLE);
                    }

                    if(ValueMessager.counter == 5){
                        imageUnity.setImage(context, photo1, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[0]);
                        imageUnity.setImage(context, photo2, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[1]);
                        imageUnity.setImage(context, photo3, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[2]);
                        imageUnity.setImage(context, photo4, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[3]);
                        imageUnity.setImage(context, photo5, "http://para.co.nz/api/JobService/GetServiceImage/" + photoAddress[4]);
                        photo1.setVisibility(View.VISIBLE);
                        photo2.setVisibility(View.VISIBLE);
                        photo3.setVisibility(View.VISIBLE);
                        photo4.setVisibility(View.VISIBLE);
                        photo5.setVisibility(View.VISIBLE);
                    }
                        photo1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            GetBooleanController c = new GetBooleanController() {
                             @Override
                                public void onResponse(Boolean result) {
                                super.onResponse(result);
                                 if(result)
                                 {
                                     photo1.setVisibility(View.INVISIBLE);
                                     ValueMessager.counter = ValueMessager.counter - 1;
                                 }
                                }
                             };
                                if(photoAddress.length>0)
                                c.execute("http://para.co.nz/api/JobService/DeleteServiceImage/"+jsm.getServiceId()+"/"+photoAddress[0], "", "DELETE");
                            }
                        });

                    photo2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            GetBooleanController c = new GetBooleanController() {
                                @Override
                                public void onResponse(Boolean result) {
                                    super.onResponse(result);
                                    if(result)
                                    {
                                        photo2.setVisibility(View.INVISIBLE);
                                        ValueMessager.counter = ValueMessager.counter - 1;
                                    }
                                }
                            };
                            if(photoAddress.length>1)
                            c.execute("http://para.co.nz/api/JobService/DeleteServiceImage/"+jsm.getServiceId()+"/"+photoAddress[1], "", "DELETE");
                        }
                    });

                    photo3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            GetBooleanController c = new GetBooleanController() {
                                @Override
                                public void onResponse(Boolean result) {
                                    super.onResponse(result);
                                    if(result)
                                    {
                                        photo2.setVisibility(View.INVISIBLE);
                                        ValueMessager.counter = ValueMessager.counter - 1;
                                    }
                                }
                            };
                            if(photoAddress.length>2)
                            c.execute("http://para.co.nz/api/JobService/DeleteServiceImage/"+jsm.getServiceId()+"/"+photoAddress[2], "", "DELETE");
                        }
                    });

                    photo4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            GetBooleanController c = new GetBooleanController() {
                                @Override
                                public void onResponse(Boolean result) {
                                    super.onResponse(result);
                                    if(result)
                                    {
                                        photo4.setVisibility(View.INVISIBLE);
                                        ValueMessager.counter = ValueMessager.counter - 1;
                                    }
                                }
                            };
                            if(photoAddress.length>3)
                            c.execute("http://para.co.nz/api/JobService/DeleteServiceImage/"+jsm.getServiceId()+"/"+photoAddress[3], "", "DELETE");
                        }
                    });

                    photo5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            GetBooleanController c = new GetBooleanController() {
                                @Override
                                public void onResponse(Boolean result) {
                                    super.onResponse(result);
                                    if(result)
                                    {
                                        photo5.setVisibility(View.INVISIBLE);
                                        ValueMessager.counter = ValueMessager.counter - 1;
                                    }
                                }
                            };
                            if(photoAddress.length>4)
                            c.execute("http://para.co.nz/api/JobService/DeleteServiceImage/"+jsm.getServiceId()+"/"+photoAddress[4], "", "DELETE");
                        }
                    });
                }

                btnFunction();
                myLoading.CloseLoadingDialog();
            }


        };
        myLoading.ShowLoadingDialog();
        c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");
    }


    public boolean isBudget (String number){

        Pattern p = Pattern.compile("\\d*\\.?\\d+");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {

                String imageUrl = data.getStringExtra("ImageUrl");
                System.out.println("here: "+ imageUrl);
                ValueMessager.stringBuffer = imageUrl;

                DataTransmitController c = new DataTransmitController() {
                    @Override
                    public void onResponse(String result) {
                        super.onResponse(result);
                        ClientPendingDetailDataConvert clientPendingDetailDataConvert = new ClientPendingDetailDataConvert();
                        jsm = clientPendingDetailDataConvert.convertJsonToModel(result);

                        photoAddress = new String[jsm.getServicePhotoUrl().length];
                        photoAddress = jsm.getServicePhotoUrl();
                        UploadPhoto(ValueMessager.stringBuffer);
                        myLoading.CloseLoadingDialog();
                    }
                };
                myLoading.ShowLoadingDialog();
                c.execute("http://para.co.nz/api/ClientJobService/GetJobService/"+ ValueMessengerTaskInfo.id,"","GET");
            }
        }
    }

    private void UploadPhoto(String bitmapFromPW)
    {
        ValueMessager.counter = ValueMessager.counter +1;

        if(photo1.getVisibility() == View.INVISIBLE)
        {
            Picasso.with(context).load("file://"+bitmapFromPW).into(photo1);
            photo1.setVisibility(View.VISIBLE);
        }
        else if(photo1.getVisibility() == View.VISIBLE && photo2.getVisibility() == View.INVISIBLE )
        {
            Picasso.with(context).load("file://"+bitmapFromPW).into(photo2);
            photo2.setVisibility(View.VISIBLE);
        }
        else if(photo2.getVisibility() == View.VISIBLE && photo3.getVisibility() == View.INVISIBLE  )
        {
            Picasso.with(context).load("file://"+bitmapFromPW).into(photo3);
            photo3.setVisibility(View.VISIBLE);
        }
        else if(photo3.getVisibility() == View.VISIBLE && photo4.getVisibility() == View.INVISIBLE  )
        {
            Picasso.with(context).load("file://"+bitmapFromPW).into(photo4);
            photo4.setVisibility(View.VISIBLE);

        }
        else if(photo4.getVisibility() == View.VISIBLE && photo5.getVisibility() == View.INVISIBLE  )
        {
            Picasso.with(context).load("file://"+bitmapFromPW).into(photo5);
            photo5.setVisibility(View.VISIBLE);

        }

    }

    public void btnFunction(){

        title = (TextView) findViewById(R.id.tree_field_title);
        //dateTv = (TextView) findViewById(R.id.textView_placeOrder_date);
        cancel = (ImageView) findViewById(R.id.imageView_back);
        save = (ImageView) findViewById(R.id.imageView_ok);
        send = (ImageView) findViewById(R.id.imageView_send);
        warning = (TextView) findViewById(R.id.textView_PO_error);
        jobTitle = (EditText) findViewById(R.id.editText_PO_workTitle);
        budget = (EditText) findViewById(R.id.editText_PO_budget);
        street = (EditText) findViewById(R.id.editText_PO_street);
        suburb = (EditText) findViewById(R.id.editText_PO_suburb);
        city = (EditText) findViewById(R.id.editText_PO_city);
        description = (EditText) findViewById(R.id.editText_PO_description);
        addPhoto = (ImageButton) findViewById(R.id.imageButton_addPhoto);
        s = (Spinner) findViewById(R.id.spinner_OP);
        taskDue = (Spinner) findViewById(R.id.spinner_dueDate);
        yes = (LinearLayout) findViewById(R.id.linearLayout_placeOrder_addFund_true);
        no = (LinearLayout) findViewById(R.id.linearLayout_placeOrder_addFund_false);
        //taskDue = (LinearLayout) findViewById(R.id.linearLayout_taskDue);
        main = (LinearLayout) findViewById(R.id.linearLayout_container);
        picYes = (ImageView) findViewById(R.id.imageView_addFund_true);
        picNo = (ImageView) findViewById(R.id.imageView_addFund_false);
        notice = (LinearLayout) findViewById(R.id.linearLayout_notice);
        budgetMain = (LinearLayout) findViewById(R.id.linearLayout_budget_container);
        budgetYes = (LinearLayout) findViewById(R.id.linearLayout_budgetYes);
        budgetNo = (LinearLayout) findViewById(R.id.linearLayout_budgetNo);
        secure = (LinearLayout) findViewById(R.id.linearLayout_secure);
        budgetContainer = (LinearLayout) findViewById(R.id.linearLayout_editText_container);
        priceYes = (ImageView) findViewById(R.id.imageView_budgetYes);
        priceNo = (ImageView) findViewById(R.id.imageView_budgetNo);

        main.removeView(secure);
        main.removeView(notice);
        budgetMain.removeView(budgetContainer);
        send.setVisibility(View.INVISIBLE);

        budgetMain.removeView(budgetContainer);
        if(jsm.getBudget() <= 0){
            isBudgetKnown = false;
        }
        else if (jsm.getBudget() > 0){
            isBudgetKnown = true;
        }

        warning.setVisibility(View.INVISIBLE);
        title.setText("Edit task");

        jobTitle.setText(ValueMessager.edit_workTitle);
        budget.setText(ValueMessager.edit_budget);
        street.setText(ValueMessager.edit_street);
        suburb.setText(ValueMessager.edit_subrub);
        city.setText(ValueMessager.edit_city);
        description.setText(ValueMessager.edit_description);


        switch (jsm.getType()){
            case "Plumber":
                s.setSelection(0);
                break;
            case "Electrician":
                s.setSelection(1);
                break;
            case "Builder":
                s.setSelection(2);
                break;
            case "Painter":
                s.setSelection(3);
                break;
            case "Cleaner":
                s.setSelection(4);
                break;
            case "Others":
                s.setSelection(5);
                break;
        }

        Date datedue;
        String calculatedDate = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat finalFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            datedue = format.parse(jsm.getDueDate().toString());
            calculatedDate = finalFormat.format(datedue);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (jsm.getDueDate()){
            case "ASAP":
                taskDue.setSelection(0);
                break;
            case "Within 24 hrs":
                taskDue.setSelection(1);
                break;
            case "Not specific":
                taskDue.setSelection(2);
                break;
        }

        if(ValueMessager.isSettingDate) {

            if (DraftValues.title != "")
                jobTitle.setText(DraftValues.title);
            if (DraftValues.category != "")
                s.setPrompt(DraftValues.category);

            if (!DraftValues.isBudget) {
                isBudgetKnown = false;
                priceYes.setImageResource(R.drawable.circle);
                priceNo.setImageResource(R.drawable.dot);
                budgetMain.removeView(budgetContainer);

            } else if(DraftValues.isBudget){
                isBudgetKnown = true;
                priceYes.setImageResource(R.drawable.dot);
                priceNo.setImageResource(R.drawable.circle);
                budgetMain.addView(budgetContainer);
                budget.setText(String.valueOf(DraftValues.budget));
            }

            if (DraftValues.street != "")
                street.setText(DraftValues.street);
            if (DraftValues.suburb != "")
                suburb.setText(DraftValues.suburb);
            if (DraftValues.city != "")
                city.setText(DraftValues.city);
            if (DraftValues.description != "")
                description.setText(DraftValues.description);

            if (ValueMessager.counter == 1) {
                photo1.setImageBitmap(DraftValues.pic1);
                photo1.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic1);
            }
            if (ValueMessager.counter == 2) {

                photo1.setImageBitmap(DraftValues.pic1);
                bitmapArray.add(DraftValues.pic1);
                photo2.setImageBitmap(DraftValues.pic2);
                bitmapArray.add(DraftValues.pic2);
                photo1.setVisibility(View.VISIBLE);
                photo2.setVisibility(View.VISIBLE);
            }
            if (ValueMessager.counter == 3) {
                photo1.setImageBitmap(DraftValues.pic1);
                photo1.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic1);
                photo2.setImageBitmap(DraftValues.pic2);
                photo2.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic2);
                photo3.setImageBitmap(DraftValues.pic3);
                photo3.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic3);
            }
            if (ValueMessager.counter == 4) {
                photo1.setImageBitmap(DraftValues.pic1);
                photo1.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic1);
                photo2.setImageBitmap(DraftValues.pic2);
                photo2.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic2);
                photo3.setImageBitmap(DraftValues.pic3);
                photo3.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic3);
                photo4.setImageBitmap(DraftValues.pic4);
                photo4.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic4);
            }
            if (ValueMessager.counter == 5) {
                photo1.setImageBitmap(DraftValues.pic1);
                photo1.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic1);
                photo2.setImageBitmap(DraftValues.pic2);
                photo2.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic2);
                photo3.setImageBitmap(DraftValues.pic3);
                photo3.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic3);
                photo4.setImageBitmap(DraftValues.pic4);
                photo4.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic4);
                photo5.setImageBitmap(DraftValues.pic5);
                photo5.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic5);
            }

//            if (ValueMessager.selectedDay != "")
//                dateTv.setText(ValueMessager.selectedDay + "/" + ValueMessager.selectedMonth + "/" + ValueMessager.selectedYear);


        }else if(!ValueMessager.isSettingDate){
            if(isBudgetKnown == false) {

                priceYes.setImageResource(R.drawable.circle);
                priceNo.setImageResource(R.drawable.dot);
                budgetMain.removeView(budgetContainer);
            }else if(isBudgetKnown == true){

                priceYes.setImageResource(R.drawable.dot);
                priceNo.setImageResource(R.drawable.circle);
                budgetMain.addView(budgetContainer);
                budget.setText(jsm.getBudget().toString());
            }
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DraftValues.title = "";
                DraftValues.category = "";
                DraftValues.budget = "";
                DraftValues.street = "";
                DraftValues.suburb = "";
                DraftValues.city = "";
                DraftValues.description = "";
                DraftValues.pic1 = null;
                DraftValues.pic2 = null;
                DraftValues.pic3 = null;
                DraftValues.pic4 = null;
                DraftValues.pic5 = null;

                ValueMessager.isSettingDate = false;
                ValueMessager.counter = 0;

                Intent intent = new Intent(Client_EditOrder.this, Client_Confirm.class);
                startActivity(intent);
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValueMessager.counter<5) {
                    Intent uploadImage = new Intent(Client_EditOrder.this, SelectPicPopupWindowUploadImage.class);
                    startActivityForResult(uploadImage, REQUESTCODE);
                }
            }
        });

        budgetYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isBudgetKnown = true;
                priceYes.setImageResource(R.drawable.dot);
                priceNo.setImageResource(R.drawable.circle);
                budgetMain.addView(budgetContainer);

            }
        });

        budgetNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isBudgetKnown = false;
                priceYes.setImageResource(R.drawable.circle);
                priceNo.setImageResource(R.drawable.dot);
                budgetMain.removeView(budgetContainer);

            }
        });

//        taskDue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(jobTitle.getText().equals("")){}
//                else{
//                    DraftValues.title = jobTitle.getText().toString();
//                }
//
//                DraftValues.category = s.getSelectedItem().toString();
//
//                if(isBudgetKnown) {
//                    DraftValues.isBudget = true;
//                    if (budget.getText().toString() != "") {
//                        DraftValues.budget = budget.getText().toString();
//                    }
//                }
//                else if(!isBudgetKnown){
//                    DraftValues.isBudget = false;
//                }
//
//                if(street.getText().equals("")){}
//                else{
//                    DraftValues.street = street.getText().toString();
//                }
//
//                if(suburb.getText().equals("")){}
//                else{
//                    DraftValues.suburb = suburb.getText().toString();
//                }
//
//                if(city.getText().equals("")){}
//                else{
//                    DraftValues.city = city.getText().toString();
//                }
//
//                if(description.getText().equals("")){}
//                else{
//                    DraftValues.description = description.getText().toString();
//                }
//
//                photo1.setDrawingCacheEnabled(true);
//                photo2.setDrawingCacheEnabled(true);
//                photo3.setDrawingCacheEnabled(true);
//                photo4.setDrawingCacheEnabled(true);
//                photo5.setDrawingCacheEnabled(true);
//
//
//                DraftValues.pic1 = photo1.getDrawingCache();
//                DraftValues.pic2 = photo2.getDrawingCache();
//                DraftValues.pic3 = photo3.getDrawingCache();
//                DraftValues.pic4 = photo4.getDrawingCache();
//                DraftValues.pic5 = photo5.getDrawingCache();
//
//                ValueMessager.isSettingDate = true;
//                ValueMessager.lastPageCalender = "EditOrder";
//
//                Intent intent = new Intent(Client_EditOrder.this, Client_Calender.class);
//                startActivity(intent);
//
//            }
//        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFundAdded = true;
                main.removeView(notice);
                picYes.setImageResource(R.drawable.dot);
                picNo.setImageResource(R.drawable.circle);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFundAdded = false;
                main.addView(notice);
                picYes.setImageResource(R.drawable.circle);
                picNo.setImageResource(R.drawable.dot);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myLoading.ShowLoadingDialog();

                final ClientUpdateServiceViewModel model=new ClientUpdateServiceViewModel();
                final ClientUpdateServiceDataConvert convert = new ClientUpdateServiceDataConvert();



                if(jobTitle.getText().toString().equals(""))
                {
                    jobTitle.setHint("Title can not be empty!");
                    jobTitle.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Title can not be empty!");
                }
                else if(isBudgetKnown == true) {
                    if (!isBudget(budget.getText().toString())) {
                        budget.setHint("Budget allows numbers only!");
                        warning.setVisibility(View.VISIBLE);
                        warning.setText("Budget allows numbers only!");
                    }
                    else if(Double.parseDouble(budget.getText().toString())<0){
                        budget.setHint("Budget must bigger than zero!");
                        warning.setVisibility(View.VISIBLE);
                        warning.setText("Budget must bigger than zero");
                    }
                }
                else if(isBudgetKnown == true) {
                    if (Double.parseDouble(budget.getText().toString()) < 10) {
                        budget.setHint("Budget can not be lower than $10!");
                        warning.setVisibility(View.VISIBLE);
                        warning.setText("Budget can not be lower than $10!");
                    }
                }

                else if(street.getText().toString().equals(""))
                {
                    street.setHint("Address can not be empty!");
                    street.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Address can not be empty!");
                }

                else if(suburb.getText().toString().equals(""))
                {
                    suburb.setHint("Address can not be empty!");
                    suburb.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Address can not be empty!");
                }

                else if(city.getText().toString().equals(""))
                {
                    city.setHint("Address can not be empty!");
                    city.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Address can not be empty!");
                }

                else if(description.getText().toString().equals(""))
                {
                    description.setHint("Description can not be empty!");
                    description.setHintTextColor(Color.parseColor("#f3736f"));
                    warning.setVisibility(View.VISIBLE);
                    warning.setText("Description can not be empty!");
                }

                if(jobTitle.getText().toString().equals("")||street.getText().toString().equals("")||suburb.getText().toString().equals("") ||
                        city.getText().toString().equals("")||description.getText().toString().equals("") || (isBudgetKnown== true && !isBudget(budget.getText().toString())) ||
                        (isBudgetKnown== true && Double.parseDouble(budget.getText().toString())<0)  ){
                    return;
                }
                else {

                    DataSendController controller = new DataSendController(){
                        @Override
                        public void onResponse(Boolean result) {
                            super.onResponse(result);

                            DataGetIntController c = new DataGetIntController(){
                                @Override
                                public void onResponse(Integer result) {
                                    super.onResponse(result);

                                    DraftValues.title = "";
                                    DraftValues.category = "";
                                    DraftValues.budget = "";
                                    DraftValues.street = "";
                                    DraftValues.suburb = "";
                                    DraftValues.city = "";
                                    DraftValues.description = "";
                                    DraftValues.pic1 = null;
                                    DraftValues.pic2 = null;
                                    DraftValues.pic3 = null;
                                    DraftValues.pic4 = null;
                                    DraftValues.pic5 = null;

                                    //ValueMessager.isSettingDate = false;
                                    ValueMessager.counter = 0;


                                    myLoading.CloseLoadingDialog();

                                    Intent intent = new Intent(Client_EditOrder.this, Client_Confirm.class);
                                    startActivity(intent);

                                }
                            };
                            if (!isBudgetKnown) {
                                budget.setText("0.0");
                                model.setBudget(Double.parseDouble(budget.getText().toString()));
                            }
                            else if(isBudgetKnown) {
                                model.setBudget(Double.parseDouble(budget.getText().toString()));
                            }
                            model.setIsSecure(isFundAdded);
                            model.setDeposit(0.0);
                            model.setDescription(description.getText().toString());
                            model.setDueDate(taskDue.getSelectedItem().toString());
                            model.setTitle(jobTitle.getText().toString());
                            model.setServiceId(ValueMessengerTaskInfo.id);
                            model.setType(s.getSelectedItem().toString());
                            String data = convert.ModelToJson(model);
                            c.execute("http://para.co.nz/api/ClientJobService/updatejobservice", data, "PUT");
                            SystemClock.sleep(2000);
                        }
                    };

                    AddressModel addressModel=new AddressModel();
                    addressModel.setId(jsm.getServiceAddressId());
                    addressModel.setStreet(street.getText().toString());
                    addressModel.setSuburb(suburb.getText().toString());
                    addressModel.setCity(city.getText().toString());
                    String data2= AddressDataConvert.ModelToJson(addressModel);
                    controller.execute("http://para.co.nz/api/Address/UpdateAddress", data2, "PUT");

                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_edit_order);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        getData();
    }
}

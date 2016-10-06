package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_PlaceOrder extends AppCompatActivity {


    private TextView  error, date;
    private EditText jobTitle, budget, street, suburb, city, description;
    private ImageButton addPhoto, mainFooter, message, pending, profile;
    private ImageView photo1, photo2, photo3, photo4, photo5, picYes, picNo, cancel, save, info, send, priceYes, priceNo;
    private Spinner spinner;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    private LinearLayout yes, no, taskDue, notice, main, budgetMain, budgetContainer, budgetYes, budgetNo;
    private static final int REQUESTCODE=3;
    private boolean isFundAdded = true, isBudgetKnown = false;
    ImageUnity imageUnity = new ImageUnity();
    Context context = this;
    Loading_Dialog myLoading;


    public boolean isBudget (String number){

        Pattern p = Pattern.compile("\\d*\\.?\\d+");
        Matcher m = p.matcher(number);
        return m.matches();
    }

    public void sendImage(Bitmap newImg,int username) {

        SendImageController controller = new SendImageController() {
            @Override
            public void onResponse(Boolean result) {
                super.onResponse(result);
                if (result) {
                    System.out.println("yyyyyyyyyyyyyes");
                } else {
                    System.out.println("nnnnnnnnnnno");
                }
            }
        };

        controller.setBitmap(newImg);
        controller.execute("http://para.co.nz/api/JobService/UploadImage/"+username);

    }

    public String readData(String openFileName){
        try {

            FileInputStream fileInputStream = openFileInput(openFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            ValueMessager.readDataBuffer = bufferedReader.readLine();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ValueMessager.readDataBuffer.toString();
    }

    public void createDraft(){

        PlaceOrderServiceViewModel placeOrderServiceViewModel=new PlaceOrderServiceViewModel();
        PlaceServiceDataConvert placeServiceDataConvert = new PlaceServiceDataConvert();

        DataGetIntController c = new DataGetIntController(){
            @Override
            public void onResponse(Integer result) {
                super.onResponse(result);




                ValueMessengerTaskInfo.id = result;

                DataSendController status = new DataSendController(){
                    @Override
                    public void onResponse(Boolean result) {
                        super.onResponse(result);

                        System.out.println("wwwwwwwwww"+bitmapArray.size());

                        for(int i=0; i<bitmapArray.size(); i++){
                            sendImage(bitmapArray.get(i),ValueMessengerTaskInfo.id);
                        }

                        ValueMessager.selectedDay = "";
                        ValueMessager.selectedMonth = "";
                        ValueMessager.selectedYear = "";
                        ValueMessager.selectedDate = "";

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

                        myLoading.CloseLoadingDialog();

                        ValueMessager.isSettingDate = false;
                        ValueMessager.counter = 0;

                        startActivity(ValueMessager.intent);
                    }
                };

                JobServiceStatusViewModel model = new JobServiceStatusViewModel();

                model.setStatus(0);
                String data= new JobServiceStatusDataConvert().ModelToJson(model);
                status.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+ValueMessengerTaskInfo.id, data, "PUT");

            }
        };
            placeOrderServiceViewModel.setClientEmail(readData("userEmail"));
            placeOrderServiceViewModel.setTitle(jobTitle.getText().toString());
        if (isBudgetKnown == false) {
            budget.setText("-1.0");
            placeOrderServiceViewModel.setBudget(Double.parseDouble(budget.getText().toString()));
            }
            else {
            placeOrderServiceViewModel.setBudget(Double.parseDouble(budget.getText().toString()));
        }
        placeOrderServiceViewModel.setStreet(street.getText().toString());
        placeOrderServiceViewModel.setSuburb(suburb.getText().toString());
        placeOrderServiceViewModel.setCity(city.getText().toString());
        placeOrderServiceViewModel.setDescription(description.getText().toString());
        placeOrderServiceViewModel.setDueDate(ValueMessager.selectedDate);

            String data = placeServiceDataConvert.convertModelToJson(placeOrderServiceViewModel);
            myLoading.ShowLoadingDialog();
            c.execute("http://para.co.nz/api/ClientJobService/AddService", data, "POST");

    }

    public void btnFunction(){

        main = (LinearLayout) findViewById(R.id.linearLayout_container);
        cancel = (ImageView) findViewById(R.id.imageView_back);
        error = (TextView) findViewById(R.id.textView_PO_error);
        save = (ImageView) findViewById(R.id.imageView_ok);
        date = (TextView) findViewById(R.id.textView_placeOrder_date);
        jobTitle = (EditText) findViewById(R.id.editText_PO_workTitle);
        budget = (EditText) findViewById(R.id.editText_PO_budget);
        street = (EditText) findViewById(R.id.editText_PO_street);
        suburb = (EditText) findViewById(R.id.editText_PO_suburb);
        city = (EditText) findViewById(R.id.editText_PO_city);
        description = (EditText) findViewById(R.id.editText_PO_description);
        addPhoto = (ImageButton) findViewById(R.id.imageButton_addPhoto);
        mainFooter = (ImageButton) findViewById(R.id.imageButton_main_post);
        send = (ImageView) findViewById(R.id.imageView_send);
        message = (ImageButton) findViewById(R.id.imageButton_message_post);
        pending = (ImageButton) findViewById(R.id.imageButton_pending_post);
        profile = (ImageButton) findViewById(R.id.imageButton_setting_post);
        photo1 = (ImageView) findViewById(R.id.imageView_OP_photo1);
        photo2 = (ImageView) findViewById(R.id.imageView_OP_photo2);
        photo3 = (ImageView) findViewById(R.id.imageView_OP_photo3);
        photo4 = (ImageView) findViewById(R.id.imageView_OP_photo4);
        photo5 = (ImageView) findViewById(R.id.imageView_OP_photo5);
        picYes = (ImageView) findViewById(R.id.imageView_addFund_true);
        priceYes = (ImageView) findViewById(R.id.imageView_budgetYes);
        priceNo = (ImageView) findViewById(R.id.imageView_budgetNo);
        picNo = (ImageView) findViewById(R.id.imageView_addFund_false);
        info = (ImageView) findViewById(R.id.imageView_info);
        spinner = (Spinner) findViewById(R.id.spinner_OP);
        yes = (LinearLayout) findViewById(R.id.linearLayout_placeOrder_addFund_true);
        no = (LinearLayout) findViewById(R.id.linearLayout_placeOrder_addFund_false);
        taskDue = (LinearLayout) findViewById(R.id.linearLayout_taskDue);
        notice = (LinearLayout) findViewById(R.id.linearLayout_notice);
        budgetMain = (LinearLayout) findViewById(R.id.linearLayout_budget_container);
        budgetYes = (LinearLayout) findViewById(R.id.linearLayout_budgetYes);
        budgetNo = (LinearLayout) findViewById(R.id.linearLayout_budgetNo);
        budgetContainer = (LinearLayout) findViewById(R.id.linearLayout_editText_container);

        priceYes.setImageResource(R.drawable.circle);
        priceNo.setImageResource(R.drawable.dot);

        budgetMain.removeView(budgetContainer);

        save.setVisibility(View.INVISIBLE);
        main.removeView(notice);
        photo1.setVisibility(View.INVISIBLE);
        photo2.setVisibility(View.INVISIBLE);
        photo3.setVisibility(View.INVISIBLE);
        photo4.setVisibility(View.INVISIBLE);
        photo5.setVisibility(View.INVISIBLE);

        error.setVisibility(View.INVISIBLE);

        if(ValueMessager.isSettingDate) {
            if (DraftValues.title != "")
                jobTitle.setText(DraftValues.title);
            if (DraftValues.category != "")
                spinner.setPrompt(DraftValues.category);
            if (DraftValues.budget != "") {
                priceYes.setImageResource(R.drawable.dot);
                priceNo.setImageResource(R.drawable.circle);
                isBudgetKnown = true;
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
                photo1.setVisibility(View.VISIBLE);
                photo2.setImageBitmap(DraftValues.pic2);
                photo2.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic1);
                bitmapArray.add(DraftValues.pic2);
            }
            if (ValueMessager.counter == 3) {
                photo1.setImageBitmap(DraftValues.pic1);
                photo1.setVisibility(View.VISIBLE);
                photo2.setImageBitmap(DraftValues.pic2);
                photo2.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic1);
                bitmapArray.add(DraftValues.pic2);
                photo3.setImageBitmap(DraftValues.pic3);
                photo3.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic3);
            }
            if (ValueMessager.counter == 4) {
                photo1.setImageBitmap(DraftValues.pic1);
                photo1.setVisibility(View.VISIBLE);
                photo2.setImageBitmap(DraftValues.pic2);
                photo2.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic1);
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
                photo2.setImageBitmap(DraftValues.pic2);
                photo2.setVisibility(View.VISIBLE);
                bitmapArray.add(DraftValues.pic1);
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
        }


        if(ValueMessager.selectedDay != "")
        date.setText(ValueMessager.selectedDay+"/"+ValueMessager.selectedMonth+"/"+ValueMessager.selectedYear);

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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_PlaceOrder.this, Client_Incoming_Services.class);
                ValueMessager.intent = intent;
                createDraft();

            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadImage = new Intent(Client_PlaceOrder.this, SelectPicPopupWindowUploadImage.class);
                startActivityForResult(uploadImage,REQUESTCODE);

            }
        });

        mainFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_PlaceOrder.this, Client_Incoming_Services.class);
                ValueMessager.intent = intent;
                createDraft();
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_PlaceOrder.this, Client_Message.class);
                ValueMessager.intent = intent;
                createDraft();
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_PlaceOrder.this, Client_Pending.class);
                ValueMessager.intent = intent;
                createDraft();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client_PlaceOrder.this, Client_Setting.class);
                ValueMessager.intent = intent;
                createDraft();
            }
        });

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

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jobTitle.getText().equals("")){}
                else{
                    DraftValues.title = jobTitle.getText().toString();
                }

                DraftValues.category = spinner.getSelectedItem().toString();

                if(isBudgetKnown == true) {
                    if (budget.getText().toString() != "") {
                        DraftValues.budget = budget.getText().toString();
                    }
                }

                if(street.getText().equals("")){}
                else{
                    DraftValues.street = street.getText().toString();
                }

                if(suburb.getText().equals("")){}
                else{
                    DraftValues.suburb = suburb.getText().toString();
                }

                if(city.getText().equals("")){}
                else{
                    DraftValues.city = city.getText().toString();
                }

                if(description.getText().equals("")){}
                else{
                    DraftValues.description = description.getText().toString();
                }

                photo1.setDrawingCacheEnabled(true);
                photo2.setDrawingCacheEnabled(true);
                photo3.setDrawingCacheEnabled(true);
                photo4.setDrawingCacheEnabled(true);
                photo5.setDrawingCacheEnabled(true);

                if(photo1.getDrawingCache() != null) {
                    DraftValues.pic1 = photo1.getDrawingCache();

                }
                if(photo2.getDrawingCache() != null) {
                    DraftValues.pic2 = photo2.getDrawingCache();

                }
                if(photo3.getDrawingCache() != null) {
                    DraftValues.pic3 = photo3.getDrawingCache();

                }
                if(photo4.getDrawingCache() != null) {
                    DraftValues.pic4 = photo4.getDrawingCache();

                }
                if(photo5.getDrawingCache() != null) {
                    DraftValues.pic5 = photo5.getDrawingCache();
                }

                Intent intent = new Intent(Client_PlaceOrder.this, Client_SecuredPaymentHelp.class);
                startActivity(intent);
            }
        });

        taskDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(jobTitle.getText().equals("")){}
                    else{
                    DraftValues.title = jobTitle.getText().toString();
                }

                DraftValues.category = spinner.getSelectedItem().toString();

                if(isBudgetKnown == true) {
                    if (budget.getText().toString() != "") {
                        DraftValues.budget = budget.getText().toString();
                    }
                }

                if(street.getText().equals("")){}
                else{
                    DraftValues.street = street.getText().toString();
                }

                if(suburb.getText().equals("")){}
                else{
                    DraftValues.suburb = suburb.getText().toString();
                }

                if(city.getText().equals("")){}
                else{
                    DraftValues.city = city.getText().toString();
                }

                if(description.getText().equals("")){}
                else{
                    DraftValues.description = description.getText().toString();
                }

                photo1.setDrawingCacheEnabled(true);
                photo2.setDrawingCacheEnabled(true);
                photo3.setDrawingCacheEnabled(true);
                photo4.setDrawingCacheEnabled(true);
                photo5.setDrawingCacheEnabled(true);




                if(photo1.getDrawingCache() != null) {
                    DraftValues.pic1 = photo1.getDrawingCache();
                }
                if(photo2.getDrawingCache() != null) {
                    DraftValues.pic2 = photo2.getDrawingCache();

                }
                if(photo3.getDrawingCache() != null) {
                    DraftValues.pic3 = photo3.getDrawingCache();

                }
                if(photo4.getDrawingCache() != null) {
                    DraftValues.pic4 = photo4.getDrawingCache();

                }
                if(photo5.getDrawingCache() != null) {
                    DraftValues.pic5 = photo5.getDrawingCache();
                }

                ValueMessager.lastPageCalender = "PlaceOrder";
                ValueMessager.isSettingDate = true;

                Intent intent = new Intent(Client_PlaceOrder.this, Client_Calender.class);
                startActivity(intent);

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlaceOrderServiceViewModel placeOrderServiceViewModel=new PlaceOrderServiceViewModel();
                PlaceServiceDataConvert placeServiceDataConvert = new PlaceServiceDataConvert();

                DataGetIntController c = new DataGetIntController(){
                    @Override
                    public void onResponse(Integer result) {
                        super.onResponse(result);


                        ValueMessengerTaskInfo.id = result;

                        DataSendController status = new DataSendController(){
                            @Override
                            public void onResponse(Boolean result) {
                                super.onResponse(result);

                                for(int i=0; i<bitmapArray.size(); i++){
                                    sendImage(bitmapArray.get(i),ValueMessengerTaskInfo.id);
                                }

                                myLoading.CloseLoadingDialog();

                                ValueMessager.selectedDay = "";
                                ValueMessager.selectedMonth = "";
                                ValueMessager.selectedYear = "";
                                ValueMessager.selectedDate = "";

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

                                ValueMessager.isSettingDate =false;
                                ValueMessager.counter = 0;

                                Intent nextPage_History = new Intent(Client_PlaceOrder.this, Client_Pending.class);
                                startActivity(nextPage_History);
                            }
                        };

                        JobServiceStatusViewModel model = new JobServiceStatusViewModel();

                        model.setStatus(1);
                        String data= new JobServiceStatusDataConvert().ModelToJson(model);
                        status.execute("http://para.co.nz/api/JobService/UpdateServiceStatus/"+ValueMessengerTaskInfo.id, data, "PUT");

                    }
                };

                if(date.getText() == ""){
                    error.setVisibility(View.VISIBLE);
                    error.setText("Date must be set!");
                }

                if(jobTitle.getText().toString().equals(""))
                {
                    jobTitle.setHint("Title can not be empty!");
                    jobTitle.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Title can not be empty!");
                }
                else if(isBudgetKnown == true) {
                    if (!isBudget(budget.getText().toString())) {
                        budget.setHint("Budget allows numbers only!");
                        error.setVisibility(View.VISIBLE);
                        error.setText("Budget allows numbers only!");
                    }
                }
                else if(isBudgetKnown == true) {
                    if (Double.parseDouble(budget.getText().toString()) < 10) {
                        budget.setHint("Budget can not be lower than $10!");
                        error.setVisibility(View.VISIBLE);
                        error.setText("Budget can not be lower than $10!");
                    }
                }
                else if(street.getText().toString().equals(""))
                {
                    street.setHint("Address can not be empty!");
                    street.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Address can not be empty!");
                }

                else if(suburb.getText().toString().equals(""))
                {
                    suburb.setHint("Address can not be empty!");
                    suburb.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Address can not be empty!");
                }

                else if(city.getText().toString().equals(""))
                {
                    city.setHint("Address can not be empty!");
                    city.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Address can not be empty!");
                }

                else if(description.getText().toString().equals(""))
                {
                    description.setHint("Description can not be empty!");
                    description.setHintTextColor(Color.parseColor("#f3736f"));
                    error.setVisibility(View.VISIBLE);
                    error.setText("Description can not be empty!");
                }

                if(jobTitle.getText().toString().equals("")||street.getText().toString().equals("")||suburb.getText().toString().equals("") || date.getText() ==""
                        ||city.getText().toString().equals("")||description.getText().toString().equals("") || (isBudgetKnown== true && !isBudget(budget.getText().toString())) ||
                        (isBudgetKnown== true && Double.parseDouble(budget.getText().toString())<0)  ){
                    return;
                }
                else {
                    placeOrderServiceViewModel.setClientEmail(readData("userEmail"));
                    placeOrderServiceViewModel.setTitle(jobTitle.getText().toString());
                    placeOrderServiceViewModel.setType(spinner.getSelectedItem().toString());
                    if (!isBudgetKnown) {
                        budget.setText("-1.0");
                    }
                    else if(isBudgetKnown) {
                        placeOrderServiceViewModel.setBudget(Double.parseDouble(budget.getText().toString()));
                    }
                    placeOrderServiceViewModel.setStreet(street.getText().toString());
                    placeOrderServiceViewModel.setSuburb(suburb.getText().toString());
                    placeOrderServiceViewModel.setCity(city.getText().toString());
                    placeOrderServiceViewModel.setDescription(description.getText().toString());
                    placeOrderServiceViewModel.setIsSecure(isFundAdded);
                    placeOrderServiceViewModel.setDueDate(ValueMessager.selectedDate);

                    String data = placeServiceDataConvert.convertModelToJson(placeOrderServiceViewModel);
                    myLoading.ShowLoadingDialog();
                    c.execute("http://para.co.nz/api/ClientJobService/AddService", data, "POST");
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                String returnedResult = data.getStringExtra("test_String");
                Bitmap bitmapFromPW = (Bitmap) data.getParcelableExtra("BitmapImage");
                System.out.println("here: "+ bitmapFromPW);

                UploadPhoto(bitmapFromPW);
            }
        }
    }

    private void UploadPhoto(Bitmap bitmapFromPW)
    {

        ValueMessager.counter = ValueMessager.counter +1;

        if(photo1.getVisibility() == View.INVISIBLE)
        {
            photo1.setImageBitmap(bitmapFromPW);
            photo1.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW) ;
        }
        else if(photo1.getVisibility() == View.VISIBLE && photo2.getVisibility() == View.INVISIBLE )
        {
            photo2.setImageBitmap(bitmapFromPW);
            photo2.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW);
        }
        else if(photo2.getVisibility() == View.VISIBLE && photo3.getVisibility() == View.INVISIBLE  )
        {
            photo3.setImageBitmap(bitmapFromPW);
            photo3.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW);
        }
        else if(photo3.getVisibility() == View.VISIBLE && photo4.getVisibility() == View.INVISIBLE  )
        {
            photo4.setImageBitmap(bitmapFromPW);
            photo4.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW);
        }
        else if(photo4.getVisibility() == View.VISIBLE && photo5.getVisibility() == View.INVISIBLE  )
        {
            photo5.setImageBitmap(bitmapFromPW);
            photo5.setVisibility(View.VISIBLE);
            bitmapArray.add(bitmapFromPW);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_place_order);

        myLoading=new Loading_Dialog();
        myLoading.getContext(this);

        btnFunction();

    }
}

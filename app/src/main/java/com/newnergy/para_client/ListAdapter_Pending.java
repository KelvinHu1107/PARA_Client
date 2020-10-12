package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newnergy.para_client.Image_package.ImageUnity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kelvin on 2016/6/20.
 */
public class ListAdapter_Pending extends ArrayAdapter<String> {
    // declaration
    CharSequence[] name;
    CharSequence[] createDate;
    CharSequence[] providerPhoto;
    Context c;
    CharSequence[] type,getTitle = {};
    Double[] budget = {};
    int[] serviceId = {};
    Integer[] providerId, status = {};
    LayoutInflater inflaterPending;
    ImageUnity imageUnity = new ImageUnity();

    public ListAdapter_Pending(Context context, String[] objectName, CharSequence[] createDate, int[] serviceId, Integer[] providerId, CharSequence[] providerPhoto,
                               CharSequence[] getTitle, Integer[] status, Double[] budget) {

        super(context, R.layout.pending_list_sample720x1080, objectName);

        this.c = context;
        this.serviceId = serviceId;
        this.createDate = createDate;
        this.providerId = providerId;
        this.providerPhoto = providerPhoto;
        this.getTitle = getTitle;
        this.status = status;
        this.budget = budget;
    }

    public class ViewHolder {
        TextView nameTv, textTv, timeTv, indication;
        ImageView imgIv;
        LinearLayout[] linearLayout = new LinearLayout[serviceId.length];
    }

//    public void getImageData(String profilePhotoUrl, final ImageView imageView) {
//
//        GetImageController controller = new GetImageController() {
//            @Override
//            public void onResponse(Bitmap mBitmap) {
//                super.onResponse(mBitmap);
//                if (mBitmap == null) {
//                    return;
//                }
//                mBitmap = imageUnity.toRoundBitmap(mBitmap);
//                imageView.setImageBitmap(mBitmap);
//
//                mBitmap.recycle();
//
//            }
//        };
//        controller.execute("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+ profilePhotoUrl, "","POST");
//
//
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflaterPending = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(ValueMessager.resolution1080x720) {
                convertView = inflaterPending.inflate(R.layout.pending_list_sample720x1080, null);
            }
            else if(ValueMessager.resolution800x480){
                convertView = inflaterPending.inflate(R.layout.pending_list_sample480x800, null);
            }else{
                convertView = inflaterPending.inflate(R.layout.pending_list_sample720x1080, null);
            }
        }

        final ViewHolder holderPending = new ViewHolder();
        holderPending.nameTv = (TextView) convertView.findViewById(R.id.textView_pending_name);
        holderPending.imgIv = (CircleImageView) convertView.findViewById(R.id.imageView_pic_pending);
        holderPending.textTv = (TextView) convertView.findViewById(R.id.textView_pending_text);
        holderPending.timeTv = (TextView) convertView.findViewById(R.id.textView_pending_time);
        holderPending.linearLayout[position] = (LinearLayout) convertView.findViewById(R.id.linearLayout_pending);
        holderPending.indication = (TextView) convertView.findViewById(R.id.textView_indication);

        if(isNumeric(ValueMessager.listPendingServiceId.toString())) {
            for (int i = 0; i < ValueMessager.listPendingServiceId.size(); i++) {
                if (!ValueMessager.listPendingServiceId.get(i).equals("") && !ValueMessager.listPendingServiceId.get(i).equals(null)) {
                    if (serviceId[position] == Integer.parseInt(ValueMessager.listPendingServiceId.get(i))) {
                        holderPending.indication.setVisibility(View.VISIBLE);

                    } else {
                        holderPending.indication.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        //format date string
        Calendar currentTime = Calendar.getInstance();
        Long diff, diffDayLong;
        int diffDay, day = 24*60*60*1000, hour = 60*60*1000;
        Date date;
        Date currentDate;
        String calculatedDate = null;
        currentDate = currentTime.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat finalFormat = new SimpleDateFormat("dd-MMMM HH:mm");
        try {
            date = format.parse(createDate[position].toString());
            calculatedDate = finalFormat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //每一個position assign item
        if(providerPhoto[position].toString().equals("") || providerId[position]<0){
            holderPending.imgIv.setImageResource(R.drawable.client_photo_round);
        }
        else
        {
            imageUnity.setImage(c, holderPending.imgIv, "http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+providerPhoto[position].toString());
        }

        holderPending.nameTv.setText(getTitle[position]);

        if(status[position] == 0) {
            holderPending.timeTv.setTextColor(Color.parseColor("#009900"));
            holderPending.timeTv.setText("Draft");
        }
        else if (status[position] == 1) {
            holderPending.timeTv.setText("Open");
            holderPending.timeTv.setTextColor(Color.parseColor("#009900"));
        }
        else if (status[position] == 2) {
            holderPending.timeTv.setText("Selecting");
            holderPending.timeTv.setTextColor(Color.parseColor("#009900"));
        }
        else if (status[position] == 3) {
            holderPending.timeTv.setText("Waiting");
            holderPending.timeTv.setTextColor(Color.parseColor("#666666"));
        }

        else if (status[position] == 4) {
            holderPending.timeTv.setText("Assigned");
            holderPending.timeTv.setTextColor(Color.parseColor("#666666"));
        }

        else if (status[position] == 5) {
            holderPending.timeTv.setText("Assigned");
            holderPending.timeTv.setTextColor(Color.parseColor("#666666"));
        }

        else if (status[position] == 6) {
            holderPending.timeTv.setText("Waiting");
            holderPending.timeTv.setTextColor(Color.parseColor("#666666"));
        }

        else if (status[position] == 7) {
            holderPending.timeTv.setText("Rejected");
            holderPending.timeTv.setTextColor(Color.parseColor("#f3736f"));
        }
        else{
            holderPending.timeTv.setText("Complete");
            holderPending.timeTv.setTextColor(Color.parseColor("#009900"));
        }

        if(budget[position]>0)
        holderPending.textTv.setText("$"+budget[position].toString());
        else if(budget[position]<=0.0) {
            holderPending.textTv.setText("Tender");
        }

        holderPending.imgIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessengerTaskInfo.id = serviceId[position];
                ValueMessengerTaskInfo.providerId = providerId[position];

                for(int i=0; i<ValueMessager.listPendingServiceId.size(); i++){
                    if(serviceId[position] == Integer.parseInt(ValueMessager.listPendingServiceId.get(i))){
                        ValueMessager.listPendingServiceId.clear();
                    }
                }

                if(status[position] == 0){
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent intent = new Intent(c, Client_EditDraft.class);
                    c.startActivity(intent);
                }else if(status[position] == 1 || status[position] == 2) {
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_Confirm.class);
                    c.startActivity(nextPage_Confirm);
                }
                else if(status[position] > 2 && status[position]<8){
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_DealingBill.class);
                    c.startActivity(nextPage_Confirm);
                }
                else {
                    ValueMessager.lastPageConfirm2 = 1;

                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_Confirm2.class);
                    c.startActivity(nextPage_Confirm);
                }
                }
        });

        holderPending.linearLayout[position].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessengerTaskInfo.id = serviceId[position];
                ValueMessengerTaskInfo.providerId = providerId[position];

                if(isNumeric(ValueMessager.listPendingServiceId.toString())) {
                    for (int i = 0; i < ValueMessager.listPendingServiceId.size(); i++) {
                        if (serviceId[position] == Integer.parseInt(ValueMessager.listPendingServiceId.get(i))) {
                            ValueMessager.listPendingServiceId.clear();
                        }
                    }
                }

                if(status[position] == 0){
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent intent = new Intent(c, Client_EditDraft.class);
                    c.startActivity(intent);
                }else if(status[position] == 1 || status[position] == 2) {
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_Confirm.class);
                    c.startActivity(nextPage_Confirm);
                }
                else if(status[position] > 2 && status[position]<8){
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_DealingBill.class);
                    c.startActivity(nextPage_Confirm);
                }
                else {
                    ValueMessager.lastPageConfirm2 = 1;

                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_Confirm2.class);
                    c.startActivity(nextPage_Confirm);
                }
            }
        });

        holderPending.timeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for(int i=0; i<ValueMessager.listPendingServiceId.size(); i++){
                    if(serviceId[position] == Integer.parseInt(ValueMessager.listPendingServiceId.get(i))){
                        ValueMessager.listPendingServiceId.clear();
                    }
                }

                ValueMessengerTaskInfo.id = serviceId[position];
                ValueMessengerTaskInfo.providerId = providerId[position];

                if(status[position] == 0){
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent intent = new Intent(c, Client_EditDraft.class);
                    c.startActivity(intent);
                }else if(status[position] == 1 || status[position] == 2) {
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_Confirm.class);
                    c.startActivity(nextPage_Confirm);
                }
                else if(status[position] > 2 && status[position]<8){
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_DealingBill.class);
                    c.startActivity(nextPage_Confirm);
                }
                else {
                    ValueMessager.lastPageConfirm2 = 1;
                    ValueMessager.notificationPending =0;
                    ValueMessager.is_pending = false;
                    ValueMessager.listPendingId.clear();
                    Intent nextPage_Confirm = new Intent(c, Client_Confirm2.class);
                    c.startActivity(nextPage_Confirm);
                }
            }
        });
        return convertView;
    }

    public boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
    }

}
package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kelvin on 2016/7/26.
 */
public class ListAdapter_History extends ArrayAdapter<String> {
    // declaration

    CharSequence[] createDate;
    int[]  serviceId;
    CharSequence[] providerPhoto;
    CharSequence[] getTitle;
    Integer[] providerId, status;
    Double[] budget;
    Context c;
    LayoutInflater inflaterPending;
    private ProviderProfileViewModel list;
    ImageUnity imageUnity = new ImageUnity();

    public ListAdapter_History(Context context, String[] objectName, CharSequence[] createDate, int[] serviceId, Integer[] providerId, CharSequence[] providerPhoto,
                               CharSequence[] getTitle, Integer[] status, Double[] budget) {

        super(context, R.layout.history_list_sample, objectName);

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
        TextView nameTv, textTv, timeTv, budgetTv;
        ImageView imgIv;
        LinearLayout linearLayout;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            inflaterPending = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(ValueMessager.resolution1080x720)
            convertView = inflaterPending.inflate(R.layout.history_list_sample, null);
            else if(ValueMessager.resolution800x480)
                convertView = inflaterPending.inflate(R.layout.history_list_sample, null);
        }

        //assign id to items , convert view
        final ViewHolder holder = new ViewHolder();
        holder.nameTv = (TextView) convertView.findViewById(R.id.textView_name_history);
        holder.imgIv = (ImageView) convertView.findViewById(R.id.imageView_pic_history);
        holder.textTv = (TextView) convertView.findViewById(R.id.textView_title_history);
        holder.timeTv = (TextView) convertView.findViewById(R.id.textView_date_history);
        holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout_history);
        holder.budgetTv = (TextView) convertView.findViewById(R.id.textView_price_history);

        Calendar currentTime = Calendar.getInstance();
        Long diff, diffDayLong;
        int diffDay, day = 24*60*60*1000, hour = 60*60*1000;
        Date date;
        Date currentDate;
        String calculatedDate = null;
        currentDate = currentTime.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat finalFormat = new SimpleDateFormat("dd-MMMM");
        try {
            date = format.parse(createDate[position].toString());
            calculatedDate = finalFormat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        imageUnity.setImage(c, holder.imgIv, "http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/"+providerPhoto[position].toString());

        holder.timeTv.setText(calculatedDate);

        DataTransmitController controller =new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);

                list = ProviderProfileDataConvert.convertJsonToModel(result);

                holder.budgetTv.setText("$ "+budget[position].toString());
                holder.textTv.setText(getTitle[position]);
                holder.nameTv.setText(list.getFirstName()+" "+list.getLastName());

            }
        };
        controller.execute("http://para.co.nz/api/ProviderProfile/GetProviderDetailById/"+providerId[position],"","GET");



        holder.imgIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessengerTaskInfo.id = serviceId[position];
                ValueMessengerTaskInfo.providerId = providerId[position];
                ValueMessager.lastPageConfirm2 = 0;

                Intent nextPage_Confirm = new Intent(c,Client_Confirm2.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessengerTaskInfo.id = serviceId[position];
                ValueMessengerTaskInfo.providerId = providerId[position];
                ValueMessager.lastPageConfirm2 = 0;

                Intent nextPage_Confirm = new Intent(c,Client_Confirm2.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holder.timeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ValueMessengerTaskInfo.id = serviceId[position];
                ValueMessengerTaskInfo.providerId = providerId[position];
                ValueMessager.lastPageConfirm2 = 0;

                Intent nextPage_Confirm = new Intent(c,Client_Confirm2.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        return convertView;
    }
}

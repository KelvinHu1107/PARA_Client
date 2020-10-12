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

import com.newnergy.para_client.Image_package.ImageUnity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kelvin on 2016/7/26.
 */
public class ListAdapter_History extends ArrayAdapter<String> {
    // declaration

    CharSequence[] completeDate;
    int[]  serviceId;
    CharSequence[] providerPhoto;
    CharSequence[] getTitle;
    String[] address;
    Integer[] providerId, status;
    Double[] budget;
    Context c;
    LayoutInflater inflaterPending;
    private ProviderProfileViewModel list;
    ImageUnity imageUnity = new ImageUnity();

    public ListAdapter_History(Context context, String[] objectName, CharSequence[] completeDate, int[] serviceId, Integer[] providerId, CharSequence[] providerPhoto,
                               CharSequence[] getTitle, String[] address, Double[] budget) {

        super(context, R.layout.history_list_sample, objectName);

        this.c = context;
        this.serviceId = serviceId;
        this.completeDate = completeDate;
        this.providerId = providerId;
        this.providerPhoto = providerPhoto;
        this.getTitle = getTitle;
        this.address = address;
        this.budget = budget;
    }

    public class ViewHolder {
        TextView[] nameTv = new TextView[serviceId.length], textTv = new TextView[serviceId.length], timeTv = new TextView[serviceId.length], budgetTv = new TextView[serviceId.length];
        ImageView[] imgIv = new ImageView[serviceId.length];
        LinearLayout[] linearLayout = new LinearLayout[serviceId.length];

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            inflaterPending = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(ValueMessager.resolution1080x720)
            convertView = inflaterPending.inflate(R.layout.history_list_sample, null);
            else if(ValueMessager.resolution800x480)
                convertView = inflaterPending.inflate(R.layout.history_list_sample, null);
            else
                convertView = inflaterPending.inflate(R.layout.history_list_sample, null);
        }

        //assign id to items , convert view
        final ViewHolder holder = new ViewHolder();
        holder.nameTv[position] = (TextView) convertView.findViewById(R.id.textView_name_history);
        holder.imgIv[position] = (ImageView) convertView.findViewById(R.id.imageView_pic_history);
        holder.textTv[position] = (TextView) convertView.findViewById(R.id.textView_title_history);
        holder.timeTv[position] = (TextView) convertView.findViewById(R.id.textView_date_history);
        holder.linearLayout[position] = (LinearLayout) convertView.findViewById(R.id.linearLayout_history);
        holder.budgetTv[position] = (TextView) convertView.findViewById(R.id.textView_price_history);

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
            date = format.parse(completeDate[position].toString());
            calculatedDate = finalFormat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(providerPhoto[position].equals("")){
            holder.imgIv[position].setImageResource(R.drawable.client_photo_round);
        }else {
            imageUnity.setImage(c, holder.imgIv[position], "http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + providerPhoto[position].toString());
        }

        ValueMessengerTaskInfo.id = serviceId[position];
        ValueMessengerTaskInfo.providerId = providerId[position];
        ValueMessengerTaskInfo.providerProfilePicUrl = "http://para.co.nz/api/ProviderProfile/GetProviderDetailById/"+providerId[position].toString();
        ValueMessager.lastPageConfirm2 = 0;

        //kelvinkelvin

        holder.timeTv[position].setTag(calculatedDate);
        String tag = (String) holder.timeTv[position].getTag();
        if(tag!=null&&tag.equals(calculatedDate))
        holder.timeTv[position].setText(calculatedDate);


        holder.budgetTv[position].setTag(budget[position].toString());
        String tag2 = (String) holder.budgetTv[position].getTag();
        if(tag2!=null&&tag2.equals(budget[position].toString()))
        holder.budgetTv[position].setText("$ "+budget[position].toString());

        holder.textTv[position].setTag(address[position].toString());
        String tag3 = (String) holder.textTv[position].getTag();
        if(tag3!=null&&tag3.equals(address[position].toString()))
        holder.textTv[position].setText(address[position]);

        holder.nameTv[position].setTag(getTitle[position].toString());
        String tag4 = (String) holder.nameTv[position].getTag();
        if(tag4!=null&&tag4.equals(getTitle[position].toString()))
        holder.nameTv[position].setText(getTitle[position]);

        //kelvinkelvin solve list shifting problem


        holder.imgIv[position].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent nextPage_Confirm = new Intent(c,Client_Confirm2.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holder.linearLayout[position].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent nextPage_Confirm = new Intent(c,Client_Confirm2.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holder.timeTv[position].setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent nextPage_Confirm = new Intent(c,Client_Confirm2.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        return convertView;
    }
}
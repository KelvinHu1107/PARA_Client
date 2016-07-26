package com.newnergy.para_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
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
    CharSequence[] name;
    CharSequence[] startDate;
    int[] clientId, id;
    CharSequence[] clientDueDate;
    CharSequence[] jobSurburb;
    CharSequence[] profilePhoto;
    CharSequence[] getTitle;
    Context c;
    LayoutInflater inflaterPending;





    public ListAdapter_History(Context context, String[] objectName, CharSequence[] name, CharSequence[] startDate, int[] clientId, CharSequence[] clientDueDate,
                               int[] id, CharSequence[] jobSurburb, CharSequence[] profilePhoto,CharSequence[] getTitle) {

        super(context, R.layout.pending_list_sample, objectName);

        this.c = context;
        this.name = name;
        this.startDate = startDate;
        this.clientId = clientId;
        this.clientDueDate = clientDueDate;
        this.id = id;
        this.jobSurburb = jobSurburb;
        this.profilePhoto = profilePhoto;
        this.getTitle = getTitle;
    }

    public class ViewHolder {
        TextView nameTv, textTv, timeTv;
        ImageView imgIv;
        HorizontalScrollView scrollViewTv;
        LinearLayout linearLayout;

    }



    public void getImageData(String profilePhotoUrl, final ImageView imageView, CharSequence picturePath) {

        GetImageController controller = new GetImageController() {
            @Override
            public void onResponse(Bitmap mBitmap) {
                super.onResponse(mBitmap);
                if (mBitmap == null) {
                }
                imageView.setImageBitmap(mBitmap);
            }
        };
        controller.execute("http://para.co.nz/api/ClientProfile/GetClientProfileImage/"+ profilePhotoUrl, "","POST");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            inflaterPending = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflaterPending.inflate(R.layout.pending_list_sample, null);
        }

        //assign id to items , convert view
        final ViewHolder holderPending = new ViewHolder();
        holderPending.nameTv = (TextView) convertView.findViewById(R.id.textView_pending_name);
        holderPending.imgIv = (ImageView) convertView.findViewById(R.id.imageView_pic_pending);
        holderPending.textTv = (TextView) convertView.findViewById(R.id.textView_pending_text);
        holderPending.timeTv = (TextView) convertView.findViewById(R.id.textView_pending_time);
        holderPending.linearLayout = (LinearLayout) convertView.findViewById(R.id.linearLayout_pending);
        holderPending.scrollViewTv = (HorizontalScrollView) convertView.findViewById(R.id.horizontalScrollView_pending);


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
            date = format.parse(startDate[position].toString());
            calculatedDate = finalFormat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //format date string

        //每一個position assign item
        getImageData(profilePhoto[position].toString(),holderPending.imgIv, profilePhoto[position]);
        holderPending.nameTv.setText(name[position]);
        holderPending.timeTv.setText(calculatedDate);
        //       holderMessage.textTv.setText(text[position]);

        // <Scrollable list layout>start

        //disable drag function
        holderPending.scrollViewTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        //disable drag function

        holderPending.imgIv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent nextPage_Confirm = new Intent(c,Client_HistoryFurther.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holderPending.linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent nextPage_Confirm = new Intent(c,Client_HistoryFurther.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        holderPending.timeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent nextPage_Confirm = new Intent(c,Client_HistoryFurther.class);
                c.startActivity(nextPage_Confirm);
            }
        });

        // <Scrollable list layout>end
        return convertView;
    }
}

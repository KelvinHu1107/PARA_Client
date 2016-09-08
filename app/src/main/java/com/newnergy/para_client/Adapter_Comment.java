package com.newnergy.para_client;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by Kelvin on 2016/7/29.
 */
public class Adapter_Comment extends ArrayAdapter<String> {

    // declaration

    private static final int PERMISSION_REQUEST = 100;
    CharSequence[] name = {};
    CharSequence[] createDate = {};
    int[] clientId = {};
    CharSequence[] clientDueDate = {};
    int[] id = {};
    int[] ratingNumber = {};
    CharSequence[] jobSurburb = {};
    CharSequence[] profilePhoto = {};
    CharSequence[] descripiton = {};
    CharSequence[] userName = {};
    CharSequence[] commentTitle = {};
    Context c;
    LayoutInflater inflater;
    RoundImage roundImage;




    public Adapter_Comment(Context context, String[] objectName, CharSequence[] name, CharSequence[] createDate, CharSequence[] commentTitle, CharSequence[] description) {
        super(context, R.layout.client_incoming_services_list_sample720x1080, objectName);

        this.c = context;
        this.name = name;
        this.createDate = createDate;
        this.commentTitle = commentTitle;
        this.descripiton = description;
    }

    private boolean checkCallPermission(){
        String permission = "android.permission.CALL_PHONE";
        int res = c.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
        //return true;
    }



    public class ViewHolder {
        TextView nameTv,createDateTv,jobSurburbTv;
        ImageView img;
        HorizontalScrollView scrollViewTv;
        LinearLayout linearLayoutTv;
        ImageButton rollbackTv,furtherInfoBtn,dialButton;
        ListView listViewTv;
        RatingBar ratingBarRb;
    }
// class list裡面要加甚麼就加


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.client_incoming_services_list_sample720x1080, null);
        }
        //assign id to items , convert view
        final ViewHolder holder = new ViewHolder();
        holder.nameTv = (TextView) convertView.findViewById(R.id.textView_name);
        holder.jobSurburbTv = (TextView) convertView.findViewById(R.id.textView_location);
        holder.img = (ImageView) convertView.findViewById(R.id.imageView_pic);
        holder.scrollViewTv = (HorizontalScrollView) convertView.findViewById(R.id.horizontalScrollView);
        holder.linearLayoutTv = (LinearLayout) convertView.findViewById(R.id.firstSection);
        holder.rollbackTv = (ImageButton) convertView.findViewById(R.id.imageButton_rollback);
        holder.listViewTv = (ListView) convertView.findViewById(R.id.listView2);
        holder.createDateTv = (TextView) convertView.findViewById(R.id.textView_status);
        holder.ratingBarRb = (RatingBar) convertView.findViewById(R.id.rtbProductRating);
        holder.furtherInfoBtn = (ImageButton) convertView.findViewById(R.id.imageButton_furtherInfo);
        holder.dialButton = (ImageButton) convertView.findViewById(R.id.imageButton_dial_incomingServices);
        //assign id to items , convert view

        //format date string
//        Calendar currentTime = Calendar.getInstance();
//        Long diff, diffDayLong;
//        int diffDay, day = 24*60*60*1000, hour = 60*60*1000;
//        Date date;
//        Date currentDate;
//        String calculatedDate = null;
//        currentDate = currentTime.getTime();
//
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        SimpleDateFormat finalFormat = new SimpleDateFormat("dd-MMMM");
//        try {
////            date = format.parse(createDate[position].toString());
//            diff = (currentDate.getTime() - currentDate.getTime()% day) - date.getTime();
//            diffDayLong = diff / (60 * 60 * 1000);
//            diffDay = Math.round(diffDayLong);
//            if(diffDay < 0)
//                        calculatedDate = "Listed Today";
//                else if(diffDay <24)
//                        calculatedDate = "Yesterday";
//                else if(diffDay <48)
//                        calculatedDate = "2 Days Ago";
//                else if(diffDay <72)
//                        calculatedDate = "3 Days Ago";
//                else if(diffDay <96)
//                        calculatedDate = "4 Days Ago";
//                else if(diffDay <120)
//                        calculatedDate = "5 Days Ago";
//                else if(diffDay <144)
//                        calculatedDate = "6 Days Ago";
//                else
//                calculatedDate = finalFormat.format(date);
//
//
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        //format date string

        //每一個position assign item

        holder.nameTv.setText(name[position]);
        holder.jobSurburbTv.setText(jobSurburb[position]);
//        holder.createDateTv.setText(calculatedDate);

        //每一個position assign item

        //scroll bar btn functions






        holder.dialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkCallPermission()) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);

                    c.startActivity(callIntent);
                }
                else {
                    requestPermissions((Activity) c,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST);
                }

            }
        });

        holder.furtherInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //passing ID from incoming jobs to further Info
                ValueMessagerFurtherInfo.userName = userName[position];
                ValueMessagerFurtherInfo.lastPage = 0; // last page = incoming jobs

                Intent nextPage_FurtherInfo = new Intent(c,Client_Further_Info.class);
                c.startActivity(nextPage_FurtherInfo);
            }
        });
        //scroll bar btn functions

        // <Scrollable list layout>start
        holder.nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.scrollViewTv.smoothScrollTo(850, 0);
            }
        });

        holder.jobSurburbTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.scrollViewTv.smoothScrollTo(850, 0);
            }
        });

        holder.createDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.scrollViewTv.smoothScrollTo(850, 0);
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.scrollViewTv.smoothScrollTo(850, 0);
            }
        });

        holder.scrollViewTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        holder.ratingBarRb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        holder.rollbackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.scrollViewTv.smoothScrollTo(0, 0);
            }
        });

        // <Scrollable list layout>end
        return convertView;
    }


}

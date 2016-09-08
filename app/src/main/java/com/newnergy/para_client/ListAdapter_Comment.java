package com.newnergy.para_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kelvin on 2016/8/18.
 */
public class ListAdapter_Comment extends ArrayAdapter<String> {

    CharSequence[] firstName;
    CharSequence[] lastName;
    CharSequence[] comment;
    CharSequence[] createDate;
    Double[] rating;
    Context c;
    LayoutInflater inflaterComment;


    public ListAdapter_Comment(Context context, String[] objectName, CharSequence[] firstName, CharSequence[] lastName,CharSequence[] comment,
                               CharSequence[] createDate,Double[] rating) {

        super(context, R.layout.client_comment_list_sample720x1080, objectName);

        this.c = context;
        this.firstName = firstName;
        this.lastName = lastName;
        this.comment = comment;
        this.createDate = createDate;
        this.rating = rating;
    }

    public class ViewHolder {
        TextView nameTv, createDateTv, comment;
        RatingBar ratingBarRb;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Date dateData;

        if (convertView == null) {
            inflaterComment = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflaterComment.inflate(R.layout.client_comment_list_sample720x1080, null);
        }

        //assign id to items , convert view
        final ViewHolder holder = new ViewHolder();
        holder.nameTv = (TextView) convertView.findViewById(R.id.textView_clientName);
        holder.createDateTv = (TextView) convertView.findViewById(R.id.textView_comment_createDate);
        holder.ratingBarRb = (RatingBar) convertView.findViewById(R.id.comment_listSample_ratingBar);
        holder.comment = (TextView) convertView.findViewById(R.id.textView_comment_comment);

        String calculatedDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat finalFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dateData = format.parse(createDate[position].toString());
            calculatedDate = finalFormat.format(dateData);
            holder.createDateTv.setText(calculatedDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        holder.ratingBarRb.setRating(Float.parseFloat(rating[position].toString()));

        holder.ratingBarRb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        holder.nameTv.setText(firstName[position]+" "+lastName[position]);

        holder.comment.setText(comment[position]);

        // <Scrollable list layout>end
        return convertView;
    }
}

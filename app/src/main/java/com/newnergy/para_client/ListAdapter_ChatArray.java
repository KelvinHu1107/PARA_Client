package com.newnergy.para_client;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kelvin on 2016/8/25.
 */
public class ListAdapter_ChatArray extends ArrayAdapter<ChatMessage> {

    private TextView chatText, time_left, time_right;
    private List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
    private LinearLayout layout, container;
    private ImageView profilePic_left,profilePic_right, picture;
    private Bitmap providerBitmap, clientBitmap;
    private String time;
    private Date date;


    public ListAdapter_ChatArray(Context context, int textViewResourceId, Bitmap providerBitmap, Bitmap clientBitmap) {
        super(context, textViewResourceId);

        this.providerBitmap = providerBitmap;
        this.clientBitmap = clientBitmap;


    }


    public void add(ChatMessage object){

        MessageList.add(object);
        super.add(object);
    }

    public  int getCount(){
         return  this.MessageList.size();
    }

    public ChatMessage getItem(int position){

        return this.MessageList.get(position);

    }

    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        if(v==null){
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_sample__chat_message, parent, false);
        }


        layout = (LinearLayout)v.findViewById(R.id.linearLayout_message1);
        chatText = (TextView) v.findViewById(R.id.textView_chatMessage1);
        time_left = (TextView) v.findViewById(R.id.textView_chat_time_left);
        time_right = (TextView) v.findViewById(R.id.textView_chat_time_right);
        profilePic_left = (ImageView) v.findViewById(R.id.imageView_chat_profile_pic_left);
        profilePic_right = (ImageView) v.findViewById(R.id.imageView_chat_profile_pic_right);
        picture = (ImageView) v.findViewById(R.id.imageView_chat_picture);
        container = (LinearLayout)v.findViewById(R.id.chat_gravityContainer);
        ChatMessage messageObj = getItem(position);
        chatText.setText(messageObj.message);

        profilePic_right.setImageBitmap(clientBitmap);
        profilePic_left.setImageBitmap(providerBitmap);




        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat finalFormat = new SimpleDateFormat("MM-dd hh:mm aa");
        try {
            if(messageObj.left) {
                date = format.parse(messageObj.currentTime);
            }
            else if(!messageObj.left){
                date = format2.parse(messageObj.currentTime);
            }
            time = finalFormat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(!messageObj.left){
            profilePic_right.setVisibility(View.VISIBLE);
            profilePic_left.setVisibility(View.INVISIBLE);
            time_right.setVisibility(View.VISIBLE);
            time_left.setVisibility(View.INVISIBLE);
            time_left.setText("");
            time_right.setText(time);
            chatText.setGravity(messageObj.left? Gravity.LEFT:Gravity.RIGHT);
            container.setGravity(messageObj.left? Gravity.LEFT:Gravity.RIGHT);
            if(messageObj.bitmap == null)
            {
                picture.setImageBitmap(null);

                picture.setVisibility(View.INVISIBLE);
                layout.setVisibility(View.VISIBLE);
            }
            else{

                picture.setImageBitmap(messageObj.bitmap);
                picture.setVisibility(View.VISIBLE);
                layout.setVisibility(View.INVISIBLE);
                chatText.setText("");
            }
            //chatText.setBackgroundResource(messageObj.left ?R.drawable.client_blank:R.drawable.chat_client);
        }
        else{
            profilePic_right.setVisibility(View.INVISIBLE);
            profilePic_left.setVisibility(View.VISIBLE);
            time_right.setVisibility(View.INVISIBLE);
            time_left.setVisibility(View.VISIBLE);
            time_right.setText("");
            time_left.setText(time);

            chatText.setGravity(messageObj.left? Gravity.LEFT:Gravity.RIGHT);
            container.setGravity(messageObj.left? Gravity.LEFT:Gravity.RIGHT);

            if(messageObj.bitmap == null)
            {

                picture.setImageBitmap(null);

                picture.setVisibility(View.INVISIBLE);
                container.setVisibility(View.VISIBLE);
            }
            else{

                picture.setImageBitmap(messageObj.bitmap);
                picture.setVisibility(View.VISIBLE);
                container.setVisibility(View.INVISIBLE);
                chatText.setText("");
            }
            //chatText.setBackgroundResource(messageObj.left ?R.drawable.client_blank:R.drawable.chat_client);
        }


        return v;

    }

    public Bitmap decodeToBitmap(byte[] decodeByte){
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }


}

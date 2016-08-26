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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelvin on 2016/8/25.
 */
public class ListAdapter_ChatArray extends ArrayAdapter<ChatMessage> {

    private TextView chatText, time_left, time_right;
    private List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
    private LinearLayout layout, container;
    private ImageView profilePic_left,profilePic_right;


    public ListAdapter_ChatArray(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
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
                v = inflater.inflate(R.layout.list_sample_message1, parent, false);
        }


        layout = (LinearLayout)v.findViewById(R.id.linearLayout_message1);
        chatText = (TextView) v.findViewById(R.id.textView_chatMessage1);
        time_left = (TextView) v.findViewById(R.id.textView_chat_time_left);
        time_right = (TextView) v.findViewById(R.id.textView_chat_time_right);
        profilePic_left = (ImageView) v.findViewById(R.id.imageView_chat_profile_pic_left);
        profilePic_right = (ImageView) v.findViewById(R.id.imageView_chat_profile_pic_right);
        container = (LinearLayout)v.findViewById(R.id.chat_gravityContainer);
        ChatMessage messageObj = getItem(position);
        chatText.setText(messageObj.message);
        System.out.println("xxxxxxxxxx"+messageObj.left);

        if(!messageObj.left){
            profilePic_right.setVisibility(View.VISIBLE);
            profilePic_left.setVisibility(View.INVISIBLE);
            time_right.setVisibility(View.VISIBLE);
            time_left.setVisibility(View.INVISIBLE);
            time_left.setText("");
            time_right.setText("time");
            chatText.setGravity(messageObj.left? Gravity.LEFT:Gravity.RIGHT);
            container.setGravity(messageObj.left? Gravity.LEFT:Gravity.RIGHT);
        }
        else{
            profilePic_right.setVisibility(View.INVISIBLE);
            profilePic_left.setVisibility(View.VISIBLE);
            time_right.setVisibility(View.INVISIBLE);
            time_left.setVisibility(View.VISIBLE);
            time_right.setText("");
            time_left.setText("time");
            chatText.setGravity(messageObj.left? Gravity.LEFT:Gravity.RIGHT);
            container.setGravity(messageObj.left? Gravity.LEFT:Gravity.RIGHT);
        }
        //chatText.setBackgroundResource(messageObj.left ?);

        return v;

    }

    public Bitmap decodeToBitmap(byte[] decodeByte){
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }


}

package com.newnergy.para_client;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private TextView chatText, time;
    private List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
    private LinearLayout layout, container;
    private ImageView profilePic;


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
        time = (TextView) v.findViewById(R.id.textView_chat_time);
        profilePic = (ImageView) v.findViewById(R.id.imageView_chat_profile_pic);
        ChatMessage messageObj = getItem(position);
        chatText.setText(messageObj.message);
        System.out.println("xxxxxxxxxx"+messageObj.left);

        //chatText.setBackgroundResource(messageObj.left ?);

        return v;

    }

    public Bitmap decodeToBitmap(byte[] decodeByte){
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }


}

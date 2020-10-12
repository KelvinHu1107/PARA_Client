package com.newnergy.para_client.Chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newnergy.para_client.Client_DisplayPic;
import com.newnergy.para_client.Image_package.ImageUnity;
import com.newnergy.para_client.R;
import com.newnergy.para_client.ValueMessager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Kelvin on 2016/8/25.
 */
public class ListAdapter_ChatArray extends ArrayAdapter<ChatMessage>
        implements Comparator<ChatMessage>{

    private TextView chatText, time_left, time_right;
    private List<ChatMessage> MessageList = new ArrayList<ChatMessage>();
    private LinearLayout layout, container;
    private ImageView profilePic_left,profilePic_right, picture;
    private Bitmap providerBitmap, clientBitmap;
    private String time;
    private Date date, dateForSorting, dateForSorting2;
    private ImageUnity imageUnity;
    private Context context;

    public ListAdapter_ChatArray(Context context, int textViewResourceId, Bitmap providerBitmap, Bitmap clientBitmap) {
        super(context, textViewResourceId);
        this.context=context;
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

        imageUnity=new ImageUnity();
        layout = (LinearLayout)v.findViewById(R.id.linearLayout_message1);
        chatText = (TextView) v.findViewById(R.id.textView_chatMessage1);
        time_left = (TextView) v.findViewById(R.id.textView_chat_time_left);
        time_right = (TextView) v.findViewById(R.id.textView_chat_time_right);
        profilePic_left = (ImageView) v.findViewById(R.id.imageView_chat_profile_pic_left);
        profilePic_right = (ImageView) v.findViewById(R.id.imageView_chat_profile_pic_right);
        picture = (ImageView) v.findViewById(R.id.imageView_chat_picture);
        container = (LinearLayout)v.findViewById(R.id.chat_gravityContainer);
        ChatMessage messageObj = getItem(position);
        chatText.setText(messageObj.get_message());

        if(clientBitmap == null){
            profilePic_right.setImageResource(R.drawable.client_photo_round);
        }
        else {
            profilePic_right.setImageBitmap(clientBitmap);
        }

        if(ValueMessager.providerProfileBitmap == null){
            profilePic_left.setImageResource(R.drawable.client_photo_round);
        }else {
            profilePic_left.setImageBitmap(providerBitmap);
        }

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");

        SimpleDateFormat finalFormat = new SimpleDateFormat("MM-dd hh:mm a");
        try {

            date = format.parse(messageObj.get_data());
            time = finalFormat.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //right = sent
        if(messageObj.get_side()==false){
            chatText.setBackgroundResource(R.drawable.border_radius_blue_button);
            chatText.setTextColor(Color.parseColor("#ffffff"));
            profilePic_right.setVisibility(View.VISIBLE);
            profilePic_left.setVisibility(View.INVISIBLE);
            time_right.setVisibility(View.VISIBLE);
            time_left.setVisibility(View.INVISIBLE);
            time_left.setText("");
            time_right.setText(time);
            chatText.setGravity(messageObj.get_side()? Gravity.LEFT:Gravity.RIGHT);
            container.setGravity(messageObj.get_side()? Gravity.LEFT:Gravity.RIGHT);

            adapterImage(messageObj, messageObj.get_Message_type());
            //adapterImageLocal(messageObj);
        }
        else{
            chatText.setBackgroundResource(R.drawable.border_radius_greydde4f2);
            chatText.setTextColor(Color.parseColor("#000000"));
            profilePic_right.setVisibility(View.INVISIBLE);
            profilePic_left.setVisibility(View.VISIBLE);
            time_right.setVisibility(View.INVISIBLE);
            time_left.setVisibility(View.VISIBLE);
            time_right.setText("");
            time_left.setText(time);

            chatText.setGravity(messageObj.get_side()? Gravity.LEFT:Gravity.RIGHT);
            container.setGravity(messageObj.get_side()? Gravity.LEFT:Gravity.RIGHT);

            adapterImage(messageObj, messageObj.get_Message_type());
        }
        return v;
    }

    private void adapterImage(final ChatMessage messageObj, Integer type)
    {

        final String buffer = messageObj.get_message();

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueMessager.displayPicUrl = buffer;

                Intent intent=new Intent(context,Client_DisplayPic.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        if(type == 1)
        {
            imageUnity.setImage(context,picture,"file://"+messageObj.get_message());

            picture.setVisibility(View.VISIBLE);
            layout.setVisibility(View.INVISIBLE);
            chatText.setText("");
        }
        else if(type == 0){

            picture.setImageBitmap(null);
            picture.setVisibility(View.INVISIBLE);
            layout.setVisibility(View.VISIBLE);
        }
    }

    public void sortHistory()
    {

        //kelvinkelvin sort chat adapter by time(0.001s)

        Collections.sort(MessageList, new Comparator<ChatMessage>() {
            @Override
            public int compare(ChatMessage cm1, ChatMessage cm2)
            {

                SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss aa");

                try {
                    dateForSorting = format.parse(cm1.get_data());
                    dateForSorting2 = format.parse(cm2.get_data());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if((long)(dateForSorting.getTime())>(long)(dateForSorting2.getTime())) {
                    return 1;
                }else if((long)(dateForSorting.getTime()) == (long)(dateForSorting2.getTime())) {
                    return 0;
                }else if((long)(dateForSorting.getTime())<(long)(dateForSorting2.getTime())) {
                    return -1;
                }else{
                    return 0;
                }

            }
        });

    }

    @Override
    public int compare(ChatMessage lhs, ChatMessage rhs) {
        return 0;
    }

}
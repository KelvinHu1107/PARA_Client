package com.newnergy.para_client.Chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newnergy.para_client.DataTransmitController;
import com.newnergy.para_client.ProviderProfileDataConvert;
import com.newnergy.para_client.ProviderProfileViewModel;
import com.newnergy.para_client.R;
import com.newnergy.para_client.ValueMessager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Kelvin on 2016/6/3.
 */

public class ListAdapter_Message extends ArrayAdapter<String> {

    private String[] fromEmail, toEmail;
    private String[] firstName;
    private String[] lastName;
    private String[] profileUrl;
    private String[] message;
    private int[] messageType;
    private String[] textDate;
    Context c;
    LayoutInflater inflaterMessage;
    private ProviderProfileViewModel list;

    public ListAdapter_Message(Context context, String[] objectName, String[] firstName, String[] lastName, int[] messageType, String[] date,
                   String[] message, String[] profileUrl, String[] fromEmail, String[] toEmail) {
        super(context, R.layout.message_list_sample, objectName);

        this.c = context;
        this.firstName = firstName;
        this.lastName = lastName;
        this.messageType = messageType;
        this.textDate = date;
        this.message = message;
        this.profileUrl = profileUrl;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
    }

    public class ViewHolder {
        TextView nameTv, textTv, timeTv, indication;
        ImageView imgIv;
        LinearLayout[] linearLayout = new LinearLayout[firstName.length];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
// getView的建構子
        if (convertView == null) {
            inflaterMessage = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflaterMessage.inflate(R.layout.message_list_sample, null);
        }

        final ViewHolder holderMessage = new ViewHolder();
        holderMessage.nameTv = (TextView) convertView.findViewById(R.id.textView_message_name);
        holderMessage.imgIv = (ImageView) convertView.findViewById(R.id.imageView_pic_message);
        holderMessage.textTv = (TextView) convertView.findViewById(R.id.textView_message_text);
        holderMessage.timeTv = (TextView) convertView.findViewById(R.id.textView_message_time);
        holderMessage.linearLayout[position] = (LinearLayout) convertView.findViewById(R.id.linearLayout_message);
        holderMessage.indication = (TextView) convertView.findViewById(R.id.textView_indication);

        for(int i=0; i<ValueMessager.listMessageFromUserName.size(); i++){
            if(fromEmail[position].equalsIgnoreCase(ValueMessager.listMessageFromUserName.get(i))){
                holderMessage.indication.setVisibility(View.VISIBLE);
            }else{
                holderMessage.indication.setVisibility(View.INVISIBLE);
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

        //每一個position assign item
        if(profileUrl[position].equals("")){

            DataTransmitController cc = new DataTransmitController() {
                    @Override
                    public void onResponse(String result) {
                        super.onResponse(result);

                        list = ProviderProfileDataConvert.convertJsonToModel(result);

                        if(!list.getProfilePicture().equals("")) {
                            Picasso.with(c).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + list.getProfilePicture()).into(holderMessage.imgIv);
                        }else{
                            holderMessage.imgIv.setImageResource(R.drawable.client_photo_round);
                        }
                    }
                };
                cc.execute("http://para.co.nz/api/ProviderProfile/GetProviderDetail/" + fromEmail[position], "", "GET");

        }
        else {
            Picasso.with(c).load("http://para.co.nz/api/ProviderProfile/GetProviderProfileImage/" + profileUrl[position]).into(holderMessage.imgIv);
        }

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        SimpleDateFormat format2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat finalFormat = new SimpleDateFormat("MM-dd hh:mm a");
        try {
            date = format.parse(textDate[position]);
            holderMessage.timeTv.setText(finalFormat.format(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        holderMessage.nameTv.setText(firstName[position]+" "+lastName[position]);
        //holderMessage.timeTv.setText(textDate[position]);
        holderMessage.textTv.setText(message[position]);

        holderMessage.linearLayout[position].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueMessager.providerEmail = fromEmail[position];
                ValueMessager.providerFirstName = firstName[position];
                ValueMessager.providerLastName = lastName[position];
                holderMessage.imgIv.setDrawingCacheEnabled(true);
                ValueMessager.providerProfileBitmap = holderMessage.imgIv.getDrawingCache();
                ValueMessager.chatLastPage = "Message";

                ValueMessager.is_chat = false;
                ValueMessager.notificationMessage = 0;
                ValueMessager.listMessageId.clear();

                for(int i=0; i<ValueMessager.listMessageFromUserName.size(); i++){
                    if(fromEmail[position].equalsIgnoreCase(ValueMessager.listMessageFromUserName.get(i))){
                        ValueMessager.listMessageFromUserName.clear();
                    }
                }

                Intent intent = new Intent(c, Client_Chat.class);
                c.startActivity(intent);
            }
        });
        return convertView;
    }
}
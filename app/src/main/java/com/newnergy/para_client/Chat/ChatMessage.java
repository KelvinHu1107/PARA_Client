package com.newnergy.para_client.Chat;

//import android.graphics.Bitmap;

/**
 * Created by Kelvin on 2016/8/25.
 */
public class ChatMessage {

    private String from_email;
    private String to_email;
    private String message;
    private int message_type;//0=text, 1=image, 2=other
    private String data;
    private boolean side;//0=false=left, 1=true=right
    private boolean isRead;//0=false, 1=true
    //private Bitmap bitmap;


    public ChatMessage(String fromID, String ToID,
                       String Message, int type,
                       int is_left, String currentTime,
                       int is_read) {

        super();
        this.from_email=fromID;
        this.to_email=ToID;

        this.message = Message;
        this.data =currentTime;
        this.message_type = type;
        //this.bitmap = bitmap;
        if(is_left==1)
            this.side=true;
        else if(is_left==0)
            this.side=false;

        if(is_read==1)
            this.isRead=true;
        else if(is_read==0)
            this.isRead=false;
    }

    public ChatMessage() {

    }

    public String get_from_email() {
        return from_email;
    }
    public String get_to_email() {
        return to_email;
    }
    public String get_message() {
        return message;
    }
    public String get_data() {
        return data;
    }
    public boolean get_isRead() {
        return this.isRead;
    }
    public int get_Message_type() {
        return message_type;
    }
    public boolean get_side() {
        return this.side;
    }

    public void set_from_email(String from) {
        //if(!TextUtils.isEmpty(from) && from.split("/").length>0)
        //  this.from_email = from.split("/")[0];
        //else
        this.from_email = from;
    }
    public void set_to_email(String to) {
        // if(!TextUtils.isEmpty(to) && to.split("/").length > 0)
        //  this.to_email = to.split("/")[0];
        //  else
        this.to_email = to;
    }
    public void set_message(String body) {
        this.message = body;
    }
    public void set_data(String sendTime) {
        this.data = sendTime;
    }
    public void set_isRead(int is_read) {
        if(is_read==1)
            this.isRead =true;
        else if(is_read==0)
            this.isRead=false;
    }
    public void set_Message_type(int message_type) {

        this.message_type = message_type;
    }
    public void set_side(int is_left) {
        if(is_left==1)
            this.side =true;
        else if(is_left==0)
            this.side=false;
    }





}

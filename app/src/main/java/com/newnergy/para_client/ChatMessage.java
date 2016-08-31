package com.newnergy.para_client;

import android.graphics.Bitmap;

/**
 * Created by Kelvin on 2016/8/25.
 */
public class ChatMessage {
    public boolean left;
    public String message;
    public String currentTime;
    public int type;
    public Bitmap  bitmap;



    public ChatMessage(boolean left, String message, String currentTime, int type, Bitmap bitmap) {

        super();
        this.left = left;
        this.message = message;
        this.currentTime =currentTime;
        this.type = type;
        this.bitmap = bitmap;
    }

}

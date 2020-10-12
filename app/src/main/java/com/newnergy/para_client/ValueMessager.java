package com.newnergy.para_client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.TextView;

import com.newnergy.para_client.Chat.Chat_SingleChat_db;
import com.newnergy.para_client.Chat.Chat_User_db;
import com.newnergy.para_client.Chat.ListAdapter_ChatArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelvin on 2016/6/8.
 */
public class ValueMessager {

    public static String currentVersion = "1.0.0";

    public static CharSequence name;
    public static CharSequence email;
    public static CharSequence phone;
    public static CharSequence address_street;
    public static CharSequence address_suburb;
    public static CharSequence address_city;
    public static Integer address_id;
    public static Integer settingLastPage = 0;
    public static Integer commentLastPage = 0;
    public static CharSequence description;
    public static Bitmap profileBitmap = null;
    public static Bitmap userProfileBitmap = null;
    public static Bitmap providerProfileBitmap = null;
    public static String bitmapUrlBuffer;
    public static Boolean notificationSwitch = true;
    public static Boolean isSettingDate = false;
    public static Boolean userLogInByFb = false;
    public static Boolean registerByFb = false;
    public static Boolean resolution1080x720 = false;
    public static Boolean resolution800x480 = false;
    public static Boolean resolution1920x1080 = false;
    public static Boolean resolution960x540 = false;
    public static CharSequence readDataBuffer = "On";
    public static CharSequence edit_workTitle;
    public static CharSequence edit_workType;
    public static CharSequence edit_budget;
    public static CharSequence edit_street;
    public static CharSequence edit_subrub;
    public static CharSequence edit_city;
    public static CharSequence edit_description;
    public static String lastPageCalender;
    public static String userFirstName;
    public static String userLastName;
    public static String userEmailBuffer;
    public static String userProfilePicUrl;
    public static Uri uriBuffer;
    public static String stringBuffer;
    public static String displayPicUrl;
    public static String providerFirstName;
    public static String providerLastName;
    public static String providerEmail;
    public static String selectedDate = "";
    public static String selectedDay = "";
    public static String selectedMonth = "";
    public static String selectedYear = "";
    public static String accessToken, refreshToken;
    public static String lastPageViewPic;
    public static String lastPageMap;
    public static String lastPageSelectPic;
    public static Long tokenDueTime;
    public static Bitmap userProfilePic;
    public static String chatLastPage;
    public static int lastPageConfirm2;
    public static int chatAddMessageFlag = 0;
    public static int imageCounter = 0;
    public static int counter;
    public static int counterForImage;
    public static int sortCounter = 0;
    public static int serviceId;
    public static Double providerRating;
    public static Double deposit;
    public static Intent intent;
    public static ListAdapter_ChatArray adapterGlobal;
    public static Chat_SingleChat_db chat_db_Global;
    public static Chat_User_db chat_User_db_Global;
    //public static ListView list chat_db=new Chat_SingleChat_db();
    public static Boolean is_chat = false, is_pending = false;
    public static ImageButton footerMessage, footerPending;
    public static TextView textMessage, textPending;
    public static int notificationMessage = 0, notificationPending = 0;
    public static List<String> bitmapListPost=new ArrayList<String>();
    public static List<String> listMessageId=new ArrayList<String>(), listPendingId=new ArrayList<String>(), listPendingServiceId=new ArrayList<String>(), listPaymentServiceId=new ArrayList<String>(), listMessageFromUserName=new ArrayList<String>();

}

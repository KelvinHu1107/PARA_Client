package com.newnergy.para_client.Notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.newnergy.para_client.Chat.Client_Message;
import com.newnergy.para_client.Client_LoginController;
import com.newnergy.para_client.Client_Pending;
import com.newnergy.para_client.R;
import com.newnergy.para_client.ValueMessager;

import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.google.android.gms.internal.zzs.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static int badgeCount = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String messageId=remoteMessage.getMessageId();//用于read message
        String message = remoteMessage.getData().get("body");
        String title = remoteMessage.getData().get("title");
        String sound = remoteMessage.getData().get("sound");
        String type=remoteMessage.getData().get("type");
        String data="";

        if (sound.equalsIgnoreCase("Y")) {
            if (isAppOnForeground(this)) {
                try {
                    showNotification(title, data, type);
                    ShortcutBadger.removeCountOrThrow(this);
                    MyFirebaseMessagingService.badgeCount = 0;
                }catch (Exception e){
                    System.out.println("not support"+"bbbb");
                }
            } else {
                showNotification(title, data, type);
                try {
                    badgeCount++;
                    ShortcutBadger.applyCountOrThrow(this,badgeCount); ;
                }catch (Exception e){
                    System.out.println("not support"+"aaaa");
                }
            }
        } else {
            if (isAppOnForeground(this)) {
                try {

                    showSilentNotification(title, data, type);

                    ShortcutBadger.removeCountOrThrow(this);
                    MyFirebaseMessagingService.badgeCount = 0;
                }catch (Exception e){
                    System.out.println("not support");
                }
            } else {
                showSilentNotification(title, data, type);
                try {
                    badgeCount++;
                    ShortcutBadger.applyCountOrThrow(this,badgeCount); ;
                }catch (Exception e){
                    System.out.println("not support");
                }
            }
        }

        if(type.equalsIgnoreCase("message")){
            String[] info = message.toString().split("-");
            ValueMessager.listMessageFromUserName.add(info[1]);
            ValueMessager.listMessageId.add(messageId);
            returnReadMessage(messageId,type);

        }else if(type.equalsIgnoreCase("pending")){
            String[] info = message.toString().split("-");
            returnReadMessage(messageId,type);
            ValueMessager.listPendingServiceId.add(info[1]);
            ValueMessager.listPendingId.add(messageId);

        }

    }

    private void showSilentNotification(String title, String message, String type) {
        Intent i=new Intent();
        if(type.equalsIgnoreCase("message")) {
             i = new Intent(this, Client_Message.class);
        }else if(type.equalsIgnoreCase("pending")){
             i = new Intent(this, Client_Pending.class);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = null;
        try {

                builder = new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.ic_launcher)
//                    .setVibrate(new long[]{500, 1000})
//                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setTicker("new message"); //带上升框
//                    .setDefaults(Notification.DEFAULT_ALL);


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (builder != null) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1, builder.build());
        } else {
            Log.d(TAG, "notificationBuilder not send");
        }
    }

    private void showNotification(String title, String message, String type) {
        Intent i=new Intent();
        if(type.equalsIgnoreCase("message")) {
            i = new Intent(this, Client_Message.class);
        }else if(type.equalsIgnoreCase("pending")){
            i = new Intent(this, Client_Pending.class);
        }

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = null;
        try {

                builder = new NotificationCompat.Builder(this)
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setVibrate(new long[]{500, 1000})
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setTicker("new message"); //带上升框
//                    .setDefaults(Notification.DEFAULT_ALL);


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (builder != null) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1, builder.build());
        } else {
            Log.d(TAG, "notificationBuilder not send");
        }
    }

    private  void refreshUi(String type){
        if(type.equalsIgnoreCase("message")){
            ValueMessager.notificationMessage = ValueMessager.notificationMessage+1;
            if(!ValueMessager.is_chat && ValueMessager.footerMessage!= null && ValueMessager.textMessage != null) {
                ValueMessager.textMessage.setVisibility(View.VISIBLE);
                if(ValueMessager.notificationMessage > 0 && ValueMessager.notificationMessage < 10){
                    ValueMessager.textMessage.setText(" "+ValueMessager.notificationMessage+" ");
                }else if(ValueMessager.notificationMessage > 9){
                    ValueMessager.textMessage.setText(String.valueOf(ValueMessager.notificationMessage));
                }else if(ValueMessager.notificationMessage>99){
                    ValueMessager.textMessage.setText("99+");
                }
            }
        }else if(type.equalsIgnoreCase("pending")){
            ValueMessager.notificationPending = ValueMessager.notificationPending+1;
            if(!ValueMessager.is_pending && ValueMessager.footerPending!= null && ValueMessager.textPending != null) {
                ValueMessager.textPending.setVisibility(View.VISIBLE);
                if (ValueMessager.notificationPending > 0 && ValueMessager.notificationPending < 10) {
                    ValueMessager.textPending.setText(" " + ValueMessager.notificationPending + " ");
                } else if (ValueMessager.notificationPending > 9) {
                    ValueMessager.textPending.setText(String.valueOf(ValueMessager.notificationPending));
                } else if (ValueMessager.notificationPending > 99) {
                    ValueMessager.textPending.setText("99+");
                }
            }
        }
    }

    private void returnReadMessage(String returnId, final String type){

        Client_LoginController controller = new Client_LoginController() {
            @Override
            public void onResponse(Boolean s) {
                super.onResponse(s);
                if(type.equalsIgnoreCase("message")){
                    ValueMessager.notificationMessage = ValueMessager.notificationMessage+1;
                    if(!ValueMessager.is_chat && ValueMessager.footerMessage!= null && ValueMessager.textMessage != null) {
                        ValueMessager.textMessage.setVisibility(View.VISIBLE);
                        if(ValueMessager.notificationMessage > 0 && ValueMessager.notificationMessage < 10){
                            ValueMessager.textMessage.setText(" "+ValueMessager.notificationMessage+" ");
                        }else if(ValueMessager.notificationMessage > 9){
                            ValueMessager.textMessage.setText(String.valueOf(ValueMessager.notificationMessage));
                        }else if(ValueMessager.notificationMessage>99){
                            ValueMessager.textMessage.setText("99+");
                        }
                    }
                }else if(type.equalsIgnoreCase("pending")){
                    ValueMessager.notificationPending = ValueMessager.notificationPending+1;
                    if(!ValueMessager.is_pending && ValueMessager.footerPending!= null && ValueMessager.textPending != null) {
                        ValueMessager.textPending.setVisibility(View.VISIBLE);
                        if (ValueMessager.notificationPending > 0 && ValueMessager.notificationPending < 10) {
                            ValueMessager.textPending.setText(" " + ValueMessager.notificationPending + " ");
                        } else if (ValueMessager.notificationPending > 9) {
                            ValueMessager.textPending.setText(String.valueOf(ValueMessager.notificationPending));
                        } else if (ValueMessager.notificationPending > 99) {
                            ValueMessager.textPending.setText("99+");
                        }
                    }
                }
            }
        };
        String data= "hahaha";
        controller.execute("http://para.co.nz/api/NoticeAndroid/ClientReadNoticeMessage", data, "PUT");
    }

    private boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
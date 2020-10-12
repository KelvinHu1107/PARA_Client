package com.newnergy.para_client.Notification;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.newnergy.para_client.DataSendController;
import com.newnergy.para_client.ValueMessager;

/**
 * Created by GaoxinHuang on 2016/9/28.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        System.out.println("refresh");
        String token = FirebaseInstanceId.getInstance().getToken();
        String username = ValueMessager.email.toString();
        registerToken(username,token);
    }

    private void registerToken(String username,String token) {
        DataSendController controller = new DataSendController() {
            @Override
            public void onResponse(Boolean s) {
            }
        };
        String data="{\"Username\":\"" + username + "\","
                +"\"NotificationToken\":\"" + token + "\"}";
        System.out.println(data);
        controller.execute("http://para.co.nz/api/NoticeAndroid/AddNotificationClient",data,"POST");
    }
}
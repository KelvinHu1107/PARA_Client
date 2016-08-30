package com.newnergy.para_client;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutionException;

import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

/**
 * Created by GaoxinHuang on 2016/8/23.
 */
public  class  SignalRHubConnection {
    public static HubConnection mHubConnection;
    public static HubProxy mHubProxy;
    private Context context;
//    private Handler mHandler=new Handler(Looper.getMainLooper());


    public static String mConnectionID;
    public final static String HOST = "http://para.co.nz";
//    private final IBinder mBinder = new LocalBinder();

    public SignalRHubConnection(Context context) {

        this.context = context;
    }

    //
     /*
    This function try to connect with chat hub and return connection ID.
     */
    public void startSignalR() {
        try {
            Platform.loadPlatformComponent(new AndroidPlatformComponent());
            mHubConnection = new HubConnection(HOST);

            mHubProxy = mHubConnection.createHubProxy("chatHub");
            ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
            SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport);
            signalRFuture.get();
            //set connection id
            mConnectionID = mHubConnection.getConnectionId();
            DataSendController controller = new DataSendController() {
                @Override
                public void onResponse(Boolean r) {
                    super.onResponse(r);
                }
            };
            String username = ValueMessager.email.toString();
            String data="{'Username':'"+username+"','ConnectionId':'"+mConnectionID+"'}";
            controller.execute("http://para.co.nz/api/ChatClient/Connect", data, "POST");


            SignalRHubConnection.mHubProxy.on("recieveTextMessage",
                    new SubscriptionHandler1<ChatGetMessageViewModel>() {
                        @Override
                        public void run(ChatGetMessageViewModel model) {
                            Handler xHandler=new Handler(Looper.getMainLooper());
                            xHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    },ChatGetMessageViewModel.class);


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}

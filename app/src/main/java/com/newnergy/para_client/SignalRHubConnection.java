package com.newnergy.para_client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.newnergy.para_client.Chat.ChatMessage;
import com.newnergy.para_client.Chat.ProviderUserDate;
import com.newnergy.para_client.Image_package.ImageUnity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public  class SignalRHubConnection {
    public static HubConnection mHubConnection;
    public static HubProxy mHubProxy;
    ProviderProfileViewModel jsm;

    private String _firstName;
    private String _lastName;
    private String _profileUrl;
    private String _providerEmail;
    private String _localEmail ;

    public static String mConnectionID;
    public final static String HOST = "http://para.co.nz";
//    private final IBinder mBinder = new LocalBinder();

    //
     /*
    This function try to connect with chat hub and return connection ID.
     */

    public void saveUserData(final String message, final int messageType, final String currentDate){
        DataTransmitController c=new DataTransmitController(){
            @Override
            public void onResponse(String result) {
                super.onResponse(result);
                jsm = ProviderProfileDataConvert.convertJsonToModel(result);

                _firstName= jsm.getFirstName();
                _lastName=jsm.getLastName();
                _profileUrl=jsm.getProfilePicture();
                ProviderUserDate pud=new ProviderUserDate(_providerEmail,_localEmail,_firstName,_lastName,
                        _profileUrl,message,messageType,currentDate);

                if(ValueMessager.chat_User_db_Global!=null)
                {
                    Boolean isExist= ValueMessager.chat_User_db_Global.isUserExist(_providerEmail,_localEmail);
                    if (isExist==true)
                    {
                        ValueMessager.chat_User_db_Global.updataNewMessage(_providerEmail,_localEmail,message,0,currentDate);
                    }
                    else if (isExist==false)
                    {
                        ValueMessager.chat_User_db_Global.insertUserData(pud);
                    }

                }
                
                //// TODO: Kelvin add adepter or not
            }
        };

        c.execute("http://para.co.nz/api/ProviderProfile/GetProviderDetail/"+ _providerEmail,"","GET");
    }


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

            receiveMessageFunction();
            receiveImageFunction();
            String username = ValueMessager.email.toString();
            String data="{'Username':'"+username+"','ConnectionId':'"+mConnectionID+"'}";
            controller.execute("http://para.co.nz/api/ChatClient/Connect", data, "POST");

            SignalRHubConnection.mHubConnection.closed(new Runnable() {
                @Override
                public void run() {
                    new Timer(false).schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startSignalR();
                        }
                    },5000);
                }
            });


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
    public void receiveMessageFunction()
    {

        SignalRHubConnection.mHubProxy.on("recieveTextMessage",
                    new SubscriptionHandler1<ChatGetMessageViewModel>() {
                        @Override
                        public void run(final ChatGetMessageViewModel model) {
                            final String message = model.getMessageContent();
                            _providerEmail = model.getFromUsername();
                            _localEmail= ValueMessager.email.toString();

                            Handler xHandler=new Handler(Looper.getMainLooper());
                            xHandler.post(new Runnable() {
                                @Override
                                public void run() {

                                    SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
                                    Date curDate = new Date(System.currentTimeMillis()) ;
                                    String currentDateTimeString = format.format(curDate);
                                    ChatMessage cm=new ChatMessage(_providerEmail,
                                            _localEmail,
                                            message,0,
                                            1,currentDateTimeString,0);
                                    saveUserData(message,0,currentDateTimeString);

                                    if(ValueMessager.chat_db_Global!=null) {
                                        ValueMessager.chat_db_Global.insertSingleChat(cm);

                                    }
                                    if(ValueMessager.is_chat==true)
                                    {
                                        if(_providerEmail.equals(ValueMessager.providerEmail)) {
                                            ValueMessager.adapterGlobal.add(cm);
                                        }
                                    }else {
                                        if(!ValueMessager.is_chat && ValueMessager.footerMessage!= null && ValueMessager.textMessage != null) {
                                            ValueMessager.textMessage.setVisibility(View.VISIBLE);
                                            if(ValueMessager.notificationMessage > 0 && ValueMessager.notificationMessage < 10){
                                                ValueMessager.textMessage.setText(" "+ValueMessager.notificationMessage+" ");
                                            }else if(ValueMessager.notificationMessage > 9){
                                                if(isNumeric(String.valueOf(ValueMessager.notificationMessage)))
                                                {
                                                    ValueMessager.textMessage.setText(String.valueOf(ValueMessager.notificationMessage));
                                                }
                                            }else if(ValueMessager.notificationMessage>99){
                                                ValueMessager.textMessage.setText("99+");
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    },ChatGetMessageViewModel.class);


    }

    public boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() )
        {
            return false;
        }
        return true;
    }

    public void receiveImageFunction()
    {
        SignalRHubConnection.mHubProxy.on("recieveImageMessage",
                new SubscriptionHandler1<ChatGetMessageViewModel>() {
                    @Override
                    public void run(ChatGetMessageViewModel model) {

                        final String message = model.getMessageContent();
                        _providerEmail=model.getFromUsername();
                        _localEmail= ValueMessager.email.toString();
                        GetImageController controller=new GetImageController(){
                            @Override
                            public void onResponse(Bitmap mBitmap) {
                                super.onResponse(mBitmap);
                                if (mBitmap == null) {
                                    System.out.println("didnt get pic form chat server!!!");
                                }
                                SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
                                Date curDate = new Date(System.currentTimeMillis()) ;
                                String currentDateTimeString = format.format(curDate);
                                ImageUnity imageUnity=new ImageUnity();


                                String ImageUrl="/sdcard/myImage/"+ _providerEmail+"_"+currentDateTimeString+ ".jpg";
                                //save sd
                                imageUnity.saveImage(mBitmap,ImageUrl);


                                //imageUnity.getBitmap("/sdcard/myImage/"+ValueMessager.providerEmail+"_"+currentDateTimeString);

                               // File("/sdcard/myImage/", fromname+"_"+data);
                                //String imageURL="/sdcard/myImage/"+ValueMessager.providerEmail+"_"+currentDateTimeString;

                                //cm=( fromemail toemail message message_type is_left time is_read)
                                ChatMessage cm=new ChatMessage(_providerEmail, _localEmail,
                                        ImageUrl,1,
                                        1,currentDateTimeString,0);
                                //"/storage/emulated/0/42b911cc45dc.jpg"
                                System.out.println("hhhhhhhhh: "+cm.get_message());

                                saveUserData("Image",1,currentDateTimeString);

                                ValueMessager.chat_db_Global.insertSingleChat(cm);
                                ValueMessager.adapterGlobal.add(cm);
                                if(!ValueMessager.is_chat && ValueMessager.footerMessage!= null && ValueMessager.textMessage != null) {
                                    ValueMessager.textMessage.setVisibility(View.VISIBLE);
                                    if(ValueMessager.notificationMessage > 0 && ValueMessager.notificationMessage < 10){
                                        ValueMessager.textMessage.setText(" "+ValueMessager.notificationMessage+" ");
                                    }else if(ValueMessager.notificationMessage > 9){
                                        if(isNumeric(String.valueOf(ValueMessager.notificationMessage))) {
                                            ValueMessager.textMessage.setText(String.valueOf(ValueMessager.notificationMessage));
                                        }
                                    }else if(ValueMessager.notificationMessage>99){
                                        ValueMessager.textMessage.setText("99+");
                                    }
                                }
                                //adapter.add(new ChatMessage(side = true, "", time, 1, mBitmap));// type0: string, 1:picture, 2:voice

                            }
                        };
                        controller.execute("http://para.co.nz/api/ChatClient/GetChatImage/"+message, "", "GET");
                    }
                },ChatGetMessageViewModel.class);
    }

}
package com.newnergy.para_client;

import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;

public class Client_Chat extends AppCompatActivity {

    private ListView list;
    private EditText chatText;
    private Button send;
    private ImageButton sendPic, voice;
    private TextView title, back;
    private String time, message;
    private Bitmap bitmap;
    Intent intent;
    private boolean side = false;
    ListAdapter_ChatArray adapter;
    private int KeyCode;
    Handler xHandler=new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_chat);

        Intent i = getIntent();


        send = (Button) findViewById(R.id.button_chat_send);
        sendPic = (ImageButton) findViewById(R.id.imageButton_camera_chat);
        title = (TextView) findViewById(R.id.textView_setting_title);
        back = (TextView) findViewById(R.id.textView_setting_back);
        chatText = (EditText) findViewById(R.id.editText_chat);
        list = (ListView) findViewById(R.id.listView_chat);
        voice = (ImageButton)findViewById(R.id.imageButton_voiceMwssage_chat);

        title.setText(ValueMessager.providerFirstName+" "+ValueMessager.providerLastName);

        adapter = new ListAdapter_ChatArray(getApplicationContext(), R.layout.list_sample__chat_message, ValueMessager.providerProfileBitmap, ValueMessager.userProfileBitmap);

        sendPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Client_Chat.this,SelectingPicForChat.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(ValueMessager.chatLastPage){
                    case 0:
                        Intent intent = new Intent(Client_Chat.this, Client_Incoming_Services.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(Client_Chat.this, Client_Further_Info.class);
                        startActivity(intent2);
                        break;
                }
            }
        });

        chatText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN) && (KeyCode == KeyEvent.KEYCODE_ENTER)){

                    return sendChatMessage();

                }

                return false;
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });

        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(adapter);

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                list.setSelection(adapter.getCount()-1);
            }
        });


        if(ValueMessager.chatAddMessageFlag == 1){

            Calendar currentTime = Calendar.getInstance();
            String calenderTime = currentTime.getTime().toString();
            adapter.add(new ChatMessage(side = false, "", calenderTime, 1, ValueMessager.bitmapBuffer));
            ValueMessager.chatAddMessageFlag = 0;
        }

        SignalRHubConnection.mHubProxy.on("recieveTextMessage",
                new SubscriptionHandler1<ChatGetMessageViewModel>() {
                    @Override
                    public void run(ChatGetMessageViewModel model) {
                        time = model.getCreateDate();

                        message = model.getMessageContent();

                        xHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                adapter.add(new ChatMessage(side = true, message, time, 0, bitmap));// type0: string, 1:picture, 2:voice
                                chatText.setText("");

                            }
                        });
                    }
                },ChatGetMessageViewModel.class);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        SignalRHubConnection.mHubProxy.on("recieveImageMessage",
                new SubscriptionHandler1<ChatGetMessageViewModel>() {
                    @Override
                    public void run(ChatGetMessageViewModel model) {
                        time = model.getCreateDate();
                        message = model.getMessageContent();
                        System.out.println("xxxxxxxxxxxxxx"+message);

                        xHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                GetImageController controller2 = new GetImageController() {
                                    @Override
                                    public void onResponse(Bitmap mBitmap) {
                                        super.onResponse(bitmap);
                                        if (bitmap == null) {
                                            System.out.println("tttttttttttttttttt");
                                            return;
                                        }
                                        adapter.add(new ChatMessage(side = true, "", time, 1, mBitmap));// type0: string, 1:picture, 2:voice

                                    }
                                };
                                controller2.execute("http://para.co.nz/api/ChatClient/GetChatImage/"+message,"","POST");
                            }
                       });
                    }
                },ChatGetMessageViewModel.class);

    }




    private boolean sendChatMessage() {

        DataSendController controller = new DataSendController() {
            @Override
            public void onResponse(Boolean r) {
                super.onResponse(r);
            }
        };
        String username = ValueMessager.email.toString();
        ChatSendMessageDataConvert convert=new ChatSendMessageDataConvert();
        ChatSendMessageViewModel model=new ChatSendMessageViewModel();
        model.setFromUsername(username);
        model.setToUsername("gaoxin");
        model.setMessageContent(chatText.getText().toString());
        String data= convert.ModelToJson(model);
        controller.execute("http://para.co.nz/api/ChatClient/SendTextMessage", data, "POST");

        Calendar currentTime = Calendar.getInstance();
        String calenderTime = currentTime.getTime().toString();

        adapter.add(new ChatMessage(side = false, chatText.getText().toString(), calenderTime, 0, bitmap));
        chatText.setText("");



        return true;
    }

}

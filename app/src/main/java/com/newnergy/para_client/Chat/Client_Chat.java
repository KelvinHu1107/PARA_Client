package com.newnergy.para_client.Chat;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.newnergy.para_client.ChatSendMessageDataConvert;
import com.newnergy.para_client.ChatSendMessageViewModel;
import com.newnergy.para_client.Client_Further_Info;
import com.newnergy.para_client.Client_PopUp_Version;
import com.newnergy.para_client.Client_dealt_providerInfo;
import com.newnergy.para_client.DataSendController;
import com.newnergy.para_client.DataTransmitController;
import com.newnergy.para_client.Loading_Dialog;
import com.newnergy.para_client.R;
import com.newnergy.para_client.RefreshTokenController;
import com.newnergy.para_client.ValueMessager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.newnergy.para_client.R.id.linearLayout_mainContainer;



public class Client_Chat extends AppCompatActivity {

    private ListView list;
    private EditText chatText;
    private ImageButton sendPic, voice;
    private TextView title;
    private ImageView back, next, send;
    private LinearLayout chatBar, mainContainer;


    Intent intent;
    private boolean side = false;
    ListAdapter_ChatArray adapter;

    private int KeyCode;
    Handler xHandler=new Handler(Looper.getMainLooper());
    //private Chat_SingleChat_db chat_db;
    private List<ChatMessage> listChatReceive,listChatLocal;
    private static final int REQUESTCODE=3;
    Context context = this;
    Loading_Dialog myLoading;

    @Override
    public void onResume()
    {
        super.onResume();
        RefreshTokenController controller = new RefreshTokenController(){
            @Override
            public void response(boolean result) {

                DataTransmitController c = new DataTransmitController() {
                    @Override
                    public void onResponse(String result) {
                        super.onResponse(result);
                        myLoading.CloseLoadingDialog();

                        String outSide[] = result.trim().split("\"");

                        String info1[] = ValueMessager.currentVersion.trim().split("\\.");
                        String info2[] = outSide[1].trim().split("\\.");

                        if(!info1[0].equals(info2[0])){
                            Intent intent = new Intent(Client_Chat.this, Client_PopUp_Version.class);
                            startActivity(intent);
                        }
                    }
                };
                c.execute("http://para.co.nz/api/version/getversion", "", "GET");
            }
        };

        myLoading.ShowLoadingDialog();
        controller.refreshToken(ValueMessager.email.toString(), ValueMessager.refreshToken);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ValueMessager.resolution1080x720)
            setContentView(R.layout.client_chat720x1080);
        else if(ValueMessager.resolution800x480)
            setContentView(R.layout.client_chat480x800);
        else
            setContentView(R.layout.client_chat720x1080);
       //Intent i = getIntent();

        myLoading = new Loading_Dialog();
        myLoading.getContext(this);

        ValueMessager.is_chat=true;
        send = (ImageView) findViewById(R.id.imageView_send);
        sendPic = (ImageButton) findViewById(R.id.imageButton_camera_chat);
        title = (TextView) findViewById(R.id.tree_field_title);
        back = (ImageView) findViewById(R.id.imageView_back);
        next = (ImageView) findViewById(R.id.imageView_ok);
        chatText = (EditText) findViewById(R.id.editText_chat);
        list = (ListView) findViewById(R.id.listView_chat);
        //voice = (ImageButton)findViewById(R.id.imageButton_voiceMwssage_chat);
        chatBar = (LinearLayout) findViewById(R.id.linearLayout_chatBar);
        mainContainer = (LinearLayout) findViewById(linearLayout_mainContainer);

        next.setVisibility(View.INVISIBLE);

        //TODo: niko //problem have to open the chat first then the show chat message//
        title.setText( ValueMessager.providerFirstName+" "+ValueMessager.providerLastName);

        ValueMessager.adapterGlobal= new ListAdapter_ChatArray(getApplicationContext(), R.layout.list_sample__chat_message, ValueMessager.providerProfileBitmap, ValueMessager.userProfileBitmap);
        ValueMessager.adapterGlobal.sortHistory();

        //adapter.clear();

        listChatReceive = new LinkedList<ChatMessage>();
        listChatReceive=  ValueMessager.chat_db_Global.obtainSingleChatList(ValueMessager.providerEmail, ValueMessager.email.toString());

        listChatLocal = new LinkedList<ChatMessage>();
        listChatLocal=  ValueMessager.chat_db_Global.obtainSingleChatList(ValueMessager.email.toString(), ValueMessager.providerEmail);

        for(int c=0;c<listChatReceive.size();c++)
        {
            listChatReceive.get(c).set_side(1);
            ValueMessager.adapterGlobal.add(listChatReceive.get(c));

        }
        for(int j=0;j<listChatLocal.size();j++)
        {
            listChatLocal.get(j).set_side(0);
            ValueMessager.adapterGlobal.add(listChatLocal.get(j));
        }

        sendPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Client_Chat.this,SelectingPicForChat.class);
                startActivityForResult(intent,REQUESTCODE);
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(ValueMessager.chatLastPage){
                    case "Message":
                        ValueMessager.is_chat = false;
                        Intent intent = new Intent(Client_Chat.this, Client_Message.class);
                        startActivity(intent);
                        break;
                    case "FurtherInfo":
                        ValueMessager.is_chat = false;
                        Intent intent2 = new Intent(Client_Chat.this, Client_Further_Info.class);
                        startActivity(intent2);
                        break;
                    case "Details":
                        ValueMessager.is_chat = false;
                        Intent intent3 = new Intent(Client_Chat.this, Client_dealt_providerInfo.class);
                        startActivity(intent3);
                        break;
                }
            }
        });


        mainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(context.INPUT_METHOD_SERVICE);
                boolean isOpen=imm.isActive();

                if(isOpen){

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
                //hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                //hide keyboard
                sendChatMessage();
                scrollMyListViewToBottom();

            }
        });

        ValueMessager.adapterGlobal.sortHistory();
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setAdapter(ValueMessager.adapterGlobal);
        scrollMyListViewToBottom();

        ValueMessager.adapterGlobal.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                scrollMyListViewToBottom();
            }
        });

        scrollMyListViewToBottom();

    }

    private void scrollMyListViewToBottom() {
        list.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                list.setSelection(list.getCount() - 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {

                String imageUrl = data.getStringExtra("ImageUrl");
                SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
                Date curDate = new Date(System.currentTimeMillis()) ;
                String currentDateTimeString = format.format(curDate);
                ChatMessage cm=new ChatMessage(ValueMessager.email.toString(),
                        ValueMessager.providerEmail,
                        imageUrl,1,
                        0, currentDateTimeString,0);

                ValueMessager.chat_db_Global .insertSingleChat(cm);
                ValueMessager.adapterGlobal.add(cm);
                ValueMessager.adapterGlobal.sortHistory();
                list.setAdapter(ValueMessager.adapterGlobal);

            }
        }
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
        //send message and opponent's chat information
        ChatSendMessageViewModel model=new ChatSendMessageViewModel();
        model.setFromUsername(username);
        model.setToUsername(ValueMessager.providerEmail);
        model.setMessageContent(chatText.getText().toString());

        //TODO: niko //add user date//
        //model.set
        String data= convert.ModelToJson(model);
        controller.execute("http://para.co.nz/api/ChatClient/SendTextMessage", data, "POST");

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
        Date curDate = new Date(System.currentTimeMillis()) ;
        String currentDateTimeString = format.format(curDate);

        ChatMessage cm=new ChatMessage(username,
                ValueMessager.providerEmail,
                chatText.getText().toString(),0,
                0,currentDateTimeString,0);

        //kelvinkelvin
        ProviderUserDate pud=new ProviderUserDate(username,ValueMessager.providerEmail,ValueMessager.providerFirstName,ValueMessager.providerLastName,
                "",chatText.getText().toString(),0,currentDateTimeString);

        if(ValueMessager.chat_User_db_Global!=null)
        {
            Boolean isExist= ValueMessager.chat_User_db_Global.isUserExist(username,ValueMessager.providerEmail);
            if (isExist==true)
            {
                ValueMessager.chat_User_db_Global.updataNewMessage(username,ValueMessager.providerEmail,chatText.getText().toString(),0,currentDateTimeString);
            }
            else if (isExist==false)
            {
                ValueMessager.chat_User_db_Global.insertUserData(pud);
            }

        }

        ValueMessager.chat_db_Global.insertSingleChat(cm);
        ValueMessager.adapterGlobal.add(cm);
        list.setAdapter(ValueMessager.adapterGlobal);

        chatText.setText("");
        return true;
    }
}
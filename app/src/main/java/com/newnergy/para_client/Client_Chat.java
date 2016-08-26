package com.newnergy.para_client;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Client_Chat extends AppCompatActivity {

    private ListView list;
    private EditText chatText;
    private Button send;
    private TextView title, back;
    Intent intent;
    private boolean side = false;
    ListAdapter_ChatArray adapter;
    private int KeyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_chat);

        Intent i = getIntent();

        send = (Button) findViewById(R.id.button_chat_send);
        title = (TextView) findViewById(R.id.textView_setting_title);
        chatText = (EditText) findViewById(R.id.editText_chat);
        list = (ListView) findViewById(R.id.listView_chat);

        adapter = new ListAdapter_ChatArray(getApplicationContext(), R.layout.list_sample_message1);

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

    }

    private boolean sendChatMessage() {

        adapter.add(new ChatMessage(side, chatText.getText().toString()));
        System.out.println("yyyyyyyy"+chatText.getText().toString());
        chatText.setText("");

        side = !side;

        return true;
    }

}

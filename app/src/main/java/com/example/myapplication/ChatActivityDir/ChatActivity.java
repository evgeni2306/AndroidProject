package com.example.myapplication.ChatActivityDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;

import com.example.myapplication.DatabaseWork.DbRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MenuActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ArrayList<UserMessage> messages = new ArrayList<UserMessage>();

        Button sendButton = findViewById(R.id.SendMessageButton);
        EditText messageField = findViewById(R.id.MessageField);
        Bundle arguments = getIntent().getExtras();
        String mid = arguments.get("id").toString();
        String chatid = arguments.get("chatid").toString();
        TextView anotherUserName = findViewById(R.id.AnotherUserName);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        DbRequest dbRequest = new DbRequest();
        messages = dbRequest.getAllMessagesInChat(db, chatid);
        RecyclerView recyclerView = findViewById(R.id.messagesList);

        UserMessageAdapter myAdapter = new UserMessageAdapter(this, messages);

        recyclerView.setAdapter(myAdapter);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

        String anotherName = dbRequest.getAnotherUserInChat(db, mid, chatid);
        anotherUserName.setText(anotherName);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (messageField.length() > 0) {

                            dbRequest.createNewMessageInChat(db, mid, chatid, messageField.getText().toString());

                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


    }


}
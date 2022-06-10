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
import com.example.myapplication.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ArrayList<UserMessage> messages = new ArrayList<UserMessage>();

        Button SendButton = findViewById(R.id.SendMessageButton);
        EditText MessageField = findViewById(R.id.MessageField);
        Bundle arguments = getIntent().getExtras();
        String mid = arguments.get("id").toString();
        String chatid = arguments.get("chatid").toString();
        TextView AnotherUserName = findViewById(R.id.AnotherUserName);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        DbRequest dbRequest = new DbRequest();

        messages = dbRequest.GetAllMessagesInChat(db, chatid);

        String Anothername = dbRequest.GetAnotherUserInChat(db, mid, chatid);

        AnotherUserName.setText(Anothername);

        RecyclerView recyclerView = findViewById(R.id.messagesList);

        UserMessageAdapter MyAdapter = new UserMessageAdapter(this, messages);

        recyclerView.setAdapter(MyAdapter);

        SendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MessageField.length() > 0) {

                    dbRequest.CreateNewMessageInChat(db, mid, chatid, MessageField.getText().toString());

                    finish();
                    startActivity(getIntent());
                }
            }
        });

    }


}
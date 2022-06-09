package com.example.myapplication.ChatListActivityDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseWork.DbRequest;
import com.example.myapplication.R;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Intent;

import com.example.myapplication.ChatActivityDir.ChatActivity;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {

    ArrayList<ChatList> ChatLists = new ArrayList<ChatList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        Bundle arguments = getIntent().getExtras();
        String mid = arguments.get("id").toString();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        DbRequest dbRequest = new DbRequest();
        ChatLists = dbRequest.ChatListGetCurrentUserChats(db, mid);
        RecyclerView recyclerView = findViewById(R.id.ChatList);
        ChatListClassAdapter.OnStateClickListener stateClickListener = new ChatListClassAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(ChatList getid, int position) {
                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                intent.putExtra("id", mid);
                intent.putExtra("chatid", getid.getid());
                startActivity(intent);

            }
        };

        ChatListClassAdapter MyAdapter = new ChatListClassAdapter(this, ChatLists, stateClickListener);
        recyclerView.setAdapter(MyAdapter);
    }
}
package com.example.myapplication.ChatListActivityDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChatActivityDir.UserMessage;
import com.example.myapplication.DatabaseWork.DbRequest;
import com.example.myapplication.R;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Intent;
import android.os.StrictMode;

import com.example.myapplication.ChatActivityDir.ChatActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ChatListActivity extends AppCompatActivity {

    ArrayList<ChatList> ChatLists = new ArrayList<ChatList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        Bundle arguments = getIntent().getExtras();
        String mid = arguments.get("id").toString();
        DbRequest dbRequest = new DbRequest(this);
        RecyclerView recyclerView = findViewById(R.id.ChatList);

        Callable task = () -> {
            return  dbRequest.chatListGetCurrentUserChats( mid);
        };
        FutureTask<ArrayList<ChatList>> chatList = new FutureTask<ArrayList<ChatList>>(task);
        new Thread(chatList).start();
        try {
            ChatLists =  chatList.get();
            chatList.isDone();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ChatListClassAdapter.OnStateClickListener stateClickListener = new ChatListClassAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(ChatList getid, int position) {
                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                intent.putExtra("id", mid);
                intent.putExtra("chatid", getid.getid());
                startActivity(intent);

            }
        };

        ChatListClassAdapter myAdapter = new ChatListClassAdapter(this, ChatLists, stateClickListener);
        recyclerView.setAdapter(myAdapter);
    }
}
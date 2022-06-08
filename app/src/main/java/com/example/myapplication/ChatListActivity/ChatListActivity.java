package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Intent;

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

        Cursor query = db.rawQuery("SELECT chatid FROM usersinchats  WHERE userid ="+mid, null);
        for (Integer i = 0; i< query.getCount(); i++){
            if(query.moveToPosition(i)){
                Cursor query1 = db.rawQuery("SELECT userid FROM usersinchats  WHERE chatid ="+query.getString(0), null);
                for (Integer s = 0; s< query1.getCount(); s++){
                    if(query1.moveToPosition(s)) {
                        Cursor query2 = db.rawQuery("SELECT name,surname FROM users  WHERE id =" + query1.getString(0) + " AND id !=" + mid, null);
                        if(query2.moveToFirst()){
                            ChatLists.add(new ChatList(query2.getString(0), query2.getString(1), query.getString(0)));

                            query2.close();
                        }

                    }
                }
                query1.close();
            }
        }



        RecyclerView recyclerView = findViewById(R.id.ChatList);


        ChatListClassAdapter.OnStateClickListener stateClickListener = new ChatListClassAdapter.OnStateClickListener() {
            @Override
            public  void onStateClick(ChatList getid, int position) {
                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                intent.putExtra("id", mid);
                intent.putExtra("chatid", getid.getid());
                startActivity(intent);

            }
        };

        ChatListClassAdapter MyAdapter = new ChatListClassAdapter(this, ChatLists,stateClickListener);
        recyclerView.setAdapter(MyAdapter);
    }
}
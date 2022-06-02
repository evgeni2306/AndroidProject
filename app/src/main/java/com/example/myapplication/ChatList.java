package com.example.myapplication;

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

import java.util.ArrayList;

public class ChatList extends AppCompatActivity {

    ArrayList<ChatListClass> ChatLists = new ArrayList<ChatListClass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        Bundle arguments = getIntent().getExtras();
        String mid = arguments.get("id").toString();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        Cursor query = db.rawQuery("SELECT name,surname,chatid,userid FROM usersinchats LEFT JOIN users WHERE userid ="+mid, null);

        for (Integer i = 0; i< query.getCount(); i++){
            if(query.moveToPosition(i)){
                System.out.println(query.getString(1));
//                if(query.getString(3).toString() != mid.toString()){
//                    ChatLists.add(new ChatListClass(query.getString(0),query.getString(1),query.getString(2)));
//                }

            }
        }



        RecyclerView recyclerView = findViewById(R.id.ChatList);


        ChatListClassAdapter.OnStateClickListener stateClickListener = new ChatListClassAdapter.OnStateClickListener() {
            @Override
            public  void onStateClick(ChatListClass getid, int position) {
                Intent intent = new Intent(ChatList.this, Chat.class);
                intent.putExtra("id", mid);
                intent.putExtra("chatid", getid.getid());
                startActivity(intent);

            }
        };

        ChatListClassAdapter MyAdapter = new ChatListClassAdapter(this, ChatLists,stateClickListener);
        recyclerView.setAdapter(MyAdapter);
    }
}
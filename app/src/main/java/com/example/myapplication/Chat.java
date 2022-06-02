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

public class Chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ArrayList<UserMessage> messages = new ArrayList<UserMessage>();

        Bundle arguments = getIntent().getExtras();
        String mid = arguments.get("id").toString();
        String aid = arguments.get("anotherUserId").toString();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        db.execSQL("INSERT OR IGNORE INTO chats VALUES (1)");
        db.execSQL("INSERT OR IGNORE INTO usersinchats VALUES ("+ "1,'" + mid + "'," + "1)");
        db.execSQL("INSERT OR IGNORE INTO usersinchats VALUES ("+ "1,'" + aid + "',"  + "1)");
        db.execSQL("INSERT OR IGNORE INTO messages VALUES ("+ "1,'" + aid + "',1," + "'privet')");

        Cursor query = db.rawQuery("SELECT * FROM messages WHERE chatid = 1", null);
        if (query.moveToFirst()) {
            String name = query.getString(0);
            String name2 = query.getString(1);
            String name3 = query.getString(2);
            String name4 = query.getString(3);
            System.out.println(name + "_" + name2 + "_" + name3 + "_" + name4);
            messages.add(new UserMessage ("Петр", "Петров", "privet"));
        }

        RecyclerView recyclerView = findViewById(R.id.messagesList);
        UserMessageAdapter MyAdapter = new UserMessageAdapter(this, messages);

        recyclerView.setAdapter(MyAdapter);

    }

}
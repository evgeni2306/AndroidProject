package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;
import com.example.myapplication.UserSearchActivityDir.SearchActivity;
import com.example.myapplication.ChatListActivityDir.ChatListActivity;
import com.example.myapplication.DatabaseWork.DbRequest;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        TextView GreetingText = findViewById(R.id.Greetingtext);
        Button SearchButton = findViewById(R.id.Search);
        Button ChatListButton = findViewById(R.id.Chats);
        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("id").toString();


        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        DbRequest dbRequest = new DbRequest();

        String username = dbRequest.MenuGetUserName(db,id);
            GreetingText.setText("Здравствуйте " + username);


        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SearchActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        ChatListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ChatListActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


    }
}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TextView GreetingSearch = findViewById(R.id.GreetingSearch);

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("id").toString();
        RecyclerView UserList = findViewById(R.id.ListUsers);
        UserList.setHasFixedSize(false);
        RecyclerView.LayoutManager Lm = new LinearLayoutManager(this);
        UserList.setLayoutManager(Lm);
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT name FROM users WHERE id !=" + id, null);
//        if (query.moveToFirst()) {
//            String name = query.getString(0);
//            String surname = query.getString(1);
//
//        }
        System.out.println(query.getCount());
//        GreetingSearch.setText(query.getCount());
    }
}
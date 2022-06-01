package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    ArrayList<UserSearch> users = new ArrayList<UserSearch>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        TextView GreetingSearch = findViewById(R.id.GreetingSearch);

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("id").toString();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT name,surname,id FROM users WHERE id !=" + id, null);
        for (Integer i = 0; i< query.getCount(); i++){
            if(query.moveToPosition(i)){
                users.add(new UserSearch(query.getString(0),query.getString(1),query.getString(2)));
            }
        }
//        if (query.moveToFirst()) {
//            String name = query.getString(0);
//            String surname = query.getString(1);
//
//        }
//        System.out.println(query.getCount());
        GreetingSearch.setText("Найдено пользователей: " + query.getCount());


        setInitialData();
        RecyclerView recyclerView = findViewById(R.id.ListUsers);
        UserSearchAdapter MyAdapter = new UserSearchAdapter(this, users);

        recyclerView.setAdapter(MyAdapter);

    }
    private void setInitialData(){
        users.add(new UserSearch("имяsd","Фамилияsd","Айдиsd"));
        users.add(new UserSearch("имя1","Фамилия2","Айди3"));


    }
}
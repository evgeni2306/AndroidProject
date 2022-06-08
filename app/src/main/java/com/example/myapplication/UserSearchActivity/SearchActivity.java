package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

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
        GreetingSearch.setText("Найдено пользователей: " + query.getCount());


        RecyclerView recyclerView = findViewById(R.id.ListUsers);

        UserSearchAdapter.OnStateClickListener stateClickListener = new UserSearchAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(UserSearch userSearch, int position) {
                Intent intent = new Intent(SearchActivity.this, ChatActivity.class);

                SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

                Cursor query3  = db.rawQuery("SELECT id FROM usersinchats ", null);
                Integer fid = 0;
                Integer sid =0;
                Integer chid =0;
                if (query3.moveToLast()){
                    fid = query.getInt(0)+1;
                    sid = fid+1;
                }else{
                    fid = 1;
                sid = fid+1;}

                Cursor query34  = db.rawQuery("SELECT id FROM chats ", null);
                if (query3.moveToLast()){
                    chid = query.getInt(0)+1;
                }else{
                    chid = 1;}
                db.execSQL("INSERT OR IGNORE INTO chats VALUES (" +chid+")");
                db.execSQL("INSERT OR IGNORE INTO usersinchats VALUES ("+ fid + ",'" + id + "'," + chid+")");

                db.execSQL("INSERT OR IGNORE INTO usersinchats VALUES ("+ sid + ",'" + userSearch.getid() + "',"  + chid+")");

                intent.putExtra("id", id);
                intent.putExtra("chatid", 1);
                startActivity(intent);



            }
        };
        UserSearchAdapter MyAdapter = new UserSearchAdapter(this, users,stateClickListener);

        recyclerView.setAdapter(MyAdapter);

    }
}
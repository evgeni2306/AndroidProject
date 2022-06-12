package com.example.myapplication.UserSearchActivityDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseWork.DbRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MenuActivity;
import com.example.myapplication.R;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;

import com.example.myapplication.ChatActivityDir.ChatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ArrayList<UserSearch> users = new ArrayList<UserSearch>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        TextView greetingSearch = findViewById(R.id.GreetingSearch);

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("id").toString();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        DbRequest dbRequest = new DbRequest();
        users = dbRequest.userSearchGetUsers(db, id);
        greetingSearch.setText("Найдено пользователей: " + users.size());




        RecyclerView recyclerView = findViewById(R.id.ListUsers);

        UserSearchAdapter.OnStateClickListener stateClickListener = new UserSearchAdapter.OnStateClickListener() {
            @Override
            public void onStateClick(UserSearch userSearch, int position) {

                Intent intent = new Intent(SearchActivity.this, ChatActivity.class);
                intent.putExtra("id", id);

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                Integer check = dbRequest.userSearchCheckChatExist(db,id,userSearch.getid());
                if (check == 0){

                    Integer fid = dbRequest.userSearchGetLastUserInChatId(db);
                    Integer sid = fid+1;
                    Integer chid = dbRequest.userSearchGetLastChatId(db);
                    dbRequest.userSearchCreateChat(db,chid);
                    dbRequest.userSearchCreateUserInChat(db,fid,id,chid);
                    dbRequest.userSearchCreateUserInChat(db,sid,userSearch.getid(),chid);

                    intent.putExtra("chatid", chid);

                }else{
                    intent.putExtra("chatid", check);
                }
                startActivity(intent);
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();

            }
        };
        UserSearchAdapter myAdapter = new UserSearchAdapter(this, users, stateClickListener);

        recyclerView.setAdapter(myAdapter);

    }
}
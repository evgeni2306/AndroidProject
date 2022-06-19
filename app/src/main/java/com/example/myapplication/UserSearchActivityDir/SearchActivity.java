package com.example.myapplication.UserSearchActivityDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChatListActivityDir.ChatList;
import com.example.myapplication.DatabaseWork.DbRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MenuActivity;
import com.example.myapplication.R;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.StrictMode;
import android.widget.TextView;
import android.content.Intent;

import com.example.myapplication.ChatActivityDir.ChatActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SearchActivity extends AppCompatActivity {

    ArrayList<UserSearch> users = new ArrayList<UserSearch>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TextView greetingSearch = findViewById(R.id.GreetingSearch);
        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("id").toString();
        DbRequest dbRequest = new DbRequest(this);


//        Callable task1 = () -> {
//            return dbRequest.dataBaseConnect(this);
//        };
//        FutureTask<SQLiteDatabase> dbb = new FutureTask<SQLiteDatabase>(task1);
//        new Thread(dbb).start();
//        try {
//            SQLiteDatabase  db = dbb.get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Callable task = () -> {
            return dbRequest.userSearchGetUsers(id);
        };
        FutureTask<ArrayList<UserSearch>> userss = new FutureTask<ArrayList<UserSearch>>(task);
        new Thread(userss).start();
        try {
            users = userss.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
                        Integer check = dbRequest.userSearchCheckChatExist( id, userSearch.getid());
                        if (check == 0) {

                            Integer fid = dbRequest.userSearchGetLastUserInChatId();
                            Integer sid = fid + 1;
                            Integer chid = dbRequest.userSearchGetLastChatId();
                            dbRequest.userSearchCreateChat( chid);
                            dbRequest.userSearchCreateUserInChat( fid, id, chid);
                            dbRequest.userSearchCreateUserInChat( sid, userSearch.getid(), chid);

                            intent.putExtra("chatid", chid);

                        } else {
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
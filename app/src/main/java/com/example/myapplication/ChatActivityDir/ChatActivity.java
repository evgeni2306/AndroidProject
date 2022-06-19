package com.example.myapplication.ChatActivityDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;
 import java.util.concurrent.Callable;
import com.example.myapplication.DatabaseWork.DbRequest;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MenuActivity;
import com.example.myapplication.R;
import java.util.concurrent.FutureTask;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ArrayList<UserMessage> messages = new ArrayList<UserMessage>();

        Button sendButton = findViewById(R.id.SendMessageButton);
        EditText messageField = findViewById(R.id.MessageField);
        Bundle arguments = getIntent().getExtras();
        String mid = arguments.get("id").toString();
        String chatid = arguments.get("chatid").toString();
        TextView anotherUserName = findViewById(R.id.AnotherUserName);
        DbRequest dbRequest = new DbRequest(this);

        RecyclerView recyclerView = findViewById(R.id.messagesList);

            Callable task = () -> {
                return dbRequest.getAllMessagesInChat( chatid);
            };
            FutureTask<ArrayList<UserMessage>> message = new FutureTask<ArrayList<UserMessage>>(task);
            new Thread(message).start();
        try {
            messages = message.get();
            message.isDone();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        UserMessageAdapter myAdapter = new UserMessageAdapter(this, messages);
        recyclerView.setAdapter(myAdapter);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

        String anotherName = dbRequest.getAnotherUserInChat( mid, chatid);
        anotherUserName.setText(anotherName);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (messageField.length() > 0) {

                            dbRequest.createNewMessageInChat( mid, chatid, messageField.getText().toString());

                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


    }


}
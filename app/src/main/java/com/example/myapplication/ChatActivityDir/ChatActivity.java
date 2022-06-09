package com.example.myapplication.ChatActivityDir;

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
import com.example.myapplication.R;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ArrayList<UserMessage> messages = new ArrayList<UserMessage>();

        Button SendButton = findViewById(R.id.SendMessageButton);
        EditText MessageField = findViewById(R.id.MessageField);
        Bundle arguments = getIntent().getExtras();
        String mid = arguments.get("id").toString();
        String cid = arguments.get("chatid").toString();
        TextView AnotherUserName = findViewById(R.id.AnotherUserName);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);


        Cursor query = db.rawQuery("SELECT * FROM messages WHERE chatid = 1", null);
        for (Integer i = 0; i< query.getCount(); i++) {
            if (query.moveToPosition(i)) {
                Cursor query1 = db.rawQuery("SELECT name,surname FROM users WHERE id =" + query.getString(1), null);
                if (query1.moveToFirst()) {
                    messages.add(new UserMessage(query1.getString(0), query1.getString(1), query.getString(3)));
                    query1.close();
                }
            }
        }
String Anothername = "";
        Cursor query1 = db.rawQuery("SELECT userid FROM usersinchats WHERE chatid = 1 AND userid!=" + mid, null);
        if (query1.moveToFirst()){
            Anothername = query1.getString(0);
        }
        query1.close();
        Cursor query2 = db.rawQuery("SELECT name,surname FROM users WHERE id =" + Anothername.toString() , null);

        if (query2.moveToFirst()){
            Anothername = query2.getString(0) + " " + query2.getString(1);
            query2.close();
        }
        AnotherUserName.setText(Anothername);
        RecyclerView recyclerView = findViewById(R.id.messagesList);
        UserMessageAdapter MyAdapter = new UserMessageAdapter(this, messages);

        recyclerView.setAdapter(MyAdapter);

        SendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MessageField.length()>0){


                    Cursor query3  = db.rawQuery("SELECT id FROM messages WHERE chatid = 1", null);
                    Integer mesid = 0;
                    if (query3.moveToLast()){
                          mesid = query.getInt(0)+1;
                    }else{
                       mesid = 1;}

                    db.execSQL("INSERT OR IGNORE INTO messages VALUES ("+ mesid +","+mid +"," +cid+ ",'"+MessageField.getText().toString()+"')");
                    finish();
                    startActivity(getIntent());
                }
            }
        });

    }


}
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle arguments = getIntent().getExtras();

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT name,surname FROM users WHERE id ="+arguments.get("id").toString(), null);
        if (query.moveToFirst()) {
            String name = query.getString(0);
            String surname = query.getString(1);
            TextView GreetingText = findViewById(R.id.Greetingtext);
            GreetingText.setText( "Здравствуйте " +name +" "+ surname);
        }

//        Bundle arguments = getIntent().getExtras();
//        System.out.println(arguments.get("id"));
    }
}
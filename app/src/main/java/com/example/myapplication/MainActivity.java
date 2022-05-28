package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.util.*;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER UNIQUE  ,email TEXT,name TEXT,surname TEXT, password TEXT," +
                "PRIMARY KEY(\"id\" AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS chats (id INTEGER UNIQUE,   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS messages (id INTEGER UNIQUE,creatorid INTEGER, chatid INTEGER, text TEXT,FOREIGN KEY(creatorid) REFERENCES users(id),FOREIGN KEY(chatid) REFERENCES chats(id),   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS usersinchats (id INTEGER UNIQUE, userid INTEGER, chatid INTEGER,  FOREIGN KEY(userid) REFERENCES users(id),FOREIGN KEY(chatid) REFERENCES chats(id),   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
//        db.execSQL("DROP TABLE users");
//        db.execSQL("DROP TABLE messages");
//        db.execSQL("DROP TABLE chats");
//        db.execSQL("DROP TABLE usersinchats");


        Button RegistrationFormButton = findViewById(R.id.RegistrationFormButton);
        EditText EmailField = findViewById(R.id.RegistrationEmail);
        EditText NameField = findViewById(R.id.RegistrationName);
        EditText SurnameField = findViewById(R.id.RegistrationSurname);
        EditText PasswordField = findViewById(R.id.RegistrationPassword);
        TextView MessageField = findViewById(R.id.RegistrationMessage);


        RegistrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailField.getText().length()>0 && NameField.getText().length()>0 && SurnameField.getText().length()>0 && PasswordField.getText().length()>0) {
                    db.execSQL("INSERT OR IGNORE INTO users VALUES (" +
                            "4 ," +
                            "'" + EmailField.getText().toString() + "'" + "," +
                            "'" + NameField.getText().toString() + "'" + "," +
                            "'" + SurnameField.getText().toString() + "'" + "," +
                            "'" + PasswordField.getText().toString() + "'" +
                            ")");
                    Cursor query = db.rawQuery("SELECT * FROM users;", null);
//                    if (query.moveToFirst()) {
//
//                        String name = query.getString(1);
//                        System.out.println(name);
//                    }
                } else {
//                    System.out.println("asd");
                    MessageField.setText("Заполните все поля");
                }



            }
        });
    }
}
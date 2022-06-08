package com.example.myapplication.DatabaseWork;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MenuActivity;

public class DbRequest {

    public void CreateTables(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER UNIQUE  ,email TEXT,name TEXT,surname TEXT, password TEXT," +
                "PRIMARY KEY(\"id\" AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS chats (id INTEGER UNIQUE,   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS messages (id INTEGER UNIQUE,creatorid INTEGER, chatid INTEGER, text TEXT,FOREIGN KEY(creatorid) REFERENCES users(id),FOREIGN KEY(chatid) REFERENCES chats(id),   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS usersinchats (id INTEGER UNIQUE, userid INTEGER, chatid INTEGER,  FOREIGN KEY(userid) REFERENCES users(id),FOREIGN KEY(chatid) REFERENCES chats(id),   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        AddDefaultUser(db);
    }
    public void DropDatabase(SQLiteDatabase db){
                db.execSQL("DROP TABLE users");
        db.execSQL("DROP TABLE messages");
        db.execSQL("DROP TABLE chats");
        db.execSQL("DROP TABLE usersinchats");
    }
    public Integer AddNewUser(SQLiteDatabase db, String email, String name, String surname, String password ){

        Integer lastid = 0;
        Cursor query = db.rawQuery("SELECT id FROM users ", null);
        if (query.moveToLast()) {
             lastid = query.getInt(0)+1;
        }
        db.execSQL("INSERT OR IGNORE INTO users VALUES (" +
                lastid + "," +
                "'" + email + "'" + "," +
                "'" + name + "'" + "," +
                "'" + surname + "'" + "," +
                "'" + password + "'" +
                ")");

        return lastid;
    }

    public void AddDefaultUser(SQLiteDatabase db){

        db.execSQL("INSERT OR IGNORE INTO users VALUES (" +
                "1," +
                "'" + "email@mail.ru" + "'" + "," +
                "'" + "Петр" + "'" + "," +
                "'" + "Петров" + "'" + "," +
                "'" + "12345" + "'" +
                ")");
    }
}

package com.example.myapplication.DatabaseWork;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.TextView;
import android.content.Intent;

import com.example.myapplication.ChatActivityDir.UserMessage;
import com.example.myapplication.ChatListActivityDir.ChatList;
import com.example.myapplication.MainActivity;
import com.example.myapplication.MenuActivity;
import com.example.myapplication.UserSearchActivityDir.UserSearch;

import java.util.ArrayList;

public class DbRequest {

    public void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER UNIQUE  ,email TEXT,name TEXT,surname TEXT, password TEXT," +
                "PRIMARY KEY(\"id\" AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS chats (id INTEGER UNIQUE,   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS messages (id INTEGER UNIQUE,creatorid INTEGER, chatid INTEGER, text TEXT,FOREIGN KEY(creatorid) REFERENCES users(id),FOREIGN KEY(chatid) REFERENCES chats(id),   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS usersinchats (id INTEGER UNIQUE, userid INTEGER, chatid INTEGER,  FOREIGN KEY(userid) REFERENCES users(id),FOREIGN KEY(chatid) REFERENCES chats(id),   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        addDefaultUser(db);
    }

    public void dropDatabase(SQLiteDatabase db) {
        db.execSQL("DROP TABLE users");
        db.execSQL("DROP TABLE messages");
        db.execSQL("DROP TABLE chats");
        db.execSQL("DROP TABLE usersinchats");
    }

    public Integer addNewUser(SQLiteDatabase db, String email, String name, String surname, String password) {

        Integer lastid = 0;
        Cursor query = db.rawQuery("SELECT id FROM users ", null);
        if (query.moveToLast()) {
            lastid = query.getInt(0) + 1;
        }
        db.execSQL("INSERT OR IGNORE INTO users VALUES (" +
                lastid + "," +
                "'" + email + "'" + "," +
                "'" + name + "'" + "," +
                "'" + surname + "'" + "," +
                "'" + password + "'" +
                ")");
        query.close();
        return lastid;
    }

    public void addDefaultUser(SQLiteDatabase db) {

        db.execSQL("INSERT OR IGNORE INTO users VALUES (" +
                "1," +
                "'" + "email@mail.ru" + "'" + "," +
                "'" + "Петр" + "'" + "," +
                "'" + "Петров" + "'" + "," +
                "'" + "12345" + "'" +
                ")");
    }

    public String menuGetUserName(SQLiteDatabase db, String id) {
        Cursor query = db.rawQuery("SELECT name,surname FROM users WHERE id =" + id, null);
        if (query.moveToFirst()) {
            String name = query.getString(0);
            String surname = query.getString(1);
            query.close();
            return name + " " + surname;
        } else {
            query.close();
            return null;
        }
    }

    public ArrayList<UserSearch> userSearchGetUsers(SQLiteDatabase db, String id) {
        ArrayList<UserSearch> users = new ArrayList<UserSearch>();

        Cursor query = db.rawQuery("SELECT name,surname,id FROM users WHERE id !=" + id, null);
        for (Integer i = 0; i < query.getCount(); i++) {
            if (query.moveToPosition(i)) {
                users.add(new UserSearch(query.getString(0), query.getString(1), query.getString(2)));
            }
        }
        query.close();
        return users;
    }

    public Integer userSearchGetLastUserInChatId(SQLiteDatabase db) {
        Cursor query3 = db.rawQuery("SELECT id FROM usersinchats ", null);
        Integer fid = 0;
        if (query3.moveToLast()) {
            fid = query3.getInt(0) + 1;
            query3.close();
            return fid;
        } else {
            fid = 1;
            query3.close();
            return fid;
        }
    }

    public Integer userSearchCheckChatExist(SQLiteDatabase db, String mid, String aid) {
        Integer chatid =0;
        Cursor query34 = db.rawQuery("SELECT id FROM usersinchats WHERE userid = " + mid, null);
        for (Integer i = 0; i < query34.getCount(); i++) {
            if (query34.moveToPosition(i)) {
               Integer id = query34.getInt(0);

                Cursor query3 = db.rawQuery("SELECT id FROM usersinchats WHERE userid = " + aid + " AND chatid = " + id, null);
                if (query3.moveToFirst()){

                  chatid = id;
                return chatid;

                }
                query3.close();
            }
        }
        return chatid;
    }

    public Integer userSearchGetLastChatId(SQLiteDatabase db) {
        Cursor query34 = db.rawQuery("SELECT id FROM chats ", null);
        Integer chid = 0;
        if (query34.moveToLast()) {
            chid = query34.getInt(0) + 1;
            query34.close();
            return chid;
        } else {
            chid = 1;
            query34.close();
            return chid;
        }
    }

    public void userSearchCreateChat(SQLiteDatabase db, Integer chid) {
        db.execSQL("INSERT OR IGNORE INTO chats VALUES (" + chid + ")");
    }

    public void userSearchCreateUserInChat(SQLiteDatabase db, Integer fid, String id, Integer chid) {
        db.execSQL("INSERT OR IGNORE INTO usersinchats VALUES (" + fid + ",'" + id + "'," + chid + ")");
    }

    public ArrayList<ChatList> chatListGetCurrentUserChats(SQLiteDatabase db, String mid) {

        ArrayList<ChatList> chatLists = new ArrayList<ChatList>();

        Cursor query = db.rawQuery("SELECT chatid FROM usersinchats  WHERE userid =" + mid, null);
        for (Integer i = 0; i < query.getCount(); i++) {
            if (query.moveToPosition(i)) {
                Cursor query1 = db.rawQuery("SELECT userid FROM usersinchats  WHERE chatid =" + query.getString(0), null);
                for (Integer s = 0; s < query1.getCount(); s++) {
                    if (query1.moveToPosition(s)) {
                        Cursor query2 = db.rawQuery("SELECT name,surname FROM users  WHERE id =" + query1.getString(0) + " AND id !=" + mid, null);
                        if (query2.moveToFirst()) {
                            chatLists.add(new ChatList(query2.getString(0), query2.getString(1), query.getString(0)));

                            query2.close();
                        }

                    }
                }
                query1.close();

            }

        }
        return chatLists;
    }
    public ArrayList<UserMessage> getAllMessagesInChat(SQLiteDatabase db,String chatid){
        ArrayList<UserMessage> messages = new ArrayList<UserMessage>();

        Cursor query = db.rawQuery("SELECT * FROM messages WHERE chatid = " + chatid, null);
        for (Integer i = 0; i < query.getCount(); i++) {
            if (query.moveToPosition(i)) {
                Cursor query1 = db.rawQuery("SELECT name,surname FROM users WHERE id =" + query.getString(1), null);
                if (query1.moveToFirst()) {
                    messages.add(new UserMessage(query1.getString(0), query1.getString(1), query.getString(3)));
                    query1.close();
                }
            }
        }
        return messages;
    }
    public String getAnotherUserInChat(SQLiteDatabase db, String mid,String chatid){
        String Anothername = "";
        Cursor query1 = db.rawQuery("SELECT userid FROM usersinchats WHERE chatid = " + chatid + " AND userid!=" + mid, null);
        if (query1.moveToFirst()) {
            Anothername = query1.getString(0);
        }
        query1.close();

        Cursor query2 = db.rawQuery("SELECT name,surname FROM users WHERE id = " + Anothername.toString(), null);
        if (query2.moveToFirst()) {
            Anothername = query2.getString(0) + " " + query2.getString(1);
            query2.close();
        }
        return Anothername;
    }

    public void createNewMessageInChat(SQLiteDatabase db,String mid, String chatid, String text){
        Cursor query3 = db.rawQuery("SELECT id FROM messages ", null);
        Integer mesid = 0;
        if (query3.moveToLast()) {
            mesid = query3.getInt(0) + 1;
        } else {
            mesid = 1;
        }

        db.execSQL("INSERT OR IGNORE INTO messages VALUES (" + mesid + "," + mid + "," + chatid + ",'" + text + "')");

    }
}

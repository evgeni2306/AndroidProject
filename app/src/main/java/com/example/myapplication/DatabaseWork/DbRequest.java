package com.example.myapplication.DatabaseWork;

import static android.content.Context.MODE_PRIVATE;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import com.example.myapplication.DatabaseWork.DbRequest;
import com.example.myapplication.ChatActivityDir.UserMessage;
import com.example.myapplication.ChatListActivityDir.ChatList;
import com.example.myapplication.UserSearchActivityDir.UserSearch;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class DbRequest {
    Context context;

    public DbRequest(Context context) {
        this.context = context;
    }

//    public SQLiteDatabase dataBaseConnect(Context context){
//                Callable task1 = () -> {
//            return context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
//        };
//        FutureTask<SQLiteDatabase> dbb = new FutureTask<SQLiteDatabase>(task1);
//        new Thread(dbb).start();
//        try {
//            SQLiteDatabase  db = dbb.get();
//
//            return db;
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
////        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
//       return null;
//    }

    public void createTables() {
//        SQLiteDatabase db = dataBaseConnect(context);
                SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER UNIQUE  ,email TEXT,name TEXT,surname TEXT, password TEXT," +
                "PRIMARY KEY(\"id\" AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS chats (id INTEGER UNIQUE,   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS messages (id INTEGER UNIQUE,creatorid INTEGER, chatid INTEGER, text TEXT,FOREIGN KEY(creatorid) REFERENCES users(id),FOREIGN KEY(chatid) REFERENCES chats(id),   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.execSQL("CREATE TABLE IF NOT EXISTS usersinchats (id INTEGER UNIQUE, userid INTEGER, chatid INTEGER,  FOREIGN KEY(userid) REFERENCES users(id),FOREIGN KEY(chatid) REFERENCES chats(id),   PRIMARY KEY(\"id\"  AUTOINCREMENT))");
        db.close();
        addDefaultUser();

    }

    public void dropDatabase() {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("DROP TABLE users");
        db.execSQL("DROP TABLE messages");
        db.execSQL("DROP TABLE chats");
        db.execSQL("DROP TABLE usersinchats");
        db.close();
    }

    public Integer addNewUser( String email, String name, String surname, String password) {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
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
        db.close();
        return lastid;
    }

    public void addDefaultUser() {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("INSERT OR IGNORE INTO users VALUES (" +
                "1," +
                "'" + "email@mail.ru" + "'" + "," +
                "'" + "Петр" + "'" + "," +
                "'" + "Петров" + "'" + "," +
                "'" + "12345" + "'" +
                ")");
        db.close();
    }

    public String menuGetUserName( String id) {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT name,surname FROM users WHERE id =" + id, null);
        if (query.moveToFirst()) {
            String name = query.getString(0);
            String surname = query.getString(1);
            db.close();
            query.close();
            return name + " " + surname;
        } else {
            query.close();
            db.close();
            return null;
        }
    }

    public ArrayList<UserSearch> userSearchGetUsers( String id) {
        ArrayList<UserSearch> users = new ArrayList<UserSearch>();
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT name,surname,id FROM users WHERE id !=" + id, null);
        for (Integer i = 0; i < query.getCount(); i++) {
            if (query.moveToPosition(i)) {
                users.add(new UserSearch(query.getString(0), query.getString(1), query.getString(2)));
            }
        }
        query.close();
        db.close();
        return users;
    }

    public Integer userSearchGetLastUserInChatId() {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query3 = db.rawQuery("SELECT id FROM usersinchats ", null);
        Integer fid = 0;
        if (query3.moveToLast()) {
            fid = query3.getInt(0) + 1;
            query3.close();
            db.close();
            return fid;
        } else {
            fid = 1;
            query3.close();
            db.close();
            return fid;
        }
    }

    public Integer userSearchCheckChatExist( String mid, String aid) {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
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
        query34.close();
        db.close();
        return chatid;
    }

    public Integer userSearchGetLastChatId() {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query34 = db.rawQuery("SELECT id FROM chats ", null);
        Integer chid = 0;
        if (query34.moveToLast()) {
            chid = query34.getInt(0) + 1;
            query34.close();
            db.close();
            return chid;
        } else {
            chid = 1;
            query34.close();
            db.close();
            return chid;
        }
    }

    public void userSearchCreateChat( Integer chid) {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("INSERT OR IGNORE INTO chats VALUES (" + chid + ")");
        db.close();
    }

    public void userSearchCreateUserInChat( Integer fid, String id, Integer chid) {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("INSERT OR IGNORE INTO usersinchats VALUES (" + fid + ",'" + id + "'," + chid + ")");
        db.close();
    }

    public ArrayList<ChatList> chatListGetCurrentUserChats( String mid) {
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
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

                        }query2.close();

                    }
                }

                query1.close();

            }

        }
        query.close();
        db.close();
        return chatLists;
    }
    public ArrayList<UserMessage> getAllMessagesInChat(String chatid){
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
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
        query.close();
        db.close();
        return messages;
    }
    public String getAnotherUserInChat( String mid,String chatid){
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
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
        db.close();
        return Anothername;
    }

    public void createNewMessageInChat(String mid, String chatid, String text){
        SQLiteDatabase db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Cursor query3 = db.rawQuery("SELECT id FROM messages ", null);
        Integer mesid = 0;
        if (query3.moveToLast()) {
            mesid = query3.getInt(0) + 1;
        } else {
            mesid = 1;
        }
query3.close();
        db.execSQL("INSERT OR IGNORE INTO messages VALUES (" + mesid + "," + mid + "," + chatid + ",'" + text + "')");
        db.close();
    }
}

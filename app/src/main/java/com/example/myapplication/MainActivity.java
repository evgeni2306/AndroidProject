package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.util.*;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS users (email TEXT,name TEXT,surname TEXT, password TEXT)");
//        db.execSQL("DROP Table users");


        Button RegistrationFormButton = findViewById(R.id.RegistrationFormButton);
        EditText EmailField = findViewById(R.id.RegistrationEmail);
        EditText NameField = findViewById(R.id.RegistrationName);
        EditText SurnameField = findViewById(R.id.RegistrationSurname);
        EditText PasswordField =findViewById(R.id.RegistrationPassword);


        RegistrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String str = "abc";
//                db.execSQL("INSERT OR IGNORE INTO users VALUES (" +
//                        "'" +EmailField.getText().toString()+"'" + ","+
//                        "'" +NameField.getText().toString()+"'" + ","+
//                        "'" +SurnameField.getText().toString() +"'" +","+
//                        "'" +PasswordField.getText().toString()+"'" +
//                                ")");
//                Cursor query = db.rawQuery("SELECT * FROM users;", null);
//                if(query.moveToFirst()){
//
//                    String name = query.getString(0);
//                    System.out.println(name);
//                }

            }
        });
    }
}
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

import com.example.myapplication.DatabaseWork.DbRequest;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        DbRequest dbRequest = new DbRequest();
        dbRequest.CreateTables(db);
//        dbRequest.DropDatabase(db);


        Button RegistrationFormButton = findViewById(R.id.RegistrationFormButton);
        EditText EmailField = findViewById(R.id.RegistrationEmail);
        EditText NameField = findViewById(R.id.RegistrationName);
        EditText SurnameField = findViewById(R.id.RegistrationSurname);
        EditText PasswordField = findViewById(R.id.RegistrationPassword);
        TextView MessageField = findViewById(R.id.RegistrationMessage);


        RegistrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EmailField.getText().length() > 0 && NameField.getText().length() > 0 && SurnameField.getText().length() > 0 && PasswordField.getText().length() > 0) {

                    if (CheckEmail(EmailField.getText().toString()) == true) {
                        Integer id = dbRequest.AddNewUser(db, EmailField.getText().toString(), NameField.getText().toString(), SurnameField.getText().toString(), PasswordField.getText().toString());

                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    } else {
                        MessageField.setText("Email некорректен");
                    }


                } else {
                    MessageField.setText("Заполните все поля");
                }


            }

        });

    }

    public boolean CheckEmail(String email) {
        boolean check = false;
        for (Integer i = 0; i < email.length(); i++) {
//            System.out.println(email.charAt(i));
            if (email.charAt(i) =='@'  && i != 0 && i != email.length() - 1) {
                check = true;
            }
        }
        if (check == true) {
            return check;
        } else {
            return check;
        }

    }
}
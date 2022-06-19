package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.content.Intent;
import android.os.StrictMode;
import com.example.myapplication.DatabaseWork.DbRequest;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbRequest dbRequest = new DbRequest(this);
//        SQLiteDatabase db = dbRequest.dataBaseConnect(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {dbRequest.createTables();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

//        dbRequest.dropDatabase(db);


        Button registrationFormButton = findViewById(R.id.RegistrationFormButton);
        EditText emailField = findViewById(R.id.RegistrationEmail);
        EditText nameField = findViewById(R.id.RegistrationName);
        EditText surnameField = findViewById(R.id.RegistrationSurname);
        EditText passwordField = findViewById(R.id.RegistrationPassword);
        TextView messageField = findViewById(R.id.RegistrationMessage);


        registrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailField.getText().length() > 0 && nameField.getText().length() > 0 && surnameField.getText().length() > 0 && passwordField.getText().length() > 0) {

                    if (checkEmail(emailField.getText().toString()) == true) {

                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {Integer id = dbRequest.addNewUser( emailField.getText().toString(), nameField.getText().toString(), surnameField.getText().toString(), passwordField.getText().toString());
                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        };
                        Thread thread = new Thread(runnable);
                        thread.start();

                    } else {
                        messageField.setText("Email некорректен");
                    }
                } else {
                    messageField.setText("Заполните все поля");
                }
            }
        });

    }

    public boolean checkEmail(String email) {
        boolean check = false;
        for (Integer i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@' && i != 0 && i != email.length() - 1) {
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
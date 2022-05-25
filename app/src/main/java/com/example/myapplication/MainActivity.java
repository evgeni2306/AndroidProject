package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.util.*;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button RegistrationFormButton = findViewById(R.id.RegistrationFormButton);
        EditText EmailField = findViewById(R.id.RegistrationEmail);
        EditText NameField = findViewById(R.id.RegistrationName);
        EditText SurnameField = findViewById(R.id.RegistrationSurname);
        EditText PasswordField =findViewById(R.id.RegistrationPassword);

        RegistrationFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            System.out.println(EmailField.getText());
            }
        });
    }
}
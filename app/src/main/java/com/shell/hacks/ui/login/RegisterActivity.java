package com.shell.hacks.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shell.hacks.R;

public class RegisterActivity extends AppCompatActivity {

    public Button registerButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button)findViewById(R.id.register);
        backButton = (Button)findViewById(R.id.back_to_login);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gets new user information
                String username = ((EditText)findViewById(R.id.username_register)).getText().toString();
                String password = ((EditText)findViewById(R.id.password_register)).getText().toString();
                String age = ((EditText)findViewById(R.id.age_register)).getText().toString();
                String disability = ((EditText)findViewById(R.id.disability_register)).getText().toString();
                String phone = ((EditText)findViewById(R.id.phone_register)).getText().toString();

                // Saves new user information onto database
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                AccountDatabase database = new AccountDatabase();
                database.saveData(new Account(username, password, age, disability, phone));
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifies login input
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
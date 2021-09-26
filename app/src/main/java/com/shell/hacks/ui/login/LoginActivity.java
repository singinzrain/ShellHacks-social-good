package com.shell.hacks.ui.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.shell.hacks.MainActivity;
import com.shell.hacks.R;

public class LoginActivity extends AppCompatActivity {

    public Button loginButton, registerAccountButton;
    public boolean validLogin = false;
    public boolean writingData = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Erase error message
        Button error = (Button)findViewById(R.id.login_error);
        error.setAlpha(0);

        // Set "onClick" event for login button
        loginButton = (Button)findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writingData = false;

                // Creates intent to open main page
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                // Login information
                String username = ((EditText)findViewById(R.id.username)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                AccountDatabase database = new AccountDatabase();

                FirebaseDatabase.getInstance().getReference().child("accounts").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            // Check the existence of the receiver
                            String value = snapshot.getValue(String.class);
                            Account account = new Gson().fromJson(value, Account.class); // Convert to account
                            if (account.getEmail().equals(username)) {
                                if (account.getPassword().equals(password)) {
                                    validLogin = true;
                                    break;
                                }
                            }
                        }

                        // Logs in iff login is pressed and register is not
                        if (validLogin)
                        {
                            if (!writingData)
                                startActivity(intent);
                        }
                        else
                        {
                            // Error message for login failure
                            Button error = (Button) findViewById(R.id.login_error);
                            error.setAlpha(1);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        validLogin = false;
                    }
                });
            }
        });

        // Set "onClick" event for register button
        registerAccountButton = (Button)findViewById(R.id.register_account);
        registerAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prevents login validation
                validLogin = false;
                writingData = true;

                // Goes to registration screen
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}
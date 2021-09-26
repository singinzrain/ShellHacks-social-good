package com.shell.hacks.ui.login;

import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import com.shell.hacks.R;


import java.util.UUID;

public class AccountDatabase {

    private DatabaseReference database;
    private FirebaseDatabase firebase;

    AccountDatabase()
    {
        this.firebase = FirebaseDatabase.getInstance();
        this.database = this.firebase.getReference();
    }

    //** Stores data in firebase runtime database
    public void saveData(Account account)
    {
        DatabaseReference accounts = firebase.getReference("accounts");

        String userID = UUID.randomUUID().toString();
        while (userID.equals("0"))
            userID = UUID.randomUUID().toString();

        accounts.child(userID).setValue(new Gson().toJson(account));
    }
}

package com.shell.hacks.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.shell.hacks.R;


import java.util.UUID;

public class HelpFragment extends Fragment {

    private HelpViewModel helpViewModel;

    private String userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (userId == null) {
            userId = "userId1";
        }

        helpViewModel =
                new ViewModelProvider(this).get(HelpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_help, container, false);
        final Button helpButton = root.findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText description = root.findViewById(R.id.help_description);

                // write a new task to firebase
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference task = database.getReference("task");

                TaskDetail taskDetail = new TaskDetail(String.valueOf(description.getText()),
                        userId, "", "", "");
                String taskId = UUID.randomUUID().toString();
                task.child(taskId).setValue(new Gson().toJson(taskDetail));


                // write a task-user mapping to firebase
                DatabaseReference mapping = database.getReference("user-task-mapping");
                mapping.child(userId).child(taskId).setValue(true);
            }
        });
        return root;
    }
}
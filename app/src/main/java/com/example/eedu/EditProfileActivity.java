package com.example.eedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    EditText f_name,l_name;
    Button btn_save;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        f_name=findViewById(R.id.fname);
        l_name=findViewById(R.id.lname);
        btn_save=findViewById(R.id.save);

        auth=FirebaseAuth.getInstance();
        currentUserID=auth.getCurrentUser().getUid();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID);
        databaseReference.keepSynced(true);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInformation();
            }
        });

    }

    public void SaveInformation(){

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

    }

}
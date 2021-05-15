package com.example.eedu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    TextView first_name,email,myLearning,wishList,editProfile,help,logout;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        first_name=findViewById(R.id.name);
        email=findViewById(R.id.email_id);
        myLearning=findViewById(R.id.my_learning);
        wishList=findViewById(R.id.wishlist);
        editProfile=findViewById(R.id.edit_profile);
        help=findViewById(R.id.help);
        logout=findViewById(R.id.log_out);

        mAuth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading user data");
        progressDialog.setMessage("Please wait while we load the user data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        myLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mycourseIntent=new Intent(ProfileActivity.this,MyCourseActivity.class);
                startActivity(mycourseIntent);
            }
        });

        wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favIntent=new Intent(ProfileActivity.this,FavouriteActivity.class);
                startActivity(favIntent);
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent=new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(editIntent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent helpIntent=new Intent(ProfileActivity.this,ContactUsActivity.class);
                startActivity(helpIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }
        });

        LoadInformation();

    }

    private void SendUserToLoginActivity() {
        Intent loginIntent=new Intent(ProfileActivity.this,LogIn.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    public void LoadInformation(){
        String currentUserId=mAuth.getCurrentUser().getUid();

        databaseReference.child("users").child(currentUserId).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("name").getValue().toString();
                first_name.setText(name);

                String uemail=snapshot.child("email").getValue().toString();
                email.setText(uemail);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
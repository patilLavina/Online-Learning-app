package com.example.eedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.eedu.CourseListDatabase.CourseAdapterClass;
import com.example.eedu.CourseListDatabase.courseData;
import com.example.eedu.courseDetails.categoryAdapter;
import com.example.eedu.courseDetails.categoryHelperClass;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    RecyclerView recyclerView;
    TextView display_name;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth mauth;
    ArrayList<categoryHelperClass> list;
    categoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        display_name=findViewById(R.id.home_name);
        recyclerView=findViewById(R.id.recyclerview_category);
        RecyclerView();

        setSupportActionBar(toolbar);
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        mauth=FirebaseAuth.getInstance();
        firebaseUser=mauth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseUser==null){
            SendUserToLoginActivity();
        }
        else{
            LoadInfo();

        }
    }

    public void LoadInfo(){
        String currentUserId=mauth.getCurrentUser().getUid();
        databaseReference.child("users").child(currentUserId).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("name").getValue().toString();
                display_name.setText("Welcome Back\n"+name+" !");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void RecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        list=new ArrayList<>();

        adapter =new categoryAdapter(MainActivity.this,list);
        recyclerView.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("category");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    categoryHelperClass helperClass=dataSnapshot.getValue(categoryHelperClass.class);
                    list.add(helperClass);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                break;
            case R.id.mycourse:
                Intent mycourse_intent=new Intent(MainActivity.this,CertificateActivity.class);
                startActivity(mycourse_intent);
                break;
            case R.id.favourite:
                Intent favourite_intent=new Intent(MainActivity.this,StartQuizActivity.class);
                startActivity(favourite_intent);
                break;
            case R.id.profile:
                Intent profile_intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(profile_intent);
                break;
            case R.id.logout:
                mauth.signOut();
                SendUserToLoginActivity();
                break;
            case R.id.rate:
                Intent rateus_intent=new Intent(MainActivity.this,RateUsActivity.class);
                startActivity(rateus_intent);
                break;
            case R.id.contact:
                Intent contactus_intent=new Intent(MainActivity.this,ContactUsActivity.class);
                startActivity(contactus_intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent=new Intent(MainActivity.this,LogIn.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

}
package com.example.eedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eedu.CourseListDatabase.courseData;
import com.example.eedu.Favourites.FavModelClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnrollNowActivity extends AppCompatActivity {
    Button btn_enroll;
    String cName,cDescription,cImage;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    TextView courseName,courseDescription;
    ImageView like,share;
    Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_now);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        courseName=findViewById(R.id.course_name);
        courseDescription=findViewById(R.id.course_description);

        like=findViewById(R.id.like_course);
        share=findViewById(R.id.share_course);

        auth=FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();

        getIncoming();
        LoadData();
        Share();
        flag=false;
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(like.isPressed()){
                    if(flag){
                        like.setBackground(getDrawable(R.drawable.ic_favourite_outline));
                        //removeFav(index);
                        flag=false;
                    }else{
                        like.setBackground(getDrawable(R.drawable.ic_favourite_filled));
                        LikeCourse();
                        flag=true;
                    }
                }
            }
        });

        btn_enroll=findViewById(R.id.enroll_now_btn);
        btn_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EnrollNowActivity.this,CoursePageActivity.class);
                intent.putExtra("name",cName);
                startActivity(intent);
            }
        });

        courseName.setText(cName);
        courseDescription.setText(cDescription);
    }

    private void Share() {
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,cName);
                    String shareMessage="Hello checkout this course "+cName+"\n"+"From "+BuildConfig.APPLICATION_ID+"\n"+"which helps to learn new things";
                    intent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    startActivity(Intent.createChooser(intent,"Share by:"));

                }catch (Exception e){
                    Toast.makeText(EnrollNowActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void getIncoming(){
        if (getIntent().hasExtra("name") && getIntent().hasExtra("description") && getIntent().hasExtra("image")){
            cName=getIntent().getStringExtra("name");
            cDescription=getIntent().getStringExtra("description");
            cImage=getIntent().getStringExtra("image");
        }

    }

    public void LikeCourse(){
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Favourite").child(currentUser.getUid()).push();
        FavModelClass modelClass=new FavModelClass(cName,cImage,cDescription);
        databaseReference.setValue(modelClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Course Added To Favourite",Toast.LENGTH_LONG).show();
                }else{
                    Log.d("No Data Saved", "onDataChange: No Movie data is saved");
                }
            }
        });


    }

    public void LoadData(){

//        databaseReference=FirebaseDatabase.getInstance().getReference("Courses").child(cName);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                    String name=snapshot.child("name").getValue().toString();
//                    String description=snapshot.child("description").getValue().toString();
//
//                    courseName.setText(name);
//                    courseDescription.setText(description);
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}
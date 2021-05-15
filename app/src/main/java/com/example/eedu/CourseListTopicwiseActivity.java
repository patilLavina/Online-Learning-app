package com.example.eedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.eedu.CourseListDatabase.CourseAdapterClass;
import com.example.eedu.CourseListDatabase.courseData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CourseListTopicwiseActivity extends AppCompatActivity {

    ArrayList<courseData> list;
    DatabaseReference databaseReference;
    CourseAdapterClass adapter;
    TextView coursename;
    String catname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list_topicwise);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        RecyclerView recyclerView=findViewById(R.id.courseList_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        coursename=findViewById(R.id.corename);

        getIncoming();
        coursename.setText(catname);

        list=new ArrayList<>();

        adapter =new CourseAdapterClass(CourseListTopicwiseActivity.this,list);
        recyclerView.setAdapter(adapter);


        databaseReference= (DatabaseReference) FirebaseDatabase.getInstance().getReference("Courses").child(catname);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    courseData courseData=dataSnapshot.getValue(courseData.class);
                    list.add(courseData);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void getIncoming(){
        if (getIntent().hasExtra("name")){
            catname=getIntent().getStringExtra("name");
        }

    }


}
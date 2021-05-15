package com.example.eedu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class CoursePageActivity extends AppCompatActivity {

    TextView courseName;
    String cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getIncoming();

        courseName=findViewById(R.id.coursePageName);
        courseName.setText(cName);
    }

    public void getIncoming(){
        if (getIntent().hasExtra("name")){
            cName=getIntent().getStringExtra("name");
        }

    }

}
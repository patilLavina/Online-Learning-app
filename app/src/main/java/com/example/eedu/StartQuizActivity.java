package com.example.eedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class StartQuizActivity extends AppCompatActivity {
    CheckBox iAmReady;
    Button startQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iAmReady=findViewById(R.id.checkBox);
        startQuiz=findViewById(R.id.start_quiz);

        StartQuiz();
    }

    private void StartQuiz() {

        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iAmReady.isChecked()){
                    Intent startQuiz=new Intent(StartQuizActivity.this,QuizActivity.class);
                    startActivity(startQuiz);

                }
                else {
                    Toast.makeText(StartQuizActivity.this,"Select I am Ready",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
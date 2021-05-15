package com.example.eedu;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eedu.QuestionDatabase.QuestionHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class QuizActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    TextView question_number, card_question,exit;
    Button optionA, optionB, optionC, optionD;
    Button next;
    int count = 0;
    private int position = 0;
    int score = 0;
    private List<QuestionHelperClass> list;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    CountDownTimer myCountDownTimer;
    int timerValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Hooks();

        progressBar.setProgress(timerValue);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        Timer();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit=new Intent(QuizActivity.this,StartQuizActivity.class);
                startActivity(exit);
            }
        });

        list = new ArrayList<>();
        databaseReference.child("Quiz").child("Java").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    list.add(snapshot.getValue(QuestionHelperClass.class));
                }
                if (list.size() > 0) {
                    for (int i = 0; i < 4; i++) {
                        linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CheckAnswer(((Button) v));
                            }
                        });
                    }

                    playAnimation(card_question, 0, list.get(position).getQuestion().toString());

                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            next.setEnabled(false);
                            next.setAlpha((float) 0.7);
                            enableOption(true);
                            position++;
                            if (position == list.size()) {
                                Intent scoreIntent = new Intent(QuizActivity.this, ScoreActivity.class);
                                scoreIntent.putExtra("score", score);
                                scoreIntent.putExtra("total", list.size());
                                startActivity(scoreIntent);
                                finish();
                                return;
                            }
                            count = 0;
                            playAnimation(card_question, 0, list.get(position).getQuestion().toString());
                        }
                    });

                } else {
                    finish();
                    Toast.makeText(QuizActivity.this, "No questions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuizActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void Hooks() {
        card_question = findViewById(R.id.question);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);

        linearLayout = findViewById(R.id.linearLayout);
        next = findViewById(R.id.btn_next);
        exit=findViewById(R.id.exit);
        question_number = findViewById(R.id.questionNumber);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_timer);
    }

    private void playAnimation(View view, final int value, final String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = list.get(position).getQa().toString();
                    } else if (count == 1) {
                        option = list.get(position).getQb().toString();
                    } else if (count == 2) {
                        option = list.get(position).getQc().toString();
                    } else if (count == 3) {
                        option = list.get(position).getQd().toString();
                    }
                    playAnimation(linearLayout.getChildAt(count), 0, option);
                    count++;
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0) {
                    try {
                        ((TextView) view).setText(data);
                        question_number.setText(position + 1 + "/" + list.size());
                    } catch (ClassCastException e) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);
                    playAnimation(view, 1, data);
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void CheckAnswer(Button selectedOption) {
        enableOption(false);
        next.setEnabled(true);
        next.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getAns())) {
            //correct
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        } else {
            //incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption = (Button) linearLayout.findViewWithTag(list.get(position).getAns());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));

        }

    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            linearLayout.getChildAt(i).setEnabled(enable);
            if (enable) {
                linearLayout.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));

            }
        }

    }

    public void Timer() {
        myCountDownTimer=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ timerValue+ millisUntilFinished);
                timerValue++;
                progressBar.setProgress((int)timerValue*100/(5000/1000));
            }

            @Override
            public void onFinish() {
               finish();
               Toast.makeText(QuizActivity.this,"Oops.. Time out! Try Again...",Toast.LENGTH_SHORT).show();
            }
        };
        myCountDownTimer.start();

    }


}
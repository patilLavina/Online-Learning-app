package com.example.eedu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class ScoreActivity extends AppCompatActivity {
    TextView score;
    Button getCertificate;
    CircularProgressBar circularProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        score=findViewById(R.id.final_score);
        circularProgressBar=findViewById(R.id.circularProgressBar);

        int result= Integer.parseInt(String.valueOf(getIntent().getIntExtra("score",0)));
        String total=String.valueOf(getIntent().getIntExtra("total",0));

        circularProgressBar.setProgress(result);
        score.setText(result+"/"+total);

        getCertificate=findViewById(R.id.get_certificate);
        getCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent certificate=new Intent(ScoreActivity.this,CertificateActivity.class);
                startActivity(certificate);
                finish();
            }
        });
    }
}
package com.example.eedu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

public class RateUsActivity extends AppCompatActivity {
    TextView type_rating;
    RatingBar star_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        type_rating=findViewById(R.id.rating_type);
        star_rating=findViewById(R.id.rating_star);

        star_rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating==0){
                    type_rating.setText("none");
                }
                else if (rating==1){
                    type_rating.setText("Very Dissatisfied");
                }
                else if (rating==2){
                    type_rating.setText("Dissatisfied");
                }
                else if (rating==3){
                    type_rating.setText("OK");
                }
                else if (rating==4){
                    type_rating.setText("Satisfied");
                }
                else if (rating==5){
                    type_rating.setText("Very Satisfied");
                }
                else{

                }

            }
        });
    }
    //------------------comment box remain--------------------------------
}
package com.example.dreamtunes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private ImageView vinyl;
    private Button playToStart;


    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vinyl = findViewById(R.id.vinyl_start);
        vinyl.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_infinitely));

        playToStart = findViewById(R.id.play_to_start);


        playToStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundResource(R.drawable.ic_play_button_clicked);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.setBackgroundResource(R.drawable.ic_play_button);
                        navigateToSignIn();
                        break;
                    }
                    default:
                        break;
                }

                return true;
            }
        });



    }

    public void navigateToSignIn() {
        Intent signInActivity = new Intent(this, SignInActivity.class);
        startActivity(signInActivity);
    }


}

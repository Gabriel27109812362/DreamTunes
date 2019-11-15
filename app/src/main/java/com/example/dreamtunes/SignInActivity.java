package com.example.dreamtunes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SignInActivity extends AppCompatActivity {


    private ImageView startTitle;
    private Button signIn;
    private Button signUpLink;

    @SuppressLint("ClickableViewAccessibility")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        startTitle = findViewById(R.id.title_start_second);
        signIn = findViewById(R.id.button_sign_in);
        signUpLink = findViewById(R.id.sign_up_link);


        startTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    navigateToMainActivity();
                }
                return true;
            }
        });


        signIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        v.setBackgroundResource(R.drawable.ic_sign_in_button);
                        break;
                    }
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundResource(R.drawable.ic_sign_in_button_clicked);
                        break;
                    }

                    default:
                        break;
                }


                return true;
            }
        });


        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUpActivity();
            }
        });


    }


    public void navigateToMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    public void navigateToSignUpActivity() {
        Intent signUpActivity = new Intent(this, SignUpActivity.class);
        startActivity(signUpActivity);
        finish();
    }
}

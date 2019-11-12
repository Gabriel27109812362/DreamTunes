package com.example.dreamtunes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    private Button signInLink;
    private ImageView imageTopTitle;
    private Button signUp;
    private Button facebook;

    private Map<Inputs, EditText> inputs = new HashMap<Inputs, EditText>();


    @SuppressLint("ClickableViewAccessibility")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        signInLink = findViewById(R.id.sign_in_link);
        imageTopTitle = findViewById(R.id.title_start_third);
        signUp = findViewById(R.id.button_sign_up);
        facebook = findViewById(R.id.sign_up_with_fb_btn);

        inputs.put(Inputs.NAME, (EditText) findViewById(R.id.input_name));
        inputs.put(Inputs.SURNAME, (EditText) findViewById(R.id.input_surname));
        inputs.put(Inputs.EMAIL, (EditText) findViewById(R.id.input_email));
        inputs.put(Inputs.PASSWORD, (EditText) findViewById(R.id.input_password));


        imageTopTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignInActivity();
            }
        });

        signUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundResource(R.drawable.ic_sign_up_button_clicked);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        v.setBackgroundResource(R.drawable.ic_sign_up_button);




                        break;
                    }
                    default:
                        break;

                }


                return true;
            }
        });


        facebook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundResource(R.drawable.ic_facebook_ico_clicked);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {

                        v.setBackgroundResource(R.drawable.ic_facebook_ico);
                        break;
                    }
                    default:
                        break;
                }


                return true;
            }
        });


    }


    public void navigateToSignInActivity() {
        Intent signInActivity = new Intent(this, SignInActivity.class);
        startActivity(signInActivity);
    }

    public void navigateToMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }


}

package com.example.dreamtunes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dreamtunes.Services.UserService;

import java.util.HashMap;
import java.util.Map;

import validators.InputValidator;

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


        inputs.get(Inputs.NAME).addTextChangedListener(new InputValidator((EditText) findViewById(R.id.input_name)) {
            @Override
            public void validate(EditText textView, String text) {
                TextView validator = findViewById(R.id.validator_name);

                if (text.length() == 0) {
                    validator.setText(R.string.name_required);
                    signUp.setEnabled(false);
                } else {
                    validator.setText(null);
                    signUp.setEnabled(true);
                }

                Log.i("text", text);
                Log.i("Edit Text", textView.getText().toString());
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

                        Intent userService = createUserServiceIntent();

                        userService.putExtra("name", inputs.get(Inputs.NAME).getText().toString());
                        userService.putExtra("surname", inputs.get(Inputs.SURNAME).getText().toString());
                        userService.putExtra("email", inputs.get(Inputs.EMAIL).getText().toString());
                        userService.putExtra("password", inputs.get(Inputs.PASSWORD).getText().toString());

                        startService(userService);

                        navigateToSignInActivity();
                        stopService(userService);

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

    public Intent createUserServiceIntent() {
        return new Intent(this, UserService.class);
    }

    public void navigateToSignInActivity() {
        Intent signInActivity = new Intent(this, SignInActivity.class);
        startActivity(signInActivity);
        finish();
    }

    public void navigateToMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }


}

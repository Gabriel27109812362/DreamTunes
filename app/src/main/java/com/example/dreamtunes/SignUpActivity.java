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
import android.widget.TextView;
import android.widget.Toast;

import com.example.dreamtunes.Services.UserService;

import java.util.EnumMap;
import java.util.Map;

import validators.InputValidator;

public class SignUpActivity extends AppCompatActivity {


    private Button signInLink;
    private ImageView imageTopTitle;
    private Button signUp;
    private Button facebook;

    private Map<Inputs, EditText> inputs = new EnumMap<>(Inputs.class);
    private Map<Inputs, Boolean> validatorLock = new EnumMap<>(Inputs.class);


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

        validatorLock.put(Inputs.NAME, false);
        validatorLock.put(Inputs.SURNAME, false);
        validatorLock.put(Inputs.EMAIL, false);
        validatorLock.put(Inputs.PASSWORD, false);

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
                    validatorLock.put(Inputs.NAME, false);
                } else {
                    validator.setText(null);
                    validatorLock.put(Inputs.NAME, true);
                }
            }
        });

        inputs.get(Inputs.SURNAME).addTextChangedListener(new InputValidator((EditText) findViewById(R.id.input_surname)) {
            @Override
            public void validate(EditText textInput, String text) {
                TextView validator = findViewById(R.id.validator_surname);

                if (text.length() == 0) {
                    validator.setText(R.string.surname_required);
                    validatorLock.put(Inputs.SURNAME, false);
                } else {
                    validator.setText(null);
                    validatorLock.put(Inputs.SURNAME, true);
                }

            }
        });


        inputs.get(Inputs.EMAIL).addTextChangedListener(new InputValidator((EditText) findViewById(R.id.input_email)) {
            @Override
            public void validate(EditText textInput, String text) {
                TextView validator = findViewById(R.id.validator_email);

                if (text.length() == 0) {
                    validator.setText(R.string.email_required);
                    validatorLock.put(Inputs.EMAIL, false);
                } else {
                    if (!text.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                        validator.setText(R.string.wrong_format);
                        validatorLock.put(Inputs.EMAIL, false);
                    } else {
                        validator.setText(null);
                        validatorLock.put(Inputs.EMAIL, true);
                    }
                }

            }
        });

        inputs.get(Inputs.PASSWORD).addTextChangedListener(new InputValidator((EditText) findViewById(R.id.input_password)) {
            @Override
            public void validate(EditText textInput, String text) {
                TextView validator = findViewById(R.id.validator_password);

                if (text.length() == 0) {
                    validator.setText(R.string.password_required);
                    validatorLock.put(Inputs.PASSWORD, false);
                } else {
                    if (text.length() <= 6) {
                        validator.setText(R.string.password_too_short);
                        validatorLock.put(Inputs.PASSWORD, false);
                    } else {
                        validator.setText(null);
                        validatorLock.put(Inputs.PASSWORD, true);
                    }
                }

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

                        if (isValid()) {

                            Intent userService = createUserServiceIntent();

                            userService.putExtra("name", inputs.get(Inputs.NAME).getText().toString());
                            userService.putExtra("surname", inputs.get(Inputs.SURNAME).getText().toString());
                            userService.putExtra("email", inputs.get(Inputs.EMAIL).getText().toString());
                            userService.putExtra("password", inputs.get(Inputs.PASSWORD).getText().toString());

                            startService(userService);

                            navigateToSignInActivity();
                            stopService(userService);
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.fill_all_form, Toast.LENGTH_SHORT).show();
                        }

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

    public boolean isValid() {
        for (Map.Entry<Inputs, Boolean> entry : validatorLock.entrySet()) {
            if (!entry.getValue()) return false;
        }
        return true;
    }

    public void navigateToMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }


}

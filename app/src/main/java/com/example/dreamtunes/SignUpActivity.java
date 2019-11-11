package com.example.dreamtunes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {


    private Button signInLink;
    private ImageView imageTopTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        signInLink = findViewById(R.id.sign_in_link);
        imageTopTitle = findViewById(R.id.title_start_third);

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




    }


    public void navigateToSignInActivity(){
        Intent signInActivity = new Intent(this, SignInActivity.class);
        startActivity(signInActivity);
    }

    public void navigateToMainActivity(){
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }


}

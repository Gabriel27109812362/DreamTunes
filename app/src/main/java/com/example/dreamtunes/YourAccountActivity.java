package com.example.dreamtunes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class YourAccountActivity extends AppCompatActivity {

    private Button allSongs, yourAccount;
    private TextView message;
    private Button logOut;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_account);

        allSongs = findViewById(R.id.button_all_songs_log_out);
        yourAccount = findViewById(R.id.button_your_account_log_out);
        message = findViewById(R.id.logged_out_message);
        logOut = findViewById(R.id.log_out_button);


        db.collection("logged")
                .document("logged_user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            email = task.getResult().getData().get("email").toString();
                            message.setText(email);
                        }
                    }
                });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });

        allSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMusicActivity();
            }
        });

    }

    public void navigateToMainActivity() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        db.collection("logged")
                .document("logged_user")
                .delete();
        startActivity(mainActivity);
        finish();
    }


    public void navigateToMusicActivity() {
        Intent musicActivity = new Intent(this, MusicActivity.class);
        startActivity(musicActivity);
        finish();
    }


}

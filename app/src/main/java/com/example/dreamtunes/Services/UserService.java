package com.example.dreamtunes.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dreamtunes.SignInActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import builders.UserBuilder;
import models.User;

public class UserService extends Service {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        User user = new UserBuilder()
                .name(intent.getExtras().getString("name"))
                .surname(intent.getExtras().getString("surname"))
                .email(intent.getExtras().getString("email"))
                .password(intent.getExtras().getString("password"))
                .build();

        saveUserInDb(user);
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void saveUserInDb(User user) {
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("ADD_SUCCES", "DocumentSnapshot added with Id" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ADD_FAILURE", "Error adding document", e);
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i("DESTROYED", "UserService destroyed");
    }
}

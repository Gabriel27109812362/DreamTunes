package com.example.dreamtunes.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserService extends Service {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

package com.example.dreamtunes.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserService extends Service {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public UserService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("DATA FROM FORM:", intent.getExtras().getString("name"));
        Log.i("DATA FROM FORM:", intent.getExtras().getString("surname"));
        Log.i("DATA FROM FORM:", intent.getExtras().getString("email"));
        Log.i("DATA FROM FORM:", intent.getExtras().getString("password"));
        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

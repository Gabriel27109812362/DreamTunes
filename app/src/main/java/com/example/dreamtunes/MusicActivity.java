package com.example.dreamtunes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MusicActivity extends AppCompatActivity implements View.OnClickListener {


    Button play, pause, stop, yourAccount, allSongs;
    MediaPlayer mediaPlayer;
    Vibrator v;
    int pauseCurrentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        yourAccount = (Button) findViewById(R.id.button_your_account);
        allSongs = (Button) findViewById(R.id.button_all_songs);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        yourAccount.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.play:
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.song);
                    mediaPlayer.start();
                    this.v.vibrate(500);
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(pauseCurrentPosition);
                    mediaPlayer.start();
                }
                break;

            case R.id.pause:
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    pauseCurrentPosition = mediaPlayer.getCurrentPosition();
                    this.v.vibrate(500);
                }
                break;

            case R.id.stop:
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                    this.v.vibrate(500);
                }
                break;

            case R.id.button_your_account:
                stop.performClick();
                navigateToYourAcccount();
                break;
            default:
                break;
        }

    }

    public void navigateToYourAcccount() {
        Intent yourAccount = new Intent(this, YourAccountActivity.class);
        startActivity(yourAccount);
        finish();
    }

}

package com.example.rahul.udemoapp7;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    CountDownTimer countDownTimer;
    Button mButton;
    SeekBar seekBar;
    TextView textView;
    Boolean counterisActive = false;
    public void updateTimer(int secondsLeft){
        int minutes = (int)secondsLeft /60;
        int seconds = secondsLeft - minutes *60;
        String secondString = Integer.toString(seconds);
        if(seconds <= 9){
            secondString = "0"+secondString;
        }
        textView.setText(minutes+":"+secondString);
    }
    public void resetTimer(){
        textView.setText("00:00");
        seekBar.setProgress(0);
        seekBar.setEnabled(true);
        mButton.setText("Start");
        counterisActive = false;
        countDownTimer.cancel();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);

        seekBar.setProgress(0);
        seekBar.setMax(600);
        //seekBar.incrementProgressBy(5);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    public void buttonClicked(View view) {
        if(counterisActive == false) {

            counterisActive = true;
            mButton.setText("Stop");
            seekBar.setEnabled(false);
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l) {

                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    textView.setText("00:00");
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mj);
                    audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
        else {
            resetTimer();
        }
    }

}

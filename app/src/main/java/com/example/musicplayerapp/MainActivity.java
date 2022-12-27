package com.example.musicplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    Button forwardButton,backwardButton,playButton,pauseButton;
    TextView timer,title,songName;
    SeekBar seekBar;



    //Media Player
    MediaPlayer mediaPlayer;

    //Handler
    Handler handler=new Handler();

    //Variables
    double startTime=0;
    double finalTime=0;
    int forwardTime=1000;
    int backwardTime=1000;
    static int oneTimeOnly=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Widgets
        forwardButton=findViewById(R.id.forward_button);
        backwardButton=findViewById(R.id.back_button);
        playButton=findViewById(R.id.play_arrow);
        pauseButton=findViewById(R.id.pause);
        timer=findViewById(R.id.timerMusic);
        title=findViewById(R.id.title);
        songName=findViewById(R.id.songName);
        seekBar=findViewById(R.id.seekBar2);


        //Building the application

        mediaPlayer=MediaPlayer.create(this,R.raw.astronaut);



        songName.setText(getResources().getIdentifier(
                "astronaut",
                "raw",
                getPackageName()
        ) );

        seekBar.setClickable(false);

        //Adding functionalities to button

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayMusic();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();

            }
        });


        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;
                if ((temp + forwardTime) <= finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);

                }else{
                    Toast.makeText(MainActivity.this,
                            "Can't Jump Forward!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;

                if ((temp - backwardTime) > 0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }else{
                    Toast.makeText(MainActivity.this,
                            "Can't Go Back!", Toast.LENGTH_SHORT).show();
                }
            }
        });













    }



    public void PlayMusic(){
        mediaPlayer.start();
        finalTime=mediaPlayer.getDuration();
        startTime=mediaPlayer.getCurrentPosition();
        if (oneTimeOnly==0){
            seekBar.setMax((int) finalTime);
            oneTimeOnly=1;
        }
        timer.setText(String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes
                                ((long) finalTime))
        ));

        seekBar.setProgress((int)startTime);
        handler.postDelayed(UpdateSongTime,100);

    }

    public Runnable UpdateSongTime=new Runnable() {
        @Override
        public void run() {
            startTime=mediaPlayer.getCurrentPosition();
            timer.setText(String.format(
                    "%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long)startTime)
                            -TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)startTime))));


            seekBar.setProgress((int)startTime);
            handler.postDelayed(this,100);

        }


    };


}
package com.example.spotifyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Button playAndpause;
    SeekBar seekBar;

    Button prev , next;

    MediaPlayer music;

    int currentIndex = 0;

    // Song Info
    TextView songName , movieName , title;
    ImageView profile;

    //Timer settings
    TextView currentTime,totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playAndpause = findViewById(R.id.play);

        //Next and Priv Song

        prev = findViewById(R.id.previous);
        next = findViewById(R.id.next);

        //Timer
        currentTime = findViewById(R.id.currentTime);
        totalTime = findViewById(R.id.totalTime);

        ArrayList<Integer> songs = new ArrayList<>();

        songs.add(0,R.raw.loveme);
        songs.add(1,R.raw.watermelon);
        songs.add(2,R.raw.nolie);
        songs.add(3,R.raw.mylife);
        songs.add(4,R.raw.song2);
        songs.add(5,R.raw.song);

        SongInfo();
        // Media Player
         music = MediaPlayer.create(MainActivity.this,songs.get(currentIndex));

        // To get total time of song
        int Time = music.getDuration();
        String newTime = String.valueOf(Time);
        totalTime.setText(toMMSS(newTime));

        // Rotate 360
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate360);

        playAndpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!music.isPlaying()){
                    music.start();
                    playAndpause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    SongInfo();

                    //Rotate Profile
                    profile.startAnimation(rotate);
                }else{
                    music.pause();
                    playAndpause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);

                    //Stop Rotating Profile
                    profile.clearAnimation();
                }
            }
        });

        //seekBar start here

        seekBar = findViewById(R.id.seekBar);

        seekBar.setMax(music.getDuration());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(music.getCurrentPosition());
            }
        },0,1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

               // music.seekTo(i);
                // if user is click on seekBar then song start from that point
                if( music != null && b ){
                    music.seekTo(i);
                }
                currentTime.setText(toMMSS(music.getCurrentPosition()+""));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Changing Song prev and next button

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music != null ){
                    playAndpause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }

                if(currentIndex < songs.size() - 1){
                    currentIndex ++;
                }else{
                    currentIndex = 0;
                }
                music.stop();
                music = MediaPlayer.create(MainActivity.this,songs.get(currentIndex));

                //seekBar size
                seekBar.setMax(music.getDuration());

                music.start();

                // To get total time of song
                int Time = music.getDuration();
                String newTime = String.valueOf(Time);
                totalTime.setText(toMMSS(newTime));

                SongInfo();

                //Rotate Profile
                profile.startAnimation(rotate);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(music != null ){
                    playAndpause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }

                if(currentIndex > 0){
                    currentIndex --;
                }else{
                    currentIndex = songs.size() -1;
                }

                music.stop();
                music = MediaPlayer.create(MainActivity.this,songs.get(currentIndex));

                //seekBar size
                seekBar.setMax(music.getDuration());

                music.start();

                // To get total time of song
                int Time = music.getDuration();
                String newTime = String.valueOf(Time);
                totalTime.setText(toMMSS(newTime));

                SongInfo();

                //Rotate Profile
                profile.startAnimation(rotate);
            }
        });

    }

    private void SongInfo(){

        songName = findViewById(R.id.SongName);
        movieName = findViewById(R.id.MovieName);
        profile = findViewById(R.id.profile_image);
        title = findViewById(R.id.textView);

        if(currentIndex == 0 ){
            songName.setText("Love Me Like You Do");
            movieName.setText("Fifty Shades Of Grey");
            profile.setImageResource(R.drawable.fiftyshades);
            title.setText("Love Me Like You Do");
        }
        if(currentIndex == 1 ){
            songName.setText("Watermelon Sugar");
            movieName.setText("Fine Line");
            profile.setImageResource(R.drawable.watermelon);
            title.setText("Watermelon Sugar");
        }
        if(currentIndex == 2 ){
            songName.setText("No Lie");
            movieName.setText("Baywatch (2017)");
            profile.setImageResource(R.drawable.nolie);
            title.setText("No Lie");
        }
        if(currentIndex == 3 ){
            songName.setText("My Life Is Going On");
            movieName.setText("Money Heist");
            profile.setImageResource(R.drawable.moneyheist);
            title.setText("My Life Is Going On");
        }
        if(currentIndex == 4 ){
            songName.setText("Invisible");
            movieName.setText("Invisible - Single (\"Cinkmusic\")");
            profile.setImageResource(R.drawable.image1);
            title.setText("Invisible ( YouTube )");
        }
        if(currentIndex == 5 ){
            songName.setText("Love Nwantiti");
            movieName.setText("Love Nwantiti YouTube");
            profile.setImageResource(R.drawable.lovenwantiti);
            title.setText("Love Nwantiti");
        }
    }

    public static String toMMSS(String duration){
        //String getTime = String.valueOf(duration);
        Long mil = Long.parseLong(String.valueOf(duration));

        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(mil) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(mil) % TimeUnit.MINUTES.toSeconds(1));

        //return String.format("%02d",SS);
        //return String.format(String.valueOf(MM)+":"+String.valueOf(SS));
    }
}
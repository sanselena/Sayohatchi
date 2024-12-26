package com.serenity.sayohatchi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set the layout from XML

        // Initialize the MediaPlayer with the audio resource
        mediaPlayer = MediaPlayer.create(this, R.raw.starsong_prophecy_loop);
        mediaPlayer.setLooping(true);  // Set it to loop
        mediaPlayer.start();  // Start the background music

        // Find the buttons by their IDs
        Button startButton = findViewById(R.id.start_button);
        Button btnSettings = findViewById(R.id.btnSettings);
        Button btnExit = findViewById(R.id.btnExit);

        // Set up the Start Game button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the game, switches to Scene1.class for the first game activity.
                Intent gameIntent = new Intent(MainActivity.this, Scene1.class);
                startActivity(gameIntent);
            }
        });

        // Set up the Settings button
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make toast for development
                Toast.makeText(MainActivity.this, "In development...", Toast.LENGTH_SHORT).show();
                // Open Settings activity
                Intent settingsIntent = new Intent(MainActivity.this, Settings.class);
                startActivity(settingsIntent);
            }
        });

        // Set up the Exit button
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the app
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();  // Pause the music when the activity is paused
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();  // Resume the music when the activity is resumed
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();  // Stop the music when the activity is destroyed
            mediaPlayer.release();  // Release the MediaPlayer resources
            mediaPlayer = null;
        }
    }
}

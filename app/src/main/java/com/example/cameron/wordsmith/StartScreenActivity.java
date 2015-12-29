package com.example.cameron.wordsmith;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            }

    public void twoPlayer(View v) {
        // Take player to a new Multiplayer game.
        Intent intent = new Intent(StartScreenActivity.this, MainActivity.class);
        Bundle b = new Bundle();
        b.putInt("players", 2);
        intent.putExtras(b);
        startActivity(intent);
        StartScreenActivity.this.finish();

    }
        };




package com.mobdeve.myflappybird;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    private TextView tvFinalScore, tvCurrency;
    private Button btnRestart, btnMainMenu;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "FlappyBirdPrefs";
    private static final String PREF_CURRENCY = "currency";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        tvFinalScore = findViewById(R.id.tv_final_score);
        tvCurrency = findViewById(R.id.tv_currency);
        btnRestart = findViewById(R.id.btn_restart);
        btnMainMenu = findViewById(R.id.btn_main_menu);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int currency = sharedPreferences.getInt(PREF_CURRENCY, 0);

        // Retrieve score passed from GameView
        int finalScore = getIntent().getIntExtra("score", 0);
        tvFinalScore.setText("Final Score: " + finalScore);
        tvCurrency.setText("Currency: " + currency);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart the game by going back to GameActivity
                Intent restartIntent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(restartIntent);
                finish();
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to the main menu
                Intent mainMenuIntent = new Intent(GameOverActivity.this, MainMenuActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });
    }
}

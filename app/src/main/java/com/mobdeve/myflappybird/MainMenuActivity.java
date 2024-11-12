package com.mobdeve.myflappybird;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    private TextView tvCurrencyMain;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "FlappyBirdPrefs";
    private static final String PREF_CURRENCY = "currency";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        tvCurrencyMain = findViewById(R.id.tv_currency_main);
        Button playButton = findViewById(R.id.play_button);
        Button storeButton = findViewById(R.id.store_button);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int currency = sharedPreferences.getInt(PREF_CURRENCY, 0);

        tvCurrencyMain.setText("Currency: " + currency);

        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
            startActivity(intent);
            finish();
        });
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to StoreActivity
                Intent intent = new Intent(MainMenuActivity.this, StoreActivity.class);
                startActivity(intent);
            }
        });
    }
}

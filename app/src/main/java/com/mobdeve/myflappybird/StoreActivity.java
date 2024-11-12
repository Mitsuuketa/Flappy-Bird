package com.mobdeve.myflappybird;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        RecyclerView rvAvatars = findViewById(R.id.rv_avatars);
        rvAvatars.setLayoutManager(new GridLayoutManager(this, 3)); // Set to 3 columns programmatically
        Button backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainMenuActivity
                Intent intent = new Intent(StoreActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish(); // Optionally, you can call finish() to close StoreActivity
            }
        });

        // Dummy data
        List<Integer> avatarImages = Arrays.asList(
                R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3,
                R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6
        );
        List<String> avatarNames = Arrays.asList(
                "avatar1", "avatar2", "avatar3", "avatar4", "avatar5", "avatar6"
        );

        AvatarAdapter adapter = new AvatarAdapter(this, avatarImages, avatarNames);
        rvAvatars.setAdapter(adapter);


    }
}

package com.example.trivialpursuit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNouvellePartie = findViewById(R.id.btnNouvellePartie);
        Button btnEntrainement = findViewById(R.id.btnEntrainement);
        Button btnRegles = findViewById(R.id.btnRegles);

        btnNouvellePartie.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        btnEntrainement.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CategorySelectionActivity.class);
            startActivity(intent);
        });

        btnRegles.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RulesActivity.class);
            startActivity(intent);
        });
    }
}
package com.example.trivialpursuit;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Button btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> finish());
    }
} 
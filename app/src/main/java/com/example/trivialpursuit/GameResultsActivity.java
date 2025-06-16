package com.example.trivialpursuit;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.ArrayList;

public class GameResultsActivity extends AppCompatActivity {
    private LinearLayout containerResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);

        containerResultat = findViewById(R.id.containerResultat);

        // Récupérer le nom du gagnant
        String gagnant = getIntent().getStringExtra("GAGNANT");
        if (gagnant != null) {
            TextView tvGagnant = new TextView(this);
            tvGagnant.setTextSize(24);
            tvGagnant.setText(gagnant + " a gagné la partie !");
            tvGagnant.setPadding(0, 16, 0, 16);
            containerResultat.addView(tvGagnant);
        }
    }
} 
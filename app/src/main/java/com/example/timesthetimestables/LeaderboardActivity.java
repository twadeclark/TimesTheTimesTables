package com.example.timesthetimestables;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class LeaderboardActivity extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        backButton = (Button) findViewById(R.id.backLeaderButton);
        backButton.setOnClickListener(view -> finish());

        getSupportActionBar().setTitle("Leaderboard");

        TextView tmpTextView;
        SharedPreferences prefs;

        tmpTextView = (TextView) findViewById(R.id.luluModeTextView);
        prefs = this.getSharedPreferences(GameType.LULUMODE.toString(), Context.MODE_PRIVATE);
        setHighScores(tmpTextView, prefs);

        tmpTextView = (TextView) findViewById(R.id.easyPeasyTextView);
        prefs = this.getSharedPreferences(GameType.EASYPEASY.toString(), Context.MODE_PRIVATE);
        setHighScores(tmpTextView, prefs);

        tmpTextView = (TextView) findViewById(R.id.SquaresBearsTextView);
        prefs = this.getSharedPreferences(GameType.SQUARESBEARS.toString(), Context.MODE_PRIVATE);
        setHighScores(tmpTextView, prefs);

        tmpTextView = (TextView) findViewById(R.id.CenturyTextView);
        prefs = this.getSharedPreferences(GameType.CENTURY.toString(), Context.MODE_PRIVATE);
        setHighScores(tmpTextView, prefs);

        tmpTextView = (TextView) findViewById(R.id.UltimateTextView);
        prefs = this.getSharedPreferences(GameType.ULTIMATECHALLENGE.toString(), Context.MODE_PRIVATE);
        setChallengeHighScore(tmpTextView, prefs);

    }

    private void setHighScores(TextView tmpTextView, SharedPreferences prefs) {
        int score = prefs.getInt("score", 0);
        String initials = prefs.getString("initials", "");

        if (score > 0) {
            String tmp = initials + " \t " + String.format("%.2f", (float)score / 1000.0f) + "\n";
            tmpTextView.setText(tmp);
        }
    }

    private void setChallengeHighScore(TextView tmpTextView, SharedPreferences prefs) {
        int score = prefs.getInt("score", 0);
        String initials = prefs.getString("initials", "");

        if (score > 0) {
            int i = (int)score;
            String tmp = initials + " \t " + i + "\n";
            tmpTextView.setText(tmp);
        }
    }

}
package com.twadeclark.timesthetimestables;

import static com.twadeclark.timesthetimestables.Utils.scrambleButton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class GameOverActivity extends AppCompatActivity {

    private Button saveOrMenuButton;
    private TextView gameOverMsg;
    private TextView gameOverDetailsMsg;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Vibrator vibrator;

    private Random r = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        gameOverMsg = (TextView) findViewById(R.id.gameOverMsg);
        gameOverDetailsMsg = (TextView) findViewById(R.id.gameOverDetailsMsg);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);

        saveOrMenuButton = (Button) findViewById(R.id.saveOrMenuButton);
        saveOrMenuButton.setOnClickListener(view -> finish());
        saveOrMenuButton.setText("menu");

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        spinner3.setVisibility(View.GONE);

        scrambleButton(saveOrMenuButton);

        Bundle bundle = getIntent().getExtras();
        GameType gameType = GameType.valueOf(bundle.getString("GameType"));
        int elapsedTime = bundle.getInt("ElapsedTime");
        int currentScore = bundle.getInt("CurrentScore");
        boolean isWin = bundle.getBoolean("IsWin");

        SharedPreferences prefs = this.getSharedPreferences(gameType.toString(), Context.MODE_PRIVATE);
        int bestScore = prefs.getInt("bestScore", 0);

        String msg = "Keep Studying!";

        if(gameType == GameType.ULTIMATECHALLENGE) {
            msg = "your score: " + currentScore + "\n";;

            if (currentScore > bestScore || bestScore == 0) {
                playWinSong();
                gameOverMsg.setText("New Record!");
                msg += "old hi score: " + bestScore;;

                gotHiScore(gameType, currentScore);
            } else {
                playLossSong();
                gameOverMsg.setText("Game Over");
                msg += "current hi score: " + bestScore;;
            }
        } else if (isWin) { // regular game win
            playWinSong();
            gameOverMsg.setText("You Win!");
            String yourTime = String.format("%.2f", (float)elapsedTime / 1000.0f);
            String oldRecord = String.format("%.2f", (float)bestScore / 1000.0f);
            msg = "your time: " + yourTime + "\n";;

            if (elapsedTime < bestScore || bestScore == 0) { // new record
                msg += "old record: " + oldRecord + "\n";
                gotHiScore(gameType, elapsedTime);
            } else {
                msg += "current record: " + oldRecord;
            }

        } else { // regular game loss
            playLossSong();
            gameOverMsg.setText("Game Over");
        }

        gameOverDetailsMsg.setText(msg);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }

    }

    private void gotHiScore(GameType gameType, int currentScore) {
        setupInitials();
        saveOrMenuButton.setOnClickListener(view -> saveInitials(currentScore, gameType));
        saveOrMenuButton.setText("save");
    }

    private void playWinSong() {
        MediaPlayer song = MediaPlayer.create(this, R.raw.bagpipevictory28720); // https://pixabay.com/sound-effects/search/victory/
        song.start();
    }

    private void playLossSong() {
        MediaPlayer song = MediaPlayer.create(this, R.raw.sfxdefeat1);
        song.start();
    }

    private void saveInitials(int score, GameType gt) {
        SharedPreferences prefs = this.getSharedPreferences(gt.toString(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String initials = spinner1.getSelectedItem().toString() + spinner2.getSelectedItem().toString() + spinner3.getSelectedItem().toString();
        editor.putString("initials", initials);
        editor.putInt("bestScore", score);

        editor.commit();
        finish();
    }

    private void setupInitials() {
        spinner1.setVisibility(View.VISIBLE);
        spinner2.setVisibility(View.VISIBLE);
        spinner3.setVisibility(View.VISIBLE);
        String[] items = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "_"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, R.layout.my_spinner, items);
        spinner1.setAdapter(ad);
        spinner2.setAdapter(ad);
        spinner3.setAdapter(ad);
    }

}
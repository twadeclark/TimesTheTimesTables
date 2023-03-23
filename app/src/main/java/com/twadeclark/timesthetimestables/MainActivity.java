package com.twadeclark.timesthetimestables;

import static com.twadeclark.timesthetimestables.Utils.scrambleButton;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button easyPeasyButton;
    private Button squaresBearsButton;
    private Button centuryButton;
    private Button ultimateChallengeButton;
    private Button luluModeButton;
    private TextView whatTextView;
    private TextView bangTextView;
    private TextView dotdotdotTextView;


    private Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyPeasyButton = (Button) findViewById(R.id.easyPeasyButton);
        easyPeasyButton.setOnClickListener(view -> startClick(GameType.EASYPEASY));

        squaresBearsButton = (Button) findViewById(R.id.squaresBearsButton);
        squaresBearsButton.setOnClickListener(view -> startClick(GameType.SQUARESBEARS));

        centuryButton = (Button) findViewById(R.id.centuryButton);
        centuryButton.setOnClickListener(view -> startClick(GameType.CENTURY));

        ultimateChallengeButton = (Button) findViewById(R.id.ultimateChallengeButton);
        ultimateChallengeButton.setOnClickListener(view -> startClick(GameType.ULTIMATECHALLENGE));

        luluModeButton = (Button) findViewById(R.id.luluModeButton);
        luluModeButton.setOnClickListener(view -> startClick(GameType.LULUMODE));

        whatTextView = (TextView) findViewById(R.id.whatTextView);
        whatTextView.setOnClickListener(view -> whatTextViewClick());

        bangTextView = (TextView) findViewById(R.id.bangTextView);
        bangTextView.setOnClickListener(view -> bangTextViewClick());

        dotdotdotTextView = (TextView) findViewById(R.id.dotdotdotTextView);
        dotdotdotTextView.setOnClickListener(view -> dotdotdotTextViewClick());

    }

    @Override
    protected void onStart() {
        scrambleButton(easyPeasyButton);
        scrambleButton(squaresBearsButton);
        scrambleButton(centuryButton);
        scrambleButton(ultimateChallengeButton);

        super.onStart();
    }

    private void startClick(GameType gameType) {
        Intent intent = new Intent(this, GameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("GameType", gameType.toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void whatTextViewClick() {
        Intent intent = new Intent(this, RulesActivity.class);
        startActivity(intent);
    }

    private void bangTextViewClick() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    private void dotdotdotTextViewClick() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}
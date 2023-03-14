package com.example.timesthetimestables;

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
    private TextView whatTextView;
    private TextView bangTextView;

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

        ultimateChallengeButton = (Button) findViewById(R.id.menuButton);
        ultimateChallengeButton.setOnClickListener(view -> startClick(GameType.ULTIMATECHALLENGE));

        whatTextView = (TextView) findViewById(R.id.whatTextView);
        whatTextView.setOnClickListener(view -> whatTextViewClick());

        bangTextView = (TextView) findViewById(R.id.bangTextView);
        bangTextView.setOnClickListener(view -> bangTextViewClick());

    }

    @Override
    protected void onStart() {
        scrambleButton(easyPeasyButton);
        scrambleButton(squaresBearsButton);
        scrambleButton(centuryButton);
        scrambleButton(ultimateChallengeButton);

        super.onStart();
    }

    private void scrambleButton(Button tmpButton) {
        int i = r.nextInt(40) - 20;
        tmpButton.setRotation(i);
        GradientDrawable gradientDrawable;
        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, gradientColorGeneratorLight());
        gradientDrawable.setCornerRadius(80f);
        tmpButton.setBackground(gradientDrawable);
    }

    @NonNull
    private int[] gradientColorGeneratorLight() {
        int[] startRaw = new int[]{r.nextInt(128) + 128, r.nextInt(128) + 128, r.nextInt(128) + 128};
        startRaw[r.nextInt(3)] = 255;

        int[] endRaw = new int[]{r.nextInt(128) + 128, r.nextInt(128) + 128, r.nextInt(128) + 128};
        endRaw[r.nextInt(3)] = 255;

        String startColor = String.format("%02X", startRaw[0]) + String.format("%02X", startRaw[1]) + String.format("%02X", startRaw[2]);
        String endColor = String.format("%02X", endRaw[0]) + String.format("%02X", endRaw[1]) + String.format("%02X", endRaw[2]);

        int[] ButtonColors = {Color.parseColor("#" + startColor),Color.parseColor("#" + endColor)};

        return ButtonColors;
    }

    private void startClick(GameType gameType) {
        Intent intent = new Intent(this, GameActivity.class);

        //Create the bundle
        Bundle bundle = new Bundle();

        //Add your data to bundle
        bundle.putString("GameType", gameType.toString());

        //Add the bundle to the intent
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

}
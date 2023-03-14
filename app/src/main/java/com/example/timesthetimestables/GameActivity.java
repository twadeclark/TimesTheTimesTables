package com.example.timesthetimestables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private final Integer MaxValue = 10;

    private Button startButton;
    private TextView equation;
    private TextView gameOverMsg;
    private TextView gameOverDetailsMsg;

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Vibrator vibrator;

    private GameType gameType;
    private Random r = new Random();
    private Integer answer;
    private Integer currentScore = 0, highScore = 0;
    private Integer[] possibleAnswers;
    private long timeStart;


    private ArrayList<String> flashCard = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        startButton = (Button) findViewById(R.id.menuButton);
        equation = (TextView) findViewById(R.id.equation);
        gameOverMsg = (TextView) findViewById(R.id.gameOverMsg);
        gameOverDetailsMsg = (TextView) findViewById(R.id.gameOverDetailsMsg);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        startButton.setOnClickListener(view -> finish());
        button0.setOnClickListener(view -> answerCheck(0));
        button1.setOnClickListener(view -> answerCheck(1));
        button2.setOnClickListener(view -> answerCheck(2));
        button3.setOnClickListener(view -> answerCheck(3));

        Bundle bundle = getIntent().getExtras();
        gameType = GameType.valueOf(bundle.getString("GameType"));

        setFlashCards();

        scrambleButtons();

        timeStart = System.currentTimeMillis();
        startClick();
    }

    private void setFlashCards() {
        Integer a, s;

        switch (gameType) {
            case ULTIMATECHALLENGE:
                flashCard.add("0,0");
                break;
            case EASYPEASY:
                for(a = 0; a <= MaxValue; a++) {
                    for(s = 0; s <= 1; s++ ) {
                        flashCard.add(a.toString() + "," + s.toString());
                        flashCard.add(s.toString() + "," + a.toString());
                    }
                    flashCard.add(a.toString() + ",10");
                    flashCard.add("10," + a.toString());
                }
                break;
            case SQUARESBEARS:
                for(s = 0; s <= MaxValue; s++ ) {
                    flashCard.add(s.toString() + "," + s.toString());
                }
                for(a = 6; a <= 8; a++) {
                    for(s = 6; s <= 8; s++ ) {
                        flashCard.add(a.toString() + "," + s.toString());
                    }
                }
                break;
            case CENTURY:
                for(a = 0; a <= MaxValue; a++) {
                    for(s = 0; s <= MaxValue; s++ ) {
                        flashCard.add(a.toString() + "," + s.toString());
                    }
                }
                break;
        }
    }

    private void scrambleButtons() {
        startButton.setRotation(r.nextInt(20) - 10);
        button0.setRotation(r.nextInt(20) - 10);
        button1.setRotation(r.nextInt(20) - 10);
        button2.setRotation(r.nextInt(20) - 10);
        button3.setRotation(r.nextInt(20) - 10);

        GradientDrawable gradientDrawable;

        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, gradientColorGeneratorLight());
        gradientDrawable.setCornerRadius(80f);
        startButton.setBackground(gradientDrawable);

        gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, gradientColorGeneratorLight());
        gradientDrawable.setCornerRadius(80f);
        button0.setBackground(gradientDrawable);
        button1.setBackground(gradientDrawable);
        button2.setBackground(gradientDrawable);
        button3.setBackground(gradientDrawable);

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

    @NonNull
    private int[] gradientColorGeneratorDark() {
        int[] startRaw = new int[]{r.nextInt(128), r.nextInt(128), r.nextInt(128)};
        startRaw[r.nextInt(3)] = 0;

        int[] endRaw = new int[]{r.nextInt(128), r.nextInt(128), r.nextInt(128)};
        endRaw[r.nextInt(3)] = 0;

        String startColor = String.format("%02X", startRaw[0]) + String.format("%02X", startRaw[1]) + String.format("%02X", startRaw[2]);
        String endColor = String.format("%02X", endRaw[0]) + String.format("%02X", endRaw[1]) + String.format("%02X", endRaw[2]);

        int[] ButtonColors = {Color.parseColor("#" + startColor),Color.parseColor("#" + endColor)};

        return ButtonColors;
    }

    private void answerCheck(Integer i) {
        if(possibleAnswers[i].equals(answer) ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE ));
            }
            currentScore++;
            setScore();

            if (flashCard.isEmpty()) {
                String g = "You Win\n";

                if(gameType==GameType.EASYPEASY) {
                    g += "Easy Peasy\n";
                } else if(gameType==GameType.SQUARESBEARS) {
                    g += "Squares\n& Bears\n";
                } else if(gameType==GameType.CENTURY) {
                    g += "Century Club\n";
                } else {
                    g += "Big Winner\n";
                }

                setGameOverDetails();

                gameOverMsg.setText(g);
                gameOverMsg.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.VISIBLE);
                equation.setVisibility(View.GONE);
                button0.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);

                currentScore = 0;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(500);
                }

                MediaPlayer song = MediaPlayer.create(this, R.raw.bagpipevictory28720); // https://pixabay.com/sound-effects/search/victory/
                song.start();
            } else {
                nextRound();
            }
        }
        else {
            setGameOverDetails();

            gameOverMsg.setText("game over\nfinal score: " + currentScore);
            gameOverMsg.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.VISIBLE);
            equation.setVisibility(View.GONE);
            button0.setVisibility(View.GONE);
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);

            currentScore = 0;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(500);
            }

            MediaPlayer song = MediaPlayer.create(this, R.raw.sfxdefeat1);
            song.start();
        }
    }

    private void setGameOverDetails() {
        Float elapsedTime = (float) (System.currentTimeMillis() - timeStart);
        String d = String.format("%.2f", elapsedTime / 1000.0f) + " seconds\n";
        d += currentScore > 0 ? String.format("%.3f", elapsedTime / currentScore / 1000.0f) + " average" : "";
        gameOverDetailsMsg.setText(d);
        gameOverDetailsMsg.setVisibility(View.VISIBLE);
    }

    private void startClick() {
        startButton.setVisibility(View.GONE);
        gameOverMsg.setVisibility(View.GONE);
        gameOverDetailsMsg.setVisibility(View.GONE);
        equation.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);

        currentScore = 0;
        setScore();
        nextRound();
    }

    private void nextRound() {
        Integer e1 = 0;
        Integer e2 = 0;

        if (gameType == GameType.ULTIMATECHALLENGE) {
            e1 = r.nextInt(MaxValue + 1);
            e2 = r.nextInt(MaxValue + 1);
        } else {
            int answerIndex = r.nextInt(flashCard.size());
            String[] s = flashCard.get(answerIndex).split(",");

            e1 = Integer.parseInt(s[0]);
            e2 = Integer.parseInt(s[1]);

            flashCard.remove(answerIndex);
        }

        possibleAnswers = new Integer[4];

        answer = e1 * e2;
        equation.setText(e1 + " x " + e2);

        int tmp;
        for(int i = 0; i < 4; i++){
            do {
                tmp = answerGenerator(e1, e2);
            } while (Arrays.asList(possibleAnswers).contains(tmp) || tmp == answer);

            possibleAnswers[i] = tmp;
        }

        possibleAnswers[r.nextInt(4)] = answer; // ensure the actual answer is in there somewhere

        button0.setText(possibleAnswers[0].toString());
        button1.setText(possibleAnswers[1].toString());
        button2.setText(possibleAnswers[2].toString());
        button3.setText(possibleAnswers[3].toString());

        scrambleButtons();
    }

    private void setScore() {





        // if there's an array of answers then show how many are left
        // else score / highscore
        switch (gameType) {
            case ULTIMATECHALLENGE:
                if(currentScore > highScore){
                    highScore = currentScore;
                }
                getSupportActionBar().setTitle("Score: " + currentScore + "      High Score: " + highScore);
                break;
            case EASYPEASY:
            case SQUARESBEARS:
            case CENTURY:
                getSupportActionBar().setTitle("Score: " + currentScore + "      Remaining: " + flashCard.size());
                break;
        }







    }

    private Integer answerGenerator(Integer a, Integer b){
        Integer retVal;

        Integer aOffset = a + r.nextInt(2) - 1;
        Integer bOffset = b + r.nextInt(2) - 1;

        switch (r.nextInt(10)) { // close tricky wrong answers not just random numbers, weighted to the default case
            case 0:
                retVal = a + b;
                break;
            case 1:
                retVal = a - b;
                break;
            case 2:
                retVal = Integer.parseInt(a.toString() + b.toString());
                break;
            case 3:
                retVal = Integer.parseInt(b.toString() + a.toString());
                break;
            case 4:
                retVal = a * b + 1;
                break;
            case 5:
                retVal = a * b - 1;
                break;
            case 6:
                retVal = r.nextInt(MaxValue * MaxValue);
                break;
            default:
                retVal = aOffset * bOffset;
                break;
        }

        return Math.abs(retVal);
    }
}
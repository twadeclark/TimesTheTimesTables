package com.twadeclark.timesthetimestables;

import static com.twadeclark.timesthetimestables.Utils.gradientColorGeneratorLight;
import static com.twadeclark.timesthetimestables.Utils.scrambleButton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private final Integer MaxValue = 10;

    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private TextView equation;
    private Vibrator vibrator;

    private GameType gameType;
    private Random r = new Random();
    private Integer answer;
    volatile Integer currentScore = 0;
    private Integer[] possibleAnswers;
    private long timeStart;

    private ArrayList<String> flashCard = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        equation = (TextView) findViewById(R.id.equation);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        button0.setOnClickListener(view -> answerCheck(0));
        button1.setOnClickListener(view -> answerCheck(1));
        button2.setOnClickListener(view -> answerCheck(2));
        button3.setOnClickListener(view -> answerCheck(3));

        Bundle bundle = getIntent().getExtras();
        gameType = GameType.valueOf(bundle.getString("GameType"));

        loadFlashCards();
        scrambleButton(button0);
        scrambleButton(button1);
        scrambleButton(button2);
        scrambleButton(button3);

        timeStart = System.currentTimeMillis();
        setScore();
        nextRound();

//        // test data
//        saveInitials("TOM", 111111, GameType.LULUMODE);
//        saveInitials("DAD", 222222, GameType.EASYPEASY);
//        saveInitials("POO", 333333, GameType.SQUARESBEARS);
//        saveInitials("LOL", 444444, GameType.CENTURY);
//        saveInitials("_X_", 555555, GameType.ULTIMATECHALLENGE);

//        // clear it out
//        SharedPreferences settings;
//        settings = this.getSharedPreferences(GameType.LULUMODE.toString(), Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
//        settings = this.getSharedPreferences(GameType.EASYPEASY.toString(), Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
//        settings = this.getSharedPreferences(GameType.SQUARESBEARS.toString(), Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
//        settings = this.getSharedPreferences(GameType.CENTURY.toString(), Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
//        settings = this.getSharedPreferences(GameType.ULTIMATECHALLENGE.toString(), Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
    }

    private void loadFlashCards() {
        Integer a, s;

        switch (gameType) {
            case ULTIMATECHALLENGE:
                flashCard.add("0,0"); // stub
                break;
            case EASYPEASY:
                for(a = 0; a <= MaxValue; a++) {
                    flashCard.add(a.toString() + ",0");
                    flashCard.add(a.toString() + ",1");
                    flashCard.add(a.toString() + ",10");
                    flashCard.add("0," + a.toString());
                    flashCard.add("1," + a.toString());
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
            default:
                flashCard.add("1,1");
        }


        // testing mode
//        flashCard.clear();
//        flashCard.add("1,1");
//        flashCard.add("1,2");
//        flashCard.add("1,3");


    }

    private void setScore() {
        switch (gameType) {
            case EASYPEASY:
            case SQUARESBEARS:
            case CENTURY:
                getSupportActionBar().setTitle("Score: " + currentScore + "      Remaining: " + flashCard.size());
                break;
            case ULTIMATECHALLENGE:
            default:
                getSupportActionBar().setTitle("Score: " + currentScore);
                break;
        }
    }

    private void nextRound() {
        Integer e1, e2;

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

        scrambleButton(button0);
        scrambleButton(button1);
        scrambleButton(button2);
        scrambleButton(button3);
    }

    private void answerCheck(Integer i) {
        if(possibleAnswers[i].equals(answer) ) { // right answer
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE ));
            }
            currentScore++;
            setScore();

            if (flashCard.isEmpty()) { // game over, win!
                callGameOverActivity(true);
            } else {
                nextRound();
            }
        } else { // wrong answer
            callGameOverActivity(false);
        }
    }

    private void callGameOverActivity(boolean isWin) {
        Intent intent = new Intent(this, GameOverActivity.class);
        Bundle bundle = new Bundle();

        int elapsedTime = (int) (System.currentTimeMillis() - timeStart);
        bundle.putString("GameType", gameType.toString());
        bundle.putInt("ElapsedTime", elapsedTime);
        bundle.putInt("CurrentScore", currentScore);
        bundle.putBoolean("IsWin", isWin);

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
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
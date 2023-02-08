package com.example.timesthetimestables;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private TextView equation;
    private TextView youLose;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Vibrator vibrator;

    private Random r;
    private Integer answer;
    private Integer currentScore = 0, highScore = 0;
    private Integer[] possibleAnswers;

    private final Integer MaxValue = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        equation = (TextView) findViewById(R.id.equation);
        youLose = (TextView) findViewById(R.id.youLose);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        startButton.setOnClickListener(view -> startClick());
        button0.setOnClickListener(view -> answerCheck(0));
        button1.setOnClickListener(view -> answerCheck(1));
        button2.setOnClickListener(view -> answerCheck(2));
        button3.setOnClickListener(view -> answerCheck(3));

    }

    private void answerCheck(Integer i) {
        if(possibleAnswers[i].equals(answer) ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE ));
            }
            currentScore++;
            setScore();
            nextRound();
        }
        else {
            youLose.setText("game over\nfinal score: " + currentScore);
            youLose.setVisibility(View.VISIBLE);
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

    private void startClick() {
        startButton.setVisibility(View.GONE);
        youLose.setVisibility(View.GONE);
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
        r = new Random();

        Integer e1 = r.nextInt(MaxValue + 1);
        Integer e2 = r.nextInt(MaxValue + 1);

        answer = e1 * e2;
        equation.setText(e1 + " x " + e2);

        possibleAnswers = new Integer[4];

        int tmp;
        for(int i = 0; i < 4; i++){
            do {
                tmp = answerGenerator(e1, e2);
            } while (Arrays.asList(possibleAnswers).contains(tmp) || tmp == answer);

            possibleAnswers[i] = tmp;
        }

        possibleAnswers[r.nextInt(4)] = answer;

        button0.setText(possibleAnswers[0].toString());
        button1.setText(possibleAnswers[1].toString());
        button2.setText(possibleAnswers[2].toString());
        button3.setText(possibleAnswers[3].toString());
    }

    private void setScore() {
        if(currentScore > highScore){
            highScore = currentScore;
        }
        getSupportActionBar().setTitle("Score: " + currentScore + "      High Score: " + highScore);
    }

    private Integer answerGenerator(Integer a, Integer b){
        Integer retVal = 0;

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
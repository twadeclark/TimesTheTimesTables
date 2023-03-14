package com.example.timesthetimestables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    private TextView rulesTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        getSupportActionBar().setTitle("Rules");

        rulesTextView = (TextView) findViewById(R.id.rulesTextView);
        rulesTextView.setText("~ Easy Peasy ~" +
                "\n" +
                "(66 questions)" +
                "\n" +
                "Just the easiest from the times tables, but pay attention when they come at you fast!" +
                "\n\n" +
                "~ Squares & Bears ~" +
                "\n" +
                "(20 questions)" +
                "\n" +
                "Each number times itself, plus those hard to remember 6, 7, and 8's!" +
                "\n\n" +
                "~ Century Club ~" +
                "\n" +
                "(121 questions)" +
                "\n" +
                "Each of the questions from zero to ten times zero to ten, plus the squares twice." +
                "\n\n" +
                "~ Ultimate High Score Challenge ~" +
                "\n" +
                "See how many you can get right in a row! No limit!");
    }
}
package com.example.timesthetimestables;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
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

//        String rules = "~ Easy Peasy ~" +
//                "\n" +
//                "(66 questions)" +
//                "\n" +
//                "Just the easiest from the times tables, but pay attention when they come at you fast!" +
//                "\n\n" +
//                "~ Squares & Bears ~" +
//                "\n" +
//                "(20 questions)" +
//                "\n" +
//                "Each number times itself, plus those hard to remember 6, 7, and 8's!" +
//                "\n\n" +
//                "~ Century Club ~" +
//                "\n" +
//                "(121 questions)" +
//                "\n" +
//                "Each of the questions from zero to ten times zero to ten, plus the squares twice." +
//                "\n\n" +
//                "~ Ultimate High Score Challenge ~" +
//                "\n" +
//                "See how many you can get right in a row! No limit!";

//        rulesTextView.setText(rules);



/////
//        String t1 = "~ Easy Peasy ~";
//        Spannable s1 = new SpannableString(t1);
//        s1.setSpan(new RelativeSizeSpan(1.5f), 0, t1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        String t2 = "(66 questions)";
//        Spannable s2 = new SpannableString(t2);
//        s2.setSpan(new RelativeSizeSpan(.5f), 0, t2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


//        spannable.setSpan(new StyleSpan(Typeface.BOLD), 10, 14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannable.setSpan(new StyleSpan(Typeface.ITALIC), 19, 25, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannable.setSpan(new UnderlineSpan(), 30, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannable.setSpan(new ForegroundColorSpan(Color.BLUE), 19, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), 8, 21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String xmlString = getString(R.string.sample_text);
        Spanned spanned = Html.fromHtml(xmlString, Html.FROM_HTML_MODE_COMPACT);
//        textView.setText(spanned);

        rulesTextView.setText(spanned);


    }
}
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

        String xmlString = getString(R.string.rulesText);
        Spanned spanned = Html.fromHtml(xmlString, Html.FROM_HTML_MODE_COMPACT);

        rulesTextView.setText(spanned);
    }
}
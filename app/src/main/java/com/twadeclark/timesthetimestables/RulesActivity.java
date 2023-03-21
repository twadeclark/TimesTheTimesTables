package com.twadeclark.timesthetimestables;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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
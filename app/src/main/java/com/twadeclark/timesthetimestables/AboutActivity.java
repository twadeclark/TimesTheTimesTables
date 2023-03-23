package com.twadeclark.timesthetimestables;

import static com.twadeclark.timesthetimestables.Utils.scrambleButton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView rulesTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        backButton = (Button) findViewById(R.id.backAboutButton);
        backButton.setOnClickListener(view -> finish());
        scrambleButton(backButton);

        getSupportActionBar().setTitle("About");

        rulesTextView = (TextView) findViewById(R.id.aboutTextView);

        String xmlString = getString(R.string.aboutText);
        Spanned spanned = Html.fromHtml(xmlString, Html.FROM_HTML_MODE_COMPACT);

        rulesTextView.setText(spanned);
    }
}
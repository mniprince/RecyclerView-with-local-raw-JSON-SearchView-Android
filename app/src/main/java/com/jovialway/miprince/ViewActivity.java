package com.jovialway.miprince;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    TextView poemName, poemDetails;
    ImageView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        btn = findViewById(R.id.button);
        poemName = findViewById(R.id.poemNameTV);
        poemDetails = findViewById(R.id.poemDetailTV);


        Intent intent = getIntent();
        String name = intent.getStringExtra("surahName");
        String detail = intent.getStringExtra("surah");
        poemName.setText(name);
        poemDetails.setText(detail);
        poemDetails.setEllipsize(TextUtils.TruncateAt.END);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

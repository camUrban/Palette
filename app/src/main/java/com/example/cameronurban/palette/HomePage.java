package com.example.cameronurban.palette;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        TextView textView1 = (TextView) findViewById(R.id.viewPaletteText);
        textView1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this, PaletteGallery.class);
                startActivity(i);
            }
        });

        TextView textView2 = (TextView) findViewById(R.id.addColorText);
        textView2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,CreatePage.class);
                startActivity(i);
            }
        });
    }
}

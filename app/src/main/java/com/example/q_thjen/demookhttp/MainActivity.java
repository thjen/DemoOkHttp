package com.example.q_thjen.demookhttp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bt_getUlrMain, bt_getAnImage, bt_postData, bt_postFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_getUlrMain = findViewById(R.id.bt_getUrlMain);
        bt_getAnImage = findViewById(R.id.bt_getanimage);
        bt_postData = findViewById(R.id.bt_postData);
        bt_postFile = findViewById(R.id.bt_postFile);

        bt_getUlrMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, GetURL.class));
            }
        });

        bt_getAnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, GetAnImage.class));
            }
        });

        bt_postData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PostData.class));
            }
        });

        bt_postFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PostFile.class));
            }
        });

    }

}

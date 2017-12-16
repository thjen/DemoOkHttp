package com.example.q_thjen.demookhttp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetURL extends AppCompatActivity {

    // https://truyenclub.com/rss/the-loai/truyen-ma

    Button bt_get;
    EditText et_url;
    TextView tv_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bt_get = findViewById(R.id.bt_geturl);
        et_url = findViewById(R.id.et_url);
        tv_description = findViewById(R.id.tv_description);

        bt_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://" + et_url.getText().toString().trim();
                new GetUrl().execute(url);

            }
        });

    }

    class GetUrl extends AsyncTask<String, String, String> {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        @Override
        protected String doInBackground(String... strings) {

            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);

            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if ( !s.equals("")) {
                tv_description.append(s);
            } else {
                Toast.makeText(GetURL.this, "Error", Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(s);
        }
    }

}

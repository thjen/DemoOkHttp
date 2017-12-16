package com.example.q_thjen.demookhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetAnImage extends AppCompatActivity {

    ImageView iv_getImage;
    Button bt_getImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_an_image);

        iv_getImage = findViewById(R.id.iv_getimage);
        bt_getImage = findViewById(R.id.bt_getImage);

        bt_getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new GetImageFromUrl().execute("http://cdn.tgdd.vn/Files/2016/02/25/792685/android-marshmallow.jpg");
            }
        });

    }

    class GetImageFromUrl extends AsyncTask<String, Void, byte[]> {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        @Override
        protected byte[] doInBackground(String... strings) {

            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);

            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {

            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            iv_getImage.setImageBitmap(bitmap);

            super.onPostExecute(bytes);
        }
    }

}

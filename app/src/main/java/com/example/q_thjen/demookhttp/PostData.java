package com.example.q_thjen.demookhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostData extends AppCompatActivity {

    EditText et_user, et_pass;
    Button bt_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_data);

        et_user = findViewById(R.id.et_user);
        et_pass = findViewById(R.id.et_password);
        bt_post = findViewById(R.id.bt_post);

        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Post(et_user.getText().toString(), et_pass.getText().toString()).execute("http://192.168.1.104/Okhttp/postuser.php");
            }
        });

    }

    class Post extends AsyncTask<String, Void, String> {

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        String user, password;

        public Post(String user, String password) {
            this.user = user;
            this.password = password;
        }

        @Override
        protected String doInBackground(String... strings) {

            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("username", user)
                    .addFormDataPart("password", password)
                    .setType(MultipartBody.FORM)
                    .build();

            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .build();

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

            super.onPostExecute(s);
            Toast.makeText(PostData.this, s, Toast.LENGTH_SHORT).show();
        }
    }

}

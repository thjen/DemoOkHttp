package com.example.q_thjen.demookhttp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostFile extends AppCompatActivity {

    ImageView iv_posted;
    Button bt_send;

    int REQUEST_CODE = 1;

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_file);

        iv_posted = findViewById(R.id.iv_posted);
        bt_send = findViewById(R.id.bt_send);

        pickImage();

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostMyFile().execute("http://192.168.1.104/Okhttp/postfile.php");
            }
        });
    }

    private void pickImage() {

        iv_posted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            path = getRealPathFromUri(uri);

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                iv_posted.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    class PostMyFile extends AsyncTask<String, Void, String> {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();

        @Override
        protected String doInBackground(String... strings) {

            File file = new File(path);
            String content_type = getType(file.getPath());
            String file_path = file.getAbsolutePath();

            RequestBody file_body = RequestBody.create(MediaType.parse(content_type), file);

            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("update_image", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
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
            Toast.makeText(PostFile.this, s + "", Toast.LENGTH_SHORT).show();
            Log.d("error", s + "");
        }

    }

    public String getRealPathFromUri(Uri contentUri) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    private String getType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

}

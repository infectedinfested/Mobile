package com.example.kevin.mobile.Collectors;

import android.os.AsyncTask;

import com.google.common.io.ByteStreams;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;






public class getUrlContentTask extends AsyncTask<String, Void, String> {
    OkHttpClient client;

    public getUrlContentTask(){
        client = new OkHttpClient();
    }

    @Override
    protected String doInBackground(String... strings) {

        Request request = new Request.Builder()
                .url(strings[0])
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.code() != 200){
                return null;
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


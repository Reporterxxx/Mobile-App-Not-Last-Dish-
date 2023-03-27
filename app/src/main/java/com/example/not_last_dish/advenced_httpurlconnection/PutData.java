package com.example.not_last_dish.advenced_httpurlconnection;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PutData extends Thread {
    private String url, method;
    String result_data = "Empty";
    String[] data, field;

    public PutData(String url, String method, @NonNull String[] field, @NonNull String[] data) {
        this.url = url;
        this.method = method;
        this.data = new String[data.length];
        this.field = new String[field.length];
        System.arraycopy(field, 0, this.field, 0, field.length);
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    @Override
    public void run() {
        try {
            String UTF8 = "UTF-8", iso = "iso-8859-1";
            URL url = new URL(this.url);
            Log.v("TAG121", "start to connect1");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            Log.v("TAG121", "start to connect2");
            httpURLConnection.setRequestMethod(this.method);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            Log.v("TAG121", "start to connect3");
            // TODO: need to fix the case were ip is not correct or connection impossible
            OutputStream outputStream = httpURLConnection.getOutputStream();
            Log.v("TAG121", "start to connect4");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, UTF8));
            Log.v("TAG121", "start to connect5");
            StringBuilder post_data = new StringBuilder();
            Log.v("TAG121", "start to connect");
            for (int i = 0; i < this.field.length; i++) {
                post_data.append(URLEncoder.encode(this.field[i], "UTF-8")).append("=").append(URLEncoder.encode(this.data[i], UTF8)).append("&");
            }
            bufferedWriter.write(post_data.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, iso));
            StringBuilder result = new StringBuilder();
            String result_line;
            while ((result_line = bufferedReader.readLine()) != null) {
                result.append(result_line);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            setData(result.toString());
        } catch (IOException e) {
            setData(e.toString());
        }
    }

    public boolean startPut() {
        PutData.this.start();
        return true;
    }

    public boolean onComplete() {
        while (true) {
            if (!this.isAlive()) {
                return true;
            }
        }
    }

    public String getResult() {
        return this.getData();
    }

    public void setData(String result_data) {
        this.result_data = result_data;
    }


    public String getData() {
        return result_data;
    }
}

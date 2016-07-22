package com.example.khoahoang.whatstheweather;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String API_KEY = "5f31a31cdc656047ae17f6317b257cf9";
        WeatherAPIConsumer task = new WeatherAPIConsumer();
        task.execute("http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID="+API_KEY);


    }

    public class WeatherAPIConsumer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1) {
                    char current = (char)data;
                    result += current;
                    data = reader.read();
                }

            } catch (Exception e) {
                return "Failed";
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Remember that this method can interact with the main UI thread!!!
        }
    }
}

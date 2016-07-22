package com.example.khoahoang.whatstheweather;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    final String API_KEY = "5f31a31cdc656047ae17f6317b257cf9";
    EditText city;
    TextView weatherDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (EditText)findViewById(R.id.cityInput);
        //Button btn = (Button)findViewById(R.id.button);
        weatherDisplay = (TextView)findViewById(R.id.weatherDisplay);
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
                JSONObject jsonData = new JSONObject(result);
                String weatherInfo = jsonData.getString("weather");
                JSONArray arr = new JSONArray(weatherInfo);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject curJSON = arr.getJSONObject(i);
                    weatherDisplay.append(curJSON.getString("main") + ": " + curJSON.getString("description") + "\n");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //. Remember that this method can interact with the main UI thread!!!
            //. Process the JSON here and put relevant data in weatherDisplay!!
            Log.i("JSON String", result);

        }
    }

    public void processClick(View view) {
        WeatherAPIConsumer task = new WeatherAPIConsumer();
        weatherDisplay.setText("");
        try {
            task.execute("http://api.openweathermap.org/data/2.5/weather?q="+ URLEncoder.encode(city.getText().toString(), "UTF-8")+"&APPID="+API_KEY);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

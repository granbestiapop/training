package com.example.ml.ejercicio.utils;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.example.ml.ejercicio.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lclavijo on 10/24/16.
 */

public class AsyncGetItem extends AsyncTask<String, Void, DummyContent.DummyItem> {

    private View activity;
    private View image;
    private String term;

    public AsyncGetItem(View v, View image){
        this.image= image;
        activity= v;
    }

    @Override
    protected DummyContent.DummyItem doInBackground(String... urls) {
        BufferedReader reader= null;
        HttpURLConnection http= null;

        try {
            String itemId= urls[0];
            URL url= new URL("https://api.mercadolibre.com/items/"+itemId);
            http= (HttpURLConnection)url.openConnection();
            http.connect();
            InputStream content= http.getInputStream();
            reader= new BufferedReader(new InputStreamReader(content, "UTF-8"));
            String s= reader.readLine();
            JSONObject json= new JSONObject(s);
            reader.close();

            return DummyContent.DummyItem.fromJSON(json);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            http.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(DummyContent.DummyItem item) {
        JSONArray results= null;
        try {
            //TextView tv= (TextView) activity;
            //tv.setText(item.content);

            new ImageDownloader(image).execute(item.images.get(0));
            super.onPostExecute(item);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

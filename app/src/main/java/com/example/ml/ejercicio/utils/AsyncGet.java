package com.example.ml.ejercicio.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.ItemsInfo;
import com.example.ml.ejercicio.interfaces.OnListFragmentInteractionListener;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lclavijo on 10/24/16.
 */

public class AsyncGet extends AsyncTask<String, Void, JSONObject> {

    private OnListFragmentInteractionListener activity;
    private String term;

    public AsyncGet(OnListFragmentInteractionListener v){
        activity= v;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        try {
            String search= "https://api.mercadolibre.com/sites/MLU/search?q="+urls[0];
            URL url= new URL(search);
            HttpURLConnection http= (HttpURLConnection)url.openConnection();
            http.connect();
            InputStream content= http.getInputStream();
            BufferedReader reader= new BufferedReader(new InputStreamReader(content, "UTF-8"));
            String s= reader.readLine();
            reader.close();
            http.disconnect();

            JSONObject json= new JSONObject(s);
            return json;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        JSONArray results= null;
        try {
            results = json.getJSONArray("results");
            //clear list

            List<Item> list= new ArrayList<>();
            for(int i=0; i<results.length(); i++){
                JSONObject o= results.getJSONObject(i);
                String title= o.getString("title");
                Double price= o.getDouble("price");
                String id= o.getString("id");
                String thumb= o.getString("thumbnail");
                list.add(new Item(id,title,price.toString(),thumb));
            }
            //refresh view
            //RecyclerView r= (RecyclerView)activity;
            ItemsInfo iInfo= new ItemsInfo(list);
            activity.setItems(iInfo);

            Log.d("SEARCH", "Finish search");
            super.onPostExecute(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

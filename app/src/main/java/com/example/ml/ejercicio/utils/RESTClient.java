package com.example.ml.ejercicio.utils;

import com.example.ml.ejercicio.dummy.DummyContent;
import com.example.ml.ejercicio.dummy.Item;

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
 * Created by lclavijo on 10/25/16.
 */

public class RESTClient {

    public static DummyContent.DummyItem getItem(String itemId){
        BufferedReader reader= null;
        HttpURLConnection http= null;

        try {
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

}

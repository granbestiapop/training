package com.example.ml.ejercicio.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.ItemWrap;
import com.example.ml.ejercicio.dto.models.ItemsInfo;
import com.example.ml.ejercicio.interfaces.OnListFragmentInteractionListener;
import com.example.ml.ejercicio.utils.Constants;
import com.example.ml.ejercicio.utils.ImageContainer;

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
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ImagesService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.ml.ejercicio.services.action.FOO";
    private static final String ACTION_BAZ = "com.example.ml.ejercicio.services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.ml.ejercicio.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.ml.ejercicio.services.extra.PARAM2";

    private OnListFragmentInteractionListener mlistener;

    public ImagesService() {
        super("ImagesService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ImagesService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ImagesService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public void setSearchInterface(OnListFragmentInteractionListener mlistener) {
       this.mlistener= mlistener;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String param1 = intent.getStringExtra(Constants.TERM);
            final String action= intent.getAction();
            Log.d("SERVICE", action);
            if(action.equals("GET_ITEM")){
                List<ItemWrap> list= getItems(param1);
                ItemsInfo iInfo= new ItemsInfo(list);
                mlistener.setItems(iInfo);
            }
        }
    }

    private Bitmap downloadUrls(String urlname){
        String fileName = urlname.substring(urlname.lastIndexOf('/')+1, urlname.length());

        Bitmap b= ImageContainer.readFile(fileName);
        if(b!=null){
            return b;
        }
        HttpURLConnection http= null;
        try {
            URL url= new URL(urlname);
            http= (HttpURLConnection)url.openConnection();
            http.connect();
            InputStream content= http.getInputStream();
            Bitmap bm= BitmapFactory.decodeStream(content);

            //Save on cache
            ImageContainer.writeFile(fileName,bm);
            return bm;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            http.disconnect();
        }
        return null;
    }

    public List<ItemWrap> getItems(String term){
        try {
            String search= "https://api.mercadolibre.com/sites/MLU/search?q="+term;
            URL url= new URL(search);
            HttpURLConnection http= (HttpURLConnection)url.openConnection();
            http.connect();
            InputStream content= http.getInputStream();
            BufferedReader reader= new BufferedReader(new InputStreamReader(content, "UTF-8"));
            String s= reader.readLine();
            reader.close();
            http.disconnect();

            JSONObject json= new JSONObject(s);
            JSONArray results = json.getJSONArray("results");
            List<ItemWrap> list= new ArrayList<>();
            for(int i=0; i<results.length(); i++){
                JSONObject o= results.getJSONObject(i);
                String title= o.getString("title");
                Double price= o.getDouble("price");
                String id= o.getString("id");
                String thumb= o.getString("thumbnail");
                list.add(new ItemWrap(id,title,price.toString(),thumb));
            }
            return list;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void downloadURLS(Context context, String url) {
        Intent intent = new Intent(context, ImagesService.class);
        intent.setAction(ACTION_BAZ);
        context.startService(intent);
    }

}

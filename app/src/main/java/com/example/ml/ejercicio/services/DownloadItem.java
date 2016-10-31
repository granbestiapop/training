package com.example.ml.ejercicio.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.ItemsInfo;
import com.example.ml.ejercicio.dummy.DummyContent;
import com.example.ml.ejercicio.interfaces.OnListFragmentInteractionListener;
import com.example.ml.ejercicio.utils.Constants;

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
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadItem extends IntentService {

    private OnListFragmentInteractionListener handler;
    private static final String ACTION_FOO = "com.example.ml.ejercicio.services.action.FOO";
    private static final String ACTION_BAZ = "com.example.ml.ejercicio.services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.ml.ejercicio.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.ml.ejercicio.services.extra.PARAM2";

    public DownloadItem() {
        super("DownloadItem");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DownloadItem.class);
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
        Intent intent = new Intent(context, DownloadItem.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            ResultReceiver rec = intent.getParcelableExtra(Constants.HANDLER);

            String itemId= intent.getExtras().getString(Constants.ITEM_ID);
            Item i= getItem(itemId);
            if(i!=null){

                Bundle b= new Bundle();
                b.putParcelable(Constants.ITEM_SEARCH, i);
                rec.send(0, b);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public Item getItem(String itemId){
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
            return Item.fromJSON(json);

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

package com.example.ml.ejercicio.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.ml.ejercicio.R;
import com.example.ml.ejercicio.dto.TrackDatabaseHandler;
import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.Track;
import com.example.ml.ejercicio.utils.Constants;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TrackIntentService extends IntentService {

    private TrackDatabaseHandler dbHandler;

    public static final String ADD_TRACK= "ADD_TRACK";
    public static final String DELETE_TRACK= "DELETE_TRACK";

    public TrackIntentService() {
        super("TrackIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dbHandler= new TrackDatabaseHandler(getApplicationContext());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            Item item= intent.getExtras().getParcelable(Constants.ITEM_ID);

            switch (action){
                case Constants.ADD_TRACK:
                    Log.d("TRACK_INTENT_SERVICE", "Add new track");
                    dbHandler.addTrack(item);
                    break;
                case Constants.DELETE_TRACK:
                    Log.d("TRACK_INTENT_SERVICE", "Delete track");
                    dbHandler.deleteItem(item.id);
                    break;
                default:
                    Log.d("TRACK_INTENT_SERVICE", "Nothing to handle");
                    break;
            }
        }
    }

}

package com.example.ml.ejercicio.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.ml.ejercicio.ProductActivity;
import com.example.ml.ejercicio.R;
import com.example.ml.ejercicio.dto.TrackDatabaseHandler;
import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.ItemWrap;
import com.example.ml.ejercicio.dto.models.ItemsInfo;
import com.example.ml.ejercicio.dto.models.Track;
import com.example.ml.ejercicio.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TrackService extends Service {
    private static Timer timer = new Timer();
    private static final int TIME= 30000;
    private TrackDatabaseHandler dbHandler;
    private NotificationManager mgr;

    private final int DAYS= 1;
    public TrackService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }


    public void onCreate() {
        super.onCreate();
        mgr= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        dbHandler= new TrackDatabaseHandler(getApplicationContext());
        startService();
    }

    private void startService() {
        timer.scheduleAtFixedRate(new cronTask(), 0, TIME);
    }

    private class cronTask extends TimerTask {
        public void run() {
            Map<String, Track> tracks= dbHandler.getTracks();
            Log.d("TRACK_SERVICE", "Track size "+ tracks.size());

            // Create ids to single query
            StringBuilder buff = new StringBuilder();
            String sep = "";
            for (Track t : tracks.values()) {
                buff.append(sep);
                buff.append(t.itemId);
                sep = ",";
            }
            String ids= buff.toString();

            if(!ids.equals("")){
                List<Item> items= DownloadItem.getItems(ids);
                for(Item i: items){
                    Log.d("TRACK_SERVICE", i.id);
                    Track compare= tracks.get(i.id);
                    boolean notificate= notificableDate(i);
                    if(!i.last_update.equals(compare.last_updated)){
                        sendNotificationUpdate(i);
                        Log.d("TRACK_SERVICE","Notification update");
                    }
                    if(notificate){
                        sendNotificationEndDate(i);
                        Log.d("TRACK_SERVICE","Notification expires");
                    }
                }
            }
        }
    }

    private void sendNotificationUpdate(Item item){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Producto actualizado");
        mBuilder.setContentText(item.getTitle());

        Intent intent= new Intent(this, ProductActivity.class);
        ItemsInfo itemsInfo= new ItemsInfo(new ArrayList<ItemWrap>());
        itemsInfo.setSearch(item);
        itemsInfo.setTrackeable(true);

        intent.putExtra(Constants.ITEM_ID, itemsInfo);

        PendingIntent i= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(i);
        mgr.notify(121, mBuilder.build());
    }

    private void sendNotificationEndDate(Item item){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Producto por expirar");
        mBuilder.setContentText(item.getTitle());

        Intent intent= new Intent(this, ProductActivity.class);
        ItemsInfo itemsInfo= new ItemsInfo(new ArrayList<ItemWrap>());
        itemsInfo.setSearch(item);
        itemsInfo.setTrackeable(true);

        intent.putExtra(Constants.ITEM_ID, itemsInfo);

        PendingIntent i= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(i);
        mgr.notify(121, mBuilder.build());
    }

    private boolean notificableDate(Item item){

        if(dbHandler.isNotified(item.id)){
            return false;
        }else{
            Log.d("TRACK_SERVICE", "NO NOTIFICADO");
        }

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String string1 = item.stop_time;
        try {
            Date endDate = df1.parse(string1);
            Date interval= new Date();
            Date now= new Date();
            interval.setDate(interval.getDate()-DAYS);
            if(endDate.after(interval) && endDate.before(now)){
                Log.d("TRACK_SERVICE", "NOTIFICATION DATE"+endDate.toString());
            }
            return endDate.after(interval) && endDate.before(now);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}

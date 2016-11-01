package com.example.ml.ejercicio;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.ItemsInfo;
import com.example.ml.ejercicio.dummy.DummyContent;
import com.example.ml.ejercicio.services.DownloadItem;
import com.example.ml.ejercicio.services.TrackIntentService;
import com.example.ml.ejercicio.utils.AsyncGetItem;
import com.example.ml.ejercicio.utils.Constants;

public class ProductActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private Item item;
    private ItemsInfo itemInfo;
    private boolean isTracked;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemInfo= (ItemsInfo) getIntent().getExtras().get(Constants.ITEM_ID);
        item= itemInfo.getSearch();
        isTracked= itemInfo.isTrackeable;

        fab = (FloatingActionButton) findViewById(R.id.fab);
        changeResourceFab();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Item tracked", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent= new Intent(getApplicationContext(), TrackIntentService.class);
                if(!isTracked){
                    intent.setAction(Constants.ADD_TRACK);
                }else{
                    intent.setAction(Constants.DELETE_TRACK);
                }
                intent.putExtra(Constants.ITEM_ID, item);
                startService(intent);
                isTracked= !isTracked;
                changeResourceFab();
            }
        });

        textView= (TextView) findViewById(R.id.item_description);
        imageView= (ImageView) findViewById(R.id.imageToolbar);
        textView.setText(item.getTitle());
    }

    private void changeResourceFab(){
        if(isTracked){
            fab.setImageResource(R.drawable.ic_close_black_24dp);
        }else{
            fab.setImageResource(R.drawable.ic_add_black_24dp);
        }
    }
}

package com.example.ml.ejercicio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.ItemWrap;
import com.example.ml.ejercicio.dto.models.ItemsInfo;
import com.example.ml.ejercicio.interfaces.OnListFragmentInteractionListener;
import com.example.ml.ejercicio.services.DownloadItem;
import com.example.ml.ejercicio.services.HandlerReceiver;
import com.example.ml.ejercicio.utils.AsyncGet;
import com.example.ml.ejercicio.utils.Constants;
import android.util.Log;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnListFragmentInteractionListener, HandlerReceiver.Receiver {

    private RecyclerView itemFragment;
    private HandlerReceiver handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ItemsInfo items= (ItemsInfo) getIntent().getExtras().get(Constants.LIST_ITEMS);

        setContentView(R.layout.activity_search_main);
        String term= getIntent().getExtras().getString(Constants.TERM);
        itemFragment=  (RecyclerView) findViewById(R.id.fragment);
        MyItemRecyclerViewAdapter adapter= new MyItemRecyclerViewAdapter(items.getItems(),this);
        itemFragment.setAdapter(adapter);

        SharedPreferences pref= getSharedPreferences(Constants.APP_NAME,0);
        SharedPreferences.Editor edit= pref.edit();
        edit.putString(Constants.LAST_QUERY, term);
        MainActivity.queries.put(term, term);
    }

    public void vipActivity(String itemId) {
        Log.d("SEARCH_ACTIVITY","VIP");
        Intent intent= new Intent(this, DownloadItem.class);
        intent.putExtra(Constants.ITEM_ID, itemId);
        handler= new HandlerReceiver(new Handler());
        handler.setReceiver(this);
        intent.putExtra(Constants.HANDLER, handler);
        startService(intent);
    }

    @Override
    public void onListFragmentInteraction(ItemWrap item) {
        vipActivity(item.id);
    }

    @Override
    public void setItems(ItemsInfo iInfo) {

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        ItemsInfo item= resultData.getParcelable(Constants.ITEM_SEARCH);
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(Constants.ITEM_ID, item);
        startActivity(intent);
    }
}

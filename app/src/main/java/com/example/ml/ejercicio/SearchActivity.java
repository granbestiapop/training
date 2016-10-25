package com.example.ml.ejercicio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.SharedPreferences;

import com.example.ml.ejercicio.dummy.DummyContent;
import com.example.ml.ejercicio.utils.AsyncGet;
import com.example.ml.ejercicio.utils.Constants;


public class SearchActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    private View itemFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        String term= getIntent().getExtras().getString(Constants.TERM);

        //get fragment
        itemFragment= findViewById(R.id.fragment);


        SharedPreferences pref= getSharedPreferences(Constants.APP_NAME,0);
        SharedPreferences.Editor edit= pref.edit();
        edit.putString(Constants.LAST_QUERY, term);
        MainActivity.queries.put(term, term);
        new AsyncGet(itemFragment).execute(term);
    }

    public void vipActivity(DummyContent.DummyItem item) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(Constants.ITEM_ID, item);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Log.d("CLICK", "Click on item"+ item.id);
        vipActivity(item);
    }
}

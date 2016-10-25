package com.example.ml.ejercicio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ml.ejercicio.dummy.DummyContent;

import android.util.Log;
import android.view.View;
import android.content.SharedPreferences;
import com.example.ml.ejercicio.utils.AsyncGet;
import com.example.ml.ejercicio.utils.Constants;


public class SearchActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        String term= getIntent().getExtras().getString(Constants.TERM);
        View v= findViewById(R.id.fragment);

        SharedPreferences pref= getSharedPreferences(Constants.APP_NAME,0);
        SharedPreferences.Editor edit= pref.edit();
        edit.putString(Constants.LAST_QUERY, term);
        MainActivity.queries.put(term, term);
        new AsyncGet(v).execute(term);
    }


    @Override
    public void onListFragmentInteraction() {

    }
}

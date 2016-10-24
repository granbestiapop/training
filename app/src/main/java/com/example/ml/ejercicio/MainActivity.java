package com.example.ml.ejercicio;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.ml.ejercicio.dummy.DummyContent;
import com.example.ml.ejercicio.utils.AsyncGet;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{

    private static Button searchButton;
    private View ifragment;
    private AutoCompleteTextView editText;
    public static final String LAST_QUERY= "LAST_QUERY";
    public static final String APP_NAME= "APP_NAME";

    private String last_query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        loadComponents(savedInstanceState);
        getAutocomplete();
        loadQuerys();
        setEvents();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        String toSearch = editText.getText().toString();
        Log.d("SEARCH", toSearch);
        // set last query
        SharedPreferences pref= getSharedPreferences(APP_NAME,0);
        SharedPreferences.Editor edit= pref.edit();
        edit.putString(LAST_QUERY, toSearch);
        new AsyncGet(ifragment).execute(toSearch);
    }

    private void getAutocomplete(){
        //autocomplete adapter
        String[] strings= {"Hola","Busqueda","Gibson", "gin"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, strings);
        editText.setAdapter(adapter);
    }

    private void loadQuerys(){
        last_query= getSharedPreferences(APP_NAME,0).getString(LAST_QUERY, "gibson");
        Log.d(LAST_QUERY, last_query);
    }

    private void setEvents(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onListFragmentInteraction(new DummyContent.DummyItem("hola","asd","asd"));
            }
        });
    }

    private void loadComponents(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton= (Button) findViewById(R.id.search);
        editText= (AutoCompleteTextView) findViewById(R.id.search_text);
        ifragment= findViewById(R.id.fragment);
    }
}

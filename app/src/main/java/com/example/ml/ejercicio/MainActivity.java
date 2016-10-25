package com.example.ml.ejercicio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.ml.ejercicio.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static Button searchButton;
    private View ifragment;
    private AutoCompleteTextView editText;

    private String last_query;
    public static Map<String,String> queries= new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadComponents(savedInstanceState);
        getAutocomplete();
        loadQuerys();
        setEvents();
    }


    public void searchActivity() {
        String toSearch = editText.getText().toString();
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(Constants.TERM, toSearch);
        startActivity(intent);
    }

    private void getAutocomplete(){
        List<String> list = new ArrayList<>(queries.values());
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        editText.setAdapter(adapter);
    }

    private void loadQuerys(){
        last_query= getSharedPreferences(Constants.APP_NAME,0).getString(Constants.LAST_QUERY, null);
        if(last_query!=null) {
            queries.put(last_query, last_query);
        }
    }

    private void setEvents(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchActivity();
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

    @Override
    public void onResume(){
        super.onResume();
        Log.d(MainActivity.class.toString(), "Resume");
        getAutocomplete();
    }

}

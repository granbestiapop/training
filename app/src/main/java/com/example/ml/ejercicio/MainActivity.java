package com.example.ml.ejercicio;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.ml.ejercicio.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener{

    private static Button searchButton;
    private View ifragment;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton= (Button) findViewById(R.id.search);
        editText= (EditText) findViewById(R.id.search_text);
        ifragment= findViewById(R.id.fragment);
        if(ifragment!=null){
            Log.d("FRAGMENT", "NO NULL");
        }else{
            Log.d("FRAGMENT", "NULL");
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onListFragmentInteraction(new DummyContent.DummyItem("hola","asd","asd"));
            }
        });
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        RecyclerView r = (RecyclerView) ifragment;
        String toSearch = editText.getText().toString();
        Log.d("SEARCH", toSearch);
        DummyContent.search(toSearch);
        r.getAdapter().notifyDataSetChanged();

    }
}

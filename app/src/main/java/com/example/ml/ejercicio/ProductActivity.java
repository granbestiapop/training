package com.example.ml.ejercicio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ml.ejercicio.dummy.DummyContent;
import com.example.ml.ejercicio.dummy.Item;
import com.example.ml.ejercicio.utils.AsyncGetItem;
import com.example.ml.ejercicio.utils.Constants;
import com.example.ml.ejercicio.utils.ImageDownloader;
import com.example.ml.ejercicio.utils.RESTClient;

public class ProductActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView= (TextView) findViewById(R.id.item_description);
        imageView= (ImageView) findViewById(R.id.imageToolbar);
        DummyContent.DummyItem item= (DummyContent.DummyItem) getIntent().getExtras().get(Constants.ITEM_ID);
        new AsyncGetItem(textView, imageView).execute(item.id);

    }
}

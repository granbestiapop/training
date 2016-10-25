package com.example.ml.ejercicio.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ml.ejercicio.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private WeakReference<ImageView> view;
    private String term;

    public ImageDownloader(View v){
        view= new WeakReference<ImageView>((ImageView) v);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        // check if is in cache
        String urlname= urls[0];
        String fileName = urlname.substring(urlname.lastIndexOf('/')+1, urlname.length());

        Bitmap b= ImageContainer.readFile(fileName);
        if(b!=null){
            Log.d("IMAGE","Get from cache");
            return b;
        }else{
            Log.d("IMAGE","Download"+ urlname);
        }

        HttpURLConnection http= null;
        try {
            URL url= new URL(urls[0]);
            http= (HttpURLConnection)url.openConnection();
            http.connect();
            InputStream content= http.getInputStream();
            Bitmap bm= BitmapFactory.decodeStream(content);

            //Save on cache
            ImageContainer.writeFile(fileName,bm);
            return bm;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            http.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView iv= view.get();
        if(view!=null && bitmap!=null){
            iv.setImageBitmap(bitmap);
        }else{
            iv.setImageResource(R.mipmap.ic_launcher);
        }
        super.onPostExecute(bitmap);
    }
}

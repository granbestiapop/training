package com.example.ml.ejercicio.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.example.ml.ejercicio.interfaces.DownloadRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lclavijo on 10/26/16.
 */

public class PhotoDownloadRunnable implements Runnable {

    private String url;
    final DownloadRunnable downloadRunnable;

    PhotoDownloadRunnable(DownloadRunnable task, String url){
        this.url= url;
        downloadRunnable= task;
    }

    private Bitmap download(String urlname){
        String fileName = urlname.substring(urlname.lastIndexOf('/')+1, urlname.length());

        Bitmap b= ImageContainer.readFile(fileName);
        if(b!=null){
            return b;
        }

        HttpURLConnection http= null;
        try {
            URL url= new URL(urlname);
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
    public void run() {
        Log.d("RUNNABLE", url);

    }
}

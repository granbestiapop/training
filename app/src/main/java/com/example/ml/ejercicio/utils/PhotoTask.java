package com.example.ml.ejercicio.utils;

import android.graphics.Bitmap;
import android.media.Image;
import android.widget.ImageView;

import com.example.ml.ejercicio.PhotoManager;
import com.example.ml.ejercicio.interfaces.DownloadRunnable;

import java.lang.ref.WeakReference;

/**
 * Created by lclavijo on 10/26/16.
 */

public class PhotoTask implements DownloadRunnable {

    private WeakReference<ImageView> mImageWeakRef;
    private PhotoManager sPhotoManager;

    public Runnable mDownloadRunnable;

    public void initializeDownloaderTask(PhotoManager photoManager, ImageView imageView, String url) {
        sPhotoManager = photoManager;
        mImageWeakRef = new WeakReference<ImageView>(imageView);
        mDownloadRunnable = new PhotoDownloadRunnable(this,url);
    }


    @Override
    public void setImageToView(Bitmap bitmap) {
        mImageWeakRef.get().setImageBitmap(bitmap);
    }
}

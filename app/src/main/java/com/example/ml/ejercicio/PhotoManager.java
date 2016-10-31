package com.example.ml.ejercicio;

import android.widget.ImageView;

import com.example.ml.ejercicio.utils.PhotoTask;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by lclavijo on 10/26/16.
 */

public class PhotoManager {
    // Sets the amount of time an idle thread will wait for a task before terminating
    private static final int KEEP_ALIVE_TIME = 1;

    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT= TimeUnit.SECONDS;

    // Sets the initial threadpool size to 8
    private static final int CORE_POOL_SIZE = 8;

    // Sets the maximum threadpool size to 8
    private static final int MAXIMUM_POOL_SIZE = 8;

    /**
     * NOTE: This is the number of total available cores. On current versions of
     * Android, with devices that use plug-and-play cores, this will return less
     * than the total number of cores. The total number of cores is not
     * available in current Android implementations.
     */
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    static PhotoManager instance;
    private final ThreadPoolExecutor mDownloadThreadPool;
    // A queue of Runnables for the image download pool
    private final BlockingQueue<Runnable> mDownloadWorkQueue;
    private final Queue<PhotoTask> mPhotoTaskWorkQueue;

    static {
        instance= new PhotoManager();
    }

    private PhotoManager(){
        mDownloadWorkQueue = new LinkedBlockingQueue<>();
        mDownloadThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, mDownloadWorkQueue);
        mPhotoTaskWorkQueue = new LinkedBlockingQueue<PhotoTask>();

    }

    public static void downloadImage(ImageView imageView, String url){
        //for(int i=0; i<10; i++){
            PhotoTask task= new PhotoTask();
            task.initializeDownloaderTask(instance, imageView, url);
            instance.mDownloadThreadPool.execute(task.mDownloadRunnable);
        //}
    }

}

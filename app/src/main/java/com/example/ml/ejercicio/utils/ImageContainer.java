package com.example.ml.ejercicio.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ml.ejercicio.MainActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class ImageContainer {
    public static void writeFile(String filename, Bitmap bitmap){
        Context ctx= MainActivity.getContext();

        try {
            FileOutputStream fo= ctx.openFileOutput(filename, ctx.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fo);
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readFile(String filename){
        Context ctx= MainActivity.getContext();

        InputStream s= null;
        try {
            s = ctx.openFileInput(filename);
        } catch (FileNotFoundException e) {
            return null;
        }
        return BitmapFactory.decodeStream(s);
    }
}

package com.example.ml.ejercicio.dto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackDatabaseHandler {

    private static DatabaseHandler dbHandler;

    private static final String[] projection = {
            Track.COLUMN_NAME_ITEMID,
            Track.COLUMN_NAME_LAST_UPDATED,
            Track.COLUMN_NAME_NOTIFIED
    };

    public TrackDatabaseHandler(Context context) {
        dbHandler = new DatabaseHandler(context);
    }

    public void addTrack(Item item) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Track.COLUMN_NAME_ITEMID, item.id);
        String last = item.last_update;
        values.put(Track.COLUMN_NAME_LAST_UPDATED, last);
        values.put(Track.COLUMN_NAME_NOTIFIED, 0);
        db.insert(Track.TABLE_NAME, null, values);
        db.close();
    }

    public Map<String, Track> getTracks() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(Track.TABLE_NAME, projection, null, null, null, null, null);
        Map<String, Track> map = new HashMap<>();
        if (cursor.moveToFirst()) {
            do {
                Track t = new Track();
                t.itemId = cursor.getString(cursor.getColumnIndexOrThrow(Track.COLUMN_NAME_ITEMID));
                t.last_updated = cursor.getString(cursor.getColumnIndexOrThrow(Track.COLUMN_NAME_LAST_UPDATED));
                t.notified = cursor.getInt(cursor.getColumnIndexOrThrow(Track.COLUMN_NAME_NOTIFIED));
                map.put(t.itemId, t);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return map;
    }

    public boolean isTracked(String itemId) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String selection = Track.COLUMN_NAME_ITEMID + " = ?";
        String[] selectionArgs = {itemId};
        Cursor cursor = db.query(Track.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        boolean tracked= cursor.moveToFirst();
        db.close();
        return tracked;
    }

    public boolean isNotified(String itemId) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String selection = Track.COLUMN_NAME_ITEMID + " = ?";
        String[] selectionArgs = {itemId};
        Cursor cursor = db.query(Track.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        boolean notified=false;
        if(cursor.moveToFirst()){
            notified= cursor.getInt(cursor.getColumnIndexOrThrow(Track.COLUMN_NAME_NOTIFIED))==1;
        }
        return notified;
    }

    public void deleteItem(String itemId){
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String selection = Track.COLUMN_NAME_ITEMID + " = ?";
        String[] selectionArgs = { itemId };
        db.delete(Track.TABLE_NAME, selection, selectionArgs);
        db.close();

    }
}

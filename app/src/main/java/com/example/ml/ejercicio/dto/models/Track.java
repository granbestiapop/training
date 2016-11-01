package com.example.ml.ejercicio.dto.models;

import android.provider.BaseColumns;

/**
 * Created by lclavijo on 11/1/16.
 */

public class Track implements BaseColumns{
    public static final String TABLE_NAME = "Track";
    public static final String COLUMN_NAME_ITEMID = "itemid";
    public static final String COLUMN_NAME_LAST_UPDATED = "last_updated";
    public static final String COLUMN_NAME_NOTIFIED = "notified";

    public String itemId;
    public String last_updated;
    public int notified;
}

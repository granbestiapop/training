package com.example.ml.ejercicio.dummy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final List<DummyItem> ITEMS_SEARCH = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    /*
    static{
        String[] strings= {"Nuevo","Motorola", "Test", "Cel", "Nuevo"};
        for(String s: strings){
            ITEMS_SEARCH.add(new DummyItem(s, s, "Descripcion"));
        }
    }*/

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void search(String term){
        ITEMS.clear();
        for(DummyItem d: ITEMS_SEARCH){
            Log.e("",d.content);
            if(d.content.contains(term)){
                ITEMS.add(d);
            }
        }
    }

    public static void clear(){
        ITEMS.clear();
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position), "");
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem implements Serializable {
        public final String id;
        public final String content;
        public final String details;
        public final String thumbnail;
        public Double price;
        public List<String> images= new ArrayList<>();

        public DummyItem(String id, String content, String details, String thumbnail) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.thumbnail= thumbnail;
        }

        @Override
        public String toString() {
            return content;
        }

        public static DummyItem fromJSON(JSONObject o){
            DummyItem i= null;
            try{
                String title= o.getString("title");
                Double price= o.getDouble("price");
                String id= o.getString("id");
                String thumb= o.getString("thumbnail");
                JSONArray images= o.getJSONArray("pictures");

                i= new DummyItem(id,title, "Descripcion HARDCODED", thumb);
                i.price= price;
                //dump images
                int size= images.length();
                for(int it=0; it<size;it++){
                    JSONObject image= images.getJSONObject(it);
                    i.images.add(image.getString("url"));
                }
            }catch (Exception e){

            }
            return i;
        }
    }
}

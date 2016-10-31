package com.example.ml.ejercicio.interfaces;

import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.ItemsInfo;

import java.util.List;

/**
 * Created by lclavijo on 10/26/16.
 */

public interface OnListFragmentInteractionListener {
    void onListFragmentInteraction(Item item);
    void setItems(ItemsInfo list);
}
package com.example.ml.ejercicio.interfaces;

import com.example.ml.ejercicio.dto.models.Item;
import com.example.ml.ejercicio.dto.models.ItemWrap;
import com.example.ml.ejercicio.dto.models.ItemsInfo;

import java.util.List;

/**
 * Created by lclavijo on 10/26/16.
 */

public interface OnListFragmentInteractionListener {
    void onListFragmentInteraction(ItemWrap itemWrap);
    void setItems(ItemsInfo list);
}
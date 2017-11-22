package com.example.android.householdroutine.utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olive on 22.11.2017.
 */

public class ConvertJsonArray {
    /**
     * Converts an arraylist into a json string
     * @param list
     * @return
     */
    public static String listToJsonArray(ArrayList<String> list) {
        JSONArray jsonItems = new JSONArray(list);
        return jsonItems.toString();
    }

    /**
     * Converts a jsonarray string into an arraylist
     * @param json
     * @return
     */
    public static List<String> jsonArrayToList(String json) {
        List<String> itemList = new ArrayList<String>();
        try {
            JSONArray jsonItems = new JSONArray(json);
            for (int i = 0; i < jsonItems.length(); i++) {
                itemList.add(jsonItems.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(itemList.size() > 0)
            return itemList;
        else
            return null;
    }
}

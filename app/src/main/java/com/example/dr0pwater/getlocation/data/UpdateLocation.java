package com.example.dr0pwater.getlocation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/18/16.
 */

public class UpdateLocation {
    public int id, customer;
    public String position="";
    UpdateLocation(){}
    public static JSONArray toJson(ArrayList<UpdateLocation> updateLocations) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < updateLocations.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("id", updateLocations.get(i).id);
                object.put("customer",updateLocations.get(i).customer);
                object.put("position",updateLocations.get(i).position);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }
}

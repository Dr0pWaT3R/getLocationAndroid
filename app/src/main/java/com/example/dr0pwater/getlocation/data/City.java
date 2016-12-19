package com.example.dr0pwater.getlocation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/17/16.
 */

public class City {
    public int id;
    public String name="";
    public City(){}
    public City(JSONObject object){
        JSONObject obj;
        try{
            id = object.getInt("pk");
            obj = object.getJSONObject("fields");
            name = obj.getString("name");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<City> fromJson (JSONArray jsonObject){
        ArrayList<City> cities = new ArrayList<>();
        for (int i=0; i<jsonObject.length(); i++){
            try {
                cities.add(new City(jsonObject.getJSONObject(i)));
            }catch (JSONException e ){
                e.printStackTrace();
            }
        }
        return cities;
    }
}

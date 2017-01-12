package com.example.dr0pwater.getlocation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/17/16.
 */

public class District {
    public int id, city;
    public String name="";
    public District(){}
    public District(JSONObject object){
        JSONObject obj;
        try{
            id = object.getInt("pk");
            obj = object.getJSONObject("fields");
            name = obj.getString("name");
            city = obj.getInt("aim");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<District> fromJson (JSONArray jsonObject){
        ArrayList<District> districts = new ArrayList<>();
        for (int i=0; i<jsonObject.length(); i++){
            try {
                districts.add(new District(jsonObject.getJSONObject(i)));
            }catch (JSONException e ){
                e.printStackTrace();
            }
        }
        return districts;
    }
}

package com.example.dr0pwater.getlocation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/16/16.
 */

public class Customerpoint {
    public int id;
    public String city, duureg, khoroo, name, position;
    public Customerpoint(){}
    public Customerpoint(JSONObject object){
        JSONObject obj;
        try{
            id = object.getInt("pk");
            obj = object.getJSONObject("fields");
            city = obj.getString("city");
            duureg = obj.getString("duureg");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Customerpoint> fromJson (JSONArray jsonObject){
        ArrayList<Customerpoint> customerpoints = new ArrayList<>();
        for (int i=0; i<jsonObject.length(); i++){
            try {
                customerpoints.add(new Customerpoint(jsonObject.getJSONObject(i)));
            }catch (JSONException e ){
                e.printStackTrace();
            }
        }
        return  customerpoints;
    }
}

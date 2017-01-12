package com.example.dr0pwater.getlocation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/17/16.
 */

public class Commission {
    public int id, district;
    public String name="", boundary="";
    Commission(){}
    public Commission(JSONObject object){
        JSONObject obj;
        try{
            id = object.getInt("pk");
            obj = object.getJSONObject("fields");
            name = obj.getString("name");
            boundary = obj.getString("boundary");
            district = obj.getInt("soum");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Commission> fromJson (JSONArray jsonObject){
        ArrayList<Commission> commissions = new ArrayList<>();
        for (int i=0; i<jsonObject.length(); i++){
            try {
                commissions.add(new Commission(jsonObject.getJSONObject(i)));
            }catch (JSONException e ){
                e.printStackTrace();
            }
        }
        return commissions;
    }
}

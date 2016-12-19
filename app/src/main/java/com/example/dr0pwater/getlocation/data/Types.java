package com.example.dr0pwater.getlocation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/17/16.
 */

public class Types {
    public int id;
    public String name="";
    public Types(){}
    public Types(JSONObject object){
        JSONObject obj;
        try{
            id = object.getInt("pk");
            obj = object.getJSONObject("fields");
            name = obj.getString("name");
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Types> fromJson (JSONArray jsonObject){
        ArrayList<Types> types = new ArrayList<>();
        for (int i=0; i<jsonObject.length(); i++){
            try {
                types.add(new Types(jsonObject.getJSONObject(i)));
            }catch (JSONException e ){
                e.printStackTrace();
            }
        }
        return types;
    }
}

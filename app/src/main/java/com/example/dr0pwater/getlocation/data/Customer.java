package com.example.dr0pwater.getlocation.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/16/16.
 */

public class Customer {
    public int id,city, duureg, khoroo, type, openif;
    public String name="", position="", phone="";
    public Customer(){}
    public Customer(JSONObject object){
        JSONObject obj;
        try{
            id = object.getInt("pk");
            obj = object.getJSONObject("fields");
            city = obj.getInt("city");
            duureg = obj.getInt("dvvreg");
            khoroo = obj.getInt("khoroo");
            name = obj.getString("name");
            position = obj.getString("position");
            phone = obj.getString("phone");
            type = obj.getInt("types");
            openif = obj.getInt("openif");
//            Log.d("pleaseType", "Customer: "+openif);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Customer> fromJson (JSONArray jsonObject){
        ArrayList<Customer> customers = new ArrayList<>();
        for (int i=0; i<jsonObject.length(); i++){
            try {
                customers.add(new Customer(jsonObject.getJSONObject(i)));
            }catch (JSONException e ){
                e.printStackTrace();
            }
        }
        return customers;
    }
}

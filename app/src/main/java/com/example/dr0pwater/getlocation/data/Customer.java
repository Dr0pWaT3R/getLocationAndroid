package com.example.dr0pwater.getlocation.data;

import android.util.Log;

import com.example.dr0pwater.getlocation.main.DataSendAndDownload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/16/16.
 */

public class Customer {
    public int id,city, duureg, khoroo, type=0, openif=0;
    public String name="", position="", phone="", outsideImage="", owner="",
            address="", obj_name="";
    public Customer(){}
    public Customer(JSONObject object){
        JSONObject obj;
        try{
            id = object.getInt("pk");
            obj = object.getJSONObject("fields");
            city = obj.getInt("aimag");
            duureg = obj.getInt("district");
            khoroo = obj.getInt("bag_khoroo");
            name = obj.getString("name").toLowerCase();
            owner = obj.getString("sponsor").toLowerCase();
            position = obj.getString("position");
            outsideImage = obj.getString("exitimg");
            openif = obj.getInt("openif");
            address = obj.getString("address");
            obj_name = obj.getString("obj_name");
            try {
                type = obj.getInt("types");
            }catch (Exception e ){}
            phone = obj.getString("phone");
//            Log.d("customer", "Customer: "+obj.getString("exitimg"));

        }catch (JSONException e){
            e.printStackTrace();
            Log.d("customer", "error: ");
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

    public static JSONArray toJson(ArrayList<Customer> customers) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < customers.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("id",customers.get(i).id);
                object.put("city",customers.get(i).city);
                object.put("district", customers.get(i).duureg);
                object.put("commission", customers.get(i).khoroo);
                object.put("type", customers.get(i).type);
                object.put("name", customers.get(i).name);
                object.put("phone", customers.get(i).phone);
                object.put("position", customers.get(i).position);
                object.put("owner", customers.get(i).owner);
                object.put("obj_name", customers.get(i).obj_name);
                object.put("address", customers.get(i).address);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }
}
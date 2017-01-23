package com.example.dr0pwater.getlocation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 1/14/17.
 */

public class CustomerUpdateInfo {
    public int id, customer, city, distric, commission, types, openif, ifsend;
    public String name="", owner="", phone="", position="", outsideImage="";
    public CustomerUpdateInfo(){}
    public static JSONArray toJson(ArrayList<CustomerUpdateInfo> customers) {
        JSONArray array = new JSONArray();
        for (int i = 0; i < customers.size(); i++) {
            JSONObject object = new JSONObject();
            try {
                object.put("customer",customers.get(i).customer);
                object.put("city",customers.get(i).city);
                object.put("district", customers.get(i).distric);
                object.put("commission", customers.get(i).commission);
                object.put("type", customers.get(i).types);
                object.put("name", customers.get(i).name);
                object.put("phone", customers.get(i).phone);
                object.put("position", customers.get(i).position);
                object.put("owner", customers.get(i).owner);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }
}

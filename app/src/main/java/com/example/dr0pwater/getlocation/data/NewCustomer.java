package com.example.dr0pwater.getlocation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 1/16/17.
 */

public class NewCustomer {
    public int id, customer, ifsend=0;
    public static String toString(ArrayList<NewCustomer> newCustomers) {
        String array = new String();
        if (newCustomers.size() > 0){
            for (int i = 0; i < newCustomers.size(); i++) {
                array += newCustomers.get(i).customer + ",";
            }
            array = array.substring(0, array.length()-1);
        }
        return array;
    }
}

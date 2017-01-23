package com.example.dr0pwater.getlocation.main;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import com.example.dr0pwater.getlocation.data.Customer;
import com.example.dr0pwater.getlocation.data.CustomerUpdateInfo;
import com.example.dr0pwater.getlocation.data.CustomerUpdateInfodb;
import com.example.dr0pwater.getlocation.data.Customerdb;
import com.example.dr0pwater.getlocation.data.Database;
import com.example.dr0pwater.getlocation.data.NewCustomer;
import com.example.dr0pwater.getlocation.data.NewCustomerdb;
import com.example.dr0pwater.getlocation.data.UpdateLocation;
import com.example.dr0pwater.getlocation.data.UpdateLocationdb;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by lol on 12/5/16.
 */

public class UploadModel {
    private AsyncHttpClient myClient;
    private Context context;
    private Database myDatabase;
    private UpdateLocationdb updateLocationdb;
    private NewCustomerdb newCustomerdb;
    private Customerdb customerdb;
    private CustomerUpdateInfodb customerUpdateInfodb;
    private ArrayList<CustomerUpdateInfo> customerUpdateInfo;
    private ArrayList<NewCustomer> newCustomer;

    public UploadModel(Context context) {
        this.context = context;
        myClient = new AsyncHttpClient();
        myDatabase = Database.getInstance(context);
        updateLocationdb = new UpdateLocationdb(myDatabase);
        newCustomerdb = new NewCustomerdb(myDatabase);
        customerdb = new Customerdb(myDatabase);
        customerUpdateInfodb = new CustomerUpdateInfodb(myDatabase);

        JSONObject object =new JSONObject();
        try {
//            Log.d("query", "UploadModel: " + customerdb.getallNewCustomer(NewCustomer.toString(newCustomerdb.getall())));
            object.put("updateLocation", UpdateLocation.toJson(updateLocationdb.getall()));
            object.put("newCustomer", Customer.toJson(customerdb.getallNewCustomer(NewCustomer.toString(newCustomerdb.getall()))));
            object.put("updateCustomer", CustomerUpdateInfo.toJson(customerUpdateInfodb.getall()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("xaxa", "UploadModel: "+object.toString());
        uploadRec(object);

//        FileuploadPart fileuploadPart = new FileuploadPart();
//        fileuploadPart.execute();
    }



    private  void uploadRec(JSONObject jsonObject) {
        myClient.addHeader("Cookie", "csrftoken=KJfiXjBEYjIFMvXG1ZLyefRY4s8BtAJu");
        myClient.addHeader("X-CSRFToken", "KJfiXjBEYjIFMvXG1ZLyefRY4s8BtAJu");


        StringEntity entity = null;
        entity = new StringEntity(jsonObject.toString(), HTTP.UTF_8);
//        myClient.post(context,Basic.HOST + "api/basic/", entity, "application/json", new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//
//            }
//        });
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        RequestParams requestParams = new RequestParams();

        Log.d("showdata", "UploadModel: "+jsonObject.toString()+"url:"+DataSendAndDownload.HOST+"api/location");
        requestParams.add("jsondata",jsonObject.toString());

        myClient.post(context, DataSendAndDownload.HOST+"api/location/", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String responseMini = new String(responseBody);
                Log.d("upload", "onSuccess: "+responseMini);
                if (responseMini.equals("ok")){
                    customerUpdateInfo = customerUpdateInfodb.getall();
                    customerUpdateInfodb.UpdateIsSend(customerUpdateInfo);
                    newCustomer = newCustomerdb.getall();
                    newCustomerdb.UpdateIsSend(newCustomer);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("showdata", "uploadError: "+"url:"+DataSendAndDownload.HOST+"api/location");
            }
        });
    }
}

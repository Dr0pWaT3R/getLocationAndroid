package com.example.dr0pwater.getlocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dr0pwater.getlocation.data.City;
import com.example.dr0pwater.getlocation.data.Citydb;
import com.example.dr0pwater.getlocation.data.Commission;
import com.example.dr0pwater.getlocation.data.Commissiondb;
import com.example.dr0pwater.getlocation.data.Customer;
import com.example.dr0pwater.getlocation.data.Customerdb;
import com.example.dr0pwater.getlocation.data.Database;
import com.example.dr0pwater.getlocation.data.District;
import com.example.dr0pwater.getlocation.data.Districtdb;
import com.example.dr0pwater.getlocation.data.Types;
import com.example.dr0pwater.getlocation.data.Typesdb;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DataSendAndDownload extends Activity {

    final public static String HOST = "http://sales.smarts.mn/";
    private Database database;
    private Customerdb customerdb;
    private Citydb citydb;
    private Districtdb districtdb;
    private Commissiondb commissiondb;
    private Typesdb typesdb;
    private Button sendDataBtn, sendDataActiveBtn, getCustomerBtn;
    private boolean dataDownload = true;
    private int btnClickCount=0;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        pref = getSharedPreferences("download.info", Context.MODE_PRIVATE);
        // Log.d("SHaredPreferences", pref.getString("username", ""));
        // Log.d("SHaredPreferences", pref.getString("password", ""));
        database = new Database(this);
        customerdb = new Customerdb(database);
        citydb = new Citydb(database);
        districtdb = new Districtdb(database);
        commissiondb = new Commissiondb(database);
        typesdb = new Typesdb(database);
        getCustomerBtn = (Button) findViewById(R.id.getCustomer_btn_id);
        sendDataBtn = (Button)findViewById(R.id.sendData_btn_id);
        sendDataActiveBtn = (Button)findViewById(R.id.sendData_btn_activeChange_id);
        if(pref.getBoolean("download",false)){
            getCustomerBtn.setEnabled(false);
        }else{getCustomerBtn.setEnabled(true);}


        sendDataActiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClickCount ++;
                Log.d("showdata", ":customerdbSize-> "+ customerdb.getall().size());
                Toast.makeText(getApplicationContext(), "Нийт харилцагч: "+customerdb.getall().size(), Toast.LENGTH_SHORT).show();
                if(btnClickCount == 10){
                    Toast.makeText(getApplicationContext(),"Мэдээлэл татах боломжтой", Toast.LENGTH_SHORT).show();
                    pref = getSharedPreferences("download.info", Context.MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putBoolean("download", false);
                    editor.apply();
                    getCustomerBtn.setEnabled(true);
                }
            }
        });
        sendDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadModel uploadModel=new UploadModel(getApplicationContext());
            }
        });
        getCustomerBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                pref = getSharedPreferences("download.info", Context.MODE_PRIVATE);
                editor = pref.edit();
                editor.putBoolean("download", true);
                editor.apply();
                getCustomer();
                getCustomerBtn.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Өгөгдөл татаж байна", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void getCustomer(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HOST+"api/position/", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.d("customer", "onStart"+HOST+"api/position/");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String customerts = new String(response);
                Log.d("customer", "xaxa"+customerts);
                if(!customerts.contentEquals("{}")){
                    JSONObject obj = null;
                    Log.d("customer", "bna"+customerts);
                    try {
                        obj = new JSONObject(customerts);
                        Log.d("customer", "bna"+obj);
                        customerdb.create( Customer.fromJson(obj.getJSONArray("position")));
                        citydb.create(City.fromJson(obj.getJSONArray("city")));
                        districtdb.create(District.fromJson(obj.getJSONArray("dvvreg")));
                        commissiondb.create(Commission.fromJson(obj.getJSONArray("khoroo")));
                        typesdb.create(Types.fromJson(obj.getJSONArray("types")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.d("customer", "customer alge bn: ");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
}

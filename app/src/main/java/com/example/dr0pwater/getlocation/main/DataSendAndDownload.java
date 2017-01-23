package com.example.dr0pwater.getlocation.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dr0pwater.getlocation.R;
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
import com.example.dr0pwater.getlocation.data.UpdateLocationdb;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DataSendAndDownload extends Activity {

    final public static String HOST = "http://sales.smarts.mn/";
    private Database database;
    private Customerdb customerdb;
    private UpdateLocationdb updateLocationdb;
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
        updateLocationdb = new UpdateLocationdb(database);
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
                Log.d("showdata", ":updateLocationdbSize-> "+ updateLocationdb.getall().size());
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
                if(isOnline()){
                    UploadModel uploadModel=new UploadModel(getApplicationContext());
                    Toast.makeText(getApplicationContext(),"Мэдээлэл илгээлээ БАЯРЛАЛАА :D", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getApplicationContext(), "Интернет холболтоо шалгана уу?", Toast.LENGTH_LONG).show();

            }
        });
        getCustomerBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(isOnline()){
                    pref = getSharedPreferences("download.info", Context.MODE_PRIVATE);
                    editor = pref.edit();
                    editor.putBoolean("download", true);
                    editor.apply();
                    getCustomer("api/position1/");
                    getCustomer("api/position2/");
                    getCustomer("api/position3/");
                    getCustomerBtn.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "Өгөгдөл татаж байна", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getApplicationContext(), "Интернет холболтоо шалгана уу?", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    int complateCount=0;
    private void getCustomer(final String positionUrl){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HOST+positionUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.d("customer", "onStart"+HOST+positionUrl);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String customerts = new String(response);
                if(!customerts.contentEquals("{}")){
                    JSONObject obj = null;
                    Log.d("customer", "bna"+customerts);
                    try {
                        obj = new JSONObject(customerts);
                        if (positionUrl.endsWith("position2/") || positionUrl.endsWith("position3/")){
                            Log.d("customer", "positionUrl->: "+positionUrl);
                            customerdb.create( Customer.fromJson(obj.getJSONArray("position")));
                        }else {
                            Log.d("customer", "positionUrl->: "+positionUrl);
                            customerdb.create( Customer.fromJson(obj.getJSONArray("position")));
                            citydb.create(City.fromJson(obj.getJSONArray("city")));
                            districtdb.create(District.fromJson(obj.getJSONArray("dvvreg")));
                            commissiondb.create(Commission.fromJson(obj.getJSONArray("khoroo")));
                            typesdb.create(Types.fromJson(obj.getJSONArray("types")));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.d("customer", "customer alge bn: ");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplication(), "Мэдээлэл татахад алдаа гарлаа", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                // Progress notification
            }

            @Override
            public void onFinish() {
                // Completed the request (either success or failure)
                Log.d("customer", "onFinish: ");
                complateCount++;
                if (complateCount==3)
                    asd();
            }
        });
    }

    int nextImg=0;
    AsyncHttpClient client = new AsyncHttpClient();
    public void asd(){
        ArrayList<Customer> customerOutsideImage = customerdb.getCustomerOutsideImage();
        Log.d("downloadImg", "cusImageSize->: "+customerOutsideImage.size());
        for (int i=0; i<10; i++)
        {
            if (customerOutsideImage.get(i).outsideImage != "")
                lol(customerOutsideImage.get(i).outsideImage);
        }

    }
    public void lol(final String lolUrl){
        complateCount=0;
        client.get("http://sales.smarts.mn/media/"+lolUrl, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File response) {
                // Do something with the file `response`

                Log.d("downloadImg", "onSuccess: "+"http://sales.smarts.mn/media/"+lolUrl+"->"+response);
                File filePath = Environment.getExternalStorageDirectory();
                File directoty = new File(filePath.getAbsolutePath() + "/Save Image Example");
                directoty.mkdirs();
                File image = response;
                File file = new File(directoty, String.valueOf(image));
            }
            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                // Progress notification
            }

            @Override
            public void onFinish() {
                // Completed the request (either success or failure)
                Log.d("customer", "onFinish: ");
                complateCount++;
                if (complateCount==10)
                    nextImg++;
            }
        });
    }
}
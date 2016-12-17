package com.example.dr0pwater.getlocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dr0pwater.getlocation.data.Customerpoint;
import com.example.dr0pwater.getlocation.data.Customerpointdb;
import com.example.dr0pwater.getlocation.data.Database;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity implements LocationListener {
    final public static String HOST = "http://192.168.1.19:8000/";

    private LocationManager locationManager;
    private boolean providerldisabled = true,burteg_GPS = true, burteg_NETWORK = true;
    private Button getLocationBtn, showdataBtn;
    private TextView textView;
    private Database database;
    private Customerpointdb customerpointdb;
    private ArrayList<Customerpoint> customerpoint;

    public void getCity() {
        customerpoint = customerpointdb.getCity();
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinner_city);
        String[] items = new String[customerpoint.size()];

        if(customerpoint.size()>0){
            Log.d("log", "cuspoint: "+customerpoint.get(0).city);
            for(int i=0; i<customerpoint.size(); i++){
                items[i] = customerpoint.get(i).city;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner.setAdapter(adapter);


        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }
    public void getDuureg() {
        customerpoint = customerpointdb.getDuureg();
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinner_duureg);
        String[] items = new String[customerpoint.size()];

        if(customerpoint.size()>0){
            Log.d("log", "cuspoint: "+customerpoint.get(0).duureg);
            for(int i=0; i<customerpoint.size(); i++){
                items[i] = customerpoint.get(i).duureg;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner.setAdapter(adapter);


        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocationBtn = (Button) findViewById(R.id.getLocation_btn_id);
        showdataBtn = (Button) findViewById(R.id.showdata_btn_id);
        textView = (TextView) findViewById(R.id.setLocation_txtView_id);
        database = new Database(this);
        customerpointdb = new Customerpointdb(database);
        getCity();
        getDuureg();
//        customerpointdb.insertCustomerpoint("ub","sukhbaatar","3r khoroo","delguur3","0.00");
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("latitude: "+getLocation().getLatitude()+" longtitude: "+ getLocation().getLongitude());
            }
        });

        showdataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("showdata", ":customerpointSize- "+customerpointdb.getall().size());
            }
        });

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                2000,
//                10, this);

        Spinner staticSpinner = (Spinner) findViewById(R.id.static_spinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);
        getCustomerpoint();
        String jsonStr = "{\"customerpoint\":[{\"id\": \"1\", \"city\": \"ub\", \"duureg\": \"bayangol\", \"khoroo\": \"2r khoroo\", \"name\": \"delguur\", \"position\": \"0\"}," +
                "{\"id\": \"2\", \"city\": \"ub\", \"duureg\": \"bayanzurkh\", \"khoroo\": \"4r khoroo\", \"name\": \"delguur1\", \"position\": \"0\"}," +
                "{\"id\": \"3\", \"city\": \"ub\", \"duureg\": \"sukhbaatar\", \"khoroo\": \"3r khoroo\", \"name\": \"delguur2\", \"position\": \"0\"}]}";


    }

    private void getCustomerpoint(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HOST+"api/getcus/", new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

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

    @Override
    public void onLocationChanged(Location location) {
        String msg = "New Latitude: "+location.getLatitude()+"New Longitude: "+location.getLongitude();
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    static Location mLastLocation = new Location("");

    public Location getLocation() {
        boolean isNetworkEnabled = false, isGPSEnabled = false;

        try {
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!isOnline())
            {
                isNetworkEnabled=false;
            }
            Log.d("gps", "+loop" +isGPSEnabled +" "+isNetworkEnabled);
            if (isGPSEnabled  || isNetworkEnabled) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    providerldisabled = false;


                    if (isGPSEnabled && burteg_GPS) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
                        mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (mLastLocation != null)
                            Toast.makeText(this,
                                    "" + mLastLocation.getLatitude(),
                                    Toast.LENGTH_LONG).show();
                        burteg_GPS = false;
                    }
                    if (isNetworkEnabled && burteg_NETWORK) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, this);
                        mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (mLastLocation != null)
                            Toast.makeText(this,
                                    "" + mLastLocation.getLatitude(),
                                    Toast.LENGTH_LONG).show();
                        burteg_NETWORK = false;
                    }

                }
                else{
                    showdialog();
                }
            } else {
                showdialog();
            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.d("gps", "+exception" );
        }

        return mLastLocation;
    }
    private void showdialog(){
        Log.d("gps", "dialog        " );
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS тохиогоо");

        // Setting Dialog Message
        alertDialog.setMessage("GPS идэвхжээгүй байна Заавал идэвхжүүлнэ үү");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("идэвхжүүлэх", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                MainActivity.this.startActivityForResult(intent, 921);

            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("буцах", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                MainActivity.this.finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}

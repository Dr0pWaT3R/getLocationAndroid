package com.example.dr0pwater.getlocation.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dr0pwater.getlocation.R;
import com.example.dr0pwater.getlocation.data.Citydb;
import com.example.dr0pwater.getlocation.data.Commission;
import com.example.dr0pwater.getlocation.data.Commissiondb;
import com.example.dr0pwater.getlocation.data.Customer;
import com.example.dr0pwater.getlocation.data.Customerdb;
import com.example.dr0pwater.getlocation.data.Database;
import com.example.dr0pwater.getlocation.data.Districtdb;
import com.example.dr0pwater.getlocation.data.Types;
import com.example.dr0pwater.getlocation.data.Typesdb;
import com.example.dr0pwater.getlocation.data.UpdateLocationdb;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class MainActivity extends Activity implements LocationListener {
    final public static String HOST  = "http://192.168.1.23:8000/";
    private Typeface fontawesome;
    private LocationManager locationManager;
    private boolean providerldisabled = true,burteg_GPS = true, burteg_NETWORK = true;
    private Button getLocationBtn, showdataBtn;
    private TableLayout table;
    private TextView textView;
    private Database database;
    private Customerdb customerdb;
    private Citydb citydb;
    private Districtdb districtdb;
    private Commissiondb commissiondb;
    private Typesdb typesdb;
    private UpdateLocationdb updateLocationdb;
    private ArrayList<Customer> customer;
    private ArrayList<Commission> commission;
    private ArrayList<Types> types;
    private ArrayList<Customer> customerInfo;
    private boolean districtChanged = false, commissionChanged = false;
    private String changedDistrict="", changedCommission="", changeTypes="";
    private int districtId=0, commissionId=0, typesId=0;
    Spinner dynamicSpinner_commission, dynamicSpinner_types;
    ListView lv;
    ArrayAdapter<Customer> adapter;

    public void getCity() {
        customer = customerdb.getCity();
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinner_city);
        String[] items = new String[customer.size()];

        if(customer.size()>0){
            Log.d("log", "cuspoint: "+ customer.get(0).city);
            for(int i = 0; i< customer.size(); i++){
                if(customer.get(i).city == 1)
                    items[i] = "Улаанбаатар";
                else
                    items[i] = "Хот сонгох";
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
        customer = customerdb.getDuureg();
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinner_duureg);
        String[] items = new String[customer.size()];

        if(customer.size()>0){
            Log.d("duureg", "cuspoint: "+ customer.get(0).duureg);
            for(int i = 0; i< customer.size(); i++){
//                String districtName = districtdb.getDistrictName(customer.get(i).duureg);
                if (districtdb.getDistrictName(customer.get(i).duureg) != "")
                    items[i] = districtdb.getDistrictName(customer.get(i).duureg);
                else
                    items[i] = "Хороо сонгох";
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
                changedDistrict = String.valueOf(parent.getItemAtPosition(position));
                districtId = districtdb.getDistrictId(changedDistrict);
                table.removeAllViews();
                getKhoroo();
//                    Toast.makeText(getApplicationContext(), "duureg id: "+districtId, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void getKhoroo(){
        commission = commissiondb.getCommission(districtId);
        dynamicSpinner_commission = (Spinner) findViewById(R.id.dynamic_spinner_khoroo);
//        dynamicSpinner_commission.setEnabled(false);
        final String[] commissionItems = new String[commission.size()];

        if(commission.size()>0){
            Log.d("duureg", "commission: "+ commission.size());
            for(int i = 0; i< commission.size(); i++){
                commissionItems[i] = commission.get(i).name;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, commissionItems);

        dynamicSpinner_commission.setAdapter(adapter);

        dynamicSpinner_commission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));

                changedCommission = (String)parent.getItemAtPosition(position);
                Log.d("query", "onItemSelected: "+changedCommission);
                for(int i=0; i<commission.size(); i++){
                    if(commission.get(i).name == changedCommission) {
                        commissionId = commission.get(i).id;
                        Log.d("query", "getCommissionID: " + commission.get(i).id+"commissionId"+commissionId);
                    }
                }
                customerInfo = customerdb.getCustomerInfo(districtId,commissionId);
//                Toast.makeText(getApplicationContext(),""+districtId+ "commis:"+ commissionId+"cusInfoSize:"+customerInfo.size(),Toast.LENGTH_LONG).show();
                table.removeAllViews();
                CreateTable();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void getTypes(){
        types = typesdb.getall();
        dynamicSpinner_types = (Spinner) findViewById(R.id.dynamic_spinner_types);
        String[] items = new String[types.size()+1];

        if(types.size()>0){
            for(int i = 0; i< types.size(); i++){
                items[0] = "Төрөл сонгох";
                items[i+1] = types.get(i).name;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner_types.setAdapter(adapter);

        dynamicSpinner_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                Log.v("item", (String) parent.getItemAtPosition(position));
                changeTypes = (String)parent.getItemAtPosition(position);
                for(int i=0; i<types.size(); i++){
                    if(types.get(i).name == changeTypes){
                        typesId = types.get(i).id;
                    }
                }
                customerInfo = customerdb.getCustomerInfoWithType(districtId,commissionId, typesId);
                Log.d("query", "districtId:->" + districtId+"commissionId->"+commissionId+"typesId->"+typesId);
                table.removeAllViews();
                CreateTable();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void update(){
        getCity();
        getDuureg();
        getKhoroo();
        getTypes();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocationBtn = (Button) findViewById(R.id.getLocation_btn_id);
        showdataBtn = (Button) findViewById(R.id.showdata_btn_id);
        getLocation();

        textView = (TextView) findViewById(R.id.setLocation_txtView_id);
        database = new Database(this);
        customerdb = new Customerdb(database);
        citydb = new Citydb(database);
        districtdb = new Districtdb(database);
        commissiondb = new Commissiondb(database);
        typesdb = new Typesdb(database);
        updateLocationdb = new UpdateLocationdb(database);
        table = (TableLayout)this.findViewById(R.id.table_customer);
        fontawesome =  Typeface.createFromAsset(this.getAssets(), "fontawesome-webfont.ttf");
        update();
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("latitude: "+getLocation().getLatitude()+" longtitude: "+ getLocation().getLongitude());
            }
        });

        final EditText searchTxt = (EditText)findViewById(R.id.search_editText_id);
        searchTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customerInfo=customerdb.searchAny(districtId, commissionId, searchTxt.getText());
                CreateTable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        adapter = new ArrayAdapter<Customer>(
//                MainActivity.this,
//                android.R.layout.simple_list_item_1,
//                customerInfo);
//        lv.setAdapter(adapter);
//        SearchView srchText = (SearchView) findViewById(R.id.search_searchView_id);
//
//        final Adapter adapter = new Adapter(this, customerInfo);
//        lv.setAdapter(adapter);
//        srchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });


        showdataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DataSendAndDownload.class);
                startActivity(intent);
//                Log.d("showdata", ":customerdbSize-> "+ customerdb.getall().size());
//                Log.d("showdata", ":citydbSize-> "+ citydb.getall().size());
//                Log.d("showdata", ":districtdbSize:-> "+ districtdb.getall().size());
//                Log.d("showdata", ":commissiondbSize-> "+ commissiondb.getall().size());
//                Log.d("showdata", ":typesdbSize-> "+ typesdb.getall().size());
//                Log.d("showdata", ":updateLocationdbSize-> "+ updateLocationdb.getall().size());
            }
        });

    }


    @Override
    public void onLocationChanged(Location location) {
        String msg = "New Latitude: "+location.getLatitude()+"New Longitude: "+location.getLongitude();
//        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
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

    private void  CreateTable(){
        table.removeAllViews();
        int row = customerInfo.size();
        int col = 3;
        for(int i=0;i<=row;i++) {
            TableRow row1 = (TableRow) getLayoutInflater().inflate(R.layout.table_row_weighted,null);

            for(int j=0;j<col;j++) {
                TextView t1=(TextView) getLayoutInflater().inflate(R.layout.textviewweighted,row1,false);

                t1.setTextColor(Color.WHITE);

                if(i==0) {
                    if(j==0 )
                        t1.setText("Харилцагчийн нэр");
                    if(j==1 )
                        t1.setText("Цэгийн мэдээлэл");
                    else if(j==2)
                        t1.setText("Судалгаа авах эсэх");
//                    t1.setTextColor(Color.BLACK);
//                    t1.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.answerAlpha));
                    t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                }
                else{
                    if(j==0){
                        t1.setText(""+ StringUtils.capitalize(customerInfo.get(i-1).name));
                    }
                    else if(j==1 ) {
                        if (customerInfo.get(i - 1).position.equals("0,0")) {
                            t1.setText(customerInfo.get(i - 1).position);
                            t1.setBackgroundColor(Color.RED);
                            Log.d("position", "CreateTable: " + customerInfo.get(i - 1).position);
                        }else{
                            t1.setText(customerInfo.get(i - 1).position);
                        }
                    }
                    else if(j==2) {
                        t1.setTypeface(fontawesome);
//                        t1.setText(""+customerInfo.get(i-1).openif);
//                            Log.d("position", "CreateTable: openIf->"+customerInfo.get(i-1).openif);
                        if (customerInfo.get(i - 1).openif == 2)
                            t1.setText(R.string.mytrue);
                        else
                            t1.setText(R.string.myfalse);
                    }

                    t1.setGravity(Gravity.CENTER);
                    t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                }
                t1.setBackgroundResource(R.drawable.border1);
                if(i>0)
                    row1.setOnClickListener(rowclick);
                row1.addView(t1,t1.getLayoutParams());
                row1.setTag(i);
            }
            table.addView(row1 );

        }
    }

    private View.OnClickListener rowclick =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int i = (int)view.getTag();
            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
            final View mView = getLayoutInflater().inflate(R.layout.model, null);
            alertDialogBuilder.setView(mView);
            final android.app.AlertDialog dialogAsk = alertDialogBuilder.create();
            dialogAsk.show();
            TextView customerName = (TextView)mView.findViewById(R.id.customer_name_id);
            customerName.setText(customerInfo.get(i-1).name);
            Button updateBtn = (Button)mView.findViewById(R.id.update_btn_id);
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), Activity_update.class);
                    intent.putExtra("customer",customerInfo.get(i-1).id);
                    startActivity(intent);
                    dialogAsk.dismiss();
                }
            });
            Button locationGetBtn = (Button)mView.findViewById(R.id.locationGet_btn_id);
            locationGetBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerInfo.get(i-1).position.equals("0,0") || customerInfo.get(i-1).position.equals("0.0,0.0")) {

                        if(getLocation() != null){
                            String latLong = getLocation().getLatitude()+ "," + getLocation().getLongitude();
                            Log.d("sdaaa", "onClick: "+customerInfo.get(i - 1).id+"latlong:"+latLong);
                            updateLocationdb.insertPosition(customerInfo.get(i - 1).id,latLong);
                            customerdb.updatePosition(customerInfo.get(i - 1).id, latLong);
                            customerInfo = customerdb.getCustomerInfo(districtId,commissionId);
                            CreateTable();
                            Log.d("showdata", ":updateLocationdbSize-> " + updateLocationdb.getall().size());
                            Toast.makeText(getApplicationContext(), "Цэгийн мэдээлэл авлаа" + latLong, Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "Цэг авах боломжгүй байна. Дахин оролдоно уу?", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Цэгийн мэдээлэл авсан байна",Toast.LENGTH_LONG).show();

                    dialogAsk.dismiss();
                }
            });
        }
    };

}

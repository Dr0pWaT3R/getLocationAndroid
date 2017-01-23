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
import android.media.Image;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;

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
    private ArrayList<Customer> customerInfo;

    private String changedCity="", changedDistrict="", changedCommission="", changeTypes="";
    ListView lv;
    ArrayAdapter<Customer> adapter;

    private ArrayList<City> city;
    private int city_id;
    private ArrayList<District> district;
    private int district_id;
    private ArrayList<Commission> commission;
    private int commission_id;
    private ArrayList<Types> types;
    private int types_id;

    Spinner dynamicSpinner_city, dynamicSpinner_district,dynamicSpinner_commission, dynamicSpinner_types;

    public void getCity() {
        city = citydb.getall();
        dynamicSpinner_city = (Spinner) findViewById(R.id.dynamic_spinner_city);
        String[] items = new String[city.size()];

        if(city.size()>0){
            for(int i = 0; i< city.size(); i++){
                items[i] = city.get(i).name;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner_city.setAdapter(adapter);

        dynamicSpinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                changedCity = String.valueOf(parent.getItemAtPosition(position));
                for(int i=0; i<city.size(); i++){
                    if(city.get(i).name.equals(changedCity) ) {
                        city_id = city.get(i).id;
                    }
                }
//                getDuureg();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }
    public void getDuureg() {
        district = districtdb.getDistrict(city_id);
        dynamicSpinner_district = (Spinner) findViewById(R.id.dynamic_spinner_duureg);
        String[] items = new String[district.size()];

        if(district.size()>0){
            for(int i = 0; i< district.size(); i++){
                items[i] = district.get(i).name;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner_district.setAdapter(adapter);

        int setPosition=0;
        for(int i=0; i<district.size(); i++){
            if(district.get(i).id == district_id ) {
                setPosition = adapter.getPosition(district.get(i).name);
            }
        }
        dynamicSpinner_commission.setSelection(setPosition);

        dynamicSpinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
//                    Toast.makeText(getApplicationContext(), "duureg id: "+districtId, Toast.LENGTH_LONG).show();
                changedDistrict = (String)parent.getItemAtPosition(position);
                Log.d("query", "onItemSelected: "+changedDistrict);
//                district_id=districtdb.getDistrictId(changedDistrict);
                for(int i=0; i<district.size(); i++){
                    if(district.get(i).name.equals(changedDistrict) ) {
                        district_id = district.get(i).id;
                    }
                }
//                getKhoroo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void getKhoroo(int currentCommission){
        commission = commissiondb.getCommission(district_id);
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

        int setPosition=0;
        for(int i=0; i<commission.size(); i++){
            if(commission.get(i).id == currentCommission ) {
                setPosition = adapter.getPosition(commission.get(i).name);
                Log.d("lol", "getKhoroo: "+commission.get(i).id+"<-curr->"+currentCommission+"->"+district_id);
            }
        }
        Log.d("lol", "getKhoroo: setPosition-> "+setPosition);
        dynamicSpinner_commission.setSelection(setPosition);

        dynamicSpinner_commission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                getDuureg();
//                changedCommission = (String)parent.getItemAtPosition(position);
//                Log.d("query", "onItemSelected: "+changedCommission);
//                for(int i=0; i<commission.size(); i++){
//                    if(commission.get(i).name.equals(changedCommission) ) {
//                        commission_id = commission.get(i).id;
//                        Log.d("query", "getCommissionID: " + commission.get(i).id+"commissionId"+commission_id);
//                    }
//                }
                customerInfo = customerdb.getCustomerInfo(district_id,commission_id);
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
        String[] items = new String[types.size()];

        if(types.size()>0){
            for(int i = 0; i< types.size(); i++){
                items[i] = types.get(i).name;
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
                        types_id = types.get(i).id;
                    }
                }
                customerInfo = customerdb.getCustomerInfoWithType(district_id,commission_id, types_id);
                Log.d("query", "districtId:->" + district_id+"commissionId->"+commission_id+"typesId->"+types_id);
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
        getTypes();
        if (CheckBoundaryInside() != 0){
            district_id = commissiondb.getDistrictId(CheckBoundaryInside());
            commission_id = CheckBoundaryInside();
            getKhoroo(CheckBoundaryInside());
        }
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
                customerInfo=customerdb.searchAny(district_id, commission_id, searchTxt.getText());
                CreateTable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ImageButton addCustomer = (ImageButton)findViewById(R.id.customer_add_id);
        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterCustomer.class);
                startActivity(intent);
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
                            customerInfo = customerdb.getCustomerInfo(district_id,commission_id);
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

    private ArrayAdapter<String> sumAdapter,bagadapter,cityadapter;
    public static String AIMAG_CITY_CODE="", SUM_DUUREG_CODE="";
    public static int BAG_HOROO_CODE=0;
    int pos1=-1,pos2=-1,pos3=-1;
    public static double PI = 3.14159265;
    public static double TWOPI = 2*PI;

    private void toirog_aimag(){
        dynamicSpinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AIMAG_CITY_CODE = String.valueOf(city.get(position).id);
                pos1 = position;
                district = districtdb.getDistrict(Integer.parseInt(AIMAG_CITY_CODE));
                if(district.size() > 0 )
                    for (int i=0;i<district.size();i++) {
                        boolean del = true;
                        for(int j = 0 ; j < district.size();j++) {
                            if(district.get(j).id != district.get(i).id)
                                del = false;
                        }
                        if (del == true) {
                            district.remove(i);
                            i = i - 1;
                        }
                    }
                ArrayList<String> temp = new ArrayList<>();

                for (int i = 0; i < district.size(); i++) {
                    temp.add(district.get(i).name);
                }

                dynamicSpinner_city.setSelection(pos2);

                toirog_sum();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void toirog_sum(){
        dynamicSpinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (district == null) return;
                pos2 = position;
                SUM_DUUREG_CODE = String.valueOf(district.get(position).id);
                district = districtdb.getDistrict(Integer.parseInt(SUM_DUUREG_CODE));
                if(commission.size() > 0 )
                    for (int i=0;i<commission.size();i++)
                    {
                        boolean del = true;
                        if(commission.size() != 0) {
                            for (int j = 0; j < commission.size(); j++) {
                                if (commission.get(j).id != commission.get(i).id)
                                    del = false;

                            }
                            if (del == true) {
                                commission.remove(i);
                                i = i - 1;
                            }
                        }
                    }
                if(commission.size()==0){
                    commission = commissiondb.getCommission(Integer.parseInt(SUM_DUUREG_CODE));
                }

                ArrayList<String> temp = new ArrayList<>();
                for (int i = 0; i < commission.size(); i++) {
                    temp.add(commission.get(i).name);
                }

                if(pos3!=-1 && pos3 < temp.size())
                    dynamicSpinner_district.setSelection(pos3);

                toirog_khoro();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void toirog_khoro(){
        dynamicSpinner_commission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (commission == null) return;
                pos3 = position;
                if(BAG_HOROO_CODE != commission.get(position).id) {
                    BAG_HOROO_CODE = commission.get(position).id;
//                    if (MainActivity.fragmentArrayList != null)
//                        for (int i = 0; i < MainActivity.fragmentArrayList.size(); i++) {
//                            ((InterfacePage)MainActivity.fragmentArrayList.get(i)).refresh();
//                        }
                }

                ArrayList<Double> lat_array = new ArrayList<Double>();
                ArrayList<Double> long_array = new ArrayList<Double>();

                try {
                    JSONArray array = new JSONArray( commission.get(position).boundary);
                    for (int i = 0 ; i < array.length() ; i++ ){
                        JSONArray la = array.getJSONArray(i);
                        if(la.length() == 2){
                            lat_array.add(la.getDouble(0));
                            long_array.add(la.getDouble(1));
                        }
                    }
                    if(MainActivity.mLastLocation==null){

                    }
                    else if(MainActivity.mLastLocation.getLatitude()==0){

                    }
                    else{
                        boolean ret =  coordinate_is_inside_polygon(MainActivity.mLastLocation.getLatitude(),MainActivity.mLastLocation.getLongitude(),lat_array,long_array);
                        Toast.makeText(getApplicationContext(),""+ret,Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                ArrayList<LocationJson> b = locationdb.getsearch(BAG_HOROO_CODE);
//                ArrayList<String> temp = new ArrayList<String>();
//                for (LocationJson a : b) {
//                    temp.add(a.gudam);
//                }
//
////                spinnerbair.setAdapter(new ArrayAdapter<String>(MainActivity.this,
////                        android.R.layout.simple_dropdown_item_1line, temp));
//                ArrayAdapter<String> tempA = new ArrayAdapter<String>(LocationQ.this.getActivity(),
//                        R.layout.spinneritem, temp);
//                tempA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                gudamspinner.setAdapter(tempA);
//                if(temp.size() == 0)
//                {
//                    tempA = new ArrayAdapter<String>(LocationQ.this.getActivity(),
//                            R.layout.spinneritem, temp);
//                    tempA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    bairspinner.setAdapter(tempA);
//                    tempA = new ArrayAdapter<String>(LocationQ.this.getActivity(),
//                            R.layout.spinneritem, temp);
//                    tempA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    tootspinner.setAdapter(tempA);
//                    locationid ="";
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public static boolean coordinate_is_inside_polygon(
            double latitude, double longitude,
            ArrayList<Double> lat_array, ArrayList<Double> long_array)
    {
        int i;
        double angle=0;
        double point1_lat;
        double point1_long;
        double point2_lat;
        double point2_long;
        int n = lat_array.size();

        for (i=0;i<n;i++) {
            point1_lat = lat_array.get(i) - latitude;
            point1_long = long_array.get(i) - longitude;
            point2_lat = lat_array.get((i+1)%n) - latitude;
            //you should have paid more attention in high school geometry.
            point2_long = long_array.get((i+1)%n) - longitude;
            angle += Angle2D(point1_lat,point1_long,point2_lat,point2_long);
        }

        if (Math.abs(angle) < PI)
            return false;
        else
            return true;
    }

    public static double Angle2D(double y1, double x1, double y2, double x2)
    {
        double dtheta,theta1,theta2;

        theta1 = Math.atan2(y1,x1);
        theta2 = Math.atan2(y2,x2);
        dtheta = theta2 - theta1;
        while (dtheta > PI)
            dtheta -= TWOPI;
        while (dtheta < -PI)
            dtheta += TWOPI;

        return(dtheta);
    }

    public int CheckBoundaryInside(){
        ArrayList<Commission> commissionArrayList = commissiondb.getall();
        for (int i=0; i<commissionArrayList.size(); i++){
            ArrayList<Double> lat_array = new ArrayList<Double>();
            ArrayList<Double> long_array = new ArrayList<Double>();

            try {
                JSONArray array = new JSONArray( commissionArrayList.get(i).boundary);
                for (int j = 0 ; j < array.length() ; j++ ){
                    JSONArray la = array.getJSONArray(j);
                    if(la.length() == 2){
                        lat_array.add(la.getDouble(0));
                        long_array.add(la.getDouble(1));
                    }
                }
                if(MainActivity.mLastLocation==null){

                }
                else if(MainActivity.mLastLocation.getLatitude()==0){

                }
                else{
                    boolean ret =  coordinate_is_inside_polygon(MainActivity.mLastLocation.getLatitude(),MainActivity.mLastLocation.getLongitude(),lat_array,long_array);
                    if (ret){
                        Toast.makeText(getApplicationContext(),""+ret+" <-commission->"+commissionArrayList.get(i).id,Toast.LENGTH_LONG).show();
                        return commissionArrayList.get(i).id;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  0;
    }

}

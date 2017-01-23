package com.example.dr0pwater.getlocation.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.example.dr0pwater.getlocation.data.NewCustomer;
import com.example.dr0pwater.getlocation.data.NewCustomerdb;
import com.example.dr0pwater.getlocation.data.Types;
import com.example.dr0pwater.getlocation.data.Typesdb;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

public class RegisterCustomer extends Activity {

    MainActivity mainActivity;
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView cusOutsideImg;
    private LinearLayout cusRegister_outideImageChange;

    private Database database;
    private Customerdb customerdb;
    private Typesdb typesdb;
    private Citydb citydb;
    private Districtdb districtdb;
    private Commissiondb commissiondb;
    private NewCustomerdb newCustomerdb;

    private NewCustomer newCustomer;
    private Customer customer;

    private ArrayList<City> city;
    private int city_id;
    private ArrayList<District> district;
    private int district_id;
    private ArrayList<Commission> commission;
    private int commission_id;
    private ArrayList<Types> types;
    private int types_id;

    private String changedCity="", changedDistrict="", changedCommission="", obj_name="",
            changeTypes="", owner="", phone="", name="", address="";

    public void getCity() {
        city = citydb.getall();
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinnerRegister_city);
        String[] items = new String[city.size()];

        if(city.size()>0){
            for(int i = 0; i< city.size(); i++){
                items[i] = city.get(i).name;
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
                changedCity = String.valueOf(parent.getItemAtPosition(position));
                for(int i=0; i<city.size(); i++){
                    if(city.get(i).name.equals(changedCity) ) {
                        city_id = city.get(i).id;
                    }
                }
                getDuureg();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }
    public void getDuureg() {
        district = districtdb.getDistrict(city_id);
        district_id = CheckDistrictInside();
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinnerRegister_duureg);
        String[] items = new String[district.size()];

        if(district.size()>0){
            for(int i = 0; i< district.size(); i++){
                items[i] = district.get(i).name;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner.setAdapter(adapter);

        int setPosition=0;
        for(int i=0; i<district.size(); i++){
            if(district.get(i).id == district_id ) {
                setPosition = adapter.getPosition(district.get(i).name);
            }
        }
        dynamicSpinner.setSelection(setPosition);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                getKhoroo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void getKhoroo(){
        commission = commissiondb.getCommission(district_id);
        commission_id = CheckCommissionInside();
        Spinner dynamicSpinner_commission = (Spinner) findViewById(R.id.dynamic_spinnerRegister_khoroo);
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
            if(commission.get(i).id == commission_id ) {
                setPosition = adapter.getPosition(commission.get(i).name);
            }
        }
        dynamicSpinner_commission.setSelection(setPosition);

        dynamicSpinner_commission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                changedCommission = (String) parent.getItemAtPosition(position);
                for(int i=0; i<commission.size(); i++){
                    if(commission.get(i).name.equals(changedCommission) ) {
                        commission_id = commission.get(i).id;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    public void getTypes(){
        types = typesdb.getall();
        Spinner dynamicSpinner_types = (Spinner) findViewById(R.id.dynamic_spinnerRegister_types);
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
        setContentView(R.layout.activity_register_customer);

        mainActivity = new MainActivity();

        database = new Database(this);
        customerdb = new Customerdb(database);
        typesdb = new Typesdb(database);
        citydb = new Citydb(database);
        districtdb = new Districtdb(database);
        commissiondb = new Commissiondb(database);
        newCustomerdb = new NewCustomerdb(database);

        getCity();
        getTypes();

        cusRegister_outideImageChange = (LinearLayout)findViewById(R.id.cusRegister_changeOutideImage_id);
        cusOutsideImg = (ImageView)findViewById(R.id.cusRegister_outsideImg_id);
        Button cusRegisterUploadImg = (Button)findViewById(R.id.cusRegister_upload_img_id);
        Button cusRegisterTakeImg = (Button)findViewById(R.id.cusRegister_take_img_id);
        cusOutsideImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cusRegister_outideImageChange.setVisibility(View.VISIBLE);
            }
        });
        cusRegisterUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        cusRegisterTakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        final EditText registerCusObj_name = (EditText) findViewById(R.id.cusRegister_obj_name_id);
        final EditText registerCusName = (EditText) findViewById(R.id.cusRegister_name_id);
        final TextView registerCusPosition = (TextView)findViewById(R.id.cusRegister_position_id);
        final EditText registerCusOwner = (EditText) findViewById(R.id.cusRegister_owner_id);
        final EditText registerCusPhone = (EditText) findViewById(R.id.cusRegister_phone_id);
        final EditText registerCusAddress = (EditText) findViewById(R.id.cusRegister_address_id);
        ImageView registerCusPositionMap = (ImageView) findViewById(R.id.cusRegister_mapIcon_id);

        if(mainActivity.getLocation() != null){
            String latLong = mainActivity.getLocation().getLatitude()+ "," + mainActivity.getLocation().getLongitude();
            registerCusPosition.setText(latLong);
            registerCusPositionMap.setImageDrawable(getResources().getDrawable(R.drawable.font_awesome_map_marker_150_5_41fa4e_none));
//            Toast.makeText(getApplicationContext(), "Цэгийн мэдээлэл авлаа" + latLong, Toast.LENGTH_LONG).show();
        } else{
//            Toast.makeText(getApplicationContext(), "Цэг авах боломжгүй байна. Дахин оролдоно уу?", Toast.LENGTH_SHORT).show();
            registerCusPositionMap.setImageDrawable(getResources().getDrawable(R.drawable.font_awesome_map_marker_150_5_f91f40_none));
        }

        registerCusObj_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                obj_name = String.valueOf(registerCusObj_name.getText());
            }
        });
        registerCusName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = String.valueOf(registerCusName.getText());
            }
        });
        registerCusOwner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                owner = String.valueOf(registerCusOwner.getText());
            }
        });
        registerCusPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = String.valueOf(registerCusPhone.getText());
            }
        });
        registerCusAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address = String.valueOf(registerCusAddress.getText());
            }
        });

        Button registerCusBtn = (Button)findViewById(R.id.cusRegister_btn_id);
        registerCusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer = new Customer();
                customer.city = city_id;
                customer.duureg = district_id;
                customer.khoroo = commission_id;
                customer.type = types_id;
                customer.obj_name = obj_name;
                customer.name = name;
                customer.owner = owner;
                customer.phone = phone;
                customer.address = address;
                customer.position = String.valueOf(registerCusPosition.getText());
                customerdb.create(customer);
                newCustomer = new NewCustomer();
                newCustomer.customer = customerdb.getLastRegisterCustomerId();
                newCustomerdb.create(newCustomer);
                Toast.makeText(getApplicationContext(), "lastId->"+customerdb.getLastRegisterCustomerId(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("lol", "onActivityResult: "+requestCode+"data->"+data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            Log.d("lol", "onActivityResult: "+ selectedImage);
            cusOutsideImg.setImageURI(selectedImage);
            cusRegister_outideImageChange.setVisibility(View.INVISIBLE);
        }
    }


    public static double PI = 3.14159265;
    public static double TWOPI = 2*PI;

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

    public int CheckDistrictInside(){
//        ArrayList<District> districtArrayList = districtdb.getall();
        for (int i=0; i<district.size(); i++){
            ArrayList<Double> lat_array = new ArrayList<Double>();
            ArrayList<Double> long_array = new ArrayList<Double>();
            Log.d("lol", "CheckDistrictInside: "+district.get(i).id);
            try {
                JSONArray array = new JSONArray( district.get(i).boundary);
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
//                        Toast.makeText(getApplicationContext(),""+ret+" <-district->"+district.get(i).id,Toast.LENGTH_LONG).show();
                        return district.get(i).id;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  0;
    }
    public int CheckCommissionInside(){
//        ArrayList<Commission> commissionArrayList = commissiondb.getall();
        for (int i=0; i<commission.size(); i++){
            ArrayList<Double> lat_array = new ArrayList<Double>();
            ArrayList<Double> long_array = new ArrayList<Double>();

            try {
                JSONArray array = new JSONArray( commission.get(i).boundary);
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
//                        Toast.makeText(getApplicationContext(),""+ret+" <-commission->"+commission.get(i).id,Toast.LENGTH_LONG).show();
                        return commission.get(i).id;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  0;
    }
}

package com.example.dr0pwater.getlocation.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.ArrayList;

public class RegisterCustomer extends Activity {

    MainActivity mainActivity;
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView cusOutsideImg;

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

        cusOutsideImg = (ImageView)findViewById(R.id.cusRegister_outsideImg_id);
        cusOutsideImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        final EditText registerCusObj_name = (EditText) findViewById(R.id.cusRegister_obj_name_id);
        final EditText registerCusName = (EditText) findViewById(R.id.cusRegister_name_id);
        final TextView registerCusPosition = (TextView)findViewById(R.id.cusRegister_position_id);
        final EditText registerCusOwner = (EditText) findViewById(R.id.cusRegister_owner_id);
        final EditText registerCusPhone = (EditText) findViewById(R.id.cusRegister_phone_id);
        final EditText registerCusAddress = (EditText) findViewById(R.id.cusRegister_address_id);

        if(mainActivity.getLocation() != null){
            String latLong = mainActivity.getLocation().getLatitude()+ "," + mainActivity.getLocation().getLongitude();
            registerCusPosition.setText(latLong);
            Toast.makeText(getApplicationContext(), "Цэгийн мэдээлэл авлаа" + latLong, Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(getApplicationContext(), "Цэг авах боломжгүй байна. Дахин оролдоно уу?", Toast.LENGTH_SHORT).show();
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
        if(requestCode == RESULT_LOAD_IMAGE && requestCode == 1 && data != null){
            Uri selectedImage = data.getData();
            cusOutsideImg.setImageURI(selectedImage);
        }
    }
}

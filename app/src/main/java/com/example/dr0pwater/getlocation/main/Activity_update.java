package com.example.dr0pwater.getlocation.main;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dr0pwater.getlocation.R;
import com.example.dr0pwater.getlocation.data.City;
import com.example.dr0pwater.getlocation.data.Citydb;
import com.example.dr0pwater.getlocation.data.Commission;
import com.example.dr0pwater.getlocation.data.Commissiondb;
import com.example.dr0pwater.getlocation.data.Customer;
import com.example.dr0pwater.getlocation.data.CustomerUpdateInfo;
import com.example.dr0pwater.getlocation.data.CustomerUpdateInfodb;
import com.example.dr0pwater.getlocation.data.Customerdb;
import com.example.dr0pwater.getlocation.data.Database;
import com.example.dr0pwater.getlocation.data.District;
import com.example.dr0pwater.getlocation.data.Districtdb;
import com.example.dr0pwater.getlocation.data.NewCustomerdb;
import com.example.dr0pwater.getlocation.data.Types;
import com.example.dr0pwater.getlocation.data.Typesdb;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Activity_update extends AppCompatActivity {

    private Database database;
    private Customerdb customerdb;
    private Typesdb typesdb;
    private Citydb citydb;
    private Districtdb districtdb;
    private Commissiondb commissiondb;
    private CustomerUpdateInfodb customerUpdateInfodb;
    private NewCustomerdb newCustomerdb;

    private ArrayList<Customer> customerDetail;
    private Customer customer;
    private CustomerUpdateInfo customerUpdateInfo;
    private String changedCity="", changedDistrict="", changedCommission="",
            changeTypes="", changedOwner="", changedPhone="", changedName="", changedOpenIf="";
    private int customer_id;

    private ArrayList<City> city;
    private int city_id;
    private ArrayList<District> district;
    private int district_id;
    private ArrayList<Commission> commission;
    private int commission_id;
    private ArrayList<Types> types;
    private int types_id;

    LinearLayout linearLCusDetailInfo;
    LinearLayout linearLCusEditInfo;

    private boolean updated = false;

    public void getCity() {
        city = citydb.getall();
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinnerEdit_city);
        String[] items = new String[city.size()];

        if(city.size()>0){
            for(int i = 0; i< city.size(); i++){
                items[i] = city.get(i).name;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner.setAdapter(adapter);

        int setPosition = adapter.getPosition(citydb.getCityName(customerDetail.get(0).city));
        dynamicSpinner.setSelection(setPosition);

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
        Spinner dynamicSpinner = (Spinner) findViewById(R.id.dynamic_spinnerEdit_duureg);
        String[] items = new String[district.size()];

        if(district.size()>0){
            for(int i = 0; i< district.size(); i++){
                items[i] = district.get(i).name;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner.setAdapter(adapter);

        int setPosition = adapter.getPosition(districtdb.getDistrictName(customerDetail.get(0).duureg));
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
        Spinner dynamicSpinner_commission = (Spinner) findViewById(R.id.dynamic_spinnerEdit_khoroo);
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

        int setPosition = adapter.getPosition(commissiondb.getCommissionName(customerDetail.get(0).khoroo));
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
        Spinner dynamicSpinner_types = (Spinner) findViewById(R.id.dynamic_spinnerEdit_types);
        String[] items = new String[types.size()];

        if(types.size()>0){
            for(int i = 0; i< types.size(); i++){
                items[i] = types.get(i).name;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner_types.setAdapter(adapter);

        int setPosition = adapter.getPosition(typesdb.getTypesName(customerDetail.get(0).type));
        dynamicSpinner_types.setSelection(setPosition);


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
    public void getOpenIf(){
        Spinner dynamicSpinner_openif = (Spinner) findViewById(R.id.dynamic_spinnerEdit_openif);
        String[] items = new String[2];
        items[0] = "Нээлттэй";
        items[1] = "Хаалттай";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner_openif.setAdapter(adapter);

        int setPosition = adapter.getPosition("Н");
        if (customerDetail.get(0).openif==2)
            dynamicSpinner_openif.setSelection(0);
        else
        dynamicSpinner_openif.setSelection(1);

        dynamicSpinner_openif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
//                Log.v("item", (String) parent.getItemAtPosition(position));
                changedOpenIf = (String)parent.getItemAtPosition(position);

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
        setContentView(R.layout.activity_update);
        database = new Database(this);
        customerdb = new Customerdb(database);
        typesdb = new Typesdb(database);
        citydb = new Citydb(database);
        districtdb = new Districtdb(database);
        commissiondb = new Commissiondb(database);
        customerUpdateInfodb = new CustomerUpdateInfodb(database);
        newCustomerdb = new NewCustomerdb(database);

        customer_id = this.getIntent().getIntExtra("customer",0);
        customerDetail = customerdb.getCustomerDetail(customer_id);
        getCity();
        getTypes();
        getOpenIf();

        TextView cusDetailName = (TextView)findViewById(R.id.cusdetail_name_id);
        TextView cusDetailPosition = (TextView)findViewById(R.id.cusdetail_position_id);
        TextView cusDetailOwner = (TextView)findViewById(R.id.cusdetail_owner_id);
        TextView cusDetailPhone = (TextView)findViewById(R.id.cusdetail_phone_id);
        TextView cusDetailType = (TextView)findViewById(R.id.cusdetail_type_id);
        ImageView cusOutsideImg = (ImageView)findViewById(R.id.cusdetail_outsideImg_id);
        TextView cusDetailCity = (TextView)findViewById(R.id.cusdetail_city_id);
        TextView cusDetailDistrict = (TextView)findViewById(R.id.cusdetail_district_id);
        TextView cusDetailCommission = (TextView)findViewById(R.id.cusdetail_commission_id);
        TextView cusDetailIsOpen = (TextView)findViewById(R.id.cusdetail_openif_id);

        cusDetailName.setText(customerDetail.get(0).name);
        cusDetailPosition.setText(customerDetail.get(0).position);
        cusDetailOwner.setText(customerDetail.get(0).owner);
        cusDetailPhone.setText(customerDetail.get(0).phone);
        cusDetailType.setText(typesdb.getTypesName(customerDetail.get(0).type));
        Picasso.with(this).load(R.drawable.common_google_signin_btn_icon_dark_disabled).into(cusOutsideImg);
        cusDetailCity.setText(citydb.getCityName(customerDetail.get(0).city));
        cusDetailDistrict.setText(districtdb.getDistrictName(customerDetail.get(0).duureg));
        cusDetailCommission.setText(commissiondb.getCommissionName(customerDetail.get(0).khoroo));
        if (customerDetail.get(0).openif==2){
            cusDetailIsOpen.setText("Нээлттэй");
        }
        else {
            cusDetailIsOpen.setText("Хаалттай");
            cusDetailIsOpen.setTextColor(Color.argb(96,246,77,0));
        }

        final EditText cusEditInfoName = (EditText)findViewById(R.id.cusedit_name_id);
        cusEditInfoName.setBackgroundResource(R.drawable.focus_border_style);
        cusEditInfoName.setText(customerDetail.get(0).name);
        final EditText cusEditInfoOwner = (EditText)findViewById(R.id.cusedit_owner_id);
        cusEditInfoOwner.setBackgroundResource(R.drawable.focus_border_style);
        cusEditInfoOwner.setText(customerDetail.get(0).owner);
        final EditText cusEditInfoPhone = (EditText)findViewById(R.id.cusedit_phone_id);
        cusEditInfoPhone.setBackgroundResource(R.drawable.focus_border_style);
        cusEditInfoPhone.setText(customerDetail.get(0).phone);
        TextView cusDetailEdit = (TextView)findViewById(R.id.cusdetail_edit_id);
        cusDetailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLCusDetailInfo = (LinearLayout) findViewById(R.id.cusdetail_info_id);
                linearLCusDetailInfo.setVisibility(View.INVISIBLE);
                linearLCusEditInfo = (LinearLayout) findViewById(R.id.cusdetail_editinfo_id);
                linearLCusEditInfo.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Удахгүй засна->"+customer_id, Toast.LENGTH_SHORT).show();
            }
        });

        cusEditInfoName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changedName = String.valueOf(cusEditInfoName.getText());
            }
        });
        cusEditInfoOwner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changedOwner = String.valueOf(cusEditInfoOwner.getText());
            }
        });
        cusEditInfoPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changedPhone = String.valueOf(cusEditInfoPhone.getText());
            }
        });
        Button updateBtn = (Button) findViewById(R.id.cusEditInfo_btn_id);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerUpdateInfo = new CustomerUpdateInfo();
                customerUpdateInfo.customer = customer_id;
                if (city_id != customerDetail.get(0).city){
                    customerUpdateInfo.city = city_id;
                    updated = true;
                }
                if (district_id != customerDetail.get(0).duureg) {
                    customerUpdateInfo.distric = district_id;
                    updated = true;
                }
                if (commission_id != customerDetail.get(0).khoroo) {
                    customerUpdateInfo.commission = commission_id;
                    updated = true;
                }
                if (types_id != customerDetail.get(0).type){
                    customerUpdateInfo.types = types_id;
                    updated = true;
                }
                if (!changedName.equals(customerDetail.get(0).name) && !changedName.equals("")){
                    customerUpdateInfo.name = changedName;
                    updated = true;
                }
                if (!changedOwner.equals(customerDetail.get(0).owner) && !changedOwner.equals("")){
                    customerUpdateInfo.owner = changedOwner;
                    updated = true;
                }
                if (!changedPhone.equals(customerDetail.get(0).phone) && !changedPhone.equals("")){
                    customerUpdateInfo.phone = changedPhone;
                    updated = true;
                }
                if (changedOpenIf.equals("Нээлттэй")){
                    if (2 != customerDetail.get(0).openif){
                        customerUpdateInfo.openif = 2;
                        updated = true;
                    }
                }
                else {
                    if (1 != customerDetail.get(0).openif){
                        customerUpdateInfo.openif = 1;
                        updated = true;
                    }
                }

                if (updated){
                   boolean canUpdate =  newCustomerdb.CheckIsSend(customerUpdateInfo.id);
                    if (canUpdate)
                        customerUpdateInfodb.create(customerUpdateInfo);

                    customer = new Customer();
                    customer.id = customerUpdateInfo.customer;
                    customer.city = customerUpdateInfo.city;
                    customer.duureg = customerUpdateInfo.distric;
                    customer.khoroo = customerUpdateInfo.commission;
                    customer.name = customerUpdateInfo.name;
                    customer.owner = customerUpdateInfo.owner;
                    customer.phone = customerUpdateInfo.phone;
                    customer.type = customerUpdateInfo.types;
                    customer.openif = customerUpdateInfo.openif;

                    customerdb.UpdateCustomer(customer);
                    Toast.makeText(getApplicationContext(), "Амжилттай өөрчилөлөө", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getBaseContext(), Activity_update.class);
//                    intent.putExtra("customer",customer_id);
//                    startActivity(intent);
                    linearLCusEditInfo.setVisibility(View.GONE);
                    linearLCusDetailInfo.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Та мэдээллээ шинэчлэнэ үү?", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

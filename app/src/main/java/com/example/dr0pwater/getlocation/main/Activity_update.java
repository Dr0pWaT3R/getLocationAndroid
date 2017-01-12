package com.example.dr0pwater.getlocation.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dr0pwater.getlocation.R;
import com.example.dr0pwater.getlocation.data.Customer;
import com.example.dr0pwater.getlocation.data.Customerdb;
import com.example.dr0pwater.getlocation.data.Database;
import com.example.dr0pwater.getlocation.data.Typesdb;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Activity_update extends AppCompatActivity {

    private Database database;
    private Customerdb customerdb;
    private Typesdb typesdb;
    private ArrayList<Customer> customerDetail;
    private int customer_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        database = new Database(this);
        customerdb = new Customerdb(database);
        typesdb = new Typesdb(database);

        customer_id = this.getIntent().getIntExtra("customer",0);
        customerDetail = customerdb.getCustomerDetail(customer_id);
        TextView cusDetailName = (TextView)findViewById(R.id.cusdetail_name_id);
        TextView cusDetailPosition = (TextView)findViewById(R.id.cusdetail_position_id);
        TextView cusDetailOwner = (TextView)findViewById(R.id.cusdetail_owner_id);
        TextView cusDetailPhone = (TextView)findViewById(R.id.cusdetail_phone_id);
        TextView cusDetailType = (TextView)findViewById(R.id.cusdetail_type_id);
        ImageView cusOutsideImg = (ImageView)findViewById(R.id.cusdetail_outsideImg_id);
        cusDetailName.setText(customerDetail.get(0).name);
        cusDetailPosition.setText(customerDetail.get(0).position);
        cusDetailOwner.setText(customerDetail.get(0).owner);
        cusDetailPhone.setText(customerDetail.get(0).phone);
        cusDetailType.setText(typesdb.getTypesName(customerDetail.get(0).type));
        Picasso.with(this).load(R.drawable.common_google_signin_btn_icon_dark_disabled).into(cusOutsideImg);
        Toast.makeText(this, "customer->"+customer_id, Toast.LENGTH_SHORT).show();

    }
}

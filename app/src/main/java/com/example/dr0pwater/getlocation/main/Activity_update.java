package com.example.dr0pwater.getlocation.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dr0pwater.getlocation.R;

public class Activity_update extends AppCompatActivity {

    private int customer_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        customer_id = this.getIntent().getIntExtra("customer",0);
        Toast.makeText(this, "customer->"+customer_id, Toast.LENGTH_SHORT).show();
    }
}

package com.example.dr0pwater.getlocation.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dr0pwater.getlocation.R;
import com.example.dr0pwater.getlocation.data.City;
import com.example.dr0pwater.getlocation.data.Commission;
import com.example.dr0pwater.getlocation.data.Commissiondb;
import com.example.dr0pwater.getlocation.data.Customerdb;
import com.example.dr0pwater.getlocation.data.Database;
import com.example.dr0pwater.getlocation.data.District;
import com.example.dr0pwater.getlocation.data.Districtdb;
import com.example.dr0pwater.getlocation.data.InterfacePage;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LocationQ extends Fragment implements InterfacePage {

    @Override
    public void refresh() {}

    @Override
    public void not_current(int i) {}

    public static double PI = 3.14159265;
    public static double TWOPI = 2*PI;
    private Spinner simduuregSpinner, bgahorooSpinner,cityspinner;
    public static String AIMAG_CITY_CODE="", SUM_DUUREG_CODE="",bair1="",toot1="",utas1="";
    public static int BAG_HOROO_CODE=0;
    private ArrayList<City> cities;
    private ArrayList<District> districts;
    private ArrayList<Commission> commissions;

    private ArrayAdapter<String> sumAdapter,bagadapter,Cityadapter;
    private Database database;
    private Customerdb customerdb;
    private Districtdb districtdb;
    private Commissiondb commissiondb;
    private View parent = null;
    List<String> mycitys;
    List<String> mydistrict;
    List<String> mykhoroo ;
    City d;
    int pos1=0,pos2,pos3;
    static String locationid="";
    public LocationQ(){
        pos1=-1;
        pos2=-1;
        pos3=-1;

        AIMAG_CITY_CODE="";
        SUM_DUUREG_CODE="";
        BAG_HOROO_CODE=0;
        bair1="";
        toot1="";
        utas1="";
        locationid="0";
    }

    private Spinner gudamspinner,bairspinner,tootspinner;
    private String GUDAM;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v;
    //        if(parent != null) return parent;
        v= inflater.inflate(R.layout.activity_main, container, false);
        parent=v;
        cityspinner = (Spinner) v.findViewById(R.id.aimaghot);
        simduuregSpinner = (Spinner) v.findViewById(R.id.sumduureg);
        bgahorooSpinner = (Spinner) v.findViewById(R.id.baghoroo);
        gudamspinner = (Spinner) v.findViewById(R.id.horoolol);
        bairspinner = (Spinner) v.findViewById(R.id.bair);
        tootspinner = (Spinner) v.findViewById(R.id.toot);

//        Cityadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        bagadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        database =  database.getInstance(this.getActivity());
        customerdb = new Customerdb(database);
        districtdb = new Districtdb(database);
        commissiondb = new Commissiondb(database);

        return v;
    }

    private void toirog_aimag(){
        cityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AIMAG_CITY_CODE = String.valueOf(cities.get(position).id);
                pos1 = position;
                districts = districtdb.getDistrict(Integer.parseInt(AIMAG_CITY_CODE));
                if(mydistrict.size() > 0 )
                    for (int i=0;i<districts.size();i++) {
                        boolean del = true;
                        for(int j = 0 ; j < mydistrict.size();j++) {
                            if(mydistrict.get(j).equalsIgnoreCase(String.valueOf(districts.get(i).id)))
                                del = false;
                        }
                        if (del == true) {
                            districts.remove(i);
                            i = i - 1;
                        }
                    }
                ArrayList<String> temp = new ArrayList<>();

                for (int i = 0; i < districts.size(); i++) {
                    temp.add(districts.get(i).name);
                }

                sumAdapter = new ArrayAdapter<>
                        (LocationQ.this.getActivity(),
                                android.R.layout.simple_spinner_item, temp);
                simduuregSpinner.setAdapter(sumAdapter);
                if(pos2!=-1 && pos2 < temp.size())
                    simduuregSpinner.setSelection(pos2);
                sumAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                toirog_sum();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void toirog_sum(){
        simduuregSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (districts == null) return;
                pos2 = position;
                SUM_DUUREG_CODE = String.valueOf(districts.get(position).id);
                districts = districtdb.getDistrict(Integer.parseInt(SUM_DUUREG_CODE));
                if(mykhoroo.size() > 0 )
                    for (int i=0;i<commissions.size();i++)
                    {
                        boolean del = true;
                        if(mykhoroo.size() != 0) {
                            for (int j = 0; j < mykhoroo.size(); j++) {
                                if (mykhoroo.get(j).equalsIgnoreCase(String.valueOf(commissions.get(i).id)))
                                    del = false;

                            }
                            if (del == true) {
                                commissions.remove(i);
                                i = i - 1;
                            }
                        }
                    }
                if(commissions.size()==0){
                    commissions = commissiondb.getCommission(Integer.parseInt(SUM_DUUREG_CODE));
                }

                ArrayList<String> temp = new ArrayList<>();
                for (int i = 0; i < commissions.size(); i++) {
                    temp.add(commissions.get(i).name);
                }
                bagadapter = new ArrayAdapter<>(LocationQ.this.getActivity(), android.R.layout.simple_spinner_item, temp);
                bgahorooSpinner.setAdapter(bagadapter);
                if(pos3!=-1 && pos3 < temp.size())
                    bgahorooSpinner.setSelection(pos3);
                toirog_khoro();
                bagadapter.setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void toirog_khoro(){
        bgahorooSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (commissions == null) return;
                pos3 = position;
                if(BAG_HOROO_CODE != commissions.get(position).id) {
                    BAG_HOROO_CODE = commissions.get(position).id;
//                    if (MainActivity.fragmentArrayList != null)
//                        for (int i = 0; i < MainActivity.fragmentArrayList.size(); i++) {
//                            ((InterfacePage)MainActivity.fragmentArrayList.get(i)).refresh();
//                        }
                }
//                Toast.makeText(LocationQ.this.getContext(),""+ bagNews.get(position).boundary,Toast.LENGTH_LONG).show();
                ArrayList<Double> lat_array = new ArrayList<Double>();
                ArrayList<Double> long_array = new ArrayList<Double>();

                try {
                    JSONArray array = new JSONArray( commissions.get(position).boundary);
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
                        Toast.makeText(LocationQ.this.getContext(),""+ret,Toast.LENGTH_LONG).show();
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

    public static boolean is_valid_gps_coordinate(double latitude,
                                                  double longitude)
    {
        //This is a bonus function, it's unused, to reject invalid lat/longs.
        if (latitude > -90 && latitude < 90 &&
                longitude > -180 && longitude < 180)
        {
            return true;
        }
        return false;
    }


}

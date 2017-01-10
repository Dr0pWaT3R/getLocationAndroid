package com.example.dr0pwater.getlocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TableLayout;

import com.example.dr0pwater.getlocation.data.Customer;

import java.util.ArrayList;

/**
 * Created by root on 1/10/17.
 */

public class Adapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Customer> customerInfo;
    private TableLayout table;
    CustomFilter filter;
    ArrayList<Customer> filterList;

    public Adapter(Context cxt, ArrayList<Customer> customers){
        this.context = cxt;
        this.customerInfo = customers;
        this.filterList = customers;
    }

    @Override
    public int getCount() {
        return customerInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return customerInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return customerInfo.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.activity_main, null);
        }
        table = (TableLayout)convertView.findViewById(R.id.table_customer);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                ArrayList<Customer> filters = new ArrayList<>();
                for (int i=0; i<filterList.size(); i++){
                    if(filterList.get(i).name.toUpperCase().contains(constraint)){
                        Customer customer = new Customer();
                        filters.add(customer);
                    }
                }

                results.count=filters.size();
                results.values=filters;
            }else {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            customerInfo= (ArrayList<Customer>) results.values;
            notifyDataSetChanged();
        }
    }
}

package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.CpuUsageInfo;
import android.text.Editable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by root on 1/14/17.
 */

public class CustomerUpdateInfodb {
    private Database myDatabase;
    public static final String table = "CustomerUpdate";
    public static final String _id = "id";
    public static final String _customer = "customer";
    public static final String _city = "city";
    public static final String _duureg = "district";
    public static final String _khoroo = "commission";
    public static final String _name = "name";
    public static final String _owner = "owner";
    public static final String _position = "position";
    public static final String _outsideImage = "outsideImage";
    public static final String _type = "types";
    public static final String _phone = "phone";
    public static final String _openif = "openif";
    public static final String _ifsend = "ifsend";

    public CustomerUpdateInfodb(Database context) {
        myDatabase = context;
    }
    public CustomerUpdateInfodb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_customer+ " int,"
                +_city+ " int,"
                +_duureg+ " int,"
                +_khoroo+ " int,"
                +_name+ " text,"
                +_owner+ " text,"
                +_position+ " text,"
                +_outsideImage+ " text,"
                +_type+ " int,"
                +_phone+ " text,"
                +_openif+ " int,"
                +_ifsend+ " int"

                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(CustomerUpdateInfo customer) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_city, customer.city);
        values.put(_duureg, customer.distric);
        values.put(_khoroo, customer.commission);
        values.put(_name, customer.name);
        values.put(_owner, customer.owner);
        values.put(_position, customer.position);
        values.put(_outsideImage, customer.outsideImage);
        values.put(_type, customer.types);
        values.put(_phone, customer.phone);
        values.put(_openif, customer.openif);
        values.put(_ifsend, customer.ifsend);
        values.put(_customer, customer.customer);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<CustomerUpdateInfo> customers) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
                _customer + "," +
                _city + "," +
                _duureg + "," +
                _khoroo + "," +
                _name + "," +
                _owner + "," +
                _position + "," +
                _outsideImage + "," +
                _type + "," +
                _phone + "," +
                _openif+ "," +
                _ifsend+ "," +
                _id + "" +
                " ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (CustomerUpdateInfo a : customers) {
            stmt.bindLong(1, a.customer);
            stmt.bindLong(2, a.city);
            stmt.bindLong(3, a.distric);
            stmt.bindLong(4, a.commission);
            stmt.bindString(5, a.name);
            stmt.bindString(6, a.owner);
            stmt.bindString(7, a.position);
            stmt.bindString(8, a.outsideImage);
            stmt.bindLong(9, a.types);
            stmt.bindString(10, a.phone);
            stmt.bindLong(11, a.openif);
            stmt.bindLong(12, a.ifsend);
            stmt.bindLong(13, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public ArrayList<CustomerUpdateInfo> getall() {
        ArrayList<CustomerUpdateInfo> customers = new ArrayList<>();

        String query = "SELECT  * FROM " + table + " where ifsend = 0 and id IN (SELECT MAX(id) FROM " + table + " GROUP by customer)";
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CustomerUpdateInfo customer = null;
        if (cursor.moveToFirst()) {
            do {

                customer = new CustomerUpdateInfo();
                customer.id = cursor.getInt(cursor.getColumnIndex(_id));
                customer.city = cursor.getInt(cursor.getColumnIndex(_city));
                customer.customer = cursor.getInt(cursor.getColumnIndex(_customer));
                customer.distric = cursor.getInt(cursor.getColumnIndex(_duureg));
                customer.commission = cursor.getInt(cursor.getColumnIndex(_khoroo));
                customer.name = cursor.getString(cursor.getColumnIndex(_name));
                customer.owner = cursor.getString(cursor.getColumnIndex(_owner));
                customer.position = cursor.getString(cursor.getColumnIndex(_position));
                customer.outsideImage = cursor.getString(cursor.getColumnIndex(_outsideImage));
                customer.types = cursor.getInt(cursor.getColumnIndex(_type));
                customer.phone = cursor.getString(cursor.getColumnIndex(_phone));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }

    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }

    public void UpdateIsSend(ArrayList<CustomerUpdateInfo> customerUpdateInfo) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();

        for (int i=0; i<customerUpdateInfo.size(); i++){
            String sql = "UPDATE "+table+" SET " + _ifsend + "=1 WHERE id = " + customerUpdateInfo.get(i).id;
            db.execSQL(sql);
            Log.d("ifsend", "updateIsSend: "+sql);
        }
        db.close();
    }
}

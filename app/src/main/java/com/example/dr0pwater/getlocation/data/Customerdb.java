package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.Editable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/16/16.
 */

public class Customerdb {
    private Database myDatabase;
    public static final String table = "customerpoint";
    public static final String _id = "id";
    public static final String _city = "city";
    public static final String _duureg = "duureg";
    public static final String _khoroo = "khoroo";
    public static final String _name = "name";
    public static final String _owner = "owner";
    public static final String _position = "position";
    public static final String _outsideImage = "outsideImage";
    public static final String _type = "type";
    public static final String _phone = "phone";
    public static final String _openif = "openif";

    public Customerdb(Database context) {
        myDatabase = context;
    }
    public Customerdb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_city+ " int,"
                +_duureg+ " int,"
                +_khoroo+ " int,"
                +_name+ " text,"
                +_owner+ " text,"
                +_position+ " text,"
                +_outsideImage+ " text,"
                +_type+ " int,"
                +_phone+ " text,"
                +_openif+ " int"

                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(Customer cusp) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_city, cusp.city);
        values.put(_duureg, cusp.duureg);
        values.put(_khoroo, cusp.khoroo);
        values.put(_name, cusp.name);
        values.put(_owner, cusp.owner);
        values.put(_position, cusp.position);
        values.put(_outsideImage, cusp.outsideImage);
        values.put(_type, cusp.type);
        values.put(_phone, cusp.phone);
        values.put(_openif, cusp.openif);
        values.put(_id, cusp.id);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<Customer> customers) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
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
                _id + "" +
                " ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (Customer a : customers) {
            stmt.bindLong(1, a.city);
            stmt.bindLong(2, a.duureg);
            stmt.bindLong(3, a.khoroo);
            stmt.bindString(4, a.name);
            stmt.bindString(5, a.owner);
            stmt.bindString(6, a.position);
            stmt.bindString(7, a.outsideImage);
            stmt.bindLong(8, a.type);
            stmt.bindString(9, a.phone);
            stmt.bindLong(10, a.openif);
            stmt.bindLong(11, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public ArrayList<Customer> getCustomerInfo(int districtId, int commissionId ) {
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT  * FROM " + table+ " where duureg="+districtId+ " and khoroo="+commissionId;
        Log.d("query", "getCustomerInfo: "+query);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {

                customer = new Customer();
                customer.id = cursor.getInt(cursor.getColumnIndex(_id));
                customer.city = cursor.getInt(cursor.getColumnIndex(_city));
                customer.duureg = cursor.getInt(cursor.getColumnIndex(_duureg));
                customer.khoroo = cursor.getInt(cursor.getColumnIndex(_khoroo));
                customer.name = cursor.getString(cursor.getColumnIndex(_name));
                customer.owner = cursor.getString(cursor.getColumnIndex(_owner));
                customer.position = cursor.getString(cursor.getColumnIndex(_position));
                customer.outsideImage = cursor.getString(cursor.getColumnIndex(_outsideImage));
                customer.phone = cursor.getString(cursor.getColumnIndex(_phone));
                customer.type = cursor.getInt(cursor.getColumnIndex(_type));
                customer.openif = cursor.getInt(cursor.getColumnIndex(_openif));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }
    public ArrayList<Customer> getCustomerInfoWithType(int districtId, int commissionId, int typeId ) {
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT  * FROM " + table+ " where duureg="+districtId+ " and khoroo="+commissionId +" and type="+typeId;
        Log.d("query", "getCustomerInfoWithType: "+query);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {

                customer = new Customer();
                customer.id = cursor.getInt(cursor.getColumnIndex(_id));
                customer.city = cursor.getInt(cursor.getColumnIndex(_city));
                customer.duureg = cursor.getInt(cursor.getColumnIndex(_duureg));
                customer.khoroo = cursor.getInt(cursor.getColumnIndex(_khoroo));
                customer.name = cursor.getString(cursor.getColumnIndex(_name));
                customer.owner = cursor.getString(cursor.getColumnIndex(_owner));
                customer.position = cursor.getString(cursor.getColumnIndex(_position));
                customer.outsideImage = cursor.getString(cursor.getColumnIndex(_outsideImage));
                customer.phone = cursor.getString(cursor.getColumnIndex(_phone));
                customer.type = cursor.getInt(cursor.getColumnIndex(_type));
                customer.openif = cursor.getInt(cursor.getColumnIndex(_openif));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }
    public ArrayList<Customer> getall() {
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {

                customer = new Customer();
                customer.id = cursor.getInt(cursor.getColumnIndex(_id));
                customer.city = cursor.getInt(cursor.getColumnIndex(_city));
                customer.duureg = cursor.getInt(cursor.getColumnIndex(_duureg));
                customer.khoroo = cursor.getInt(cursor.getColumnIndex(_khoroo));
                customer.name = cursor.getString(cursor.getColumnIndex(_name));
                customer.owner = cursor.getString(cursor.getColumnIndex(_owner));
                customer.position = cursor.getString(cursor.getColumnIndex(_position));
                customer.outsideImage = cursor.getString(cursor.getColumnIndex(_outsideImage));
                customer.type = cursor.getInt(cursor.getColumnIndex(_type));
                customer.phone = cursor.getString(cursor.getColumnIndex(_phone));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }
    public ArrayList<Customer> getCity() {
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT DISTINCT city FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {

                customer = new Customer();
                customer.city = cursor.getInt(cursor.getColumnIndex(_city));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }
    public ArrayList<Customer> getDuureg() {
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT DISTINCT duureg FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {
                customer = new Customer();
                customer.duureg = cursor.getInt(cursor.getColumnIndex(_duureg));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }

    public void updatePosition(int pk, String position){
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        String strFilter = "id=" + pk;
        ContentValues args = new ContentValues();
        args.put("position",position);
        db.update(table, args, strFilter, null);
        db.close();
        Log.d("updatePosition", " success: ");
    }
    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }

    public ArrayList<Customer> searchAny(int districtId, int commissionId, Editable text) {
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT  * FROM " + table+ " where duureg="+districtId+ " and khoroo="+commissionId +" and name like '%"+text+"%'";
        Log.d("query", "getCustomerInfoSearch: "+query);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {

                customer = new Customer();
                customer.id = cursor.getInt(cursor.getColumnIndex(_id));
                customer.city = cursor.getInt(cursor.getColumnIndex(_city));
                customer.duureg = cursor.getInt(cursor.getColumnIndex(_duureg));
                customer.khoroo = cursor.getInt(cursor.getColumnIndex(_khoroo));
                customer.name = cursor.getString(cursor.getColumnIndex(_name));
                customer.owner = cursor.getString(cursor.getColumnIndex(_owner));
                customer.position = cursor.getString(cursor.getColumnIndex(_position));
                customer.outsideImage = cursor.getString(cursor.getColumnIndex(_outsideImage));
                customer.phone = cursor.getString(cursor.getColumnIndex(_phone));
                customer.type = cursor.getInt(cursor.getColumnIndex(_type));
                customer.openif = cursor.getInt(cursor.getColumnIndex(_openif));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }

    public ArrayList<Customer> getCustomerOutsideImage() {
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {
                customer = new Customer();
                customer.outsideImage = cursor.getString(cursor.getColumnIndex(_outsideImage));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }

    public ArrayList<Customer> getCustomerDetail(int customer_id) {
        ArrayList<Customer> customers = new ArrayList<>();

        String query = "SELECT  * FROM " + table+ " WHERE id="+customer_id;
        Log.d("query", "getCustomerInfo: "+query);
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customer customer = null;
        if (cursor.moveToFirst()) {
            do {

                customer = new Customer();
                customer.id = cursor.getInt(cursor.getColumnIndex(_id));
                customer.city = cursor.getInt(cursor.getColumnIndex(_city));
                customer.duureg = cursor.getInt(cursor.getColumnIndex(_duureg));
                customer.khoroo = cursor.getInt(cursor.getColumnIndex(_khoroo));
                customer.name = cursor.getString(cursor.getColumnIndex(_name));
                customer.owner = cursor.getString(cursor.getColumnIndex(_owner));
                customer.position = cursor.getString(cursor.getColumnIndex(_position));
                customer.outsideImage = cursor.getString(cursor.getColumnIndex(_outsideImage));
                customer.phone = cursor.getString(cursor.getColumnIndex(_phone));
                customer.type = cursor.getInt(cursor.getColumnIndex(_type));
                customer.openif = cursor.getInt(cursor.getColumnIndex(_openif));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }
}

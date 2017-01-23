package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by root on 1/16/17.
 */

public class NewCustomerdb {
    private Database myDatabase;
    public static final String table = "NewCustomer";
    public static final String _id = "id";
    public static final String _customer = "customer";
    public static final String _ifsend = "ifsend";

    public NewCustomerdb(Database context) {
        myDatabase = context;
    }
    public NewCustomerdb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_customer+ " int,"
                +_ifsend+ " int"
                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(NewCustomer customer) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_customer, customer.customer);
        values.put(_ifsend, customer.ifsend);
//        values.put(_id, customer.id);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<NewCustomer> newCustomers) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
                _customer + "," +
                _ifsend + "," +
                _id + "" +
                " ) VALUES ( ?,?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (NewCustomer a : newCustomers) {
            stmt.bindLong(1, a.customer);
            stmt.bindLong(2, a.ifsend);
            stmt.bindLong(3, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public ArrayList<NewCustomer> getall() {
        ArrayList<NewCustomer> newCustomers = new ArrayList<>();

        String query = "SELECT  * FROM " + table + " WHERE ifsend = 0";
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        NewCustomer newCustomer = null;
        if (cursor.moveToFirst()) {
            do {

                newCustomer = new NewCustomer();
                newCustomer.id = cursor.getInt(cursor.getColumnIndex(_id));
                newCustomer.customer = cursor.getInt(cursor.getColumnIndex(_customer));
                newCustomers.add(newCustomer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return newCustomers;
    }

    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }

    public void UpdateIsSend(ArrayList<NewCustomer> newCustomer) {
        SQLiteDatabase db = myDatabase.getWritableDatabase();

        for (int i=0; i< newCustomer.size(); i++){
            String sql = "UPDATE "+table+" SET " + _ifsend + "=1 WHERE id = " + newCustomer.get(i).id;
            db.execSQL(sql);
            Log.d("ifsend", "updateIsSend: "+sql);
        }

        db.close();
    }

    public boolean CheckIsSend(int customer) {
        String query = "SELECT * FROM `" +table + "` WHERE customer="+customer;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getInt(cursor.getColumnIndex("ifsend")) == 1)
                    return true;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return false;
    }
}

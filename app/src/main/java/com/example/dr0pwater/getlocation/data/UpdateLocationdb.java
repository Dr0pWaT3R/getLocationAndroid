package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/18/16.
 */

public class UpdateLocationdb {
    private Database myDatabase;
    public static final String table = "updateLocation";
    public static final String _id = "id";
    public static final String _position = "position";
    public static final String _customer = "customer";

    public UpdateLocationdb(Database context) {
        myDatabase = context;
    }
    public UpdateLocationdb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_position+ " text,"
                +_customer+ " int"
                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(UpdateLocation updateLocation) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_customer, updateLocation.customer);
        values.put(_position, updateLocation.position);
        values.put(_id, updateLocation.id);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<UpdateLocation> updateLocations) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
                _customer + "," +
                _position + "," +
                _id + "" +
                " ) VALUES ( ?,?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (UpdateLocation a : updateLocations) {
            stmt.bindLong(1, a.customer);
            stmt.bindString(2, a.position);
            stmt.bindLong(2, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public ArrayList<UpdateLocation> getall() {
        ArrayList<UpdateLocation> updateLocations = new ArrayList<>();

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        UpdateLocation updateLocation = null;
        if (cursor.moveToFirst()) {
            do {

                updateLocation = new UpdateLocation();
                updateLocation.id = cursor.getInt(cursor.getColumnIndex(_id));
                updateLocation.customer = cursor.getInt(cursor.getColumnIndex(_customer));
                updateLocation.position = cursor.getString(cursor.getColumnIndex(_position));
                updateLocations.add(updateLocation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return updateLocations;
    }

    public void insertPosition(int customer, String position){
        ContentValues contentValues = new ContentValues();
        contentValues.put(_customer, customer);
        contentValues.put(_position, position);
        myDatabase.getWritableDatabase().insertOrThrow(table,"", contentValues);

    }
    public int checkThisLocation(int customer){
        String query = "SELECT customer FROM `updateLocation` WHERE customer="+customer;
        int thisCustomer =0;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                thisCustomer = cursor.getInt(cursor.getColumnIndex("customer"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  thisCustomer;
    }
    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }
}

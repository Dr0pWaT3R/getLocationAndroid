package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/17/16.
 */

public class Districtdb {
    private Database myDatabase;
    public static final String table = "district";
    public static final String _id = "id";
    public static final String _city = "city";
    public static final String _name = "name";

    public Districtdb(Database context) {
        myDatabase = context;
    }
    public Districtdb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_name+ " text,"
                +_city+ " int"
                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(District district) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_name, district.name);
        values.put(_city, district.city);
        values.put(_id, district.id);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<District> districts) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
                _name + "," +
                _city+ "," +
                _id + "" +
                " ) VALUES ( ?,?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (District a : districts) {
            stmt.bindString(1, a.name);
            stmt.bindLong(2, a.city);
            stmt.bindLong(3, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public ArrayList<District> getall() {
        ArrayList<District> districts = new ArrayList<>();

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        District district = null;
        if (cursor.moveToFirst()) {
            do {

                district = new District();
                district.id = cursor.getInt(cursor.getColumnIndex(_id));
                district.name = cursor.getString(cursor.getColumnIndex(_name));
                district.city = cursor.getInt(cursor.getColumnIndex(_city));
                districts.add(district);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return districts;
    }
    public String getDistrictName(int pk){
        String query = "SELECT name FROM `district` WHERE id="+pk;
        String districtName="";
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                districtName = cursor.getString(cursor.getColumnIndex("name"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return districtName;
    }
    public int getDistrictId(String name){
        String query = "SELECT id FROM `district` WHERE name='"+ name+ "'";
        int districtId=0;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                districtId = cursor.getInt(cursor.getColumnIndex("id"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return districtId;
    }
    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }
}

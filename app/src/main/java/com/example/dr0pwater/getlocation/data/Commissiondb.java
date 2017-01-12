package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/17/16.
 */

public class Commissiondb {
    private Database myDatabase;
    public static final String table = "commission";
    public static final String _id = "id";
    public static final String _name = "name";
    public static final String _boundary = "boundary";
    public static final String _district = "district";

    public Commissiondb(Database context) {
        myDatabase = context;
    }
    public Commissiondb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_name+ " text,"
                +_boundary+ " text,"
                +_district+ " int"
                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(Commission commission) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_name, commission.name);
        values.put(_boundary, commission.boundary);
        values.put(_district, commission.district);
        values.put(_id, commission.id);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<Commission> commissions) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
                _name + "," +
                _boundary + "," +
                _district + "," +
                _id + "" +
                " ) VALUES ( ?,?,?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (Commission a : commissions) {
            stmt.bindString(1, a.name);
            stmt.bindString(2, a.boundary);
            stmt.bindLong(3, a.district);
            stmt.bindLong(4, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public ArrayList<Commission> getall() {
        ArrayList<Commission> commissions = new ArrayList<>();

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Commission commission = null;
        if (cursor.moveToFirst()) {
            do {

                commission = new Commission();
                commission.id = cursor.getInt(cursor.getColumnIndex(_id));
                commission.name = cursor.getString(cursor.getColumnIndex(_name));
                commission.boundary = cursor.getString(cursor.getColumnIndex(_boundary));
                commission.district = cursor.getInt(cursor.getColumnIndex(_district));
                commissions.add(commission);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return commissions;
    }
    public ArrayList<Commission> getCommission(int districtId) {
        ArrayList<Commission> commissions = new ArrayList<>();

        String query = "SELECT  * FROM " + table+ " WHERE district="+districtId;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Commission commission = null;
        if (cursor.moveToFirst()) {
            do {

                commission = new Commission();
                commission.id = cursor.getInt(cursor.getColumnIndex(_id));
                commission.name = cursor.getString(cursor.getColumnIndex(_name));
                commission.boundary = cursor.getString(cursor.getColumnIndex(_boundary));
                commission.district = cursor.getInt(cursor.getColumnIndex(_district));
                commissions.add(commission);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return commissions;
    }
    public String getCommissionName (int pk){
        String query = "SELECT name FROM `commission` WHERE id="+pk;
        String commissionName="";
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                commissionName = cursor.getString(cursor.getColumnIndex("name"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return commissionName;
    }
    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }
}

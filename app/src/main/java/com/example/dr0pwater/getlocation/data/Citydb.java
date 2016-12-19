package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/17/16.
 */

public class Citydb {
    private Database myDatabase;
    public static final String table = "city";
    public static final String _id = "id";
    public static final String _name = "name";

    public Citydb(Database context) {
        myDatabase = context;
    }
    public Citydb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_name+ " text"
                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(City city) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_name, city.name);
        values.put(_id, city.id);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<City> cities) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
                _name + "," +
                _id + "" +
                " ) VALUES ( ?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (City a : cities) {
            stmt.bindString(1, a.name);
            stmt.bindLong(2, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public ArrayList<City> getall() {
        ArrayList<City> cities = new ArrayList<>();

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        City city = null;
        if (cursor.moveToFirst()) {
            do {

                city = new City();
                city.id = cursor.getInt(cursor.getColumnIndex(_id));
                city.name = cursor.getString(cursor.getColumnIndex(_name));
                cities.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cities;
    }
    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }
}

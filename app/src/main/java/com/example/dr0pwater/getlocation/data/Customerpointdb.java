package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/16/16.
 */

public class Customerpointdb {
    private Database myDatabase;
    public static final String table = "customerpoint";
    public static final String _id = "id";
    public static final String _city = "city";
    public static final String _duureg = "duureg";
    public static final String _khoroo = "khoroo";
    public static final String _name = "name";
    public static final String _position = "position";

    public Customerpointdb(Database context) {
        myDatabase = context;
    }
    public Customerpointdb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_city+ " text,"
                +_duureg+ " text,"
                +_khoroo+ " text,"
                +_name+ " text"
                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(Customerpoint cusp) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_city, cusp.city);
        values.put(_duureg, cusp.duureg);
        values.put(_khoroo, cusp.khoroo);
        values.put(_name, cusp.name);
        values.put(_position, cusp.position);
        values.put(_id, cusp.id);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<Customerpoint> customerpoints) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
                _city + "," +
                _duureg + "," +
                _khoroo + "," +
                _name + "," +
                _position + "," +
                _id + "" +
                " ) VALUES ( ?,?,?,?,?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (Customerpoint a : customerpoints) {
            stmt.bindString(1, a.city);
            stmt.bindString(2, a.duureg);
            stmt.bindString(3, a.khoroo);
            stmt.bindString(4, a.name);
            stmt.bindString(5, a.position);
            stmt.bindLong(6, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public ArrayList<Customerpoint> getall() {
        ArrayList<Customerpoint> customerpoints = new ArrayList<>();

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Customerpoint customerpoint = null;
        if (cursor.moveToFirst()) {
            do {

                customerpoint = new Customerpoint();
                customerpoint.id = cursor.getInt(cursor.getColumnIndex(_id));
                customerpoint.city = cursor.getString(cursor.getColumnIndex(_city));
                customerpoint.duureg = cursor.getString(cursor.getColumnIndex(_duureg));
                customerpoint.khoroo = cursor.getString(cursor.getColumnIndex(_khoroo));
                customerpoint.name = cursor.getString(cursor.getColumnIndex(_name));
                customerpoint.position = cursor.getString(cursor.getColumnIndex(_position));
                customerpoints.add(customerpoint);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customerpoints;
    }

    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }
}

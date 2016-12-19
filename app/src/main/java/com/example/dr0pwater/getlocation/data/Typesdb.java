package com.example.dr0pwater.getlocation.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

/**
 * Created by dr0pwater on 12/17/16.
 */

public class Typesdb {
    private Database myDatabase;
    public static final String table = "types";
    public static final String _id = "id";
    public static final String _name = "name";

    public Typesdb(Database context) {
        myDatabase = context;
    }
    public Typesdb() {}
    static public String createtable(){
        String CREATE_BOOK_TABLE = "CREATE TABLE "+table+" ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +_name+ " text"
                +" ); " ;
        return CREATE_BOOK_TABLE;
    }
    static public String  droptable(){
        return "DROP TABLE IF EXISTS "+table;
    }
    public void create(Types types) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(_name, types.name);
        values.put(_id, types.id);
        db.insert(table, null, values);
        // close database transaction
        db.close();
    }
    public void create(ArrayList<Types> types) {
        // get reference of the BookDB database
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        String sql = "INSERT OR REPLACE INTO "+table+" (" +
                _name + "," +
                _id + "" +
                " ) VALUES ( ?,?)";
        db.beginTransaction();
        SQLiteStatement stmt = db.compileStatement(sql);
        for (Types a : types) {
            stmt.bindString(1, a.name);
            stmt.bindLong(2, a.id);
            stmt.execute();
            stmt.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public ArrayList<Types> getall() {
        ArrayList<Types> typesies = new ArrayList<>();

        String query = "SELECT  * FROM " + table;
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Types types = null;
        if (cursor.moveToFirst()) {
            do {

                types = new Types();
                types.id = cursor.getInt(cursor.getColumnIndex(_id));
                types.name = cursor.getString(cursor.getColumnIndex(_name));
                typesies.add(types);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return typesies;
    }

    public String getTypesName(int pk){
        String query = "SELECT name FROM `types` WHERE id="+pk;
        String typeName="";
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                typeName = cursor.getString(cursor.getColumnIndex("name"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return typeName;
    }
    public void deleteall() {
        SQLiteDatabase db = myDatabase.getWritableDatabase();
        db.execSQL("delete from " + table);
        db.close();
    }
}

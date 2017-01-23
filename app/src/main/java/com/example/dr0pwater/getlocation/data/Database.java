package com.example.dr0pwater.getlocation.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dr0pwater on 12/16/16.
 */

public class Database extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ApuLocation";
    private static Database sInstance;
    public static synchronized Database getInstance(Context context){
        // Use the application context, which will ensure that you don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new Database(context.getApplicationContext());
        }
        return sInstance;
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTalbes(sqLiteDatabase);
    }
    public void createTalbes(SQLiteDatabase db){
        db.execSQL(Customerdb.createtable());
        db.execSQL(Citydb.createtable());
        db.execSQL(Districtdb.createtable());
        db.execSQL(Commissiondb.createtable());
        db.execSQL(Typesdb.createtable());
        db.execSQL(UpdateLocationdb.createtable());
        db.execSQL(CustomerUpdateInfodb.createtable());
        db.execSQL(NewCustomerdb.createtable());
    }
    public  void dropTables(SQLiteDatabase db){
        db.execSQL(Customerdb.droptable());
        db.execSQL(Citydb.droptable());
        db.execSQL(Districtdb.droptable());
        db.execSQL(Commissiondb.droptable());
        db.execSQL(Typesdb.droptable());
        db.execSQL(UpdateLocationdb.droptable());
        db.execSQL(CustomerUpdateInfodb.droptable());
        db.execSQL(NewCustomerdb.droptable());
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropTables(sqLiteDatabase);
        this.onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }

}

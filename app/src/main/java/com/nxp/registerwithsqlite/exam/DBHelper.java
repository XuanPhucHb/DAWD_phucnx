package com.nxp.registerwithsqlite.exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "EXAM";
    public static final int DB_VERSION = 1;

    public static String TABLE_NAME = "TBL_PRODUCT";
    public static String ID = "id";
    public static String NAME = "name";
    public static String QUANTITY = "quantity";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE Product ( "
                + ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT, "
                + QUANTITY + " INTEGER)";
        sqLiteDatabase.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addProduct(String name, Integer quantity) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(QUANTITY, quantity);
        long idAdd = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (idAdd == -1) {
            return false;
        }
        sqLiteDatabase.close();
        return true;
    }

    public Cursor getProduct() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor c = sqLiteDatabase.rawQuery(sql, null);
        return c;
    }

}

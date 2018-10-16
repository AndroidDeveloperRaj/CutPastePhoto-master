package com.odyssey.apps.cutpastephoto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by OdysseyApps on 2/15/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ForegroundImage.db";

    // Table Names
    private static final String DB_TABLE = "foreground";

    // column names
    private static final String ID = "image_id";
    private static final String IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            IMAGE + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }
    public void addImage(Integer id, byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        //cv.put(ID,    String.valueOf(id));
        cv.put(IMAGE,   image);
        database.insert( DB_TABLE, null, cv );
    }
    public void deleteImage(Integer id)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        int Id = getSpecId(id);
        database.delete(DB_TABLE, ID + "=" + String.valueOf(Id), null);
    }
    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DB_TABLE);
        return numRows;
    }
    public ArrayList<Bitmap> getAllData() {
        ArrayList<Bitmap> array_list = new ArrayList<Bitmap>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from foreground", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            byte image[] = res.getBlob(res.getColumnIndex(IMAGE));
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            array_list.add(bmp);
            res.moveToNext();
        }
        return array_list;
    }
    public int getSpecId(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from foreground", null );
        res.moveToFirst();
        int Id=0;
        int i = 1;
        while(res.isAfterLast() == false){
            if(id==i)
            {
                Id = res.getInt(res.getColumnIndex(ID));
                break;
            }
            res.moveToNext();
            i++;
        }
        return Id;
    }
}

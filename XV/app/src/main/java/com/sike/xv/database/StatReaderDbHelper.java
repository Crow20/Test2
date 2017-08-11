package com.sike.xv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by agritsenko on 08.08.2017.
 */

public class StatReaderDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "StatReader.db";
    private static String TABLE_STAT = "stat";
    private static String TABLE_SETTINGS = "settings";
    private static String TABLE_CACHE = "cache";

    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_STEPS = "steps";


    public StatReaderDbHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STAT_TABLE = "CREATE TABLE "+TABLE_STAT+"("+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT,"
                + KEY_STEPS + " TEXT" + ")";
        db.execSQL(CREATE_STAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_STAT);

        onCreate(db);
    }

     public void addEntry(StatEntryContract entryContract){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, entryContract.get_time()); // Contact Name
        values.put(KEY_STEPS, entryContract.get_steps());// Contact Phone

        // Inserting Row
        db.insert(TABLE_STAT, null, values);
        db.close(); // Closing database connection
    }

    StatEntryContract getEntryContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STAT, new String[] { KEY_ID,
                        KEY_TIME, KEY_STEPS }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        StatEntryContract entryContract = new StatEntryContract(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        // return contact
        return entryContract;
    }

    public List<StatEntryContract> getAllEntries(){
        List<StatEntryContract> statEntryContractList = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+TABLE_STAT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                StatEntryContract entryContract = new StatEntryContract();
                entryContract.set_id(Integer.parseInt(cursor.getString(0)));
                entryContract.set_time(cursor.getString(1));
                entryContract.set_steps(Integer.parseInt(cursor.getString(2)));
                statEntryContractList.add(entryContract);
            }while(cursor.moveToNext());
        }

        return statEntryContractList;
    }

    public int updateEntry(StatEntryContract entryContract){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, entryContract.get_time());
        values.put(KEY_STEPS, entryContract.get_steps());

        return db.update(TABLE_STAT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(entryContract.get_id()) });
    }

    public void deleteEnrty(StatEntryContract entryContract){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STAT, KEY_ID + " = ?",
                new String[] { String.valueOf(entryContract.get_id()) });
        db.close();
    }

    public int getEntryCount(){
        String countQuery = "SELECT  * FROM " + TABLE_STAT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public void executeQueryRequest(SQLiteDatabase db, String query){
        db.execSQL(query);
    }
}

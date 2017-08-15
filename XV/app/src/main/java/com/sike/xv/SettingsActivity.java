package com.sike.xv;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.sike.xv.database.StatEntryContract;
import com.sike.xv.database.StatReaderDbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    Button menuSet;
    StatReaderDbHelper db;

    public static final int color1=0x7f0b0086;
    public static final int color10=0x7f0b008f;
    public static final int color11=0x7f0b0090;
    public static final int color12=0x7f0b0091;
    public static final int color2=0x7f0b0087;
    public static final int color3=0x7f0b0088;
    public static final int color4=0x7f0b0089;
    public static final int color5=0x7f0b008a;
    public static final int color6=0x7f0b008b;
    public static final int color7=0x7f0b008c;
    public static final int color8=0x7f0b008d;
    public static final int color9=0x7f0b008e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        menuSet = (Button) findViewById(R.id.menu_settings);
        final Intent intent = new Intent(this, MainActivity.class);
        db = new StatReaderDbHelper(this);
        //db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS settings");
        //db.executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "CREATE TABLE IF NOT EXISTS settings ( id TEXT PRIMARY KEY, number INTEGER, level INTEGER)");
        //db.addEntryToTable(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "INSERT INTO settings VALUES" +"("+"'"+"1"+"', "+"1, 100)");
        menuSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        if(!(checkSettingsState("game")) && !(checkSettingsState("settings"))){
            db.executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "CREATE TABLE IF NOT EXISTS settings ( id TEXT PRIMARY KEY, number INTEGER, level INTEGER)");
            ContentValues value = new ContentValues();
            value.put("number", 12);
            value.put("level", 1);
            value.put("id", "color");
            db.getWritableDatabase().insert("settings", null, value);
            ContentValues value1 = new ContentValues();
            value1.put("number", 1);
            value1.put("level", 50);
            value1.put("id", "sound");
            db.getWritableDatabase().insert("settings", null, value1);
            db.close();
        }else if(checkSettingsState("settings") && settingsChange()){

        }
//        addSettings();
//        getAllEntries();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case color1:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 1 WHERE id = "+"'"+"color"+"'");
                break;
            case color2:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 2 WHERE id = "+"'"+"color"+"'");
                break;
            case color3:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 3 WHERE id = "+"'"+"color"+"'");
                break;
            case color4:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 4 WHERE id = "+"'"+"color"+"'");
                break;
            case color5:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 5 WHERE id = "+"'"+"color"+"'");
                break;
            case color6:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 6 WHERE id = "+"'"+"color"+"'");
                break;
            case color7:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 7 WHERE id = "+"'"+"color"+"'");
                break;
            case color8:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 8 WHERE id = "+"'"+"color"+"'");
                break;
            case color9:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 9 WHERE id = "+"'"+"color"+"'");
                break;
            case color10:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 10 WHERE id = "+"'"+"color"+"'");
                break;
            case color11:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 11 WHERE id = "+"'"+"color"+"'");
                break;
            case color12:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 12 WHERE id = "+"'"+"color"+"'");
                break;
        }
    }

    public void addSettings(){
        //db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS settings");

    }


    public boolean checkSettingsState(String tableName){

        SQLiteDatabase db = this.db.getWritableDatabase();
        if (tableName == null || db == null)
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public boolean settingsChange(){
        ArrayList<Integer> list = getAllEntries();
        for(Integer val:list){
            if(val == 12 || val == 1 || val == 50){
            }else{
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> getAllEntries(){
        ArrayList<Integer> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM settings";

        SQLiteDatabase db = this.db.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                list.add(Integer.parseInt(cursor.getString(1)));
                list.add(Integer.parseInt(cursor.getString(2)));
            }while(cursor.moveToNext());
        }
        return list;
    }

    public void updateSettings(String id){

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}


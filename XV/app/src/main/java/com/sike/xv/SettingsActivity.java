package com.sike.xv;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sike.xv.database.StatReaderDbHelper;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener{

    Toolbar toolbar;
    Button menuSet;
    StatReaderDbHelper db;
    RadioGroup soundChooser;
    SeekBar seekBar;
    Button soundOff;
    int tmpEntry = 0;
    MediaPlayer mp = null;

    int level;
    boolean clicked = false;
    private final String TAG = "SettingsStates";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private FirebaseAnalytics mFirebaseAnalytics;


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
        menuSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        soundChooser = (RadioGroup) findViewById(R.id.sound_chooser);
        soundChooser.setOnCheckedChangeListener(this);
        seekBar = (SeekBar) findViewById(R.id.soundLevel);
        seekBar.setOnSeekBarChangeListener(this);
        soundOff = (Button) findViewById(R.id.sound);
        soundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> list = getAllEntries();
                if(clicked){
                    soundOff.setBackground(getResources().getDrawable(R.drawable.ic_volume_up_black_36dp));
                    seekBar.setProgress(list.get(3));
                    clicked = false;
                }else{
                    soundOff.setBackground(getResources().getDrawable(R.drawable.ic_volume_off_black_36dp));
                    seekBar.setProgress(0);
                    clicked = true;
                }
            }
        });
        if(!(checkSettingsState("settings"))){
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
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        buttonClick.setDuration(500L);
        Log.d(TAG, "SettingsActivity: onCreate()");
    }



    @Override
    public void onClick(View v) {
        setClicked(v);
        switch (v.getId()){
            //v.startAnimation(buttonClick);
            case R.id.color1:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 1 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color2:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 2 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color3:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 3 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color4:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 4 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color5:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 5 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color6:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 6 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color7:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 7 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color8:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 8 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color9:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 9 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color10:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 10 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color11:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 11 WHERE id = "+"'"+"color"+"'");
                break;
            case R.id.color12:
                v.startAnimation(buttonClick);
                db.getWritableDatabase().execSQL("UPDATE settings SET level = 1 WHERE id = "+"'"+"color"+"'");
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 12 WHERE id = "+"'"+"color"+"'");
                break;
        }
    }



    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.sound1:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 1 WHERE id = "+"'"+"sound"+"'");
                break;
            case R.id.sound2:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 2 WHERE id = "+"'"+"sound"+"'");
                break;
            case R.id.sound3:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 3 WHERE id = "+"'"+"sound"+"'");
                break;
            case R.id.sound4:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 4 WHERE id = "+"'"+"sound"+"'");
                break;
            case R.id.sound5:
                db.getWritableDatabase().execSQL("UPDATE settings SET number = 5 WHERE id = "+"'"+"sound"+"'");
                break;
        }
        if(clicked){
            mp = MediaPlayer.create(this, getResources().getIdentifier("sound_" + getAllEntries().get(2), "raw", this.getPackageName()));
            mp.setVolume((float) seekBar.getProgress()/100 ,(float) seekBar.getProgress()/100);
            mp.start();
            Toast.makeText(this, "Music playing", Toast.LENGTH_SHORT).show();
        }

//        if(!(mp.isPlaying())) mp.release();
//        if(mp == null){
//
//        }else {
//            mp = MediaPlayer.create(this, getResources().getIdentifier("sound_" + getAllEntries().get(2), "raw", this.getPackageName()));
//        }

    }

    public void imageButtonOnClick(View v){
        startActivity(new Intent("android.intent.action.CROP"));
        db.getWritableDatabase().execSQL("UPDATE settings SET number = 12 WHERE id = "+"'"+"color"+"'");
        db.getWritableDatabase().execSQL("UPDATE settings SET level = 0 WHERE id = "+"'"+"color"+"'");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        ArrayList<Integer> list = getAllEntries();
        if(seekBar.getProgress() != list.get(3)){
            level = seekBar.getProgress();
            if(level != 0 && !(clicked)){
                db.getWritableDatabase().execSQL("UPDATE settings SET level = "+seekBar.getProgress()+" WHERE id = "+"'"+"sound"+"'");
            }else if(level == 0){
                db.getWritableDatabase().execSQL("UPDATE settings SET level = "+seekBar.getProgress()+" WHERE id = "+"'"+"sound"+"'");
            }

        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(seekBar.getProgress() == 0){
            clicked = true;
            soundOff.setBackground(getResources().getDrawable(R.drawable.ic_volume_off_black_36dp));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        clicked = false;
        soundOff.setBackground(getResources().getDrawable(R.drawable.ic_volume_up_black_36dp));
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

    public void updateSettings(){
        ArrayList<Integer> list = getAllEntries();
        RadioButton b = (RadioButton)findViewById(getResources().getIdentifier("sound"+list.get(2), "id", this.getPackageName()));
        b.setChecked(true);
        if(list.get(3) != 0){
            seekBar.setProgress(list.get(3));
        }else{
            clicked = true;
            soundOff.setBackground(getResources().getDrawable(R.drawable.ic_volume_off_black_36dp));
            seekBar.setProgress(0);
        }
        Button btn;
        btn = (Button)findViewById(getResources().getIdentifier("color"+list.get(0), "id", this.getPackageName()));
        Drawable backgroundRes = btn.getBackground();
        Drawable drawableRes = getResources().getDrawable(R.drawable.border);
        Drawable[] drawableLayers = { backgroundRes, drawableRes };
        LayerDrawable ld = new LayerDrawable(drawableLayers);
        btn.setBackgroundDrawable(ld);
        //btn.setBackground(getResources().getDrawable(R.drawable.image_button_back));
//        btn.setTextSize(20f);
//        btn.setHint("+");
    }

    private void setClicked(View v){
        if(!(v.equals(findViewById(getResources().getIdentifier("color"+getAllEntries().get(0), "id", this.getPackageName()))))){
            Button btn = (Button) v;
            Drawable backgroundRes = btn.getBackground();
            Drawable drawableRes = getResources().getDrawable(R.drawable.border);
            Drawable[] drawableLayers = { backgroundRes, drawableRes };
            LayerDrawable ld = new LayerDrawable(drawableLayers);
            btn.setBackgroundDrawable(ld);
//            btn.setTextColor(Color.WHITE);
            Button tmpBtn = (Button) findViewById(getResources().getIdentifier("color"+getAllEntries().get(0), "id", this.getPackageName()));
            if(getAllEntries().get(0) != 12){
                tmpBtn.setBackground(getResources().getDrawable(getResources().getIdentifier("color_"+getAllEntries().get(0), "drawable", this.getPackageName())));
            }else{
                tmpBtn.setBackgroundColor(Color.parseColor("#ff757575"));
            }

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        updateSettings();
        App.settingsActivity = this;
        Log.d(TAG, "SettingsActivity: onStart()");

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "SettingsActivity: onDestroy()");
        super.onDestroy();
        App.settingsActivity = null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        clicked = true;
        Log.d(TAG, "SettingsActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "SettingsActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "SettingsActivity: onStop()");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}


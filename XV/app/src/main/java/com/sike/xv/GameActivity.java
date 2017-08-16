package com.sike.xv;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sike.xv.database.DataBaseAdapter;
import com.sike.xv.database.StatEntryContract;
import com.sike.xv.database.StatReaderDbHelper;
import com.sike.xv.engine.Plate;
import com.sike.xv.manager.ColumnEnum;
import com.sike.xv.manager.GameManager;
import com.sike.xv.manager.RowEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    AbsoluteLayout absoluteLayout;
    int width = 0;
    int height = 0;
    int pixel = 87;
    protected int[] coorX = {ColumnEnum.FIRST_COLUMN.getValue(), ColumnEnum.SECOND_COLUMN.getValue(), ColumnEnum.THIRD_COLUMN.getValue(), ColumnEnum.FOURTH_COLUMN.getValue()};
    protected int[] coorY = {RowEnum.FIRST_ROW.getValue(), RowEnum.SECOND_ROW.getValue(), RowEnum.THIRD_ROW.getValue(), RowEnum.FOURTH_ROW.getValue()};
    protected static float density;
    Toolbar toolbar;
    TextView steps;
    TextView time;
    TextView best_time;
    ImageView pausePic;
    Button menuGame;
    Button pause;
    Button start;
    MediaPlayer mp;


    static GameManager manager;
    AlertDialog.Builder adb;
    private Plate[][] plates;
    final String TAG = "States";
    final String DB_TAG = "Datebase";
    long MillisecondTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    private long mTime = 0L;
    static boolean gamePaused ,gameStarted ,play ,newgame= false;
    boolean off = false;
    final int DIALOG_EXIT = 1;
    int games = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        density = this.getResources().getDisplayMetrics().density;
        width = (int) (pixel * density);
        height = (int) (pixel * density);
        newgame = false;

        steps = (TextView) findViewById(R.id.steps);
        time = (TextView) findViewById(R.id.time);
        menuGame = (Button) findViewById(R.id.menuGame);
        pause = (Button) findViewById(R.id.pause);
        pausePic = (ImageView) findViewById(R.id.pausePic);
        start = (Button) findViewById(R.id.start);
        best_time = (TextView) findViewById(R.id.best_time);
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.absoluteLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar_game);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        manager = new GameManager();
        manager.createDB(this);
        //plates = manager.setTestFields(this, manager.getPlatesNum());
        adb = new AlertDialog.Builder(this);
        plates = manager.setFields(this, manager.getPlatesNum());
        //manager.saveGameState(plates);
        //manager.setGame(true);
        addButtons();
//        setFonts();
        handler = new Handler();

        //manager.getDb().getEntries("cache");
       // adapter.dataBase();
        Log.d(TAG, "GameActivity: onCreate()");
    }

    void addButtons() {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    plates[i][j].getBtn().setLayoutParams(new AbsoluteLayout.LayoutParams(width, height, (int)(coorX[j] * density), (int) (coorY[i] * density)));
                    plates[i][j].getBtn().setText(String.valueOf(plates[i][j].getNumber()));
                    plates[i][j].getBtn().setBackgroundColor(getResources().getColor(R.color.colorButton));
                    plates[i][j].getBtn().getBackground().setAlpha(64);
                    plates[i][j].getBtn().setTextColor(Color.WHITE);
                    plates[i][j].getBtn().setTextSize(9*density);
                    //plates[i][j].getBtn().setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                    plates[i][j].getBtn().setOnClickListener(this);
                    //absoluteLayout.addView(plates[i][j].getBtn());
                    if(plates[i][j].getNumber() != 0){
                        absoluteLayout.addView(plates[i][j].getBtn());
                    }
                }
            }
        }


    @Override
    public void onClick(View v) {
        manager.setGame(true);
        //Log.d(TAG, "manager.isGame()"+manager.isGame());
        if (manager.move(v.getX(), v.getY(), density) && manager.isGame()) {
            steps.setText(String.valueOf(manager.getCountSteps()));
            manager.buttonAnimator(v, v.getX(), coorX[manager.getX()] * density, v.getY(), coorY[manager.getY()] * density, manager.getDir());
            if(!(off)){
                mp.start();
                if(mp.isPlaying()){
                    mp.seekTo(0);
                }
            }
            if(mTime == 0L){
                mTime = SystemClock.uptimeMillis()-MillisecondTime;
                handler.removeCallbacks(timer);
                handler.postDelayed(timer, 0);
            }
//            if(inst == -1){
//                StartTime = SystemClock.uptimeMillis();
//                handler.postDelayed(timer, 0);
//                //handler.runAfterDelay(timer, 0);
//            }
        }
        if(manager.checkGameOver()){
            //timerStopped = true;
            manager.setGame(false);
            manager.getDb().addEntry(new StatEntryContract(games, String.valueOf(time.getText()), manager.getCountSteps()));
            handler.removeCallbacks(timer);
            games++;
            newgame = true;
            manager.getDb().executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "DROP TABLE IF EXISTS cache");
            manager.getDb().executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "DROP TABLE IF EXISTS game");
            manager.getDb().executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "DROP TABLE IF EXISTS value");
            //onCreateDialog(1);
            showDialog(DIALOG_EXIT);
        }
//        manager.getDb().executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null),"CREATE TABLE cache(first INTEGER PRIMARY KEY, two INTEGER, three INTEGER, four INTEGER )");
        //manager.getDb().executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "DROP TABLE cache");

    }

    public void onButtonsClick(View v){
        switch (v.getId()){
            case R.id.menuGame:
                Intent intent = new Intent(this, MainActivity.class);
                if(manager.isGame()){
                    moveTaskToBack(true);
                    intent.putExtra("game", manager.isGame());
                    startActivity(intent);
                }else{
                    intent.putExtra("new game", true);
                    startActivity(intent);
                }
                break;
            case R.id.sound:
                if(!(off)){
                    v.setBackground(getResources().getDrawable(R.drawable.ic_volume_up_black_36dp));
                    off = true;
                }else{
                    v.setBackground(getResources().getDrawable(R.drawable.ic_volume_off_black_36dp));
                    off = false;
                }
                break;
            case R.id.pause:
                if(!play){
                    pausePic.setVisibility(View.VISIBLE);
                    pause.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_36dp));
                    absoluteLayout.setVisibility(View.INVISIBLE);
                    absoluteLayout.setClickable(false);
                    play = true;
                    handler.removeCallbacks(timer);
                    gamePaused = true;
                    gameStarted = true;

                    //handler.pause();
                }else{
                    pausePic.setVisibility(View.INVISIBLE);
                    pause.setBackground(getResources().getDrawable(R.drawable.ic_pause_black_36dp));
                    absoluteLayout.setVisibility(View.VISIBLE);
                    absoluteLayout.setClickable(true);
                    gamePaused = false;
                    play = false;
                    if(gameStarted && manager.isGame()){
                        mTime = SystemClock.uptimeMillis()-MillisecondTime;
                        handler.postDelayed(timer, 0);
                    }
                    //handler.resume();
                }
                //Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
                break;
            case R.id.restart:
                //manager.setToDefault();
                gameStarted = false;
                play = false;
                gamePaused = false;
                manager.getDb().executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "DROP TABLE IF EXISTS cache");
                manager.getDb().executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "DROP TABLE IF EXISTS value");
                MillisecondTime = 0L;
                recreate();
                //Toast.makeText(getApplicationContext(), "Restart", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_EXIT) {
            return new AlertDialog.Builder(this).setTitle("Игра закончена")
                    .setMessage("Поздравляем! Вы окончили игру за "+time.getText()+" минут(у) и сделали "+manager.getCountSteps()+" ходов.")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setNeutralButton(R.string.yes, myClickListener)
                    .create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_NEUTRAL:
                    //absoluteLayout.setOnClickListener(null);
                    break;
            }
        }
    };



    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        if(id == DIALOG_EXIT){
            ((AlertDialog) dialog).setMessage("Поздравляем! Вы окончили игру за "+time.getText()+" минут(у) и сделали "+manager.getCountSteps()+" ходов.");
        }
        super.onPrepareDialog(id, dialog);
    }

    public Runnable timer = new Runnable() {
        @Override
        public void run() {
            final long start = mTime;
            //Log.d(TAG, " mTime="+mTime);
            MillisecondTime = SystemClock.uptimeMillis() - start;
            Log.d("Timer", " MillisecondTime="+MillisecondTime);
//            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (MillisecondTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            time.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds));
            handler.postDelayed(this, 0);
            }
        };

    private void setFonts(){
        int color = 0;
        if(checkState("settings")){
            SQLiteDatabase db = manager.getDb().getWritableDatabase();
            Cursor cursor = db.query("settings", new String[] { "id",
                            "number", "level" }, "id" + "=?",
                    new String[] { "color" }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            color = Integer.parseInt(cursor.getString(1));
            if(color == 12){
                absoluteLayout.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
            }else{
                absoluteLayout.setBackground(getResources().getDrawable(getResources().getIdentifier("font_"+color, "drawable", this.getPackageName())));
            }
        }
    }

    private void setSounds(){
        int sound = 0;
        int level = 0;
        mp = new MediaPlayer();
        if(checkState("settings")){
            SQLiteDatabase db = manager.getDb().getWritableDatabase();
            Cursor cursor = db.query("settings", new String[] { "id",
                            "number", "level" }, "id" + "=?",
                    new String[] { "sound",}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            sound = Integer.parseInt(cursor.getString(1));
            level = Integer.parseInt(cursor.getString(2));
            mp = MediaPlayer.create(this, getResources().getIdentifier("sound_"+sound, "raw", this.getPackageName()));
            mp.setVolume((float)1-level ,(float)1-level);
        }
    }

    private void setBestTime(){
        List<StatEntryContract> list = manager.getDb().getAllEntries();
        if(list.size() != 0){
            Collections.sort(list, new Comparator<StatEntryContract>() {
                @Override
                public int compare(StatEntryContract o1, StatEntryContract o2) {
                    return String.valueOf(o1.get_time()).compareTo(String.valueOf(o2.get_time()));
                }
            });
            best_time.setText(list.get(0).get_time());
        }else{
            best_time.setVisibility(View.INVISIBLE);
        }

    }

    private void getLastTime(){
        if(checkState("value")){
            SQLiteDatabase db = manager.getDb().getWritableDatabase();
            Cursor cursor = db.query("value", new String[] {
                            "time", "steps" }, "rowid" + "=?",
                    new String[] { "1"}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            MillisecondTime = Integer.parseInt(cursor.getString(0));
            steps.setText(cursor.getString(1));
            Seconds = (int) (MillisecondTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            time.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds));
        }
    }

    public boolean checkState(String tableName){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null);
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

    @Override
    protected void onStart() {
        super.onStart();
        App.gameActivity = this;
        if(manager.isGame() && !gamePaused){
            getLastTime();
            mTime = SystemClock.uptimeMillis()-MillisecondTime;
            handler.postDelayed(timer, 0);
        }
        if(newgame){
            recreate();
        }
        setFonts();
        setSounds();
        setBestTime();
        Log.d(TAG, "GameActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "GameActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(timer);
        Log.d(TAG, "GameActivity: onPause()");
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(timer);
        manager.getDb().getWritableDatabase().execSQL("DROP TABLE IF EXISTS value");
        manager.getDb().getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS value(time REAL, steps INTEGER)");
        ContentValues values = new ContentValues();
        values.put("time", MillisecondTime);
        values.put("steps", manager.getCountSteps());
        manager.getDb().getWritableDatabase().insert("value", null, values);
        manager.getDb().getWritableDatabase().close();
        super.onStop();
        Log.d(TAG, "GameActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.gameActivity = null;
        Log.d(TAG, "GameActivity: onDestroy()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}









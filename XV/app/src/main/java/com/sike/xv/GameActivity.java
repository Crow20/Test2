package com.sike.xv;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sike.xv.engine.Plate;
import com.sike.xv.manager.ColumnEnum;
import com.sike.xv.manager.GameManager;
import com.sike.xv.manager.RowEnum;


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
    ImageView pausePic;
    Button menuGame;
    Button pause;
    TimerTask timerTask;
    static GameManager manager;
    //ArrayList<Plate> plates = new ArrayList<>();
    private Plate[][] plates;
    final String TAG = "States";
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    static boolean timeStarted = true;
    static boolean timerStopped = false;
    final int DIALOG_EXIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        density = this.getResources().getDisplayMetrics().density;
        width = (int) (pixel * density);
        height = (int) (pixel * density);

        steps = (TextView) findViewById(R.id.steps);
        time = (TextView) findViewById(R.id.time);
        menuGame = (Button) findViewById(R.id.menuGame);
        pause = (Button) findViewById(R.id.pause);
        pausePic = (ImageView) findViewById(R.id.pausePic);
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.absoluteLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar_game);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        manager = new GameManager();
        //plates = manager.setTestFields(this, manager.getPlatesNum());
        plates = manager.setFields(this, manager.getPlatesNum());
        //manager.saveGameState(plates);
        //manager.setGame(true);
        addButtons();
        handler = new Handler();
        timerTask = new TimerTask();
        Log.d(TAG, "GameActivity: onCreate()");
    }

    void addButtons() {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    plates[i][j].getBtn().setLayoutParams(new AbsoluteLayout.LayoutParams(width, height, (int)(coorX[j] * density), (int) (coorY[i] * density)));
                    plates[i][j].getBtn().setText(String.valueOf(plates[i][j].getNumber()));
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
        if (manager.move(v.getX(), v.getY(), density)) {
            steps.setText(String.valueOf(manager.getCountSteps()));
            manager.buttonAnimator(v, v.getX(), coorX[manager.getX()] * density, v.getY(), coorY[manager.getY()] * density, manager.getDir());
//            if(timeStarted){
//                StartTime = SystemClock.uptimeMillis();
//                //timerTask.execute();
//            }
        }
        if(manager.checkGameOver()){
            //timerStopped = true;
            showDialog(DIALOG_EXIT);
        }
    }

    public void onButtonsClick(View v){
        switch (v.getId()){
            case R.id.menuGame:
                Toast.makeText(getApplicationContext(), "Menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sound:
                Toast.makeText(getApplicationContext(), "Sound", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pause:
//                try {
//                    timer.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                pausePic.setVisibility(View.VISIBLE);
                pause.setBackground(getResources().getDrawable(R.drawable.ic_play_arrow_black_36dp));
                absoluteLayout.setVisibility(View.INVISIBLE);
                absoluteLayout.setClickable(false);
                Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
                break;
            case R.id.restart:
                //manager.setToDefault();
                recreate();
                //Toast.makeText(getApplicationContext(), "Restart", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    protected Dialog onCreateDialog(int id){
        if (id == DIALOG_EXIT) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Игра закончена");
            adb.setMessage( "Поздравляем! Вы окончили игру за "+time.getText()+" минут(у) и сделали "+manager.getCountSteps()+" ходов.");
            adb.setIcon(android.R.drawable.ic_dialog_info);
            adb.setNeutralButton(R.string.yes, myClickListener);
            return adb.create();
        }
        return super.onCreateDialog(id);
    }

    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        }
    };


   public Runnable timer = new Runnable() {
        @Override
        public void run() {
            if(timerStopped){
                time.setText("0:00");
                steps.setText("0");
                manager.setGame(false);
            }else {
                timeStarted = false;
                MillisecondTime = SystemClock.uptimeMillis() - StartTime;
                UpdateTime = TimeBuff + MillisecondTime;
                Seconds = (int) (UpdateTime / 1000);
                Minutes = Seconds / 60;
                Seconds = Seconds % 60;
                MilliSeconds = (int) (UpdateTime % 1000);
                time.setText("" + Minutes + ":"
                        + String.format("%02d", Seconds));
            }
        }
    };

     private class TimerTask extends AsyncTask<Void, Integer, Void>{

         @Override
         protected void onPostExecute(Void aVoid) {
             super.onPostExecute(aVoid);
         }

         @Override
         protected void onProgressUpdate(Integer... values) {
             super.onProgressUpdate(values);
             Minutes = Seconds / 60;
             Seconds = Seconds % 60;
             time.setText("" + Minutes + ":"
                     + String.format("%02d", Seconds));
         }

         @Override
         protected void onPreExecute() {
             super.onPreExecute();

         }

         @Override
         protected Void doInBackground(Void... params) {

             MillisecondTime = SystemClock.uptimeMillis() - StartTime;
             UpdateTime = TimeBuff + MillisecondTime;
             Seconds = (int) (UpdateTime / 1000);
             publishProgress(Seconds);
             try {
                 Thread.sleep(1);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             return null;
         }
     }

    @Override
    protected void onStart() {
        super.onStart();
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
        Log.d(TAG, "GameActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "GameActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "GameActivity: onDestroy()");
    }
}









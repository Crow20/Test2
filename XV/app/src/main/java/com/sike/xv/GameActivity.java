package com.sike.xv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sike.xv.engine.Plate;
import com.sike.xv.manager.ColumnEnum;
import com.sike.xv.manager.GameManager;
import com.sike.xv.manager.RowEnum;

import java.util.ArrayList;

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
    Button menuGame;
    static GameManager manager;
    ArrayList<Plate> plates = new ArrayList<>();
    final String TAG = "States";

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
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.absoluteLayout);
        Log.d("AbsoluteLayout", "height= " + String.valueOf(absoluteLayout.getLayoutParams().height));
        Log.d("AbsoluteLayout", "width= " + String.valueOf(absoluteLayout.getLayoutParams().width));
        toolbar = (Toolbar) findViewById(R.id.toolbar_game);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        manager = new GameManager();
        plates = manager.setFields(this, manager.getPlatesNum());
        manager.saveGameState(plates);
        manager.setGame(true);
        addButtons();
        Log.d(TAG, "GameActivity: onCreate()");
    }

    void addButtons() {
        if(manager.isGame()){
            plates = manager.getPlates();
        }else {
            plates = manager.getCurstate();
        }
        int i = 0;
        int j = 0;
        for(Plate obj:plates){
            if(!(obj.getNumber() == 0)){
                obj.getBtn().setLayoutParams(new AbsoluteLayout.LayoutParams(width, height, (int)(coorX[j] * density), (int) (coorY[i] * density)));
                obj.getBtn().setText(String.valueOf(obj.getNumber()));
                obj.getBtn().setOnClickListener(this);
                absoluteLayout.addView(obj.getBtn());
                j++;
                if(j > 3 && i != 3){
                    i++;
                    j = 0;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (manager.move(v.getX(), v.getY(), density)) {
            steps.setText(String.valueOf(manager.getCountSteps()));
            manager.buttonAnimator(v, v.getX(), coorX[manager.getX()] * density, v.getY(), coorY[manager.getY()] * density, manager.getDir());
            //timer();
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
                Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_SHORT).show();
                break;
            case R.id.restart:
                Toast.makeText(getApplicationContext(), "Restart", Toast.LENGTH_SHORT).show();
                break;
        }
    }
//    public void timer(){
//        int seconds = 0, minutes = 0;
//        while (true) {
//            seconds++;
//            if (minutes != 0)
//                time.setText(minutes + ":" + seconds); // текст в JLabel
//            //System.out.print(minutes + ":");
//            //System.out.println(seconds);
//            if (seconds == 59) {
//                seconds = -1;
//                minutes++;
//            }
//        }
//    }

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









package com.sike.xv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Toast;

import com.sike.xv.engine.Plate;
import com.sike.xv.manager.ColumnEnum;
import com.sike.xv.manager.Direction;
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
    static GameManager manager;
    ArrayList<Plate> plates = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        density = this.getResources().getDisplayMetrics().density;
        width = (int) (pixel * density);
        height = (int) (pixel * density);
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
        if(manager.move(v.getX(), v.getY(), density))
            manager.buttonAnimator(v, v.getX(), coorX[manager.getX()]*density, v.getY(), coorY[manager.getY()]*density, manager.getDir());
        }

    }









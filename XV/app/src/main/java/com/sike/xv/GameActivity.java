package com.sike.xv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;

import com.sike.xv.manager.ColumnEnum;
import com.sike.xv.manager.RowEnum;

import org.xmlpull.v1.XmlPullParser;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    AbsoluteLayout absoluteLayout;
    int width = 0;
    int height = 0;
    int pixel = 75;
    protected int [] coorX = {ColumnEnum.FIRST_COLUMN.getValue(), ColumnEnum.SECOND_COLUMN.getValue(), ColumnEnum.THIRD_COLUMN.getValue(), ColumnEnum.FOURTH_COLUMN.getValue()};
    protected int [] coorY = {RowEnum.FIRST_ROW.getValue(), RowEnum.SECOND_ROW.getValue(), RowEnum.THIRD_ROW.getValue(), RowEnum.FOURTH_ROW.getValue()};
    protected static float density;
    Button one;
    Button two;
    Button seven;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        density = this.getResources().getDisplayMetrics().density;
        width = (int)(pixel*density);
        height = (int)(pixel*density);
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.absoluteLayout);
        Log.d("AbsoluteLayout" ,"height= "+String.valueOf(absoluteLayout.getLayoutParams().height));
        Log.d("AbsoluteLayout" ,"width= "+String.valueOf(absoluteLayout.getLayoutParams().width));
        //addButtons();
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);

    }

    void addButtons(){
        for(int i = 0; i < 4;i++){
            for(int j = 0; j < 4; j++){
                Button btnTag = new Button(absoluteLayout.getContext());
                int x = (int)(coorX[j]*density);
                int y = (int)(coorY[i]*density);
                btnTag.setLayoutParams(new AbsoluteLayout.LayoutParams(width, height, (int)(coorX[j]*density), (int)(coorY[i]*density)));
                Log.d("AbsoluteLayout", "i="+(i+1)+" j="+(j+1)+" x="+String.valueOf(x)+" y="+String.valueOf(y)+" Size="+String.valueOf(width));
                btnTag.setText("1");
                absoluteLayout.addView(btnTag);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.one:
                break;
            case R.id.two:
                break;
            case R.id.three:
                break;
            case R.id.four:
                break;
            case R.id.five:
                break;
            case R.id.six:
                break;
            case R.id.seven:
                break;
        }
    }
}

package com.sike.xv;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
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
    protected int[] coorX = {ColumnEnum.FIRST_COLUMN.getValue(), ColumnEnum.SECOND_COLUMN.getValue(), ColumnEnum.THIRD_COLUMN.getValue(), ColumnEnum.FOURTH_COLUMN.getValue()};
    protected int[] coorY = {RowEnum.FIRST_ROW.getValue(), RowEnum.SECOND_ROW.getValue(), RowEnum.THIRD_ROW.getValue(), RowEnum.FOURTH_ROW.getValue()};
    protected static float density;
    Button one;
    Button two;
    Button seven;
    Button three;
    Animation anim = null;
    ValueAnimator animator;
    Toolbar toolbar;


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
        //addButtons();
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        seven = (Button) findViewById(R.id.seven);
        three.setOnClickListener(this);
        one.setOnClickListener(this);
        seven.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_game);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    void addButtons() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button btnTag = new Button(absoluteLayout.getContext());
                int x = (int) (coorX[j] * density);
                int y = (int) (coorY[i] * density);
                btnTag.setLayoutParams(new AbsoluteLayout.LayoutParams(width, height, (int) (coorX[j] * density), (int) (coorY[i] * density)));
                Log.d("AbsoluteLayout", "i=" + (i + 1) + " j=" + (j + 1) + " x=" + String.valueOf(x) + " y=" + String.valueOf(y) + " Size=" + String.valueOf(width));
                btnTag.setText("1");
                absoluteLayout.addView(btnTag);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one:
                anim = AnimationUtils.loadAnimation(this, R.anim.animation_left);
                v.startAnimation(anim);
                break;
            case R.id.two:
                anim = new TranslateAnimation(v.getX(), 140 * density, v.getY(), 0);
                v.startAnimation(anim);
                break;
            case R.id.three:
//                v.animate();
//                anim = new TranslateAnimation(v.getX(),  140*density, v.getY(),  40*density);
//                v.setAnimation(anim);
//                v.startAnimation(anim);
//                anim = AnimationUtils.loadAnimation(this, R.anim.animation_left);
//                v.startAnimation(anim);
               //translationXAnimator(v.getX(), (int) (140*density), v, 3000);
                ObjectAnimator.ofFloat(v, View.X, v.getX(), 140*density).start();
                break;
            case R.id.four:
                break;
            case R.id.five:
                break;
            case R.id.six:
                break;
            case R.id.seven:
                ObjectAnimator.ofFloat(v, View.Y, v.getY(), 134*density).start();
                break;
        }
    }

    public static Animator translationXAnimator(final float startX, int end, final View view, long duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.X, end);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setTranslationX(startX);
            }
        });
        return animator;
    }
}




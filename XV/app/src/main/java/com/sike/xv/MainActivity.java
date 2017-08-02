package com.sike.xv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sike.xv.manager.GameManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button start;
    Button stat;
    Button pref;
    Button exit;
    Button continuegame;
    final String TAG = "States";
    private static boolean game = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        stat = (Button) findViewById(R.id.stat);
        pref = (Button) findViewById(R.id.pref);
        exit = (Button) findViewById(R.id.exit);
        continuegame = (Button) findViewById(R.id.continuegame);

        exit.setOnClickListener(this);
        if(continuegame.isEnabled()){
            continuegame.setOnClickListener(this);
        }
        pref.setOnClickListener(this);
        start.setOnClickListener(this);
        stat.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Log.d(TAG, "MainActivity: onCreate()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                game = true;
                startActivity(new Intent("android.intent.action.START"));
                break;
            case R.id.stat:
                startActivity(new Intent("android.intent.action.STAT"));
                break;
            case R.id.pref:
                startActivity(new Intent("android.intent.action.PREFERENCE"));
                break;
            case R.id.exit:
                this.finish();
                break;
            case R.id.continuegame:
                break;
        }
    }

    @Override
    protected void onRestart() {
        if(game){
            continuegame.setEnabled(true);
            continuegame.setVisibility(View.VISIBLE);
        }
        super.onRestart();
       // Log.d(TAG, "MainActivity: onRestart()");
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d(TAG, "MainActivity: onStart()");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "MainActivity: onResume()");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d(TAG, "MainActivity: onPause()");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "MainActivity: onStop()");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "MainActivity: onDestroy()");
//    }

}

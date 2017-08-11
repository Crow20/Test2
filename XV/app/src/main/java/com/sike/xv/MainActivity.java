package com.sike.xv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sike.xv.database.StatReaderDbHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button start;
    Button stat;
    Button pref;
    Button exit;
    Intent intent;
    StatReaderDbHelper db;
    boolean exitApp = false;

    final String TAG = "States";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        stat = (Button) findViewById(R.id.stat);
        pref = (Button) findViewById(R.id.pref);
        exit = (Button) findViewById(R.id.exit);

        exit.setOnClickListener(this);
        pref.setOnClickListener(this);
        start.setOnClickListener(this);
        stat.setOnClickListener(this);
        intent = getIntent();
        if (intent.getBooleanExtra("game", false)) {
            start.setText("Продолжить");
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        db = new StatReaderDbHelper(this);
        //db.

        Log.d(TAG, "MainActivity: onCreate()");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                if (intent.getBooleanExtra("game", false)) {
                    Intent intent = new Intent(this, GameActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    break;
                } else {
                    startActivity(new Intent("android.intent.action.START"));
                }
                break;
            case R.id.stat:
                startActivity(new Intent("android.intent.action.STAT"));
                break;
            case R.id.pref:
                startActivity(new Intent("android.intent.action.PREFERENCE"));
                break;
            case R.id.exit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Выход:Вы уверены?")
                        .setCancelable(false)
                        .setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                App.close();
                                exitApp = true;
                                onStart();
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create();
                dialog.show();
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(exitApp){
            this.finish();
        }
        Log.d(TAG, "MainActivity: onStart()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        Log.d(TAG, "MainActivity: onDestroy()");

    }
}

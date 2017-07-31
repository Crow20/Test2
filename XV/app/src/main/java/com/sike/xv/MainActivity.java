package com.sike.xv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sike.xv.manager.GameManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button start;
    Button stat;
    Button pref;
    Button exit;
    Button continuegame;
    GameManager manager;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        stat = (Button) findViewById(R.id.stat);
        pref = (Button) findViewById(R.id.pref);
        exit = (Button) findViewById(R.id.exit);
        //continuegame = (Button) findViewById(R.id.continuegame);

        exit.setOnClickListener(this);
        if(continuegame != null){
            continuegame.setOnClickListener(this);
        }
        pref.setOnClickListener(this);
        start.setOnClickListener(this);
        stat.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
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
//            case R.id.continuegame:
//                break;
        }
    }

}

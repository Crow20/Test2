package com.sike.xv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.sike.xv.database.DataBaseAdapter;
import com.sike.xv.database.StatEntryContract;
import com.sike.xv.database.StatReaderDbHelper;

import java.util.ArrayList;

public class StatActivity extends AppCompatActivity {

    Toolbar toolbar;
    DataBaseAdapter adapter;
    StatReaderDbHelper db;
    GridView statGV;
    ArrayList<StatEntryContract> list;
    Button menuStat;
    float density;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        density = this.getResources().getDisplayMetrics().density;
        toolbar = (Toolbar) findViewById(R.id.toolbar_stat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        menuStat = (Button) findViewById(R.id.menu_stat);
        db = new StatReaderDbHelper(this);
        list = new ArrayList<>(db.getAllEntries());
        adapter = new DataBaseAdapter(this, list, density);
        statGV = (GridView) findViewById(R.id.statGV);
        statGV.setAdapter(adapter);
        final Intent intent = new Intent(this, MainActivity.class);
        menuStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(new Intent(intent));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}

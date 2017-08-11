package com.sike.xv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.sike.xv.database.StatReaderDbHelper;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button menuSet;
    StatReaderDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        menuSet = (Button) findViewById(R.id.menu_settings);
        final Intent intent = new Intent(this, MainActivity.class);
        db = new StatReaderDbHelper(this);
        db.executeQueryRequest(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "CREATE TABLE IF NOT EXISTS settings ( id TEXT PRIMARY KEY, number INTEGER, level INTEGER)");
        //db.addEntryToTable(getBaseContext().openOrCreateDatabase("StatReader.db", MODE_PRIVATE, null), "INSERT INTO settings VALUES" +"("+"'"+"1"+"', "+"1, 100)");
        menuSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}

package com.sike.xv;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.sike.xv.database.StatEntryContract;
import com.sike.xv.database.StatReaderDbHelper;

import java.util.ArrayList;
import java.util.List;
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
        db = new StatReaderDbHelper(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}

class Griadapter extends BaseAdapter{

    Context ctx;
    LayoutInflater Inflater;
    ArrayList<Button> objects;

    Griadapter(Context context, ArrayList<Button> list ){
        ctx=context;
        objects = list;
        Inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Button getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = Inflater.inflate(R.layout.item, parent, false);
        }
        return view;
    }
}

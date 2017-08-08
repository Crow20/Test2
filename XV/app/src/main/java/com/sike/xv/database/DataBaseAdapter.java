package com.sike.xv.database;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sike.xv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agritsenko on 08.08.2017.
 */

public class DataBaseAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater Inflater;
    ArrayList<StatEntryContract> objects;
    StatReaderDbHelper db;

    public DataBaseAdapter(Context context/*,ArrayList<StatEntryContract> entry*/){
        ctx=context;
        //objects = entry;
        Inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new StatReaderDbHelper(ctx);
        db.addEntry(new StatEntryContract("1:13", 201));
        db.addEntry(new StatEntryContract("1:10", 302));
        db.addEntry(new StatEntryContract("0:13", 101));
        db.addEntry(new StatEntryContract("1:45", 56));
        db.addEntry(new StatEntryContract("2:13", 12));
//        density = context.getResources().getDisplayMetrics().density;
//        paddingDp = (int)(paddingPixel*density);
//        paddingDpTop = (int)(paddingPixelTop*density);
//        green = context.getResources().getColor(R.color.Green);
//        red = context.getResources().getColor(R.color.Red);
//        this.width = width;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = Inflater.inflate(R.layout.item, parent, false);
        }
//        if(view == null){
//            if(width <= 960){
//                Log.d("Screen", "Выбрала меньший макет "+String.valueOf(width));
//                view = Inflater.inflate(R.layout.item_small_width, parent, false);
//            }else{
//                Log.d("Screen", "Выбрала больший макет "+String.valueOf(width));
//                view = Inflater.inflate(R.layout.item_medium_width, parent, false);
//            }

        Object entryContract = getItem(position);


        return view;
    }

    public void dataBase() {
        List<StatEntryContract> entry = db.getAllEntries();
        for (StatEntryContract en : entry) {
            String log = "Id: " + en.get_id() + " ,Time: " + en.get_time() + " ,Steps: " + en.get_steps();
            // Writing Contacts to log
            Log.d("Datebase", log);
        }
    }
}

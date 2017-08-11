package com.sike.xv.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sike.xv.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by agritsenko on 08.08.2017.
 */

public class DataBaseAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater Inflater;
    List<StatEntryContract> objects;
    float density;


    public DataBaseAdapter(Context context, List<StatEntryContract> entry, float value){
        ctx=context;
        objects = entry;
        Inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        density = value;
        Collections.sort(objects, new Comparator<StatEntryContract>() {
            @Override
            public int compare(StatEntryContract o1, StatEntryContract o2) {
                return String.valueOf(o1.get_time()).compareTo(String.valueOf(o2.get_time()));
            }
        });
        if (objects.size() > 10) {
            objects.subList(9, objects.size()).clear();
        }


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
    public StatEntryContract getItem(int position) {
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

        StatEntryContract entryContract = getItem(position);
        if(entryContract.get_id()<=9){
            String tmp;
            tmp = "Игрок " + String.valueOf(entryContract.get_id() + ".");
            ((TextView) view.findViewById(R.id.col1)).setPadding(0, 0, (int)(10*density), 0);
            ((TextView) view.findViewById(R.id.col1)).setText(tmp);
            tmp = "Время: " + entryContract.get_time();
            ((TextView) view.findViewById(R.id.col2)).setText(tmp);
            tmp = "Ходы:" + entryContract.get_steps();
            ((TextView) view.findViewById(R.id.col3)).setText(tmp);
        }else{
            String tmp;
            tmp = "Игрок " + String.valueOf(entryContract.get_id() + ".");
            ((TextView) view.findViewById(R.id.col1)).setText(tmp);
            tmp = "Время: " + entryContract.get_time();
            ((TextView) view.findViewById(R.id.col2)).setText(tmp);
            tmp = "Ходы:" + entryContract.get_steps();
            ((TextView) view.findViewById(R.id.col3)).setText(tmp);
        }


        return view;
    }

//    public void dataBase() {
//        List<StatEntryContract> entry = db.getAllEntries();
//        for (StatEntryContract en : entry) {
//            String log = "Id: " + en.get_id() + " ,Time: " + en.get_time() + " ,Steps: " + en.get_steps();
//            // Writing Contacts to log
//            Log.d("Datebase", log);
//        }
//    }
}

package com.sike.xv.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sike.xv.R;

import java.util.List;

/**
 * Created by agritsenko on 08.08.2017.
 */

public class DataBaseAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater Inflater;
    List<StatEntryContract> objects;


    public DataBaseAdapter(Context context, List<StatEntryContract> entry){
        ctx=context;
        objects = entry;
        Inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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

        String tmp;
        tmp = "Игрок " + String.valueOf(entryContract.get_id() + ".");
        ((TextView) view.findViewById(R.id.col1)).setText(tmp);
        tmp = "Время: " + entryContract.get_time();
        ((TextView) view.findViewById(R.id.col2)).setText(tmp);
        tmp = "Ходы:" + entryContract.get_steps();
        ((TextView) view.findViewById(R.id.col3)).setText(tmp);

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

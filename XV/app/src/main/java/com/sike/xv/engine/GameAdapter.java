package com.sike.xv.engine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.sike.xv.R;

import java.util.ArrayList;

/**
 * Created by agritsenko on 25.07.2017.
 */

public class GameAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Button> objects;
    ArrayList<ArrayList<Integer>> numbers;

    public GameAdapter(Context context, ArrayList<Button> buttons, ArrayList<ArrayList<Integer>> numberslist) {
        ctx = context;
        objects = buttons;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        numbers = numberslist;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }
        return view;
    }
}
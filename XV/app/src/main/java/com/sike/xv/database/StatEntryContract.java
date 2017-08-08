package com.sike.xv.database;

import android.provider.BaseColumns;

/**
 * Created by agritsenko on 08.08.2017.
 */

public final class StatEntryContract {

    private int _id;
    private String _time;
    private int _steps;

    public StatEntryContract(){

    }

    public StatEntryContract(int id, String time, int steps){
        this._id = id;
        this._time = time;
        this._steps = steps;
    }

    public StatEntryContract(String time, int steps){
        this._time = time;
        this._steps = steps;
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_time() {
        return this._time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public int get_steps() {
        return this._steps;
    }

    public void set_steps(int _steps) {
        this._steps = _steps;
    }
}

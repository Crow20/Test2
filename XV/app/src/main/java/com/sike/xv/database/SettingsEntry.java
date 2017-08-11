package com.sike.xv.database;

/**
 * Created by agritsenko on 11.08.2017.
 */

public class SettingsEntry {

    private String _id;
    private int _number;
    private int _level;

    public SettingsEntry(){

    }

    public SettingsEntry(String id, int number, int level){
        this._id = id;
        this._number = number;
        this._level = level;
    }

    public SettingsEntry(int number, int level){
        this._number = number;
        this._level = level;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int get_number() {
        return _number;
    }

    public void set_number(int _number) {
        this._number = _number;
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }
}

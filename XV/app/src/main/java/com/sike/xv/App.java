package com.sike.xv;

import android.app.Activity;

/**
 * Created by agritsenko on 10.08.2017.
 */

public class App {

    public static Activity gameActivity;
    public static Activity cropActivity;
    public static Activity statActivity;
    public static Activity settingsActivity;


    public static void close(){
        if(App.gameActivity != null) App.gameActivity.finish();
        if(App.cropActivity != null) App.cropActivity.finish();
        if(App.settingsActivity != null) App.settingsActivity.finish();
        if(App.statActivity != null) App.statActivity.finish();
    }
}

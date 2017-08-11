package com.sike.xv;

import android.app.Activity;

/**
 * Created by agritsenko on 10.08.2017.
 */

public class App {

    public static Activity gameActivity;

    public static void close(){
        if(App.gameActivity != null) App.gameActivity.finish();
    }
}

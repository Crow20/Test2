package com.sike.xv;

import android.os.Build;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PrefActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }

//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= 16)
//            this.
//    }
}

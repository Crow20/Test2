package com.sike.xv.manager;

import android.inputmethodservice.Keyboard;

/**
 * Created by agritsenko on 25.07.2017.
 */

public enum RowEnum {

    FIRST_ROW(0), SECOND_ROW(87), THIRD_ROW(174), FOURTH_ROW(261);

    private int value;

    RowEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

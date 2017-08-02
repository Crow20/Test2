package com.sike.xv.manager;

/**
 * Created by agritsenko on 25.07.2017.
 */

public enum ColumnEnum {
    FIRST_COLUMN(0), SECOND_COLUMN(87), THIRD_COLUMN(174), FOURTH_COLUMN(261);

    private int value;

    ColumnEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

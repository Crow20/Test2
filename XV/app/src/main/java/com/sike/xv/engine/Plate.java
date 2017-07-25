package com.sike.xv.engine;

import android.widget.Button;

/**
 * Created by agritsenko on 25.07.2017.
 */

public class Plate {
    Button btn;
    int x;
    int y;
    int number;

    Plate(Button btn, int x, int y, int number){
        this.btn = btn;
        this.x = x;
        this.y = y;
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Button getBtn() {
        return btn;
    }

    public int getNumber() {
        return number;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

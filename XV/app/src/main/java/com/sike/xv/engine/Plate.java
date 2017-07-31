package com.sike.xv.engine;

import android.content.Context;
import android.widget.Button;

/**
 * Created by agritsenko on 31.07.2017.
 */

public class Plate {

    Button btn;
    int x;
    int y;
    int number;
    boolean exist = true;

    public Plate(Context ctx, int x, int y, int number){
        this.number = number;
        this.y = y;
        this.x = x;
        this.btn = new Button(ctx);
    }

    public Button getBtn() {
        return btn;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumber() {
        return number;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}

package com.sike.xv.engine;

import android.content.Context;
import android.widget.Button;

import com.sike.xv.manager.Direction;

/**
 * Created by agritsenko on 31.07.2017.
 */

public class Plate {

    Button btn;
    int x;
    int y;
    int number;
    boolean active = true;
    Direction dir;

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

    public boolean isActive() {
    return active;
}

    public void setActive(boolean active) {
        this.active = active;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

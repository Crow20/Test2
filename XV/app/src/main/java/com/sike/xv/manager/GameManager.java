package com.sike.xv.manager;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.sike.xv.engine.Plate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by agritsenko on 25.07.2017.
 */

public class GameManager {

    protected Plate[][] plates = new Plate[4][4];
    private ArrayList<Plate> curstate;
    protected ArrayList<Plate> tmpList = new ArrayList<>();
    protected int[] coorX = {ColumnEnum.FIRST_COLUMN.getValue(), ColumnEnum.SECOND_COLUMN.getValue(), ColumnEnum.THIRD_COLUMN.getValue(), ColumnEnum.FOURTH_COLUMN.getValue()};
    protected int[] coorY = {RowEnum.FIRST_ROW.getValue(), RowEnum.SECOND_ROW.getValue(), RowEnum.THIRD_ROW.getValue(), RowEnum.FOURTH_ROW.getValue()};
    private boolean isGame = false;
    protected int [][] arrPlates = new int[4][4];
    private static int [][] defPlates = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0}};
    int [][] platesNum = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 0, 15}};
    protected Direction dir = Direction.NOTMOVE;
    private int x0;
    private int y0;
    private int x;
    private int y;
    protected int countSteps = 0;


    public Plate[][] setTestFields(Context ctx, int [][] numbers){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                plates[i][j] = new Plate(ctx, j, i, numbers[i][j]);
                arrPlates[i][j] = plates[i][j].getNumber();
            }
        }
        return plates;
    }

    public Plate[][] setFields(Context ctx, int [][] numbers){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                plates[i][j] = new Plate(ctx, j, i, numbers[i][j]);
            }
        }
        for(int i = 0; i < 4; i++){
          for(int j = 0; j < 4; j++){
              tmpList.add(plates[i][j]);
          }
        }
        Collections.shuffle(tmpList);
        int k =0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                plates[i][j] = tmpList.get(k);
                k++;
            }
        }
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                arrPlates[i][j] = plates[i][j].getNumber();
            }
        }
        return plates;
    }

    public int[][] getPlatesNum() {
        return platesNum;
    }

    public Plate[][] getPlates() {
        return plates;
    }

    public void setDirection(int x0, int y0, int x, int y){


    }

    public boolean move(float flX, float flY, float density) {

        boolean res = false;
        // Координаты пустой клетки
        int px0 = -1, py0 = -1;
        int tmpPlate;
        // Ищем пустую клетку на поле
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arrPlates[i][j] == 0) {
                    px0 = j;
                    py0 = i;
                }
            }
        }

        int x = (int) (flX/density);
        int y = (int) (flY/density);

        int tmp = x;
        for(int i = 0; i < 4; i++){
            if(i != 3){
                if(tmp >= coorX[i] && tmp <= coorX[i+1]){
                    if((coorX[i+1]+coorX[i])/2 < x) {
                        x = i+1;

                    }else {
                        x = i;

                    }
                }
            }else if(x == tmp && tmp != 0) {
                x = 3;
            }
        }
        tmp = y;
        for(int j = 0; j < 4; j++) {
            if (j != 3) {
                if (tmp >= coorY[j] && tmp <= coorY[j + 1]) {
                    if ((coorY[j + 1] + coorY[j]) / 2 < y) {
                        y = j + 1;

                    } else {
                        y = j;

                    }
                }
            } else if (y == tmp && tmp != 0) {
                y = 3;
            }
        }
        //tmpPlate[x][y]
        if ((px0 == x || py0 == y) && (Math.abs(px0-x) == 1 || Math.abs(py0-y) == 1)) {
            if (!(px0 == x && py0 == y)) {
                if (px0 == x) {
                    if (py0 < y) {
                        arrPlates[y - 1][x] = arrPlates[y][x];
                        arrPlates[y][x] = 0;
                    } else {
                        arrPlates[y + 1][x] = arrPlates[y][x];
                        arrPlates[y][x] = 0;
                    }
                }
                if (py0 == y) {
                    if (px0 < x) {
                        arrPlates[y][x - 1] = arrPlates[y][x];
                        arrPlates[y][x] = 0;
                    } else {
                        arrPlates[y][x + 1] = arrPlates[y][x];
                        arrPlates[y][x] = 0;
                    }
                }
                //arrPlates[x][y] = 0;
                //проверять наоборот икс и игрек
                if (px0 < x && (x - px0 == 1)) {
                    dir = Direction.RIGHT;
                } else if (px0 > x&& (px0 - x == 1)) {
                    dir = Direction.LEFT;
                } else if (px0 == x) {
                    if (py0 < y && (Math.abs(y-py0) == 1)) {
                        dir = Direction.UP;
                    } else if(py0 > y && Math.abs(y-py0) == 1){
                        dir = Direction.DOWN;
                    }
                }
                res = true;
            } else {
                res = false;
            }
        }
        // Возвращаем результат
        setX(px0);
        setY(py0);
        Log.d("Move", " Move="+String.valueOf(res));
        if(res)countSteps++;
        return res;
    }

    public void buttonAnimator(View v, float curX, float endX, float curY, float endY, Direction dir){
        switch (dir){
            case UP:
                ObjectAnimator.ofFloat(v, View.Y, curY, endY).start();
                break;
            case DOWN:
                ObjectAnimator.ofFloat(v, View.Y, curY, endY).start();
                break;
            case LEFT:
                ObjectAnimator.ofFloat(v, View.X, curX, endX).start();
                break;
            case RIGHT:
                ObjectAnimator.ofFloat(v, View.X, curX, endX).start();
                break;
            case NOTMOVE:
                break;
        }

        //ObjectAnimator.ofFloat(v, View.X, v.getX(), 140*density).start();
    }

    public boolean checkGameOver()
    {
        int a = 1;
        boolean res = true;
        for (int i = 0; i <4; i++) {
            for (int j = 0; j < 4; j++) {
                if(i == 3 && j == 3) a=0;
                if (this.arrPlates[i][j] != a) {
                    res = false;

                }
                a++;
            }
        }
        return res;
    }

    public void saveGameState(ArrayList<Plate> startState){
        curstate = new ArrayList<>();
        curstate = startState;
    }

    public void timer(){

    }

    public int getCountSteps() {
        return countSteps;
    }

//    public ArrayList<Plate> getCurstate() {
//        return curstate;
//    }

    public boolean isGame() {
        return isGame;
    }

    public void setGame(boolean game) {
        isGame = game;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDir() {
        return dir;
    }
}

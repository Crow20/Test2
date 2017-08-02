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

    protected ArrayList<ArrayList<Integer>> tmpList = new ArrayList<>(4);
    protected ArrayList<Plate> plates = new ArrayList<>();
    private ArrayList<Plate> curstate;
    protected int[] coorX = {ColumnEnum.FIRST_COLUMN.getValue(), ColumnEnum.SECOND_COLUMN.getValue(), ColumnEnum.THIRD_COLUMN.getValue(), ColumnEnum.FOURTH_COLUMN.getValue()};
    protected int[] coorY = {RowEnum.FIRST_ROW.getValue(), RowEnum.SECOND_ROW.getValue(), RowEnum.THIRD_ROW.getValue(), RowEnum.FOURTH_ROW.getValue()};
    private boolean isGame = false;
    private static int [][] arrPlates = {{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 0}};
    int [][] platesNum = new int[4][4];
    protected Direction dir = Direction.NOTMOVE;
    private int x0;
    private int y0;
    private int x;
    private int y;
    protected int countSteps = 0;

    protected int [] fieldArray = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,  13, 14, 15, 0};
    int j = 1;

    public GameManager(){
        tmpList.add(new ArrayList<Integer>(4));
        tmpList.add(new ArrayList<Integer>(4));
        tmpList.add(new ArrayList<Integer>(4));
        tmpList.add(new ArrayList<Integer>(4));
        for(ArrayList<Integer> tmp:tmpList){
            for(int i = 0; i < 4 ; i++, j++){
                tmp.add(i,fieldArray[j-1]);
            }
        }
        fieldRandomizer();
    }

    private int[][] fieldRandomizer(){
        ArrayList<Integer> cont= new ArrayList<>();
        for (int i = 0;i < tmpList.size(); i++){
            if(!(cont.isEmpty())){
                cont.addAll(cont.size(), tmpList.get(i));
            }else {
                cont = tmpList.get(i);
            }
        }
        int k = 0;
        for(int m = 0; m < 4;m++){
            for(int n = 0; n < 4; n++, k++){
                platesNum[m][n] = cont.get(k);
            }
        }
        return platesNum;
    }

    public ArrayList<Plate> setFields(Context ctx, int [][] numbers){

        int m = 0;
        int n = 0;
        for (; plates.size() < 16 && m != 4; m++) {
            if (plates.isEmpty()) {
                for (int j = 0; j < 4; j++, n++) {
                    plates.add(j, new Plate(ctx, n, m, numbers[m][n]));
                }
                n = 0;
            } else {
                for (int j = plates.size(); j < 16 && n != 4; j++, n++) {
                    plates.add(j, new Plate(ctx, n, m, numbers[m][n]));
                }
                n = 0;
            }
        }
        Collections.shuffle(plates);
        return plates;
    }

    public int[][] getPlatesNum() {
        return platesNum;
    }

    public ArrayList<Plate> getPlates() {
        return plates;
    }

    public void setDirection(int x0, int y0, int x, int y){
        setX(x);
        setX0(x0);
        setY(y);
        setY0(y0);
        dir = Direction.NOTMOVE;
        if (x0 < x && (x - x0 == 1)) {
            dir = Direction.RIGHT;
        } else if (x0 > x&& (x0 - x == 1)) {
            dir = Direction.LEFT;
        } else if (x0 == x) {
            if (y0 < y && (Math.abs(y-y0) == 1)) {
                dir = Direction.DOWN;
            } else if(Math.abs(y-y0) == 1){
                dir = Direction.UP;
            }
        }
    }

    public boolean move(float flX, float flY, float density) {

        boolean res = false;
        // Координаты пустой клетки
        int px0 = -1, py0 = -1;

        // Ищем пустую клетку на поле
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arrPlates[i][j] == 0) {
                    px0 = i;
                    py0 = j;
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
                    }
                }
            }else if(x == tmp && tmp != 0) {
                x = 3;
            }
        }
        tmp = y;
        for(int j = 0; j < 4; j++){
            if(j != 3){
                if(tmp >= coorY[j] && tmp <= coorY[j+1]){
                    if((coorY[j+1]+coorY[j])/2 < y) {
                        y = j+1;
                    }
                }
            }else if(y == tmp && tmp != 0) {
                y = 3;
            }
        }

        setDirection(x, y, px0, py0);
        // Когда нашли делаем ход, если он возможен
        if(dir != Direction.NOTMOVE){
                if (px0 == x || py0 == y ) {
                    if (!(px0 == x && py0 == y)) {
                        if (px0 == x) {
                            if (py0 < y) {
                                for (int i = py0 + 1; i <= y; i++) {
                                    arrPlates[x][i - 1] = arrPlates[x][i];
                                }
                            } else {
                                for (int i = py0; i > y; i--) {
                                    arrPlates[x][i] = arrPlates[x][i - 1];
                                }
                            }
                        }
                        if (py0 == y) {
                            if (px0 < x) {
                                for (int i = px0 + 1; i <= x; i++) {
                                    arrPlates[i - 1][y] = arrPlates[i][y];
                                }
                            } else {
                                for (int i = px0; i > x; i--) {
                                    arrPlates[i][y] = arrPlates[i - 1][y];
                                }
                            }
                        }
                        arrPlates[x][y] = 0;
                        res = true;
                    } else {
                        res = false;
                    }
                }
        }
        // Возвращаем результат
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
        for (int i = 0; i <4; i++)
            for (int j = 0; j<4; j++)
            {
                if (i==3&&j==3){a=0;}
                if (arrPlates[j][i]!=a)
                {
                    res=false;
                    break;
                }
                a++;
            }
        return res;
    }

    public void saveGameState(ArrayList<Plate> startState){
        curstate = new ArrayList<>();
        curstate = startState;
    }

    public int getCountSteps() {
        return countSteps;
    }

    public ArrayList<Plate> getCurstate() {
        return curstate;
    }

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

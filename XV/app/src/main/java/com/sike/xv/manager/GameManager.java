package com.sike.xv.manager;

import android.animation.ObjectAnimator;
import android.content.Context;
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
    float aX = 0;
    float aY = 0;

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

    public Direction setDirection(){
        //if()
        return Direction.UP;
    }

    public int getActive(){
       return 1;
    }
    public void setActive(int x, int y){
//        if(arrPlates[y][x] == 0 && y != 0 && x != 0){
//            arrPlates[y][x] = 1;
//            arrPlates[y-1][x] = 0;
//            arrPlates[y][x-1] = 0;
//        }else
    }


    public void buttonAnimator(View v, float curX, float endX, float curY, float endY, Direction dir){
        switch (dir){
            case UP:
                ObjectAnimator.ofFloat(v, View.Y, curY, endY+v.getHeight()).start();
                break;
            case DOWN:
                ObjectAnimator.ofFloat(v, View.Y, curY, endY).start();
                break;
            case LEFT:
                ObjectAnimator.ofFloat(v, View.X, curX, endX).start();
                break;
            case RIGHT:
                ObjectAnimator.ofFloat(v, View.X, curX, endX+v.getWidth()).start();
                break;
        }

        //ObjectAnimator.ofFloat(v, View.X, v.getX(), 140*density).start();
    }


    public void saveGameState(ArrayList<Plate> startState){
        curstate = new ArrayList<>();
        curstate = startState;
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
}

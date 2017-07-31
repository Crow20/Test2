package com.sike.xv.manager;

import android.content.Context;

import com.sike.xv.engine.Plate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by agritsenko on 25.07.2017.
 */

public class GameManager {

    protected ArrayList<ArrayList<Integer>> tmpList = new ArrayList<>(4);
    protected ArrayList<Plate> plates = new ArrayList<>();
    int [][] platesNum = new int[4][4];

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

    public ArrayList<Plate> setFields(Context ctx, int [][] numbers, boolean game){
        if(game){
            int m = 0;
            int n = 0;
            for(; plates.size() < 16 && m != 4;m++){
                if(plates.isEmpty()){
                    for (int j = 0; j < 4; j++, n++){
                        plates.add(j, new Plate(ctx, n, m, numbers[m][n]));
                    }
                    n = 0;
                }else{
                    for (int j = plates.size(); j < 16 && n != 4; j++, n++){
                        plates.add(j, new Plate(ctx, n, m, numbers[m][n]));
                    }
                    n = 0;
                }
            }
            Collections.shuffle(plates);
            return plates;
        }
        return null;
    }

    public int[][] getPlatesNum() {
        return platesNum;
    }

    public ArrayList<Plate> getPlates() {
        return plates;
    }

    void setEmptyFields(ArrayList<Plate> list){

    }

    void setNoExist(Plate obj){
        obj.setExist(false);
    }

}

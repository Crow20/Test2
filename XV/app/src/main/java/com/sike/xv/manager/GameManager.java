package com.sike.xv.manager;

import com.sike.xv.engine.Plate;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by agritsenko on 25.07.2017.
 */

public class GameManager {

    protected ArrayList<ArrayList<Integer>> list = new ArrayList<>(4);

    protected int [] fieldArray = {0, 1, 2, 3, 4, 0, 5, 6, 7, 8, 0, 9, 10, 11, 12, 0, 13, 14, 15 };
    int j = 1;

    public GameManager(){
        list.add(new ArrayList<Integer>(5));
        list.add(new ArrayList<Integer>(5));
        list.add(new ArrayList<Integer>(5));
        list.add(new ArrayList<Integer>(5));
        for(ArrayList<Integer> tmp:list){
            for(int i = 0; i < 5 && j != 20 ; i++, j++){
                tmp.add(i,fieldArray[j-1]);
            }
        }
        fieldRandomizer();
    }

    void fieldRandomizer(){
        Collections.shuffle(list);
        Collections.shuffle(list.get(0));
        Collections.shuffle(list.get(1));
        Collections.shuffle(list.get(2));
        Collections.shuffle(list.get(3));
    }
}

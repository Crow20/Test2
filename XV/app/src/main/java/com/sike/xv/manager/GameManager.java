package com.sike.xv.manager;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.sike.xv.GameActivity;
import com.sike.xv.database.StatReaderDbHelper;
import com.sike.xv.engine.Plate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
    protected static StatReaderDbHelper db;
    List<int [][]> tmpList1 = new ArrayList<>();

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
        if(!(checkGameState("cache"))){
            db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS cache");
            db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS game");
            db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS game(first INTEGER, two INTEGER, three INTEGER, four INTEGER)");
            for(int i = 0; i < 4; i++){
                ContentValues values = new ContentValues();
                values.put("first", arrPlates[i][0]);
                values.put("two", arrPlates[i][1]);
                values.put("three", arrPlates[i][2]);
                values.put("four", arrPlates[i][3]);
                db.getWritableDatabase().insert("game", null, values);
                db.getWritableDatabase().close();
            }
        }else if(checkGameState("game")){
            int i = 0;
            int j = 0;
            //tmpList1 = db.getEntries("cache");
            arrPlates = db.getEntries("cache").get(0);
            for(Plate[] pl:plates){
                for(Plate obj:pl){
                    if(j == 4) j = 0;
                    obj.setNumber(arrPlates[i][j]);
                    if(j < 4 ) j++;
                }
                if(i < 3){
                    i++;
                }else {
                    break;
                }
            }
        }
        return plates;
    }

    public int[][] getPlatesNum() {
        return platesNum;
    }

    public GameManager getGameManager(){
        return this;
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
        if ((px0 == x || py0 == y) && (Math.abs(px0-x) == 1 || Math.abs(py0-y) == 1)&& this.isGame()) {
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
        if(res){
            countSteps++;
            updateCacheGame();
        }
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

    public void updateCacheGame(){
        db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS cache");
        for(int i = 0; i < 4; i++){
            db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS cache(first INTEGER, two INTEGER, three INTEGER, four INTEGER, cached BOOLEAN)");
            ContentValues values = new ContentValues();
            values.put("first", arrPlates[i][0]);
            values.put("two", arrPlates[i][1]);
            values.put("three", arrPlates[i][2]);
            values.put("four", arrPlates[i][3]);
            values.put("cached", true);
            db.getWritableDatabase().insert("cache", null, values);
            db.getWritableDatabase().close();
        }
        db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS value(time REAL, steps INTEGER)");


//        int i = 0;
//        int j = 0;
//        String columnIndex = null;
//        for(; i < 4; i++){
//            for(; j < 4; j++){
//                if(arrPlates[i][j] == 0) break;
//            }
//        }
//        switch (j){
//            case 0:
//                columnIndex = "one";
//                break;
//            case 1:
//                columnIndex = "two";
//                break;
//            case 2:
//                columnIndex = "three";
//                break;
//            case 3:
//                columnIndex = "four";
//                break;
//        }
//        String sqlQuery = "UPDATE sache SET "+columnIndex+"="+arrPlates[getX()][getY()]+" WHERE rowid="+getY();
//        db.getWritableDatabase().execSQL(sqlQuery);
//        sqlQuery = "UPDATE sache SET "+columnIndex+"="+arrPlates[getX()][getY()]+" WHERE rowid="+getY();
//        db.getWritableDatabase().execSQL(sqlQuery);
    }

    public boolean checkGameState(String tableName){

        SQLiteDatabase db = getDb().getWritableDatabase();
        if (tableName == null || db == null)
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public void createDB(Context ctx){
        db = new StatReaderDbHelper(ctx);
    }


    public static StatReaderDbHelper getDb() {
        return db;
    }

    public static void setDb(StatReaderDbHelper db) {
        GameManager.db = db;
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

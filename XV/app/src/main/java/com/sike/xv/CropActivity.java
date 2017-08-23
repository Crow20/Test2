package com.sike.xv;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sike.xv.manager.GameManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class CropActivity extends AppCompatActivity implements View.OnClickListener{

    Button crop;
    Button save;

    ImageView sourceImage;
    private final int REQ_CODE_PICK_IMAGE = 2;

    float density = 0;
    private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
    GameManager manager;

    ArrayList<Bitmap> chunkedImage;

    // Number of rows and columns in chunked image
    int rows, cols;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        density = this.getResources().getDisplayMetrics().density;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crop);

        sourceImage = (ImageView) findViewById(R.id.source_image);
        crop = (Button) findViewById(R.id.crop);
        save = (Button) findViewById(R.id.save);
        crop.setOnClickListener(this);
        save.setOnClickListener(this);
        manager = new GameManager();
        ImageCropFunction();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.crop:
                ImageCropFunction();
                break;
            case R.id.save:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        File tempFile = getTempFile();
                        String filePath = Environment.getExternalStorageDirectory()
                                + "/" + TEMP_PHOTO_FILE;
                        Log.d("Files" ,"path: " + filePath);
                        manager.getDb().getWritableDatabase().execSQL("DROP TABLE IF EXISTS file");
                        manager.getDb().getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS file(id TEXT PRIMARY KEY, path TEXT)");
                        ContentValues values = new ContentValues();
                        values.put("id", "filepath");
                        values.put("path", filePath);
                        manager.getDb().getWritableDatabase().insert("file", null, values);
                        manager.getDb().close();
                        Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                        sourceImage.setImageBitmap(selectedImage);
                        //splitImage(filePath, chunkSideLength);
                        if (tempFile.exists()) tempFile.delete();
                    }
                }
                break;
        }
    }

    public void ImageCropFunction() {
        // Image Crop Code
        try {
            Intent CropIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //CropIntent.setDataAndType(uri, "image/*");
            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 350);
            CropIntent.putExtra("outputY", 350);
            CropIntent.putExtra("aspectX", 1);
            CropIntent.putExtra("aspectY", 1);
            CropIntent.putExtra("scaleIfNeeded", true);
            CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
            CropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(CropIntent, REQ_CODE_PICK_IMAGE);
        } catch (ActivityNotFoundException e) {

        }
    }
    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
        if (isSDCARDMounted()) {

            File f = new File(Environment.getExternalStorageDirectory(),TEMP_PHOTO_FILE);
            try {
                f.createNewFile();
            } catch (IOException e) {

            }
            return f;
        } else {
            return null;
        }
    }

    private boolean isSDCARDMounted(){
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED))
            return true;
        return false;
    }

    private void splitImage(String path, int chunkSideLength) {

        int higherChunkSide, widerChunkSide;

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        rows = bitmap.getHeight() / chunkSideLength;
        higherChunkSide = bitmap.getHeight() / rows;
        cols = bitmap.getWidth() / chunkSideLength;
        widerChunkSide = bitmap.getWidth() / cols;

        chunkedImage = new ArrayList<Bitmap>(rows * cols);

        if (higherChunkSide != chunkSideLength) {
            if (widerChunkSide != chunkSideLength) {
                int yCoord = 0;
                for (int y = 0; y < rows; ++y) {
                    int xCoord = 0;
                    for (int x = 0; x < cols; ++x) {
                        chunkedImage.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkSideLength, chunkSideLength));
                        xCoord += chunkSideLength;
                    }
                    yCoord += chunkSideLength;
                }
            }
        }
        //mergeImage(chunkedImage, 350, 350);
    }

    void mergeImage(ArrayList<Bitmap> imageChunks, int width, int height) {

        Collections.shuffle(imageChunks);

        // create a bitmap of a size which can hold the complete image after merging
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);

        // create a canvas for drawing all those small images
        Canvas canvas = new Canvas(bitmap);
        int count = 0;
        Bitmap currentChunk = imageChunks.get(0);

        //Array of previous row chunks bottom y coordinates
        int[] yCoordinates = new int[cols];
        Arrays.fill(yCoordinates, 0);

        for (int y = 0; y < rows; ++y) {
            int xCoord = 0;
            for (int x = 0; x < cols; ++x) {
                currentChunk = imageChunks.get(count);
                canvas.drawBitmap(currentChunk, xCoord, yCoordinates[x], null);
                xCoord += currentChunk.getWidth();
                yCoordinates[x] += currentChunk.getHeight();
                count++;
            }
        }

        sourceImage.setImageBitmap(bitmap);
    }
}

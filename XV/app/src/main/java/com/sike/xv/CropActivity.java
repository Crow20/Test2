package com.sike.xv;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.sike.xv.database.StatReaderDbHelper;
import com.sike.xv.manager.GameManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class CropActivity extends AppCompatActivity implements View.OnClickListener{

    Button crop;
    Button save;
    ImageView sourceImage;

    private final int REQ_CODE_PICK_IMAGE = 2;
    float density = 0;
    private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";
    GameManager manager;
    StatReaderDbHelper db;
    boolean execute = false;

    Intent intent;


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
        db = new StatReaderDbHelper(this);
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission_group.CAMERA}, REQ_CODE_PICK_IMAGE);
        if (Build.VERSION.SDK_INT >= 23){
            if(isStoragePermissionGranted()) ImageCropFunction();
        }else{
            ImageCropFunction();
        }


        intent = new Intent(this, SettingsActivity.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.crop:
                ImageCropFunction();
                break;
            case R.id.save:
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_PICK_IMAGE:
                if (data != null) {
                    String filePath = this.getFilesDir()
                            + "/" + TEMP_PHOTO_FILE;
                    Log.d("Files", "path: " + filePath);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap = Bitmap.createScaledBitmap(bitmap, 350, 350, true);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] bitmapdata = bos.toByteArray();
                    File f = new File(filePath);
                    try {
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(bitmapdata);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    db.getWritableDatabase().execSQL("DROP TABLE IF EXISTS file");
                    db.getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS file(id TEXT PRIMARY KEY, path TEXT)");
                    ContentValues values = new ContentValues();
                    values.put("id", "filepath");
                    values.put("path", filePath);
                    db.getWritableDatabase().insert("file", null, values);
                    db.getWritableDatabase().close();
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    sourceImage.setImageBitmap(selectedImage);
                    execute = true;
                    //splitImage(filePath, chunkSideLength);
                    //if (tempFile.exists()) tempFile.delete();
                }
                break;
        }
    }

    public void ImageCropFunction() {
        // Image Crop Code

        //
        try {
            Intent CropIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //CropIntent.setDataAndType(uri, "image/*");
            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 350);
            CropIntent.putExtra("outputY", 350);
            CropIntent.putExtra("aspectX", 1);
            CropIntent.putExtra("aspectY", 1);
            CropIntent.putExtra("scale", true);
            CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
            CropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            CropIntent.putExtra("return-data", true);
            startActivityForResult(CropIntent, REQ_CODE_PICK_IMAGE);
        } catch (ActivityNotFoundException e) {

        }
    }
    private Uri getTempUri() {
        return Uri.fromFile(getTempFile());
    }

    private File getTempFile() {
        //File tempFile = getTempFile();
        //if (tempFile.exists()) tempFile.delete();
        File f = new File(this.getFilesDir(), TEMP_PHOTO_FILE);
        if(f.exists()) f.getAbsoluteFile();
        return f;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED&&!execute){
            ImageCropFunction();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.cropActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.cropActivity = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}

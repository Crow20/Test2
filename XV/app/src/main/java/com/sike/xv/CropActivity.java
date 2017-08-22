package com.sike.xv;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
    Uri selectedImage;
    private final int RESULT_LOAD_IMAGE = 1;
    private final int REQ_CODE_PICK_IMAGE = 2;
    int chunkSideLength = 75;
    float density = 0;
    public static final int RequestPermissionCode  = 1 ;
    private static final String TEMP_PHOTO_FILE = "temporary_holder.jpg";

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
        //EnableRuntimePermission();
        //alertDialogForCameraImage();
        ImageCropFunction();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.crop:
                //alertDialogForCameraImage();
                //ImageCropFunction();
//                Intent CropIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                CropIntent.putExtra("crop", "true");
//                CropIntent.putExtra("outputX", 350);
//                CropIntent.putExtra("outputY", 350);
//                CropIntent.putExtra("aspectX", 1);
//                CropIntent.putExtra("aspectY", 1);
//                CropIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
//                CropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//                startActivityForResult(CropIntent, REQ_CODE_PICK_IMAGE);
                break;
            case R.id.save:
                break;
        }
    }

    void pickImageFromGallery() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // startActivityForResult(pickPhoto , 0);
        startActivityForResult(pickPhoto, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    // takenPictureData = handleResultFromChooser(data);
                    selectedImage = data.getData();
                    //ImageCropFunction(selectedImage);


                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null,
                            null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    // ImageView imageView = (ImageView) findViewById(R.id.imgView);
                    //sourceImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    //sourceImage.setBackground(BitmapDrawable.createFromPath(picturePath));
//                    Bitmap picture = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
//                     Bitmap picture = BitmapFactory.decodeFile(picturePath);
                    //sourceImage.setImageBitmap(Bitmap.createScaledBitmap(picture, (int) (350*density), (int) (350*density), false));
                    //sourceImage.setImageBitmap(getResizedBitmap(BitmapFactory.decodeFile(picturePath), (int) (350*density), (int) (350*density)));
                    sourceImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    // Function of split the image(divide the image into pieces)
                    //sourceImage.setImageBitmap( BitmapFactory.decodeFile(picturePath));
                    //splitImage(picturePath,(int) (chunkSideLength*density));
                }
                break;
            case REQ_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {

                        File tempFile = getTempFile();

                        String filePath = Environment.getExternalStorageDirectory()
                                + "/" + TEMP_PHOTO_FILE;
                        System.out.println("path " + filePath);


                        Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                        sourceImage.setImageBitmap(selectedImage);

                        if (tempFile.exists()) tempFile.delete();
                    }
                }
                break;
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
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

    public void alertDialogForCameraImage() {
        AlertDialog.Builder adb = new AlertDialog.Builder(CropActivity.this);
        adb.setTitle("Pick Image From Gallery: ");
        adb.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                pickImageFromGallery();

            }
        });
        adb.show();
    }

    /**
     * Splits the source image and show them all into a grid in a new activity
     *
     * @param image
     *            The source image to split
     * @param chunkSideLength
     *            Image parts side length
     */
    private void splitImage(String path, int chunkSideLength) {
        Random random = new Random(System.currentTimeMillis());

        // height and weight of higher|wider chunks if they would be
        int higherChunkSide, widerChunkSide;

        // Getting the scaled bitmap of the source image
//        Drawable bitDraw = image.getBackground();
//        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        Bitmap bitmap = BitmapFactory.decodeFile(path);

        rows = bitmap.getHeight() / chunkSideLength;
        higherChunkSide = bitmap.getHeight() % chunkSideLength + chunkSideLength;

        cols = bitmap.getWidth() / chunkSideLength;
        widerChunkSide = bitmap.getWidth() % chunkSideLength + chunkSideLength;

        // To store all the small image chunks in bitmap format in this list
        chunkedImage = new ArrayList<Bitmap>(rows * cols);

        if (higherChunkSide != chunkSideLength) {
            if (widerChunkSide != chunkSideLength) {
                // picture has both higher and wider chunks plus one big square chunk

                ArrayList<Bitmap> widerChunks = new ArrayList<Bitmap>(rows - 1);
                ArrayList<Bitmap> higherChunks = new ArrayList<Bitmap>(cols - 1);
                Bitmap squareChunk;

                int yCoord = 0;
                for (int y = 0; y < rows - 1; ++y) {
                    int xCoord = 0;
                    for (int x = 0; x < cols - 1; ++x) {
                        chunkedImage.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkSideLength, chunkSideLength));
                        xCoord += chunkSideLength;
                    }
                    // add last chunk in a row to array of wider chunks
                    widerChunks.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, widerChunkSide, chunkSideLength));

                    yCoord += chunkSideLength;
                }

                // add last row to array of higher chunks
                int xCoord = 0;
                for (int x = 0; x < cols - 1; ++x) {
                    higherChunks.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkSideLength, higherChunkSide));
                    xCoord += chunkSideLength;
                }

                //save bottom-right big square chunk
                squareChunk = Bitmap.createBitmap(bitmap, xCoord, yCoord, widerChunkSide, higherChunkSide);

                //shuffle arrays
//                Collections.shuffle(chunkedImage);
//                Collections.shuffle(higherChunks);
//                Collections.shuffle(widerChunks);

                //determine random position of big square chunk
                int bigChunkX = random.nextInt(cols);
                int bigChunkY = random.nextInt(rows);

                //add wider and higher chunks into resulting array of chunks
                //all wider(higher) chunks should be in one column(row) to avoid collisions between chunks
                //We must insert it row by row because they will displace each other from their columns otherwise
                for (int y = 0; y < rows - 1; ++y) {
                    chunkedImage.add(cols * y + bigChunkX, widerChunks.get(y));
                }

                //And then we insert the whole row of higher chunks
                for (int x = 0; x < cols - 1; ++x) {
                    chunkedImage.add(bigChunkY * cols + x, higherChunks.get(x));
                }

                chunkedImage.add(bigChunkY * cols + bigChunkX, squareChunk);
            } else {
                // picture has only number of higher chunks

                ArrayList<Bitmap> higherChunks = new ArrayList<Bitmap>(cols);

                int yCoord = 0;
                for (int y = 0; y < rows - 1; ++y) {
                    int xCoord = 0;
                    for (int x = 0; x < cols; ++x) {
                        chunkedImage.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkSideLength, chunkSideLength));
                        xCoord += chunkSideLength;
                    }
                    yCoord += chunkSideLength;
                }

                // add last row to array of higher chunks
                int xCoord = 0;
                for (int x = 0; x < cols; ++x) {
                    higherChunks.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkSideLength, higherChunkSide));
                    xCoord += chunkSideLength;
                }

                //shuffle arrays
//                Collections.shuffle(chunkedImage);
//                Collections.shuffle(higherChunks);

                //add higher chunks into resulting array of chunks
                //Each higher chunk should be in his own column to preserve original image size
                //We must insert it row by row because they will displace each other from their columns otherwise
                List<Point> higherChunksPositions = new ArrayList<Point>(cols);
                for (int x = 0; x < cols; ++x) {
                    higherChunksPositions.add(new Point(x, random.nextInt(rows)));
                }

                //sort positions of higher chunks. THe upper-left elements should be first
                Collections.sort(higherChunksPositions, new Comparator<Point>() {
                    @Override
                    public int compare(Point lhs, Point rhs) {
                        if (lhs.y != rhs.y) {
                            return lhs.y < rhs.y ? -1 : 1;
                        } else if (lhs.x != rhs.x) {
                            return lhs.x < rhs.x ? -1 : 1;
                        }
                        return 0;
                    }
                });

                for (int x = 0; x < cols; ++x) {
                    Point currentCoord = higherChunksPositions.get(x);
                    chunkedImage.add(currentCoord.y * cols + currentCoord.x, higherChunks.get(x));
                }

            }
        } else {
            if (widerChunkSide != chunkSideLength) {
                // picture has only number of wider chunks

                ArrayList<Bitmap> widerChunks = new ArrayList<Bitmap>(rows);

                int yCoord = 0;
                for (int y = 0; y < rows; ++y) {
                    int xCoord = 0;
                    for (int x = 0; x < cols - 1; ++x) {
                        chunkedImage.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkSideLength, chunkSideLength));
                        xCoord += chunkSideLength;
                    }
                    // add last chunk in a row to array of wider chunks
                    widerChunks.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, widerChunkSide, chunkSideLength));

                    yCoord += chunkSideLength;
                }

                //shuffle arrays
//                Collections.shuffle(chunkedImage);
//                Collections.shuffle(widerChunks);

                //add wider chunks into resulting array of chunks
                //Each wider chunk should be in his own row to preserve original image size
                for (int y = 0; y < rows; ++y) {
                    chunkedImage.add(cols * y + random.nextInt(cols), widerChunks.get(y));
                }

            } else {
                // picture perfectly splits into square chunks
                int yCoord = 0;
                for (int y = 0; y < rows; ++y) {
                    int xCoord = 0;
                    for (int x = 0; x < cols; ++x) {
                        chunkedImage.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkSideLength, chunkSideLength));
                        xCoord += chunkSideLength;
                    }
                    yCoord += chunkSideLength;
                }

//                Collections.shuffle(chunkedImage);
            }
        }

        // Function of merge the chunks images(after image divided in pieces then i can call this function to combine
        // and merge the image as one)
        mergeImage(chunkedImage, bitmap.getWidth(), bitmap.getHeight());
    }

    void mergeImage(ArrayList<Bitmap> imageChunks, int width, int height) {

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

    /*
     * The result image is shown in a new Activity
     */

        sourceImage.setImageBitmap(bitmap);
    }

    public void EnableRuntimePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(CropActivity.this,
                Manifest.permission.CAMERA))
        {
            Toast.makeText(CropActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(CropActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(CropActivity.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CropActivity.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}

package com.example.aggarwals.cameradn2;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Camera camera;
    String filePath;
    FrameLayout framelayout;
    ShowCamera showCamera;
    final int camera_capture = 1;

    private static final int REQUEST_IMGAGE_CAPTURE = 188;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        framelayout=(FrameLayout)findViewById(R.id.frameLayout);
        camera= Camera.open();
        showCamera=new ShowCamera(this,camera);
        framelayout.addView(showCamera);

    }
    Camera.PictureCallback mPictureCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            // Store bitmap to local storage
            FileOutputStream out = null;
            try {
                // Prepare file path to store bitmap
                // This will create Pictures/MY_APP_NAME_DIR/
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "MY_APP_NAME_DIR");
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
//                        Log.d(Constants.LOG_TAG, "failed to create directory");
//                        return null;
                    }
                }

                // Bitmap will be stored at /Pictures/MY_APP_NAME_DIR/YOUR_FILE_NAME.jpg
                 filePath = mediaStorageDir.getPath() + File.separator + "YOUR_FILE_NAME.jpg";

                out = new FileOutputStream(filePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };



    public void captureImage(View v){

        if (camera!=null){
            camera.takePicture(null,null,mPictureCallback);
//intt();
        }
    }

public void intt(){
    Intent i = new Intent(this, display.class);
    i.putExtra("FILE_PATH", filePath);
    startActivity(i);

}





}

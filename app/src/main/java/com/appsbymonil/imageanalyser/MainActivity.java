 package com.appsbymonil.imageanalyser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import java.io.InputStream;

 public class MainActivity extends AppCompatActivity {
     private static final String TAG="MainActivity";

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream  inputStream = getResources().openRawResource(R.raw.m);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

         FaceDetector detector = new FaceDetector.Builder(getApplicationContext())
                 .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                 .setTrackingEnabled(false)
                 .setMode(FaceDetector.FAST_MODE)
                 .build();

         Frame frame = new Frame.Builder().setBitmap(bitmap).build();

         SparseArray<Face> faces = detector.detect(frame);

         if(!detector.isOperational()){
             Log.w(TAG , "Face detector dependencies is not available");
             return;
         }

         MyView myView = (MyView)findViewById(R.id.my_view);

         myView.setContent(bitmap , faces);

         detector.release();


    }
}

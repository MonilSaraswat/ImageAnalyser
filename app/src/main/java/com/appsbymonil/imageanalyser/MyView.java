package com.appsbymonil.imageanalyser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

/**
 * Created by hp 15 on 3/16/2018.
 */

public class MyView extends View{
    private SparseArray<Face> mFaces ;
    private Bitmap mbitmap;

    public MyView(Context context , AttributeSet attrs){

        super(context , attrs);
    }

    void setContent(Bitmap bitmap , SparseArray<Face> faces){
        mbitmap = bitmap;
        mFaces = faces;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if((mbitmap!= null) && (mFaces != null)){
            double viewWidth = canvas.getWidth();
            double viewHeight = canvas.getHeight();

            double imageWidth = mbitmap.getWidth();
            double imageHeight = mbitmap.getHeight();

            double scale = Math.min(viewWidth/imageWidth , viewHeight/imageHeight);

            Rect destBounds = new Rect( 0 , 0 , (int)(imageWidth*scale) , (int)(imageHeight*scale));

            canvas.drawBitmap(mbitmap , null , destBounds , null);

            Paint paint = new Paint();

            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            int i;
            for( i = 0 ; i<mFaces.size() ; i++){
                Face face = mFaces.valueAt(i);
                int x = (int)(face.getPosition().x + scale);
                int y = (int)(face.getPosition().y + scale);

                int width = (int)(face.getWidth()*scale) + x ;
                int height = (int)(face.getHeight()*scale) + y ;

                canvas.drawRect(x , y , width , height , paint);

                for(Landmark landmark : face.getLandmarks()){

                    int cx = (int)(landmark.getPosition().x + scale) ;
                    int xy = (int)(landmark.getPosition().y + scale);
                    canvas.drawCircle(cx,xy, 5 , paint);





                }

            }
            Toast.makeText(getContext(), i+ "  Faces Detected" , Toast.LENGTH_LONG).show();


        }
    }
}

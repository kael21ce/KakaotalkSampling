package org.techtown.kakaotalksampling;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ScaleItem extends View {

    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Paint mPaint;

    public ScaleItem(Context context) {
        super(context);

        init(context);
    }

    public ScaleItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();

    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        createCacheBitmap(w, h);
        testDrawing();
    }

    private void createCacheBitmap(int w, int h) {
        cacheBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
    }

    private void testDrawing() {
        cacheCanvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLACK);

        Path path = new Path();
        path.moveTo(75, 400);
        path.lineTo(325, 400);
        path.lineTo(200, 10);
        path.close();

        cacheCanvas.drawPath(path, mPaint);
    }




    @Override
    public void addChildrenForAccessibility(ArrayList<View> outChildren) {
        super.addChildrenForAccessibility(outChildren);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cacheBitmap!=null) {
            canvas.drawBitmap(cacheBitmap, 0, 0, null);
        }
    }


}

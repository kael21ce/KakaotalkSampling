package org.techtown.kakaotalksampling;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ScaleItem extends View {
    @Override
    public void addChildrenForAccessibility(ArrayList<View> outChildren) {
        super.addChildrenForAccessibility(outChildren);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();
        path.moveTo(75, 50);
        path.lineTo(150, 50);
        path.lineTo(112, 10);
        path.close();

        canvas.drawPath(path, paint);
    }

    private Paint paint;

    public ScaleItem(Context context) {
        super(context);

        init(context);
    }

    public ScaleItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.BLACK);

    }
}

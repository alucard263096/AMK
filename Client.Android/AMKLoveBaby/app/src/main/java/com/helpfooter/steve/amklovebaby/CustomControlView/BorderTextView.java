package com.helpfooter.steve.amklovebaby.CustomControlView;

/**
 * Created by jl11997 on 2015/12/17.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("DrawAllocation")
public class BorderTextView extends TextView{

    private int mBorderColor = Color.BLACK;
    private int sroke_width = 1;

    public BorderTextView(Context context) {
        super(context);
    }
    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBorderColor(int color)
    {
        mBorderColor = color;
    }

    public void setBorderWidth(int width)
    {
        sroke_width = width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        //  将为边框设颜色
        paint.setColor(mBorderColor);
        //  画TextView的4个边
        canvas.drawLine(0, 0, this.getWidth() - sroke_width, 0, paint);
        canvas.drawLine(0, 0, 0, this.getHeight() - sroke_width, paint);
        canvas.drawLine(this.getWidth() - sroke_width, 0, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
        canvas.drawLine(0, this.getHeight() - sroke_width, this.getWidth() - sroke_width, this.getHeight() - sroke_width, paint);
        super.onDraw(canvas);
    }
}



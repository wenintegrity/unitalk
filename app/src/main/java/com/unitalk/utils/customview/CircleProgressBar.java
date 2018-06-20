package com.unitalk.utils.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.unitalk.R;


public class CircleProgressBar extends View {
    private float strokeWidth = 7;
    private float progress = 0;
    private int min = 0;
    private int max = 2000;

    private RectF rectF;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleProgressBar,
                0, 0);
        //Reading values from the XML layout
        try {
            strokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_progressBarThickness, strokeWidth);
            progress = typedArray.getFloat(R.styleable.CircleProgressBar_progress, progress);
            min = typedArray.getInt(R.styleable.CircleProgressBar_min, min);
            max = typedArray.getInt(R.styleable.CircleProgressBar_max, max);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        rectF.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(rectF, createGroundPaint(R.color.colorCircleProgressBackground));
        float angle = 360 * progress / max;
        int startAngle = -90;
        canvas.drawArc(rectF, startAngle, angle, false, createGroundPaint(R.color.colorCircleProgressForeground));
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();   // Notify the view to redraw it self (the onDraw method is called)
    }

    public float getProgress() {
        return progress;
    }

    private Paint createGroundPaint(@ColorRes int mainColor) {
        final Paint groundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        groundPaint.setColor(ContextCompat.getColor(getContext(), mainColor));
        groundPaint.setStyle(Paint.Style.STROKE);
        groundPaint.setStrokeWidth(strokeWidth);
        return groundPaint;
    }
}

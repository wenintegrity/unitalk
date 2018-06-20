package com.unitalk.utils.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.unitalk.R;

public class VisualizerView extends FrameLayout {
    private static final int DEFAULT_NUM_COLUMNS = 36;
    private static final int RENDER_RANGE_TOP = 0;
    private static final int RENDER_RANGE_BOTTOM = 1;
    private static final int RENDER_RANGE_TOP_BOTTOM = 2;

    private int numColumns;
    private int renderColor;
    private int type;
    private int renderRange;
    private int baseY;
    private float columnWidth;
    private float space;
    private Canvas canvas;
    private Bitmap canvasBitmap;
    private Rect rect;
    private Paint paint;
    private Paint fadePaint;
    private Matrix matrix;

    public VisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        paint.setColor(renderColor);
        fadePaint.setColor(Color.argb(138, 255, 255, 255));
    }

    private void init(final Context context, final AttributeSet attrs) {
        rect = new Rect();
        paint = new Paint();
        fadePaint = new Paint();
        matrix = new Matrix();

        final TypedArray args = context.obtainStyledAttributes(attrs, R.styleable.visualizerView);
        numColumns = args.getInteger(R.styleable.visualizerView_numColumns, DEFAULT_NUM_COLUMNS);
        renderColor = args.getColor(R.styleable.visualizerView_renderColor, Color.WHITE);
        type = args.getInt(R.styleable.visualizerView_renderType, Type.BAR.getFlag());
        renderRange = args.getInteger(R.styleable.visualizerView_renderRange, RENDER_RANGE_TOP);
        args.recycle();
    }

    /**
     * @param baseY center Y position of visualizer
     */
    public void setBaseY(final int baseY) {
        this.baseY = baseY;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        // Create canvas once we're ready to draw
        rect.set(0, 0, getWidth(), getHeight());

        if (canvasBitmap == null) {
            canvasBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        }

        if (this.canvas == null) {
            this.canvas = new Canvas(canvasBitmap);
        }

        if (numColumns > getWidth()) {
            numColumns = DEFAULT_NUM_COLUMNS;
        }

        columnWidth = (float) getWidth() / (float) numColumns;
        space = columnWidth / 8f;

        if (baseY == 0) {
            baseY = getHeight() / 2;
        }

        canvas.drawBitmap(canvasBitmap, matrix, null);
    }

    /**
     * receive volume from the recording
     *
     * @param volume volume from mic input
     */
    public void receive(final int volume) {
        // COEFFICIENT FOR SCALING RECORDING DATA INTO THE VISUALIZATION DATA
        final int visualScaleIndex = volume / 100;
        new Handler(Looper.getMainLooper()).post(() -> {
            if (canvas == null) {
                return;
            }

            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            if ((type & Type.PIXEL.getFlag()) != 0) {
                drawPixel(visualScaleIndex);
            }
            invalidate();
        });
    }

    private void drawPixel(final int volume) {
        for (int i = 0; i < numColumns; i++) {
            final float height = getRandomHeight(volume);
            final float left = i * columnWidth + space;
            final float right = (i + 1) * columnWidth - space;

            int drawCount = (int) (height / (right - left));
            if (drawCount == 0) {
                drawCount = 1;
            }
            final float drawHeight = height / drawCount;

            // draw each pixel
            for (int j = 0; j < drawCount; j++) {
                float top, bottom;
                switch (renderRange) {
                    case RENDER_RANGE_TOP:
                        top = baseY + (drawHeight * j);
                        bottom = top + drawHeight - space;
                        break;

                    case RENDER_RANGE_BOTTOM:
                        bottom = baseY - (drawHeight * j);
                        top = bottom - drawHeight + space;
                        break;

                    case RENDER_RANGE_TOP_BOTTOM:
                        bottom = baseY - (height / 2) + (drawHeight * j);
                        top = bottom - drawHeight + space;
                        break;

                    default:
                        return;
                }

                final RectF rect = new RectF(left, top, right, bottom);
                canvas.drawRect(rect, paint);
            }
        }
    }

    private float getRandomHeight(final int volume) {
        final double randomVolume = Math.random() * volume / 2 + 1;
        float height = getHeight();
        switch (renderRange) {
            case RENDER_RANGE_TOP:
                height = baseY;
                break;
            case RENDER_RANGE_BOTTOM:
                height = (getHeight() - baseY);
                break;
            case RENDER_RANGE_TOP_BOTTOM:
                height = getHeight();
                break;
        }
        return (height / 60f) * (float) randomVolume;
    }

    /**
     * visualizer type
     */
    public enum Type {
        BAR(0x1), PIXEL(0x2);

        private int mFlag;

        Type(final int flag) {
            mFlag = flag;
        }

        public int getFlag() {
            return mFlag;
        }
    }
}

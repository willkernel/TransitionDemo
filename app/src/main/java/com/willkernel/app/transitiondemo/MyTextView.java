package com.willkernel.app.transitiondemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

/**
 * Created by admin on 2016/9/24.
 * mail:willkerneljc@gmail.com
 */
public class MyTextView extends TextView {
    private Matrix matrix;
    private Paint marginPaint, textPaint;
    private Resources resources;
    private Canvas mCanvas;
    private int backgroundColor;
    private float margin;
    private Rect bounds;
    private String text;
    private Paint.FontMetrics fontMetrics;

    public MyTextView(Context context) {
        super(context);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        resources = getResources();
        text = resources.getString(R.string.app_name);
        bounds = new Rect();
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(resources.getColor(android.R.color.black));
        textPaint.setColor(resources.getColor(android.R.color.black));
        backgroundColor = resources.getColor(android.R.color.darker_gray);
        margin = resources.getDimension(R.dimen.activity_horizontal_margin);
        matrix = new Matrix();
        matrix.setRotate(30, 0, 0);
        matrix.setScale(0.6f, 0.6f, 0, 0);
        matrix.setTranslate(margin, 0);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        canvas.drawColor(backgroundColor);
        canvas.drawText(text, 0, 0, textPaint);
        textPaint.getTextBounds(resources.getString(R.string.app_name), 0, text.length(), bounds);
        fontMetrics = textPaint.getFontMetrics();
        Log.e(TAG, "bounds height=" + bounds.height() + "  width=" + bounds.width());
        Log.e(TAG, "getMeasuredHeight=" + getMeasuredHeight() + "  getMeasuredWidth=" + getMeasuredWidth());
        Log.e(TAG, "baseline=" + (bounds.height() / 2 + (bounds.bottom - bounds.top) / 2 - bounds.bottom));
        Log.e(TAG, "getBaseline()=" +getBaseline());
        Log.e(TAG, "ascent=" + fontMetrics.ascent + "  descent=" + fontMetrics.descent + "  top=" + fontMetrics.top + "  bottom=" + fontMetrics.bottom + "  leading=" + fontMetrics.leading + "\nbaseline=" + (bounds.height() - fontMetrics.descent - fontMetrics.leading));
        canvas.drawLine(0, 0, 0, getMeasuredHeight(), marginPaint);
        canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), marginPaint);
        canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);
        canvas.save();
        canvas.translate(margin, 0);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                invalidate();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
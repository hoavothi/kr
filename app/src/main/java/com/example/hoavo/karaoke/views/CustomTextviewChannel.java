package com.example.hoavo.karaoke.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.hoavo.karaoke.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 31/07/2017.
 */

public class CustomTextviewChannel extends AppCompatTextView {

    private Paint mPaint;

    public CustomTextviewChannel(Context context) {
        super(context);
    }

    public CustomTextviewChannel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextviewChannel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorGrayLight));
            mPaint.setStrokeWidth(3);
        }

        canvas.drawLine(0, 0, getWidth(), 0, mPaint);
        canvas.drawLine(0, 0, 0, getHeight(), mPaint);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mPaint);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), mPaint);
    }
}

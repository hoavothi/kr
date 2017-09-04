package com.example.hoavo.kotlin.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

import com.example.hoavo.karaoke.R

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 31/07/2017.
 */

class CustomTextviewChannel : AppCompatTextView {

    private val mPaint: Paint by lazy {
        Paint()
    }

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = ContextCompat.getColor(context, R.color.colorGrayLight)
        mPaint.strokeWidth = 3f

        canvas.drawLine(0f, 0f, width.toFloat(), 0f, mPaint)
        canvas.drawLine(0f, 0f, 0f, height.toFloat(), mPaint)
        canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), mPaint)
        canvas.drawLine(width.toFloat(), 0f, width.toFloat(), height.toFloat(), mPaint)
    }
}

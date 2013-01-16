package com.example.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.example.customview.R;

/**
 * Created by Dave Smith
 * Xcellent Creations, Inc.
 * Date: 1/10/13
 * BoxGridLayout
 */
public class BoxGridLayout extends ViewGroup {

    private static final int COUNT = 3;

    private Paint mGridPaint;

    public BoxGridLayout(Context context) {
        this(context, null);
    }

    public BoxGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoxGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoxGridLayout, 0, defStyle);

        int strokeWidth = a.getDimensionPixelSize(R.styleable.BoxGridLayout_separatorWidth, 0);
        int strokeColor = a.getColor(R.styleable.BoxGridLayout_separatorColor, Color.WHITE);

        a.recycle();

        mGridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(strokeColor);
        mGridPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize, heightSize;

        //Get the width based on the measure specs
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize =  MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            widthSize = specSize;
        } else {
            widthSize = 0;
        }

        //Get the height based on measure specs
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize =  MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            heightSize = specSize;
        } else {
            heightSize = 0;
        }

        int majorDimension = Math.min(widthSize, heightSize);
        //Measure all child views
        int blockDimension = majorDimension / COUNT;
        int blockSpec = MeasureSpec.makeMeasureSpec(blockDimension, MeasureSpec.EXACTLY);
        measureChildren(blockSpec, blockSpec);

        //MUST call this to save our own dimensions
        setMeasuredDimension(majorDimension, majorDimension);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row, col, left, top;
        for (int i=0; i < getChildCount(); i++) {
            row = i / COUNT;
            col = i % COUNT;
            View child = getChildAt(i);
            left = col * child.getMeasuredWidth();
            top = row * child.getMeasuredHeight();

            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //Let the framework do its thing
        super.dispatchDraw(canvas);

        //Draw the grid lines
        for (int i=0; i <= getWidth(); i += (getWidth() / COUNT)) {
            canvas.drawLine(i, 0, i, getHeight(), mGridPaint);
        }
        for (int i=0; i <= getHeight(); i += (getHeight() / COUNT)) {
            canvas.drawLine(0, i, getWidth(), i, mGridPaint);
        }
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, LayoutParams params) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child, width, height);
    }
}

package com.example.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.example.customview.R;

/**
 * Created by Dave Smith
 * Xcellent Creations, Inc.
 * Date: 1/9/13
 * DoubleImageView
 */
public class DoubleImageView extends View {

    private Drawable mLeftDrawable, mRightDrawable;
    private int mSpacing;

    public DoubleImageView(Context context) {
        this(context, null);
    }

    public DoubleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DoubleImageView, 0, defStyle);

        Drawable d = a.getDrawable(R.styleable.DoubleImageView_android_drawableLeft);
        if (d != null) {
            setLeftDrawable(d);
        }

        d = a.getDrawable(R.styleable.DoubleImageView_android_drawableRight);
        if (d != null) {
            setRightDrawable(d);
        }

        int spacing = a.getDimensionPixelSize(R.styleable.DoubleImageView_android_spacing, 0);
        setSpacing(spacing);

        a.recycle();
    }

    public void setLeftDrawableResource(int resId) {
        Drawable d = getResources().getDrawable(resId);
        setLeftDrawable(d);
    }

    public void setLeftDrawable(Drawable left) {
        mLeftDrawable = left;
        updateContentBounds();
        invalidate();
    }

    public void setRightDrawableResource(int resId) {
        Drawable d = getResources().getDrawable(resId);
        setRightDrawable(d);
    }

    public void setRightDrawable(Drawable right) {
        mRightDrawable = right;
        updateContentBounds();
        invalidate();
    }

    public void setSpacing(int spacing) {
        mSpacing = spacing;
        updateContentBounds();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Get the width measurement
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSize = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                //Big as we want to be
                widthSize = getDesiredWidth();
                break;
            case MeasureSpec.AT_MOST:
                //Big as we want to be, up to the spec
                widthSize = Math.min(getDesiredWidth(), specSize);
                break;
            case MeasureSpec.EXACTLY:
                //Must be the spec size
                widthSize = specSize;
                break;
        }

        //Get the height measurement
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSize = 0;
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                //Big as we want to be
                heightSize = getDesiredHeight();
                break;
            case MeasureSpec.AT_MOST:
                //Big as we want to be, up to the spec
                heightSize = Math.min(getDesiredHeight(), specSize);
                break;
            case MeasureSpec.EXACTLY:
                //Must be the spec size
                heightSize = specSize;
                break;
        }

        //MUST call this to store the measurements
        setMeasuredDimension(widthSize, heightSize);
    }

    private int getDesiredWidth() {
        int leftWidth;
        if (mLeftDrawable == null) {
            leftWidth = 0;
        } else {
            leftWidth = mLeftDrawable.getIntrinsicWidth();
        }

        int rightWidth;
        if (mRightDrawable == null) {
            rightWidth = 0;
        } else {
            rightWidth = mRightDrawable.getIntrinsicWidth();
        }

        return (leftWidth + mSpacing + rightWidth);
    }

    private int getDesiredHeight() {
        int leftHeight;
        if (mLeftDrawable == null) {
            leftHeight = 0;
        } else {
            leftHeight = mLeftDrawable.getIntrinsicHeight();
        }

        int rightHeight;
        if (mRightDrawable == null) {
            rightHeight = 0;
        } else {
            rightHeight = mRightDrawable.getIntrinsicHeight();
        }

        return Math.max(leftHeight, rightHeight);
    }

    private void updateContentBounds() {
        int left = (getWidth() - getDesiredWidth()) / 2;
        int top;

        if (mLeftDrawable != null) {
            top = (getHeight() - mLeftDrawable.getIntrinsicHeight()) / 2;
            mLeftDrawable.setBounds(left, top,
                    left + mLeftDrawable.getIntrinsicWidth(), top + mLeftDrawable.getIntrinsicHeight());

            left += mLeftDrawable.getIntrinsicWidth();
            left += mSpacing;
        }

        if (mRightDrawable != null) {
            top = (getHeight() - mRightDrawable.getIntrinsicHeight()) / 2;
            mRightDrawable.setBounds(left, top,
                    left + mRightDrawable.getIntrinsicWidth(), top + mRightDrawable.getIntrinsicHeight());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            updateContentBounds();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mLeftDrawable != null) {
            mLeftDrawable.draw(canvas);
        }
        if (mRightDrawable != null) {
            mRightDrawable.draw(canvas);
        }
    }
}
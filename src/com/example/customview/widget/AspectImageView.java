package com.example.customview.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Dave Smith
 * Xcellent Creations, Inc.
 * Date: 1/9/13
 * AspectImageView
 *
 * ImageView that sizes itself to match the proper aspect ratio size of
 * the image content
 */
public class AspectImageView extends ImageView {

    public AspectImageView(Context context) {
        super(context);
    }

    public AspectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Figure out the aspect ratio of the image content
        int widthSize = 0;
        int desiredSize;
        float aspect;
        Drawable d = getDrawable();
        if (d == null) {
            desiredSize = 0;
            aspect = 1f;
        } else {
            desiredSize = d.getIntrinsicWidth();
            aspect = (float) d.getIntrinsicWidth() / (float) d.getIntrinsicHeight();
        }
        //Get the width based on the measure specs
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize =  MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                //Big as we want to be
                widthSize = desiredSize;
                break;
            case MeasureSpec.AT_MOST:
                //Big as we want to be, up to the spec
                widthSize = Math.min(desiredSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                //Must be the spec size
                widthSize = specSize;
                break;
        }

        //Calculate height based on aspect
        int heightSize = (int)(widthSize / aspect);

        //Make sure the height we want is not too large
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.AT_MOST || specMode == MeasureSpec.EXACTLY) {
            //If our measurement exceeds the max height, shrink back
            if (heightSize > specSize) {
                heightSize = specSize;
                widthSize = (int)(heightSize * aspect);
            }
        }

        //MUST do this to store the measurements
        setMeasuredDimension(widthSize, heightSize);
    }
}

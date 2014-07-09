/**
 * Copyright (c) 2013 Wireless Designs, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.customview.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
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
        int widthSize = MeasureUtils.getMeasurement(widthMeasureSpec, desiredSize);

        //Calculate height based on aspect
        int heightSize = (int)(widthSize / aspect);

        //Make sure the height we want is not too large
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
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

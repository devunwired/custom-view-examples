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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.example.customview.R;

public class EntryFormView extends RelativeLayout implements View.OnClickListener {

    public interface OnEntrySubmittedListener {
        public void onEntrySubmitted(CharSequence name, CharSequence email);
    }

    private EditText mNameText, mEmailText;
    private OnEntrySubmittedListener mListener;

    public EntryFormView(Context context) {
        this(context, null);
    }

    public EntryFormView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EntryFormView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //Inflate and attach the content
        LayoutInflater.from(context).inflate(R.layout.entry_form, this);

        setBackgroundResource(R.drawable.background);

        mNameText = (EditText) findViewById(R.id.name_text);
        mEmailText = (EditText) findViewById(R.id.email_text);

        findViewById(R.id.save_button).setOnClickListener(this);
    }

    public void setOnEntrySubmittedListener(OnEntrySubmittedListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View v) {
        //Hide the keyboard
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mNameText.getWindowToken(), 0);

        //Notify the listener
        if (mListener != null) {
            mListener.onEntrySubmitted(mNameText.getText(), mEmailText.getText());
        }

        //Clear the fields
        mNameText.setText(null);
        mEmailText.setText(null);
    }
}

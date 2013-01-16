package com.example.customview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.example.customview.R;

/**
 * Created by Dave Smith
 * Xcellent Creations, Inc.
 * Date: 1/10/13
 * EntryFormView
 */
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

        //Clear the fields
        mNameText.setText(null);
        mEmailText.setText(null);

        //Notify the listener
        if (mListener != null) {
            mListener.onEntrySubmitted(mNameText.getText(), mEmailText.getText());
        }
    }
}

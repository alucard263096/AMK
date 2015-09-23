package com.helpfooter.steve.amkdoctor.CustomControlView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.helpfooter.steve.amkdoctor.Utils.FontUtil;

/**
 * Created by Steve on 2015/8/26.
 */
public class MyTextView  extends TextView {
    public MyTextView(Context context) {
        super(context);
        init(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTextView(Context context, AttributeSet attrs, int defSyle) {
        super(context, attrs, defSyle);
        init(context);
    }

    public void init(Context context) {
        try {
            //setTypeface(FontUtil.setFont(context));
        }catch (Exception ex){
            Log.e("LoadFontError",ex.getMessage());
        }

    }
}
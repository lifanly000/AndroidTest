package com.sleep.sunshine.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by lifan on 2017/7/27.
 */

public class MyLayout extends LinearLayout {
    private static final String TAG = "TestTouchEventLayout";

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: "+event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: "+ev.getAction());
        return super.dispatchTouchEvent(ev);
//        return true;
    }


    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        Log.i(TAG, "onFilterTouchEventForSecurity: "+event.getAction());
        return super.onFilterTouchEventForSecurity(event);
//        return false;
    }
}

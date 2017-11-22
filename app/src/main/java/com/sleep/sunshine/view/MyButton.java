package com.sleep.sunshine.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by lifan on 2017/7/27.
 */

public class MyButton extends AppCompatButton {
    private static final String TAG = "TestTouchEventButton";
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(context);
    }

//    private void init(Context context) {
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10);
//        this.setLayoutParams(lp);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Handler handler = new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                    }
//                };
//                handler.sendEmptyMessage(1);
//                Looper.loop();
//            }
//        }).start();
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: "+event.getAction());
        return super.onTouchEvent(event);
//        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent: "+event.getAction());
        return super.dispatchTouchEvent(event);
//        return false;
    }

    @Override
    public boolean onFilterTouchEventForSecurity(MotionEvent event) {
        Log.i(TAG, "onFilterTouchEventForSecurity: "+event.getAction());
        return super.onFilterTouchEventForSecurity(event);
//        return false;
    }



}

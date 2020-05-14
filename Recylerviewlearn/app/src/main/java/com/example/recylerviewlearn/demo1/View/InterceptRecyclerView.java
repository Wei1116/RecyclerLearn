package com.example.recylerviewlearn.demo1.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InterceptRecyclerView extends RecyclerView {
    private int downRawX, downRawY;


    public InterceptRecyclerView(@NonNull Context context) {
        super(context);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downRawX = (int) ev.getRawX();
                downRawY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getRawY();
                int moveX = (int) ev.getRawX();
                /*判断向右滑动,或者判断xiang右滑动，大于先上滑动*/
                if ((moveX - downRawX) > Math.abs(moveY - downRawY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}

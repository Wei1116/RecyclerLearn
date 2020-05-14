package com.example.recylerviewlearn.demo1.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recylerviewlearn.R;

public class InterceptScrollView extends ScrollView {
    private int downX,downY;
    private RecyclerView mRecyclerView;
//    private int mTouchSlop;

    public InterceptScrollView(Context context) {
        this(context, null);
    }

    public InterceptScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InterceptScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InterceptScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event){
//
//        return super.onInterceptTouchEvent(event);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewParent parent = getParent();
        float x = ev.getX();
        float y = ev.getY();
        mRecyclerView = findViewById(R.id.rv);
        if (touchEventInRecyclerView(mRecyclerView,x,y))
            Log.d("TAG", "dispatchTouchEvent: in RecyclerView");
//        requestDisallowInterceptTouchEvent(true);
//        int x =

//        getParent().requestDisallowInterceptTouchEvent(true);
        setNestedScrollingEnabled(true);
        return super.dispatchTouchEvent(ev);
    }
//
    private boolean touchEventInRecyclerView(View view, float x, float y){
        if (view==null)
            return false;
        int[] location = new int [2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];

        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        if(y>=top && y<=bottom && x>=left && x <= right){
            return true;
        }

        return false;

   }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        ViewParent parent = getParent();
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//                parent.requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                parent.requestDisallowInterceptTouchEvent(true);
//                break;
//        }
        return super.onTouchEvent(ev);
    }
}

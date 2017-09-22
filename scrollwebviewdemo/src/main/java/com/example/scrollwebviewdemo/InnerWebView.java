package com.example.scrollwebviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * @author Zhenhua on 2017/9/19.
 * @email zhshan@ctrip.com ^.^
 */
public class InnerWebView extends WebView {
    private final String TAG = "XinInnerScrollView";
    private boolean debug = true;
    private float downX, downY; // 按下时
    private float currX, currY; // 移动时
    private float moveX; // 移动长度-横向
    boolean isOnTop;
    boolean goDown;

    public InnerWebView(Context context) {
        super(context);
    }

    public InnerWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                printLog("onTouchEvent ACTION_DOWN");
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                currX = ev.getX();
                currY = ev.getY();
                moveX = Math.abs(currX - downX);
                isOnTop = getScrollY() == 0;
                goDown = currY - downY > 3 ? true : false;
                printLog("onTouchEvent ACTION_MOVE getScrollX()=" + getScrollX() + " getScrollY()=" + getScrollY());
                int[] position = new int[2];
                InnerWebView.this.getLocationInWindow(position);
                // 垂直滑动
                if (Math.abs(currY - downY) > moveX) {
                    printLog("onTouchEvent ACTION_MOVE 在顶部 下滑 父处理1");
                    printLog("currentY=" + currY + ";downY=" + downY);
                    // 处于顶部或者无法滚动，并且继续下滑，交出事件（currY-downY >0是下滑, <0则是上滑）
                    *//*if (position[1] < CommonUtil.dp2px(getContext(), 200) + CommonUtil.getBarHeight(getContext())) {
                        printLog("onTouchEvent ACTION_MOVE 在顶部 下滑 父处理");
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    } else *//*
                    if (isOnTop && !goDown) {
                        printLog("onTouchEvent ACTION_MOVE 在顶部 下滑 父处理");
                        getParent().getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    printLog("status bar的高度=" + CommonUtil.getBarHeight(getContext()));
                    printLog("text view高度=" + CommonUtil.dp2px(getContext(), 200));
                    printLog("距离顶部位置=" + position[1]);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                printLog("onTouchEvent ACTION_UP");
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }
        return super.onTouchEvent(ev);
    }*/

    public void printLog(String msg) {
        if (debug) {
            Log.d(TAG, msg);
        }
    }
}

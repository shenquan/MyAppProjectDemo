package com.example.scrollwebviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * @author Zhenhua on 2017/6/2 09:46.
 * @email zhshan@ctrip.com
 */

public class NestedScrollView extends ScrollView {
    private static final String TAG = "szh";
    private Runnable scrollerTask;
    private int initialPosition;
    private int newCheck = 100;
    private View fixView;
    private OnFixListener listener;
    private boolean isTop;
    private boolean rvDisable;
    //    private OnScrollStoppedListener onScrollStoppedListener;
    private OnScrollChangeListener changeListener;
    private boolean fixed;
    private float downY;
    private float currY;


    public NestedScrollView(Context context) {
        super(context);
    }

    public NestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (getScrollY() >= fixView.getTop()) {
            fix();
            isTop = true;
        } else {
            dismiss();
            isTop = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            LinearLayout linearLayout = (LinearLayout) getChildAt(0);
            if (linearLayout != null) {
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    if (linearLayout.getChildAt(i).getTag() != null && linearLayout.getChildAt(i).getTag().equals("fix")) {
                        fixView = linearLayout.getChildAt(i);
                    }
                }
            }
        }
    }

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercepted = false;
        boolean goDown = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isIntercepted = false;
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                currY = ev.getY();
                goDown = currY - downY > 0 ? true : false;
                isIntercepted = true;
                if (isTop && goDown) {
                    isIntercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isIntercepted = false;
                break;
            default:
                break;
        }
        return isIntercepted;
    }*/

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
        }
        if (action == MotionEvent.ACTION_MOVE) {
            if (isTop && !rvDisable) {
                return false;
            } else {
                return super.onInterceptTouchEvent(ev);
            }
        }

        return super.onInterceptTouchEvent(ev);
    }*/

    public void setFixListener(OnFixListener listener) {
        this.listener = listener;
    }

    private void fix() {
        if (listener != null && !fixed) {
            listener.onFix();
            fixed = true;
        }
    }

    private void dismiss() {
        if (listener != null && fixed) {
            listener.onDismiss();
            fixed = false;
        }
    }

    public interface OnFixListener {
        void onFix();

        void onDismiss();
    }

    public void setRvDisable(boolean rvDisable) {
        this.rvDisable = rvDisable;
    }

    /*public void setOnScrollListener(OnScrollChangeListener listener) {
        this.changeListener = listener;
    }*/

    public interface OnScrollChangeListener {
        void onScrollChanged(float dy);
    }

}

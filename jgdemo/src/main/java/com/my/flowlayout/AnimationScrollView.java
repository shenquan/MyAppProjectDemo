package com.my.flowlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ScrollView;

import static android.R.attr.action;

/**
 * @author Zhenhua on 2017/8/22.
 * @email zhshan@ctrip.com ^.^
 */
public class AnimationScrollView extends ScrollView {
    private OnScrollChangeListener listener;
    private Context context;

    public AnimationScrollView(Context context) {
        super(context);
        this.context = context;
    }

    public AnimationScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }


    public void setOnScrollListener(OnScrollChangeListener listener) {
        this.listener = listener;
    }

    public interface OnScrollChangeListener {
        void onScrollChanged(int action, int dy);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        listener.onScrollChanged(action, (int) (getScrollY() * 0.5f));

    }
}

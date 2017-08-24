package com.my.flowlayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author Zhenhua on 2017/7/12.
 * @email zhshan@ctrip.com ^.^
 */
public class VacationHomeFragment extends Fragment {

    private static final String TAG = "VacationHomeFragment";
    public static final int REQUEST = 1;
    private View mRootView;
    private Activity context;

    private LinearLayout tagView, search_layout;
    private TextView title;


    @Override
    public void onAttach(Activity activity) {
        this.context = activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;
        initView();
    }

    private void initView() {
//        showDialog();
        initHeadAnimation();
    }

    private int minWidth, maxWidth, minHeight, maxHeight;

    private void initHeadAnimation() {
        tagView = (LinearLayout) mRootView.findViewById(R.id.tagView);
        search_layout = (LinearLayout) mRootView.findViewById(R.id.search_layout);
        title = (TextView) mRootView.findViewById(R.id.title);
        minWidth = CommonUtil.getScreenWidth(context) - CommonUtil.dp2px(context, 56f);
        maxWidth = CommonUtil.getScreenWidth(context) - CommonUtil.dp2px(context, 20f);
        minHeight = CommonUtil.dp2px(context, 5.5f);
        maxHeight = CommonUtil.dp2px(context, 59.7f);
        AnimationScrollView scrollView = (AnimationScrollView) mRootView.findViewById(R.id.scrollview);
        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tagView.getLayoutParams();
        scrollView.setOnScrollListener(new AnimationScrollView.OnScrollChangeListener() {
            @Override
            public synchronized void onScrollChanged(int action, final int dy) {

                int newTopMargin = maxHeight - dy;
                int newWidth = maxWidth - dy;
                int rightMargin = 0;
                int alpha = 0;

                if (newWidth < minWidth) {
                    newWidth = minWidth;
                } else {
//                    params.rightMargin = (newWidth) * CommonUtil.dp2px(context, 36f) / maxWidth;
                }
                if (newTopMargin < minHeight) {
                    newTopMargin = minHeight;
//                    alpha = 0;

                } else {
//                    alpha = newTopMargin * 225 / maxHeight;
//                    search_layout.getBackground().setAlpha(alpha);
                }
                if ((newTopMargin - minHeight) > 0) {
                    alpha = (newTopMargin - minHeight) * 225 / maxHeight;
                    search_layout.getBackground().setAlpha(alpha);
                } else {
                    search_layout.getBackground().setAlpha(225);
                }
                params.topMargin = newTopMargin;
                params.width = newWidth;
                tagView.setLayoutParams(params);
            }

        });
    }
}

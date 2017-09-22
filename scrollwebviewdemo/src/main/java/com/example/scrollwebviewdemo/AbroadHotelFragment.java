package com.example.scrollwebviewdemo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.scrollwebviewdemo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhenhua on 2017/9/19.
 * @email zhshan@ctrip.com ^.^
 */
public class AbroadHotelFragment extends Fragment implements NestedScrollView.OnFixListener {
    private String TAG = "SZH";
    private boolean debug = true;
    private InnerWebView webView;
    private View mRootView;
    private Context context;
    private NestedScrollView scrollView;
    private float downX, downY; // 按下时
    private float currX, currY; // 移动时
    private float moveX; // 移动长度-横向
    boolean isOnTop;
    boolean goDown;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.abroad_hotel_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;
        initView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void initView() {
        scrollView = (NestedScrollView) mRootView.findViewById(R.id.scrollview);
        webView = (InnerWebView) mRootView.findViewById(R.id.webview);
//        webView.setLayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.getScreenHeight(context) - CommonUtil.getBarHeight(context) - CommonUtil.dp2px(context, 200));
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtil.getScreenHeight(context) - CommonUtil.getBarHeight(context)));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        setWebView("http://m.ctrip.com/html5/");
        setTouchListener();
    }

    private void setWebView(String url) {
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT > 20) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setAllowContentAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Access-Control-Allow-Origin", "*");
        extraHeaders.put("Access-Control-Allow-Headers", "Content-Type");
        webView.loadUrl(url, extraHeaders);
    }

    private void setTouchListener() {
        scrollView.setFixListener(this);
//        webView.getParent().requestDisallowInterceptTouchEvent(true);
    }

    @Override
    public void onFix() {
        printLog("onFix");
        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        webView.requestDisallowInterceptTouchEvent(true);
                        printLog("onTouchEvent ACTION_DOWN");
                        downX = ev.getX();
                        downY = ev.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        currX = ev.getX();
                        currY = ev.getY();
                        moveX = Math.abs(currX - downX);
                        isOnTop = webView.getScrollY() < 3;
                        goDown = currY - downY > 0 ? true : false;
                        printLog("getScrollY()=" + webView.getScrollY());
                        // 垂直滑动
                        if (Math.abs(currY - downY) > moveX) {
                            printLog("currentY=" + currY + ";downY=" + downY);
                            if (isOnTop && goDown) {
                                printLog("onTouchEvent ACTION_MOVE 在顶部 下滑 父处理");
                                webView.requestDisallowInterceptTouchEvent(false);
                            } else if (isOnTop && !goDown) {
                                webView.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        printLog("onTouchEvent ACTION_UP");
//                        webView.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }
        });
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.performClick();

            }
        }, 200);
    }

    @Override
    public void onDismiss() {
        webView.setOnTouchListener(null);
    }

    public void printLog(String msg) {
        if (debug) {
            Log.e(TAG, msg);
        }
    }
}

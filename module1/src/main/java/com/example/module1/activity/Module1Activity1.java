package com.example.module1.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hsqbusiness.util.AndroidUtil;
import com.example.module1.R;
import com.example.module1.adapter.ListTest1Adaper;

public class Module1Activity1 extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private int sizeOfListView = 10;

    private EditText editText;
    private Button button;
    private ListTest1Adaper adapter;
    private LayoutInflater inflater;
    private View header1;
    private View header2;
    private View footer1;
    private View suspendBar;
    private FrameLayout frameLayout;

    private int testNum = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_activity1_layout);
        context = this;
        inflater = LayoutInflater.from(context);

        initIndex();
    }

    public boolean isListViewReachBottomEdge(final AbsListView listView) {
        boolean result = false;
        int listViewWholeHeight = listView.getHeight();
        //listview中当前可见的最后一个item的位置
        int visiableItem = listView.getLastVisiblePosition() - listView.getFirstVisiblePosition();
        //如果最后item的高度小于listview的高度，说明个数较少没有填充满listview，则返回false

        //listView.getChildAt()中的数量不能超过listview一屏幕中最多显示的个数，因为是复用的view
//        ListView.getCount()（实际上是 AdapterView.getCount()） 返回的是其 Adapter.getCount() 返回的值。也就是“所包含的 Item 总个数”。
//        ListView.getChildCount()（ViewGroup.getChildCount） 返回的是显示层面上的“所包含的子 View 个数”。
        //方法一
        /*if (listView.getChildAt(visiableItem).getBottom() < listViewWholeHeight) {
            return false;
        }*/
        //方法二：推荐使用
        if (listView.getCount() <= listView.getChildCount()) {
            return false;
        }
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(visiableItem);
            //当前可见的最底部的view的底部距离父控件(该listview)顶部的高度（不是滚动高度）
            int bottomChildViewHeight = bottomChildView.getBottom();
            result = (listViewWholeHeight >= bottomChildViewHeight);
        }
        return result;
    }

    private TextView suspendBarTv;

    private void initIndex() {

        editText = (EditText) findViewById(R.id.et);
        button = (Button) findViewById(R.id.bt);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        listView = (ListView) findViewById(R.id.list_view);
        suspendBar = inflater.inflate(R.layout.listview_header_2, null);
        suspendBarTv = (TextView) suspendBar.findViewById(R.id.bar_tv);
        suspendBar.setVisibility(View.GONE);
        frameLayout.addView(suspendBar);

        header1 = inflater.inflate(R.layout.listview_header_1, null);
        header2 = inflater.inflate(R.layout.listview_header_2, null);
        footer1 = inflater.inflate(R.layout.listview_footer_1, null);

        listView.addHeaderView(header1);
        listView.addHeaderView(header2);
        listView.addFooterView(footer1);

        adapter = new ListTest1Adaper(context, sizeOfListView);
        listView.setAdapter(adapter);

        editText.setText(listView.getCount() + "");//初始化edittext

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case SCROLL_STATE_IDLE: {
                        boolean flag = isListViewReachBottomEdge(absListView);
                        if (flag) {
                            if (sizeOfListView >= 40) {
                                AndroidUtil.showToast(context, "没有更多资源了~");
                                return;
                            }
                            sizeOfListView += 10;
                            adapter.setData(sizeOfListView);
                            adapter.notifyDataSetChanged();

                            editText.setText(sizeOfListView + "");
                            AndroidUtil.showToast(context, "滑动到底部，加载更多~");
                        }
                        break;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                MyLogUtil.e("滑动了" + (++testNum));
//                MyLogUtil.e("header2 margin" + header2.getTop());
                int header2Top = header2.getTop();
                if (header2Top <= 0) {
                    suspendBar.setVisibility(View.VISIBLE);
                } else {
                    suspendBar.setVisibility(View.GONE);
                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sizeOfListView = Integer.parseInt(editText.getText().toString());
                    adapter.setData(sizeOfListView);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        header2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) header2.findViewById(R.id.tv1);
                textView.setText("点击了改变了数值：" + (testNum++));

            }
        });
        /*listView.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                MyLogUtil.e("onChildViewAdded执行");
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                MyLogUtil.e("onChildViewRemoved执行");
                frameLayout.addView(header2);//不行
            }
        });*/
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //直接加的方法会报异常，所以要先gone掉原来的
                //The specified child already has a parent. You must call removeView() on the child's parent first.
//                listView.removeHeaderView(header2);
                frameLayout.addView(header2);
            }
        }, 1000);*/
        /*header2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                MyLogUtil.e("onViewAttachedToWindow执行");
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                MyLogUtil.e("onViewDetachedFromWindow哈哈");
                frameLayout.addView(header2);//这个方法不行
            }
        });*/


    }

}

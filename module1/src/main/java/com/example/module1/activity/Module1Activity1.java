package com.example.module1.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hsqbusiness.util.AndroidUtil;
import com.example.module1.R;
import com.example.module1.adapter.ListTest1Adaper;

public class Module1Activity1 extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private int sizeOfListView = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_activity1_layout);
        context = this;

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

    private EditText editText;
    private Button button;
    private ListTest1Adaper adapter;

    private void initIndex() {

        editText = (EditText) findViewById(R.id.et);
        button = (Button) findViewById(R.id.bt);

        listView = (ListView) findViewById(R.id.list);
        adapter = new ListTest1Adaper(context, sizeOfListView);
        listView.setAdapter(adapter);

        editText.setText(listView.getCount() + "");//初始化edittext

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case SCROLL_STATE_IDLE:
                        boolean flag = isListViewReachBottomEdge(absListView);
                        if (flag) {
                            if (sizeOfListView > 50) {
                                AndroidUtil.showToast(context, "没有更多资源了~");
                                return;
                            }
                            sizeOfListView += 10;
                            adapter.setData(sizeOfListView);
                            adapter.notifyDataSetChanged();

                            editText.setText(sizeOfListView + "");
                            AndroidUtil.showToast(context, "滑动到底部，加载更多");
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
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
    }

}

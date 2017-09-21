package com.example.module1.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.module1.R;
import com.example.module1.adapter.ListTest1Adaper;

public class Module1Activity1 extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private int listSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module1_activity1_layout);
        context = this;

        listView = (ListView) findViewById(R.id.list);
        BaseAdapter adapter = new ListTest1Adaper(listSize, context);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("hsq", "Activity1销毁");
    }
}

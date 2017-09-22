package com.example.module0.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.model.LottieComposition;
import com.example.hsqbusiness.MyBaseApplication;
import com.example.hsqbusiness.android.base.BaseActivity;
import com.example.hsqbusiness.bus.Bus;
import com.example.hsqbusiness.eventbusmessage.MyMessage;
import com.example.hsqbusiness.util.AndroidUtil;
import com.example.module0.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class Module0MainActivity extends BaseActivity {
    private Context mContext;
    private TextView mTextMessage;

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //在library project中不能使用switch
            //http://tools.android.com/tips/non-constant-fields
            //https://stackoverflow.com/questions/15247606/intellij-android-java-constant-expression-required-on-case-r-id-viewid
            *//*switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.module0_title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.module0_title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.module0_title_notifications);
                    return true;
            }*//*

            int id = item.getItemId();
            if (R.id.navigation_home == id) {
                mTextMessage.setText(R.string.module0_title_home);
                return true;//需要返回true
            } else if (R.id.navigation_dashboard == id) {
                mTextMessage.setText(R.string.module0_title_dashboard);
                return true;
            } else if (R.id.navigation_notifications == id) {
                mTextMessage.setText(R.string.module0_title_notifications);
                return true;
            }

            return false;
        }

    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.module0_main_activity_layout);
        mContext = this;
        MyBaseApplication.getInstance().isHomeCreated = true;

        /*try {
            //间隔调试使用
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        //每个模块若使用business工具类，需要依赖business
//        AndroidUtil.showToast(mContext, "加载business模块成功");
//        long xx = Calendar.getInstance().getTimeInMillis();
////        long xx1 = System.currentTimeMillis();
//        long xx1 = new Date().getTime();
//        Log.e("hsq", "xx=" + xx + ",xx1=" + xx1);

//        String gewg = BuildConfig.API_URL;

        mTextMessage = (TextView) findViewById(R.id.message1);
        mTextMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(mContext, "com.my.flowlayout.JGActivity");
                intent.setComponent(componentName);
                startActivity(intent);

                /*Intent intent1 = new Intent(mContext, SplashActivity.class);
                startActivity(intent1);*/

            }
        });
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TextView tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可以用json传第三个参数：new JSONObject().toString()
                Bus.callData(mContext, "module1/goMain");
            }
        });
        TextView tv3 = (TextView) findViewById(R.id.tv3);
//        tv3.setVisibility(View.GONE);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.callData(mContext, "KotlinModule/goMain");
//                AndroidUtil.showToast(mContext, "点击了，但无效果");
            }
        });
        //引用Lottie显示动画的三种方法
        //方式一，放在本地,xml中的app:lottie_fileName不能为空字符串，否则报错
        //方式二，放在本地
        final LottieAnimationView xx = (LottieAnimationView) findViewById(R.id.animation_view);
        LottieComposition.fromAssetFileName(mContext, "loading.json", new LottieComposition.OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(LottieComposition lottieComposition) {
                xx.setComposition(lottieComposition);
            }
        });
        xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidUtil.showToast(mContext, "点击了图片");
            }
        });
        //方式三，网络请求动画json文件。服务器端发送的数据为：new JSONObject().toString()
        /*try {
            JSONObject json = new JSONObject(response.body().string());
            LottieComposition.fromJson(getResources(), json, new LottieComposition.OnCompositionLoadedListener() {
                @Override
                public void onCompositionLoaded(LottieComposition composition) {
                    setComposition(composition);
                }
            });
        } catch (JSONException e) {
        }*/

        TextView tv4 = (TextView) findViewById(R.id.tv4);
        tv4.setVisibility(View.GONE);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //崩溃热修复测试
                /*vv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*/
                Intent intent = new Intent(mContext, Module0SlideActivity.class);
                startActivity(intent);
            }
        });

        TextView tv5 = (TextView) findViewById(R.id.tv5);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.callData(mContext, "scrollDemo/goMain");
            }
        });


    }

    private View vv;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent1111(MyMessage messageEvent) {//方法名随便写都行，自己按实际意义命名即可
//        tv_message.setText(messageEvent.getMessage());
        switch (messageEvent.type) {
            case 1:
                String str1 = "Eventbus1:" + messageEvent.text;
                AndroidUtil.showToast(mContext, str1);
                mTextMessage.setText(str1);
                break;
            case 2:
                String str2 = "Eventbus2:" + messageEvent.text;
                AndroidUtil.showToast(mContext, str2);
                mTextMessage.setText(str2);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

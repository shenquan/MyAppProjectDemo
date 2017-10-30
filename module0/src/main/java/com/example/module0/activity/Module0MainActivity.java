package com.example.module0.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
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

import java.util.ArrayList;
import java.util.List;


public class Module0MainActivity extends BaseActivity {
    private Context mContext;
    private TextView mTextMessage;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;

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

        tv2 = (TextView) findViewById(R.id.tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可以用json传第三个参数：new JSONObject().toString()
                Bus.callData(mContext, "module1/goMain");
            }
        });
        tv3 = (TextView) findViewById(R.id.tv3);
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

        tv4 = (TextView) findViewById(R.id.tv4);
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

        tv5 = (TextView) findViewById(R.id.tv5);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.callData(mContext, "scrollDemo/goMain");
            }
        });

        tv6 = (TextView) findViewById(R.id.tv6);
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();//请求相关权限
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI), 10);
            }
        });


    }

    //相关权限申请
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            final List<String> permissionsList = new ArrayList<>();
            addPermission(permissionsList, Manifest.permission.READ_CONTACTS);
            if (permissionsList.size() > 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 100);
            }
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            return false;
        }
        return true;
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            try {
                // ContentProvider展示数据类似一个单个数据库表
                // ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
                ContentResolver reContentResolverol = getContentResolver();
                // URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
                Uri contactData = data.getData();
                // 查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
                Cursor cursor = mContext.getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();
                // 获得DATA表中的名字
                String username = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                // 条件为联系人ID
                String contactId = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
                Cursor phone = reContentResolverol.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                                + contactId, null, null);
                String usernumber = "";
                while (phone.moveToNext()) {
                    usernumber = phone
                            .getString(phone
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    tv6.setText(usernumber + " (" + username + ")");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}

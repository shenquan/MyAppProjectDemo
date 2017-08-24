package com.my.flowlayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JGActivity extends Activity {
    TagLayout mFlowLayout;
    private Context mContext;
    String[] tags = new String[]{"别人家孩子作业做到转钟", "别人家孩子周末都在家学习", "成天就知道玩游戏", "别人上清华了", "比你优秀的人还比你勤奋", "我怎么教出你这么个不争气的败家子", "因为你是小明？"};

    private LinearLayout linear;
    private ImageView id_ball;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hsq_activity_main);
        mContext = this;

        mFlowLayout = (TagLayout) findViewById(R.id.tags);
        for (int i = 0; i < tags.length; i++) {
            TextView tv = new TextView(this);
            tv.setText(tags[i]);
            tv.setTextColor(Color.BLACK);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            tv.setLayoutParams(params);

            tv.setBackgroundResource(R.drawable.text_background);
            mFlowLayout.addView(tv);
        }

        linear = (LinearLayout) findViewById(R.id.linear);
        id_ball = (ImageView) findViewById(R.id.id_ball);
        Button test1 = (Button) findViewById(R.id.test1);
        TextView test2 = (Button) findViewById(R.id.test2);
        TextView test3 = (Button) findViewById(R.id.test3);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索栏滑动动画测试
                Intent intent = new Intent(JGActivity.this, VacationHomeActivity.class);
                JGActivity.this.startActivity(intent);

            }
        });
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跨模块Activity调用测试
                /*//方法一成功
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(mContext, "com.example.module1.activity.Module1Activity1");
                intent.setComponent(componentName);
                startActivity(intent);*/

                /*//不行，只能用于启动第三方的app,http://blog.csdn.net/huangyabin001/article/details/36626341
//                ComponentName componentName = new ComponentName("com.example.sqhan.myapplication1", "com.example.sqhan.myapplication1.SelfActivity");
                //可以
                ComponentName componentName = new ComponentName(mContext, "com.example.module1.activity.Module1Activity1");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                startActivity(intent);*/

                //方法二 隐式调用，成功
                // manifest中必须加<category android:name="android.intent.category.DEFAULT"/>
                Intent intent2 = new Intent();
                intent2.setAction("hsq");
                startActivity(intent2);

            }
        });

        test3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private boolean flag = false;

    public void rotateyAnimRun(final View view) {
        //实线翻转
//        ObjectAnimator objectAnimator = ObjectAnimator//
//                .ofFloat(view, "rotationX", 0.0F, 180.0F)//
//                .setDuration(500);//


        //缩小和颜色渐变
//        ObjectAnimator anim = ObjectAnimator//
//                .ofFloat(view, "zhy", 1.0F,  0.5F)//
//                .setDuration(500);//
//        anim.start();
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
//        {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation)
//            {
//                float cVal = (Float) animation.getAnimatedValue();
//                view.setAlpha(cVal);
//                view.setScaleX(cVal);
//                view.setScaleY(cVal);
//            }
//        });

//        ValueAnimator animator = ValueAnimator.ofFloat(0, 200);
//        animator.setTarget(linear);
//        animator.setDuration(1000).start();
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float cVal = (Float) animation.getAnimatedValue();
//                linear.setTranslationX(cVal);
//                view.setAlpha(0.5f);
//                view.setScaleX(0.5f);
//                view.setScaleY(0.5f);
//            }
//        });
//
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        linear.setBackgroundResource(R.drawable.ic_launcher);
//                    }
//                });
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });

        //缩小
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(linear, "scaleY",
                1.0f, 0f);
//        anim1.setDuration(500);
//        anim1.start();
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
            }
        });
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(linear, "scaleY",
                0f, 1);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
            }
        });
        anim2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!flag) {
                    id_ball.setImageResource(R.drawable.my2);
                    flag = true;
                } else {
                    id_ball.setImageResource(R.drawable.my3);
                    flag = false;
                }

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(500);
        animSet.setInterpolator(new LinearInterpolator());
        //两个动画同时执行
//        animSet.playTogether(anim1, anim2);
        //一个一个的执行动画
        animSet.playSequentially(anim1, anim2);
        animSet.start();

//        ScaleAnimation animation =new ScaleAnimation(1f, 1f, 1f, 0f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(500);//设置动画持续时间
//        linear.startAnimation(animation);
//
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                ScaleAnimation animation1 =new ScaleAnimation(1f, 1f, 0f, 1f,
//                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                animation1.setDuration(500);//设置动画持续时间
//                linear.startAnimation(animation1);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }

}

package com.cm.fm.mall.view.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.cm.fm.mall.R;
import com.cm.fm.mall.base.BaseActivity;
import com.cm.fm.mall.common.util.LogUtil;
import com.cm.fm.mall.common.util.Utils;

public class SplashActivity extends BaseActivity {

    private final String TAG = "FM_SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LogUtil.d(TAG,"splash start");
        appendAnimation();
    }
    //给闪屏图片添加一个2秒的动画
    public void appendAnimation(){
        AlphaAnimation animation = new AlphaAnimation(0.0f,1.0f);   //透明度，完全透明到完全不透明
        animation.setRepeatCount(0);
        animation.setRepeatMode(Animation.REVERSE); //重复动画的模式，
        animation.setDuration(1500);
        ImageView tv_splash = findViewById(R.id.tv_splash);
        tv_splash.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtil.d(TAG,"splash end");
                Utils.startActivityClose(SplashActivity.this,MainActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}

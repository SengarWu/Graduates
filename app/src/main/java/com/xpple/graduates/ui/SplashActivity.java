package com.xpple.graduates.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bmob.pay.tool.BmobPay;
import com.ktplay.open.KTPlay;
import com.xpple.graduates.R;
import com.xpple.graduates.config.Config;
import com.xpple.graduates.util.SpSettingsUtil;
import com.xpple.graduates.view.BaseActivity;

import net.slidingmenu.tools.AdManager;
import net.slidingmenu.tools.st.SpotManager;


public class SplashActivity extends BaseActivity {
    private static final int GO_GUIDE = 100;
    private static final int GO_MAIN = 200;
    private Boolean user_first, isUserSuggest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SpSettingsUtil mSharedSettingsUtil = mApplication.getSpSettingsUtil();
        user_first = mSharedSettingsUtil.isAllowFirst();
        isUserSuggest = mSharedSettingsUtil.isUserSuggest();
        // 必须先初始化
        BmobPay.init(this, Config.BMOB_KEY);
        // 初始化 KTPlay SDK
        KTPlay.startWithAppKey(this, Config.KTPLAY_KEY, Config.KTPLAY_SECRET);
        // 初始化 有米广告 SDK
        if (!isUserSuggest) {
            AdManager.getInstance(this).init(Config.YOUMI_KEY, Config.YOUMI_SECRET, false);
            // 开启用户数据统计服务,默认不开启，传入 false 值也不开启，只有传入 true 才会调用
            AdManager.getInstance(this).setUserDataCollect(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user_first) {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, 1500);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_MAIN, 1500);
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_GUIDE:
                    startAnimActivity(GuideActivity.class);
                    finish();
                    break;
                case GO_MAIN:
                    if (!isUserSuggest) {
                        goAdsAndMain();
                    } else {
                        startAnimActivity(MainActivity.class);
                        finish();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void goAdsAndMain() {
        if (isNetConnected()) {
            SpotManager.getInstance(this).showSplashSpotAds(this, MainActivity.class);
        } else {
            startAnimActivity(MainActivity.class);
            finish();
        }
    }

    // 请务必加上词句，否则进入网页广告后无法进去原sdk
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 10045) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotManager.getInstance(this).onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpotManager.getInstance(this).onDestroy();
    }

}

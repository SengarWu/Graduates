package com.xpple.graduates.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.xpple.graduates.CustomApplication;
import com.xpple.graduates.util.CommonUtil;


/**
 * Activity基类
 *
 * @author nEdAy
 */
public class BaseActivity extends FragmentActivity {

    protected CustomApplication mApplication;

    protected int mScreenWidth;
    protected int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = CustomApplication.getInstance();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
    }

    public void showLog(String msg) {
        Log.i("life", msg);
    }

    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(this, cla));
    }

    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }

    public boolean isNetConnected() {
        return CommonUtil.isNetworkAvailable(this);
    }


}

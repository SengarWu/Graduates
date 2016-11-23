package com.xpple.graduates.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.xpple.graduates.CustomApplication;
import com.xpple.graduates.R;
import com.xpple.graduates.util.CommonUtil;

/**
 * Fragmenet 基类
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener {

    protected CustomApplication mApplication;

    public LayoutInflater mInflater;

    private final static int TOAST_LAYOUT_ID_FAIL = R.layout.toast_fail;
    private final static int TOAST_LAYOUT_ID_SUCCSESS = R.layout.toast_success;

    private final static int TOAST_ID_TEXTVIEW_SUCCSESS = R.id.toast_message;
    private final static int TOAST_ID_TEXTVIEW_FAIL = R.id.toast_fail_message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mApplication = CustomApplication.getInstance();
        mInflater = LayoutInflater.from(getActivity());
    }

    public BaseFragment() {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(this);
    }

    Toast mToast;

    public void showToast(String text, boolean state) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(state ? TOAST_LAYOUT_ID_SUCCSESS : TOAST_LAYOUT_ID_FAIL, (ViewGroup) getActivity().findViewById(TOAST_ID_TEXTVIEW_SUCCSESS));
        ((TextView) layout.findViewById(state ? TOAST_ID_TEXTVIEW_SUCCSESS : TOAST_ID_TEXTVIEW_FAIL)).setText(text);
        if (mToast == null) {
            mToast = new Toast(getActivity().getApplicationContext());
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(layout);
            //toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL);
            //toast.setMargin(0, 1);
        } else {
            mToast.setView(layout);
        }
        mToast.show();
    }

    public void showToast(int text, boolean state) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(state ? TOAST_LAYOUT_ID_SUCCSESS : TOAST_LAYOUT_ID_FAIL, (ViewGroup) getActivity().findViewById(TOAST_ID_TEXTVIEW_SUCCSESS));
        ((TextView) layout.findViewById(state ? TOAST_ID_TEXTVIEW_SUCCSESS : TOAST_ID_TEXTVIEW_FAIL)).setText(text);
        if (mToast == null) {
            mToast = new Toast(getActivity().getApplicationContext());
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(layout);
        } else {
            mToast.setView(layout);
        }
        mToast.show();
    }

    public boolean isNetConnected() {
        return CommonUtil.isNetworkAvailable(getActivity());
    }

    public void startAnimActivity(Intent intent) {
        this.startActivity(intent);
    }

    public void startAnimActivity(Class<?> cla) {
        this.startActivity(new Intent(getActivity(), cla));
    }

    // 按钮模拟心脏跳动
    public void playHeartbeatAnimation(final View imageView) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.4f));

        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(0.5f, 1.0f, 0.5f,
                        1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.addAnimation(new AlphaAnimation(0.4f, 1.0f));

                animationSet.setDuration(600);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);

                // 实现心跳的View
                imageView.startAnimation(animationSet);
            }
        });

        // 实现心跳的View
        imageView.startAnimation(animationSet);
    }


    public void showLog(String msg) {
        Log.i("life", msg);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}

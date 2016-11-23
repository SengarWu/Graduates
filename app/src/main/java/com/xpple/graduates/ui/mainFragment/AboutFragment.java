package com.xpple.graduates.ui.mainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xpple.graduates.R;
import com.xpple.graduates.util.SpSettingsUtil;
import com.xpple.graduates.view.BaseFragment;

import net.slidingmenu.tools.st.SpotManager;
import net.slidingmenu.tools.video.VideoAdManager;
import net.slidingmenu.tools.video.listener.VideoAdListener;


public class AboutFragment extends BaseFragment implements View.OnClickListener {

    public AboutFragment() {
    }

    private static AboutFragment instance = new AboutFragment();

    public static AboutFragment newInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_about, container, false);
        ImageView app_logo = (ImageView) parentView.findViewById(R.id.app_logo);
        app_logo.setOnClickListener(this);
        return parentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SpSettingsUtil mSharedSettingsUtil = mApplication.getSpSettingsUtil();
        boolean isUserSuggest = mSharedSettingsUtil.isUserSuggest();
        if (!isUserSuggest) {
            // 设置插屏竖屏展示与展示动画设置
            SpotManager.getInstance(getActivity()).setSpotOrientation(
                    SpotManager.ORIENTATION_PORTRAIT);
            // 插屏广告展示测试
            SpotManager.getInstance(getActivity()).showSpotAds(getActivity());
        }
    }


    @Override
    public void onClick(View v) {
        playHeartbeatAnimation(v);
        VideoAdManager.getInstance(getActivity()).showVideo(getActivity(),
                new VideoAdListener() {
                    // 视频播放失败
                    @Override
                    public void onVideoPlayFail() {
                    }

                    // 视频播放完成
                    @Override
                    public void onVideoPlayComplete() {
                        showToast("您获得了1个金币的奖励", true);
                    }

                    // 视频播放完成的记录向服务器发送是否成功
                    @Override
                    public void onVideoCallback(boolean callback) {
                        // 视屏播放记录发送是否回调成功
                    }

                    // 视频播放中途退出
                    @Override
                    public void onVideoPlayInterrupt() {
                        showToast("视频未播放完成，无法获取奖励", false);
                    }

                    @Override
                    public void onDownloadComplete(String id) {
                    }

                    @Override
                    public void onNewApkDownloadStart() {

                    }

                    @Override
                    public void onVideoLoadComplete() {

                    }

                });
    }
}

package com.xpple.graduates.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xpple.graduates.R;
import com.xpple.graduates.util.SpSettingsUtil;
import com.xpple.graduates.view.BaseActivity;
import com.xpple.graduates.view.parallaxpager.ParallaxContainer;


public class GuideActivity extends BaseActivity implements View.OnClickListener {
    ImageView iv_man;
    ImageView iv_start, iv_login_0, iv_login_1, iv_login_2;
    ParallaxContainer parallaxContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        /**
         * 动画支持11以上sdk,11以下默认不显示动画
         * 若需要支持11以下动画,也可导入https://github.com/JakeWharton/NineOldAndroids
         */
        iv_man = (ImageView) findViewById(R.id.iv_man);
        parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);

        if (parallaxContainer != null) {
            parallaxContainer.setImage(iv_man);
            parallaxContainer.setLooping(false);

            iv_man.setVisibility(View.VISIBLE);
            parallaxContainer.setupChildren(getLayoutInflater(),
                    R.layout.view_intro_1, R.layout.view_intro_2,
                    R.layout.view_intro_3, R.layout.view_intro_4,
                    R.layout.view_intro_5, R.layout.view_login);
        }

        iv_start = (ImageView) findViewById(R.id.iv_start);
        iv_login_0 = (ImageView) findViewById(R.id.iv_login_0);
        iv_login_1 = (ImageView) findViewById(R.id.iv_login_1);
        iv_login_2 = (ImageView) findViewById(R.id.iv_login_2);
        iv_start.setOnClickListener(this);
        iv_login_0.setOnClickListener(this);
        iv_login_1.setOnClickListener(this);
        iv_login_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SpSettingsUtil mSharedSettingsUtil = mApplication.getSpSettingsUtil();
        mSharedSettingsUtil.setAllowFirstEnable(false);
        startAnimActivity(MainActivity.class);
        finish();
    }
}

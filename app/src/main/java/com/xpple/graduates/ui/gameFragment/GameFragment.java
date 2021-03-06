package com.xpple.graduates.ui.gameFragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xpple.graduates.CustomApplication;
import com.xpple.graduates.R;
import com.xpple.graduates.ui.MainActivity;
import com.xpple.graduates.ui.OverActivity;
import com.xpple.graduates.util.SpSaveUtil;
import com.xpple.graduates.util.SpScoreUtil;
import com.xpple.graduates.view.BaseFragment;
import com.xpple.graduates.view.NiftyDialogBuilder;
import com.xpple.graduates.view.SolarSystem;

import java.util.Random;

public class GameFragment extends BaseFragment implements
        View.OnClickListener {
    private View parentView;
    private SpScoreUtil mSharedScoreUtil;
    private SpSaveUtil mSharedSaveUtil;
    private ImageView stock, position, house, car, partner, lottery, btn_back;
    private TextView mHealthyValueView, mMoneyValueView, mAbilityValueView,
            mExperienceValueView, mHappyValueView, mMoralityValueView,
            mCommunicationValueView, mAgeValueView,
            mPositionView, mCarView, mHouseView, mPartnerView, mMonthValueView, mTimeValueView;
    private Integer mStockValue, mIndexStockValue, mIndexHouseValue, mIncomeValue,
            mCommunicationMonthlyValue, mHappyMonthlyValue,
            mLoadValue, mHealthyValue, mMoneyValue, mAbilityValue,
            mExperienceValue, mHappyValue, mMoralityValue,
            mCommunicationValue, mAgeValue, mPositionValue, mCarValue, mHouseValue,
            mPartnerValue, mTimeValue, mMonthValue;
    private Button btn_save, btn_load, btn_go_next_month;
    private SolarSystem solarSystem;
    private static String[] mPositionString = {"国内中小公司基层员工", "国内知名公司基层员工", "海外驻华公司基层员工",
            "国内中小公司基层主管", "国内知名公司基层主管", "海外驻华公司基层主管",
            "国内中小公司中层经理", "国内知名公司中层经理", "海外驻华公司中层经理",
            "国内中小公司高层老总", "国内知名公司高层老总", "海外驻华公司高层老总"};
    private static String[] mCarString = {"你还没有买车", "你有一辆小型节油低档车", "你有一辆经济实用中档车",
            "你有一辆豪华舒适中档车", "你有一辆超豪华享受高档车", "你有一辆时尚拉风跑车"};
    private static String[] mHouseString = {"你还没有买房", "你有1室1厅的房子"
            , "你有2室1厅的房子", "你有2室2厅的房子", "你有3室1厅的房子", "你有3室2厅的房子", "你有4室2厅的房子",
            "你有市郊豪华小别墅", "你有城区超豪华别墅"};
    private static String[] mPartnerString = {"你还没有女朋友", "你的女友是施施", "你的女友是阿禅",
            "你的女友是昭君", "你的女友是玉环", "你的女友是圆圆", "你的女友是香香",
            "你的女友是十娘", "你的女友是小小", "你的女友是飞燕", "你的女友是莺莺"};

    public GameFragment() {

    }

    private static GameFragment instance = new GameFragment();

    public static GameFragment newInstance() {
        return instance;
    }

    private AnimationDrawable animationDrawable;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 101)
                animationDrawable.stop();
            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_game, container, false);
        setUpViews();
        return parentView;
    }


    private void setUpViews() {
        mHealthyValueView = (TextView) parentView.findViewById(R.id.tv_healthy);
        mMoneyValueView = (TextView) parentView.findViewById(R.id.tv_money);
        mAbilityValueView = (TextView) parentView.findViewById(R.id.tv_ability);
        mExperienceValueView = (TextView) parentView.findViewById(R.id.tv_experience);
        mHappyValueView = (TextView) parentView.findViewById(R.id.tv_happy);
        mMoralityValueView = (TextView) parentView.findViewById(R.id.tv_morality);
        mCommunicationValueView = (TextView) parentView.findViewById(R.id.tv_communication);
        mAgeValueView = (TextView) parentView.findViewById(R.id.tv_age);
        mPositionView = (TextView) parentView.findViewById(R.id.tv_position);
        mCarView = (TextView) parentView.findViewById(R.id.tv_car);
        mHouseView = (TextView) parentView.findViewById(R.id.tv_house);
        mPartnerView = (TextView) parentView.findViewById(R.id.tv_partner);
        mMonthValueView = (TextView) parentView.findViewById(R.id.tv_month);
        mTimeValueView = (TextView) parentView.findViewById(R.id.tv_time);

        stock = (ImageView) parentView.findViewById(R.id.stock);
        position = (ImageView) parentView.findViewById(R.id.position);
        house = (ImageView) parentView.findViewById(R.id.house);
        car = (ImageView) parentView.findViewById(R.id.car);
        partner = (ImageView) parentView.findViewById(R.id.partner);
        lottery = (ImageView) parentView.findViewById(R.id.lottery);
        btn_back = (ImageView) parentView.findViewById(R.id.btn_exit);
        btn_save = (Button) parentView.findViewById(R.id.btn_save);
        btn_load = (Button) parentView.findViewById(R.id.btn_load);
        btn_go_next_month = (Button) parentView.findViewById(R.id.btn_go_next_month);
        setSolarSystem();
        setListener();
    }

    private void setSolarSystem() {
        RelativeLayout relative = (RelativeLayout) parentView
                .findViewById(R.id.relative);
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
        ViewGroup.LayoutParams lp = relative.getLayoutParams();
        lp.width = screenWidth / 3;
        lp.height = screenWidth / 3;
        relative.setLayoutParams(lp);
        relative.setBackgroundResource(R.drawable.animation_rect_list);
        animationDrawable = (AnimationDrawable) relative.getBackground();
        solarSystem = (SolarSystem) parentView
                .findViewById(R.id.ss);
        int childCount = solarSystem.getChildCount();
        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(
                screenWidth / 5, screenWidth / 5);

        for (int i = 0; i < childCount - 1; i++) {
            solarSystem.getChildAt(i).setLayoutParams(tvParams);
        }
        solarSystem.setRadius(screenWidth / 3);
        solarSystem.setRotaMenu(true);
        solarSystem.toggleMenu(300);
        solarSystem.setOnMenuStatus(new SolarSystem.MenuStatus() {
            @Override
            public void openMenu() {
                animationDrawable.start();
                handler.sendEmptyMessageDelayed(101, 600);
            }

            @Override
            public void closeMenu() {
                animationDrawable.start();
                handler.sendEmptyMessageDelayed(101, 600);
            }
        });
    }

    private void setListener() {
        stock.setOnClickListener(this);
        position.setOnClickListener(this);
        house.setOnClickListener(this);
        car.setOnClickListener(this);
        partner.setOnClickListener(this);
        lottery.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_load.setOnClickListener(this);
        btn_go_next_month.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setMValue();
        setClickAble();
        setSsItemChoose();
    }

    private void setMValue() {
        mSharedSaveUtil = mApplication.getSpSaveUtil();
        mSharedScoreUtil = mApplication.getSpScoreUtil();
        mLoadValue = mSharedScoreUtil.getLoad();
        mHealthyValue = mSharedScoreUtil.getHealthy();
        mMoneyValue = mSharedScoreUtil.getMoney();
        mAbilityValue = mSharedScoreUtil.getAbility();
        mExperienceValue = mSharedScoreUtil.getExperience();
        mHappyValue = mSharedScoreUtil.getHappy();
        mMoralityValue = mSharedScoreUtil.getMorality();
        mCommunicationValue = mSharedScoreUtil.getCommunication();
        mMonthValue = mSharedScoreUtil.getMonth();
        mTimeValue = mSharedScoreUtil.getTime();
        mCommunicationMonthlyValue = mSharedScoreUtil.getCommunicationMonthly();
        mHappyMonthlyValue = mSharedScoreUtil.getHappyMonthly();
        mAgeValue = 22 + (96 - mMonthValue) / 12;
        mCarValue = mSharedScoreUtil.getCar();
        mPartnerValue = mSharedScoreUtil.getPartner();
        mPositionValue = mSharedScoreUtil.getPosition();
        mHouseValue = mSharedScoreUtil.getHouse();
        mIncomeValue = mSharedScoreUtil.getIncome();
        mStockValue = mSharedScoreUtil.getStock();
        mIndexStockValue = mSharedScoreUtil.getIndexStock();
        mIndexHouseValue = mSharedScoreUtil.getIndexHouse();
        mHealthyValueView.setText("" + mHealthyValue);
        mMoneyValueView.setText("" + mMoneyValue);
        mAbilityValueView.setText("" + mAbilityValue);
        mExperienceValueView.setText("" + mExperienceValue);
        mHappyValueView.setText("" + mHappyValue);
        mMoralityValueView.setText("" + mMoralityValue);
        mCommunicationValueView.setText("" + mCommunicationValue);
        mMonthValueView.setText("" + mMonthValue);
        mTimeValueView.setText("" + mTimeValue);
        mAgeValueView.setText("" + mAgeValue);
        mPositionView.setText(mPositionString[mPositionValue]);
        mCarView.setText(mCarString[mCarValue]);
        mHouseView.setText(mHouseString[mHouseValue]);
        mPartnerView.setText(mPartnerString[mPartnerValue]);
    }

    private void setClickAble() {
        Boolean isCar = mSharedScoreUtil.isCar();
        car.setClickable(!isCar);
        car.setImageResource(isCar ? R.mipmap.qiche_0 : R.mipmap.qiche_1);

        Boolean isHouse = mSharedScoreUtil.isHouse();
        house.setClickable(!isHouse);
        house.setImageResource(isHouse ? R.mipmap.fangchan_0 : R.mipmap.fangchan_1);

        Boolean isPartner = mSharedScoreUtil.isPartner();
        partner.setClickable(!isPartner);
        partner.setImageResource(isPartner ? R.mipmap.hongniang_0 : R.mipmap.hongniang_1);

        Boolean isPosition = mSharedScoreUtil.isPosition();
        position.setClickable(!isPosition);
        position.setImageResource(isPosition ? R.mipmap.rencai_0 : R.mipmap.rencai_1);

        Boolean isLottery = mSharedScoreUtil.isLottery();
        lottery.setClickable(!isLottery);
        lottery.setImageResource(isLottery ? R.mipmap.caipiao_0 : R.mipmap.caipiao_1);

        Boolean isStock = mSharedScoreUtil.isStock();
        stock.setClickable(!isStock);
        stock.setImageResource(isStock ? R.mipmap.gupiao_0 : R.mipmap.gupiao_1);
    }

    private void setSsItemChoose() {
        solarSystem.setOnMenuItemClickListener(new SolarSystem.onMenuItemClickListener() {
            @Override
            public void onItem(View view, int position) {
                String tag = (String) view.getTag();
                switch (tag) {
                    // 阅读专业书籍：能力+6，经验+7，快乐-2，金钱-200，时间-30
                    case "yd":
                        if (checkValue(30, 200)) {
                            CustomApplication.playSound(R.raw.read);
                            showImageViewDialog(R.string.plan, R.string.plan_0, R.mipmap.rd);
                            setValue(0, -200, 6, 7, -2, 0, 0, -30);
                            mSharedScoreUtil.setYd(1);
                            if (mSharedScoreUtil.getYd().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_0,
                                        R.string.opportunity_0_a,
                                        R.string.opportunity_0_b,
                                        R.string.opportunity_0_c,
                                        R.string.opportunity_0_d,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 博览其他书籍：能力+4，经验+5，快乐+2，金钱-100，时间-30
                    case "bl":
                        if (checkValue(30, 100)) {
                            CustomApplication.playSound(R.raw.read);
                            showImageViewDialog(R.string.plan, R.string.plan_1, R.mipmap.bl);
                            setValue(0, -100, 4, 5, 2, 0, 0, -30);
                            mSharedScoreUtil.setBl(1);
                            if (mSharedScoreUtil.getBl().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_1,
                                        R.string.opportunity_1_a,
                                        R.string.opportunity_1_b,
                                        R.string.opportunity_1_c,
                                        R.string.opportunity_1_d,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 同事疯狂派对：快乐+5，交际+6，经验+4，道德-2，健康-2，金钱-1000，时间-30
                    case "ts":
                        if (checkValue(30, 1000)) {
                            CustomApplication.playSound(R.raw.bar);
                            showImageViewDialog(R.string.plan, R.string.plan_2, R.mipmap.ts);
                            setValue(-2, -1000, 0, 4, 5, -2, 6, -30);
                            mSharedScoreUtil.setTs(1);
                            if (mSharedScoreUtil.getTs().equals(20)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_2,
                                        R.string.opportunity_2_b,
                                        R.string.opportunity_2_c,
                                        R.string.opportunity_2_a,
                                        R.string.opportunity_2_d,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub

                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 同学朋友聚会：快乐+3，交际+4 经验+2，金钱-600，时间-30
                    case "tx":
                        if (checkValue(30, 600)) {
                            CustomApplication.playSound(R.raw.classmate);
                            showImageViewDialog(R.string.plan, R.string.plan_3, R.mipmap.tx);
                            setValue(0, -600, 0, 2, 3, 0, 4, -30);
                            mSharedScoreUtil.setPy(1);
                            if (mSharedScoreUtil.getPy().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_3,
                                        R.string.opportunity_3_a,
                                        R.string.opportunity_3_d,
                                        R.string.opportunity_3_c,
                                        R.string.opportunity_3_b,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 逛街购物吃饭：快乐+7，经验+2，金钱-1000，时间-30
                    case "gj":
                        if (checkValue(30, 1000)) {
                            CustomApplication.playSound(R.raw.shopping);
                            showImageViewDialog(R.string.plan, R.string.plan_4, R.mipmap.gj);
                            setValue(0, -1000, 0, 2, 7, 0, 0, -30);
                            mSharedScoreUtil.setGj(1);
                            if (mSharedScoreUtil.getGj().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_4,
                                        R.string.opportunity_4_c,
                                        R.string.opportunity_4_a,
                                        R.string.opportunity_4_d,
                                        R.string.opportunity_4_b,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 出门旅游度假：健康+4，能力+4，经验+4，快乐+10，交际+2，金钱-3000，时间-60
                    case "cm":
                        if (checkValue(60, 3000)) {
                            CustomApplication.playSound(R.raw.tour);
                            showImageViewDialog(R.string.plan, R.string.plan_5, R.mipmap.cm);
                            setValue(4, -3000, 4, 4, 10, 0, 2, -60);
                            mSharedScoreUtil.setCm(1);
                            if (mSharedScoreUtil.getCm().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_5,
                                        R.string.opportunity_5_d,
                                        R.string.opportunity_5_a,
                                        R.string.opportunity_5_b,
                                        R.string.opportunity_5_c,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        });
                            }
                        }
                        break;
                    // 参加学习培训：能力+20，经验+20，快乐-4，健康-2，金钱-2000，时间-60
                    case "cj":
                        if (checkValue(60, 2000)) {
                            CustomApplication.playSound(R.raw.train);
                            showImageViewDialog(R.string.plan, R.string.plan_6, R.mipmap.cj);
                            setValue(-2, -2000, 20, 20, -4, 0, 0, -60);
                            mSharedScoreUtil.setCj(1);
                            if (mSharedScoreUtil.getCj().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_6,
                                        R.string.opportunity_6_c,
                                        R.string.opportunity_6_b,
                                        R.string.opportunity_6_a,
                                        R.string.opportunity_6_d,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 在家睡觉休息：健康+2，快乐+2，时间-20
                    case "zj":
                        if (checkValue(20, -5000000)) {
                            CustomApplication.playSound(R.raw.sleep);
                            showImageViewDialog(R.string.plan, R.string.plan_7, R.mipmap.zj);
                            setValue(2, 0, 0, 0, 2, 0, 0, -20);
                            mSharedScoreUtil.setZj(1);
                            if (mSharedScoreUtil.getZj().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_7,
                                        R.string.opportunity_7_a,
                                        R.string.opportunity_7_c,
                                        R.string.opportunity_7_b,
                                        R.string.opportunity_7_d,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 加班或做兼职：能力+10，经验+10，快乐-8，健康-5，金钱+1500，时间-60
                    case "jb":
                        if (checkValue(60, -5000000)) {
                            CustomApplication.playSound(R.raw.hammer);
                            showImageViewDialog(R.string.plan, R.string.plan_8, R.mipmap.jb);
                            setValue(-5, 1500, 10, 10, -8, 0, 0, -60);
                            mSharedScoreUtil.setJb(1);
                            if (mSharedScoreUtil.getJb().equals(20)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_8,
                                        R.string.opportunity_8_d,
                                        R.string.opportunity_8_b,
                                        R.string.opportunity_8_c,
                                        R.string.opportunity_8_a,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 上网休闲娱乐：快乐+8，经验+2，交际-1，健康-2，金钱-100，时间-60
                    case "sw":
                        if (checkValue(60, -100)) {
                            CustomApplication.playSound(R.raw.internet);
                            showImageViewDialog(R.string.plan, R.string.plan_9, R.mipmap.sw);
                            setValue(-2, -100, 0, 2, 8, 0, -1, -60);
                            mSharedScoreUtil.setSw(1);
                            if (mSharedScoreUtil.getSw().equals(20)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_9,
                                        R.string.opportunity_9_b,
                                        R.string.opportunity_9_a,
                                        R.string.opportunity_9_d,
                                        R.string.opportunity_9_c,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        });
                            }
                        }
                        break;
                    // 体育健身运动：能力+3，健康+5，快乐+2，金钱-100，时间-30
                    case "ty":
                        if (checkValue(30, -100)) {
                            CustomApplication.playSound(R.raw.exercise);
                            showImageViewDialog(R.string.plan, R.string.plan_10, R.mipmap.ty);
                            setValue(5, -100, 3, 0, 2, 0, 0, -30);
                            mSharedScoreUtil.setTy(1);
                            if (mSharedScoreUtil.getTy().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_10,
                                        R.string.opportunity_10_d,
                                        R.string.opportunity_10_c,
                                        R.string.opportunity_10_a,
                                        R.string.opportunity_10_b,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        });
                            }
                        }
                        break;
                    // 回家看望父母：快乐+2，道德+2，健康+1，金钱-300，时间-30
                    case "hj":
                        if (checkValue(30, -300)) {
                            CustomApplication.playSound(R.raw.parents);
                            showImageViewDialog(R.string.plan, R.string.plan_11, R.mipmap.hj);
                            setValue(1, -300, 0, 0, 2, 2, 0, -30);
                            mSharedScoreUtil.setHj(1);
                            if (mSharedScoreUtil.getHj().equals(30)) {
                                showRgChooseViewDialog(R.string.opportunity,
                                        R.string.opportunity_11,
                                        R.string.opportunity_11_a,
                                        R.string.opportunity_11_b,
                                        R.string.opportunity_11_c,
                                        R.string.opportunity_11_d,
                                        new Rb_a_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.both_add, true);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                                mSharedScoreUtil.setLove(1);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        }, new Rb_b_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.love_add, true);
                                                mSharedScoreUtil.setLove(1);
                                            }
                                        }, new Rb_c_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.no_add, true);
                                            }
                                        }, new Rb_d_onClickListener() {
                                            @Override
                                            public void onClick() {
                                                // TODO Auto-generated method stub
                                                showToast(R.string.career_add, true);
                                                mSharedScoreUtil.setCareer(1);
                                            }
                                        });
                            }
                        }
                        break;
                }
            }
        });
        solarSystem.setOnMenuItemLongClickListener(new SolarSystem.onMenuItemLongClickListener() {
            @Override
            public void onItem(View view, int position) {
                String tag = (String) view.getTag();
                switch (tag) {
                    case "yd":
                        showToast(R.string.plan_time_0, true);
                        break;
                    case "bl":
                        showToast(R.string.plan_time_1, true);
                        break;
                    case "ts":
                        showToast(R.string.plan_time_2, true);
                        break;
                    case "tx":
                        showToast(R.string.plan_time_3, true);
                        break;
                    case "gj":
                        showToast(R.string.plan_time_4, true);
                        break;
                    case "cm":
                        showToast(R.string.plan_time_5, true);
                        break;
                    case "cj":
                        showToast(R.string.plan_time_6, true);
                        break;
                    case "zj":
                        showToast(R.string.plan_time_7, true);
                        break;
                    case "jb":
                        showToast(R.string.plan_time_8, true);
                        break;
                    case "sw":
                        showToast(R.string.plan_time_9, true);
                        break;
                    case "ty":
                        showToast(R.string.plan_time_10, true);
                        break;
                    case "hj":
                        showToast(R.string.plan_time_11, true);
                        break;
                }
            }
        });
    }

    public void setValue(Integer mHealthyValue, Integer mMoneyValue, Integer mAbilityValue, Integer mExperienceValue,
                         Integer mHappyValue, Integer mMoralityValue, Integer mCommunicationValue, Integer mTimeValue) {
        mSharedScoreUtil.setHealthy(mHealthyValue);
        mSharedScoreUtil.setMoney(mMoneyValue);
        mSharedScoreUtil.setAbility(mAbilityValue);
        mSharedScoreUtil.setExperience(mExperienceValue);
        mSharedScoreUtil.setHappy(mHappyValue);
        mSharedScoreUtil.setMorality(mMoralityValue);
        mSharedScoreUtil.setCommunication(mCommunicationValue);
        mSharedScoreUtil.setTime(mTimeValue);
        onActivityCreated(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // 屏蔽back
                    showReturnDialog();
                    return true;
                }
                return false;
            }
        });
    }

    private boolean checkValue(Integer nTime, Integer nMoney) {
        if (mTimeValue < nTime) {
            showToast(R.string.time_run_out, false);
            return false;
        } else if (mMoneyValue < nMoney) {
            showToast(R.string.money_run_out, false);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        playHeartbeatAnimation(view);
        switch (view.getId()) {
            case R.id.stock:
                mSharedScoreUtil.setStock(true);
                CustomApplication.playSound(R.raw.button_0);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_game,
                                StockFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.position:
                CustomApplication.playSound(R.raw.button_0);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_game,
                                PositionFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.house:
                mSharedScoreUtil.setHouse(true);
                CustomApplication.playSound(R.raw.button_0);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_game,
                                HouseFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.car:
                CustomApplication.playSound(R.raw.button_0);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_game,
                                CarFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.partner:
                CustomApplication.playSound(R.raw.button_0);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_game,
                                PartnerFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.lottery:
                CustomApplication.playSound(R.raw.button_0);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_game,
                                LotteryFragment.newInstance()).addToBackStack(null).commit();
                break;
            case R.id.btn_exit:
                showReturnDialog();
                break;
            case R.id.btn_save:
                goSave();
                break;
            case R.id.btn_load:
                if (mSharedSaveUtil.isSave()) {
                    showLoadDialog();
                } else {
                    showToast(R.string.no_save, false);
                }
                break;
            case R.id.btn_go_next_month:
                CustomApplication.playSound(R.raw.next_month);
                if (mHappyValue < 0 || mMoralityValue < 0 || mHealthyValue < 0) {
                    CustomApplication.playSound(R.raw.die);
                    toGameOver();
                } else if (mMonthValue <= 0) {
                    toGameOver();
                } else {
                    setMonthValue();
//                  setRandomStroy();
                    onActivityCreated(null);
                }
                break;
            default:
                break;
        }
    }


    private void setRandomStroy() {
        //随机事件指数
        Random random_s = new Random();
        Integer s = random_s.nextInt(21); //0~20
        showImageChooseViewDialog("随机事件", "XXXX", R.mipmap.ic_launcher, new Button1onClickListener() {
            @Override
            public void onClick() {
                // TODO Auto-generated method stub
                showToast("随机事件 否", false);
            }
        }, new Button2onClickListener() {
            @Override
            public void onClick() {
                // TODO Auto-generated method stub
                showToast("随机事件 是", true);
            }
        });
    }

    private void toGameOver() {
        startAnimActivity(OverActivity.class);
        getActivity().finish();
    }

    private void setMonthValue() {
        mSharedScoreUtil.setStock(false);
        mSharedScoreUtil.setPosition(false);
        mSharedScoreUtil.setHouse(false);
        mSharedScoreUtil.setCar(false);
        mSharedScoreUtil.setPartner(false);
        mSharedScoreUtil.setLottery(false);
        mSharedScoreUtil.setMonth(-1);
        mSharedScoreUtil.setTime(0);
        mSharedScoreUtil.setMoney(mIncomeValue);
        mSharedScoreUtil.setHealthy(-2);
        mSharedScoreUtil.setAbility(11);
        mSharedScoreUtil.setExperience(11);
        mSharedScoreUtil.setHappy(mHappyMonthlyValue);
        mSharedScoreUtil.setCommunication(mCommunicationMonthlyValue);
        //随机股票指数
        Random random_x = new Random();
        Integer x = random_x.nextInt(21); //0~20,0~9 (┬＿┬)↘ 跌，10平，1~20 (￣︶￣)↗ 涨
        if (x < 20) {
            Random random_y = new Random();
            Integer y = random_y.nextInt(5);
            if (y < 3) {
                x = x + 1;
            }
        }
        x = x + 90;
        mSharedScoreUtil.setRandomStock(x);
        mSharedScoreUtil.setIndexStock(mIndexStockValue * x / 100);
        mSharedScoreUtil.setStock(mStockValue * (x - 100) / 100);

        //随机房价指数
        Random random_m = new Random();
        Integer m = random_m.nextInt(21); //0~30,0~9 (┬＿┬)↘ 跌，10平，11~20 (￣︶￣)↗ 涨
        if (m < 20) {
            Random random_n = new Random();
            Integer n = random_n.nextInt(5);
            if (n < 3) {
                m = m + 1;
            }
        }
        m = m + 90;
        mSharedScoreUtil.setIndexHouse(mIndexHouseValue * m / 100);
    }


    private void showLoadDialog() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder
                .getInstance(getActivity());
        dialogBuilder.withTitle(R.string.load_or_un)
                .withMessage("您的数据将恢复到上次保存的值（可读档次数共9次，当前还剩" + mLoadValue + "次，请慎重使用）。").isCancelable(true)
                .withDuration(500).withButtonCancle().withButtonOk().setCustomView(0, getActivity())
                .setButtonCancleClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.getDismiss();
                    }
                }).setButtonOk(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                goLoad();
            }
        }).show();
    }

    private void goSave() {
        CustomApplication.playSound(R.raw.appreciation);
        mSharedSaveUtil.setSave(true);
        mSharedSaveUtil.setHealthy(mHealthyValue);
        mSharedSaveUtil.setMoney(mMoneyValue);
        mSharedSaveUtil.setAbility(mAbilityValue);
        mSharedSaveUtil.setExperience(mExperienceValue);
        mSharedSaveUtil.setHappy(mHappyValue);
        mSharedSaveUtil.setMorality(mMoralityValue);
        mSharedSaveUtil.setCommunication(mCommunicationValue);
        mSharedSaveUtil.setMonth(mMonthValue);
        mSharedSaveUtil.setTime(mTimeValue);
        mSharedSaveUtil.setStock(mStockValue);
        mSharedSaveUtil.setPosition(mPositionValue);
        mSharedSaveUtil.setHouse(mHouseValue);
        mSharedSaveUtil.setCar(mCarValue);
        mSharedSaveUtil.setPartner(mPartnerValue);
        mSharedSaveUtil.setCareer(mSharedScoreUtil.getCareer());
        mSharedSaveUtil.setLove(mSharedScoreUtil.getLove());
        mSharedSaveUtil.setRandomStock(mSharedScoreUtil.getRandomStock());
        mSharedSaveUtil.setIndexStock(mIndexStockValue);
        mSharedSaveUtil.setIndexHouse(mIndexHouseValue);
        mSharedSaveUtil.setPurity(mSharedScoreUtil.getPurity());
        mSharedSaveUtil.setIncome(mIncomeValue);
        mSharedSaveUtil.setHappyMonthly(mHappyMonthlyValue);
        mSharedSaveUtil.setCommunicationMonthly(mCommunicationMonthlyValue);
        mSharedSaveUtil.setStock(mSharedScoreUtil.isStock());
        mSharedSaveUtil.setPosition(mSharedScoreUtil.isPosition());
        mSharedSaveUtil.setHouse(mSharedScoreUtil.isHouse());
        mSharedSaveUtil.setCar(mSharedScoreUtil.isCar());
        mSharedSaveUtil.setPartner(mSharedScoreUtil.isPartner());
        mSharedSaveUtil.setLottery(mSharedScoreUtil.isLottery());
        mSharedSaveUtil.setYd(mSharedScoreUtil.getYd());
        mSharedSaveUtil.setBl(mSharedScoreUtil.getBl());
        mSharedSaveUtil.setTs(mSharedScoreUtil.getTs());
        mSharedSaveUtil.setPy(mSharedScoreUtil.getPy());
        mSharedSaveUtil.setGj(mSharedScoreUtil.getGj());
        mSharedSaveUtil.setCm(mSharedScoreUtil.getCm());
        mSharedSaveUtil.setCj(mSharedScoreUtil.getCj());
        mSharedSaveUtil.setZj(mSharedScoreUtil.getZj());
        mSharedSaveUtil.setJb(mSharedScoreUtil.getJb());
        mSharedSaveUtil.setSw(mSharedScoreUtil.getSw());
        mSharedSaveUtil.setTy(mSharedScoreUtil.getTy());
        mSharedSaveUtil.setHj(mSharedScoreUtil.getHj());
        mSharedSaveUtil.setPartnerStory(mSharedScoreUtil.getPartnerStory());
        mSharedSaveUtil.setPartnerXy(mSharedScoreUtil.getPartnerXy());
        mSharedSaveUtil.setPartnerSs(mSharedScoreUtil.getPartnerSs());
        mSharedSaveUtil.setPartnerAc(mSharedScoreUtil.getPartnerAc());
        mSharedSaveUtil.setPartnerZj(mSharedScoreUtil.getPartnerZj());
        mSharedSaveUtil.setPartnerSn(mSharedScoreUtil.getPartnerSn());
        mSharedSaveUtil.setPartnerFy(mSharedScoreUtil.getPartnerFy());
        onActivityCreated(null);
        showToast(R.string.save_success, true);
    }

    private void goLoad() {

        if (mLoadValue.equals(0)) {
            showToast(R.string.load_times_run_out, false);
        } else {
            CustomApplication.playSound(R.raw.appreciation);
            Integer load = mSharedScoreUtil.getLoad();
            mSharedScoreUtil.cleanSharedPreference();
            mSharedScoreUtil.setScore(true);
            mSharedScoreUtil.setLoad(load - 1);
            mSharedScoreUtil.setHealthy(mSharedSaveUtil.getHealthy() - 200);
            mSharedScoreUtil.setMoney(mSharedSaveUtil.getMoney() - 2000);
            mSharedScoreUtil.setAbility(mSharedSaveUtil.getAbility() - 100);
            mSharedScoreUtil.setExperience(mSharedSaveUtil.getExperience() - 100);
            mSharedScoreUtil.setHappy(mSharedSaveUtil.getHappy() - 200);
            mSharedScoreUtil.setMorality(mSharedSaveUtil.getMorality() - 200);
            mSharedScoreUtil.setCommunication(mSharedSaveUtil.getCommunication() - 100);
            mSharedScoreUtil.setMonth(mSharedSaveUtil.getMonth() - 96);
            mSharedScoreUtil.setTime(mSharedSaveUtil.getTime() - 230);
            mSharedScoreUtil.setStock(mSharedSaveUtil.getStock());
            mSharedScoreUtil.setPosition(mSharedSaveUtil.getPosition());
            mSharedScoreUtil.setHouse(mSharedSaveUtil.getHouse());
            mSharedScoreUtil.setCar(mSharedSaveUtil.getCar());
            mSharedScoreUtil.setPartner(mSharedSaveUtil.getPartner());
            mSharedScoreUtil.setCareer(mSharedSaveUtil.getCareer());
            mSharedScoreUtil.setLove(mSharedSaveUtil.getLove());
            mSharedScoreUtil.setRandomStock(mSharedSaveUtil.getRandomStock() - 100);
            mSharedScoreUtil.setIndexStock(mSharedSaveUtil.getIndexStock() - 1700);
            mSharedScoreUtil.setIndexHouse(mSharedSaveUtil.getIndexHouse() - 100);
            mSharedScoreUtil.setPurity(mSharedSaveUtil.getPurity() - 100);
            mSharedScoreUtil.setIncome(mSharedSaveUtil.getIncome() - 2200);
            mSharedScoreUtil.setCommunicationMonthly(mSharedSaveUtil.getCommunicationMonthly());
            mSharedScoreUtil.setHappyMonthly(mSharedSaveUtil.getHappyMonthly() - 2);
            mSharedScoreUtil.setStock(mSharedSaveUtil.isStock());
            mSharedScoreUtil.setPosition(mSharedSaveUtil.isPosition());
            mSharedScoreUtil.setHouse(mSharedSaveUtil.isHouse());
            mSharedScoreUtil.setCar(mSharedSaveUtil.isCar());
            mSharedScoreUtil.setPartner(mSharedSaveUtil.isPartner());
            mSharedScoreUtil.setLottery(mSharedSaveUtil.isLottery());
            mSharedScoreUtil.setYd(mSharedSaveUtil.getYd());
            mSharedScoreUtil.setBl(mSharedSaveUtil.getBl());
            mSharedScoreUtil.setTs(mSharedSaveUtil.getTs());
            mSharedScoreUtil.setPy(mSharedSaveUtil.getPy());
            mSharedScoreUtil.setGj(mSharedSaveUtil.getGj());
            mSharedScoreUtil.setCm(mSharedSaveUtil.getCm());
            mSharedScoreUtil.setCj(mSharedSaveUtil.getCj());
            mSharedScoreUtil.setZj(mSharedSaveUtil.getZj());
            mSharedScoreUtil.setJb(mSharedSaveUtil.getJb());
            mSharedScoreUtil.setSw(mSharedSaveUtil.getSw());
            mSharedScoreUtil.setTy(mSharedSaveUtil.getTy());
            mSharedScoreUtil.setHj(mSharedSaveUtil.getHj());
            mSharedScoreUtil.setPartnerStory(mSharedSaveUtil.getPartnerStory());
            mSharedScoreUtil.setPartnerXy(mSharedSaveUtil.getPartnerXy());
            mSharedScoreUtil.setPartnerSs(mSharedSaveUtil.getPartnerSs());
            mSharedScoreUtil.setPartnerAc(mSharedSaveUtil.getPartnerAc());
            mSharedScoreUtil.setPartnerZj(mSharedSaveUtil.getPartnerZj());
            mSharedScoreUtil.setPartnerSn(mSharedSaveUtil.getPartnerSn());
            mSharedScoreUtil.setPartnerFy(mSharedSaveUtil.getPartnerFy());
            onActivityCreated(null);
            showToast(R.string.load_success, true);
        }
    }

    private void showReturnDialog() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder
                .getInstance(getActivity());
        dialogBuilder.withTitle(R.string.return_main_or_un)
                .withMessage(R.string.return_main_or_un_0).isCancelable(true)
                .withDuration(500).withButtonCancle().withButtonOk()
                .setCustomView(0, getActivity())
                .setButtonCancleClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.getDismiss();
                    }
                }).setButtonOk(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.closeDialog(dialogBuilder);
                startAnimActivity(MainActivity.class);
                getActivity().finish();
            }
        }).show();
    }

    private void showImageViewDialog(int title, int message, int iv) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder
                .getInstance(getActivity());
        dialogBuilder.withTitle(title)
                .withMessage(message).withImageView(iv).isCancelable(true)
                .withDuration(500).withButtonOk()
                .setCustomView(0, getActivity())
                .setButtonOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.closeDialog(dialogBuilder);
                    }
                }).show();
    }

    private void showImageChooseViewDialog(String title, String message, int iv,
                                           final Button1onClickListener Button1onClickListener, final Button2onClickListener Button2onClickListener) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder
                .getInstance(getActivity());
        dialogBuilder.withTitle(title)
                .withMessage(message).withImageView(iv).isCancelable(false)
                .withDuration(500).withButtonCancle().withButtonOk()
                .setCustomView(0, getActivity())
                .setButtonCancleClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button1onClickListener.onClick();
                        dialogBuilder.closeDialog(dialogBuilder);
                    }
                })
                .setButtonOk(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button2onClickListener.onClick();
                        dialogBuilder.closeDialog(dialogBuilder);
                    }
                }).show();
    }

    private void showRgChooseViewDialog(int title, int message,
                                        int a, int b, int c, int d,
                                        final Rb_a_onClickListener Rb_a_onClickListener,
                                        final Rb_b_onClickListener Rb_b_onClickListener,
                                        final Rb_c_onClickListener Rb_c_onClickListener,
                                        final Rb_d_onClickListener Rb_d_onClickListener) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder
                .getInstance(getActivity());
        dialogBuilder.withTitle(title).withRg(a, b, c, d).setRb_a(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rb_a_onClickListener.onClick();
                dialogBuilder.closeDialog(dialogBuilder);
            }
        }).setRb_b(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rb_b_onClickListener.onClick();
                dialogBuilder.closeDialog(dialogBuilder);
            }
        }).setRb_c(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rb_c_onClickListener.onClick();
                dialogBuilder.closeDialog(dialogBuilder);
            }
        }).setRb_d(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rb_d_onClickListener.onClick();
                dialogBuilder.closeDialog(dialogBuilder);
            }
        })
                .withMessage(message).isCancelable(false)
                .withDuration(500).setCustomView(0, getActivity()).show();
    }

    private interface Button1onClickListener {
        void onClick();
    }

    private interface Button2onClickListener {
        void onClick();
    }

    private interface Rb_a_onClickListener {
        void onClick();
    }

    private interface Rb_b_onClickListener {
        void onClick();
    }

    private interface Rb_c_onClickListener {
        void onClick();
    }

    private interface Rb_d_onClickListener {
        void onClick();
    }
}
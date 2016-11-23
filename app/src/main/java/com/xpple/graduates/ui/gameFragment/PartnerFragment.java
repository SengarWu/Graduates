package com.xpple.graduates.ui.gameFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xpple.graduates.CustomApplication;
import com.xpple.graduates.R;
import com.xpple.graduates.util.SpScoreUtil;
import com.xpple.graduates.view.BaseFragment;


public class PartnerFragment extends BaseFragment implements View.OnClickListener {
    private View parentView;
    private ImageView iv_partner, iv_partner_0, iv_partner_1, iv_partner_2, iv_partner_3, iv_partner_4, iv_partner_5, iv_partner_6, iv_partner_7, iv_partner_8, iv_partner_9;
    private TextView tv_partner_condition;
    private LinearLayout ll_partner_0, ll_partner_1;

    private SpScoreUtil mSharedScoreUtil;

    public PartnerFragment() {
    }

    private static PartnerFragment instance = new PartnerFragment();

    public static PartnerFragment newInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_partner, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        tv_partner_condition = (TextView) parentView.findViewById(R.id.tv_partner_condition);
        ll_partner_0 = (LinearLayout) parentView.findViewById(R.id.ll_partner_0);
        ll_partner_1 = (LinearLayout) parentView.findViewById(R.id.ll_partner_1);
        iv_partner = (ImageView) parentView.findViewById(R.id.iv_partner);
        iv_partner.setOnClickListener(this);
        iv_partner_0 = (ImageView) parentView.findViewById(R.id.iv_partner_0);
        iv_partner_1 = (ImageView) parentView.findViewById(R.id.iv_partner_1);
        iv_partner_2 = (ImageView) parentView.findViewById(R.id.iv_partner_2);
        iv_partner_3 = (ImageView) parentView.findViewById(R.id.iv_partner_3);
        iv_partner_4 = (ImageView) parentView.findViewById(R.id.iv_partner_4);
        iv_partner_5 = (ImageView) parentView.findViewById(R.id.iv_partner_5);
        iv_partner_6 = (ImageView) parentView.findViewById(R.id.iv_partner_6);
        iv_partner_7 = (ImageView) parentView.findViewById(R.id.iv_partner_7);
        iv_partner_8 = (ImageView) parentView.findViewById(R.id.iv_partner_8);
        iv_partner_9 = (ImageView) parentView.findViewById(R.id.iv_partner_9);
        setListener();
    }

    private void setListener() {
        iv_partner_0.setOnClickListener(this);
        iv_partner_1.setOnClickListener(this);
        iv_partner_2.setOnClickListener(this);
        iv_partner_3.setOnClickListener(this);
        iv_partner_4.setOnClickListener(this);
        iv_partner_5.setOnClickListener(this);
        iv_partner_6.setOnClickListener(this);
        iv_partner_7.setOnClickListener(this);
        iv_partner_8.setOnClickListener(this);
        iv_partner_9.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedScoreUtil = mApplication.getSpScoreUtil();
        Integer my_partner = mSharedScoreUtil.getPartner();
        switch (my_partner) {
            case 0:
                iv_partner_0.setClickable(false);
                break;
            case 1:
                iv_partner_1.setClickable(false);
                break;
            case 2:
                iv_partner_2.setClickable(false);
                break;
            case 3:
                iv_partner_3.setClickable(false);
                break;
            case 4:
                iv_partner_4.setClickable(false);
                break;
            case 5:
                iv_partner_5.setClickable(false);
                break;
            case 6:
                iv_partner_6.setClickable(false);
                break;
            case 7:
                iv_partner_7.setClickable(false);
                break;
            case 8:
                iv_partner_8.setClickable(false);
                break;
            case 9:
                iv_partner_9.setClickable(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        mSharedScoreUtil.setMoney(-1000);
        CustomApplication.playSound(R.raw.money);
        mSharedScoreUtil.setPartner(true);
        switch (v.getId()) {
            case R.id.iv_partner:
                ll_partner_0.setVisibility(View.GONE);
                ll_partner_1.setVisibility(View.GONE);
                tv_partner_condition.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_partner_0:
                if (mSharedScoreUtil.getHouse().equals(2) || mSharedScoreUtil.getHouse().equals(3)) {
                    mSharedScoreUtil.setPartner(0);
                } else {
                    showToast(R.string.partner_0_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_1:
                if (mSharedScoreUtil.getMoney() > 2000000) {
                    mSharedScoreUtil.setPartner(1);
                } else {
                    showToast(R.string.partner_1_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_2:
                if (mSharedScoreUtil.getPosition().equals(8) || mSharedScoreUtil.getPosition().equals(11)) {
                    mSharedScoreUtil.setPartner(2);
                } else {
                    showToast(R.string.partner_2_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_3:
                if (mSharedScoreUtil.getPosition().equals(10) || mSharedScoreUtil.getPosition().equals(11)) {
                    mSharedScoreUtil.setPartner(3);
                } else {
                    showToast(R.string.partner_3_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_4:
                if (mSharedScoreUtil.getCommunication() > 750) {
                    mSharedScoreUtil.setPartner(4);
                } else {
                    showToast(R.string.partner_4_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_5:
                if (mSharedScoreUtil.getCar().equals(3) || mSharedScoreUtil.getCar().equals(4)) {
                    mSharedScoreUtil.setPartner(5);
                } else {
                    showToast(R.string.partner_5_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_6:
                if (mSharedScoreUtil.getMorality() > 750) {
                    mSharedScoreUtil.setPartner(6);
                } else {
                    showToast(R.string.partner_6_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_7:
                if (mSharedScoreUtil.getHealthy() > 750) {
                    mSharedScoreUtil.setPartner(7);
                } else {
                    showToast(R.string.partner_7_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_8:
                if (mSharedScoreUtil.getHappy() > 750) {
                    mSharedScoreUtil.setPartner(8);
                } else {
                    showToast(R.string.partner_8_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.iv_partner_9:
                if (mSharedScoreUtil.getTime() < 24) {
                    mSharedScoreUtil.setPartner(9);
                } else {
                    showToast(R.string.partner_9_plus, false);
                }
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}

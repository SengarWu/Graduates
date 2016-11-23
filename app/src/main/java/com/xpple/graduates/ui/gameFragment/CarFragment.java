package com.xpple.graduates.ui.gameFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xpple.graduates.CustomApplication;
import com.xpple.graduates.R;
import com.xpple.graduates.util.SpScoreUtil;
import com.xpple.graduates.view.BaseFragment;

public class CarFragment extends BaseFragment implements View.OnClickListener {
    private View parentView;
    private Button btn_car_0, btn_car_1, btn_car_2, btn_car_3, btn_car_4;
    private SpScoreUtil mSharedScoreUtil;
    private Integer my_car;

    public CarFragment() {
    }

    private static CarFragment instance = new CarFragment();

    public static CarFragment newInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_car, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        btn_car_0 = (Button) parentView.findViewById(R.id.btn_car_0);
        btn_car_1 = (Button) parentView.findViewById(R.id.btn_car_1);
        btn_car_2 = (Button) parentView.findViewById(R.id.btn_car_2);
        btn_car_3 = (Button) parentView.findViewById(R.id.btn_car_3);
        btn_car_4 = (Button) parentView.findViewById(R.id.btn_car_4);
        setListener();
    }

    private void setListener() {
        btn_car_0.setOnClickListener(this);
        btn_car_1.setOnClickListener(this);
        btn_car_2.setOnClickListener(this);
        btn_car_3.setOnClickListener(this);
        btn_car_4.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedScoreUtil = mApplication.getSpScoreUtil();
        my_car = mSharedScoreUtil.getCar();
        onViewShow(my_car);
    }

    private void onViewShow(Integer my_car) {
        btn_car_0.setVisibility(View.INVISIBLE);
        btn_car_1.setVisibility(View.INVISIBLE);
        btn_car_2.setVisibility(View.INVISIBLE);
        btn_car_3.setVisibility(View.INVISIBLE);
        btn_car_4.setVisibility(View.INVISIBLE);
        switch (my_car) {
            case 0:
                btn_car_0.setVisibility(View.VISIBLE);
                btn_car_1.setVisibility(View.VISIBLE);
                btn_car_2.setVisibility(View.VISIBLE);
                btn_car_3.setVisibility(View.VISIBLE);
                btn_car_4.setVisibility(View.VISIBLE);
                btn_car_0.setText(R.string.buy);
                btn_car_1.setText(R.string.buy);
                btn_car_2.setText(R.string.buy);
                btn_car_3.setText(R.string.buy);
                btn_car_4.setText(R.string.buy);
                break;
            case 1:
                btn_car_0.setVisibility(View.VISIBLE);
                btn_car_0.setText(R.string.sell);
                break;
            case 2:
                btn_car_1.setVisibility(View.VISIBLE);
                btn_car_1.setText(R.string.sell);
                break;
            case 3:
                btn_car_2.setVisibility(View.VISIBLE);
                btn_car_2.setText(R.string.sell);
                break;
            case 4:
                btn_car_3.setVisibility(View.VISIBLE);
                btn_car_3.setText(R.string.sell);
                break;
            case 5:
                btn_car_4.setVisibility(View.VISIBLE);
                btn_car_4.setText(R.string.sell);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        mSharedScoreUtil.setCar(true);
        switch (v.getId()) {
            case R.id.btn_car_0:
                onBuyOrSell(1, 40000, 600, 10, 1);
                break;
            case R.id.btn_car_1:
                onBuyOrSell(2, 120000, 1000, 15, 2);
                break;
            case R.id.btn_car_2:
                onBuyOrSell(3, 350000, 1500, 20, 2);
                break;
            case R.id.btn_car_3:
                onBuyOrSell(4, 1600000, 3000, 30, 3);
                break;
            case R.id.btn_car_4:
                onBuyOrSell(5, 2500000, 4000, 40, 3);
                break;
            default:
                break;
        }
    }

    private void onBuyOrSell(Integer car, Integer money, Integer income, Integer value, Integer monthly) {
        CustomApplication.playSound(R.raw.money);
        Integer mMoney = mSharedScoreUtil.getMoney();
        if (my_car.equals(car)) {
            mSharedScoreUtil.setCar(0);
            mSharedScoreUtil.setMoney(+money / 2);
            mSharedScoreUtil.setIncome(income);
            mSharedScoreUtil.setCommunicationMonthly(-monthly);
        } else {
            if (mMoney < money) {
                showToast(R.string.car_no_money,false);
            } else {
                mSharedScoreUtil.setCar(car);
                mSharedScoreUtil.setMoney(-money);
                mSharedScoreUtil.setIncome(-income);
                mSharedScoreUtil.setAbility(value);
                mSharedScoreUtil.setExperience(value);
                mSharedScoreUtil.setHappy(value);
                mSharedScoreUtil.setCommunicationMonthly(monthly);
                getActivity().getSupportFragmentManager().popBackStack();
            }

        }
        onActivityCreated(null);
    }
}

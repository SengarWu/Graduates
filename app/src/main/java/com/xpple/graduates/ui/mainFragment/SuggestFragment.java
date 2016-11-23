package com.xpple.graduates.ui.mainFragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.PayListener;
import com.xpple.graduates.CustomApplication;
import com.xpple.graduates.R;
import com.xpple.graduates.util.SpSettingsUtil;
import com.xpple.graduates.view.BaseFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;


public class SuggestFragment extends BaseFragment implements
        RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private View parentView;
    private SpSettingsUtil mSharedSettingsUtil;
    private BmobPay bmobPay;
    private EditText price;
    private Button go;
    private RadioGroup type;
    private TextView tv;
    private String name = "支持作者";
    private ProgressDialog dialog;
    private RelativeLayout rl_switch_suggest;
    private ImageView iv_open_suggest, iv_close_suggest;
    private TextView tv_suggest;
    private Integer suggest_money;

    public SuggestFragment() {

    }

    private static SuggestFragment instance = new SuggestFragment();

    public static SuggestFragment newInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_suggest, container, false);
        setUpViews();
        return parentView;
    }

    private void setUpViews() {
        price = (EditText) parentView.findViewById(R.id.price);
        go = (Button) parentView.findViewById(R.id.go);
        type = (RadioGroup) parentView.findViewById(R.id.type);
        tv = (TextView) parentView.findViewById(R.id.tv);
        rl_switch_suggest = (RelativeLayout) parentView.findViewById(R.id.rl_switch_suggest);
        iv_close_suggest = (ImageView) parentView.findViewById(R.id.iv_close_suggest);
        iv_open_suggest = (ImageView) parentView.findViewById(R.id.iv_open_suggest);
        tv_suggest = (TextView) parentView.findViewById(R.id.tv_suggest);
        TextView tv_suggest_1 = (TextView) parentView.findViewById(R.id.tv_suggest_1);
        setListener();
    }

    private void setListener() {
        rl_switch_suggest.setOnClickListener(this);
        type.setOnCheckedChangeListener(this);
        go.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedSettingsUtil = mApplication.getSpSettingsUtil();
        suggest_money = mSharedSettingsUtil.getUserSuggestMoney();
        boolean isUserSuggest = mSharedSettingsUtil.isUserSuggest();
        if (isUserSuggest) {
            iv_open_suggest.setVisibility(View.VISIBLE);
            iv_close_suggest.setVisibility(View.INVISIBLE);
        } else {
            iv_open_suggest.setVisibility(View.INVISIBLE);
            iv_close_suggest.setVisibility(View.VISIBLE);
        }

        DecimalFormat df = new DecimalFormat("0.00");
        String mSuggestMoney = df.format(suggest_money / 100);
        tv_suggest.setText(mSuggestMoney);

    }

    // 调用赞助宝赞助
    void payByAli() {
        showDialog("正在获取订单...");
        // 初始化BmobPay对象,可以在支付时再初始化
        bmobPay = new BmobPay(getActivity());
        bmobPay.pay(getPrice(), name, "支付宝赞助", new PayListener() {

            // 因为网络等原因,赞助结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                showToast("赞助结果未知,请稍后手动查询", false);
                tv.append(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 赞助成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                mSharedSettingsUtil.setUserSuggestEnable(true);
                mSharedSettingsUtil.setUserSuggestMoney((int) (getPrice() * 100));
                showToast("赞助成功,感谢您的支持!", true);
                tv.append(name + "'s pay status is success\n\n");
                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                tv.append(name + "'s orderid is " + orderId + "\n\n");
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 赞助失败,原因可能是用户中断赞助操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {
                showToast("赞助中断!", false);
                tv.append(name + "'s pay status is fail, error code is " + code
                        + " ,reason is " + reason + "\n\n");
                hideDialog();
            }
        });
    }

    // 调用微信赞助
    void payByWeiXin() {
        showDialog("正在获取订单...");
        // 初始化BmobPay对象,可以在支付时再初始化
        bmobPay = new BmobPay(getActivity());
        bmobPay.payByWX(getPrice(), name, "微信赞助", new PayListener() {

            // 因为网络等原因,赞助结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                showToast("赞助结果未知,请稍后手动查询", false);
                tv.append(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 赞助成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                mSharedSettingsUtil.setUserSuggestEnable(true);
                showToast("赞助成功,感谢您的支持!", true);
                tv.append(name + "'s pay status is success\n\n");
                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                tv.append(name + "'s orderid is " + orderId + "\n\n");
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 赞助失败,原因可能是用户中断赞助操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(
                                    "监测到你尚未安装支付插件,无法进行微信支付,请选择安装插件(已打包在本地,无流量消耗)还是用赞助宝赞助")
                            .setPositiveButton("安装",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            installBmobPayPlugin("BmobPayPlugin.apk");
                                        }
                                    })
                            .setNegativeButton(R.string.suggest_zfb_0,
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            payByAli();
                                        }
                                    }).create().show();
                } else {
                    showToast("赞助中断!", false);
                }
                tv.append(name + "'s pay status is fail, error code is " + code
                        + " ,reason is " + reason + "\n\n");
                hideDialog();
            }
        });
    }

    // 以下仅为控件操作，可以略过
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.alipay:
                // 以下仅为控件操作，可以略过
                go.setText(R.string.suggest_zfb_0);
                break;
            case R.id.wxpay:
                // 以下仅为控件操作，可以略过
                go.setText(R.string.suggest_wx_0);
                break;
            default:
                break;
        }
    }

    // 默认为0.1
    double getPrice() {
        double price = 1;
        try {
            price = Double.parseDouble(this.price.getText().toString());
        } catch (NumberFormatException ignored) {
        }
        return price;
    }

    void showDialog(String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
        }
        dialog.setMessage(message);
        dialog.show();
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go:
                // 当选择的是支付宝支付时
                if (type.getCheckedRadioButtonId() == R.id.alipay) {
                    payByAli();
                } else {
                    // 调用插件用微信支付
                    payByWeiXin();
                }
                break;
            case R.id.rl_switch_suggest:
                CustomApplication.playSound(R.raw.button_1);
                if (suggest_money > 0) {
                    if (iv_open_suggest.getVisibility() == View.VISIBLE) {
                        iv_open_suggest.setVisibility(View.INVISIBLE);
                        iv_close_suggest.setVisibility(View.VISIBLE);
                        mSharedSettingsUtil.setUserSuggestEnable(false);
                    } else {
                        iv_open_suggest.setVisibility(View.VISIBLE);
                        iv_close_suggest.setVisibility(View.INVISIBLE);
                        mSharedSettingsUtil.setUserSuggestEnable(true);
                    }
                    onActivityCreated(null);
                } else {
                    showToast(R.string.suggest_0, true);
                }
                break;
        }
    }
}

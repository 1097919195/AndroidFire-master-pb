package com.jaydenxiao.androidfire.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.api.ApiConstants;
import com.jaydenxiao.androidfire.app.AppConstant;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.ClearEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by yyh on 2017-06-13.
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.cet_setting_paperip)
    ClearEditText paperip;
    @Bind(R.id.cet_setting_sendip)
    ClearEditText sendip;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    public int getLayoutId() {
        return R.layout.act_send_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        sp = getSharedPreferences(AppConstant.USERINFO, MODE_PRIVATE);
        editor = sp.edit();
        sendip.setText(sp.getString("sendip", ""));
        paperip.setText(sp.getString("paperip", ""));
    }

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, SettingActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                com.jaydenxiao.common.R.anim.fade_out);
    }

    //设置更换报刊ip，发送ip地址
    @OnClick(R.id.btn_setting)
    public void Setting() {
        ApiConstants.SEND_PAPER_HOST = paperip.getText().toString();
        ApiConstants.SEND_SOCKET_HOST = sendip.getText().toString();
        editor.putString("paperip", ApiConstants.SEND_PAPER_HOST);
        editor.putString("sendip", ApiConstants.SEND_SOCKET_HOST);
//        SPUtils.setSharedStringData(this, "paperip", ApiConstants.SEND_PAPER_HOST);
//        SPUtils.setSharedStringData(this, "sendip", ApiConstants.SEND_SOCKET_HOST);
        editor.commit();
        System.exit(0);
    }

    @OnClick(R.id.btn_cancel)
    public void Cancel() {
        finish();
    }


}

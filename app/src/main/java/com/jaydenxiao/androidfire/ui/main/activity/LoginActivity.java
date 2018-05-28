package com.jaydenxiao.androidfire.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.CheckBox;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.api.ApiConstants;
import com.jaydenxiao.androidfire.app.AppConstant;
import com.jaydenxiao.androidfire.bean.SendLoginData;
import com.jaydenxiao.androidfire.ui.main.contract.SendLoginContract;
import com.jaydenxiao.androidfire.ui.main.model.SendLoginModel;
import com.jaydenxiao.androidfire.ui.main.presenter.SendLoginPresenter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonwidget.ClearEditText;
import com.jaydenxiao.common.commonwidget.LoadingTip;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by yyh on 2017-05-25.
 */

public class LoginActivity extends BaseActivity<SendLoginPresenter, SendLoginModel> implements SendLoginContract.View {
    @Bind(R.id.cet_username)
    ClearEditText cet_username;
    @Bind(R.id.cet_password)
    ClearEditText cet_password;
    @Bind(R.id.cb_remain_username)
    CheckBox cb_remain_username;
    @Bind(R.id.cb_remain_password)
    CheckBox cb_remain_password;
    @Bind(R.id.loadedTip)
    LoadingTip loadedTip;

    private String username = "";
    private String password = "";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        //一个参数是第一个activity进入时的动画，另外一个参数则是第二个activity退出时的动画。
        activity.overridePendingTransition(R.anim.fade_in,
                com.jaydenxiao.common.R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        sp = getSharedPreferences(AppConstant.USERINFO, MODE_PRIVATE);
        editor = sp.edit();
        username = sp.getString("username", "");
        password = sp.getString("password", "");
        //是否勾选了记住用户名或密码，或者使用SharedPreferences存储勾选的情况
        if (!"".equals(username)) {
            cet_username.setText(username);
            cb_remain_username.setChecked(true);
        }
        if (!"".equals(password)) {
            cet_password.setText(password);
            cb_remain_password.setChecked(true);
        }
    }

    @OnClick(R.id.btn_login)
    public void Login() {
        if (!"".equals(sp.getString("paperip", ""))) {
            ApiConstants.SEND_PAPER_HOST = sp.getString("paperip", "");
            username = cet_username.getText().toString();
            password = cet_password.getText().toString();
            if (!"".equals(sp.getString("sendip", ""))) {
                ApiConstants.SEND_SOCKET_HOST = sp.getString("sendip", "");
            } else {
                ToastUitl.showShort("发送服务器未设置，不能分发报纸");
            }
//            ToastUitl.showShort(ApiConstants.SEND_SOCKET_HOST + "&&" + ApiConstants.SEND_PAPER_HOST);
            if (cb_remain_username.isChecked()) {
                editor.putString("username", username);
            } else {
                editor.putString("username", "");
            }

            if (cb_remain_password.isChecked()) {
                editor.putString("password", password);
            } else {
                editor.putString("password", "");
            }
            editor.commit();

            //发起请求，将model（配置请求接口）和view(获取数据)进行绑定
            mPresenter.CheckLoginDataRequest(username, password);

//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("username",username);
//        this.startActivity(intent);
        } else {
            ToastUitl.showShort("请检查报刊服务器地址设置是否正确");
        }
    }

    //
    @OnClick(R.id.btn_setting)
    public void Reset() {
        SettingActivity.startAction(LoginActivity.this);

//        mRxManager.add(Api.getDefault(HostType.OA_TEST).getJZList1("1","410000").compose(RxSchedulers.<BsListEntity>io_main()).subscribe(new RxSubscriber<BsListEntity>(mContext,false) {
//            @Override
//            protected void _onNext(BsListEntity bsListEntity) {
//                ToastUitl.showShort(bsListEntity.toString());
//            }
//
//            @Override
//            protected void _onError(String message) {
//
//            }
//        }));
//        mRxManager.add(Api.getDefault(HostType.SEND_SOCKET).getdisplayData1(1, 1, 1).compose(RxSchedulers.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>(mContext, false) {
//            @Override
//            protected void _onNext(JSONObject jsonObject) {
//                ToastUitl.showShort(jsonObject.toJSONString());
//            }
//
//            @Override
//            protected void _onError(String message) {
//
//            }
//        }));


//        final BkDept bkDept=new BkDept();
//        bkDept.setDeptBox(010);
//        mRxManager.add(Api.getDefault(HostType.SEND_PAPER).getBkDeptData1(bkDept).compose(RxSchedulers.<JSONArray>io_main()).subscribe(new RxSubscriber<JSONArray>(mContext,false) {
//            @Override
//            protected void _onNext(JSONArray objects) {
//                Log.i("objects-----",objects.get(0).toString());
//                Gson mgson=new Gson();
//                BkDeptData bkDeptData=mgson.fromJson(objects.get(0).toString(),BkDeptData.class);
//                ToastUitl.showShort(bkDeptData.getDeptName()+bkDeptData.getDeptBox());
//            }
//
//            @Override
//            protected void _onError(String message) {
//                ToastUitl.showShort(message);
//            }
//        }));


//        final BkInfo bkInfo = new BkInfo();
//        bkInfo.setBkName("中国");
//        bkInfo.setCategory(1);
//        mRxManager.add(Api.getDefault(HostType.SEND_PAPER).getBkSearchData("中国",1,2017,0,12).compose(RxSchedulers.<BkSearchData>io_main()).subscribe(new RxSubscriber<BkSearchData>(mContext, false) {
//            @Override
//            protected void _onNext(BkSearchData bkSearchData) {
//                Log.i("bkSearchData-----", bkSearchData.toString()+"@@@@@"+bkSearchData.getTotal());
////                ToastUitl.showShort(bkSearchData.() + bkSearchData.getDeptBox());
//            }
//
//            @Override
//            protected void _onError(String message) {
//                ToastUitl.showShort(message);
//            }
//        }));


//        mRxManager.add(Api.getDefault(HostType.SEND_PAPER).getBkSendData(1,2017,"1-10").compose(RxSchedulers.<BkSendData>io_main()).subscribe(new RxSubscriber<BkSendData>(mContext,false) {
//            @Override
//            protected void _onNext(BkSendData bkSendData) {
//                ToastUitl.showShort(bkSendData.toString());
//            }
//
//            @Override
//            protected void _onError(String message) {
//                ToastUitl.showShort(message);
//            }
//        }));

//        String a="2-31";
//
//        mRxManager.add(Api.getDefault(HostType.SEND_PAPER).getBkSendData1(1,2017,a).map(new Func1<JSONArray, BkSendData>() {
//            @Override
//            public BkSendData call(JSONArray objects) {
//                Log.i("objects",objects.get(0).toString());
//                Gson mgson=new Gson();
//                BkSendData bkSendData=mgson.fromJson(objects.get(0).toString(),BkSendData.class);
//                return bkSendData;
//            }
//        }).compose(RxSchedulers.<BkSendData>io_main()).subscribe(new RxSubscriber<BkSendData>(mContext,false) {
//            @Override
//            protected void _onNext(BkSendData bkSendData) {
//                ToastUitl.showShort(bkSendData.getBkDomesticPostCode()+bkSendData.getBkName()+bkSendData.getBkId());
//            }
//
//            @Override
//            protected void _onError(String message) {
//                ToastUitl.showShort(message);
//            }
//
//
//    }));
    }

    @Override
    public void renturnCheckLoginData(SendLoginData sendLoginData) {
        try {
            if (sendLoginData != null) {
                if (sendLoginData.getSuccess()) {
                    ToastUitl.showShort("登录成功!");
                    editor.putString("padaddress", sendLoginData.getData());
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("username", username);
                    editor.commit();
                    this.startActivity(intent);
                    this.overridePendingTransition(R.anim.fade_in,
                            com.jaydenxiao.common.R.anim.fade_out);
                    finish();
                } else {
                ToastUitl.showShort(sendLoginData.getMessage());
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading(String title) {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.loading);
    }

    @Override
    public void stopLoading() {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.finish);
    }

    @Override
    public void showErrorTip(String msg) {
        loadedTip.setLoadingTip(LoadingTip.LoadStatus.error);
        loadedTip.setTips(msg);
    }
}

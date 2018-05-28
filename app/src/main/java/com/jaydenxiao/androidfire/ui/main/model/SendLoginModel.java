package com.jaydenxiao.androidfire.ui.main.model;

import com.jaydenxiao.androidfire.api.Api;
import com.jaydenxiao.androidfire.api.HostType;
import com.jaydenxiao.androidfire.bean.SendLoginData;
import com.jaydenxiao.androidfire.ui.main.contract.SendLoginContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import rx.Observable;

/**
 * Created by yyh on 2017-05-27.
 */

public class SendLoginModel implements SendLoginContract.Model {
    @Override
    public Observable<SendLoginData> CheckLoginData(String username, String password) {
        return Api.getDefault(HostType.SEND_PAPER)
                .CheckLogin(username,password)
                .compose(RxSchedulers.<SendLoginData>io_main());
}
}

package com.jaydenxiao.androidfire.ui.main.contract;

import com.jaydenxiao.androidfire.bean.SendLoginData;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import rx.Observable;

/**
 * 登陆返回数据Contract
 * Created by yyh on 2017-05-27.
 */

public interface SendLoginContract {

    interface Model extends BaseModel {

        Observable<SendLoginData> CheckLoginData(String username,String password);
    }

    interface View extends BaseView{

        void renturnCheckLoginData(SendLoginData sendLoginData);
    }

    abstract static class Presenter extends BasePresenter<View,Model> {
        public abstract void CheckLoginDataRequest(String username,String password);
    }
}

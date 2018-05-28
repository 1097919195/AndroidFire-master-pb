package com.jaydenxiao.androidfire.ui.main.presenter;

import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.bean.SendLoginData;
import com.jaydenxiao.androidfire.ui.main.contract.SendLoginContract;
import com.jaydenxiao.common.baserx.RxSubscriber;

/**
 * Created by yyh on 2017-05-27.
 */

public class SendLoginPresenter extends SendLoginContract.Presenter {
    @Override
    public void CheckLoginDataRequest(String username, String password) {
        mRxManage.add(mModel.CheckLoginData(username,password)
                .subscribe(new RxSubscriber<SendLoginData>(mContext,false) {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(SendLoginData sendLoginData) {
                mView.renturnCheckLoginData(sendLoginData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}

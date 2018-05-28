package com.jaydenxiao.androidfire.ui.oa.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.bean.User;
import com.jaydenxiao.androidfire.ui.oa.contract.OaContract;
import com.jaydenxiao.common.basebean.BaseRespose;
import com.jaydenxiao.common.baserx.RxSubscriber;

import okhttp3.ResponseBody;

/**
 * Created by yyh on 2017-05-19.
 */

public class OaPresenter extends OaContract.Presenter {
    @Override
    public void getMessageDetailDataRequest(String id) {
        mRxManage.add(mModel.getMessageDetailData(id).subscribe(new RxSubscriber<JSONObject>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(JSONObject jsonObject) {
                mView.returnMessageDetailData(jsonObject.toString());
                mView.stopLoading();
            }
            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}

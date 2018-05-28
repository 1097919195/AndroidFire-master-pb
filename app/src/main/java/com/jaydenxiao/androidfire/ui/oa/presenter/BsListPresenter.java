package com.jaydenxiao.androidfire.ui.oa.presenter;

import com.alibaba.fastjson.JSONObject;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.bean.BsLSumm;
import com.jaydenxiao.androidfire.bean.BsListData;
import com.jaydenxiao.androidfire.bean.BsListEntity;
import com.jaydenxiao.androidfire.bean.BsListSummary;
import com.jaydenxiao.androidfire.ui.oa.contract.BsListContract;
import com.jaydenxiao.common.baserx.RxManager;
import com.jaydenxiao.common.baserx.RxSubscriber;

import java.util.List;

/**
 * Created by yyh on 2017-05-22.
 */

public class BsListPresenter extends BsListContract.Presenter {
    @Override
    public void getBsListDataRequest(String slevel, String sno) {
        mRxManage.add(mModel.getJZListData(slevel, sno).subscribe(new RxSubscriber<JSONObject>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(JSONObject jsonObject) {
                mView.returnJZListData(jsonObject);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getBsListData1Request(String slevel, String sno) {
        mRxManage.add(mModel.getJZListData1(slevel, sno)
                .subscribe(new RxSubscriber<List<BsListData>>(mContext, false) {
                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(List<BsListData> bsListDatas) {
                        mView.returnJZListData1(bsListDatas);
                        mView.stopLoading();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message);
                    }
                })
        );
    }

    @Override
    public void getBsListData2Request(String slevel, String sno) {
//        mRxManage.add(mModel.getJZListData2(slevel, sno).subscribe(new RxSubscriber<List<BsListSummary>>(mContext, false) {
//            @Override
//            public void onStart() {
//                super.onStart();
//                mView.showLoading(mContext.getString(R.string.loading));
//            }
//
//            @Override
//            protected void _onNext(List<BsListSummary> bsListSummaries) {
//                mView.returnJZListData2(bsListSummaries);
//                mView.stopLoading();
//            }
//
//            @Override
//            protected void _onError(String message) {
//                mView.showErrorTip(message);
//            }
//        }));

        mRxManage.add(mModel.getJZListData2(slevel, sno).subscribe(new RxSubscriber<List<BsLSumm.BsLData>>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(List<BsLSumm.BsLData> bsLDatas) {

                mView.returnJZListData2(bsLDatas);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }
}

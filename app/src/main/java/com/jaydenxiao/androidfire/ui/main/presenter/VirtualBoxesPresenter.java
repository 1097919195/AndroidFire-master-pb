package com.jaydenxiao.androidfire.ui.main.presenter;

import com.alibaba.fastjson.JSONObject;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.bean.BKAllDeptData;
import com.jaydenxiao.androidfire.bean.BKBroadDisplayData;
import com.jaydenxiao.androidfire.bean.BkDeptData;
import com.jaydenxiao.androidfire.bean.BkOrderData;
import com.jaydenxiao.androidfire.bean.BkSearchData;
import com.jaydenxiao.androidfire.bean.BkSendData;
import com.jaydenxiao.androidfire.bean.BkTSendData;
import com.jaydenxiao.androidfire.ui.main.contract.VirtualBoxesContract;
import com.jaydenxiao.common.baserx.RxSubscriber;
import com.jaydenxiao.common.commonutils.LogUtils;

/**
 * Created by yyh on 2017-05-27.
 */

public class VirtualBoxesPresenter extends VirtualBoxesContract.Presenter {
    @Override
    public void getAllDeptDataRequest() {
        mRxManage.add(mModel.getAllDeptData().subscribe(new RxSubscriber<JSONObject>(mContext) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(JSONObject jsonObject) {
                mView.returngetAllDeptData(jsonObject);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getBkDeptDataRequest(int deptBox) {
        mRxManage.add(mModel.getBkDeptData(deptBox).subscribe(new RxSubscriber<BkDeptData>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BkDeptData bkDeptData) {
                mView.returngetBkDeptData(bkDeptData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getBkSearchDataRequest(String bkName, String domesticPostCode, int category, int year, int pageNumber, int pageSize) {
        mRxManage.add(mModel.getBkSearchData(bkName, domesticPostCode, category, year, pageNumber, pageSize).subscribe(new RxSubscriber<BkSearchData>(mContext, false) {
            @Override
            protected void _onNext(BkSearchData bkSearchData) {
                mView.returngetBkSearchData(bkSearchData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getBkSendDataRequest(int category, int year, String domesticPostCode) {
        mRxManage.add(mModel.getBkSendData(category, year, domesticPostCode).subscribe(new RxSubscriber<BkSendData>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BkSendData bkSendData) {
                mView.returngetBkSendData(bkSendData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getBkOrderDataRequest(int orderId) {
        mRxManage.add(mModel.getBkOrderData(orderId).subscribe(new RxSubscriber<BkOrderData>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BkOrderData bkOrderData) {
                mView.returngetBkOrderData(bkOrderData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        }));
    }

    @Override
    public void getdisplayDataRequest(int idint, int eAddrint, int valueint) {
        mRxManage.add(mModel.getdisplayData(idint, eAddrint, valueint)
                .subscribe(new RxSubscriber<JSONObject>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(JSONObject jsonObject) {
                mView.returngetdisplayData(jsonObject.toJSONString());//toJSONString()函数可以将任何JSONS对象转换成JSON字符串
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip("1");
            }
        }));
    }

    @Override
    public void getTSendDataRequest() {
        mRxManage.add(mModel.getTSendData()
                .subscribe(new RxSubscriber<BkTSendData>(mContext,false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(BkTSendData bkTSendData) {
                LogUtils.logi("sss1111-----------"+bkTSendData.getBk()+"&&"+bkTSendData.getQk());
                mView.returngetTSendData(bkTSendData);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip("2");
            }
        }));
    }

    @Override
    public void getEndSendRequest(int eAddrint, int valueint) {
        mRxManage.add(mModel.getEndSendData(eAddrint,valueint)
                .subscribe(new RxSubscriber<BKBroadDisplayData>(mContext,false) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(BKBroadDisplayData bkBKDisplayData) {
                        mView.returngetEndSendData(bkBKDisplayData);
                        mView.stopLoading();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message);
                    }
                }));
    }

    @Override
    public void getAllDeptDataRequests(int type) {
        mRxManage.add(mModel.getAllDeptDatas(type)
                .subscribe(new RxSubscriber<BKAllDeptData>(mContext,false) {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoading(mContext.getString(R.string.loading));
                    }

                    @Override
                    protected void _onNext(BKAllDeptData bkBKAllDeptData) {
                        mView.returngetAllDeptDatas(bkBKAllDeptData);
                        mView.stopLoading();
                    }

                    @Override
                    protected void _onError(String message) {
                        mView.showErrorTip(message);
                    }
                }));
    }

    @Override
    public void getdisplayDataRequest1(int idint, int eAddrint, int valueint) {
        mRxManage.add(mModel.getdisplayData1(idint, eAddrint, valueint).subscribe(new RxSubscriber<JSONObject>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(JSONObject jsonObject) {
                mView.returngetdisplayData1(jsonObject.toJSONString());
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip("3");
            }
        }));

    }

    @Override
    public void getStartSendALLPaperDataRequest(String result) {
        mRxManage.add(mModel.getStartSendALLPaperData(result)
                .subscribe(new RxSubscriber<JSONObject>(mContext, false) {
            @Override
            public void onStart() {
                super.onStart();
                mView.showLoading(mContext.getString(R.string.loading));
            }

            @Override
            protected void _onNext(JSONObject jsonObject) {
                mView.returngetStartSendALLPaperData(jsonObject.toJSONString());
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip("1");

            }
        }));
    }

}

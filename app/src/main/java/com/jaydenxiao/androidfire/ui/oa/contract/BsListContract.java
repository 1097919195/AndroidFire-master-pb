package com.jaydenxiao.androidfire.ui.oa.contract;

import com.alibaba.fastjson.JSONObject;
import com.jaydenxiao.androidfire.bean.BsLSumm;
import com.jaydenxiao.androidfire.bean.BsListData;
import com.jaydenxiao.androidfire.bean.BsListEntity;
import com.jaydenxiao.androidfire.bean.BsListSummary;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by yyh on 2017-05-22.
 */

public interface BsListContract {
    interface Model extends BaseModel{
        //请求获取基站列表
        Observable<JSONObject> getJZListData(String slevel, String sno);

        Observable<List<BsListData>> getJZListData1(String slevel,String sno);

        Observable<List<BsLSumm.BsLData>> getJZListData2(String slevel, String sno);
    }
    interface View extends BaseView{
        //返回获取的基站列表数据
        void returnJZListData(JSONObject jsonObject);

        void returnJZListData1(List<BsListData> bsListDatas);

        void returnJZListData2(List<BsLSumm.BsLData> bsLDatas);
    }
    abstract static class Presenter extends BasePresenter<View,Model>
    {
        //发起基站列表数据请求
        public abstract void getBsListDataRequest(String slevel, String sno);

        public abstract void getBsListData1Request(String slevel,String sno);

        public abstract void getBsListData2Request(String slevel,String sno);
    }
}

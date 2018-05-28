package com.jaydenxiao.androidfire.ui.oa.contract;

import android.graphics.PorterDuff;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jaydenxiao.androidfire.bean.PhotoGirl;
import com.jaydenxiao.androidfire.bean.User;
import com.jaydenxiao.common.base.BaseModel;
import com.jaydenxiao.common.base.BasePresenter;
import com.jaydenxiao.common.base.BaseView;
import com.jaydenxiao.common.basebean.BaseRespose;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by yyh on 2017-05-19.
 */

public interface OaContract {
    interface Model extends BaseModel{
        //请求获取图片
        Observable<JSONObject> getMessageDetailData(String id);
    }
    interface View extends BaseView{
        //返回获取的图片
        void returnMessageDetailData(String mdetail);
    }
    abstract static class Presenter extends BasePresenter<View,Model>
    {
        //发起获取消息详情
        public abstract void getMessageDetailDataRequest(String id);
    }
}

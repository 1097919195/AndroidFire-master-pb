package com.jaydenxiao.androidfire.ui.main.model;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaydenxiao.androidfire.api.Api;
import com.jaydenxiao.androidfire.api.HostType;
import com.jaydenxiao.androidfire.bean.BKAllDeptData;
import com.jaydenxiao.androidfire.bean.BKBroadDisplayData;
import com.jaydenxiao.androidfire.bean.BkDeptData;
import com.jaydenxiao.androidfire.bean.BkOrderData;
import com.jaydenxiao.androidfire.bean.BkSearchData;
import com.jaydenxiao.androidfire.bean.BkSendData;
import com.jaydenxiao.androidfire.bean.BkTSendData;
import com.jaydenxiao.androidfire.ui.main.contract.VirtualBoxesContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yyh on 2017-05-27.
 */

public class VirtualBoxesModel implements VirtualBoxesContract.Model {
    @Override
    public Observable<JSONObject> getAllDeptData() {
        return Api.getDefault(HostType.SEND_PAPER)
                .getAllDept()
                .compose(RxSchedulers.<JSONObject>io_main());
    }

    @Override
    public Observable<BkDeptData> getBkDeptData(int deptBox) {
        return Api.getDefault(HostType.SEND_PAPER)
                .getBkDeptData(deptBox)
                .compose(RxSchedulers.<BkDeptData>io_main());
    }

    @Override
    public Observable<BkSearchData> getBkSearchData(String bkName,String domesticPostCode, int category, int year, int pageNumber, int pageSize) {
        return Api.getDefault(HostType.SEND_PAPER)
                .getBkSearchData(bkName,domesticPostCode, category, year, pageNumber, pageSize)
                .compose(RxSchedulers.<BkSearchData>io_main());
    }

    @Override
    public Observable<BkSendData> getBkSendData(int category, int year, String domesticPostCode) {
        return Api.getDefault(HostType.SEND_PAPER)
                .getBkSendData(category, year, domesticPostCode)
                //map()事件对象的直接变换。所谓变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列。
                //json数据转换为实体类
                .map(new Func1<JSONArray, BkSendData>() {
                    @Override
                    public BkSendData call(JSONArray objects) {
                        Log.e("objects", objects.get(0).toString());
                        Gson mgson = new Gson();
                        BkSendData bkSendData = mgson.fromJson(objects.get(0).toString(), BkSendData.class);
                        return bkSendData;
                    }
                })
                .compose(RxSchedulers.<BkSendData>io_main());
    }

    @Override
    public Observable<BkOrderData> getBkOrderData(int orderId) {
        return Api.getDefault(HostType.SEND_PAPER)
                .getBkOrderData(orderId)
                .compose(RxSchedulers.<BkOrderData>io_main());
    }

    @Override
    public Observable<JSONObject> getdisplayData(int idint, int eAddrint, int valueint) {
        return Api.getDefault(HostType.SEND_SOCKET)
                .getdisplayData(idint,eAddrint,valueint)
                .compose(RxSchedulers.<JSONObject>io_main());
    }


    @Override
    public Observable<BkTSendData> getTSendData() {
        return Api.getDefault(HostType.SEND_PAPER)
                .getTSendData()
                .compose(RxSchedulers.<BkTSendData>io_main());
    }

    @Override
    public Observable<BKBroadDisplayData> getEndSendData(int eAddrint, int valueint) {
        return Api.getDefault(HostType.SEND_SOCKET)
                .getbroadcastDisplayData(eAddrint, valueint)
                .compose(RxSchedulers.<BKBroadDisplayData>io_main());
    }

    @Override
    public Observable<BKAllDeptData> getAllDeptDatas(int type) {
        return Api.getDefault(HostType.SEND_PAPER)
                .getAllDeptData(type)
                //json数据的集合转换为存储实体类的集合      http://blog.csdn.net/yaochangliang159/article/details/48752549
                .map(new Func1<JSONArray, BKAllDeptData>() {
                    @Override
                    public BKAllDeptData call(JSONArray Json) {
                        Log.e("JsonArray", Json.toString());
                        BKAllDeptData bkAllDeptData = new BKAllDeptData();
                        List<BKAllDeptData.BKDept> list = new ArrayList<BKAllDeptData.BKDept>();
                        Gson mgson = new Gson();
                        list = mgson.fromJson(Json.toString(), new TypeToken<List<BKAllDeptData.BKDept>>() {
                        }.getType());
                        bkAllDeptData.setMlist(list);
                        return bkAllDeptData;
                    }
                })
                .compose(RxSchedulers.<BKAllDeptData>io_main());
    }

    @Override
    public Observable<JSONObject> getdisplayData1(int idint, int eAddrint, int valueint) {
        return Api.getDefault(HostType.SEND_SOCKET)
                .getdisplayData(idint,eAddrint,valueint)
                .compose(RxSchedulers.<JSONObject>io_main());
    }

    @Override
    public Observable<JSONObject> getStartSendALLPaperData(String result) {
        return Api.getDefault(HostType.SEND_SOCKET)
                .getStartSendALLPaper(result)
                .compose(RxSchedulers.<JSONObject>io_main());
    }
}

package com.jaydenxiao.androidfire.ui.oa.model;

import com.alibaba.fastjson.JSONObject;
import com.jaydenxiao.androidfire.api.Api;
import com.jaydenxiao.androidfire.api.HostType;
import com.jaydenxiao.androidfire.bean.BsLSumm;
import com.jaydenxiao.androidfire.bean.BsListData;
import com.jaydenxiao.androidfire.bean.BsListEntity;
import com.jaydenxiao.androidfire.bean.BsListSummary;
import com.jaydenxiao.androidfire.ui.oa.contract.BsListContract;
import com.jaydenxiao.common.baserx.RxSchedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yyh on 2017-05-22.
 */

public class BsListModel implements BsListContract.Model {
    @Override
    public Observable<JSONObject> getJZListData(String slevel, String sno) {
        return Api.getDefault(HostType.OA_TEST)
                .getJZList(slevel,sno)
                .compose(RxSchedulers.<JSONObject>io_main());
    }

    @Override
    public Observable<List<BsListData>> getJZListData1(String slevel, String sno) {
        return Api.getDefault(HostType.OA_TEST)
                .getJZList1(slevel,sno)
                .map(new Func1<BsListEntity, List<BsListData>>() {
                    @Override
                    public List<BsListData> call(BsListEntity bsListEntity) {
                        return bsListEntity.getSList();
                    }
                })
                .compose(RxSchedulers.<List<BsListData>>io_main());
    }

    @Override
    public Observable<List<BsLSumm.BsLData>> getJZListData2(String slevel, String sno) {
        return Api.getDefault(HostType.OA_TEST)
                .getJZList2(slevel,sno)
                .map(new Func1<BsLSumm, List<BsLSumm.BsLData>>() {
                    @Override
                    public List<BsLSumm.BsLData> call(BsLSumm bsLSumm) {
                        return bsLSumm.getSList();
                    }
                })
                .compose(RxSchedulers.<List<BsLSumm.BsLData>>io_main());
    }


//    @Override
//    public Observable<List<BsListSummary>> getJZList2(final String slevel, final String sno) {
//        return Api.getDefault(HostType.OA_TEST)
//                .getJZList2(slevel,sno)
//                .flatMap(new Func1<Map<String, List<BsListSummary>>, Observable<BsListSummary>>() {
//                    @Override
//                    public Observable<BsListSummary> call(Map<String, List<BsListSummary>> stringListMap) {
//                        return Observable.from(stringListMap.get("SList"));
//                    }
//                })
//                .map(new Func1<BsListSummary, List<BsListSummary>>() {
//                    @Override
//                    public List<BsListSummary> call(BsListSummary bsListSummary) {
//                        List<BsListSummary> bsListSummaries=new ArrayList<BsListSummary>();
//                        bsListSummaries.add(bsListSummary);
//                        return bsListSummaries;
//                    }
//                })
//                .compose(RxSchedulers.<List<BsListSummary>>io_main());
//
//    }

//    @Override
//    public Observable<List<BsListData>> getJZListData(String slevel, String sno) {
//        return Api.getDefault(HostType.OA_TEST)
//                .getJZList(slevel,sno)
//                .map(new Func1<BsListEntity, List<BsListData>>() {
//                    @Override
//                    public List<BsListData> call(BsListEntity bsListEntity) {
//                        return bsListEntity.getResults();
//                    }
//                })
//                .compose(RxSchedulers.<List<BsListData>>io_main());
//    }
}

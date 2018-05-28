package com.jaydenxiao.androidfire.ui.oa.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.animation.ScaleInAnimation;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.bean.BsLSumm;
import com.jaydenxiao.androidfire.bean.BsListData;
import com.jaydenxiao.androidfire.ui.oa.adapter.BsListAdapter;
import com.jaydenxiao.androidfire.ui.oa.contract.BsListContract;
import com.jaydenxiao.androidfire.ui.oa.model.BsListModel;
import com.jaydenxiao.androidfire.ui.oa.presenter.BsListPresenter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.NormalTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by yyh on 2017-05-22.
 */

public class BsListActivity extends BaseActivity<BsListPresenter, BsListModel> implements BsListContract.View, View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @Bind(R.id.irc_bslist)
    IRecyclerView irc;
    @Bind(R.id.tv_bsdata)
    TextView tv_bsdata;
    @Bind(R.id.ntb_bs)
    NormalTitleBar ntb_bs;
//    private CommonRecycleViewAdapter<BsListData> adapter;
    private BsListAdapter adapter;
    private List<BsLSumm.BsLData> datas=new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.act_send_newspaper;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {

//    mPresenter.getBsListDataRequest("1","410000");
        ntb_bs.setTitleText("列表");
        ntb_bs.setTvLeftVisiable(false);
//        adapter = new CommonRecycleViewAdapter<BsListData>(getApplicationContext(), R.layout.item_bslist) {
//            @Override
//            public void convert(ViewHolderHelper helper, final BsListData bsListData) {
//                TextView textView = helper.getView(R.id.tv_bslist);
//                textView.setText(bsListData.getName());
//            }
//        };
        adapter=new BsListAdapter(this,datas);
        adapter.openLoadAnimation(new ScaleInAnimation());
        irc.setAdapter(adapter);
        irc.setLayoutManager(new LinearLayoutManager(this));
        irc.setOnRefreshListener(this);
        irc.setOnLoadMoreListener(this);
        mPresenter.getBsListData2Request("1", "410000");
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        tv_bsdata.setText(msg);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void returnJZListData(JSONObject jsonObject) {
        tv_bsdata.setText(jsonObject.toString());
    }

    @Override
    public void returnJZListData1(List<BsListData> bsListDatas) {
//        if (bsListDatas != null) {
//            adapter.replaceAll(bsListDatas);
//            irc.setRefreshing(false);
//        }
    }

    @Override
    public void returnJZListData2(List<BsLSumm.BsLData> bsLDatas) {
        if (bsLDatas != null) {
            irc.setRefreshing(false);
            adapter.replaceAll(bsLDatas);
        }
    }

    @Override
    public void onLoadMore(View loadMoreView) {

    }


    @Override
    public void onRefresh() {
        irc.setRefreshing(true);
        mPresenter.getBsListData2Request("1", "410000");
    }
}

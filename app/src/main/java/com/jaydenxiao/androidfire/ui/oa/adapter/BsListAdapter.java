package com.jaydenxiao.androidfire.ui.oa.adapter;

import android.content.Context;
import android.widget.TextView;

import com.aspsine.irecyclerview.baseadapter.BaseReclyerViewAdapter;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.abslistview.MultiItemAblistViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemRecycleViewAdapter;
import com.aspsine.irecyclerview.universaladapter.recyclerview.MultiItemTypeSupport;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.bean.BsLSumm;
import com.jaydenxiao.androidfire.bean.BsListSummary;
import com.jaydenxiao.androidfire.ui.oa.activity.BsListActivity;

import java.util.List;

/**
 * Created by yyh on 2017-05-22.
 */

public class BsListAdapter extends MultiItemRecycleViewAdapter<BsLSumm.BsLData> {


    public BsListAdapter(Context context, List<BsLSumm.BsLData> datas) {
        super(context, datas, new MultiItemTypeSupport<BsLSumm.BsLData>() {
            @Override
            public int getLayoutId(int itemType) {
                return R.layout.item_bslist;
            }

            @Override
            public int getItemViewType(int position, BsLSumm.BsLData bsLData) {
                return 0;
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper helper, BsLSumm.BsLData bsLData) {
        TextView textView=helper.getView(R.id.tv_bslist);
        textView.setText(bsLData.getName());
    }
}

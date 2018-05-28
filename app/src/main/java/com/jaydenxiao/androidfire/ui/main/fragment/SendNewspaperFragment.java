package com.jaydenxiao.androidfire.ui.main.fragment;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.app.AppApplication;
import com.jaydenxiao.androidfire.bean.BKAllDeptData;
import com.jaydenxiao.androidfire.bean.BKBroadDisplayData;
import com.jaydenxiao.androidfire.bean.BkDept;
import com.jaydenxiao.androidfire.bean.BkDeptData;
import com.jaydenxiao.androidfire.bean.BkInfo;
import com.jaydenxiao.androidfire.bean.BkOrderData;
import com.jaydenxiao.androidfire.bean.BkSearchData;
import com.jaydenxiao.androidfire.bean.BkSendData;
import com.jaydenxiao.androidfire.bean.BkTSendData;
import com.jaydenxiao.androidfire.bean.SendPaperData;
import com.jaydenxiao.androidfire.bean.VirtualBoxesData;
import com.jaydenxiao.androidfire.event.SendEvent;
import com.jaydenxiao.androidfire.event.SendNumEvent;
import com.jaydenxiao.androidfire.ui.main.activity.MainActivity;
import com.jaydenxiao.androidfire.ui.main.contract.VirtualBoxesContract;
import com.jaydenxiao.androidfire.ui.main.model.VirtualBoxesModel;
import com.jaydenxiao.androidfire.ui.main.presenter.VirtualBoxesPresenter;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.base.BasePopupWindow;
import com.jaydenxiao.common.commonutils.KeyBordUtil;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.SPUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.wevey.selector.dialog.DialogOnClickListener1;
import com.wevey.selector.dialog.MDAlertDialog2;
import com.wevey.selector.dialog.MDAlertDialog3;
import com.wevey.selector.dialog.MDAlertDialog4;
import com.yuyh.library.imgsel.widget.DividerGridItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;

import static com.jaydenxiao.androidfire.ui.main.activity.MainActivity.padaddress;


/**
 * A simple {@link Fragment} subclass.
 * 发报纸页面
 * create an instance of this fragment.
 */
public class SendNewspaperFragment extends BaseFragment<VirtualBoxesPresenter, VirtualBoxesModel> implements VirtualBoxesContract.View, OnLoadMoreListener, OnRefreshListener, View.OnClickListener {

    @Bind(R.id.irc_virtualboxes)
    RecyclerView irc_virtualboxes;
    @Bind(R.id.irc_sendpaper)
    IRecyclerView irc_sendpaper;
    @Bind(R.id.ll_send1)
    LinearLayout ll_send1;
    private Button btn_left;
    private Button btn_right;
    private ImageView pop_exit, pop_search;
    private EditText pop_et_search;
    private IRecyclerView irc_search;
    private LinearLayout ll_search_main;
    private CommonRecycleViewAdapter<VirtualBoxesData> adapter;
    private List<VirtualBoxesData> virtualBoxesDatas = new ArrayList<>();

    private CommonRecycleViewAdapter<SendPaperData> adapter2;
    private List<SendPaperData> sendPaperDatas = new ArrayList<>();

    private BkInfo bkInfo = new BkInfo();
    private BkDept bkDept = new BkDept();

    private BasePopupWindow popupWindow;
    private MDAlertDialog3 dialog3;
    private MDAlertDialog4 dialog4;
    private View pop;
    private int searchType = 1;
    private int searchbox = 0;
    private String searchBk = "";
    private String searchCode = "";
    private int pageNumber = 1;
    private int pageSize = 12;
    private CommonRecycleViewAdapter<BkSearchData.BkRow> adapter3;
    private List<BkSearchData.BkRow> bkrows = new ArrayList<>();
    private List<BkSearchData.BkRow> bkrows_temp = new ArrayList<>();
    private SendPaperData sendPaperData;
    private int year;
    private int position;
    private int sendPosition;
    private int count = 0;
    private Subscription rxsub;
    private Observable<SendEvent> observable;

    private int sendindex = 0;
    private int sendcount = 0;
    private int orderid = 0;

    private boolean showBar = false;//是否显示信箱

    private List<BkSendData.Order> allorder = new ArrayList<>();

    private String result;
    private List<Integer> id1 = new ArrayList<Integer>();
    private List<Integer> id2 = new ArrayList<Integer>();
    private List<Integer> values1 = new ArrayList<Integer>();
    private List<Integer> values2 = new ArrayList<Integer>();
    private List<Integer> eaddr1 = new ArrayList<Integer>();
    private List<Integer> eaddr2 = new ArrayList<Integer>();
    private int j = 0;


    @Override
    protected int getLayoutResource() {
        return R.layout.fra_send_newspaper;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        Calendar c = Calendar.getInstance();//首先要获取日历对象
        year = c.get(Calendar.YEAR);
        for (int i = 0; i < 144; i++) {
            VirtualBoxesData virtualBoxesData = new VirtualBoxesData();
            int m = i + 1;
            String boxnum = String.format("%03d", m);
            virtualBoxesData.setNum(boxnum + "");
            virtualBoxesDatas.add(virtualBoxesData);
        }
//        for (int i = 0; i < 8; i++) {
//             sendPaperData = new SendPaperData();
//            sendPaperData.setPaperName("人民日报" + i);
//            sendPaperDatas.add(sendPaperData);
//        }

        EventBus.getDefault().register(this);
        sendPaperDatas = new SendPaperData().sendPaperData(getActivity(), searchType);
        pop = LayoutInflater.from(getActivity()).inflate(R.layout.pop_searchbk, null);
        btn_left = (Button) pop.findViewById(R.id.pop_leftbtn);
        btn_right = (Button) pop.findViewById(R.id.pop_rightbtn);
        pop_exit = (ImageView) pop.findViewById(R.id.pop_exit);
        irc_search = (IRecyclerView) pop.findViewById(R.id.irc_search);
        pop_et_search = (EditText) pop.findViewById(R.id.pop_et_search);
        pop_search = (ImageView) pop.findViewById(R.id.pop_search);
        ll_search_main = (LinearLayout) pop.findViewById(R.id.ll_search_main);
        ll_search_main.setVisibility(View.VISIBLE);
        btn_right.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        pop_search.setOnClickListener(this);
        //虚拟信箱
        adapter = new CommonRecycleViewAdapter<VirtualBoxesData>(getActivity(), R.layout.item_virtualboxes, virtualBoxesDatas) {
            @Override
            public void convert(ViewHolderHelper helper, final VirtualBoxesData virtualBoxesData) {
                TextView textView = helper.getView(R.id.tv_virtualbox_num);
                LinearLayout ll_virtualbox = helper.getView(R.id.ll_virtualbox);
                textView.setText(virtualBoxesData.getNum());

                ll_virtualbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.getBkDeptDataRequest(Integer.valueOf(virtualBoxesData.getNum()));
//                        ShowDialog( virtualBoxesData.getNum(), virtualBoxesData.getNum(), virtualBoxesData.getNum());
                    }
                });
            }
        };
        irc_virtualboxes.setAdapter(adapter);
        irc_virtualboxes.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //显示固定32个报纸
        adapter2 = new CommonRecycleViewAdapter<SendPaperData>(getActivity(), R.layout.item_sendpaper, sendPaperDatas) {
            @Override
            public void convert(final ViewHolderHelper helper, final SendPaperData sendPaperData) {
                TextView textView = helper.getView(R.id.tv_sendpaper);
                TextView textViewCode = helper.getView(R.id.tv_postcode);
                ImageView imageView = helper.getView(R.id.iv_sendpaper);
                LinearLayout linearLayout = helper.getView(R.id.ll_sendpaper);
                //设置字体
                textView.setTypeface(AppApplication.typeface);
                int length = sendPaperData.getPaperName().length();
                if (length <= 5) {
                    textView.setTextSize(50);
                } else if (length > 5 && length <= 14) {
                    textView.setTextSize(38);
                } else if (length > 14) {
                    textView.setTextSize(26);
                }
                textView.setText(sendPaperData.getPaperName());
                textViewCode.setText(sendPaperData.getDomesticPostCode());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        ToastUitl.showShort("点击了:" + sendPaperData.getPaperName() + "的编辑按钮");
                        try {
                            sendPosition = getPosition(helper) - 2;
                            if (sendPosition < 0) {
                                sendPosition = 0;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendPosition = 0;
                        }
                        showPopupwindow();

                    }
                });

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if ("".equals(sendPaperData.getDomesticPostCode())) {
                            ToastUitl.showShort("待替换");
                        } else {
                            ShowDialog3(sendPaperData.getDomesticPostCode());
                        }
                    }
                });
            }
        };
        irc_sendpaper.setAdapter(adapter2);
        irc_sendpaper.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        //添加分割线
        irc_sendpaper.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        //pop替换名称
        adapter3 = new CommonRecycleViewAdapter<BkSearchData.BkRow>(getActivity(), R.layout.item_search_bk, bkrows) {

            @Override
            public void convert(final ViewHolderHelper helper, final BkSearchData.BkRow bkRow) {

                TextView tv_bkname = helper.getView(R.id.tv_search_bkname);
                CheckBox cb_choice = helper.getView(R.id.cb_choice_bk);
                tv_bkname.setText(bkRow.getBkName());
                cb_choice.setChecked(bkRow.isSelected());
                cb_choice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (BkSearchData.BkRow data : mDatas) {
                            data.setSelected(false);
                        }
                        bkRow.setSelected(true);
                        try {
                            position = getPosition(helper) - 2;
                            if (position < 0) {
                                position = 0;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            position = 0;
                        }
                        sendPaperData = new SendPaperData();
                        sendPaperData.setPaperName(bkRow.getBkName());
                        sendPaperData.setDomesticPostCode(bkRow.getDomesticPostCode());
                        notifyDataSetChanged();
                    }
                });
            }

        };
        irc_search.setAdapter(adapter3);
        irc_search.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        irc_search.setOnLoadMoreListener(this);
        irc_search.setOnRefreshListener(this);

    }

    private void ShowDialog(int txt1, String txt2, String txt3) {
        MDAlertDialog2 dialog1 = new MDAlertDialog2.Builder(getActivity())
                .setHeight(0.7f)  //屏幕高度*0.21
                .setWidth(0.25f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("信箱")
                .setTitleTextColor(R.color.white)
                .setContentText("确定发送文件？")
                .setContentTextColor(R.color.black_light)
                .setStringtxt1(txt1 + "")
                .setStringtxt2(txt2)
                .setStringtxt3(txt3)
                .setCanceledOnTouchOutside(false)
                .build();
        dialog1.show();
    }

    //虚拟信箱的显示状态
    @OnClick(R.id.change_bar)
    public void OnClick(View view) {
        LinearLayout container_bar = (LinearLayout) rootView.findViewById(R.id.container_bar);
        TextView direction = (TextView) rootView.findViewById(R.id.direction);
            if (showBar==false) {
                container_bar.setVisibility(View.VISIBLE);
                direction.setBackgroundResource(R.drawable.retract_arrow);
                showBar = true;
            } else {
                container_bar.setVisibility(View.GONE);
                direction.setBackgroundResource(R.drawable.open_arrow);
                showBar = false;
            }
    }

    //是否分发该报纸
    private void ShowDialog3(final String domesticPostCode) {
        dialog3 = new MDAlertDialog3.Builder(getActivity())
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setContentText("是否分发该报刊")
                .setContentTextColor(R.color.black_light)
                .setTitleTextSize(35)
                .setIvImageResource(R.mipmap.send_start)
                .setLlbox2Visible(false)
                .setLlboxVisible(true)
                .setLeftButtonTextColor(R.color.white)
                .setRightButtonTextColor(R.color.white)
                .setIvVisible(true)
                .setContentTextSize(35)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogOnClickListener1() {
                    @Override
                    public void clickLeftButton(View view) {
                        try {
//                            Log.i("clickLeftButton=====", searchType + "@@" + year + domesticPostCode);
                            mPresenter.getBkSendDataRequest(searchType, year, domesticPostCode);
                            dialog3.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog3.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {

                    }
                })
                .build();
        dialog3.show();
    }

    //正在分发中
    private void ShowDialog4() {
        dialog3 = new MDAlertDialog3.Builder(getActivity())
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setIvVisible(false)
                .setPbVisible(true)
                .setContentText("正在分发中... ...")
                .setLlboxVisible(false)
                .setLlbox2Visible(false)

                .setCenterButtonText("结束分发")
                .setContentTextColor(R.color.black_light)
                .setTitleTextSize(35)
                .setContentTextSize(35)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogOnClickListener1() {
                    @Override
                    public void clickLeftButton(View view) {
                        try {
                            dialog3.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog3.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {
                        dialog3.dismiss();
                        ShowDialog5();
                    }
                })
                .build();
        dialog3.show();
    }

    //分发成功
    private void ShowDialog5() {
        dialog4 = new MDAlertDialog4.Builder(getActivity())
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setContentText("分发结束!")
                .setLlbox2Visible(true)
                .setLlboxVisible(false)
                .setIvImageResource(R.mipmap.send_end)
                .setCenterButtonText("关闭")
                .setContentTextColor(R.color.black_light)
                .setTitleTextSize(35)
                .setContentTextSize(35)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogOnClickListener1() {
                    @Override
                    public void clickLeftButton(View view) {
                        dialog4.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog4.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {
                        dialog4.dismiss();
                        ToastUitl.showShort("正在设置中，请稍后...");
                        mPresenter.getEndSendRequest(padaddress,100);
                    }
                })
                .build();
        dialog4.show();
    }

    private void showPopupwindow() {
        bkrows = new ArrayList<>();
        pop_et_search.setText("");
        adapter3.clear();
        popupWindow = new BasePopupWindow(getActivity());
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(pop);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAtLocation(ll_search_main, Gravity.CENTER, 0, 0);
        irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        irc_search.setRefreshing(false);
        pop.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        adapter3.getPageBean().setRefresh(false);
        //发起请求
        irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getBkSearchDataRequest(searchBk, searchCode, searchType, year, pageNumber, pageSize);
    }

    @Override
    public void onRefresh() {
        adapter3.getPageBean().setRefresh(true);
        pageNumber = 1;
        count = 0;
        //发起请求
        irc_search.setRefreshing(true);
        irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        mPresenter.getBkSearchDataRequest(searchBk, searchCode, searchType, year, pageNumber, pageSize);
    }

    @Override
    public void returngetAllDeptData(JSONObject jsonObject) {

    }

    @Override
    public void returngetBkDeptData(BkDeptData bkDeptData) {
        LogUtils.logi(bkDeptData.getBkInfoList().toString());
        //弹出虚拟信箱内容
        int m = bkDeptData.getBkInfoList().size();
        String s = "";
        if (m > 0) {
            for (int i = 0; i < m; i++) {
                s += bkDeptData.getBkInfoList().get(i).getBkName() + "\n";

            }
        }
        ShowDialog(bkDeptData.getDeptBox(), bkDeptData.getDeptName(), s);
    }

    @Override
    public void returngetBkSearchData(BkSearchData bkSearchData) {
        try {
//            ToastUitl.showShort(bkSearchData.getTotal() + "&&&&" + bkSearchData.getRows().size() + "&&&" + pageNumber);
            int m = bkSearchData.getRows().size();
            count += m;
            int total = Integer.valueOf(bkSearchData.getTotal());
            if (total > 0) {
                if (count <= total) {
                    if (m > 0) {
                        bkrows_temp = bkSearchData.getRows();
                        pageNumber += 1;
                        if (adapter3.getPageBean().isRefresh()) {
                            irc_search.setRefreshing(false);
                            adapter3.replaceAll(bkrows_temp);
                        } else {
                            if (bkrows_temp.size() > 0) {
                                irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                                adapter3.addAll(bkrows_temp);
                            } else {
                                irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                            }
                        }
                    }
                } else {

                    irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                }
            } else {
                ToastUitl.showShort("没有数据");
                irc_search.setRefreshing(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 传送硬件，开始一次性分发报纸数量
     * @param orders
     */
    private void StartSendALLPaper ( final List<BkSendData.Order> orders){

        try {
            sendindex = 0;//索引
            sendcount = orders.size();//总个数
            allorder = orders;//数据集合
            for (int i = 0; i < orders.size(); i++) {
                int m = orders.get(i).getDeptBox();
                if (m < 73) {
                    id1.add(m);
                    values1.add(orders.get(i).getOrderNum());
                    eaddr1.add(padaddress);
                } else {
                    id2.add(m);
                    values2.add(orders.get(i).getOrderNum());
                    eaddr2.add(padaddress);
                }
            }
            int mid1 = id1.size();
            Integer[] id1arr = (Integer[]) id1.toArray(new Integer[mid1]);
            int mid2 = id2.size();
            Integer[] id2arr = (Integer[]) id2.toArray(new Integer[mid2]);
            int mv1 = values1.size();
            Integer[] v1arr = (Integer[]) values1.toArray(new Integer[mv1]);
            int mv2 = values2.size();
            Integer[] v2arr = (Integer[]) values2.toArray(new Integer[mv2]);
            int me1 = eaddr1.size();
            Integer[] e1arr = (Integer[]) eaddr1.toArray(new Integer[me1]);
            int me2 = eaddr2.size();
            Integer[] e2arr = (Integer[]) eaddr2.toArray(new Integer[me2]);
            //Arrays.toString() 将对应数组的值转换为字符串 并 显示出来。 Arrays.asList()将一个数组转化为一个List对象
            result = "{" + "\"id1\":" + Arrays.toString(id1arr) + ",\"values1\":" + Arrays.toString(v1arr) + ",\"eaddr1\":" + Arrays.toString(e1arr) + ",\"id2\":" + Arrays.toString(id2arr) + ",\"values2\":" + Arrays.toString(v2arr) + ",\"eaddr2\":" + Arrays.toString(e2arr) + "}";
            LogUtils.logi(result);
            mPresenter.getStartSendALLPaperDataRequest(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returngetBkSendData(BkSendData bkSendData) {
        try {
            if (bkSendData != null) {
                List<BkSendData.Order> orders = bkSendData.getOrder();
                if (orders.size() > 0) {
                    ShowDialog4();
                    String s = JSON.toJSONString(bkSendData);
                    LogUtils.logd(s);
                    //StartSendPaper(orders);
                    StartSendALLPaper(orders);//一次性发完
                } else {
                    ToastUitl.showShort("没有待分发的报刊!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 传送硬件，开始分发报纸数量
     */
    private void StartSendPaper(final List<BkSendData.Order> orders) {
        try {

            sendindex = 0;//索引
            orderid = 0;
            sendcount = orders.size();//总个数
            allorder = orders;//数据集合
            sendPaper();//第一个开始
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
            //发送硬件接口
//            try {
//
//                //调用硬件发送接口,返回后再调更新已发报刊接口
//                orderid = orders.get(i).getOrderNum();//订单信息
//                mPresenter.getdisplayDataRequest(orders.get(i).getDeptBox(), padaddress, orders.get(i).getOrderNum());
//                LogUtils.logi(orders.get(i).getDeptBox() + "&&" + padaddress + "&&" + orders.get(i).getOrderNum());
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//                    EventBus.getDefault().post(new SendEvent(2));
//                }
//            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPaper() {
        try {
            if (sendindex < sendcount) {
                orderid = allorder.get(sendindex).getOrderNum();//订单信息
                //发送硬件0,1,2
                mPresenter.getdisplayDataRequest(allorder.get(sendindex).getDeptBox(), padaddress, allorder.get(sendindex).getOrderNum());
            } else {//个数更新结束
                EventBus.getDefault().post(new SendEvent(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //单个发送报纸的时候使用
    @Override
    public void returngetdisplayData(String result) {
        //硬件返回后更新订单信息
        ToastUitl.showShort(result);
        mPresenter.getBkOrderDataRequest(allorder.get(sendindex).getOrderId());//更新收发
    }

    @Override
    public void returngetBkOrderData(BkOrderData bkOrderData) {
//        sendindex++;
//        sendPaper();
        if(j==sendcount){
            EventBus.getDefault().post(new SendEvent(3));//通过post发送信息
        }
        LogUtils.logi("iiiiiiiii=======" + sendcount + "jjjjjj" + j);
    }


    //更新发送的报纸数量
    @Override
    public void returngetTSendData(BkTSendData bkTSendData) {
        MainActivity.bknum = bkTSendData.getBk();
        MainActivity.qknum = bkTSendData.getQk();
        EventBus.getDefault().post(new SendNumEvent(1));
    }

    @Override
    public void returngetEndSendData(BKBroadDisplayData sendBKDisplayData) {

        try {
            if (sendBKDisplayData != null) {
                if (sendBKDisplayData.getBroadcastDisplay().equals("ok")) {
                    showShortToast("设置数码管全灭成功");

                } else {
                    showShortToast("设置数码管全灭失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returngetAllDeptDatas(BKAllDeptData bkBKAllDeptData) {

    }

    @Override
    public void returngetdisplayData1(String string) {

    }

    @Override
    public void returngetStartSendALLPaperData(String jsonObject) {
        try {
            if(jsonObject!=null){
                ToastUitl.showShort(jsonObject);

                //成功则更新服务器的数据
                for (j=0; j<sendcount;j++){
                    mPresenter.getBkOrderDataRequest(allorder.get(j).getOrderId());//更新收发
                }

                //EventBus.getDefault().post(new SendEvent(3));//通过post发送信息
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(SendEvent sendEvent) {
        if (sendEvent.getNum() == 3) {
            j = 0;
            dialog3.dismiss();
            ShowDialog5();
            mPresenter.getTSendDataRequest();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_search:
                pageNumber = 1;
                count = 0;
                //发起请求
                getReplaceData();
                KeyBordUtil.hideSoftKeyboard(ll_search_main);

                break;
            case R.id.pop_leftbtn:
                //将数组的position的索引替换内容并刷新irc
                if (sendPaperData != null) {
                    sendPaperDatas.set(sendPosition, sendPaperData);
                    String jsonString = JSON.toJSONString(sendPaperDatas);
                    SPUtils.setSharedStringData(getActivity(), "sendPaperDatas", jsonString);
//                    ToastUitl.showShort(jsonString);
                    adapter2.notifyDataSetChanged();
                }
                bkrows = new ArrayList<>();
                sendPaperData = null;
                adapter3.clear();
                popupWindow.dismiss();
                break;
            case R.id.pop_rightbtn:
                sendPaperData = null;
                adapter3.clear();
                bkrows = new ArrayList<>();
                popupWindow.dismiss();
                break;
        }

    }

    private void getReplaceData() {
        String s = pop_et_search.getText().toString();
        searchCode = "";
        searchBk = "";
        if (s.contains("-")) {
            searchCode = s;
        } else {
            searchBk = s;
        }
        if (!"".equals(searchBk) || !"".equals(searchCode)) {
            irc_search.setRefreshing(true);
            mPresenter.getBkSearchDataRequest(searchBk, searchCode, searchType, year, pageNumber, pageSize);
        } else {
            adapter3.clear();
            ToastUitl.showShort("请输入报刊名称");
        }
    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {
        if ("1".equals(msg)) {
            dialog3.dismiss();
            ShowDialog5();
//            sendindex++;
//            sendPaper();
            ToastUitl.showShort("发送硬件服务器失败");
        } else if ("2".equals(msg)) {
            dialog3.dismiss();
            ShowDialog5();
//            sendindex++;
//            sendPaper();
            ToastUitl.showShort("更新收发服务器失败");
        } else {
            ToastUitl.showShort(msg);
            if (dialog3 != null) {
                dialog3.dismiss();
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

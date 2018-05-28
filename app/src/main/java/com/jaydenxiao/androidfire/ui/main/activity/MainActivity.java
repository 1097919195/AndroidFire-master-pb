package com.jaydenxiao.androidfire.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.aspsine.irecyclerview.universaladapter.ViewHolderHelper;
import com.aspsine.irecyclerview.universaladapter.recyclerview.CommonRecycleViewAdapter;
import com.aspsine.irecyclerview.widget.LoadMoreFooterView;
import com.google.zxing.activity.CaptureActivity;
import com.jaydenxiao.androidfire.R;
import com.jaydenxiao.androidfire.app.AppConstant;
import com.jaydenxiao.androidfire.bean.BKAllDeptData;
import com.jaydenxiao.androidfire.bean.BKBroadDisplayData;
import com.jaydenxiao.androidfire.bean.BkDeptData;
import com.jaydenxiao.androidfire.bean.BkOrderData;
import com.jaydenxiao.androidfire.bean.BkSearchData;
import com.jaydenxiao.androidfire.bean.BkSendData;
import com.jaydenxiao.androidfire.bean.BkTSendData;
import com.jaydenxiao.androidfire.event.SendEvent;
import com.jaydenxiao.androidfire.event.SendNumEvent;
import com.jaydenxiao.androidfire.ui.main.contract.VirtualBoxesContract;
import com.jaydenxiao.androidfire.ui.main.fragment.SendNewspaperFragment;
import com.jaydenxiao.androidfire.ui.main.fragment.SendThePressFragment;
import com.jaydenxiao.androidfire.ui.main.model.VirtualBoxesModel;
import com.jaydenxiao.androidfire.ui.main.presenter.VirtualBoxesPresenter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.base.BasePopupWindow;
import com.jaydenxiao.common.baseapp.AppConfig;
import com.jaydenxiao.common.commonutils.KeyBordUtil;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.daynightmodeutils.ChangeModeController;
import com.wevey.selector.dialog.DialogOnClickListener1;
import com.wevey.selector.dialog.DialogTip;
import com.wevey.selector.dialog.MDAlertDialog2;
import com.wevey.selector.dialog.MDAlertDialog3;
import com.wevey.selector.dialog.MDAlertDialog4;
import com.wevey.selector.dialog.MDAlertDialog8;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * des:主界面
 * Created by xsf
 * on 2016.09.15:32
 */
public class MainActivity extends BaseActivity<VirtualBoxesPresenter, VirtualBoxesModel> implements VirtualBoxesContract.View, View.OnClickListener, OnLoadMoreListener, OnRefreshListener {
    //    @Bind(R.id.tab_layout)
//    CommonTabLayout tabLayout;
    @Bind(R.id.iv_send_newspaper)
    ImageView iv_send_newspaper;
    @Bind(R.id.iv_send_thepress)
    ImageView iv_send_thepress;
    @Bind(R.id.iv_send_setting)
    ImageView iv_send_setting;
    @Bind(R.id.sp_send_type)
    Spinner sp_send_type;
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.rl_main)
    RelativeLayout rl_main;
    @Bind(R.id.tv_newspaper_num)
    TextView newspaper_num;
    @Bind(R.id.tv_thepress_num)
    TextView thepress_num;

    @Bind(R.id.container_department_one)
    LinearLayout container_department_one;
    @Bind(R.id.container_department_two)
    LinearLayout container_department_two;
    @Bind(R.id.iv_department_one)
    ImageView iv_department_one;
    @Bind(R.id.iv_department_two)
    ImageView iv_department_two;


//    private String[] mTitles = {"首页", "服务","本地","我的"};
//    private int[] mIconUnselectIds = {
//            R.mipmap.ic_home_normal,R.mipmap.ic_girl_normal,R.mipmap.ic_video_normal,R.mipmap.ic_care_normal};
//    private int[] mIconSelectIds = {
//            R.mipmap.ic_home_selected,R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected,R.mipmap.ic_care_selected};
//    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    //    private NewsMainFragment newsMainFragment;
    private SendNewspaperFragment sendNewspaperFragment;
    private SendThePressFragment sendThePressFragment;
    //    private PhotosMainFragment photosMainFragment;
//    private VideoMainFragment videoMainFragment;
//    private CareMainFragment careMainFragment;
    private View pop;
    private Button btn_left;
    private Button btn_right;
    private ImageView pop_exit;
    private IRecyclerView irc_search;
    private BasePopupWindow popupWindow;
    private static int tabLayoutHeight;
    private Intent intent;
    private String username = "";
    private static Boolean isQuit = false;
    private Timer timer = new Timer();
    private static final int REQ_CODE_PERMISSION = 0x1111;
    public static final int REQ_CODE = 0xF0F0;
    public static int padaddress = 0;//硬件地址1,2,3,4

    private int searchType;//搜索类型
    private int searchbox = 0;//信箱号
    private String searchBk = "";//模糊搜索报刊名
    private String searchCode = "";//模糊搜索报刊邮发代码
    private int pageNumber = 1;
    private int pageSize = 12;
    private CommonRecycleViewAdapter<BkSearchData.BkRow> adapter;
    private List<BkSearchData.BkRow> bkrows = new ArrayList<>();
    private int year;
    private int position;
    private MDAlertDialog3 dialog3,dialog6,dialog7;
    private MDAlertDialog4 dialog4,dialog9;
    private MDAlertDialog8 dialog8;
    private DialogTip dialogTip;
    private DialogTip dialogTip1;
    private int count = 0;

    public static int bknum = 0;
    public static int qknum = 0;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private int sendindex = 0;
    private int sendcount = 0;
    private int orderid = 0;

    private boolean lightOffOne = false;//是否熄灭绿灯
    private boolean lightOffTwo = false;//是否熄灭绿灯
    private int btn;
    private int sendindex_lamp = 0;
    private int sendcount_lamp = 0;

    private List<BkSendData.Order> allorder = new ArrayList<>();
    private List<BKAllDeptData.BKDept> BKDept = new ArrayList<>();

    private String result;
    private List<Integer> id1 = new ArrayList<Integer>();
    private List<Integer> id2 = new ArrayList<Integer>();
    private List<Integer> values1 = new ArrayList<Integer>();
    private List<Integer> values2 = new ArrayList<Integer>();
    private List<Integer> eaddr1 = new ArrayList<Integer>();
    private List<Integer> eaddr2 = new ArrayList<Integer>();
    private int j = 0;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                com.jaydenxiao.common.R.anim.fade_out);
    }

    @Override
    public int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        //左下角按钮背景的透明度设置
        container_department_one.getBackground().setAlpha(150);
        container_department_two.getBackground().setAlpha(150);

        //此处填上在http://fir.im/注册账号后获得的API_TOKEN以及APP的应用ID
        UpdateKey.API_TOKEN = AppConfig.API_FIRE_TOKEN;
        UpdateKey.APP_ID = AppConfig.APP_FIRE_ID;
        //如果你想通过Dialog来进行下载，可以如下设置
//        UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;
        UpdateFunGO.init(this);
        //初始化菜单
//        initTab();
        //初始化sipnner
        initSipnner();
        mPresenter.getTSendDataRequest();
        try {
            intent = getIntent();
            username = intent.getStringExtra("username");
            tv_username.setText(username);
        } catch (Exception e) {
            e.printStackTrace();
            tv_username.setText("未知用户");
        }
        sp = getSharedPreferences(AppConstant.USERINFO, MODE_PRIVATE);
        try {
            padaddress = Integer.valueOf(sp.getString("padaddress", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ToastUitl.showShort("平板对应硬件地址错误，导致发送硬件失败");
            padaddress = 0;
        }
        Calendar c = Calendar.getInstance();//首先要获取日历对象
        year = c.get(Calendar.YEAR);//获得当前的年份
        EventBus.getDefault().register(this);//EventBus注册
        pop = LayoutInflater.from(this).inflate(R.layout.pop_searchbk, null);
        btn_left = (Button) pop.findViewById(R.id.pop_leftbtn);
        btn_right = (Button) pop.findViewById(R.id.pop_rightbtn);
        pop_exit = (ImageView) pop.findViewById(R.id.pop_exit);
        irc_search = (IRecyclerView) pop.findViewById(R.id.irc_search);
        btn_right.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        adapter = new CommonRecycleViewAdapter<BkSearchData.BkRow>(this, R.layout.item_search_bk, bkrows) {

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
                        notifyDataSetChanged();
                    }
                });
            }

        };
        irc_search.setAdapter(adapter);
        irc_search.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        irc_search.setOnLoadMoreListener(this);
        irc_search.setOnRefreshListener(this);
        pop_exit.setOnClickListener(this);

    }


    private void initSipnner() {
        List<String> spinnerList = new ArrayList<String>();
        //spinnerList.add("搜报箱");
        spinnerList.add("搜报纸");
        spinnerList.add("搜期刊");
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.item_sipnner_search, R.id.tv_sipnner, spinnerList);
        /* myspinner_dropdown 为自定义下拉菜单样式定义在res/layout 目录下 */
        myAdapter.setDropDownViewResource(R.layout.item_sipnner_search);
        this.sp_send_type.setAdapter(myAdapter);

        /* 下拉菜单弹出的内容选项被选中事件处理 */
        sp_send_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                searchType = i+1;
                Log.i("searchType", i + "");
//                if (searchType == 0) {
//                     /* 将所选mySpinner 的类型带入EditText 中 */
//                     //输入类型为数字文本
//                    et_search.setInputType(InputType.TYPE_CLASS_NUMBER);
//                    et_search.setText("");
//                } else {
//                    //输入类型为普通文本
//                    et_search.setInputType(InputType.TYPE_CLASS_TEXT);
//                    et_search.setText("");
//                }
                et_search.setInputType(InputType.TYPE_CLASS_TEXT);
                et_search.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //切换daynight模式要立即变色的页面
        ChangeModeController.getInstance().init(this, R.attr.class);
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
//        tabLayout.measure(0,0);
//        tabLayoutHeight=tabLayout.getMeasuredHeight();
        //监听菜单显示或隐藏
//        mRxManager.on(AppConstant.MENU_SHOW_HIDE, new Action1<Boolean>() {
//
//            @Override
//            public void call(Boolean hideOrShow) {
//                startAnimation(hideOrShow);
//            }
//        });
    }

    //
    @OnClick({R.id.iv_send_newspaper, R.id.iv_send_thepress, R.id.iv_search, R.id.iv_end_send,R.id.iv_exit, R.id.iv_send_setting,R.id.container_department_one,R.id.container_department_two})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_send_newspaper:
                SwitchTo(0);
                break;
            case R.id.iv_send_thepress:
                SwitchTo(1);
                break;
            case R.id.iv_search:
                adapter.clear();
                pageNumber = 1;
//                if (searchType == 0) {
//                    getSearchBoxData();
//                } else {
//                    getSearchBkData();
//                }
                getSearchBkData();
                //隐藏主界面的软键盘
                KeyBordUtil.hideSoftKeyboard(rl_main);
                break;
            case R.id.iv_end_send:
                DialogTip();
                break;
//            case R.id.iv_saosao:
//                //检测是否拥有权限
//                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    // 申请授权
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
//                } else {
//                    startCaptureActivityForResult();
//                }
//                break;
            case R.id.iv_exit:
                DialogTip1();
//                mPresenter.getTSendDataRequest();
//                showPopupwindow();
                break;
            case R.id.container_department_one:
                btn = 1;
                if (!lightOffOne) {
                    ShowDialog6();
                } else {
                    iv_department_one.setBackgroundResource(R.drawable.belong_to_normal);
                    lightOffOne = false;
                }
                break;
            case R.id.container_department_two:
                btn = 2;
                if (!lightOffTwo) {
                    ShowDialog7();
                } else {
                    iv_department_two.setBackgroundResource(R.drawable.belong_to_normal);
                    lightOffTwo = false;
                }
                break;
            default:
                break;
        }
    }

    //是否关闭数码管
    private void DialogTip() {
        dialogTip = new DialogTip.Builder(this)
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setContentText("全灭控制的数码管")
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
                        ToastUitl.showShort("正在设置中，请稍后...");
                        mPresenter.getEndSendRequest(padaddress,100);
                        dialogTip.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialogTip.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {

                    }
                })
                .build();
        dialogTip.show();
    }

    //是否退出登录
    private void DialogTip1() {
        dialogTip1 = new DialogTip.Builder(this)
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setContentText("是否退出登录")
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
                        finish();
                        dialogTip1.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialogTip1.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {

                    }
                })
                .build();
        dialogTip1.show();
    }
    /**
     * 获取报刊搜索结果
     */
    private void getSearchBkData() {
        String s = et_search.getText().toString();
        searchCode = "";
        searchBk = "";
        count = 0;
        //String.contains此方法返回true，如果此字符串包含，否则返回false
        if (s.contains("-")) {
            searchCode = s;//例如1-6
        } else {
            searchBk = s;//例如中国能源报
        }
        LogUtils.logi(searchCode + "&&" + searchBk);
        mPresenter.getBkSearchDataRequest(searchBk, searchCode, searchType, year, pageNumber, pageSize);
        showPopupwindow();
    }

    /**
     * 获取信箱搜索结果
     */
    private void getSearchBoxData() {

        try {
            searchbox = Integer.valueOf(et_search.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        mPresenter.getBkDeptDataRequest(searchbox);

    }

    //启动扫描界面
    private void startCaptureActivityForResult() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQ_CODE);
    }

    //用户是否授权
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    ToastUitl.showShort("You must agree the camera permission request before you use the code scan function");
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        ToastUitl.showShort(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            ToastUitl.showShort(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                        }
                        break;
                }
                break;
        }
    }
    /**
     * 初始化tab
     */
//    private void initTab() {
//        for (int i = 0; i < mTitles.length; i++) {
//            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
//        }
//        tabLayout.setTabData(mTabEntities);
//        //点击监听
//        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                SwitchTo(position);
//            }
//            @Override
//            public void onTabReselect(int position) {
//            }
//        });
//    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            sendNewspaperFragment = (SendNewspaperFragment) getSupportFragmentManager().findFragmentByTag("sendNewspaperFragment");
            sendThePressFragment = (SendThePressFragment) getSupportFragmentManager().findFragmentByTag("sendThePressFragment");
//            newsMainFragment = (NewsMainFragment) getSupportFragmentManager().findFragmentByTag("newsMainFragment");
//            photosMainFragment = (PhotosMainFragment) getSupportFragmentManager().findFragmentByTag("photosMainFragment");
//            videoMainFragment = (VideoMainFragment) getSupportFragmentManager().findFragmentByTag("videoMainFragment");
//            careMainFragment = (CareMainFragment) getSupportFragmentManager().findFragmentByTag("careMainFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            sendNewspaperFragment = new SendNewspaperFragment();
            sendThePressFragment = new SendThePressFragment();
//            newsMainFragment = new NewsMainFragment();
//            photosMainFragment = new PhotosMainFragment();
//            videoMainFragment = new VideoMainFragment();
//            careMainFragment = new CareMainFragment();

            transaction.add(R.id.fl_body, sendNewspaperFragment, "sendNewspaperFragment");
            transaction.add(R.id.fl_body, sendThePressFragment, "sendThePressFragment");
//            transaction.add(R.id.fl_body, newsMainFragment, "newsMainFragment");
//            transaction.add(R.id.fl_body, photosMainFragment, "photosMainFragment");
//            transaction.add(R.id.fl_body, videoMainFragment, "videoMainFragment");
//            transaction.add(R.id.fl_body, careMainFragment, "careMainFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
//        tabLayout.setCurrentTab(currentTabPosition);
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {

            case 0:
//                transaction.hide(photosMainFragment);
//                transaction.hide(videoMainFragment);
//                transaction.hide(careMainFragment);
//                transaction.show(newsMainFragment);
                transaction.hide(sendThePressFragment);
                transaction.show(sendNewspaperFragment);
                transaction.commitAllowingStateLoss();
                iv_send_newspaper.setImageResource(R.drawable.snewspaper_in);
                iv_send_thepress.setImageResource(R.drawable.sthepress_out);
                break;

            case 1:
//                transaction.hide(newsMainFragment);
                transaction.hide(sendNewspaperFragment);
//                transaction.hide(videoMainFragment);
//                transaction.hide(careMainFragment);
//                transaction.show(photosMainFragment);
                transaction.show(sendThePressFragment);
                transaction.commitAllowingStateLoss();
                iv_send_newspaper.setImageResource(R.drawable.snewspaper_out);
                iv_send_thepress.setImageResource(R.drawable.sthepress_in);
                break;
            //视频
            case 2:
//                transaction.hide(newsMainFragment);
//                transaction.hide(sendNewspaperFragment);
//                transaction.hide(photosMainFragment);
//                transaction.hide(careMainFragment);
//                transaction.show(videoMainFragment);
//                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
//                transaction.hide(newsMainFragment);
//                transaction.hide(sendNewspaperFragment);
//                transaction.hide(photosMainFragment);
//                transaction.hide(videoMainFragment);
//                transaction.show(careMainFragment);
//                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    /**
     * 菜单显示隐藏动画
     * @param showOrHide
     */
//    private void startAnimation(boolean showOrHide){
//        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
//        ValueAnimator valueAnimator;
//        ObjectAnimator alpha;
//        if(!showOrHide){
//             valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
//            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
//        }else{
//             valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
//            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
//        }
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                layoutParams.height= (int) valueAnimator.getAnimatedValue();
//                tabLayout.setLayoutParams(layoutParams);
//            }
//        });
//        AnimatorSet animatorSet=new AnimatorSet();
//        animatorSet.setDuration(500);
//        animatorSet.playTogether(valueAnimator,alpha);
//        animatorSet.start();
//    }

    /**
     * 监听全屏视频时返回键
     */
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isQuit) {
                isQuit = true;
                ToastUitl.showShort("再次点击确定退出软件");
                TimerTask task = null;
                task = new TimerTask() {
                    @Override
                    public void run() {
                        isQuit = false;
                    }
                };
                timer.schedule(task, 2000);
            } else {
//                moveTaskToBack(false);//退后台
                finish();
            }
        }
//        return super.onKeyDown(keyCode, event);//返回上一级
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.loge("onSaveInstanceState进来了1");
//        if (tabLayout != null) {
//            LogUtils.loge("onSaveInstanceState进来了2");
//            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateFunGO.onResume(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChangeModeController.onDestory();
        EventBus.getDefault().unregister(this);//取消注册
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

    private void ShowDialog(int txt1, String txt2, String txt3) {
        MDAlertDialog2 dialog1 = new MDAlertDialog2.Builder(this)
                .setHeight(0.7f)  //屏幕高度*0.21
                .setWidth(0.2f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("信箱")
                .setTitleTextColor(R.color.white)
                .setContentText("确定发送文件？")
                .setContentTextColor(R.color.black_light)
                .setStringtxt1(txt1 + "")
                .setStringtxt2(txt2)
                .setStringtxt3(txt3)
                .setTitleTextSize(16)
                .setCanceledOnTouchOutside(false)
                .build();
        dialog1.show();
    }

    private void ShowDialog3() {
        dialog3 = new MDAlertDialog3.Builder(this)
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setContentText("是否分发该报刊")
                .setIvVisible(true)
                .setIvImageResource(R.mipmap.send_start)
                .setLeftButtonTextColor(R.color.white)
                .setRightButtonTextColor(R.color.white)
                .setContentTextColor(R.color.black_light)
                .setTitleTextSize(35)
                .setContentTextSize(35)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogOnClickListener1() {
                    @Override
                    public void clickLeftButton(View view) {
                        try {
                            Log.i("clickLeftButton=====", searchType + year + bkrows.get(position).getDomesticPostCode());
                            mPresenter.getBkSendDataRequest(searchType, year, bkrows.get(position).getDomesticPostCode());
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
        dialog3 = new MDAlertDialog3.Builder(this)
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
                .setCenterButtonText("完成")
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
        dialog4 = new MDAlertDialog4.Builder(this)
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
                        ToastUitl.showShort("正在设置中，请稍后...");
                        mPresenter.getEndSendRequest(padaddress,100);
                        dialog4.dismiss();

                    }
                })
                .build();
        dialog4.show();
    }

    private void ShowDialog6() {
        dialog6 = new MDAlertDialog3.Builder(this)
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setContentText("是否点亮固定的报箱")
                .setIvVisible(true)
                .setIvImageResource(R.mipmap.send_start)
                .setLeftButtonTextColor(R.color.white)
                .setRightButtonTextColor(R.color.white)
                .setContentTextColor(R.color.black_light)
                .setTitleTextSize(35)
                .setContentTextSize(35)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogOnClickListener1() {
                    @Override
                    public void clickLeftButton(View view) {
                        try {
                            mPresenter.getAllDeptDataRequests(1);
                            dialog6.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog6.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {
                        dialog6.dismiss();
                    }
                })
                .build();
        dialog6.show();
    }

    private void ShowDialog7() {
        dialog7 = new MDAlertDialog3.Builder(this)
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setContentText("是否点亮固定的报箱")
                .setIvVisible(true)
                .setIvImageResource(R.mipmap.send_start)
                .setLeftButtonTextColor(R.color.white)
                .setRightButtonTextColor(R.color.white)
                .setContentTextColor(R.color.black_light)
                .setTitleTextSize(35)
                .setContentTextSize(35)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogOnClickListener1() {
                    @Override
                    public void clickLeftButton(View view) {
                        try {
                            mPresenter.getAllDeptDataRequests(2);
                            dialog7.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog7.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {

                    }
                })
                .build();
        dialog7.show();
    }

    private void ShowDialog8() {
        dialog8 = new MDAlertDialog8.Builder(this)
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setIvVisible(false)
                .setPbVisible(true)
                .setContentText("正在点亮... ...")
                .setLlboxVisible(false)
                .setLlbox2Visible(false)
                .setCenterButtonText("完成")
                .setContentTextColor(R.color.black_light)
                .setTitleTextSize(35)
                .setContentTextSize(35)
                .setCanceledOnTouchOutside(false)
                .setOnclickListener(new DialogOnClickListener1() {
                    @Override
                    public void clickLeftButton(View view) {
                        try {
                            dialog8.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog8.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {
                        dialog8.dismiss();
                    }
                })
                .build();
        dialog8.show();
    }

    private void ShowDialog9() {
        dialog9 = new MDAlertDialog4.Builder(this)
                .setHeight(0.45f)  //屏幕高度*0.21
                .setWidth(0.4f)  //屏幕宽度*0.7
                .setTitleVisible(true)
                .setTitleText("提示")
                .setTitleTextColor(R.color.white)
                .setContentText("结束点亮!")
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
                        dialog9.dismiss();
                    }

                    @Override
                    public void clickRightButton(View view) {
                        dialog9.dismiss();
                    }

                    @Override
                    public void clickCenterButton(View view) {
                        dialog9.dismiss();
                    }
                })
                .build();
        dialog9.show();
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

    //获取需要点亮的报箱
    @Override
    public void returngetAllDeptDatas(BKAllDeptData bkAllDeptData) {
        try {
            if (bkAllDeptData != null) {
                List<BKAllDeptData.BKDept> mlist = bkAllDeptData.getMlist();
                if (mlist.size() > 0) {
                    ShowDialog8();
                    StartSendLamp(mlist);
                    //mPresenter.getdisplayDataRequest1(mlist.get(0).getDeptBox(), padaddress, 0);
                } else {
                    showShortToast("无未代点亮的报箱");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returngetBkSearchData(BkSearchData bkSearchData) {
        try {
            ToastUitl.showShort(bkSearchData.getTotal() + "&&&&" + bkSearchData.getRows().size() + "&&&" + pageNumber);
            int m = bkSearchData.getRows().size();
            int total = Integer.valueOf(bkSearchData.getTotal());
            count += m;
            if (total > 0) {
                if (count <= total) {
                    if (m > 0) {
                        bkrows = bkSearchData.getRows();
                        pageNumber += 1;
                        if (adapter.getPageBean().isRefresh()) {
                            irc_search.setRefreshing(false);
                            adapter.replaceAll(bkrows);
                        } else {
                            if (bkrows.size() > 0) {
                                irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                                adapter.addAll(bkrows);
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

    //显示弹出的搜索窗口
    private void showPopupwindow() {
        popupWindow = new BasePopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(pop);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.showAtLocation(rl_main, Gravity.CENTER, 0, 0);
    }

    @Override
    public void returngetBkSendData(BkSendData bkSendData) {
        try {
            if (bkSendData != null) {
                List<BkSendData.Order> orders = bkSendData.getOrder();
                if (orders.size() > 0) {
                    ShowDialog4();
//                    String s = JSON.toJSONString(bkSendData);
//                    LogUtils.logd(s);
                    StartSendALLPaper(orders);//一次性完成全部报的分发
                    //StartSendPaper(orders);
                } else {
                    ToastUitl.showShort("没有待分发的报刊!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //成功点亮报箱后的操作
    @Override
    public void returngetdisplayData1(String string) {
        showShortToast(string);
        //发送成功索引+1，再发送接下来的sendLamp，直到发完
        sendindex_lamp++;
        sendLamp();
    }

    @Override
    public void returngetStartSendALLPaperData(String jsonObject) {
        try {
            if(jsonObject!=null){
                showShortToast(jsonObject);

                //成功则更新服务器的数据
                for (j=0; j<sendcount;j++){
                    mPresenter.getBkOrderDataRequest(allorder.get(j).getOrderId());//更新收发
                }

                //EventBus.getDefault().post(new SendEvent(2));//通过post发送信息
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
                    //添加数据到集合中
                    id1.add(m);
                    values1.add(orders.get(i).getOrderNum());
                    eaddr1.add(padaddress);
                } else {
                    id2.add(m);
                    values2.add(orders.get(i).getOrderNum());
                    eaddr2.add(padaddress);
                }
            }
            int mid1 = id1.size();//获取数组的个数
            Integer[] id1arr = (Integer[]) id1.toArray(new Integer[mid1]);//把集合转成数组
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
            //Arrays.toString(id1arr)将对应数组的值转换为字符串 并 显示出来,节省了循环输出的步骤
            result = "{" + "\"id1\":" + Arrays.toString(id1arr) + ",\"values1\":" + Arrays.toString(v1arr) + ",\"eaddr1\":" + Arrays.toString(e1arr) + ",\"id2\":" + Arrays.toString(id2arr) + ",\"values2\":" + Arrays.toString(v2arr) + ",\"eaddr2\":" + Arrays.toString(e2arr) + "}";
            LogUtils.logi(result);
            mPresenter.getStartSendALLPaperDataRequest(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getJOSN() {
        try {
            JSONObject one = new JSONObject();
            JSONArray id1 = new JSONArray();
            JSONArray value1 = new JSONArray();
            JSONArray eaddr1 = new JSONArray();

            for (int i = 1; i <= BKDept.size(); i++) {
                if (BKDept.get(sendindex_lamp).getDeptBox() < 73) {
                    int m=BKDept.get(i).getDeptBox();
                    id1.add(m);
                }
            }

            one.put("id1",id1);
            one.put("value1", BKDept.get(sendindex_lamp).getDeptName());
            one.put("eaddr1", padaddress);
//            one.put("id", 100);
//            JSONObject address = new JSONObject();
//            address.put("country", "china");
//            address.put("province", "jiangsu");
//            one.put("address", address);
//            one.put("married", false);
            Log.e("Tag", one.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 传送硬件，开始传送待点亮的信箱数
     */
    private void StartSendLamp(final List<BKAllDeptData.BKDept> mlist) {
        try {
            sendindex_lamp = 0;//索引
            sendcount_lamp = mlist.size();//总个数
            BKDept = mlist;//数据集合
            sendLamp();//第一个开始(一个一个发)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendLamp() {
        try {
            //是否全部发送
            if (sendindex_lamp < sendcount_lamp) {
                mPresenter.getdisplayDataRequest1(BKDept.get(sendindex_lamp).getDeptBox(), padaddress,1);
            } else {//个数更新结束
                EventBus.getDefault().post(new SendEvent(8));//通过post发送信息
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
                EventBus.getDefault().post(new SendEvent(2));//通过post发送信息
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //订阅接受消息，使用onEventMainThread作为订阅函数，那么不论事件是在哪个线程中发布出来的，onEventMainThread都会在UI线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)//MAIN:运行在Android的主线程中。可加入第二个参数priority=100决定优先级
    public void onMainEventBus(SendEvent sendEvent) {
        if (sendEvent.getNum() == 2) {
            j = 0;
            dialog3.dismiss();
            ShowDialog5();
            mPresenter.getTSendDataRequest();//获取今日报刊
        }else if(sendEvent.getNum()==8)
        {
            if (btn == 1) {
                iv_department_one.setBackgroundResource(R.drawable.belong_to_light);
                lightOffOne=true;
            } else if (btn == 2) {
                iv_department_two.setBackgroundResource(R.drawable.belong_to_light);
                lightOffTwo=true;
            }
            if (dialog8!=null) {
                dialog8.dismiss();
                ShowDialog9();
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus1(SendNumEvent sendNumEvent) {
        try {
            if (sendNumEvent.getNum() == 1) {
                newspaper_num.setText(String.valueOf(bknum));//String.valueOf()比起string（强转换），tostring转化成字符时会自动判断是否为空
                thepress_num.setText(String.valueOf(qknum));
                LogUtils.logi("wwwwww========" + String.valueOf(bknum) + "&&&" + String.valueOf(qknum));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送硬件成功(单个发送报纸的时候使用)
    @Override
    public void returngetdisplayData(String result) {
        //硬件返回后更新订单信息
//        mPresenter.getBkOrderDataRequest(0);
        ToastUitl.showShort(result);
        mPresenter.getBkOrderDataRequest(allorder.get(sendindex).getOrderId());//更新收发

    }

    //更新订单后
    @Override
    public void returngetBkOrderData(BkOrderData bkOrderData) {
//        sendindex++;
//        sendPaper();
        if(j==sendcount){
            EventBus.getDefault().post(new SendEvent(2));//通过post发送信息
        }
        LogUtils.logi("iiiiiiiii=======" + sendcount + "jjjjjj" + j);
    }

    @Override
    public void returngetTSendData(BkTSendData bkTSendData) {

        bknum = bkTSendData.getBk();
        qknum = bkTSendData.getQk();
        EventBus.getDefault().post(new SendNumEvent(1));
//        newspaper_num.setText(bknum+"");
//        thepress_num.setText(qknum+"");
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
            ToastUitl.showShort("发送硬件服务器失败");
            dialog3.dismiss();
            ShowDialog5();
//            sendindex++;
//            sendPaper();
        } else if ("2".equals(msg)) {
            dialog3.dismiss();
            ShowDialog5();
//            sendindex++;
//            sendPaper();
            ToastUitl.showShort("更新收发服务器失败");
        } else if ("3".equals(msg)) {
            sendindex_lamp++;
            sendLamp();
            ToastUitl.showShort("信箱点亮失败");
        } else {
            ToastUitl.showShort(msg);
            if (dialog3 != null) {
                dialog3.dismiss();
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_leftbtn:
                popupWindow.dismiss();
                ShowDialog3();
                break;
            case R.id.pop_rightbtn:
                popupWindow.dismiss();
                break;
            case R.id.pop_exit:
                popupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    /**
     * 设置准备发送硬件
     */
    private void setPrepareSend() {
        ToastUitl.showShort(bkrows.get(position).toString() + "&&" + bkrows.get(position).getBkName());
        mPresenter.getBkSendDataRequest(searchType, year, bkrows.get(position).getDomesticPostCode());
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        adapter.getPageBean().setRefresh(false);
        //发起请求
        irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        mPresenter.getBkSearchDataRequest(searchBk, searchCode, searchType, year, pageNumber, pageSize);
    }

    @Override
    public void onRefresh() {
        adapter.getPageBean().setRefresh(true);
        pageNumber = 1;
        count = 0;
        //发起请求
        irc_search.setRefreshing(true);
        irc_search.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
        mPresenter.getBkSearchDataRequest(searchBk, searchCode, searchType, year, pageNumber, pageSize);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

}

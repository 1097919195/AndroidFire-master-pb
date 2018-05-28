package com.jaydenxiao.androidfire.app;

import android.graphics.Typeface;

import com.jaydenxiao.androidfire.BuildConfig;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.commonutils.LogUtils;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {

    public static Typeface typeface;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);

        typeface=Typeface.createFromAsset(getAssets(),"fonts/fzxk.ttf");//使用自定义的字体集
    }
}

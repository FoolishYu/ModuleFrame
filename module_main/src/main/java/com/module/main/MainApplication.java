package com.module.main;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.module.comm.config.RouterConstant;
import com.module.lib.base.ApplicationDelegate;
import com.module.lib.util.LogUtil;

/**
 * @author zhouchunjie
 * @date 2017/11/7
 */

@Route(path = RouterConstant.APPLICATION_MAIN)
public class MainApplication implements ApplicationDelegate {

    @Override
    public void onCreate() {
        LogUtil.logE(this, "onCreate");
    }

    @Override
    public void onTerminate() {
        LogUtil.logE(this, "onTerminate");
    }

    @Override
    public void onLowMemory() {
        LogUtil.logE(this, "onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        LogUtil.logE(this, "onTrimMemory: " + level);
    }

    @Override
    public void init(Context context) {
        LogUtil.logE(this, "init");
    }
}

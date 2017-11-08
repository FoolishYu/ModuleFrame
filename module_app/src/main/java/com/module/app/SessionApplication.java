package com.module.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.comm.config.RouterConstant;
import com.module.lib.base.ApplicationDelegate;
import com.module.lib.base.BaseApplication;

/**
 * @author zhouchunjie
 * @date 2017/11/1
 */

public class SessionApplication extends BaseApplication {

    ApplicationDelegate applicationDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationDelegate = (ApplicationDelegate) ARouter.getInstance()
                .build(RouterConstant.APPLICATION_MAIN).navigation();
        applicationDelegate.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        applicationDelegate.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        applicationDelegate.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        applicationDelegate.onTrimMemory(level);
    }
}
package com.module.lib.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.module.lib.fresco.FrescoUtil;
import com.module.lib.util.LogUtil;
import com.module.lib.util.Utils;

/**
 * @author zhouchunjie
 * @date 2017/10/26
 */

public class BaseApplication extends Application {

    private static BaseApplication application;
    // 是否调试模式
    protected boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // 工具初始化
        Utils.init(this);
        // Log初始化
        LogUtil.debug(isDebug);
        // 路由初始化
        if (isDebug) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        // 图片加载初始化
        FrescoUtil.init(this);
    }

    /**
     * 获取应用实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return application;
    }
}

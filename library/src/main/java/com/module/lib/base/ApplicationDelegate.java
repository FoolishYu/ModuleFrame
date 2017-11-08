package com.module.lib.base;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author zhouchunjie
 * @date 2017/11/7
 */

public interface ApplicationDelegate extends IProvider {

    void onCreate();

    void onTerminate();

    void onLowMemory();

    void onTrimMemory(int level);
}

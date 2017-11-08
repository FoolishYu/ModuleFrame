package com.module.data;

import android.support.annotation.NonNull;

import com.module.data.helper.api.DataHelper;
import com.module.data.helper.api.IImageInfoHelper;
import com.module.data.helper.impl.ImageInfoHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouchunjie
 * @date 2017/10/26
 */

public class DataManager {

    private static DataManager dataManager;
    private Map<Object, DataHelper> dataHelperMap;

    private DataManager() {
        dataHelperMap = new HashMap<>();
    }

    /**
     * 获取单例模式
     *
     * @return
     */
    public static DataManager getInstance() {
        if (dataManager == null) {
            synchronized (DataManager.class) {
                if (dataManager == null) {
                    dataManager = new DataManager();
                }
            }
        }
        return dataManager;
    }

    /**
     * 获取数据模块
     *
     * @param cls
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends DataHelper> T getDataHelper(@NonNull Class<T> cls, @NonNull Object tag) {
        // 取缓存
        T dataHelper = (T) dataHelperMap.get(tag);
        if (dataHelper == null) {
            // 新建实例
            if (cls.equals(IImageInfoHelper.class)) {
                dataHelper = (T) new ImageInfoHelper();
            } else {
                // TODO: 2017/11/6 Others

            }
            // 存缓存
            dataHelperMap.put(tag, dataHelper);
        }
        return dataHelper;
    }

    /**
     * 结束对应模块
     *
     * @param tag
     */
    public void cancelTag(@NonNull Object... tag) {
        for (Object obj : tag) {
            DataHelper dataHelper = dataHelperMap.get(obj);
            if (dataHelper != null) {
                dataHelper.cancel();
                dataHelperMap.remove(obj);
            }
        }
    }

    /**
     * 结束所有模块
     */
    public void cancelAll() {
        for (Object tag : dataHelperMap.keySet()) {
            DataHelper dataHelper = dataHelperMap.get(tag);
            if (dataHelper != null) {
                dataHelper.cancel();
            }
        }
        dataHelperMap.clear();
    }
}

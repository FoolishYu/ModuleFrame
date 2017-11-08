package com.module.lib.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类
 *
 * @author zhouchunjie
 * @date 2017/9/14
 */

public class NetworkUtil {

    /**
     * 判断网络是否连接
     *
     * @param context 上下文
     * @return  {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}

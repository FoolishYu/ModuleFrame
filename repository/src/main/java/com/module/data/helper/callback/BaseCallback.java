package com.module.data.helper.callback;

/**
 * @author zhouchunjie
 * @date 2017/10/31
 */

public interface BaseCallback {

    void onError(boolean isCancel, int errorCode, Exception e);
}

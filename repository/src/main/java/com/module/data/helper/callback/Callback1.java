package com.module.data.helper.callback;

import android.support.annotation.NonNull;

/**
 * @author zhouchunjie
 * @date 2017/11/7
 */

public interface Callback1<X> extends BaseCallback {

    void onSuccess(@NonNull X value);
}

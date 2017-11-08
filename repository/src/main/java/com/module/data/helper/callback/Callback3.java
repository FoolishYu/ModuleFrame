package com.module.data.helper.callback;

import android.support.annotation.NonNull;

/**
 * @author zhouchunjie
 * @date 2017/11/7
 */

public interface Callback3<X, Y, Z> extends BaseCallback {

    void onSuccess(@NonNull X value1, @NonNull Y value2, @NonNull Z value3);
}

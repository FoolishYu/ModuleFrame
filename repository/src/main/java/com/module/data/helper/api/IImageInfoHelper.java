package com.module.data.helper.api;

import android.support.annotation.NonNull;

import com.module.data.model.bean.ImageInfo;
import com.module.data.helper.callback.Callback1;
import com.module.data.helper.callback.Callback2;

import java.util.List;

/**
 * @author zhouchunjie
 * @date 2017/10/26
 */

public interface IImageInfoHelper extends DataHelper {

    void getList(@NonNull Callback1<List<ImageInfo>> callback1);

    void isHave(@NonNull Callback2<Boolean, List<ImageInfo>> callback2);
}

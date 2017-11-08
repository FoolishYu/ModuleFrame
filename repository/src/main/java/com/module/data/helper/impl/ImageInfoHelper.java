package com.module.data.helper.impl;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.module.data.model.bean.ImageInfo;
import com.module.data.helper.callback.Callback1;
import com.module.data.helper.callback.Callback2;
import com.module.data.helper.api.IImageInfoHelper;
import com.module.lib.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouchunjie
 * @date 2017/10/26
 */

public class ImageInfoHelper implements IImageInfoHelper {

    @Override
    public void cancel() {
    }

    @Override
    public void getList(@NonNull Callback1<List<ImageInfo>> callback1) {
        List<ImageInfo> result = new ArrayList<>();

        ContentResolver contentResolver = BaseApplication.getInstance().getContentResolver();
        // 系统图库扫描
        Cursor cursor = contentResolver.query(
                MediaStore.Files.getContentUri("external"),
                null, "media_type = ? and mime_type like ?",
                new String[]{"1", "image/%"},
                null);
        while (cursor != null && cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            String data = cursor.getString(cursor.getColumnIndex("_data"));
            long dateModified = cursor.getLong(cursor.getColumnIndex("date_modified")) * 1000;

            if (!TextUtils.isEmpty(data)) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setId(id);
                imageInfo.setPath(data);
                imageInfo.setDateModified(dateModified);
                result.add(imageInfo);
            }
        }
        if (cursor != null) {
            cursor.close();
        }

        callback1.onSuccess(result);
    }

    @Override
    public void isHave(@NonNull Callback2<Boolean, List<ImageInfo>> callback2) {
        List<ImageInfo> result = new ArrayList<>();
        callback2.onSuccess(true, result);
    }
}

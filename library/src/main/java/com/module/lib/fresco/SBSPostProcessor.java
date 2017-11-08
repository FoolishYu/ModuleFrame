package com.module.lib.fresco;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.request.BasePostprocessor;

import java.io.File;

/**
 * SBS后处理
 *
 * @author zhouchunjie
 * @date 2017/11/1
 */

public class SBSPostProcessor extends BasePostprocessor {

    private boolean isFull;
    private String path;

    public SBSPostProcessor(String path, boolean isFull) {
        this.isFull = isFull;
        this.path = path;
    }

    @Override
    public String getName() {
        return "LR";
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return new SimpleCacheKey(path + File.separator + String.valueOf(isFull));
    }

    @Override
    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        CloseableReference<Bitmap> bitmapRef;
        if (isFull) {
            bitmapRef = bitmapFactory.createBitmap(
                    sourceBitmap.getWidth() / 2,
                    sourceBitmap.getHeight());
        } else {
            bitmapRef = bitmapFactory.createBitmap(
                    sourceBitmap.getWidth(),
                    sourceBitmap.getHeight());
        }

        try {
            Bitmap destBitmap = bitmapRef.get();

            Canvas canvas2d = new Canvas(destBitmap);
            canvas2d.drawBitmap(sourceBitmap,
                    new Rect(0, 0, sourceBitmap.getWidth() / 2, sourceBitmap.getHeight()),
                    new Rect(0, 0, destBitmap.getWidth(), destBitmap.getHeight()), null);
            return CloseableReference.cloneOrNull(bitmapRef);
        } finally {
            CloseableReference.closeSafely(bitmapRef);
        }
    }
}

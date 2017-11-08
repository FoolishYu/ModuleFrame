package com.module.lib.fresco;

import android.net.Uri;
import android.util.Log;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.File;

/**
 * Fresco自定义缓存key
 *
 * @author zhouchunjie
 * @date 2017/4/19
 */
public class DefaultCacheKeyFactory implements CacheKeyFactory {

    private static DefaultCacheKeyFactory sInstance = null;

    protected DefaultCacheKeyFactory() {
    }

    public static synchronized DefaultCacheKeyFactory getInstance() {
        if (sInstance == null) {
            sInstance = new DefaultCacheKeyFactory();
        }
        return sInstance;
    }

    @Override
    public CacheKey getBitmapCacheKey(ImageRequest request, Object callerContext) {
        return new BitmapMemoryCacheKey(
                getCacheKeySourceUri(request.getSourceUri()),
                request.getResizeOptions(),
                request.getRotationOptions(),
                request.getImageDecodeOptions(),
                null,
                null,
                callerContext);
    }

    @Override
    public CacheKey getPostprocessedBitmapCacheKey(ImageRequest request, Object callerContext) {
        final Postprocessor postprocessor = request.getPostprocessor();
        final CacheKey postprocessorCacheKey;
        final String postprocessorName;
        if (postprocessor != null) {
            postprocessorCacheKey = postprocessor.getPostprocessorCacheKey();
            postprocessorName = postprocessor.getClass().getName();
        } else {
            postprocessorCacheKey = null;
            postprocessorName = null;
        }
        return new BitmapMemoryCacheKey(
                getCacheKeySourceUri(request.getSourceUri()),
                request.getResizeOptions(),
                request.getRotationOptions(),
                request.getImageDecodeOptions(),
                postprocessorCacheKey,
                postprocessorName,
                callerContext);
    }

    @Override
    public CacheKey getEncodedCacheKey(ImageRequest request, Object callerContext) {
        return getEncodedCacheKey(request, request.getSourceUri(), callerContext);
    }

    @Override
    public CacheKey getEncodedCacheKey(
            ImageRequest request,
            Uri sourceUri,
            Object callerContext) {
        return new SimpleCacheKey(getCacheKeySourceUri(sourceUri));
    }

    /**
     * @return a {@link Uri} that unambiguously indicates the source of the image.
     */
    private String getCacheKeySourceUri(Uri sourceUri) {
        // 重写自定义返回
        String url = sourceUri.toString();
        if (url.toLowerCase().startsWith("file")) {
            File file = new File(sourceUri.getPath());
            Log.d("DefaultCacheKeyFactory", "sourceUri: " + url + ":" + file.length());
            return url + ":" + file.length();
        }
        return url;
    }
}

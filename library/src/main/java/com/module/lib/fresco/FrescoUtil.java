package com.module.lib.fresco;

import android.app.Application;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.module.lib.base.BaseApplication;

import java.io.File;

/**
 * Fresco加载工具类
 *
 * @author zhouchunjie
 * @date 2017/4/19
 */

public class FrescoUtil {

    private FrescoUtil() {
    }

    /**
     * 初始化
     */
    public static void init(Application application) {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(application)
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .setCacheKeyFactory(DefaultCacheKeyFactory.getInstance())
                .build();
        Fresco.initialize(application, config);
    }

    /**
     * 初始化
     */
    public static void init(Application application, DiskCacheConfig diskCacheConfig) {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(application)
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setCacheKeyFactory(DefaultCacheKeyFactory.getInstance())
                .build();
        Fresco.initialize(application, config);
    }

    /**
     * 加载网络图片
     *
     * @param url 图片链接
     * @return
     */
    public static FrescoBuilder load(@NonNull String url) {
        return load(Uri.parse(url));
    }

    /**
     * 加载本地文件
     *
     * @param file 本地文件
     * @return
     */
    public static FrescoBuilder load(@NonNull File file) {
        return load(Uri.fromFile(file));
    }

    /**
     * 加载资源文件
     *
     * @param resId 资源ID
     * @return
     */
    public static FrescoBuilder load(@IdRes int resId) {
        Uri uri = Uri.parse("res://" + BaseApplication.getInstance().getPackageName() + "/" + resId);
        return load(uri);
    }

    /**
     * 加载URI
     *
     * @param uri
     * @return
     */
    public static FrescoBuilder load(@NonNull Uri uri) {
        return new FrescoBuilder(uri);
    }

    public static class FrescoBuilder {

        private Uri uri;
        private Postprocessor postprocessor;
        private ResizeOptions resizeOptions;
        private Priority priority;

        public FrescoBuilder(Uri uri) {
            this.uri = uri;
        }

        public FrescoBuilder resize(int width, int height) {
            resizeOptions = new ResizeOptions(width, height);
            return this;
        }

        public FrescoBuilder setPostprocessor(Postprocessor postprocessor) {
            this.postprocessor = postprocessor;
            return this;
        }

        public FrescoBuilder setPriority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public void to(SimpleDraweeView simpleDraweeView) {
            ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);

            if (resizeOptions != null) {
                imageRequestBuilder.setResizeOptions(resizeOptions);
            }
            if (postprocessor != null) {
                imageRequestBuilder.setPostprocessor(postprocessor);
            }
            if (priority != null) {
                imageRequestBuilder.setRequestPriority(priority);
            }

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(imageRequestBuilder.build())
                    .setOldController(simpleDraweeView.getController())
                    .build();
            simpleDraweeView.setController(controller);
        }
    }
}

package com.module.image;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.module.comm.config.RouterConstant;
import com.module.data.DataManager;
import com.module.data.model.bean.ImageInfo;
import com.module.data.helper.callback.Callback1;
import com.module.data.helper.callback.Callback2;
import com.module.data.helper.api.IImageInfoHelper;
import com.module.lib.fresco.FrescoUtil;
import com.module.lib.fresco.SBSPostProcessor;
import com.module.lib.util.LogUtil;
import com.senierr.seadapter.internal.RVHolder;
import com.senierr.seadapter.internal.SeAdapter;
import com.senierr.seadapter.internal.ViewHolderWrapper;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterConstant.ACTIVITY_IMAGE_MAIN)
public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.rv_main)
    RecyclerView rvMain;

    private SeAdapter seAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_image_activity_main);
        ButterKnife.bind(this);

        rvMain.setLayoutManager(new GridLayoutManager(this, 4));
        rvMain.setHasFixedSize(true);
        rvMain.addItemDecoration(new BaseItemDecoration(this, R.dimen.module_comm_dimen_2, R.color.module_image_colorAccent));

        seAdapter = new SeAdapter();
        ViewHolderWrapper<ImageInfo> viewHolderWrapper = new ViewHolderWrapper<ImageInfo>() {
            @Override @NonNull
            public RVHolder onCreateViewHolder(@NonNull ViewGroup viewGroup) {
                return RVHolder.create(viewGroup, R.layout.module_image_item_image);
            }

            @Override
            public void onBindViewHolder(@NonNull RVHolder rvHolder, @NonNull ImageInfo imageInfo) {
                SimpleDraweeView imageView = rvHolder.getView(R.id.iv_image);

                FrescoUtil.load(new File(imageInfo.getPath()))
                        .resize(200, 200)
                        .setPostprocessor(new SBSPostProcessor(imageInfo.getPath(), true))
                        .to(imageView);
            }
        };
        viewHolderWrapper.setOnItemClickListener(new ViewHolderWrapper.OnItemClickListener() {
            @Override
            public void onClick(RVHolder viewHolder, int position) {
                ARouter.getInstance().build(RouterConstant.ACTIVITY_MAIN_SPLASH).navigation();
            }
        });

        seAdapter.bind(ImageInfo.class, viewHolderWrapper);

        IImageInfoHelper imageInfoHelper = DataManager.getInstance()
                .getDataHelper(IImageInfoHelper.class, this);
        imageInfoHelper.getList(new Callback1<List<ImageInfo>>() {
            @Override
            public void onSuccess(@NonNull List<ImageInfo> value) {
                for (ImageInfo imageInfo : value) {
                    seAdapter.getDataList().add(imageInfo);
                }
                seAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(boolean isCancel, int errorCode, Exception e) {

            }
        });

        imageInfoHelper.isHave(new Callback2<Boolean, List<ImageInfo>>() {
            @Override
            public void onSuccess(@NonNull Boolean value1, @NonNull List<ImageInfo> value2) {
                LogUtil.logE(this, value1 + ", " + value2.size());
            }

            @Override
            public void onError(boolean isCancel, int errorCode, Exception e) {

            }
        });

        rvMain.setAdapter(seAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataManager.getInstance().cancelTag(this, "aa");
    }
}

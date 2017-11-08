package com.module.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.module.comm.config.RouterConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConstant.ACTIVITY_MAIN_SPLASH)
public class SplashActivity extends AppCompatActivity {

    @BindView(R2.id.btn_main)
    Button btnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_main_activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.btn_main)
    public void onViewClicked() {
        ARouter.getInstance().build(RouterConstant.ACTIVITY_IMAGE_MAIN).navigation();
    }
}

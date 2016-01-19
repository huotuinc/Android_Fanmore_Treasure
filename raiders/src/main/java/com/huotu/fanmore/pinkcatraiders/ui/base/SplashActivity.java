package com.huotu.fanmore.pinkcatraiders.ui.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.ui.guide.GuideActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.MsgPopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * 启动界面
 */
public class SplashActivity extends BaseActivity implements Handler.Callback {

    @Bind(R.id.splashL)
    RelativeLayout splashL;
    private
    BaseApplication application;
    private boolean isConnection = false;// 假定无网络连接
    private
    MsgPopWindow popWindow;
    public WindowManager wManager;
    public Handler mHandler;
    public
    ProgressPopupWindow progress;
    public
    NoticePopWindow noticePop;
    public Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_splash);
        ButterKnife.bind(this);
        //设置沉浸模式
        ButterKnife.bind(this);
        application = ( BaseApplication ) SplashActivity.this.getApplication ( );
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        setImmerseLayout(splashL);
        resources = this.getResources();
        progress = new ProgressPopupWindow ( SplashActivity.this, SplashActivity.this, wManager );
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView()
    {
        //加载背景图片
        Drawable drawable = resources.getDrawable(R.mipmap.splash_bg);
        SystemTools.loadBackground(splashL, drawable);
        AlphaAnimation anima = new AlphaAnimation ( 0.0f, 1.0f );
        anima.setDuration ( Contant.ANIMATION_COUNT );// 设置动画显示时间
        splashL.setAnimation(anima);
        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                progress.showProgress ( "正在初始化数据" );
                progress.showAtLocation(splashL,
                        Gravity.CENTER, 0, 0
                );
                //检测网络
                isConnection = application.checkNet ( SplashActivity.this );
                if ( ! isConnection ) {
                    //无网络日志
                    popWindow = new MsgPopWindow ( SplashActivity.this, new
                            SettingNetwork ( ), new CancelNetwork ( ), "网络连接错误",
                            "请打开你的网络连接！", false );
                    popWindow.showAtLocation ( splashL, Gravity.CENTER, 0,0 );
                    popWindow.setOnDismissListener ( new PoponDismissListener(SplashActivity.this) );
                }
                else
                {
                    //定位
                    //初始化接口
                    //跳转到新特新界面
                    ActivityUtils.getInstance().skipActivity(SplashActivity.this, HomeActivity.class);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(SplashActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //设置网络点击事件
    private class SettingNetwork implements View.OnClickListener
    {

        @Override
        public
        void onClick ( View v ) {

            Intent intent = null;
            // 判断手机系统的版本 即API大于10 就是3.0或以上版本
            if (android.os.Build.VERSION.SDK_INT > 10)
            {
                intent = new Intent(
                        android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            } else
            {
                intent = new Intent();
                ComponentName component = new ComponentName(
                        "com.android.settings",
                        "com.android.settings.WirelessSettings");
                intent.setComponent(component);
                intent.setAction("android.intent.action.VIEW");
            }
            SplashActivity.this
                    .startActivity(intent);

        }
    }

    //取消设置网络
    private class CancelNetwork implements View.OnClickListener
    {

        @Override
        public
        void onClick ( View v ) {

            popWindow.dismiss();
            // 未设置网络，关闭应用
            closeSelf(SplashActivity.this);
        }
    }
}

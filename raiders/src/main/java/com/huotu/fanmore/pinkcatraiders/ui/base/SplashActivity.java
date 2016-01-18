package com.huotu.fanmore.pinkcatraiders.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
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
        progress = new ProgressPopupWindow ( SplashActivity.this, SplashActivity.this, wManager );
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView()
    {
        AlphaAnimation anima = new AlphaAnimation ( 0.0f, 1.0f );
        anima.setDuration ( Contant.ANIMATION_COUNT );// 设置动画显示时间
        splashL.setAnimation(anima);
        anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

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
    protected void onResume() {
        super.onResume();
    }
}

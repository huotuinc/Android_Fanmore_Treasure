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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.CarouselModel;
import com.huotu.fanmore.pinkcatraiders.model.CateGoryOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.InitOutputsModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.SlideListModel;
import com.huotu.fanmore.pinkcatraiders.model.SlideListOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.guide.GuideActivity;
import com.huotu.fanmore.pinkcatraiders.ui.login.LoginActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.MsgPopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

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
                    String url = Contant.REQUEST_URL + Contant.INIT;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), SplashActivity.this);
                    Map<String, Object> maps = new HashMap<String, Object>();
                    String suffix = params.obtainGetParam(maps);
                    url = url + suffix;
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doVolleyGet (
                            url, new Response.Listener< JSONObject > ( ) {

                                @Override
                                public
                                void onResponse ( JSONObject response ) {

                                    if ( SplashActivity.this.isFinishing ( ) ) {
                                        return;
                                    }
                                    JSONUtil< InitOutputsModel > jsonUtil    = new JSONUtil<InitOutputsModel > ( );
                                    InitOutputsModel             initOutputs = new
                                            InitOutputsModel ( );
                                    initOutputs = jsonUtil.toBean ( response.toString ( ),
                                            initOutputs );
                                    if ( null != initOutputs && null != initOutputs.getResultData
                                            ( ) && ( 1 == initOutputs.getResultCode ( ) ) ) {
                                        //加载全局变量数据
                                        if ( null != initOutputs.getResultData ( ).getGlobal ( ) ) {
                                            application.loadGlobalData ( initOutputs
                                                    .getResultData (
                                                    )
                                                    .getGlobal ( ) );
                                        }
                                        if ( null != initOutputs.getResultData ( ).getUpdate ( ) ) {
                                            //加载更新信息
                                            application.loadUpdate ( initOutputs.getResultData (
                                            )
                                                    .getUpdate ( ) );
                                        }
                                        if ( null != initOutputs.getResultData ( ).getUser ( ) ) {
                                            //加载用户信息
                                            application.writeUserInfo ( initOutputs.getResultData
                                                    ( ).getUser ( ) );
                                        }
                                        //记载首页轮播图片数据
                                        String url = Contant.REQUEST_URL + Contant.GET_SLIDE_LIST;
                                        AuthParamUtils params = new AuthParamUtils ( application,
                                                System.currentTimeMillis ( ), SplashActivity.this );
                                        Map< String, Object > maps = new HashMap< String, Object
                                                > ( );
                                        String suffix = params.obtainGetParam ( maps );
                                        url = url + suffix;
                                        HttpUtils httpUtils = new HttpUtils ( );
                                        httpUtils.doVolleyGet (
                                                url, new Response.Listener< JSONObject > ( ) {

                                                    @Override
                                                    public
                                                    void onResponse ( JSONObject response ) {

                                                        //转换轮播数据
                                                        if ( SplashActivity.this.isFinishing ( ) ) {
                                                            return;
                                                        }
                                                        JSONUtil< SlideListOutputModel > jsonUtil    = new JSONUtil<
                                                                SlideListOutputModel > ( );
                                                        SlideListOutputModel             slideListOutput = new
                                                                SlideListOutputModel ( );
                                                        slideListOutput = jsonUtil.toBean ( response.toString ( ),
                                                                slideListOutput );
                                                        if ( null != slideListOutput && null != slideListOutput.getResultData
                                                                ( ) && ( 1 == slideListOutput.getResultCode ( ) ) ) {

                                                            //轮播数据写入数据库
                                                            List<SlideListModel > slides = slideListOutput.getResultData ().getList ();
                                                            if(null!=slides && !slides.isEmpty ())
                                                            {
                                                                //删除全部
                                                                CarouselModel.deleteAll (
                                                                        CarouselModel.class );
                                                                Iterator<SlideListModel> iterator = slides.iterator ();
                                                                while ( iterator.hasNext () )
                                                                {
                                                                    SlideListModel slide = iterator.next ();
                                                                    CarouselModel carousel = new CarouselModel (  );
                                                                    carousel.setPid ( slide.getPid () );
                                                                    carousel.setGoodsId ( slide.getGoodsId ( ) );
                                                                    carousel.setLink ( slide.getLink ( ) );
                                                                    carousel.setPictureUrl (
                                                                            slide.getPictureUrl ( ) );
                                                                    CarouselModel.save ( carousel );
                                                                }

                                                                if ( application.isFirst ( ) ) {
                                                                    ActivityUtils.getInstance ( ).skipActivity (
                                                                            SplashActivity.this, GuideActivity.class );
                                                                    //写入初始化数据
                                                                    application.writeInitInfo ( "inited" );
                                                                }
                                                                else {
                                                                    //判断是否登录
                                                                    if ( application.isLogin ( ) ) {
                                                                        ActivityUtils.getInstance ( ).skipActivity (
                                                                                SplashActivity.this, HomeActivity.class );
                                                                    }
                                                                    else {
                                                                        ActivityUtils.getInstance ( )
                                                                                .skipActivity (
                                                                                        SplashActivity
                                                                                                .this,
                                                                                        LoginActivity.class
                                                                                );
                                                                    }
                                                                }
                                                            }
                                                            else
                                                            {
                                                                //判断数据库中是否有旧数据
                                                                Iterator<CarouselModel> iterator = CarouselModel.findAll (
                                                                        CarouselModel.class );
                                                                if(iterator.hasNext ( ))
                                                                {
                                                                    if ( application.isFirst ( ) ) {
                                                                        ActivityUtils.getInstance ( ).skipActivity (
                                                                                SplashActivity.this, GuideActivity.class );
                                                                        //写入初始化数据
                                                                        application.writeInitInfo ( "inited" );
                                                                    }
                                                                    else {
                                                                        //判断是否登录
                                                                        if ( application.isLogin ( ) ) {
                                                                            ActivityUtils.getInstance ( ).skipActivity (
                                                                                    SplashActivity.this, HomeActivity.class );
                                                                        }
                                                                        else {
                                                                            ActivityUtils.getInstance ( )
                                                                                    .skipActivity (
                                                                                            SplashActivity
                                                                                                    .this,
                                                                                            LoginActivity.class
                                                                                    );
                                                                        }
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    //使用默认图片
                                                                    CarouselModel carousel1 = new CarouselModel (  );
                                                                    carousel1.setGoodsId ( 0 );
                                                                    carousel1.setPictureUrl (""

                                                                    );
                                                                    carousel1.setPid ( 0 );
                                                                    CarouselModel.save ( carousel1 );
                                                                    CarouselModel carousel2 = new CarouselModel (  );
                                                                    carousel2.setGoodsId ( 1 );
                                                                    carousel2.setPictureUrl (
                                                                            ""
                                                                    );
                                                                    carousel2.setPid ( 1 );
                                                                    CarouselModel.save ( carousel2 );

                                                                    if ( application.isFirst ( ) ) {
                                                                        ActivityUtils.getInstance ( ).skipActivity (
                                                                                SplashActivity.this, GuideActivity.class );
                                                                        //写入初始化数据
                                                                        application.writeInitInfo ( "inited" );
                                                                    }
                                                                    else {
                                                                        //判断是否登录
                                                                        if ( application.isLogin ( ) ) {
                                                                            ActivityUtils.getInstance ( ).skipActivity (
                                                                                    SplashActivity.this, HomeActivity.class );
                                                                        }
                                                                        else {
                                                                            ActivityUtils.getInstance ( )
                                                                                    .skipActivity (
                                                                                            SplashActivity
                                                                                                    .this,
                                                                                            LoginActivity.class
                                                                                    );
                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        }
                                                        else
                                                        {
                                                            //判断数据库中是否有旧数据
                                                            Iterator<CarouselModel> iterator = CarouselModel.findAll (
                                                                    CarouselModel.class );
                                                            if(iterator.hasNext ( ))
                                                            {
                                                                if ( application.isFirst ( ) ) {
                                                                    ActivityUtils.getInstance ( ).skipActivity (
                                                                            SplashActivity.this, GuideActivity.class );
                                                                    //写入初始化数据
                                                                    application.writeInitInfo ( "inited" );
                                                                }
                                                                else {
                                                                    //判断是否登录
                                                                    if ( application.isLogin ( ) ) {
                                                                        ActivityUtils.getInstance ( ).skipActivity (
                                                                                SplashActivity.this, HomeActivity.class );
                                                                    }
                                                                    else {
                                                                        ActivityUtils.getInstance ( )
                                                                                .skipActivity (
                                                                                        SplashActivity
                                                                                                .this,
                                                                                        LoginActivity.class
                                                                                );
                                                                    }
                                                                }
                                                            }
                                                            else
                                                            {
                                                                ToastUtils.showLongToast ( SplashActivity.this, "初始化数据失败" );
                                                            }
                                                        }
                                                    }
                                                }, new Response.ErrorListener ( ) {

                                                    @Override
                                                    public
                                                    void onErrorResponse ( VolleyError error ) {
                                                        ToastUtils.showLongToast ( SplashActivity.this, "初始化数据失败" );
                                                    }
                                                }
                                        );
                                    }
                                    else {
                                        //异常处理，自动切换成无数据
                                        ToastUtils.showLongToast ( SplashActivity.this, "初始化数据失败" );
                                    }
                                }
                            }, new Response.ErrorListener ( ) {

                                @Override
                                public
                                void onErrorResponse ( VolleyError error ) {
                                    //初始化失败
                                    //异常处理，自动切换成无数据
                                    ToastUtils.showLongToast ( SplashActivity.this, "初始化数据失败" );
                                }
                            }
                    );

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
        VolleyUtil.cancelAllRequest();
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
        if(  JPushInterface.isPushStopped(SplashActivity.this))
        {
            JPushInterface.resumePush(SplashActivity.this);
        }
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

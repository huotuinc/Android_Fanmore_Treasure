package com.huotu.fanmore.pinkcatraiders.ui.guide;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.ViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.login.LoginActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 引导界面
 */
public
class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, Handler.Callback {


    @Bind(R.id.vp_activity)
    ViewPager        mVPActivity;
    @Bind(R.id.guideL)
    LinearLayout guideL;
    private ViewPagerAdapter vpAdapter;
    private List< View >     views;
    private int lastValue = - 1;
    private
    Resources resources;
    public Handler mHandler;
    public BaseApplication application;

    //引导图片资源
    private String[] pics;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(GuideActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected
    void onCreate ( Bundle arg0 ) {
        // TODO Auto-generated method stub
        super.onCreate ( arg0 );
        setContentView(R.layout.guide_ui);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources ();
        mHandler = new Handler ( this );
        //设置沉浸模式
        setImmerseLayout(guideL);
        views = new ArrayList< View > ( );
        initImage ( );

        //初始化Adapter
        vpAdapter = new ViewPagerAdapter ( views );
        mVPActivity.setAdapter ( vpAdapter );
        //绑定回调
        mVPActivity.setOnPageChangeListener ( this );
    }

    private
    void initImage ( ) {
        try {
            pics = this.getResources ( ).getAssets ( ).list ( "guide" );
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                                              LinearLayout.LayoutParams.MATCH_PARENT);
            pics = resources.getStringArray ( R.array.guide_icon );

            //初始化引导图片列表
            for(int i=0; i<pics.length; i++) {
                RelativeLayout iv = ( RelativeLayout ) LayoutInflater.from(GuideActivity.this).inflate ( R.layout.guid_item, null );
                TextView skipText = (TextView) iv.findViewById(R.id.skipText);
                iv.setLayoutParams ( mParams );
                int iconId = resources.getIdentifier ( pics[i], "mipmap", Contant.SYS_PACKAGE);
                Drawable menuIconDraw = resources.getDrawable ( iconId );
                SystemTools.loadBackground(iv, menuIconDraw);
                skipText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //延时2秒后跳入新界面
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                //判断是否登录
                                if (application.isLogin()) {
                                    ActivityUtils.getInstance().skipActivity(GuideActivity.this, HomeActivity.class);

                                } else {
                                    ActivityUtils.getInstance()
                                            .skipActivity(
                                                    GuideActivity
                                                            .this,
                                                    LoginActivity.class);
                                }
                            }
                        }, 1000);
                    }
                });
                views.add(iv);
            }
        } catch (IOException e) {

        }
    }

    /**
     *设置当前的引导页
     */
    private void setCurView(int position)
    {
        if (position < 0 || position >= pics.length) {
            return;
        }

        mVPActivity.setCurrentItem(position);
    }


    @Override
    public
    void onClick ( View v ) {
        int position = (Integer)v.getTag();
        setCurView ( position );
    }

    @Override
    public
    void onPageScrolled ( int arg0, float v, int i1 ) {
        lastValue = arg0;
    }

    @Override
    public
    void onPageSelected ( int arg0 ) {

    }

    @Override
    public
    void onPageScrollStateChanged ( int arg0 ) {

    }

    @Override
    public
    boolean handleMessage ( Message msg ) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
    }
}

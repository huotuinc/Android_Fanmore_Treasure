package com.huotu.fanmore.pinkcatraiders.ui.raiders;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareOrderActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.textright)
    TextView textright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_share_order);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        initTitle();
    }
    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("晒单");
    }
    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(ShareOrderActivity.this);
    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

package com.huotu.fanmore.pinkcatraiders.ui.product;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品详情界面
 */
public class ProductDetailActivity extends BaseActivity implements Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    public
    AssetManager am;
    public ProgressPopupWindow progress;
    public
    NoticePopWindow noticePop;

    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_product_detail);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        //设置沉浸模式
        //setImmerseLayout(this.findViewById(R.id.titleLayoutL));
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
        titleText.setText("奖品详情");

    }

    @Override
    protected void onDestroy() {
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
            this.closeSelf(ProductDetailActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}

package com.huotu.fanmore.pinkcatraiders.ui.assistant;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.MoneyAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 充值界面
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public Handler mHandler;
    public WindowManager wManager;
    public
    AssetManager am;
    public Resources res;
    public BaseApplication application;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;

    @Bind(R.id.moneyGrid)
    MyGridView moneyGrid;
    @Bind(R.id.wxPayL)
    RelativeLayout wxPayL;
    @Bind(R.id.moneyMethodIcon1)
    ImageView moneyMethodIcon1;
    @Bind(R.id.aliPayL)
    RelativeLayout aliPayL;
    @Bind(R.id.moneyMethodIcon2)
    ImageView moneyMethodIcon2;

    public MoneyAdapter adapter;


    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        am = this.getAssets();
        res = this.getResources();
        application = (BaseApplication) this.getApplication ();
        wManager = this.getWindowManager();
        initTitle();
        initView();
    }

    private void initView()
    {
        //重构充值面额
        List<String> moneys = new ArrayList<String>();
        moneys.add("20");
        moneys.add("50");
        moneys.add("100");
        moneys.add("200");
        moneys.add("500");
        moneys.add("其他金额");
        adapter = new MoneyAdapter(moneys, RechargeActivity.this, RechargeActivity.this);
        moneyGrid.setAdapter(adapter);
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = res.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = res.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("充值");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(RechargeActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}

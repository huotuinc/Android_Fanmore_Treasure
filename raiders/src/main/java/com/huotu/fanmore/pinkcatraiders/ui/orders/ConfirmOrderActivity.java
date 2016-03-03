package com.huotu.fanmore.pinkcatraiders.ui.orders;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppBalanceModel;
import com.huotu.fanmore.pinkcatraiders.model.OrderDetailOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.OrderModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 确认订单
 */
public
class ConfirmOrderActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public Handler mHandler;

    public WindowManager wManager;

    public
    AssetManager am;

    public Resources resources;

    public BaseApplication application;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;

    @Bind ( R.id.confirmOrderRefresh )
    PullToRefreshScrollView confirmOrderRefresh;

    @Bind ( R.id.orderAccountL )
    RelativeLayout          orderAccountL;

    @Bind ( R.id.receiverName )
    TextView                receiverName;

    @Bind ( R.id.receiverPhone )
    TextView                receiverPhone;

    @Bind ( R.id.receiverAddress )
    TextView                receiverAddress;

    @Bind ( R.id.products )
    LinearLayout            products;
    @Bind ( R.id.redPackageShow )
    TextView                redPackageShow;
    @Bind ( R.id.totalMoney )
    TextView                totalMoney;
    @Bind ( R.id.money )
    TextView                money;
    @Bind ( R.id.funOpBtn )
    TextView                funOpBtn;

    public Bundle bundle;
    public AppBalanceModel balance;



    @Override
    public
    boolean handleMessage ( Message msg ) {

        return false;
    }

    @Override
    public
    void onClick ( View v ) {

    }

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView(R.layout.confirm_order);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        am = this.getAssets ( );
        resources = this.getResources();
        application = ( BaseApplication ) this.getApplication ( );
        wManager = this.getWindowManager ( );
        bundle = this.getIntent().getExtras();
        balance = (AppBalanceModel) bundle.getSerializable("balance");
        initTitle ( );
        initScroll();
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf ( ConfirmOrderActivity.this );
    }

    private void initScroll()
    {
        initData ( );
        confirmOrderRefresh.setOnRefreshListener (
                new PullToRefreshBase.OnRefreshListener< ScrollView > ( ) {

                    @Override
                    public
                    void onRefresh ( PullToRefreshBase< ScrollView > pullToRefreshBase ) {

                        initData ( );
                    }
                }
                                                 );
    }

    private void initData()
    {
    }

    @OnClick(R.id.funOpBtn)
    void confirmOrder()
    {

        ActivityUtils.getInstance ().showActivity ( ConfirmOrderActivity.this, PayOrderActivity.class );
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("确认订单");
    }

    @Override
    protected
    void onDestroy ( ) {

        super.onDestroy ( );
        VolleyUtil.cancelAllRequest ( );
        ButterKnife.unbind ( this );
    }

    @Override
    public
    boolean onKeyDown ( int keyCode, KeyEvent event ) {

        if (keyCode == KeyEvent.KEYCODE_BACK
            && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(ConfirmOrderActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}

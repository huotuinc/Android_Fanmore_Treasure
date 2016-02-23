package com.huotu.fanmore.pinkcatraiders.ui.assistant;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加收货地址
 */
public
class AddAddressActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler         mHandler;
    public
    WindowManager  wManager;
    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;
    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;
    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;
    @Bind ( R.id.titleRightImage )
    ImageView       titleRightImage;

    @Bind ( R.id.receiverName )
    EditText receiverName;
    @Bind ( R.id.receiverNameBtn )
    ImageView receiverNameBtn;
    @Bind ( R.id.receiverPhone )
    EditText receiverPhone;
    @Bind ( R.id.receiverPhoneBtn )
    ImageView receiverPhoneBtn;
    @Bind ( R.id.provinceL )
    LinearLayout provinceL;
    @Bind ( R.id.province )
    TextView province;
    @Bind ( R.id.cityL )
    LinearLayout cityL;
    @Bind ( R.id.city )
    TextView city;
    @Bind ( R.id.areaL )
    LinearLayout areaL;
    @Bind ( R.id.area )
    TextView area;
    @Bind ( R.id.detail )
    EditText detail;
    @Bind ( R.id.detailBtn )
    ImageView detailBtn;
    @Bind ( R.id.defauleBtn )
    ImageView defauleBtn;

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
        setContentView ( R.layout.add_address );
        ButterKnife.bind ( this );
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        initTitle ( );
        initData();
    }

    private void initData()
    {
        //设置标记：0 非默认  1 默认
        defauleBtn.setTag ( 0 );
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate ( );
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        SystemTools.loadBackground ( titleRightImage, resources.getDrawable ( R.mipmap.save_btn ) );
        titleText.setText ( "添加地址" );
    }

    //清空收货人
    @OnClick(R.id.receiverNameBtn)
    void clearReceiverName()
    {
        receiverName.setText ( "" );
    }

    //清空收货人手机号
    @OnClick(R.id.receiverPhoneBtn)
    void clearReceiverPhone()
    {
        receiverPhone.setText ( "" );
    }

    //清空收货人详细地址
    @OnClick(R.id.detailBtn)
    void clearReceiverDetail()
    {
        detail.setText ( "" );
    }

    //设置默认按钮
    @OnClick(R.id.defauleBtn)
    void settingDefault()
    {
        if(0 == defauleBtn.getTag ())
        {
            //设置默认背景
            defauleBtn.setTag ( 1 );
            SystemTools.loadBackground ( defauleBtn, resources.getDrawable ( R.mipmap.setting_default_a ) );
        }
        else if(1 == defauleBtn.getTag ())
        {
            //设置非默认背景
            defauleBtn.setTag ( 0 );
            SystemTools.loadBackground ( defauleBtn, resources.getDrawable ( R.mipmap.setting_default_b ) );
        }
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf ( AddAddressActivity.this );
    }

    @OnClick(R.id.titleRightImage)
    void doSave()
    {
        //保存地址数据

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

        if ( keyCode == KeyEvent.KEYCODE_BACK
             && event.getAction ( ) == KeyEvent.ACTION_DOWN ) {
            //关闭
            this.closeSelf ( AddAddressActivity.this );
        }
        return super.onKeyDown ( keyCode, event );
    }


}

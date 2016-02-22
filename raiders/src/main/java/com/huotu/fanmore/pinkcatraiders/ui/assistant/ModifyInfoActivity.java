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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改用户信息界面
 */
public
class ModifyInfoActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;
    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;
    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;
    public Bundle bundle;
    @Bind ( R.id.modityTextInput )
    EditText modityTextInput;

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
        setContentView ( R.layout.user_modify );
        ButterKnife.bind ( this );
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        bundle = this.getIntent ().getExtras ( );
        wManager = this.getWindowManager ( );
        initTitle ( );
        initData();
    }

    @OnClick ( R.id.titleLeftImage )
    void doback()
    {
        closeSelf ( ModifyInfoActivity.this );
    }
    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate ( );
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText ( bundle.getString ( "profile" ) + "信息修改" );
    }

    private void initData()
    {
        modityTextInput.setText ( bundle.getString ( "content" ) );
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
            this.closeSelf(ModifyInfoActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}

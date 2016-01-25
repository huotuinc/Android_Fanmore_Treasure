package com.huotu.fanmore.pinkcatraiders.ui.login;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.LoginWXModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;

import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements Handler.Callback,View.OnClickListener {

    private
    AutnLogin      login;
    //handler对象
    public Handler mHandler;
    public
    ProgressPopupWindow progress;
    public
    ProgressPopupWindow successProgress;
    //windows类
    WindowManager wManager;
    public
    NoticePopWindow noticePop;
    public
    AssetManager am;

    public
    BaseApplication application;
    public Resources res;

    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.edtUserName)
    EditText edtUserName;
    @Bind(R.id.edtPwd)
    EditText edtPwd;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.btn_phonereg)
    Button btn_phonereg;
    @Bind(R.id.btn_wx)
    ImageView btn_wx;
    @Bind(R.id.tv_wx)
    TextView tv_wx;
    @Bind(R.id.btn_qq)
    ImageView btn_qq;
    @Bind(R.id.tv_qq)
    TextView tv_qq;
    @Bind(R.id.loginL)
    RelativeLayout  loginL;
    @Bind(R.id.tv_forgetpsd)
    TextView tv_forgetpsd;


    @Override
    public boolean handleMessage(Message msg) {
        switch ( msg.what )
        {

            //授权登录
            case Contant.MSG_AUTH_COMPLETE:
            {
                //提示授权成功
                Platform plat = ( Platform ) msg.obj;
                login.authorize ( plat );
            }
            break;
            //授权登录
            case Contant.LOGIN_AUTH_ERROR:
            {
                btn_wx.setClickable(true);
                successProgress.dismissView();
                ToastUtils.showShortToast(this, "登录失败");
            }
            break;
            case Contant.MSG_AUTH_ERROR:
            {
                loginL.setClickable ( true );
                successProgress.dismissView();

                Throwable throwable = ( Throwable ) msg.obj;
                if("cn.sharesdk.wechat.utils.WechatClientNotExistException".equals ( throwable.toString () ))
                {
                    //手机没有安装微信客户端
                    ToastUtils.showShortToast(this, "手机没有安装微信客户端");

                }
                else
                {
                    loginL.setClickable ( true );
                      successProgress.dismissView();
//                    //提示授权失败
                    ToastUtils.showShortToast(this, "授权操作遇到错误");

                }

            }
            break;
            case Contant.MSG_AUTH_CANCEL:
            {
                loginL.setClickable ( true );
                //提示取消授权
                  successProgress.dismissView();
                ToastUtils.showShortToast(this, "授权操作已取消");


            }
            break;
            case Contant.MSG_USERID_FOUND:
            {
                successProgress.dismissView();
                ToastUtils.showShortToast(this, "已经获取用户信息");


            }
            break;
            case Contant.MSG_LOGIN:
            {
                successProgress.dismissView();
                ToastUtils.showShortToast(this,"登陆成功");
//                LoginWXModel loginWXModel  = (LoginWXModel) msg.obj;
//
//                AuthParamUtils paramUtils = new AuthParamUtils( application, System.currentTimeMillis (),  LoginActivity.this );
               // paramUtils.obtainPostParam ( "loginWXModel",loginWXModel);




            }
            break;
            case Contant.MSG_USERID_NO_FOUND:
            {
                  successProgress.dismissView();
                //提示授权成功
                ToastUtils.showShortToast(this, "获取用户信息失败");

            }
            break;
            case Contant.INIT_MENU_ERROR:
            {
                  successProgress.dismissView();
                ToastUtils.showShortToast(this, "获取用户信息失败");

            }
            break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(LoginActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_ui);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        //设置沉浸模式
        setImmerseLayout(titleLayoutL);
        am = this.getAssets();
        res = this.getResources();
        application = ( BaseApplication ) this.getApplication ();
        wManager = this.getWindowManager();
        progress = new ProgressPopupWindow ( LoginActivity.this, LoginActivity.this, wManager );
        successProgress = new ProgressPopupWindow ( LoginActivity.this, LoginActivity.this, wManager );
        successProgress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
        Drawable bgDraw = res.getDrawable(R.color.title_bg);
        stubTitleText.inflate();
        TextView titleText= (TextView) findViewById(R.id.titleText);
        titleText.setText("登  录");
        titleText.setTextColor(getResources().getColor(R.color.color_white));
        btn_wx.setOnClickListener(this);
        tv_qq.setOnClickListener(this);
        btn_qq.setOnClickListener(this);
        tv_wx.setOnClickListener(this);
        tv_forgetpsd.setOnClickListener(this);
        btn_phonereg.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        SystemTools.loadBackground(titleLayoutL, bgDraw);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_wx:
            case R.id.btn_wx:{
               successProgress.showProgress("正在登录");
               ShareSDK.getPlatform(LoginActivity.this, Wechat.NAME);
               login = new AutnLogin(LoginActivity.this, mHandler, loginL, application);
               login.authorize(new Wechat(LoginActivity.this));
               loginL.setClickable(false);

            }
           break;
            case R.id.tv_qq:
            case R.id.btn_qq:{
                successProgress.showProgress("正在登录");
                ShareSDK.getPlatform(LoginActivity.this, QQ.NAME);
                login = new AutnLogin(LoginActivity.this, mHandler, loginL, application);
                login.authorize(new QQ(LoginActivity.this));
                loginL.setClickable(false);
            }
            break;
            case R.id.tv_forgetpsd:
            {
                ActivityUtils.getInstance().skipActivity(LoginActivity.this, MobileRegActivity.class);
            }
           default:
               break;
        }

    }


}

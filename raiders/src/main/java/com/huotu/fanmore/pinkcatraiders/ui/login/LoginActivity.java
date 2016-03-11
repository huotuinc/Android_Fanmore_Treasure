package com.huotu.fanmore.pinkcatraiders.ui.login;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.common.eventbus.EventBus;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.android.library.libedittext.MainActivity;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.model.AppWXLoginModel;
import com.huotu.fanmore.pinkcatraiders.model.InitOutputsModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginOutputsModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginQQModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginWXModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.guide.GuideActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.EncryptUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.GsonRequest;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;

import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity
        implements Handler.Callback,View.OnClickListener {

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

               // ToastUtils.showShortToast(this,"登陆成功");

                if( msg.arg1 == 1 ) {
                    LoginQQModel qqModel = (LoginQQModel) msg.obj;
                    String url = Contant.REQUEST_URL + Contant.AUTHLOGIN;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), LoginActivity.this);
                    //中文字符特殊处理
                    //1 拼装参数
                    Map<String, Object> maps = new HashMap<String, Object> ();
                    maps.put("username",qqModel.getNickname());
                    maps.put("unionId",qqModel.getOpenid());
                    maps.put("head",qqModel.getIcon());
                    maps.put("type", 2);
                    maps = params.obtainAllParamUTF8 ( maps );
                    //获取sign
                    String signStr = params.obtainSignUTF8 ( maps );
                    maps.put("username", URLEncoder.encode(qqModel.getNickname()));
                    maps.put ( "sign", signStr);
                    //拼装URL
                    String suffix = params.obtainGetParamUTF8 (maps);
                    url = url + suffix;
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress.dismissView();
                            if(LoginActivity.this.isFinishing())
                            {
                                return;
                            }
                            JSONUtil<AppWXLoginModel> jsonUtil = new JSONUtil<AppWXLoginModel>();
                            AppWXLoginModel appWXLoginModel = new AppWXLoginModel();
                            appWXLoginModel = jsonUtil.toBean(response.toString(), appWXLoginModel);
                            if(null != appWXLoginModel && null != appWXLoginModel.getResultData() && (1==appWXLoginModel.getResultCode()))
                            {
                                if(null!=appWXLoginModel.getResultData().getUser())
                                {
                                    try {
                                        //加载用户信息
                                        application.writeUserInfo(appWXLoginModel.getResultData().getUser());
                                        //跳转到首页
                                        ActivityUtils.getInstance().skipActivity(LoginActivity.this, HomeActivity.class);
                                    } catch (Exception e)
                                    {
                                        //未获取该用户信息
                                        noticePop = new NoticePopWindow ( LoginActivity.this, LoginActivity.this, wManager, "用户数据存在非法字符");
                                        noticePop.showNotice ( );
                                        noticePop.showAtLocation (titleLayoutL,
                                                Gravity.CENTER, 0, 0
                                        );
                                    }
                                }
                                else
                                {
                                    //未获取该用户信息
                                    noticePop = new NoticePopWindow ( LoginActivity.this, LoginActivity.this, wManager, "未获取该用户信息");
                                    noticePop.showNotice ( );
                                    noticePop.showAtLocation (titleLayoutL,
                                            Gravity.CENTER, 0, 0
                                    );
                                }
                            }
                            else
                            {
                                //异常处理，自动切换成无数据
                                noticePop = new NoticePopWindow ( LoginActivity.this, LoginActivity.this, wManager, "登录失败");
                                noticePop.showNotice ( );
                                noticePop.showAtLocation(titleLayoutL,
                                        Gravity.CENTER, 0, 0
                                );
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.dismissView();
                            //初始化失败
                            //异常处理，自动切换成无数据
                            noticePop = new NoticePopWindow ( LoginActivity.this, LoginActivity.this, wManager, "登录失败");
                            noticePop.showNotice ( );
                            noticePop.showAtLocation(titleLayoutL,
                                    Gravity.CENTER, 0, 0
                            );
                        }
                    });

                }
                else if( msg.arg1 == 2 ) {
                    LoginWXModel loginWXModel = (LoginWXModel) msg.obj;
                    AuthParamUtils paramUtils = new AuthParamUtils( application, System.currentTimeMillis (),  LoginActivity.this );
                    //中文字符特殊处理
                    //1 拼装参数
                    Map<String, Object> maps = new HashMap<String, Object> ();
                    maps.put("username",loginWXModel.getNickname());
                    maps.put("unionId",loginWXModel.getUnionid());
                    maps.put("head", loginWXModel.getHeadimgurl());
                    maps.put("type", 1);
                    maps = paramUtils.obtainAllParamUTF8 ( maps );
                    //获取sign
                    String signStr = paramUtils.obtainSignUTF8 ( maps );
                    maps.put("username", URLEncoder.encode(loginWXModel.getNickname()));
                    maps.put ( "sign", signStr);
                    //拼装URL
                    String suffix = paramUtils.obtainGetParamUTF8 (maps);
                    String url=Contant.REQUEST_URL+"authLogin"+suffix;

                    GsonRequest<AppWXLoginModel> loginRequest = new GsonRequest<AppWXLoginModel>(
                            Request.Method.GET,
                            url ,
                            AppWXLoginModel.class,
                            null,
                            loginListener,
                            errorListener

                    );

                    VolleyUtil.addRequest(loginRequest);
                }








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
        titleText.setText("用户登录");
        titleText.setTextColor(getResources().getColor(R.color.color_white));
        btn_wx.setOnClickListener(this);
        tv_qq.setOnClickListener(this);
        btn_qq.setOnClickListener(this);
        tv_wx.setOnClickListener(this);
        tv_forgetpsd.setOnClickListener(this);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
    }

    @OnClick(R.id.btn_login)
    void doLogin()
    {
        if(TextUtils.isEmpty(edtUserName.getText()))
        {
            ToastUtils.showLongToast(LoginActivity.this, "请输入邮箱或者手机号");
            return;
        }
        else if(TextUtils.isEmpty(edtPwd.getText()))
        {
            ToastUtils.showLongToast(LoginActivity.this, "请输入密码");
            return;
        }
        else
        {
            progress.showProgress("正在登录");
            progress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
            //登录接口
            String url = Contant.REQUEST_URL + Contant.LOGIN;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), LoginActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("username", edtUserName.getText().toString());
            maps.put("password", EncryptUtil.getInstance().encryptMd532(edtPwd.getText().toString()));
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progress.dismissView();
                    if(LoginActivity.this.isFinishing())
                    {
                        return;
                    }
                    JSONUtil<LoginOutputsModel> jsonUtil = new JSONUtil<LoginOutputsModel>();
                    LoginOutputsModel loginOutputs = new LoginOutputsModel();
                    loginOutputs = jsonUtil.toBean(response.toString(), loginOutputs);
                    if(null != loginOutputs && null != loginOutputs.getResultData() && (1==loginOutputs.getResultCode()))
                    {

                        if(null!=loginOutputs.getResultData().getUser())
                        {
                            try {
                                //加载用户信息
                                application.writeUserInfo(loginOutputs.getResultData().getUser());
                                //跳转到首页
                                ActivityUtils.getInstance().skipActivity(LoginActivity.this, HomeActivity.class);
                            } catch (Exception e)
                            {
                                //未获取该用户信息
                                noticePop = new NoticePopWindow ( LoginActivity.this, LoginActivity.this, wManager, "用户数据存在非法字符");
                                noticePop.showNotice ( );
                                noticePop.showAtLocation (titleLayoutL,
                                        Gravity.CENTER, 0, 0
                                );
                            }
                        }
                        else
                        {
                            //未获取该用户信息
                            noticePop = new NoticePopWindow ( LoginActivity.this, LoginActivity.this, wManager, "未获取该用户信息");
                            noticePop.showNotice ( );
                            noticePop.showAtLocation (titleLayoutL,
                                    Gravity.CENTER, 0, 0
                            );
                        }
                    }
                    else
                    {
                        //异常处理，自动切换成无数据
                        noticePop = new NoticePopWindow ( LoginActivity.this, LoginActivity.this, wManager, "登录失败");
                        noticePop.showNotice ( );
                        noticePop.showAtLocation(titleLayoutL,
                                Gravity.CENTER, 0, 0
                        );
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress.dismissView();
                    //初始化失败
                    //异常处理，自动切换成无数据
                    noticePop = new NoticePopWindow ( LoginActivity.this, LoginActivity.this, wManager, "登录失败");
                    noticePop.showNotice ( );
                    noticePop.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0
                    );
                }
            });
        }
    }

    @OnClick(R.id.btn_phonereg)
    void doReg()
    {
        //注册
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        ActivityUtils.getInstance().skipActivity(LoginActivity.this, MobileRegActivity.class, bundle);
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
                //修改密码
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                ActivityUtils.getInstance().showActivity(LoginActivity.this, MobileRegActivity.class, bundle);
            }
           default:
               break;
        }

    }

     Response.ErrorListener errorListener =new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
             noticePop = new NoticePopWindow(LoginActivity.this, LoginActivity.this, wManager, "登录失败");
             noticePop.showNotice();
             noticePop.showAtLocation(titleLayoutL,
                     Gravity.CENTER, 0, 0);
         }

     };

    Response.Listener<AppWXLoginModel> loginListener = new Response.Listener<AppWXLoginModel>() {
        @Override
        public void onResponse(AppWXLoginModel appWXLoginModel) {
            //LoginActivity.this.closeProgressDialog();

            if( null == appWXLoginModel ){

                return;
            }
            else if( appWXLoginModel.getSystemResultCode() != 1){

                return;
            }else if( appWXLoginModel.getResultCode() !=1){

                return;
            }
            if( appWXLoginModel.getResultData() ==null ){

                return;
            }
            AppUserModel user = appWXLoginModel.getResultData().getUser();
            if(null != user)
            {
                //记录token
                BaseApplication.getInstance().writeUserInfo(user);

                ActivityUtils.getInstance().skipActivity(LoginActivity.this, HomeActivity.class );
                finish();
            }
            else
            {
                ToastUtils.showShortToast(LoginActivity.this, "未请求到数据");
            }
        }};

}

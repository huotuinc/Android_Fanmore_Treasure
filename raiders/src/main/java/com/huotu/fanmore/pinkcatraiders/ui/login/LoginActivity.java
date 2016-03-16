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
import com.google.gson.Gson;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.android.library.libedittext.MainActivity;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppBalanceModel;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.model.AppWXLoginModel;
import com.huotu.fanmore.pinkcatraiders.model.BalanceOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseBalanceModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.CartCountModel;
import com.huotu.fanmore.pinkcatraiders.model.CartDataModel;
import com.huotu.fanmore.pinkcatraiders.model.InitOutputsModel;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.ListOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.LocalCartOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginOutputsModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginQQModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginWXModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.guide.GuideActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.PayOrderActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.EncryptUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.GsonRequest;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.PreferenceHelper;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;

import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Bundle bundle;

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
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;


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
                progress.dismissView();
                ToastUtils.showShortToast(this, "登录失败");
            }
            break;
            case Contant.MSG_AUTH_ERROR:
            {
                loginL.setClickable ( true );
                progress.dismissView();

                Throwable throwable = ( Throwable ) msg.obj;
                if("cn.sharesdk.wechat.utils.WechatClientNotExistException".equals ( throwable.toString () ))
                {
                    //手机没有安装微信客户端
                    ToastUtils.showShortToast(this, "手机没有安装微信客户端");

                }
                else
                {
                    loginL.setClickable ( true );
                    progress.dismissView();
//                    //提示授权失败
                    ToastUtils.showShortToast(this, "授权操作遇到错误");

                }

            }
            break;
            case Contant.MSG_AUTH_CANCEL:
            {
                loginL.setClickable ( true );
                //提示取消授权
                progress.dismissView();
                ToastUtils.showShortToast(this, "授权操作已取消");


            }
            break;
            case Contant.MSG_USERID_FOUND:
            {

                ToastUtils.showShortToast(this, "已经获取用户信息");


            }
            break;
            case Contant.MSG_LOGIN:
            {

               // ToastUtils.showShortToast(this,"登陆成功");

                if( msg.arg1 == 1 ) {
                    progress.showProgress("正在登录");
                    LoginQQModel qqModel = (LoginQQModel) msg.obj;
                    String url = Contant.REQUEST_URL + Contant.AUTHLOGIN;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), LoginActivity.this);
                    //中文字符特殊处理
                    //1 拼装参数
                    Map<String, Object> maps = new HashMap<String, Object> ();
                    maps.put("username",qqModel.getNickname());
                    maps.put("unionId",qqModel.getOpenid());
                    application.writeunionid(qqModel.getOpenid());
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

                                        if(bundle!=null&&null != bundle.getString("loginData") && !"".equals(bundle.getString("loginData")))



                                        {
                                            uploadCartData();
                                        }
                                        else
                                        {
                                            //跳转到首页
                                            ActivityUtils.getInstance().skipActivity(LoginActivity.this, HomeActivity.class);
                                        }

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
                    progress.showProgress("正在登录");
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
                progress.dismissView();
                //提示授权成功
                ToastUtils.showShortToast(this, "获取用户信息失败");

            }
            break;
            case Contant.INIT_MENU_ERROR:
            {
                progress.dismissView();
                ToastUtils.showShortToast(this, "获取用户信息失败");

            }
            break;
        }
        return false;
    }

    private void uploadCartData()
    {
            //提交本地购物数据
            String data = bundle.getString("loginData");
            JSONUtil<LocalCartOutputModel> jsonUtil = new JSONUtil<LocalCartOutputModel>();
            LocalCartOutputModel localCartOutput = new LocalCartOutputModel();
            localCartOutput = jsonUtil.toBean(data, localCartOutput);
            List<ListModel> lists = localCartOutput.getResultData().getLists();
            List<UploadLocalCartDataModel> uploadLocalCarts = new ArrayList<UploadLocalCartDataModel>();
            for(int i=0; i<lists.size(); i++)
            {
                UploadLocalCartDataModel uploadLocalCartData = new UploadLocalCartDataModel();
                uploadLocalCartData.setIssueId(lists.get(i).getIssueId());
                uploadLocalCartData.setAmount(lists.get(i).getUserBuyAmount());
                uploadLocalCarts.add(uploadLocalCartData);
            }

            Gson gson = new Gson();
            String carts = gson.toJson(uploadLocalCarts);

            String url = Contant.REQUEST_URL + Contant.JOIN_ALL_CART_TO_SERVER;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), LoginActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put ( "cartsJson", carts );
            Map<String, Object> param = params.obtainPostParam(maps);
            ListOutputModel base = new ListOutputModel ();
            HttpUtils<ListOutputModel> httpUtils = new HttpUtils<ListOutputModel> ();

            httpUtils.doVolleyPost (
                    base, url, param, new Response.Listener< ListOutputModel > ( ) {
                        @Override
                        public
                        void onResponse ( ListOutputModel response ) {
                            //清空本地数据
                            CartDataModel.deleteAll(CartDataModel.class);
                            ListOutputModel base = response;
                            if(null!=base&&null!=base.getResultData()&&null!=base.getResultData().getList()&&1==base.getResultCode())
                            {
                                //跳转到结算界面
                                List<ListModel> ls = base.getResultData().getList();
                                List<CartBalanceModel> paramsList = new ArrayList<CartBalanceModel>();
                                for(int i=0; i<ls.size(); i++)
                                {
                                    CartBalanceModel cartBalanceModel = new CartBalanceModel();
                                    cartBalanceModel.setPid(ls.get(i).getSid());
                                    cartBalanceModel.setBuyAmount(ls.get(i).getUserBuyAmount());
                                    paramsList.add(cartBalanceModel);
                                }

                                //转成json格式参数
                                Gson gson = new Gson();
                                String carts = gson.toJson(paramsList);
                                String url = Contant.REQUEST_URL + Contant.BALANCE;
                                AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), LoginActivity.this);
                                Map<String, Object> maps = new HashMap<String, Object> ();
                                maps.put ( "carts",  carts);
                                Map<String, Object> param = params.obtainPostParam(maps);
                                BalanceOutputModel base1 = new BalanceOutputModel ();
                                HttpUtils<BalanceOutputModel> httpUtils = new HttpUtils<BalanceOutputModel> ();
                                httpUtils.doVolleyPost(
                                        base1, url, param, new Response.Listener<BalanceOutputModel>() {
                                            @Override
                                            public void onResponse(BalanceOutputModel response) {
                                                BalanceOutputModel base1 = response;
                                                if (1 == base1.getResultCode() && null != base1.getResultData() && null != base1.getResultData().getData()) {
                                                    AppBalanceModel balance = base1.getResultData().getData();
                                                    BaseBalanceModel baseBalance = new BaseBalanceModel();
                                                    baseBalance.setMoney(balance.getMoney());
                                                    baseBalance.setRedPacketsEndTime(balance.getRedPacketsEndTime());
                                                    baseBalance.setRedPacketsFullMoney(balance.getRedPacketsFullMoney());
                                                    baseBalance.setRedPacketsId(balance.getRedPacketsId());
                                                    baseBalance.setRedPacketsMinusMoney(balance.getRedPacketsMinusMoney());
                                                    baseBalance.setRedPacketsNumber(balance.getRedPacketsNumber());
                                                    baseBalance.setRedPacketsRemark(balance.getRedPacketsRemark());
                                                    baseBalance.setRedPacketsStartTime(balance.getRedPacketsStartTime());
                                                    baseBalance.setRedPacketsStatus(null==balance.getRedPacketsStatus()?null:balance.getRedPacketsStatus().getName());
                                                    baseBalance.setTotalMoney(balance.getTotalMoney());
                                                    baseBalance.setRedPacketsTitle(balance.getRedPacketsTitle());
                                                    Bundle bundle = new Bundle();
                                                    bundle.putSerializable("baseBalance", baseBalance);
                                                    ActivityUtils.getInstance ().skipActivity(LoginActivity.this, PayOrderActivity.class, bundle );
                                                } else {
                                                    progress.dismissView();
                                                    VolleyUtil.cancelAllRequest();
                                                    //上传失败
                                                    noticePop = new NoticePopWindow(LoginActivity.this, LoginActivity.this, wManager, "结算失败");
                                                    noticePop.showNotice();
                                                    noticePop.showAtLocation(
                                                            findViewById(R.id.titleLayout),
                                                            Gravity.CENTER, 0, 0
                                                    );
                                                }
                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                progress.dismissView();
                                                VolleyUtil.cancelAllRequest();
                                                //系统级别错误
                                                noticePop = new NoticePopWindow(LoginActivity.this, LoginActivity.this, wManager, "结算失败");
                                                noticePop.showNotice();
                                                noticePop.showAtLocation(
                                                        findViewById(R.id.titleLayout),
                                                        Gravity.CENTER, 0, 0
                                                );
                                            }
                                        }
                                );
                            }
                            else
                            {
                                //跳转到购物车
                                MyBroadcastReceiver.sendBroadcast(LoginActivity.this, MyBroadcastReceiver.JUMP_CART);
                                ActivityUtils.getInstance().skipActivity(LoginActivity.this, HomeActivity.class);
                            }
                        }
                    }, new Response.ErrorListener ( ) {

                        @Override
                        public
                        void onErrorResponse ( VolleyError error ) {
                            CartDataModel.deleteAll(CartDataModel.class);
                            //跳转到购物车
                            MyBroadcastReceiver.sendBroadcast(LoginActivity.this, MyBroadcastReceiver.JUMP_CART);
                            ActivityUtils.getInstance().skipActivity(LoginActivity.this, HomeActivity.class);
                        }
                    }
            );
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
        bundle = this.getIntent().getExtras();
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
        SystemTools.loadBackground(titleLeftImage, res.getDrawable(R.mipmap.title_back_white));

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

                                if(bundle!=null&&null != bundle.getString("loginData") && !"".equals(bundle.getString("loginData")))

                                {
                                    uploadCartData();
                                }
                                else
                                {
                                    //跳转到首页
                                    ActivityUtils.getInstance().skipActivity(LoginActivity.this, HomeActivity.class);
                                }
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

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(LoginActivity.this);
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
//               ShareSDK.getPlatform(LoginActivity.this, Wechat.NAME);
               login = new AutnLogin(LoginActivity.this, mHandler, loginL, application);
               login.authorize(new Wechat(LoginActivity.this));
               loginL.setClickable(false);

            }
           break;
            case R.id.tv_qq:
            case R.id.btn_qq:{
//                Platform platform=ShareSDK.getPlatform(LoginActivity.this, QQ.NAME);
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

                if(null!=bundle&&null != bundle.getString("loginData") && !"".equals(bundle.getString("loginData")))

                {
                    uploadCartData();
                }
                else
                {
                    //跳转到首页
                    ActivityUtils.getInstance().skipActivity(LoginActivity.this, HomeActivity.class);
                }
            }
            else
            {
                ToastUtils.showShortToast(LoginActivity.this, "未请求到数据");
            }
        }};


    public class UploadLocalCartDataModel
    {
        private long issueId;
        private long amount;

        public long getIssueId() {
            return issueId;
        }

        public void setIssueId(long issueId) {
            this.issueId = issueId;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }
    }

    public class CartBalanceModel
    {
        private long pid;
        private long buyAmount;

        public long getPid() {
            return pid;
        }

        public void setPid(long pid) {
            this.pid = pid;
        }

        public long getBuyAmount() {
            return buyAmount;
        }

        public void setBuyAmount(long buyAmount) {
            this.buyAmount = buyAmount;
        }
    }

}

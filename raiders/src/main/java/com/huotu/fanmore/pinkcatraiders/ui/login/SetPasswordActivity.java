package com.huotu.fanmore.pinkcatraiders.ui.login;


import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.model.AppWXLoginModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginOutputsModel;
import com.huotu.fanmore.pinkcatraiders.model.RegOutputsModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.EncryptUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetPasswordActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;

    public
    AssetManager am;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.edtpsd)
    EditText edtpsd;
    @Bind(R.id.btnshow)
    TextView btnshow;
    @Bind(R.id.btn_commitpsd)
    Button btn_commitpsd;
    public
    ProgressPopupWindow progress;
    public
    NoticePopWindow noticePop;
    public Handler mHandler;
    //windows类
    WindowManager wManager;
    public Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
        resources = this.getResources ();
        application = ( BaseApplication ) this.getApplication ();
        btn_commitpsd.setOnClickListener(this);
        mHandler = new Handler(this);
        btnshow.setOnClickListener(this);
        btnshow.setText("显示密码");
        btnshow.setTag(0);
        wManager = this.getWindowManager();
        progress = new ProgressPopupWindow ( SetPasswordActivity.this, SetPasswordActivity.this, wManager );
        bundle = this.getIntent().getExtras();
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
        titleText.setText("设置密码");
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
            case R.id.btnshow:{
                if(0==Integer.parseInt(btnshow.getTag().toString()))
                {
                    //显示密码
                    edtpsd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    btnshow.setText("隐藏密码");
                    btnshow.setTag(1);
                }
                else if(1==Integer.parseInt(btnshow.getTag().toString()))
                {
                    edtpsd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnshow.setText("显示密码");
                    btnshow.setTag(0);
                }
            }
            break;
            case R.id.btn_commitpsd:{
                if(TextUtils.isEmpty(edtpsd.getText()))
                {
                    ToastUtils.showMomentToast(SetPasswordActivity.this, SetPasswordActivity.this, "请输入密码");
                    return;
                }
                else {

                    if(0==bundle.getInt("type"))
                    {
                        progress.showProgress("注册中......");
                        progress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
                        String url = Contant.REQUEST_URL + Contant.REG;
                        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), SetPasswordActivity.this);
                        Map<String, Object> maps = new HashMap<String, Object>();
                        maps.put("phone", bundle.getString("phone"));
                        maps.put("password", EncryptUtil.getInstance().encryptMd532(edtpsd.getText().toString()));
                        String suffix = params.obtainGetParam(maps);
                        url = url + suffix;
                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progress.dismissView();
                                if (SetPasswordActivity.this.isFinishing()) {
                                    return;
                                }
                                JSONUtil<RegOutputsModel> jsonUtil = new JSONUtil<RegOutputsModel>();
                                RegOutputsModel regOutputsModel = new RegOutputsModel();
                                regOutputsModel = jsonUtil.toBean(response.toString(), regOutputsModel);
                                if (null != regOutputsModel && null != regOutputsModel.getResultData() && (1 == regOutputsModel.getResultCode())) {
                                    if (null != regOutputsModel.getResultData().getUser()) {
                                        try {
                                            //加载用户信息
                                            application.writeUserInfo(regOutputsModel.getResultData().getUser());
                                            //跳转到首页
                                            ToastUtils.showMomentToast(SetPasswordActivity.this, SetPasswordActivity.this, "注册成功，2秒后跳转到首页");
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ActivityUtils.getInstance().skipActivity(SetPasswordActivity.this, HomeActivity.class);
                                                }
                                            }, 1500);

                                        } catch (Exception e) {
                                            //未获取该用户信息
                                            noticePop = new NoticePopWindow(SetPasswordActivity.this, SetPasswordActivity.this, wManager, "用户数据存在非法字符");
                                            noticePop.showNotice();
                                            noticePop.showAtLocation(edtpsd,
                                                    Gravity.CENTER, 0, 0
                                            );
                                        }
                                    } else {
                                        //未获取该用户信息
                                        noticePop = new NoticePopWindow(SetPasswordActivity.this, SetPasswordActivity.this, wManager, "未获取该用户信息");
                                        noticePop.showNotice();
                                        noticePop.showAtLocation(edtpsd,
                                                Gravity.CENTER, 0, 0
                                        );
                                    }
                                } else if (54001 == regOutputsModel.getResultCode()) {
                                    //异常处理，自动切换成无数据
                                    noticePop = new NoticePopWindow(SetPasswordActivity.this, SetPasswordActivity.this, wManager, regOutputsModel.getResultDescription());
                                    noticePop.showNotice();
                                    noticePop.showAtLocation(edtpsd,
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
                                noticePop = new NoticePopWindow(SetPasswordActivity.this, SetPasswordActivity.this, wManager, "服务器未响应");
                                noticePop.showNotice();
                                noticePop.showAtLocation(edtpsd,
                                        Gravity.CENTER, 0, 0
                                );
                            }
                        });
                    }
                    else if(1==bundle.getInt("type"))
                    {
                        progress.showProgress("提交中......");
                        progress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
                        String url = Contant.REQUEST_URL + "forgetPassword";
                        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), SetPasswordActivity.this);
                        Map<String, Object> maps = new HashMap<String, Object>();
                        maps.put("phone", bundle.getString("phone"));
                        maps.put("password", EncryptUtil.getInstance().encryptMd532(edtpsd.getText().toString()));
                        String suffix = params.obtainGetParam(maps);
                        url = url + suffix;
                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progress.dismissView();
                                if (SetPasswordActivity.this.isFinishing()) {
                                    return;
                                }
                                JSONUtil<BaseModel> jsonUtil = new JSONUtil<BaseModel>();
                                BaseModel base = new BaseModel();
                                base = jsonUtil.toBean(response.toString(), base);
                                if (null != base && (1 == base.getResultCode())) {
                                    if (application.isLogin()){
                                        ToastUtils.showMomentToast(SetPasswordActivity.this, SetPasswordActivity.this, "修改密码成功");
                                        finish();
                                    }else {

                                        ToastUtils.showMomentToast(SetPasswordActivity.this, SetPasswordActivity.this, "修改密码成功");
                                        ActivityUtils.getInstance().skipActivity(SetPasswordActivity.this, LoginActivity.class);
                                    }
                                } else if (54001 == base.getResultCode()) {
                                    //异常处理，自动切换成无数据
                                    noticePop = new NoticePopWindow(SetPasswordActivity.this, SetPasswordActivity.this, wManager, base.getResultDescription());
                                    noticePop.showNotice();
                                    noticePop.showAtLocation(edtpsd,
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
                                noticePop = new NoticePopWindow(SetPasswordActivity.this, SetPasswordActivity.this, wManager, "服务器未响应");
                                noticePop.showNotice();
                                noticePop.showAtLocation(edtpsd,
                                        Gravity.CENTER, 0, 0
                                );
                            }
                        });
                    }

               }
            }
            break;
            default:
                break;
        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}

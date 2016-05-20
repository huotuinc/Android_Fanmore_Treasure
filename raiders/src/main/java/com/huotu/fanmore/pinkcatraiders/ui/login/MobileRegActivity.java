package com.huotu.fanmore.pinkcatraiders.ui.login;

import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.BindOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.UserSettingActivity;
import com.huotu.fanmore.pinkcatraiders.widget.CountDownTimerButton;


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


import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;

import com.huotu.fanmore.pinkcatraiders.model.GetCode;

import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/1/22.
 */
public class MobileRegActivity extends BaseActivity implements Handler.Callback, View.OnClickListener {

    public
    Resources resources;
    public BaseApplication application;

    public
    AssetManager am;
    // 按钮倒计时控件
    private CountDownTimerButton countDownBtn;

    @Bind(R.id.edtPhone)
    EditText edtPhone;

    @Bind(R.id.edtCode)
    EditText edtCode;

    @Bind(R.id.btn_code)
    TextView btn_code;

    @Bind(R.id.btn_commit)
    Button btn_commit;

    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;

    GetCode getVCResult = null;
    public Resources res;


    public
    ProgressPopupWindow progress;

    public
    ProgressPopupWindow successProgress;

    //windows类
    WindowManager wManager;

    public
    NoticePopWindow noticePop;
    public Bundle bundle;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobilereg_forgetpsd_ui);
        ButterKnife.bind(this);
        res = this.getResources();
        application = (BaseApplication) this.getApplication();
        bundle = this.getIntent().getExtras();
        btn_commit.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        wManager = this.getWindowManager();
        progress = new ProgressPopupWindow(
                MobileRegActivity.this, MobileRegActivity.this,
                wManager
        );
        initTitle();
    }

    private void initTitle() {
        //背景色
        Drawable bgDraw = res.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = res.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        if (1 == bundle.getInt("type")) {
            titleText.setText("手机注册");
        } else if (2 == bundle.getInt("type")) {
            titleText.setText("忘记密码");
        } else if (3 == bundle.getInt("type")) {
            titleText.setText("修改手机");
        } else if (4 == bundle.getInt("type")) {
            titleText.setText("设置手机");
        }
    }

    @OnClick(R.id.titleLeftImage)
    void doBack() {
        closeSelf(MobileRegActivity.this);
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
        if (null != countDownBtn) {
            countDownBtn.Stop();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            //关闭
            this.closeSelf(MobileRegActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void checkAuthCode() {

        if (TextUtils.isEmpty(edtPhone.getText())) {
            ToastUtils.showMomentToast(MobileRegActivity.this, MobileRegActivity.this, "请输入手机号");
            return;
        } else if (TextUtils.isEmpty(edtCode.getText())) {
            ToastUtils.showMomentToast(MobileRegActivity.this, MobileRegActivity.this, "请输入验证码");
            return;
        } else {
            progress.showProgress("正在提交验证码");
            progress.showAtLocation(btn_commit, Gravity.CENTER, 0, 0);
            //登录接口
            String url = Contant.REQUEST_URL + "checkAuthCode";
            AuthParamUtils params = new AuthParamUtils(
                    application, System.currentTimeMillis(
            ),
                    MobileRegActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("phone", edtPhone.getText().toString());
            maps.put("authcode", edtCode.getText().toString());
            if (1 == bundle.getInt("type")) {
                maps.put("type", "1");
            } else if (2 == bundle.getInt("type")) {
                maps.put("type", "2");
            } else {
                maps.put("type", "3");
            }
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(
                    url, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            progress.dismissView();
                            if (MobileRegActivity.this.isFinishing()) {
                                return;
                            }
                            JSONUtil<GetCode> jsonUtil = new JSONUtil<GetCode>();
                            GetCode getCode = new GetCode();
                            getCode = jsonUtil.toBean(response.toString(), getCode);
                            if (1 == getCode.getResultCode()) {

                                Bundle bundle1 = new Bundle();
                                bundle1.putString("phone", edtPhone.getText().toString());
                                if (1 == bundle.getInt("type")) {
                                    bundle1.putInt("type", 0);
                                    ActivityUtils.getInstance().skipActivity(MobileRegActivity.this, SetPasswordActivity.class, bundle1);
                                } else if (2 == bundle.getInt("type")) {
                                    bundle1.putInt("type", 1);
                                    ActivityUtils.getInstance().skipActivity(MobileRegActivity.this, SetPasswordActivity.class, bundle1);
                                } else {
                                    bindMobile();

                                }

                            } else if (53007 == getCode.getResultCode()) {
                                //异常处理，自动切换成无数据
                                noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "验证码错误");
                                noticePop.showNotice();
                                noticePop.showAtLocation(btn_commit,
                                        Gravity.CENTER, 0, 0
                                );
                            } else {
                                //异常处理，自动切换成无数据
                                noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "验证失败");
                                noticePop.showNotice();
                                noticePop.showAtLocation(btn_commit,
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
                            noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "服务器未响应");
                            noticePop.showNotice();
                            noticePop.showAtLocation(btn_commit,
                                    Gravity.CENTER, 0, 0
                            );
                        }
                    });

        }
    }

    /**
     * @throws
     * @方法描述：绑定手机
     * @方法名：bindMobile
     * @参数：
     * @返回：void
     */
    private void bindMobile() {
        if (3 == Integer.parseInt(bundle.get("type").toString())) {
            progress.showProgress("正在修改手机");
        } else if (4 == Integer.parseInt(bundle.get("type").toString())) {
            progress.showProgress("正在绑定手机");
        }
        progress.showAtLocation(btn_commit, Gravity.CENTER, 0, 0);

        String url = Contant.REQUEST_URL + Contant.BINDMOBLIE;
        AuthParamUtils params = new AuthParamUtils(
                application, System.currentTimeMillis(
        ),
                MobileRegActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("phone", edtPhone.getText().toString());
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(
                url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        progress.dismissView();
                        if (MobileRegActivity.this.isFinishing()) {
                            return;
                        }
                        JSONUtil<BindOutputModel> jsonUtil = new JSONUtil<BindOutputModel>();
                        BindOutputModel bindmoblie = new BindOutputModel();
                        bindmoblie = jsonUtil.toBean(response.toString(), bindmoblie);
                        if (null != bindmoblie && null != bindmoblie.getResultData() && (1 == bindmoblie.getResultCode())) {

                            AppUserModel user = bindmoblie.getResultData().getData();
                            if (null != user) {
                                application.writeUserInfo(user);
                                ActivityUtils.getInstance().skipActivity(MobileRegActivity.this, UserSettingActivity.class);

                            } else {
                                ToastUtils.showMomentToast(MobileRegActivity.this, MobileRegActivity.this, "未请求到数据");
                            }


                        } else {
                            //异常处理，自动切换成无数据
                            noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "绑定失败");
                            noticePop.showNotice();
                            noticePop.showAtLocation(btn_commit,
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
                        noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "服务器未响应");
                        noticePop.showNotice();
                        noticePop.showAtLocation(btn_commit,
                                Gravity.CENTER, 0, 0
                        );
                    }
                });

    }


    /**
     * @throws
     * @方法描述：获取验证码
     * @方法名：getCode
     * @参数：
     * @返回：void
     */
    private void getCode() {
        progress.showProgress("正在获取验证码");
        progress.showAtLocation(btn_commit, Gravity.CENTER, 0, 0);
        //登录接口
        String url = Contant.REQUEST_URL + "sendSMS";
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), MobileRegActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("phone", edtPhone.getText().toString());
        if (1 == bundle.getInt("type")) {
            maps.put("type", "1");
        } else if (2 == bundle.getInt("type")) {
            maps.put("type", "2");
            maps.put("userName", edtPhone.getText().toString());
        } else {
            maps.put("type", "3");
        }
        maps.put("codeType", "0");
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress.dismissView();
                if (MobileRegActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<GetCode> jsonUtil = new JSONUtil<GetCode>();
                GetCode getCode = new GetCode();
                getCode = jsonUtil.toBean(response.toString(), getCode);
                if (null != getCode && null != getCode.getResultData() && (1 == getCode.getResultCode())) {
                    if (getCode.getResultData().isVoiceAble() == false) {
                        try {
                            ToastUtils.showMomentToast(MobileRegActivity.this, MobileRegActivity.this, "获取成功");
                        } catch (Exception e) {
                            //未获取该用户信息
                            noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "用户数据存在非法字符");
                            noticePop.showNotice();
                            noticePop.showAtLocation(btn_commit,
                                    Gravity.CENTER, 0, 0
                            );
                        }
                    } else {
                        //未获取该用户信息
                        noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "未获取该用户信息");
                        noticePop.showNotice();
                        noticePop.showAtLocation(btn_commit,
                                Gravity.CENTER, 0, 0
                        );
                    }
                } else if (55001 == getCode.getResultCode()) {
                    //异常处理，自动切换成无数据
                    noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "短信通道不稳定，请重试");
                    noticePop.showNotice();
                    noticePop.showAtLocation(btn_commit,
                            Gravity.CENTER, 0, 0
                    );
                } else if (53014 == getCode.getResultCode()) {
                    //异常处理，自动切换成无数据
                    noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "验证码已发，60之后再试");
                    noticePop.showNotice();
                    noticePop.showAtLocation(btn_commit,
                            Gravity.CENTER, 0, 0
                    );
                } else {
                    //异常处理，自动切换成无数据
                    noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "验证失败");
                    noticePop.showNotice();
                    noticePop.showAtLocation(btn_commit,
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
                noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "服务器未响应");
                noticePop.showNotice();
                noticePop.showAtLocation(btn_commit,
                        Gravity.CENTER, 0, 0
                );
            }
        });

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_code: {
                if (isWritePhone() == true) {
//                        if(0==bundle.getInt("type"))
//                        {
//                            getCode();
//                            countDownBtn = new CountDownTimerButton(btn_code, "%d秒重发",
//                                    "获取验证码", 60000, new CountDownFinish());
//                            countDownBtn.start();
//                        }
//                        else
//                        {
                    //验证用户名是否存在
                    checkUserName();
//                        }
                } else {
                    ToastUtils.showMomentToast(MobileRegActivity.this, MobileRegActivity.this, "手机号不能为空");
                }
            }
            break;
            case R.id.btn_commit: {
                checkAuthCode();
                //ActivityUtils.getInstance().skipActivity(MobileRegActivity.this, SetPasswordActivity.class);
            }
            break;
            default:
                break;
        }

    }

    private boolean isWritePhone() {
        if (!TextUtils.isEmpty(edtPhone.getText())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 倒计时控件 完成时，回调类
     *
     * @类名称：CountDownFinish
     * @类描述：
     * @创建人：jinxiangdong
     * @修改人：
     * @修改时间：2015年7月8日 上午9:17:06
     * @修改备注：
     * @version:
     */
    class CountDownFinish implements CountDownTimerButton.CountDownFinishListener {

        @Override
        public void finish() {
            if (getVCResult != null && getVCResult.getResultData() != null && getVCResult.getResultData().isVoiceAble()) {
                // 刷新获取按钮状态，设置为可获取语音
                btn_code.setText("尝试语音获取");
                btn_code.setTag(Contant.SMS_TYPE_VOICE);
                ToastUtils.showMomentToast(MobileRegActivity.this, MobileRegActivity.this,
                        "还没收到短信，请尝试语音获取");
            }
        }

    }

    private void checkUserName() {
        progress.showProgress("正在验证手机是否存在");
        progress.showAtLocation(btn_commit, Gravity.CENTER, 0, 0);
        //登录接口
        String url = Contant.REQUEST_URL + "checkPhone";
        AuthParamUtils params = new AuthParamUtils(
                application, System.currentTimeMillis(
        ),
                MobileRegActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("phone", edtPhone.getText().toString());
        if (1 == bundle.getInt("type")) {
            maps.put("type", 1);
        } else if (2 == bundle.getInt("type")) {
            maps.put("type", 2);
        } else if (3 == bundle.getInt("type")) {
            maps.put("type", 3);
        } else if (4 == bundle.getInt("type")) {
            maps.put("type", 3);
        }
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(
                url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progress.dismissView();
                        if (MobileRegActivity.this.isFinishing()) {
                            return;
                        }
                        JSONUtil<BaseModel> jsonUtil = new JSONUtil<BaseModel>();
                        BaseModel base = new BaseModel();
                        base = jsonUtil.toBean(response.toString(), base);
                        if (1 == base.getResultCode()) {
                            //获取验证码
                            getCode();
                            countDownBtn = new CountDownTimerButton(btn_code, "%d秒重发",
                                    "获取验证码", 60000, new CountDownFinish());
                            countDownBtn.start();
                        } else if (52010 == base.getResultCode()) {
                            //异常处理，自动切换成无数据
                            noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "该手机已被注册");
                            noticePop.showNotice();
                            noticePop.showAtLocation(btn_commit,
                                    Gravity.CENTER, 0, 0
                            );
                        } else {
                            //异常处理，自动切换成无数据
                            noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "验证错误");
                            noticePop.showNotice();
                            noticePop.showAtLocation(btn_commit,
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
                        noticePop = new NoticePopWindow(MobileRegActivity.this, MobileRegActivity.this, wManager, "服务器未响应");
                        noticePop.showNotice();
                        noticePop.showAtLocation(btn_commit,
                                Gravity.CENTER, 0, 0
                        );
                    }
                });
    }

}




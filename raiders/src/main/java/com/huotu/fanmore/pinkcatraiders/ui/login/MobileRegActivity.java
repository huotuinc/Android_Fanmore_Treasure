package com.huotu.fanmore.pinkcatraiders.ui.login;

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
import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.model.AppWXLoginModel;
import com.huotu.fanmore.pinkcatraiders.model.GetCode;
import com.huotu.fanmore.pinkcatraiders.model.LoginOutputsModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.EncryptUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.GsonRequest;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.CountDownTimerButton;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/1/22.
 */
public class MobileRegActivity extends BaseActivity implements Handler.Callback,View.OnClickListener {

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
    GetCode getVCResult=null;
    public
    ProgressPopupWindow progress;
    public
    ProgressPopupWindow successProgress;
    //windows类
    WindowManager wManager;
    public
    NoticePopWindow noticePop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobilereg_forgetpsd_ui);
        ButterKnife.bind(this);
        edtPhone.setText("18767152078");
        btn_commit.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        wManager = this.getWindowManager();
        progress = new ProgressPopupWindow ( MobileRegActivity.this, MobileRegActivity.this, wManager );
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
        titleText.setText("用户注册");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
        if (null != countDownBtn)
        {
            countDownBtn.Stop();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(MobileRegActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void checkAuthCode(){
        if (TextUtils.isEmpty(edtPhone.getText()))
        {
            ToastUtils.showLongToast(MobileRegActivity.this, "请输入邮箱或者手机号");
            return;
        }
        else if(TextUtils.isEmpty(edtCode.getText()))
        {
            ToastUtils.showLongToast(MobileRegActivity.this, "请输入验证码");
            return;
        }
        else{
            progress.showProgress("正在提交验证码");
            progress.showAtLocation(btn_commit, Gravity.CENTER, 0, 0);
            //登录接口
            String url = Contant.REQUEST_URL + "checkAuthCode";
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), MobileRegActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("phone", edtPhone.getText().toString());
            maps.put("authcode", edtCode.getText().toString());
            maps.put("type","1");
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progress.dismissView();
                    if(MobileRegActivity.this.isFinishing())
                    {
                        return;
                    }
                    JSONUtil<GetCode> jsonUtil = new JSONUtil<GetCode>();
                    GetCode getCode = new GetCode();
                    getCode = jsonUtil.toBean(response.toString(), getCode);
                    if(1==getCode.getResultCode())
                    {

                                ActivityUtils.getInstance().skipActivity(MobileRegActivity.this, SetPasswordActivity.class,"phone",edtPhone.getText().toString());


                        }


                    else
                    {
                        //异常处理，自动切换成无数据
                        noticePop = new NoticePopWindow ( MobileRegActivity.this, MobileRegActivity.this, wManager, "验证失败");
                        noticePop.showNotice ( );
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
                    noticePop = new NoticePopWindow ( MobileRegActivity.this, MobileRegActivity.this, wManager, "验证失败");
                    noticePop.showNotice ( );
                    noticePop.showAtLocation(btn_commit,
                            Gravity.CENTER, 0, 0
                    );
                }
            });

    }}
    /**
     *
     * @方法描述：获取验证码
     * @方法名：getCode
     * @参数：
     * @返回：void
     * @exception
     * @since
     */
    private void getCode()
    {
        progress.showProgress("正在提交验证码");
        progress.showAtLocation(btn_commit, Gravity.CENTER, 0, 0);
        //登录接口
        String url = Contant.REQUEST_URL + "sendSMS";
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), MobileRegActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("phone", edtPhone.getText().toString());
        maps.put("type","1");
        maps.put("codeType","0");
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress.dismissView();
                if(MobileRegActivity.this.isFinishing())
                {
                    return;
                }
                JSONUtil<GetCode> jsonUtil = new JSONUtil<GetCode>();
                GetCode getCode = new GetCode();
                getCode = jsonUtil.toBean(response.toString(), getCode);
                if(null != getCode && null != getCode.getResultData() && (1==getCode.getResultCode()))
                {
                    if(getCode.getResultData().isVoiceAble()==false)
                    {
                        try {
                           ToastUtils.showShortToast(MobileRegActivity.this,"获取成功");
                        } catch (Exception e)
                        {
                            //未获取该用户信息
                            noticePop = new NoticePopWindow( MobileRegActivity.this, MobileRegActivity.this, wManager, "用户数据存在非法字符");
                            noticePop.showNotice ( );
                            noticePop.showAtLocation (btn_commit,
                                    Gravity.CENTER, 0, 0
                            );
                        }
                    }
                    else
                    {
                        //未获取该用户信息
                        noticePop = new NoticePopWindow ( MobileRegActivity.this, MobileRegActivity.this, wManager, "未获取该用户信息");
                        noticePop.showNotice ( );
                        noticePop.showAtLocation (btn_commit,
                                Gravity.CENTER, 0, 0
                        );
                    }
                }

                else
                {
                    //异常处理，自动切换成无数据
                    noticePop = new NoticePopWindow ( MobileRegActivity.this, MobileRegActivity.this, wManager, "验证失败");
                    noticePop.showNotice ( );
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
                noticePop = new NoticePopWindow ( MobileRegActivity.this, MobileRegActivity.this, wManager, "验证失败");
                noticePop.showNotice ( );
                noticePop.showAtLocation(btn_commit,
                        Gravity.CENTER, 0, 0
                );
            }
        });

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch ( msg.what )
        {

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
                case R.id.btn_code: {
                    if (isWritePhone() == true) {
                        getCode();

                        countDownBtn = new CountDownTimerButton(btn_code, "%d秒重发",
                                "获取验证码", 60000, new CountDownFinish());
                        countDownBtn.start();
                    } else {
                        ToastUtils.showShortToast(MobileRegActivity.this, "手机号不能为空");
                    }
                }
            break;
            case R.id.btn_commit:
            {
                checkAuthCode();
                //ActivityUtils.getInstance().skipActivity(MobileRegActivity.this, SetPasswordActivity.class);
            }
            break;
            default:
                break;
        }

    }
    private boolean isWritePhone()
    {
        if (!TextUtils.isEmpty(edtPhone.getText()))
        {
            return true;
        } else
        {
            return false;
        }
    }
    /**
     * 倒计时控件 完成时，回调类
     * @类名称：CountDownFinish
     * @类描述：
     * @创建人：jinxiangdong
     * @修改人：
     * @修改时间：2015年7月8日 上午9:17:06
     * @修改备注：
     * @version:
     */
    class CountDownFinish  implements CountDownTimerButton.CountDownFinishListener {

        @Override
        public void finish()
        {
            if( getVCResult !=null && getVCResult.getResultData()!=null && getVCResult.getResultData().isVoiceAble()){
                // 刷新获取按钮状态，设置为可获取语音
                btn_code.setText("尝试语音获取");
                btn_code.setTag(Contant.SMS_TYPE_VOICE);
                ToastUtils.showLongToast(MobileRegActivity.this,
                        "还没收到短信，请尝试语音获取");
            }
        }

    }









}




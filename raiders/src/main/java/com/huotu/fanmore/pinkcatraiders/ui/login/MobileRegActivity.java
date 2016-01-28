package com.huotu.fanmore.pinkcatraiders.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.GsonRequest;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.CountDownTimerButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/1/22.
 */
public class MobileRegActivity extends BaseActivity implements Handler.Callback,View.OnClickListener {


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
    GetCode getVCResult=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobilereg_forgetpsd_ui);
        ButterKnife.bind(this);
        btn_commit.setOnClickListener(this);
        btn_code.setOnClickListener(this);
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

        AuthParamUtils paramUtils = new AuthParamUtils( application, System.currentTimeMillis (),
                MobileRegActivity.this);
//
//            LoginWXModel loginWXModel = (LoginWXModel) msg.obj;
//            AuthParamUtils paramUtils = new AuthParamUtils( application, System.currentTimeMillis (),  LoginActivity.this );
        Map<String,Object> checkAuthCode =new HashMap<>();
        checkAuthCode.put("phone",edtPhone.getText().toString());
        checkAuthCode.put("authcode",edtCode.getText().toString());
        String str=paramUtils.obtainGetParam(checkAuthCode);
        String url= Contant.URL+"checkAuthCode"+str;

        GsonRequest<GetCode> loginRequest = new GsonRequest<GetCode>(
                Request.Method.GET,
                url ,
                GetCode.class,
                null,
                loginListener,
                this
        );

        VolleyUtil.addRequest(loginRequest);
    }
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

//
            AuthParamUtils paramUtils = new AuthParamUtils( application, System.currentTimeMillis (),
                    MobileRegActivity.this);
//
//            LoginWXModel loginWXModel = (LoginWXModel) msg.obj;
//            AuthParamUtils paramUtils = new AuthParamUtils( application, System.currentTimeMillis (),  LoginActivity.this );
            Map<String,Object> getcode =new HashMap<>();
             getcode.put("phone",edtPhone.getText().toString());
             getcode.put("type","1");
             getcode.put("codeType","0");
            String str=paramUtils.obtainGetParam(getcode);
            String url= Contant.URL+"sendSMS"+str;

            GsonRequest<GetCode> loginRequest = new GsonRequest<GetCode>(
                    Request.Method.GET,
                    url ,
                    GetCode.class,
                    null,
                    loginListener,
                    this
            );

            VolleyUtil.addRequest(loginRequest);
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
                case R.id.btn_code:
                    if (isWritePhone())
                    {
                getCode();

                countDownBtn = new CountDownTimerButton(btn_code, "%d秒重发",
                        "获取验证码", 90000,  new CountDownFinish());
                countDownBtn.start();
            } else
            {	edtPhone.requestFocus();
                edtPhone.setError("手机号码不能为空");
            }

            break;
            case R.id.btn_commit:
            {
                checkAuthCode();
               // ActivityUtils.getInstance().skipActivity(MobileRegActivity.this,SetPasswordActivity.class);
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







    Response.Listener<GetCode> loginListener = new Response.Listener<GetCode>() {
        @Override
        public void onResponse(GetCode response) {
            if( null == response ){

                return;
            }
            else if( response.getSystemResultCode() != 1){
                ToastUtils.showShortToast(MobileRegActivity.this,response.getResultDescription());

                return;
            }else if( response.getResultCode() !=1){

                return;
            }
            else if (response.getResultCode()==53003){
                ToastUtils.showShortToast(MobileRegActivity.this,response.getResultDescription());
            }
            if( response.getResultData() ==null ){

                return;
            }
            boolean voiceAble = response.getResultData().isVoiceAble();
            if(voiceAble != false)
            {



            }
            else
            {
                ToastUtils.showShortToast(MobileRegActivity.this, "未请求到数据");
            }
        }};

}




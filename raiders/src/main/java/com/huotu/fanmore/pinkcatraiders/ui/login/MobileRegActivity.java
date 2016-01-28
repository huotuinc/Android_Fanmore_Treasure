package com.huotu.fanmore.pinkcatraiders.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppWXLoginModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginWXModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.GsonRequest;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/1/22.
 */
public class MobileRegActivity extends BaseActivity implements Handler.Callback,View.OnClickListener {
 public BaseApplication application;

    // 按钮倒计时控件
   // private CountDownTimerButton countDownBtn;
    @Bind(R.id.edtPhone)
    EditText edtPhone;
    @Bind(R.id.btn_code)
    TextView btn_code;
    @Bind(R.id.btn_commit)
    Button btn_commit;
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
//            AuthParamUtils paramUtils = new AuthParamUtils( application, System.currentTimeMillis (),
//                    MobileRegActivity.this);
//
//            LoginWXModel loginWXModel = (LoginWXModel) msg.obj;
//            AuthParamUtils paramUtils = new AuthParamUtils( application, System.currentTimeMillis (),  LoginActivity.this );
//            Map<String,Object> wxlogin =new HashMap<>();
//            wxlogin.put("username",loginWXModel.getNickname());
//            wxlogin.put("unionId",loginWXModel.getUnionid());
//            wxlogin.put("head",loginWXModel.getHeadimgurl());
//            wxlogin.put("type", "1");
//            String str=paramUtils.obtainGetParam(wxlogin);
//            String url= Contant.URL+"authLogin"+str;
//
//            GsonRequest<AppWXLoginModel> loginRequest = new GsonRequest<AppWXLoginModel>(
//                    Request.Method.GET,
//                    url ,
//                    AppWXLoginModel.class,
//                    null,
//                    loginListener,
//                    this
//            );
//
//            VolleyUtil.addRequest(loginRequest);
    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_code:
//              if (isWritePhone())
//            {
//                getCode();
//
//                countDownBtn = new CountDownTimerButton(btnGet, "%d秒重新发送",
//                        "获取验证码", 60000,  new CountDownFinish());
//                countDownBtn.start();
//            } else
//            {
//                edtPhone.setError("手机号码不能为空");
//            }

            break;
            case R.id.btn_commit:
            {
                ActivityUtils.getInstance().skipActivity(MobileRegActivity.this,SetPasswordActivity.class);
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
}

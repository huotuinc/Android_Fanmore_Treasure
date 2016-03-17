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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.RegOutputsModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.UserSettingActivity;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity implements Handler.Callback,View.OnClickListener {
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
    @Bind(R.id.edtOldPsd)
    EditText edtOldPsd;
    @Bind(R.id.edtNewPwd)
    EditText edtNewPwd;
    @Bind(R.id.btn_commit)
    Button btn_commit;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;

    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_change_password);
        ButterKnife.bind(this);
        res = this.getResources ();
        wManager = this.getWindowManager();
        application = (BaseApplication) this.getApplication ();
        progress = new ProgressPopupWindow (ChangePasswordActivity.this, ChangePasswordActivity.this,
                wManager
        );
        initTitle ( );
    }
    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = res.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = res.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate ( );
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
       titleText.setText("修改密码");
    }
    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(ChangePasswordActivity.this);
    }
    @OnClick(R.id.btn_commit)
    void modifyPassword(){
        if ( TextUtils.isEmpty(edtOldPsd.getText()) ) {
            ToastUtils.showMomentToast(ChangePasswordActivity.this, ChangePasswordActivity.this, "请输入旧密码");
            return;
        }
        else if ( TextUtils.isEmpty ( edtNewPwd.getText ( ) ) ) {
            ToastUtils.showMomentToast(ChangePasswordActivity.this, ChangePasswordActivity.this, "请输入新密码");
            return;
        }
        else {
            progress.showProgress ( "正在提交密码" );
            progress.showAtLocation ( btn_commit, Gravity.CENTER, 0, 0 );
            //登录接口
            String url = Contant.REQUEST_URL + "modifyPassword";
            AuthParamUtils params = new AuthParamUtils (
                    application, System.currentTimeMillis (
            ),
                    ChangePasswordActivity.this );
            Map< String, Object > maps = new HashMap< String, Object >( );
            maps.put ( "password", EncryptUtil.getInstance().encryptMd532(edtOldPsd.getText().toString()));
            maps.put ( "newPassword", EncryptUtil.getInstance().encryptMd532(edtNewPwd.getText().toString()));
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progress.dismissView();
                    if (ChangePasswordActivity.this.isFinishing()) {
                        return;
                    }
                    JSONUtil<BaseModel> jsonUtil = new JSONUtil<BaseModel>();
                    BaseModel changepsd = new BaseModel();
                    changepsd = jsonUtil.toBean(response.toString(), changepsd);
                    if (1 == changepsd.getResultCode()) {

                        ToastUtils.showMomentToast(ChangePasswordActivity.this, ChangePasswordActivity.this, "修改成功");
                        ActivityUtils.getInstance().skipActivity(ChangePasswordActivity.this, UserSettingActivity.class);


                            }
                        else {
                            //未获取该用户信息
                            noticePop = new NoticePopWindow(ChangePasswordActivity.this, ChangePasswordActivity.this, wManager, "不合法的用户密码");
                            noticePop.showNotice();
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
                    noticePop = new NoticePopWindow(ChangePasswordActivity.this, ChangePasswordActivity.this, wManager, "修改密码失败");
                    noticePop.showNotice();
                    noticePop.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0
                    );
                }
            });
        }
    }
    @Override
    protected
    void onDestroy ( ) {

        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();

    }
    @Override
    public
    boolean onKeyDown ( int keyCode, KeyEvent event ) {

        if ( keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction ( ) == KeyEvent.ACTION_DOWN ) {
            //关闭
            this.closeSelf(ChangePasswordActivity.this);
        }
        return super.onKeyDown ( keyCode, event );
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

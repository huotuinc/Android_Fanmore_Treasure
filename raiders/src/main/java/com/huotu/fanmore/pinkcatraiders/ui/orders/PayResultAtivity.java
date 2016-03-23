package com.huotu.fanmore.pinkcatraiders.ui.orders;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.CartCountModel;
import com.huotu.fanmore.pinkcatraiders.model.UserOutputModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付结果
 */
public
class PayResultAtivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public Handler mHandler;

    public WindowManager wManager;

    public
    AssetManager am;

    public Resources resources;

    public BaseApplication application;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;

    @Bind(R.id.carryOnBtn)
    TextView carryOnBtn;
    @Bind(R.id.showBtn)
    TextView showBtn;
    @Bind(R.id.resultTag)
    TextView resultTag;
    @Bind(R.id.productName)
    TextView productName;
    @Bind(R.id.parentCount)
    TextView parentCount;
    @Bind(R.id.issue)
    TextView issue;
    @Bind(R.id.number)
    TextView number;


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
        setContentView ( R.layout.pay_result );
        ButterKnife.bind ( this );
        mHandler = new Handler ( this );
        am = this.getAssets ( );
        resources = this.getResources ( );
        application = ( BaseApplication ) this.getApplication ( );
        wManager = this.getWindowManager ( );
        initTitle ( );
        initData();
    }

    private void initData()
    {
        //左边
        ViewGroup.LayoutParams pl = carryOnBtn.getLayoutParams();
        pl.width = (wManager.getDefaultDisplay().getWidth()*3)/7;
        carryOnBtn.setLayoutParams(pl);
        //左边
        ViewGroup.LayoutParams pr = showBtn.getLayoutParams();
        pr.width = (wManager.getDefaultDisplay().getWidth()*3)/7;
        showBtn.setLayoutParams(pr);
        //结算刷新用户数据
        //刷新用户信息
        String url = Contant.REQUEST_URL + Contant.UPDATE_USER_INFORMATION;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), PayResultAtivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONUtil<UserOutputModel> jsonUtil = new JSONUtil<UserOutputModel>();
                UserOutputModel userOutput = new UserOutputModel();
                userOutput = jsonUtil.toBean(response.toString(), userOutput);

                if (null != userOutput && null != userOutput.getResultData() && null != userOutput.getResultData().getUser() && 1 == userOutput.getResultCode()) {
                    application.writeUserInfo(userOutput.getResultData().getUser());
                } else {
                    ToastUtils.showMomentToast(PayResultAtivity.this, PayResultAtivity.this, "刷新余额出现问题");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showMomentToast(PayResultAtivity.this, PayResultAtivity.this, "刷新余额出现问题");
            }
        });
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText ( "支付结果" );
    }

    @Override
    protected
    void onDestroy ( ) {

        super.onDestroy ( );
        VolleyUtil.cancelAllRequest ( );
        ButterKnife.unbind ( this );
    }

    @OnClick ( R.id.titleLeftImage )
    void doBack ( ) {

        closeSelf ( PayResultAtivity.this );
    }

    @Override
    public
    boolean onKeyDown ( int keyCode, KeyEvent event ) {

        if ( keyCode == KeyEvent.KEYCODE_BACK
             && event.getAction ( ) == KeyEvent.ACTION_DOWN ) {
            //关闭
            this.closeSelf ( PayResultAtivity.this );
        }
        return super.onKeyDown ( keyCode, event );
    }
}

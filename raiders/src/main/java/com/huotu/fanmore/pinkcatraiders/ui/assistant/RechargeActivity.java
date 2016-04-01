package com.huotu.fanmore.pinkcatraiders.ui.assistant;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.MoneyAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.model.PayOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RechargeOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.UserOutputModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.PayResultAtivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.PayFunc;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.MyGridView;
import com.huotu.fanmore.pinkcatraiders.widget.PayPopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值界面
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, MyBroadcastReceiver.BroadcastListener {

    public Handler       mHandler;

    public WindowManager wManager;

    public
    AssetManager am;

    public Resources       res;

    public BaseApplication application;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub       stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView      titleLeftImage;

    @Bind ( R.id.moneyGrid )
    MyGridView     moneyGrid;

    @Bind ( R.id.wxPayL )
    RelativeLayout wxPayL;

    @Bind ( R.id.moneyMethodIcon1 )
    ImageView      moneyMethodIcon1;

    @Bind ( R.id.aliPayL )
    RelativeLayout aliPayL;

    @Bind ( R.id.moneyMethodIcon2 )
    ImageView      moneyMethodIcon2;
    @Bind ( R.id.rechargeBtn )
    TextView rechargeBtn;

    public MoneyAdapter adapter;

    public
    ProgressPopupWindow progress;
    public List<Long> moneys;
    public long money = -1;
    public int payType = -1;
    private MyBroadcastReceiver myBroadcastReceiver;


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
        setContentView(R.layout.recharge);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        am = this.getAssets ( );
        res = this.getResources();
        application = ( BaseApplication ) this.getApplication ( );
        wManager = this.getWindowManager ( );
        myBroadcastReceiver = new MyBroadcastReceiver(RechargeActivity.this, RechargeActivity.this, MyBroadcastReceiver.ACTION_PAY_SUCCESS);
        initTitle ( );
        initView ( );
    }

    @OnClick(R.id.wxPayL)
    void selectWX()
    {
        SystemTools.loadBackground ( moneyMethodIcon1, res.getDrawable ( R.mipmap.money_select ) );
        SystemTools.loadBackground ( moneyMethodIcon2, res.getDrawable ( R.mipmap.unselect ) );
        payType = 0;

    }

    @OnClick(R.id.aliPayL)
    void selectAlipay()
    {
        SystemTools.loadBackground ( moneyMethodIcon1, res.getDrawable ( R.mipmap.unselect ) );
        SystemTools.loadBackground ( moneyMethodIcon2, res.getDrawable ( R.mipmap.money_select ) );
        payType = 1;
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf ( RechargeActivity.this );
    }

    private
    void initView ( ) {
        //重构充值面额
        String                url    = Contant.REQUEST_URL + Contant.GET_DEFAULT_PUT_MONEY_LIST;
        AuthParamUtils        params = new AuthParamUtils ( application, System.currentTimeMillis
                ( ), RechargeActivity.this );
        Map< String, Object > maps   = new HashMap< String, Object > ( );
        String                suffix = params.obtainGetParam ( maps );
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils ( );
        httpUtils.doVolleyGet (
                url, new Response.Listener< JSONObject > ( ) {

                    @Override
                    public
                    void onResponse ( JSONObject response ) {
                        if ( RechargeActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        JSONUtil< RechargeOutputModel > jsonUtil = new JSONUtil<
                                RechargeOutputModel > ( );
                        RechargeOutputModel rechargeOutput = new RechargeOutputModel
                                ( );
                        rechargeOutput = jsonUtil.toBean ( response.toString ( ), rechargeOutput );
                        if ( null != rechargeOutput && null != rechargeOutput.getResultData ( )
                             && ( 1 == rechargeOutput.getResultCode ( ) ) ) {
                            moneys = rechargeOutput.getResultData ().getList ();
                            adapter = new MoneyAdapter ( moneys, RechargeActivity.this,
                                                         RechargeActivity.this );
                            moneyGrid.setAdapter ( adapter );
                        }
                        else {
                            //异常处理，自动切换成无数据
                            ToastUtils.showMomentToast(RechargeActivity.this, RechargeActivity.this, "加载默认金额失败");
                        }
                    }
                }, new Response.ErrorListener ( ) {

                    @Override
                    public
                    void onErrorResponse ( VolleyError error ) {
                        if ( RechargeActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        ToastUtils.showMomentToast(RechargeActivity.this, RechargeActivity.this, "服务器未响应");
                    }
                }
                              );
        moneyGrid.setOnItemClickListener (
                new AdapterView.OnItemClickListener ( ) {

                    @Override
                    public
                    void onItemClick ( AdapterView< ? > parent, View view, int position, long id ) {

                        adapter.setSeclection ( position );
                        adapter.notifyDataSetChanged ( );
                        money = moneys.get ( position );
                    }
                }
                                         );
    }



    private void initTitle()
    {
        //背景色
        Drawable bgDraw = res.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = res.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate ( );
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText ( "充值" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ( );
        ButterKnife.unbind ( this );
        VolleyUtil.cancelAllRequest ( );
        if( null != myBroadcastReceiver)
        {
            myBroadcastReceiver.unregisterReceiver();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(RechargeActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.rechargeBtn)
    void doRecharge()
    {
        if(-1 == money)
        {
            ToastUtils.showMomentToast(RechargeActivity.this, RechargeActivity.this, "请选择充值的面额" );
            return;
        }
        else if(-1 == payType)
        {
            ToastUtils.showMomentToast(RechargeActivity.this, RechargeActivity.this, "请选择支付方式");
            return;
        }
        else
        {
            progress = new ProgressPopupWindow ( RechargeActivity.this, RechargeActivity.this, wManager );
            progress.showProgress ( "正在提交支付信息" );
            progress.showAtLocation ( rechargeBtn, Gravity.CENTER, 0, 0 );
            String                url    = Contant.REQUEST_URL + Contant.PUT_MONEY;
            AuthParamUtils        params = new AuthParamUtils ( application, System.currentTimeMillis
                    ( ), RechargeActivity.this );
            Map< String, Object > maps   = new HashMap< String, Object > ( );
            maps.put ( "money", String.valueOf ( money ) );
            maps.put ( "payType", String.valueOf ( payType ) );
            String                suffix = params.obtainGetParam ( maps );
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils ( );
            httpUtils.doVolleyGet (
                    url, new Response.Listener< JSONObject > ( ) {

                        @Override
                        public
                        void onResponse ( JSONObject response ) {
                            progress.dismissView ();

                            if ( RechargeActivity.this.isFinishing ( ) ) {
                                return;
                            }
                            JSONUtil< PayOutputModel > jsonUtil = new JSONUtil<
                                    PayOutputModel > ( );
                            PayOutputModel payOutput = new PayOutputModel
                                    ( );
                            payOutput = jsonUtil.toBean ( response.toString ( ), payOutput );
                            if ( null != payOutput && null != payOutput.getResultData ( )
                                 && ( 1 == payOutput.getResultCode ( ) ) ) {
                                PayModel payModel = payOutput.getResultData().getData();
                                //payType:0微信 1支付宝
                                if(0==payType)
                                {
                                    progress.showProgress ( "等待微信支付跳转" );
                                    progress.showAtLocation (
                                            RechargeActivity.this.findViewById ( R.id.titleText ),
                                            Gravity.CENTER, 0, 0
                                    );
                                    //微信支付
                                    payModel.setAttach ( payModel.getOrderNo ( ) + "_0" );
                                    //添加微信回调路径
                                    PayFunc payFunc = new PayFunc ( RechargeActivity.this, payModel, application, mHandler, RechargeActivity.this, progress );
                                    payFunc.wxPay ( );
                                }
                                else if(1==payType)
                                {
                                    //支付宝支付
                                    progress.showProgress("等待支付宝支付跳转");
                                    progress.showAtLocation(
                                            RechargeActivity.this.findViewById(R.id.titleText),
                                            Gravity.CENTER, 0, 0
                                    );
                                    PayFunc payFunc = new PayFunc ( RechargeActivity.this, payModel, application, mHandler, RechargeActivity.this, progress );
                                    payFunc.aliPay();
                                }
                                else
                                {
                                    //无效支付
                                    ToastUtils.showMomentToast(RechargeActivity.this, RechargeActivity.this, "无效支付信息");
                                }
                            }
                            else {
                                //异常处理，自动切换成无数据
                                ToastUtils.showMomentToast(RechargeActivity.this, RechargeActivity.this, "提交支付信息失败");
                            }
                        }
                    }, new Response.ErrorListener ( ) {

                        @Override
                        public
                        void onErrorResponse ( VolleyError error ) {
                            progress.dismissView ();

                            if ( RechargeActivity.this.isFinishing ( ) ) {
                                return;
                            }
                            ToastUtils.showMomentToast(RechargeActivity.this, RechargeActivity.this, "服务器未响应");
                        }
                    }
                                  );

        }

    }

    @Override
    public void onFinishReceiver(MyBroadcastReceiver.ReceiverType type, Object msg) {
        if(type == MyBroadcastReceiver.ReceiverType.wxPaySuccess)
        {
            //结算刷新用户数据
            //跳转到首页
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            MyBroadcastReceiver.sendBroadcast(RechargeActivity.this, MyBroadcastReceiver.JUMP_CART, bundle);
            closeSelf(RechargeActivity.this);
        }
    }
}

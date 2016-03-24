package com.huotu.fanmore.pinkcatraiders.ui.orders;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
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
import com.huotu.fanmore.pinkcatraiders.model.BaseBalanceModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.CartCountModel;
import com.huotu.fanmore.pinkcatraiders.model.OrderDetailOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.model.PayOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.UserOutputModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.PayFunc;
import com.huotu.fanmore.pinkcatraiders.uitls.PreferenceHelper;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付订单
 */
public
class PayOrderActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, MyBroadcastReceiver.BroadcastListener {


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
    @Bind(R.id.redPackageMoney)
    TextView redPackageMoney;
    @Bind ( R.id.totalMoney )
    TextView totalMoney;
    @Bind ( R.id.balance )
    TextView balance;
    @Bind ( R.id.money )
    TextView money;
    @Bind ( R.id.rechargeBtn )
    TextView rechargeBtn;
    @Bind(R.id.moneyMethodIcon1)
    ImageView moneyMethodIcon1;
    @Bind(R.id.moneyMethodIcon2)
    ImageView moneyMethodIcon2;
    @Bind(R.id.moneyMethodIcon3)
    ImageView moneyMethodIcon3;
    public Bundle bundle;
    public BaseBalanceModel balance1;

    public
    ProgressPopupWindow progress;
    public List<Long> moneys;
    public long moneyTag = -1;
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
        setContentView(R.layout.pay_order);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        am = this.getAssets ( );
        resources = this.getResources();
        application = ( BaseApplication ) this.getApplication ( );
        wManager = this.getWindowManager();
        bundle = this.getIntent().getExtras();
        balance1 = (BaseBalanceModel) bundle.getSerializable("baseBalance");
        myBroadcastReceiver = new MyBroadcastReceiver(PayOrderActivity.this, PayOrderActivity.this, MyBroadcastReceiver.ACTION_PAY_SUCCESS);
        initTitle ( );
        initData();
    }

    private void initData()
    {
        totalMoney.setText(String.valueOf(balance1.getTotalMoney()));
        redPackageMoney.setText(null==balance1.getRedPacketsMinusMoney() || (0==balance1.getRedPacketsMinusMoney().compareTo(BigDecimal.ZERO))?"无可使用红包":balance1.getRedPacketsMinusMoney()+"元");
        String balanceStr = PreferenceHelper.readString(PayOrderActivity.this, Contant.LOGIN_USER_INFO, Contant.LOGIN_AUTH_MONEY);
        balance.setText("余额支付(余额：" + ((null != balanceStr && !balanceStr.isEmpty() && !"null".equals(balanceStr)) ? balanceStr : 0) + "元)");
        money.setText(balance1.getMoney() + "元");
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("支付订单");
    }

    @OnClick(R.id.wxPayL)
    void selectWX()
    {
        SystemTools.loadBackground(moneyMethodIcon1, resources.getDrawable(R.mipmap.money_select));
        SystemTools.loadBackground(moneyMethodIcon2, resources.getDrawable(R.mipmap.unselect));
        SystemTools.loadBackground(moneyMethodIcon3, resources.getDrawable(R.mipmap.unselect));
        payType = 0;

    }

    @OnClick(R.id.aliPayL)
    void selectAlipay()
    {
        SystemTools.loadBackground ( moneyMethodIcon1, resources.getDrawable ( R.mipmap.unselect ) );
        SystemTools.loadBackground(moneyMethodIcon2, resources.getDrawable(R.mipmap.money_select));
        SystemTools.loadBackground(moneyMethodIcon3, resources.getDrawable(R.mipmap.unselect));
        payType = 1;
    }

    @OnClick(R.id.balanceL)
    void selectbalance()
    {
        SystemTools.loadBackground(moneyMethodIcon1, resources.getDrawable(R.mipmap.unselect));
        SystemTools.loadBackground(moneyMethodIcon2, resources.getDrawable(R.mipmap.unselect));
        SystemTools.loadBackground(moneyMethodIcon3, resources.getDrawable(R.mipmap.money_select));
        payType = 2;

    }

    @Override
    protected
    void onDestroy ( ) {

        super.onDestroy ( );
        VolleyUtil.cancelAllRequest ( );
        ButterKnife.unbind ( this );
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf ( PayOrderActivity.this );
    }

    @OnClick(R.id.rechargeBtn)
    void doPay()
    {
        moneyTag = balance1.getMoney().longValue();
        if(-1 == moneyTag || 0==moneyTag)
        {
            ToastUtils.showMomentToast(PayOrderActivity.this, PayOrderActivity.this, "购买金额为空");
            return;
        }
        else if(-1 == payType)
        {
            ToastUtils.showMomentToast(PayOrderActivity.this, PayOrderActivity.this, "请选择支付方式");
            return;
        }
        else if(2==payType)
        {
            //余额支付
            progress = new ProgressPopupWindow ( PayOrderActivity.this, PayOrderActivity.this, wManager );
            progress.showProgress ( "正在提交支付信息" );
            progress.showAtLocation ( rechargeBtn, Gravity.CENTER, 0, 0 );
            String                url    = Contant.REQUEST_URL + Contant.PAY;
            AuthParamUtils params = new AuthParamUtils ( application, System.currentTimeMillis
                    ( ), PayOrderActivity.this );
            Map< String, Object > maps   = new HashMap< String, Object >( );
            maps.put ( "money", String.valueOf ( moneyTag ) );
            maps.put ( "payType", String.valueOf ( payType ) );
            maps.put ( "redPacketsId", String.valueOf ( balance1.getRedPacketsId() ) );
            maps.put("allPay", "1");
            Map<String, Object> param = params.obtainPostParam(maps);
            PayOutputModel payOutput = new PayOutputModel();
            HttpUtils<PayOutputModel> httpUtils = new HttpUtils<PayOutputModel> ();
            httpUtils.doVolleyPost(payOutput, url, param, new Response.Listener<PayOutputModel> ( ) {

                @Override
                public void onResponse(PayOutputModel response) {
                    progress.dismissView ();
                    if ( PayOrderActivity.this.isFinishing ( ) ) {
                        return;
                    }
                    PayOutputModel payOutput = response;
                    if(1==payOutput.getResultCode())
                    {
                        ToastUtils.showMomentToast(PayOrderActivity.this, PayOrderActivity.this, "余额支付成功, 2秒后关闭订单");
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //修改购物车数量
                                CartCountModel cartCountIt = CartCountModel.findById(CartCountModel.class, 0l);
                                if(null==cartCountIt)
                                {
                                    CartCountModel cartCount = new CartCountModel();
                                    cartCount.setId(0l);
                                    cartCount.setCount(0);
                                    CartCountModel.save(cartCount);
                                }
                                else
                                {

                                    cartCountIt.setCount(0);
                                    CartCountModel.save(cartCountIt);
                                }
                                //关闭支付订单界面
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", 0);
                                MyBroadcastReceiver.sendBroadcast(PayOrderActivity.this, MyBroadcastReceiver.SHOP_CART, bundle);
                                closeSelf(PayOrderActivity.this);
                            }
                        }, 1000);
                    }
                    else
                    {
                        ToastUtils.showMomentToast(PayOrderActivity.this, PayOrderActivity.this, "余额支付失败");
                    }

                }
            }, new Response.ErrorListener ( ) {

                @Override
                public
                void onErrorResponse ( VolleyError error ) {
                    progress.dismissView ();

                    if ( PayOrderActivity.this.isFinishing ( ) ) {
                        return;
                    }
                    ToastUtils.showMomentToast(PayOrderActivity.this, PayOrderActivity.this, "余额支付失败");
                }
            });
        }
        else
        {
            progress = new ProgressPopupWindow ( PayOrderActivity.this, PayOrderActivity.this, wManager );
            progress.showProgress("正在提交支付信息");
            progress.showAtLocation(rechargeBtn, Gravity.CENTER, 0, 0);
            String                url    = Contant.REQUEST_URL + Contant.PAY;
            AuthParamUtils params = new AuthParamUtils ( application, System.currentTimeMillis
                    ( ), PayOrderActivity.this );
            Map< String, Object > maps   = new HashMap< String, Object >( );
            maps.put ( "money", String.valueOf ( moneyTag ) );
            maps.put ( "payType", String.valueOf ( payType ) );
            maps.put("allPay", "1");
            maps.put ( "redPacketsId", String.valueOf ( balance1.getRedPacketsId() ) );
            Map<String, Object> param = params.obtainPostParam(maps);
            PayOutputModel payOutput = new PayOutputModel();
            HttpUtils<PayOutputModel> httpUtils = new HttpUtils<PayOutputModel> ();
            httpUtils.doVolleyPost (
                    payOutput, url, param, new Response.Listener<PayOutputModel> ( ) {

                        @Override
                        public
                        void onResponse ( PayOutputModel response ) {
                            progress.dismissView ();

                            if ( PayOrderActivity.this.isFinishing ( ) ) {
                                return;
                            }
                            PayOutputModel payOutput = response;
                            if ( null != payOutput && null != payOutput.getResultData ( )
                                    && ( 1 == payOutput.getResultCode ( ) ) ) {
                                //修改购物车数量
                                CartCountModel cartCountIt = CartCountModel.findById(CartCountModel.class, 0l);
                                if(null==cartCountIt)
                                {
                                    CartCountModel cartCount = new CartCountModel();
                                    cartCount.setId(0l);
                                    cartCount.setCount(0);
                                    CartCountModel.save(cartCount);
                                }
                                else
                                {

                                    cartCountIt.setCount(0);
                                    CartCountModel.save(cartCountIt);
                                }
                                PayModel payModel = payOutput.getResultData().getData();
                                //payType:0微信 1支付宝
                                if(0==payType)
                                {
                                    progress.showProgress ( "等待微信支付跳转" );
                                    progress.showAtLocation (
                                            PayOrderActivity.this.findViewById ( R.id.titleText ),
                                            Gravity.CENTER, 0, 0
                                    );
                                    //微信支付
                                    payModel.setAttach ( payModel.getOrderNo ( ) + "_0" );
                                    //添加微信回调路径
                                    PayFunc payFunc = new PayFunc ( PayOrderActivity.this, payModel, application, mHandler, PayOrderActivity.this, progress );
                                    payFunc.wxPay ( );
                                }
                                else if(1==payType)
                                {
                                    //支付宝支付
                                    progress.showProgress("等待支付宝支付跳转");
                                    progress.showAtLocation(
                                            PayOrderActivity.this.findViewById(R.id.titleText),
                                            Gravity.CENTER, 0, 0
                                    );
                                    PayFunc payFunc = new PayFunc ( PayOrderActivity.this, payModel, application, mHandler, PayOrderActivity.this, progress );
                                    payFunc.aliPay();
                                }
                                else
                                {
                                    //无效支付
                                    ToastUtils.showMomentToast(PayOrderActivity.this, PayOrderActivity.this, "无效支付信息");
                                }
                            }
                            else {
                                //异常处理，自动切换成无数据
                                ToastUtils.showMomentToast(PayOrderActivity.this, PayOrderActivity.this, "提交支付信息失败");
                            }
                        }
                    }, new Response.ErrorListener ( ) {

                        @Override
                        public
                        void onErrorResponse ( VolleyError error ) {
                            progress.dismissView ();

                            if ( PayOrderActivity.this.isFinishing ( ) ) {
                                return;
                            }
                            ToastUtils.showMomentToast(PayOrderActivity.this, PayOrderActivity.this, "提交支付信息失败");
                        }
                    }
            );

        }

    }

    @Override
    public
    boolean onKeyDown ( int keyCode, KeyEvent event ) {

        if ( keyCode == KeyEvent.KEYCODE_BACK
             && event.getAction ( ) == KeyEvent.ACTION_DOWN ) {
            //关闭
            this.closeSelf ( PayOrderActivity.this );
        }
        return super.onKeyDown ( keyCode, event );
    }

    @Override
    public void onFinishReceiver(MyBroadcastReceiver.ReceiverType type, Object msg) {
        if(type == MyBroadcastReceiver.ReceiverType.wxPaySuccess)
        {
            //跳转到成功界面
            //跳转到首页
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            MyBroadcastReceiver.sendBroadcast(PayOrderActivity.this, MyBroadcastReceiver.JUMP_CART, bundle);
            closeSelf(PayOrderActivity.this);
        }
    }
}

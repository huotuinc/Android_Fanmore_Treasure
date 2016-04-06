package cy.com.morefan.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付
 */

public
class WXPayEntryActivity extends Activity implements Handler.Callback, IWXAPIEventHandler {

    private Handler handler;
    private IWXAPI api;
    private
    BaseApplication application;
    TextView orderPayTag;

    @Override
    public
    boolean handleMessage ( Message msg ) {

        switch ( msg.what){
            case Contant.PAY_ERROR:
            {
                ToastUtils.showLongToast(this, msg.obj.toString());
                this.finish();
            }
            break;
            case Contant.PAY_OK:
            {
                ToastUtils.showLongToast(this, msg.obj.toString());
                this.finish();
            }
            break;
        }
        return false;
    }

    @Override
    public
    void onReq ( BaseReq baseReq ) {
    }

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result_sys);
        handler = new Handler(this);
        orderPayTag = (TextView) WXPayEntryActivity.this.findViewById(R.id.orderPayTag);
        application = ( BaseApplication ) this.getApplication ();
        api = WXAPIFactory.createWXAPI ( this, Contant.WXPAY_ID );
        api.handleIntent ( getIntent ( ), this );
    }

    @Override
    public
    void onResp ( BaseResp resp ) {


        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            PayResp payResp = (PayResp)resp;
            if(null==payResp){
                orderPayTag.setText("订单支付失败，2秒后关闭");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WXPayEntryActivity.this.finish();
                    }
                }, 2000);
                return;
            }
            else
            {
                if( payResp.errCode== 0)
                {
                    orderPayTag.setText("订单支付成功，2秒后跳转到首页");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            WXPayEntryActivity.this.finish();
                        }
                    }, 2000);
                    MyBroadcastReceiver.sendBroadcast(this, MyBroadcastReceiver.ACTION_PAY_SUCCESS);
                    return;
                }else if( payResp.errCode== -1){
                    orderPayTag.setText("订单支付失败，2秒后关闭");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            WXPayEntryActivity.this.finish();
                        }
                    }, 2000);
                    return;
                }else if(payResp.errCode ==-2){
                    orderPayTag.setText("订单支付失败，2秒后关闭");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            WXPayEntryActivity.this.finish();
                        }
                    }, 2000);
                    return;
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 2秒以内按两次推出程序
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
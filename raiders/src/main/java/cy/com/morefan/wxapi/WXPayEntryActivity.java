package cy.com.morefan.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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

    private       Handler handler    = new Handler ( this );
    private IWXAPI api;
    private
    BaseApplication application;

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
                //MyBroadcastReceiver.sendBroadcast ( this, MyBroadcastReceiver.ACTION_FLOW_ADD );
                //MyBroadcastReceiver.sendBroadcast(this,MyBroadcastReceiver.ACTION_WX_PAY_CALLBACK);
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
        setContentView ( R.layout.pay_result_sys );
        application = ( BaseApplication ) this.getApplication ();
        api = WXAPIFactory.createWXAPI ( this, Contant.WXPAY_ID );
        api.handleIntent ( getIntent ( ), this );
    }

    @Override
    public
    void onResp ( BaseResp resp ) {


        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String msg = "";
            if( resp.errCode== 0)
            {
                msg="支付成功";
                MyBroadcastReceiver.sendBroadcast(this, MyBroadcastReceiver.ACTION_PAY_SUCCESS);
                this.finish();
                ToastUtils.showLongToast ( WXPayEntryActivity.this, msg );
                return;
            }else if( resp.errCode== -1){
                msg="支付失败";
                this.finish();
                ToastUtils.showLongToast ( WXPayEntryActivity.this, msg );
                return;
            }else if(resp.errCode ==-2){
                msg="用户取消支付";
                this.finish();
                ToastUtils.showLongToast(WXPayEntryActivity.this, msg);
                return;
            }

            PayResp payResp = (PayResp)resp;
            if(null==payResp){
                msg="支付失败";
                ToastUtils.showLongToast(WXPayEntryActivity.this, msg);
                this.finish();
                return;
            }else{
                //Log.i("wxpay>>>prepayid",payResp.prepayId);
            }

            /*PayGoodBean para=new PayGoodBean ();
            JSONUtil<PayGoodBean> jsonUtil=new JSONUtil<PayGoodBean>();
            para = jsonUtil.toBean( payResp.extData, para);

            new DeliveryGoodAsyncTask ( WXPayEntryActivity.this , handler ,  para.getOrderNo(),para.getProductType(), para.getProductId() ).execute();*/
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
}
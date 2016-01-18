package cy.com.morefan.wxapi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 微信支付
 */

public
class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

    private
    IWXAPI api;

    @Override
    public
    void onReq ( BaseReq baseReq ) {

    }

    @Override
    public
    void onResp ( BaseResp baseResp ) {

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_tip);
            builder.setMessage(getString( R.string.pay_result_callback_msg, String.valueOf(baseResp.errCode)));
            builder.show();
        }
    }

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.pay_result );
        api = WXAPIFactory.createWXAPI(this, Contant.WXPAY_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected
    void onNewIntent ( Intent intent ) {
        super.onNewIntent ( intent );
        setIntent(intent);
        api.handleIntent(intent, this);
    }

}

package com.huotu.fanmore.pinkcatraiders.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/1/22.
 */
public class MobileRegActivity extends BaseActivity implements Handler.Callback,View.OnClickListener {

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

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_code:
            {
                ToastUtils.showShortToast(this,"adsadsd");
            }
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
}

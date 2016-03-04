package com.huotu.fanmore.pinkcatraiders.ui.redpackage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;

/**
 * 红包接口
 */
public class ReadPackageActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}

package com.huotu.fanmore.pinkcatraiders.ui.mall;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import butterknife.ButterKnife;

/**
 * 商城首页入口
 */
public class MallHomeActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    //application引用
    public BaseApplication application;

    //handler对象
    public Handler mHandler;

    //windows类
    public
    WindowManager wManager;

    public
    AssetManager am;

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
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(MallHomeActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}

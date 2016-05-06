package com.huotu.fanmore.pinkcatraiders.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * activity的基类
 */
public class BaseActivity extends FragmentActivity implements Response.ErrorListener  {

    public BaseApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (BaseApplication) BaseActivity.this.getApplication();
        //禁止横屏
        BaseActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void setImmerseLayout(View view)
    {
        if (application.isKITKAT ()) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = this.getStatusBarHeight ( this.getBaseContext ( ) );
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    public int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void closeSelf(Activity aty)
    {
        aty.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //finish自身
            BaseActivity.this.finish();
            return true;
        }
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }

    public boolean canConnect(){
        //网络访问前先检测网络是否可用
        if(!application.checkNet(BaseActivity.this)){
            ToastUtils.showLongToast(BaseActivity.this, "网络连接失败");
            return false;
        }
        return true;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {



            String message="";
            if( volleyError instanceof TimeoutError){
                message = "网络连接超时";
            }else if( volleyError instanceof NetworkError || volleyError instanceof NoConnectionError) {
                message ="网络请求异常，请检查网络状态";
            }else if( volleyError instanceof ParseError){
                message = "数据解析失败，请检测数据的正确性";
            }else if( volleyError instanceof ServerError || volleyError instanceof AuthFailureError){
                if( null != volleyError.networkResponse){
                    message=new String( volleyError.networkResponse.data);
                }else{
                    message = volleyError.getMessage();
                }
            }

            if( message.length()<1){
                message = "网络请求失败，请检查网络状态";
            }

            //DialogUtils.showDialog(BaseFragmentActivity.this, BaseFragmentActivity.this.getSupportFragmentManager(), "错误信息", message, "关闭");
        }


}

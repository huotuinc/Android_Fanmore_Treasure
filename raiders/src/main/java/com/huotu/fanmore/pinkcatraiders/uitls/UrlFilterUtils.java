package com.huotu.fanmore.pinkcatraiders.uitls;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

/**
 * Created by lenovo on 2016/3/24.
 */
public class UrlFilterUtils {


    private
    Context context;
    private
    Activity aty;
    private Handler mHandler;
    private
    BaseApplication application;
    //windows类
    private WindowManager wManager;
    public
    ProgressPopupWindow payProgress;

    public
    UrlFilterUtils (
            Activity aty, Context context, Handler mHandler,
            BaseApplication application, WindowManager wManager
    ) {
        this.context = context;
        this.mHandler = mHandler;
        this.application = application;
        this.aty = aty;
        this.wManager = wManager;
        payProgress = new ProgressPopupWindow ( context, aty, wManager );
    }

    /**
     * webview拦截url作相应的处理
     * @param view
     * @param url
     * @return
     */
    public
    boolean shouldOverrideUrlBySFriend ( WebView view, String url ) {
            //跳转到新界面
            view.loadUrl(url);
            return false;
    }
}

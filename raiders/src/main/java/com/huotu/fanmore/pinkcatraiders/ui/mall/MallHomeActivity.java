package com.huotu.fanmore.pinkcatraiders.ui.mall;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商城首页入口
 */
public class MallHomeActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    //application引用
    public BaseApplication application;

    //handler对象
    public Handler mHandler;
    public
    Resources resources;

    //windows类
    public
    WindowManager wManager;

    public
    AssetManager am;
    public ProgressPopupWindow progress;
    public
    NoticePopWindow noticePop;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.underwebView)
     WebView underwebView;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.webPage)
    PullToRefreshWebView webPage;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    public Bundle bundle;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what)
        {
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.mall_home_webview);
        ButterKnife.bind(this);
        mHandler = new Handler( this );
        resources = this.getResources();
        application = (BaseApplication) this.getApplication();
        wManager = this.getWindowManager();
        bundle = this.getIntent().getExtras();
        progressBar.setMax(100);
        initTitle();
        initWebPage();
    }
    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("商城");
    }

    private void initWebPage()
    {
        webPage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {
            @Override
            public void onRefresh(PullToRefreshBase<WebView> pullToRefreshBase) {
                loadPage();
            }
        });
        loadPage();
    }

    private void loadPage()
    {
        WebView viewPage = webPage.getRefreshableView();
        viewPage.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        viewPage.setVerticalScrollBarEnabled(false);
        viewPage.setClickable(true);
        viewPage.getSettings().setUseWideViewPort(true);
        //是否需要避免页面放大缩小操作
        underwebView.getSettings().setJavaScriptEnabled(true);
        underwebView.getSettings().setDomStorageEnabled(true);

        underwebView.getSettings().setUseWideViewPort(true);
        underwebView.getSettings().setLoadWithOverviewMode(true);
        viewPage.getSettings().setSupportZoom(true);
        viewPage.getSettings().setBuiltInZoomControls(true);
        viewPage.getSettings().setJavaScriptEnabled(true);
        viewPage.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        viewPage.getSettings().setSaveFormData(true);
        viewPage.getSettings().setAllowFileAccess(true);
        viewPage.getSettings().setLoadWithOverviewMode(false);
        viewPage.getSettings().setSavePassword(true);
        viewPage.getSettings().setLoadsImagesAutomatically(true);
        viewPage.loadUrl(bundle.getString("url"));
        underwebView.loadUrl(bundle.getString("bottomurl"));
        //底部webview控制器

        viewPage.setWebViewClient(
                new WebViewClient() {

                }


        );

        viewPage.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (100 == newProgress) {
                    webPage.onRefreshComplete();
                }
                super.onProgressChanged(view, newProgress);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
    }
    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        this.closeSelf(MallHomeActivity.this);
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

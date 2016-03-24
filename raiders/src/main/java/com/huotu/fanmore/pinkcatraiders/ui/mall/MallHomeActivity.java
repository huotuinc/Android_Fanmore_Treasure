package com.huotu.fanmore.pinkcatraiders.ui.mall;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.webkit.ValueCallback;
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
import com.huotu.fanmore.pinkcatraiders.model.MallPayModel;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.UrlFilterUtils;
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
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.underwebView)
    WebView underwebView;
    private TextView titleText;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.webPage)
    PullToRefreshWebView webPage;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    public Bundle bundle;
    public WebView viewPage;
    public static ValueCallback< Uri > mUploadMessage;
    public static final int FILECHOOSER_RESULTCODE = 1;

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what)
        {
            case 0x12131422:
            {
                MallPayModel payModel = ( MallPayModel ) msg.obj;
                //调用JS
                viewPage.loadUrl ( "javascript:utils.Go2Payment("+payModel.getCustomId ()+","+ payModel.getTradeNo ()+","+ payModel.getPaymentType ()+", "
                        + "false);\n" );
            }
                break;
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
        viewPage = webPage.getRefreshableView();
        progressBar.setMax(100);
        initTitle();
        loadMainMenu();
        initWebPage();
    }

    private
    void loadMainMenu ( )
    {

        underwebView.getSettings().setJavaScriptEnabled(true);
        underwebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //首页默认为商户站点 + index
        underwebView.loadUrl(bundle.getString("bottomurl"));

        underwebView.setWebViewClient(
                new WebViewClient() {

                    //重写此方法，浏览器内部跳转
                    public boolean shouldOverrideUrlLoading(
                            WebView view, String
                            url
                    ) {
                        viewPage.loadUrl(url);
                        return true;
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {

                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                    }
                }
        );
    }


    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("商城");
    }

    private void initWebPage()
    {
        loadPage();
        webPage.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<WebView>() {
            @Override
            public void onRefresh(PullToRefreshBase<WebView> pullToRefreshBase) {
                loadPage();
            }
        });
    }

    private void loadPage()
    {
        viewPage.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        viewPage.setVerticalScrollBarEnabled(false);
        viewPage.setClickable(true);
        viewPage.getSettings().setUseWideViewPort(true);
        //是否需要避免页面放大缩小操作

        viewPage.getSettings().setSupportZoom(true);
        viewPage.getSettings().setBuiltInZoomControls(true);
        viewPage.getSettings().setJavaScriptEnabled(true);
        viewPage.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        viewPage.getSettings().setSaveFormData(true);
        viewPage.getSettings().setAllowFileAccess(true);
        viewPage.getSettings().setLoadWithOverviewMode(false);
        viewPage.getSettings().setSavePassword(true);
        viewPage.getSettings().setLoadsImagesAutomatically(true);
        viewPage.getSettings().setDomStorageEnabled(true);
        viewPage.loadUrl(bundle.getString("url"));

        viewPage.setWebViewClient(
                new WebViewClient() {

                    //重写此方法，浏览器内部跳转
                    public boolean shouldOverrideUrlLoading(
                            WebView view, String
                            url
                    ) {
                        UrlFilterUtils filter = new UrlFilterUtils(
                                MallHomeActivity.this,
                                MallHomeActivity.this,
                                titleText,
                                mHandler,
                                application,
                                wManager,
                                bundle.getString("orderurl")
                        );
                        return filter.shouldOverrideUrlBySFriend(viewPage, url);
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        //页面加载完成后,读取菜单项
                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onReceivedError(
                            WebView view, int errorCode, String description,
                            String failingUrl
                    )
                    {
                        super.onReceivedError(view, errorCode, description, failingUrl);
                    }

                }
        );

        viewPage.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if(100 == newProgress)
                {
                    webPage.onRefreshComplete();
                    progressBar.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                MallHomeActivity.mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                MallHomeActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), MallHomeActivity.FILECHOOSER_RESULTCODE);
            }

            public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
                openFileChooser(uploadMsg);
            }

            //For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){

                openFileChooser(uploadMsg);

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

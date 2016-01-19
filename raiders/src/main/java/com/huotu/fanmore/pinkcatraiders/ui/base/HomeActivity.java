package com.huotu.fanmore.pinkcatraiders.ui.base;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.MorePopWin;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;
import com.huotu.fanmore.pinkcatraiders.widget.SharePopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity implements Handler.Callback {

    //获取资源对象
    public
    Resources resources;
    private long exitTime = 0l;
    //application引用
    public BaseApplication application;

    //handler对象
    public Handler mHandler;

    //windows类
    public
    WindowManager wManager;
    public
    AssetManager am;
    public MorePopWin morePopWin;
    public ProgressPopupWindow progress;
    public
    NoticePopWindow noticePop;
    private SharePopupWindow share;
    //title
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.titleRightImage)
    ImageView titleRightImage;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.stubSearchBar)
    ViewStub stubSearchBar;
    //bottom
    @Bind(R.id.onBuyL)
    RelativeLayout onBuyL;
    @Bind(R.id.newestL)
    RelativeLayout newestL;
    @Bind(R.id.listL)
    RelativeLayout listL;
    @Bind(R.id.profileL)
    RelativeLayout profileL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_home);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        //设置沉浸模式
        setImmerseLayout(this.findViewById(R.id.titleLayoutL));
        wManager = this.getWindowManager();
        am = this.getAssets();
        //初始化title面板
        initTitle();
        //
        initBottom();
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.color.title_bg);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.title_setting_white);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        Drawable rightDraw = resources.getDrawable(R.mipmap.title_more_white);
        SystemTools.loadBackground(titleRightImage, rightDraw);
        stubSearchBar.inflate();
        EditText searchL = (EditText) this.findViewById(R.id.titleSearchBar);
    }

    private void initBottom()
    {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 2秒以内按两次推出程序
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if ((System.currentTimeMillis() - exitTime) > 2000)
            {
                ToastUtils.showLongToast(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
                // 切出菜单界面
                // layDrag.openDrawer(Gravity.LEFT);
            } else
            {
                try
                {
                    // 默认登出
                    HomeActivity.this.finish();
                    Runtime.getRuntime().exit(0);
                } catch (Exception e)
                {
                    Runtime.getRuntime().exit(-1);
                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}

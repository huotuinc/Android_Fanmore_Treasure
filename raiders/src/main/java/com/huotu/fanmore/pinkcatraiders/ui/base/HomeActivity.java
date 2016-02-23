package com.huotu.fanmore.pinkcatraiders.ui.base;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.MyGridAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.fragment.HomeFragment;
import com.huotu.fanmore.pinkcatraiders.fragment.ListFragment;
import com.huotu.fanmore.pinkcatraiders.fragment.NewestFragment;
import com.huotu.fanmore.pinkcatraiders.fragment.ProfileFragment;

import com.huotu.fanmore.pinkcatraiders.ui.login.LoginActivity;

import com.huotu.fanmore.pinkcatraiders.model.ProductModel;

import com.huotu.fanmore.pinkcatraiders.ui.raiders.UserSettingActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.FuncPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.MorePopWin;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;
import com.huotu.fanmore.pinkcatraiders.widget.SharePopupWindow;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity implements Handler.Callback, View.OnClickListener {

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
    public FuncPopWin funcPopWin;
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
    @Bind(R.id.oneBuy)
    ImageView oneBuy;
    @Bind(R.id.obBuyLabel)
    TextView obBuyLabel;
    @Bind(R.id.newest)
    ImageView newest;
    @Bind(R.id.newestLabel)
    TextView newestLabel;
    @Bind(R.id.list)
    ImageView list;
    @Bind(R.id.listLabel)
    TextView listLabel;
    @Bind(R.id.profile)
    ImageView profile;
    @Bind(R.id.profileLabel)
    TextView profileLabel;
    @Bind(R.id.homeBottom)
    LinearLayout homeBottom;

    //加载首页产品列表适配器
    //人气
    public MyGridAdapter popAdapter;
    public List<ProductModel> popProducts;
    //最新
    public MyGridAdapter newestAdapter;
    public List<ProductModel> newestProducts;
    //进度
    public MyGridAdapter progressAdapter;
    public List<ProductModel> progressProducts;
    //总需
    public MyGridAdapter totalAdapter;
    public List<ProductModel> totalProducts;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_home);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        application.mFragManager = FragManager.getIns(this, R.id.fragment_container);
        resources = this.getResources();
        mHandler = new Handler ( this );
        //设置沉浸模式
        setImmerseLayout(this.findViewById(R.id.titleLayoutL));
        wManager = this.getWindowManager();
        funcPopWin = new FuncPopWin(HomeActivity.this, HomeActivity.this, wManager);
        am = this.getAssets();
        //初始化title面板
        initTitle();
        if (null == savedInstanceState)
        {
            application.mFragManager.setCurrentFrag(FragManager.FragType.HOME);

        } else
        {
            application.mFragManager.setPreFragType(FragManager.FragType.HOME);
            FragManager.FragType curFragType = (FragManager.FragType) savedInstanceState
                    .getSerializable("curFragType");
            application.mFragManager.setCurrentFrag(FragManager.FragType.HOME);
        }
        initView();
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.color.title_bg);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.title_setting_white);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        Drawable rightDraw = resources.getDrawable(R.mipmap.title_msg);
        SystemTools.loadBackground ( titleRightImage, rightDraw );
        stubSearchBar.inflate ( );
        EditText searchL = (EditText) this.findViewById(R.id.titleSearchBar);
    }

    @OnClick (R.id.titleLeftImage)
    void doSetting()
    {
        ActivityUtils.getInstance ( ).showActivity ( HomeActivity.this, UserSettingActivity.class );
    }

    private void initView() {
        initTab();

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putSerializable("curFragType",
                application.mFragManager.getCurrentFragType());
        // TODO Auto-generated method stub
        super.onSaveInstanceState(savedInstanceState);
    }

    private void initTab()
    {
        Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_press);
        SystemTools.loadBackground(oneBuy, oneBuyDraw);
        obBuyLabel.setTextColor(resources.getColor(R.color.title_bg));
        //重置其他
        Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
        SystemTools.loadBackground(newest, newestDraw);
        newestLabel.setTextColor(resources.getColor(R.color.text_black));
        Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_normal);
        SystemTools.loadBackground(list, listDraw);
        listLabel.setTextColor(resources.getColor(R.color.text_black));
        Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
        SystemTools.loadBackground(profile, profileDraw);
        profileLabel.setTextColor(resources.getColor(R.color.text_black));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
    }

    public void onTabClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.onBuyL:
            {
                //设置选中状态
                Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_press);
                SystemTools.loadBackground(oneBuy, oneBuyDraw);
                obBuyLabel.setTextColor(resources.getColor(R.color.title_bg));
                //重置其他
                Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
                SystemTools.loadBackground(newest, newestDraw);
                newestLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_normal);
                SystemTools.loadBackground(list, listDraw);
                listLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
                SystemTools.loadBackground(profile, profileDraw);
                profileLabel.setTextColor(resources.getColor(R.color.text_black));
                funcPopWin.dismissView();
                //切换内容
                String tag = Contant.TAG_1;
                //加载具体的页面
                Message msg = mHandler.obtainMessage(Contant.SWITCH_UI, tag);
                mHandler.sendMessage(msg );
            }
            break;
            case R.id.newestL:
            {
                //设置选中状态
                Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_normal);
                SystemTools.loadBackground(oneBuy, oneBuyDraw);
                obBuyLabel.setTextColor(resources.getColor(R.color.text_black));
                //重置其他
                Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_press);
                SystemTools.loadBackground(newest, newestDraw);
                newestLabel.setTextColor(resources.getColor(R.color.title_bg));
                Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_normal);
                SystemTools.loadBackground(list, listDraw);
                listLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
                SystemTools.loadBackground(profile, profileDraw);
                profileLabel.setTextColor(resources.getColor(R.color.text_black));
                funcPopWin.dismissView();
                //切换内容
                String tag = Contant.TAG_2;
                //加载具体的页面
                Message msg = mHandler.obtainMessage(Contant.SWITCH_UI, tag);
                mHandler.sendMessage(msg);
            }
            break;
            case R.id.listL:
            {
                //设置选中状态
                Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_normal);
                SystemTools.loadBackground(oneBuy, oneBuyDraw);
                obBuyLabel.setTextColor(resources.getColor(R.color.text_black));
                //重置其他
                Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
                SystemTools.loadBackground(newest, newestDraw);
                newestLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_press);
                SystemTools.loadBackground(list, listDraw);
                listLabel.setTextColor(resources.getColor(R.color.title_bg));
                Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
                SystemTools.loadBackground(profile, profileDraw);
                profileLabel.setTextColor(resources.getColor(R.color.text_black));
                //显示清单操作弹出框
                funcPopWin.showLayout();
                funcPopWin.showAsDropDown(homeBottom, 0, -(2*(int)resources.getDimension(R.dimen.bottom_height)));
                //切换内容
                String tag = Contant.TAG_3;
                //加载具体的页面
                Message msg = mHandler.obtainMessage(Contant.SWITCH_UI, tag);
                mHandler.sendMessage(msg);
            }
            break;
            case R.id.profileL:
            {
                //设置选中状态
//                if (application.isLogin()==false){
//                    Intent intent = new Intent();
//                    intent.setClass(HomeActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }else {
                    Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_normal);
                    SystemTools.loadBackground(oneBuy, oneBuyDraw);
                    obBuyLabel.setTextColor(resources.getColor(R.color.text_black));
                    //重置其他
                    Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
                    SystemTools.loadBackground(newest, newestDraw);
                    newestLabel.setTextColor(resources.getColor(R.color.text_black));
                    Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_normal);
                    SystemTools.loadBackground(list, listDraw);
                    listLabel.setTextColor(resources.getColor(R.color.text_black));
                    Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_press);
                    SystemTools.loadBackground(profile, profileDraw);
                    profileLabel.setTextColor(resources.getColor(R.color.title_bg));
                    funcPopWin.dismissView();
                    //切换内容
                    String tag = Contant.TAG_4;
                    //加载具体的页面
                    Message msg = mHandler.obtainMessage(Contant.SWITCH_UI, tag);
                    mHandler.sendMessage(msg);
 //               }
            }
            break;
            default:
                break;
        }
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
        switch (msg.what) {
            case Contant.SWITCH_UI:
            {
                String tag = msg.obj.toString ();
                if(tag.equals(Contant.TAG_1))
                {
                    application.mFragManager.setCurrentFrag(FragManager.FragType.HOME);
                }
                else if(tag.equals(Contant.TAG_2))
                {
                    application.mFragManager.setCurrentFrag(FragManager.FragType.NEWEST);
                }
                else if(tag.equals(Contant.TAG_3))
                {
                    application.mFragManager.setCurrentFrag(FragManager.FragType.LIST);
                }
                else if(tag.equals(Contant.TAG_4))
                {
                    application.mFragManager.setCurrentFrag(FragManager.FragType.PROFILE);
                }
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
}

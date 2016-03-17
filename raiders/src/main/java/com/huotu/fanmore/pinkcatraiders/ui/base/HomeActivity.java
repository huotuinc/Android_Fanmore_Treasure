package com.huotu.fanmore.pinkcatraiders.ui.base;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.ListAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.NewestGridAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.PopGridAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.ProgressGridAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.TotalGridAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.fragment.HomeFragment;
import com.huotu.fanmore.pinkcatraiders.fragment.ListFragment;
import com.huotu.fanmore.pinkcatraiders.fragment.NewestFragment;
import com.huotu.fanmore.pinkcatraiders.fragment.ProfileFragment;

import com.huotu.fanmore.pinkcatraiders.model.AppBalanceModel;
import com.huotu.fanmore.pinkcatraiders.model.BalanceOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseBalanceModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.CarouselModel;
import com.huotu.fanmore.pinkcatraiders.model.CartCountModel;
import com.huotu.fanmore.pinkcatraiders.model.CartDataModel;
import com.huotu.fanmore.pinkcatraiders.model.CartModel;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;

import com.huotu.fanmore.pinkcatraiders.model.OutputUrlModel;

import com.huotu.fanmore.pinkcatraiders.model.LocalCartOutputModel;

import com.huotu.fanmore.pinkcatraiders.model.ProductDetailsOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.SlideDetailOutputModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.MsgActivity;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.SearchActivity;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.WebExhibitionActivity;
import com.huotu.fanmore.pinkcatraiders.ui.login.LoginActivity;

import com.huotu.fanmore.pinkcatraiders.model.ProductModel;

import com.huotu.fanmore.pinkcatraiders.ui.mall.MallHomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.ConfirmOrderActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.PayOrderActivity;
import com.huotu.fanmore.pinkcatraiders.ui.product.ProductDetailActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.UserSettingActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.CartUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;
import com.huotu.fanmore.pinkcatraiders.widget.FunPopWin1;
import com.huotu.fanmore.pinkcatraiders.widget.FuncPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.GuideLoginPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.MorePopWin;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;
import com.huotu.fanmore.pinkcatraiders.widget.SharePopupWindow;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity implements Handler.Callback, View.OnClickListener, MyBroadcastReceiver.BroadcastListener {

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
    AssetManager  am;

    public MorePopWin          morePopWin;

    public ProgressPopupWindow progress;

    public
    NoticePopWindow noticePop;

    private SharePopupWindow share;

    public  FuncPopWin       funcPopWin;
    public
    FunPopWin1 funcPopWin1;

    //title
    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.titleLeftImage )
    ImageView      titleLeftImage;

    @Bind ( R.id.titleRightImage )
    ImageView      titleRightImage;

    @Bind ( R.id.stubTitleText )
    ViewStub       stubTitleText;

    @Bind ( R.id.stubSearchBar )
    ViewStub       stubSearchBar;

    //bottom
    @Bind ( R.id.onBuyL )
    RelativeLayout onBuyL;

    @Bind ( R.id.newestL )
    RelativeLayout newestL;

    @Bind ( R.id.listL )
    RelativeLayout listL;
    @Bind ( R.id.mallL )
    RelativeLayout mallL;

    @Bind ( R.id.profileL )
    RelativeLayout profileL;

    @Bind ( R.id.oneBuy )
    ImageView      oneBuy;

    @Bind ( R.id.obBuyLabel )
    TextView       obBuyLabel;

    @Bind ( R.id.newest )
    ImageView      newest;

    @Bind ( R.id.newestLabel )
    TextView       newestLabel;

    @Bind ( R.id.list )
    ImageView      list;

    @Bind ( R.id.listLabel )
    TextView       listLabel;

    @Bind ( R.id.mall )
    ImageView      mall;

    @Bind ( R.id.mallLabel )
    TextView       mallLabel;

    @Bind ( R.id.profile )
    ImageView      profile;

    @Bind ( R.id.profileLabel )
    TextView       profileLabel;
    @Bind(R.id.titleMsgAmount)
    TextView titleMsgAmount;

    @Bind ( R.id.homeBottom )
    LinearLayout   homeBottom;

    //加载首页产品列表适配器
    //人气
    public PopGridAdapter popAdapter;

    public List< ProductModel > popProducts;

    //最新
    public NewestGridAdapter newestAdapter;

    public List< ProductModel > newestProducts;

    //进度
    public ProgressGridAdapter progressAdapter;

    public List< ProductModel > progressProducts;

    //总需
    public TotalGridAdapter totalAdapter;

    public List< ProductModel > totalProducts;

    public int label = 0;
    //清单选中删除的数量
    //清单结算数量
    public long payAllNum = 0;
    //清单结算总金额
    public long payAllAmount = 0;
    //购物车删除ID列表
    public double prices = 0;
    public int payNum = 0;

    private MyBroadcastReceiver myBroadcastReceiver;

    public GuideLoginPopWin guideLoginPopWin;

    @Override
    protected
    void onResume ( ) {

        super.onResume ( );
    }

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_home);
        ButterKnife.bind(this);
        application = ( BaseApplication ) this.getApplication ( );
        application.mFragManager = FragManager.getIns(this, R.id.fragment_container);
        resources = this.getResources();
        mHandler = new Handler ( this );
        myBroadcastReceiver = new MyBroadcastReceiver(HomeActivity.this, this, MyBroadcastReceiver.JUMP_CART);
        //设置沉浸模式
        setImmerseLayout ( this.findViewById ( R.id.titleLayoutL ) );
        wManager = this.getWindowManager ( );
        funcPopWin = new FuncPopWin ( HomeActivity.this, HomeActivity.this, wManager, mHandler  );
        funcPopWin1 = new FunPopWin1 ( HomeActivity.this, HomeActivity.this, wManager, mHandler );
        am = this.getAssets ( );
        //初始化title面板
        initTitle ( );
        if ( null == savedInstanceState ) {
            application.mFragManager.setCurrentFrag ( FragManager.FragType.HOME );

        }
        else {
            application.mFragManager.setPreFragType ( FragManager.FragType.HOME );
            FragManager.FragType curFragType = ( FragManager.FragType ) savedInstanceState
                    .getSerializable ( "curFragType" );
            application.mFragManager.setCurrentFrag ( FragManager.FragType.HOME );
        }
        initView ( );
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.color.title_bg );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable ( R.mipmap.title_search );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        titleMsgAmount.setVisibility(View.VISIBLE);
        titleMsgAmount.setText("0");
        //消息模式
        titleRightImage.setTag(0);
        Drawable rightDraw = resources.getDrawable ( R.mipmap.title_msg );
        SystemTools.loadBackground(titleRightImage, rightDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setTextColor(resources.getColor(R.color.color_white));
        titleText.setText("奇兵夺宝");
    }

    @OnClick (R.id.titleLeftImage)
    void doSetting()
    {
        ActivityUtils.getInstance ( ).showActivity (
                HomeActivity.this,
                SearchActivity.class );
    }

    @OnClick (R.id.titleRightImage)
    void showMsg()
    {
        int tag = ( int ) titleRightImage.getTag ();
        if(0 == tag)
        {
            ActivityUtils.getInstance ( ).showActivity ( HomeActivity.this, MsgActivity.class );
        }
        else if(1 == tag)
        {
            //切换编辑模式
            //隐藏
            if(0 == label)
            {
                SystemTools.loadBackground ( titleRightImage, resources.getDrawable ( R.mipmap.title_cancel ) );
                label = 1;
                //显示清单操作弹出框
                funcPopWin1.dismissView();
                funcPopWin.showLayout();
                funcPopWin.showAsDropDown(homeBottom, 0, -(2 * (int) resources.getDimension(R.dimen.bottom_height)));
                //改变列表状态-编辑模式
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                MyBroadcastReceiver.sendBroadcast(this, MyBroadcastReceiver.SHOP_CART, bundle);
            }
            else if(1 == label)
            {
                SystemTools.loadBackground ( titleRightImage, resources.getDrawable ( R.mipmap.title_edit ) );
                label = 0;
                funcPopWin.dismissView();
                funcPopWin1.showLayout();
                funcPopWin1.showAsDropDown(homeBottom, 0, -(2 * (int) resources.getDimension(R.dimen.bottom_height)));
                //改变列表状态-结算模式
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                MyBroadcastReceiver.sendBroadcast(this, MyBroadcastReceiver.SHOP_CART, bundle);
            }
        }

    }

    private void initView() {
        initTab();

    }



    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        /*savedInstanceState.putSerializable("curFragType",
                application.mFragManager.getCurrentFragType());*/
        // TODO Auto-generated method stub
        super.onSaveInstanceState(savedInstanceState);
    }

    private void initTab()
    {
        titleMsgAmount.setVisibility(View.VISIBLE);
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
        Drawable mallDraw = resources.getDrawable(R.mipmap.mall_icon_common);
        SystemTools.loadBackground(mall, mallDraw);
        mallLabel.setTextColor(resources.getColor(R.color.text_black));
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
                titleMsgAmount.setVisibility(View.VISIBLE);
                //设置选中状态
                Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_press);
                SystemTools.loadBackground(oneBuy, oneBuyDraw);
                obBuyLabel.setTextColor(resources.getColor(R.color.title_bg));
                //标题栏右图标
                //消息模式
                titleRightImage.setTag(0);
                Drawable rightDraw = resources.getDrawable(R.mipmap.title_msg);
                SystemTools.loadBackground(titleRightImage, rightDraw);
                //重置其他
                Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
                SystemTools.loadBackground(newest, newestDraw);
                newestLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_normal);
                SystemTools.loadBackground(list, listDraw);
                listLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
                SystemTools.loadBackground(profile, profileDraw);
                Drawable mallDraw = resources.getDrawable(R.mipmap.mall_icon_common);
                SystemTools.loadBackground(mall, mallDraw);
                mallLabel.setTextColor(resources.getColor(R.color.text_black));
                profileLabel.setTextColor(resources.getColor(R.color.text_black));
                funcPopWin1.dismissView();
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
                titleMsgAmount.setVisibility(View.VISIBLE);
                //设置选中状态
                Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_normal);
                SystemTools.loadBackground(oneBuy, oneBuyDraw);
                obBuyLabel.setTextColor(resources.getColor(R.color.text_black));
                //标题栏右图标
                //消息模式
                titleRightImage.setTag ( 0 );
                Drawable rightDraw = resources.getDrawable(R.mipmap.title_msg);
                SystemTools.loadBackground(titleRightImage, rightDraw);
                //重置其他
                Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_press);
                SystemTools.loadBackground(newest, newestDraw);
                newestLabel.setTextColor(resources.getColor(R.color.title_bg));
                Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_normal);
                SystemTools.loadBackground(list, listDraw);
                listLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable mallDraw = resources.getDrawable(R.mipmap.mall_icon_common);
                SystemTools.loadBackground(mall, mallDraw);
                mallLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
                SystemTools.loadBackground(profile, profileDraw);
                profileLabel.setTextColor(resources.getColor(R.color.text_black));
                funcPopWin1.dismissView();
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
                titleMsgAmount.setVisibility(View.GONE);
                //设置选中状态
                Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_normal );
                SystemTools.loadBackground(oneBuy, oneBuyDraw);
                obBuyLabel.setTextColor(resources.getColor(R.color.text_black));
                //标题栏右图标
                //编辑模式
                titleRightImage.setTag ( 1 );
                Drawable rightDraw = resources.getDrawable(R.mipmap.title_edit);
                SystemTools.loadBackground(titleRightImage, rightDraw);
                //重置其他
                Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
                SystemTools.loadBackground(newest, newestDraw);
                newestLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_press);
                SystemTools.loadBackground(list, listDraw);
                listLabel.setTextColor(resources.getColor(R.color.title_bg));
                Drawable mallDraw = resources.getDrawable(R.mipmap.mall_icon_common);
                SystemTools.loadBackground(mall, mallDraw);
                mallLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
                SystemTools.loadBackground(profile, profileDraw);
                profileLabel.setTextColor(resources.getColor(R.color.text_black));

                //显示清单操作弹出框
                funcPopWin1.showLayout();
                funcPopWin1.showAsDropDown(homeBottom, 0, -(2 * (int) resources.getDimension(R.dimen.bottom_height)));
                //切换内容
                String tag = Contant.TAG_3;
                //加载具体的页面
                Message msg = mHandler.obtainMessage(Contant.SWITCH_UI, tag);
                mHandler.sendMessage(msg);
                //广播确认每次都是结算界面，并且下拉刷新
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                MyBroadcastReceiver.sendBroadcast(this, MyBroadcastReceiver.SHOP_CART, bundle);
            }
            break;
            case R.id.mallL:
            {
                titleMsgAmount.setVisibility(View.VISIBLE);
                //设置选中状态
                Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_normal );
                SystemTools.loadBackground(oneBuy, oneBuyDraw);
                obBuyLabel.setTextColor(resources.getColor(R.color.text_black));
                //标题栏右图标
                //编辑模式
                titleRightImage.setTag ( 0 );
                Drawable rightDraw = resources.getDrawable(R.mipmap.title_msg);
                SystemTools.loadBackground(titleRightImage, rightDraw);
                //重置其他
                Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
                SystemTools.loadBackground(newest, newestDraw);
                newestLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_normal);
                SystemTools.loadBackground(list, listDraw);
                listLabel.setTextColor(resources.getColor(R.color.text_black));
                Drawable mallDraw = resources.getDrawable(R.mipmap.mall_icon_clicked);
                SystemTools.loadBackground(mall, mallDraw);
                mallLabel.setTextColor(resources.getColor(R.color.title_bg));
                Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
                SystemTools.loadBackground(profile, profileDraw);
                profileLabel.setTextColor(resources.getColor(R.color.text_black));

                //显示清单操作弹出框
                funcPopWin1.dismissView();
                funcPopWin.dismissView();
                //切换内容
                String tag = Contant.TAG_5;
                //加载具体的页面
                Message msg = mHandler.obtainMessage(Contant.SWITCH_UI, tag);
                mHandler.sendMessage(msg);
            }
            break;
            case R.id.profileL:
            {
                titleMsgAmount.setVisibility(View.VISIBLE);
                //设置选中状态
                if (application.isLogin()==false){
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_normal);
                    SystemTools.loadBackground(oneBuy, oneBuyDraw);
                    obBuyLabel.setTextColor(resources.getColor(R.color.text_black));
                    //标题栏右图标
                    //消息模式
                    titleRightImage.setTag(0);
                    Drawable rightDraw = resources.getDrawable(R.mipmap.title_msg);
                    SystemTools.loadBackground(titleRightImage, rightDraw);
                    //重置其他
                    Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
                    SystemTools.loadBackground(newest, newestDraw);
                    newestLabel.setTextColor(resources.getColor(R.color.text_black));
                    Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_normal);
                    SystemTools.loadBackground(list, listDraw);
                    listLabel.setTextColor(resources.getColor(R.color.text_black));
                    Drawable mallDraw = resources.getDrawable(R.mipmap.mall_icon_common);
                    SystemTools.loadBackground(mall, mallDraw);
                    mallLabel.setTextColor(resources.getColor(R.color.text_black));
                    Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_press);
                    SystemTools.loadBackground(profile, profileDraw);
                    profileLabel.setTextColor(resources.getColor(R.color.title_bg));
                    funcPopWin1.dismissView();
                    funcPopWin.dismissView();
                    //切换内容
                    String tag = Contant.TAG_4;
                    //加载具体的页面
                    Message msg = mHandler.obtainMessage(Contant.SWITCH_UI, tag);
                    mHandler.sendMessage(msg);
             }
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
            case Contant.SWITCH_UI: {

                String tag = msg.obj.toString();
                if (tag.equals(Contant.TAG_1)) {
                    application.mFragManager.setCurrentFrag(FragManager.FragType.HOME);
                } else if (tag.equals(Contant.TAG_2)) {
                    application.mFragManager.setCurrentFrag(FragManager.FragType.NEWEST);
                } else if (tag.equals(Contant.TAG_3)) {
                    application.mFragManager.setCurrentFrag(FragManager.FragType.LIST);
                } else if (tag.equals(Contant.TAG_4)) {
                    application.mFragManager.setCurrentFrag(FragManager.FragType.PROFILE);
                } else if (tag.equals(Contant.TAG_5)) {


                    //判断是否登陆
                    if(!application.isLogin())
                    {

                        guideLoginPopWin = new GuideLoginPopWin(HomeActivity.this, mHandler, HomeActivity.this, wManager);
                        guideLoginPopWin.showWin();
                        guideLoginPopWin.showAtLocation(
                                findViewById(R.id.titleLayout),
                                Gravity.CENTER, 0, 0
                        );

                    } else {

                        String url = Contant.REQUEST_URL + Contant.GETMALLURL;
                        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), HomeActivity.this);
                        //中文字符特殊处理
                        //1 拼装参数
                        Map<String, Object> maps = new HashMap<String, Object>();
                        String suffix = params.obtainGetParam(maps);
                        url = url + suffix;
                        HttpUtils httpUtils = new HttpUtils();
                        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (HomeActivity.this.isFinishing()) {
                                    return;
                                }
                                JSONUtil<OutputUrlModel> jsonUtil = new JSONUtil<OutputUrlModel>();
                                OutputUrlModel getmallurl = new OutputUrlModel();
                                getmallurl = jsonUtil.toBean(response.toString(), getmallurl);
                                if (1 == getmallurl.getResultCode() && getmallurl.getResultData() != null) {

                                    try {
                                        String loginUrl = getmallurl.getResultData().getLoginUrl();
                                        String bottomUrl = getmallurl.getResultData().getBottomNavUrl();
                                        String orderUrl = getmallurl.getResultData().getOrderRequestUrl();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("url", loginUrl);
                                        bundle.putString("bottomurl", bottomUrl);
                                        bundle.putString("orderurl", orderUrl);
                                        ActivityUtils.getInstance().showActivity(HomeActivity.this, MallHomeActivity.class, bundle);
                                    } catch (Exception e) {
                                        //未获取该用户信息
                                        noticePop = new NoticePopWindow(HomeActivity.this, HomeActivity.this, wManager, "用户数据存在非法字符");
                                        noticePop.showNotice();
                                        noticePop.showAtLocation(titleLayoutL,
                                                Gravity.CENTER, 0, 0
                                        );

                                    }
                                } else {
                                    //未获取该用户信息
                                    noticePop = new NoticePopWindow(HomeActivity.this, HomeActivity.this, wManager, "未获取该用户信息");
                                    noticePop.showNotice();
                                    noticePop.showAtLocation(titleLayoutL,
                                            Gravity.CENTER, 0, 0
                                    );
                                }

                            }


                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progress.dismissView();
                                //初始化失败
                                //异常处理，自动切换成无数据
                                noticePop = new NoticePopWindow(HomeActivity.this, HomeActivity.this, wManager, "登录失败");
                                noticePop.showNotice();
                                noticePop.showAtLocation(titleLayoutL,
                                        Gravity.CENTER, 0, 0
                                );
                            }
                        });


                    }

                    }

            }
            break;
            case Contant.CAROUSE_URL:
            {
                CarouselModel model = ( CarouselModel ) msg.obj;
                if(model.getGoodsId()>0)
                {
                    //直接跳转到goods详情
                    final ProductModel productModel = new ProductModel();
                    productModel.setPid(model.getGoodsId());
                    String url = Contant.REQUEST_URL + Contant.GET_GOODS_DTAIL_BY_GOODS_ID;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), HomeActivity.this);
                    Map<String, Object> maps = new HashMap<String, Object>();
                    //商品id
                    maps.put("goodsId", productModel.getPid());
                    String suffix = params.obtainGetParam(maps);
                    url = url + suffix;
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (HomeActivity.this.isFinishing()) {
                                        return;
                                    }
                                    JSONUtil<ProductDetailsOutputModel> jsonUtil = new
                                            JSONUtil<ProductDetailsOutputModel>();
                                    ProductDetailsOutputModel productDetailsOutput = new
                                            ProductDetailsOutputModel();
                                    productDetailsOutput = jsonUtil.toBean(
                                            response.toString(),
                                            productDetailsOutput
                                    );
                                    if (null != productDetailsOutput && null != productDetailsOutput
                                            .getResultData() && (
                                            1 == productDetailsOutput.getResultCode(
                                            )
                                    )) {
                                        productModel.setImgs(productDetailsOutput.getResultData().getData().getPictureUrl());
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("tip", 1);
                                        bundle.putSerializable("product", productModel);
                                        ActivityUtils.getInstance().showActivity(HomeActivity.this, ProductDetailActivity.class, bundle);

                                    } else {
                                        //异常处理，自动切换成无数据
                                        ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "无效商品无法打开");
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "无效商品无法打开");
                                    //暂无数据提示

                                }
                            }
                    );
                }
                else
                {
                    if(null==model.getLink() || model.getLink().isEmpty())
                    {
                        ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "商品链接无效");
                    }
                    else {
                        Bundle bundle = new Bundle ( );
                        bundle.putString ( "title", "详情信息" );
                        bundle.putString ( "link", model.getLink() );
                        ActivityUtils.getInstance ().showActivity ( HomeActivity.this, WebExhibitionActivity.class, bundle );
                    }
                }
            }
            break;
            case Contant.CART_SELECT:
            {
                if(0==msg.arg1)
                {
                    //结算模式
                    prices = 0;

                    List<ListModel> lists = (List<ListModel>) msg.obj;
                    Iterator<ListModel> it = lists.iterator();
                    payNum = lists.size();
                    while (it.hasNext())
                    {
                        ListModel list = it.next();
                        double price = list.getPricePercentAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        double total = price*list.getUserBuyAmount();
                        prices+=total;
                    }

                    funcPopWin1.setMsg(String.valueOf(payNum), String.valueOf(prices));
                    funcPopWin1.setData(lists);
                }
                else if(2==msg.arg1)
                {
                    //结算模式 加
                    prices=0;
                    List<ListModel> lists = (List<ListModel>) msg.obj;
                    Iterator<ListModel> it = lists.iterator();
                    payNum = lists.size();
                    while (it.hasNext())
                    {
                        ListModel list = it.next();
                        double price = list.getPricePercentAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        double total = price*list.getUserBuyAmount();
                        prices+=total;
                    }

                    funcPopWin1.setMsg(String.valueOf(payNum), String.valueOf(prices));
                    funcPopWin1.setData(lists);
                }
                else if(3==msg.arg1)
                {
                    //结算模式 减
                    prices=0;
                    List<ListModel> lists = (List<ListModel>) msg.obj;
                    Iterator<ListModel> it = lists.iterator();
                    payNum = lists.size();
                    while (it.hasNext())
                    {
                        ListModel list = it.next();
                        double price = list.getPricePercentAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        double total = price*list.getUserBuyAmount();
                        prices+=total;
                    }

                    funcPopWin1.setMsg(String.valueOf(payNum), String.valueOf(prices));
                    funcPopWin1.setData(lists);
                }
                else if(1==msg.arg1)
                {
                    //购物车删除ID列表
                    List<Long> deleteIds = new ArrayList<Long>();
                    int deleteAllNum =0;
                    //编辑模式
                    List<ListModel> lists = (List<ListModel>) msg.obj;
                    for(int i=0; i<lists.size(); i++)
                    {
                        if(lists.get(i).isSelect())
                        {
                            deleteAllNum++;
                            deleteIds.add(lists.get(i).getSid());
                        }
                    }
                    if(deleteIds.size()==lists.size())
                    {
                        //设置全选
                        funcPopWin.setSelectAll();
                    }
                    else

                    {
                        //取消全选
                        funcPopWin.setUNSelectAll();
                    }
                    funcPopWin.setMsg ( String.valueOf ( deleteAllNum ) );
                    funcPopWin.setDeletes(deleteIds);

                }
                else if(4==msg.arg1)
                {
                    //全部选
                    funcPopWin.setMsg(String.valueOf(0));
                    funcPopWin.setDeletes(null);
                }
                else if(5==msg.arg1)
                {
                    List<Long> deleteIds = new ArrayList<Long>();
                    //全部选
                    List<ListModel> ls = (List<ListModel>) msg.obj;
                    funcPopWin.setMsg(String.valueOf(ls.size()));
                    for(int i=0; i<ls.size(); i++)
                    {
                        deleteIds.add(ls.get(i).getSid());
                    }
                    funcPopWin.setDeletes(deleteIds);
                }

            }
            break;
            case Contant.ADD_LIST:
            {
                ProductModel product = ( ProductModel ) msg.obj;
                progress = new ProgressPopupWindow ( HomeActivity.this, HomeActivity.this, wManager );
                progress.showProgress ( "正在添加清单" );
                progress.showAtLocation(titleLayoutL,
                        Gravity.CENTER, 0, 0
                );
                CartUtils.addCartDone(product, String.valueOf(product.getIssueId()), progress, application, HomeActivity.this, mHandler);
            }
            break;
            case Contant.BILLING:
            {
                List<CartBalanceModel> list = new ArrayList<CartBalanceModel>();
                List<ListModel> datas = (List<ListModel>) msg.obj;
                if(0==payNum||0==prices)
                {
                    ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "购物车为空");
                }
                else if(null==datas || datas.isEmpty())
                {
                    ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "购物车为空");
                }
                else
                {
                    if(!application.isLogin())
                    {
                        //未登录状态
                        //跳转到登陆
                        CartDataModel cartData = CartDataModel.findById(CartDataModel.class, 1000l);
                        String cartStr = cartData.getCartData();
                        guideLoginPopWin = new GuideLoginPopWin(HomeActivity.this, mHandler, HomeActivity.this, wManager);
                        guideLoginPopWin.setLoginData(cartStr);
                        guideLoginPopWin.showWin();
                        guideLoginPopWin.showAtLocation(
                                findViewById(R.id.titleLayout),
                                Gravity.CENTER, 0, 0
                        );
                    }
                    else
                    {
                        Iterator<ListModel> it = datas.iterator();
                        while (it.hasNext())
                        {
                            ListModel listModel = it.next();
                            CartBalanceModel cartBalanceModel = new CartBalanceModel();
                            cartBalanceModel.setPid(listModel.getSid());
                            cartBalanceModel.setBuyAmount(listModel.getUserBuyAmount());
                            list.add(cartBalanceModel);
                        }
                        //转成json格式参数
                        Gson gson = new Gson();
                        String carts = gson.toJson(list);
                        String url = Contant.REQUEST_URL + Contant.BALANCE;
                        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), HomeActivity.this);
                        Map<String, Object> maps = new HashMap<String, Object> ();
                        maps.put ( "carts",  carts);
                        Map<String, Object> param = params.obtainPostParam(maps);
                        BalanceOutputModel base = new BalanceOutputModel ();
                        HttpUtils<BalanceOutputModel> httpUtils = new HttpUtils<BalanceOutputModel> ();
                        httpUtils.doVolleyPost(
                                base, url, param, new Response.Listener<BalanceOutputModel>() {
                                    @Override
                                    public void onResponse(BalanceOutputModel response) {
                                        BalanceOutputModel base = response;
                                        if (1 == base.getResultCode() && null != base.getResultData() && null != base.getResultData().getData()) {
                                            AppBalanceModel balance = base.getResultData().getData();
                                            BaseBalanceModel baseBalance = new BaseBalanceModel();
                                            baseBalance.setMoney(balance.getMoney());
                                            baseBalance.setRedPacketsEndTime(balance.getRedPacketsEndTime());
                                            baseBalance.setRedPacketsFullMoney(balance.getRedPacketsFullMoney());
                                            baseBalance.setRedPacketsId(balance.getRedPacketsId());
                                            baseBalance.setRedPacketsMinusMoney(balance.getRedPacketsMinusMoney());
                                            baseBalance.setRedPacketsNumber(balance.getRedPacketsNumber());
                                            baseBalance.setRedPacketsRemark(balance.getRedPacketsRemark());
                                            baseBalance.setRedPacketsStartTime(balance.getRedPacketsStartTime());
                                            baseBalance.setRedPacketsStatus(null==balance.getRedPacketsStatus()?null:balance.getRedPacketsStatus().getName());
                                            baseBalance.setTotalMoney(balance.getTotalMoney());
                                            baseBalance.setRedPacketsTitle(balance.getRedPacketsTitle());
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("baseBalance", baseBalance);
                                            ActivityUtils.getInstance ().showActivity(HomeActivity.this, PayOrderActivity.class, bundle );
                                        } else {
                                            progress.dismissView();
                                            VolleyUtil.cancelAllRequest();
                                            //上传失败
                                            noticePop = new NoticePopWindow(HomeActivity.this, HomeActivity.this, wManager, "结算失败");
                                            noticePop.showNotice();
                                            noticePop.showAtLocation(
                                                    findViewById(R.id.titleLayout),
                                                    Gravity.CENTER, 0, 0
                                            );
                                        }
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progress.dismissView();
                                        VolleyUtil.cancelAllRequest();
                                        //系统级别错误
                                        noticePop = new NoticePopWindow(HomeActivity.this, HomeActivity.this, wManager, "结算失败");
                                        noticePop.showNotice();
                                        noticePop.showAtLocation(
                                                findViewById(R.id.titleLayout),
                                                Gravity.CENTER, 0, 0
                                        );
                                    }
                                }
                        );
                    }
                }
            }
            break;
            case Contant.LIST_DELETE:
            {
                if(1==msg.arg1)
                {
                    ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "未选择删除的商品");
                }
                else if(0==msg.arg1)
                {
                    //弹出确认框
                    if(!application.isLogin())
                    {
                        progress = new ProgressPopupWindow ( HomeActivity.this, HomeActivity.this, wManager );
                        progress.showProgress ( "正在删除选中的商品" );
                        progress.showAtLocation (titleLayoutL,
                                Gravity.CENTER, 0, 0
                        );
                        final List<Long> deletes = (List<Long>) msg.obj;
                        //未登录状态
                        CartDataModel cartData = CartDataModel.findById(CartDataModel.class, 1000l);
                        //转换成JSON
                        String data = cartData.getCartData();
                        JSONUtil<LocalCartOutputModel> jsonUtil = new JSONUtil<LocalCartOutputModel>();
                        LocalCartOutputModel localCartOutput = new LocalCartOutputModel();
                        localCartOutput = jsonUtil.toBean(data, localCartOutput);
                        List<ListModel> lists = localCartOutput.getResultData().getLists();
                        List<ListModel> removes = new ArrayList<ListModel>();
                        for(int j=0; j<deletes.size(); j++)
                        {
                            for(int i=0; i<lists.size(); i++)
                            {
                                if(deletes.get(j) == lists.get(i).getSid())
                                {
                                    removes.add(lists.get(i));
                                }
                            }
                        }
                        lists.removeAll(removes);
                        progress.dismissView();
                        ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "删除成功");
                        //全选按钮置空
                        funcPopWin.setUNSelectAll();
                        localCartOutput.getResultData().setLists(lists);
                        String str = jsonUtil.toJson(localCartOutput);
                        cartData.setCartData(str);
                        CartDataModel.save(cartData);
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 1);
                        MyBroadcastReceiver.sendBroadcast(this, MyBroadcastReceiver.SHOP_CART, bundle);
                    }
                    else
                    {
                        //删除清单数据
                        progress = new ProgressPopupWindow ( HomeActivity.this, HomeActivity.this, wManager );
                        progress.showProgress ( "正在删除选中的商品" );
                        progress.showAtLocation (titleLayoutL,
                                Gravity.CENTER, 0, 0
                        );
                        final List<Long> deletes = (List<Long>) msg.obj;
                        for(int i=0; i<deletes.size(); i++)
                        {
                            Long chartId = deletes.get(i);
                            String url = Contant.REQUEST_URL + Contant.DELETE_CART;
                            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), HomeActivity.this);
                            Map<String, Object> maps = new HashMap<String, Object> ();
                            maps.put ( "shoppingCartId",  String.valueOf(chartId));
                            Map<String, Object> param = params.obtainPostParam(maps);
                            BaseModel base = new BaseModel ();
                            HttpUtils<BaseModel> httpUtils = new HttpUtils<BaseModel> ();
                            httpUtils.doVolleyPost(
                                    base, url, param, new Response.Listener<BaseModel>() {
                                        @Override
                                        public void onResponse(BaseModel response) {
                                            BaseModel base = response;
                                            if (1 == base.getResultCode()) {
                                                CartCountModel cartCountIt = CartCountModel.findById(CartCountModel.class, 1l);
                                                if(null==cartCountIt)
                                                {
                                                    CartCountModel cartCount = new CartCountModel();
                                                    cartCount.setId(1l);
                                                    cartCount.setCount(0);
                                                    CartCountModel.save(cartCount);
                                                }
                                                else
                                                {
                                                    cartCountIt.setCount(cartCountIt.getCount()-1);
                                                    CartCountModel.save(cartCountIt);
                                                }
                                                //全选按钮置空
                                                funcPopWin.setUNSelectAll();
                                                ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "删除成功");

                                            } else {
                                                progress.dismissView();
                                                //全选按钮置空
                                                funcPopWin.setUNSelectAll();
                                                VolleyUtil.cancelAllRequest();
                                                //上传失败
                                                ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "删除失败");
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            progress.dismissView();
                                            //全选按钮置空
                                            funcPopWin.setUNSelectAll();
                                            VolleyUtil.cancelAllRequest();
                                            //系统级别错误
                                            ToastUtils.showMomentToast(HomeActivity.this, HomeActivity.this, "删除失败");
                                        }
                                    }
                            );
                        }

                        progress.dismissView();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 1);
                        MyBroadcastReceiver.sendBroadcast(this, MyBroadcastReceiver.SHOP_CART, bundle);
                    }

                }
            }
            break;
            case Contant.GO_LOGIN:
            {
                String loginData = (String) msg.obj;
                if(null==loginData || "".equals(loginData))
                {
                    ActivityUtils.getInstance().showActivity(HomeActivity.this, LoginActivity.class);
                }
                else
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("loginData", loginData);
                    ActivityUtils.getInstance().showActivity(HomeActivity.this, LoginActivity.class, bundle);
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

    @Override
    public void onFinishReceiver(MyBroadcastReceiver.ReceiverType type, Object msg) {
        if(type == MyBroadcastReceiver.ReceiverType.jumpCart)
        {
            titleMsgAmount.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            bundle.putInt("type", 0);
            MyBroadcastReceiver.sendBroadcast(this, MyBroadcastReceiver.SHOP_CART, bundle);
            //显示清单列表
            //设置选中状态
            Drawable oneBuyDraw = resources.getDrawable(R.mipmap.bottom_onebuy_normal );
            SystemTools.loadBackground(oneBuy, oneBuyDraw);
            obBuyLabel.setTextColor(resources.getColor(R.color.text_black));
            //标题栏右图标
            //编辑模式
            titleRightImage.setTag ( 1 );
            Drawable rightDraw = resources.getDrawable(R.mipmap.title_edit);
            SystemTools.loadBackground(titleRightImage, rightDraw);
            //重置其他
            Drawable newestDraw = resources.getDrawable(R.mipmap.bottom_newest_normal);
            SystemTools.loadBackground(newest, newestDraw);
            newestLabel.setTextColor(resources.getColor(R.color.text_black));
            Drawable listDraw = resources.getDrawable(R.mipmap.bottom_list_press);
            SystemTools.loadBackground(list, listDraw);
            listLabel.setTextColor(resources.getColor(R.color.title_bg));
            Drawable mallDraw = resources.getDrawable(R.mipmap.mall_icon_common);
            SystemTools.loadBackground(mall, mallDraw);
            mallLabel.setTextColor(resources.getColor(R.color.text_black));
            Drawable profileDraw = resources.getDrawable(R.mipmap.bottom_profile_normal);
            SystemTools.loadBackground(profile, profileDraw);
            profileLabel.setTextColor(resources.getColor(R.color.text_black));

            //显示清单操作弹出框
            funcPopWin1.showLayout();
            funcPopWin1.showAsDropDown(homeBottom, 0, -(2 * (int) resources.getDimension(R.dimen.bottom_height)));
            //切换内容
            String tag = Contant.TAG_3;
            //加载具体的页面
            Message message = mHandler.obtainMessage(Contant.SWITCH_UI, tag);
            mHandler.sendMessage(message);
        }
    }

    public class CartBalanceModel
    {
        private long pid;
        private long buyAmount;

        public long getPid() {
            return pid;
        }

        public void setPid(long pid) {
            this.pid = pid;
        }

        public long getBuyAmount() {
            return buyAmount;
        }

        public void setBuyAmount(long buyAmount) {
            this.buyAmount = buyAmount;
        }
    }
}

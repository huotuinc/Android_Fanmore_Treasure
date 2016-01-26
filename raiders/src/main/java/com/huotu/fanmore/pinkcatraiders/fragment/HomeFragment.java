package com.huotu.fanmore.pinkcatraiders.fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.HomeViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.TabPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.product.AreaActivity;
import com.huotu.fanmore.pinkcatraiders.ui.product.ProductDetailActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.BuyLogActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RaidesLogActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.MarqueenTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页UI
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    @Bind(R.id.homeViewPager)
    ViewPager homeViewPager;
    @Bind(R.id.dot)
    LinearLayout dot;
    List<AdEntity> adDataList = null;
    @Bind(R.id.homeHornText)
    MarqueenTextView homeHornText;
    @Bind(R.id.productsL)
    LinearLayout productsL;
    @Bind(R.id.productSwitch)
    LinearLayout productSwitch;
    @Bind(R.id.rqInnerL)
    RelativeLayout rqInnerL;
    @Bind(R.id.zxInnerL)
    RelativeLayout zxInnerL;
    @Bind(R.id.jdInnerL)
    RelativeLayout jdInnerL;
    @Bind(R.id.zxrsInnerL)
    RelativeLayout zxrsInnerL;
    @Bind(R.id.rqLabel)
    TextView rqLabel;
    @Bind(R.id.zxLabel)
    TextView zxLabel;
    @Bind(R.id.jdLabel)
    TextView jdLabel;
    @Bind(R.id.zxrsLabel)
    TextView zxrsLabel;
    @Bind(R.id.homePullRefresh)
    PullToRefreshScrollView homePullRefresh;
    private int currentIndex = 0;
    public TabPagerAdapter tabPagerAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    public WindowManager wManager;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resources = getActivity().getResources();
        rootView = inflater.inflate(R.layout.home_frag, container, false);
        application = (BaseApplication) getActivity().getApplication();
        rootAty = (HomeActivity) getActivity();
        ButterKnife.bind(this, rootView);
        application.proFragManager = FragManager.getIns(getActivity(), R.id.productsL);
        application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
        wManager = getActivity().getWindowManager();
        initView();
        return rootView;
    }

    private void initView()
    {
        homePullRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                operateType = OperateTypeEnum.REFRESH;
                homePullRefresh.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                operateType = OperateTypeEnum.LOADMORE;
                homePullRefresh.onRefreshComplete();
            }
        });
        homePullRefresh.getRefreshableView().smoothScrollTo(0, 0);
        initSwitchImg();
        initProduct();
    }

    @OnClick(R.id.lbL)
    void showCatagoryUi()
    {
        //ToastUtils.showLongToast(getActivity(), "弹出分类界面");
        ActivityUtils.getInstance().showActivity(getActivity(), RaidesLogActivity.class);
    }

    @OnClick(R.id.zqL)
    void showZqUi()
    {
        Bundle bundle = new Bundle();
        bundle.putString("type", "十元专区");
        ActivityUtils.getInstance().showActivity(getActivity(), AreaActivity.class, bundle);
    }

    @OnClick(R.id.sdL)
    void showSdUi()
    {
        //ToastUtils.showLongToast(getActivity(), "弹出晒单界面");
        //购买记录
        ActivityUtils.getInstance().showActivity(getActivity(), BuyLogActivity.class);
    }

    @OnClick(R.id.wtL)
    void showWtUi()
    {
        ActivityUtils.getInstance().showActivity(getActivity(), ProductDetailActivity.class);
    }

    private void initProduct()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.deep_red));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        SystemTools.loadBackground(rqInnerL, press);
        SystemTools.loadBackground(zxInnerL, normal);
        SystemTools.loadBackground(jdInnerL, normal);
        SystemTools.loadBackground(zxrsInnerL, normal);
    }

    @OnClick(R.id.rqInnerL)
    void clickRql()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.deep_red));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        SystemTools.loadBackground(rqInnerL, press);
        SystemTools.loadBackground(zxInnerL, normal);
        SystemTools.loadBackground(jdInnerL, normal);
        SystemTools.loadBackground(zxrsInnerL, normal);

    }
    @OnClick(R.id.zxInnerL)
    void clickZxl()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.NEWEST_PRODUCT);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.text_black));
        zxLabel.setTextColor(resources.getColor(R.color.deep_red));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        SystemTools.loadBackground(rqInnerL, normal);
        SystemTools.loadBackground(zxInnerL, press);
        SystemTools.loadBackground(jdInnerL, normal);
        SystemTools.loadBackground(zxrsInnerL, normal);
    }
    @OnClick(R.id.jdInnerL)
    void clickJdl()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.PROGRESS);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.text_black));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.deep_red));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        SystemTools.loadBackground(rqInnerL, normal);
        SystemTools.loadBackground(zxInnerL, normal);
        SystemTools.loadBackground(jdInnerL, press);
        SystemTools.loadBackground(zxrsInnerL, normal);
    }
    @OnClick(R.id.zxrsInnerL)
    void clickZxrsl()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.TOTAL);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.text_black));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.deep_red));
        SystemTools.loadBackground(rqInnerL, normal);
        SystemTools.loadBackground(zxInnerL, normal);
        SystemTools.loadBackground(jdInnerL, normal);
        SystemTools.loadBackground(zxrsInnerL, press);
    }

    /**
     * 初始化加载数据
     */
    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) return;
                operateType = OperateTypeEnum.REFRESH;
                homePullRefresh.setRefreshing(true);
            }
        }, 1000);
    }

    private void initSwitchImg()
    {
        homeHornText.requestFocus();
        adDataList = new ArrayList<AdEntity>();
        adDataList.add(new AdEntity(R.mipmap.home_switch_imgg1));
        adDataList.add(new AdEntity(R.mipmap.home_switch_imgg2));
        adDataList.add(new AdEntity(R.mipmap.home_switch_imgg3));
        initDots();
        //通过适配器引入图片
        homeViewPager.setAdapter(new HomeViewPagerAdapter(adDataList, getActivity()));
        int centerValue=Integer.MAX_VALUE/2;
        int value=centerValue%adDataList.size();
        homeViewPager.setCurrentItem(centerValue - value);
        initListener();
        //更新文本内容
        updateTextAndDot();
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    /**
     * 初始化监听器
     */
    @SuppressWarnings("deprecation")
    private void initListener() {
        homeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                updateTextAndDot();

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 更新文本信息
     */
    private void updateTextAndDot(){
        int currentPage=homeViewPager.getCurrentItem()%adDataList.size();

        //改变dot
        for (int i = 0; i < dot.getChildCount(); i++) {
            dot.getChildAt(i).setEnabled(i == currentPage);
        }

    }

    private void initDots()
    {
        for (int i = 0; i < adDataList.size(); i++) {
            View view=new View(getActivity());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(8,8);
            if(i!=0){
                params.leftMargin=5;
            }

            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selecter_dot);
            dot.addView(view);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(getActivity());
        VolleyUtil.cancelAllRequest();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onReshow() {

    }

    @Override
    public void onFragPasue() {

    }

    @Override
    public void onClick(View view) {

    }

    public Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                {
                    homeViewPager.setCurrentItem(homeViewPager.getCurrentItem()+1);
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                }
                break;
                default:
                    break;
            }
        }


    };

}

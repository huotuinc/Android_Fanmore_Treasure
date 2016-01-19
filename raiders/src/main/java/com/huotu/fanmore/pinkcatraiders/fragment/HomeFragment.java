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

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.HomeViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.TabPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.MarqueenTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页UI
 */
public class HomeFragment extends BaseFragment implements Handler.Callback, View.OnClickListener {

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
    @Bind(R.id.productsViewPager)
    ViewPager productsViewPager;
    @Bind(R.id.productSwitch)
    LinearLayout productSwitch;
    @Bind(R.id.rqL)
    RelativeLayout rqL;
    @Bind(R.id.zxL)
    RelativeLayout zxL;
    @Bind(R.id.jdL)
    RelativeLayout jdL;
    @Bind(R.id.zxrsL)
    RelativeLayout zxrsL;
    private int currentIndex = 0;
    public TabPagerAdapter tabPagerAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    public WindowManager wManager;


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
        wManager = getActivity().getWindowManager();
        initView();
        return rootView;
    }

    private void initView()
    {
        initSwitchImg();
        initProduct();
    }

    private void initProduct()
    {
        PopularityFrag popularity = new PopularityFrag();
        NewestProductFrag newestProduct = new NewestProductFrag();
        ProgressFrag progress = new ProgressFrag();
        TotalRequiredFrag totalRequired = new TotalRequiredFrag();

        Bundle b = new Bundle();
        b.putInt("index", 0);
        popularity.setArguments(b);
        mFragmentList.add(popularity);
        b = new Bundle();
        b.putInt("index", 1);
        newestProduct.setArguments(b);
        mFragmentList.add(newestProduct);
        b = new Bundle();
        b.putInt("index", 2);
        progress.setArguments(b);
        mFragmentList.add(progress);
        b = new Bundle();
        b.putInt("index", 3);
        totalRequired.setArguments(b);
        mFragmentList.add(totalRequired);
        tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentList);
        productsViewPager.setAdapter(tabPagerAdapter);

        productsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels != 0) {
                }
                if (positionOffsetPixels == 0) {
                    currentIndex = position;
                    changeIndex(currentIndex);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.rqL)
    void clickRql()
    {
        productsViewPager.setCurrentItem(0);
        changeIndex(productsViewPager.getCurrentItem());
    }
    @OnClick(R.id.zxL)
    void clickZxl()
    {
        productsViewPager.setCurrentItem(1);
        changeIndex(productsViewPager.getCurrentItem());
    }
    @OnClick(R.id.jdL)
    void clickJdl()
    {
        productsViewPager.setCurrentItem(2);
        changeIndex(productsViewPager.getCurrentItem());
    }
    @OnClick(R.id.zxrsL)
    void clickZxrsl()
    {
        productsViewPager.setCurrentItem(3);
        changeIndex(productsViewPager.getCurrentItem());
    }

    private void changeIndex(int index){
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        if(index == 0){
            SystemTools.loadBackground(rqL, press);
            SystemTools.loadBackground(zxL, normal);
            SystemTools.loadBackground(jdL, normal);
            SystemTools.loadBackground(zxrsL, normal);
        }else if(index == 1){
            SystemTools.loadBackground(rqL, normal);
            SystemTools.loadBackground(zxL, press);
            SystemTools.loadBackground(jdL, normal);
            SystemTools.loadBackground(zxrsL, normal);
        }
        else if(index == 2){
            SystemTools.loadBackground(rqL, normal);
            SystemTools.loadBackground(zxL, normal);
            SystemTools.loadBackground(jdL, press);
            SystemTools.loadBackground(zxrsL, normal);
        }
        else if(index == 3){
            SystemTools.loadBackground(rqL, normal);
            SystemTools.loadBackground(zxL, normal);
            SystemTools.loadBackground(jdL, normal);
            SystemTools.loadBackground(zxrsL, press);
        }
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
            dot.getChildAt(i).setEnabled(i==currentPage);
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

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            homeViewPager.setCurrentItem(homeViewPager.getCurrentItem()+1);
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };

}

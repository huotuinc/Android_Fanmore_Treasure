package com.huotu.fanmore.pinkcatraiders.fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.HomeViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页UI
 */
public class HomeFragment extends BaseFragment implements Handler.Callback, View.OnClickListener {

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.homeViewPager)
    ViewPager homeViewPager;
    @Bind(R.id.dot)
    LinearLayout dot;
    List<AdEntity> adDataList = null;

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
    }

    private void initSwitchImg()
    {
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

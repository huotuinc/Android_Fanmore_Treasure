package com.huotu.fanmore.pinkcatraiders.ui.raiders;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.TabPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.fragment.RaidersLogAllFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.RaidersLogDoneFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.RaidersLogFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.RedEnvelopesnouseFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.RedEnvelopesuseFrag;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedEnvelopesActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.useL)
    RelativeLayout useL;
    @Bind(R.id.useLabel)
    TextView useLabel;
    @Bind(R.id.useCount)
    TextView useCount;
    @Bind(R.id.nouseL)
    RelativeLayout nouseL;
    @Bind(R.id.nouseLabel)
    TextView nouseLabel;
    @Bind(R.id.nouseCount)
    TextView nouseCount;
    @Bind(R.id.RedViewPager)
    ViewPager RedViewPager;
    private int currentIndex = 0;
    public TabPagerAdapter tabPagerAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    public NoticePopWindow noticePopWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_red_envelopes);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        initTitle();
        initSwitch();
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
        titleText.setText("我的红包");
    }
    @OnClick(R.id.useL)
    void clickuse()
    {
       RedViewPager.setCurrentItem(0);
        changeIndex(RedViewPager.getCurrentItem());
    }

    @OnClick(R.id.nouseL)
    void clicknouse()
    {
        RedViewPager.setCurrentItem(1);
        changeIndex(RedViewPager.getCurrentItem());
    }
    private void initSwitch()
    {
        RedEnvelopesuseFrag redEnvelopesuseFrag = new RedEnvelopesuseFrag();
        RedEnvelopesnouseFrag redEnvelopesnouseFrag = new RedEnvelopesnouseFrag();
        Bundle b = new Bundle();
        b.putInt("index", 0);
        redEnvelopesuseFrag.setArguments(b);
        mFragmentList.add(redEnvelopesuseFrag);
        b = new Bundle();
        b.putInt("index", 1);
        redEnvelopesnouseFrag.setArguments(b);
        mFragmentList.add(redEnvelopesnouseFrag);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragmentList);
        RedViewPager.setAdapter(tabPagerAdapter);
       RedViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

           @Override
           public void onPageSelected(int index) {
           }

           @Override
           public void onPageScrolled(int index, float arg1, int pixes) {
               if (pixes != 0) {
               }
               if (pixes == 0) {
                   currentIndex = index;
                   changeIndex(currentIndex);
               }
           }

           @Override
           public void onPageScrollStateChanged(int state) {
           }
       });
    }
    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(RedEnvelopesActivity.this);
    }

    private void changeIndex(int index){
        if(index == 0){
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(useL, drawable_press);
            SystemTools.loadBackground(nouseL, drawable_normal);
            useLabel.setTextColor(resources.getColor(R.color.deep_red));
            useCount.setTextColor(resources.getColor(R.color.deep_red));
            nouseLabel.setTextColor(resources.getColor(R.color.text_black));
            nouseCount.setTextColor(resources.getColor(R.color.text_black));
        }else if(index == 1){
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(useL, drawable_normal);
            SystemTools.loadBackground(nouseL, drawable_press);
            useLabel.setTextColor(resources.getColor(R.color.text_black));
            useCount.setTextColor(resources.getColor(R.color.text_black));
            nouseLabel.setTextColor(resources.getColor(R.color.deep_red));
            nouseCount.setTextColor(resources.getColor(R.color.deep_red));

        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

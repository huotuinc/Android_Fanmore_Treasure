package com.huotu.fanmore.pinkcatraiders.ui.raiders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.TabPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.fragment.BuyAllFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.BuyWaitDeliverFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.BuyWaitPayFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.BuyWaitReceiptFrag;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购买记录
 */
public class BuyLogActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

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
    @Bind(R.id.allL)
    RelativeLayout allL;
    @Bind(R.id.waitPayL)
    RelativeLayout waitPayL;
    @Bind(R.id.waitDeliverL)
    RelativeLayout waitDeliverL;
    @Bind(R.id.waitReceiptL)
    RelativeLayout waitReceiptL;
    @Bind(R.id.allIcon)
    ImageView allIcon;
    @Bind(R.id.allLabel)
    TextView allLabel;
    @Bind(R.id.allCount)
    TextView allCount;
    @Bind(R.id.waitPayIcon)
    ImageView waitPayIcon;
    @Bind(R.id.waitPayLabel)
    TextView waitPayLabel;
    @Bind(R.id.waitPayCount)
    TextView waitPayCount;
    @Bind(R.id.waitDeliverIcon)
    ImageView waitDeliverIcon;
    @Bind(R.id.waitDeliverLabel)
    TextView waitDeliverLabel;
    @Bind(R.id.waitDeliverCount)
    TextView waitDeliverCount;
    @Bind(R.id.waitReceiptIcon)
    ImageView waitReceiptIcon;
    @Bind(R.id.waitReceiptLabel)
    TextView waitReceiptLabel;
    @Bind(R.id.waitReceiptCount)
    TextView waitReceiptCount;

    @Bind(R.id.buyViewPager)
    ViewPager buyViewPager;
    private int currentIndex = 0;
    public TabPagerAdapter tabPagerAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_buy_log);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        initTitle();
        initSwitch();
    }

    private void initSwitch()
    {
        BuyAllFrag buyAll = new BuyAllFrag();
        BuyWaitPayFrag buyWaitPay = new BuyWaitPayFrag();
        BuyWaitDeliverFrag buyWaitDeliver = new BuyWaitDeliverFrag();
        BuyWaitReceiptFrag buyWaitReceipt = new BuyWaitReceiptFrag();
        Bundle b = new Bundle();
        b.putInt("index", 0);
        buyAll.setArguments(b);
        mFragmentList.add(buyAll);
        b = new Bundle();
        b.putInt("index", 1);
        buyWaitPay.setArguments(b);
        mFragmentList.add(buyWaitPay);
        b.putInt("index", 2);
        buyWaitDeliver.setArguments(b);
        mFragmentList.add(buyWaitDeliver);
        b.putInt("index", 3);
        buyWaitReceipt.setArguments(b);
        mFragmentList.add(buyWaitReceipt);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragmentList);
        buyViewPager.setAdapter(tabPagerAdapter);
        buyViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

    private void changeIndex(int index){
        if(index == 0){
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(allL, drawable_press);
            SystemTools.loadBackground(waitPayL, drawable_normal);
            SystemTools.loadBackground(waitDeliverL, drawable_normal);
            SystemTools.loadBackground(waitReceiptL, drawable_normal);
            SystemTools.loadBackground(allIcon, resources.getDrawable(R.mipmap.buy_all_red));
            SystemTools.loadBackground(waitPayIcon, resources.getDrawable(R.mipmap.buy_fukuan));
            SystemTools.loadBackground(waitDeliverIcon, resources.getDrawable(R.mipmap.buy_fahuo));
            SystemTools.loadBackground(waitReceiptIcon, resources.getDrawable(R.mipmap.buy_shouhuo));
            allLabel.setTextColor(resources.getColor(R.color.deep_red));
            allCount.setTextColor(resources.getColor(R.color.deep_red));
            waitPayLabel.setTextColor(resources.getColor(R.color.text_black));
            waitPayCount.setTextColor(resources.getColor(R.color.text_black));
            waitDeliverLabel.setTextColor(resources.getColor(R.color.text_black));
            waitDeliverCount.setTextColor(resources.getColor(R.color.text_black));
            waitReceiptLabel.setTextColor(resources.getColor(R.color.text_black));
            waitReceiptCount.setTextColor(resources.getColor(R.color.text_black));
        }else if(index == 1){
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(allL, drawable_normal);
            SystemTools.loadBackground(waitPayL, drawable_press);
            SystemTools.loadBackground(waitDeliverL, drawable_normal);
            SystemTools.loadBackground(waitReceiptL, drawable_normal);
            SystemTools.loadBackground(allIcon, resources.getDrawable(R.mipmap.buy_all));
            SystemTools.loadBackground(waitPayIcon, resources.getDrawable(R.mipmap.buy_fukuan_red));
            SystemTools.loadBackground(waitDeliverIcon, resources.getDrawable(R.mipmap.buy_fahuo));
            SystemTools.loadBackground(waitReceiptIcon, resources.getDrawable(R.mipmap.buy_shouhuo));
            allLabel.setTextColor(resources.getColor(R.color.text_black));
            allCount.setTextColor(resources.getColor(R.color.text_black));
            waitPayLabel.setTextColor(resources.getColor(R.color.deep_red));
            waitPayCount.setTextColor(resources.getColor(R.color.deep_red));
            waitDeliverLabel.setTextColor(resources.getColor(R.color.text_black));
            waitDeliverCount.setTextColor(resources.getColor(R.color.text_black));
            waitReceiptLabel.setTextColor(resources.getColor(R.color.text_black));
            waitReceiptCount.setTextColor(resources.getColor(R.color.text_black));
        } else if(index == 2)
        {
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(allL, drawable_normal);
            SystemTools.loadBackground(waitPayL, drawable_normal);
            SystemTools.loadBackground(waitDeliverL, drawable_press);
            SystemTools.loadBackground(waitReceiptL, drawable_normal);
            SystemTools.loadBackground(allIcon, resources.getDrawable(R.mipmap.buy_all));
            SystemTools.loadBackground(waitPayIcon, resources.getDrawable(R.mipmap.buy_fukuan));
            SystemTools.loadBackground(waitDeliverIcon, resources.getDrawable(R.mipmap.buy_fahuo_red));
            SystemTools.loadBackground(waitReceiptIcon, resources.getDrawable(R.mipmap.buy_shouhuo));
            allLabel.setTextColor(resources.getColor(R.color.text_black));
            allCount.setTextColor(resources.getColor(R.color.text_black));
            waitPayLabel.setTextColor(resources.getColor(R.color.text_black));
            waitPayCount.setTextColor(resources.getColor(R.color.text_black));
            waitDeliverLabel.setTextColor(resources.getColor(R.color.deep_red));
            waitDeliverCount.setTextColor(resources.getColor(R.color.deep_red));
            waitReceiptLabel.setTextColor(resources.getColor(R.color.text_black));
            waitReceiptCount.setTextColor(resources.getColor(R.color.text_black));
        }
        else if(index == 3)
        {
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(allL, drawable_normal);
            SystemTools.loadBackground(waitPayL, drawable_normal);
            SystemTools.loadBackground(waitDeliverL, drawable_normal);
            SystemTools.loadBackground(waitReceiptL, drawable_press);
            SystemTools.loadBackground(allIcon, resources.getDrawable(R.mipmap.buy_all));
            SystemTools.loadBackground(waitPayIcon, resources.getDrawable(R.mipmap.buy_fukuan));
            SystemTools.loadBackground(waitDeliverIcon, resources.getDrawable(R.mipmap.buy_fahuo));
            SystemTools.loadBackground(waitReceiptIcon, resources.getDrawable(R.mipmap.buy_shouhuo_red));
            allLabel.setTextColor(resources.getColor(R.color.text_black));
            allCount.setTextColor(resources.getColor(R.color.text_black));
            waitPayLabel.setTextColor(resources.getColor(R.color.text_black));
            waitPayCount.setTextColor(resources.getColor(R.color.text_black));
            waitDeliverLabel.setTextColor(resources.getColor(R.color.text_black));
            waitDeliverCount.setTextColor(resources.getColor(R.color.text_black));
            waitReceiptLabel.setTextColor(resources.getColor(R.color.deep_red));
            waitReceiptCount.setTextColor(resources.getColor(R.color.deep_red));
        }
    }

    @OnClick(R.id.allL)
    void clickAll()
    {
        buyViewPager.setCurrentItem(0);
        changeIndex(buyViewPager.getCurrentItem());
    }

    @OnClick(R.id.waitPayL)
    void clickWaitPay()
    {
        buyViewPager.setCurrentItem(1);
        changeIndex(buyViewPager.getCurrentItem());
    }

    @OnClick(R.id.waitDeliverL)
    void clickDeliver()
    {
        buyViewPager.setCurrentItem(2);
        changeIndex(buyViewPager.getCurrentItem());
    }

    @OnClick(R.id.waitReceiptL)
    void clickReceipt()
    {
        buyViewPager.setCurrentItem(3);
        changeIndex(buyViewPager.getCurrentItem());
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
        titleText.setText("购买记录");
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
        closeSelf(BuyLogActivity.this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(BuyLogActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

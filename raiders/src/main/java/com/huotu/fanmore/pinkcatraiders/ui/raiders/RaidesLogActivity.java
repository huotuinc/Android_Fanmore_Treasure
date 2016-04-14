package com.huotu.fanmore.pinkcatraiders.ui.raiders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.TabPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.fragment.RaidersLogAllFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.RaidersLogDoneFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.RaidersLogFrag;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.CartUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 夺宝记录
 */
public class RaidesLogActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

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
    @Bind(R.id.allLabel)
    TextView allLabel;
    @Bind(R.id.allCount)
    TextView allCount;
    @Bind(R.id.doingL)
    RelativeLayout doingL;
    @Bind(R.id.doingLabel)
    TextView doingLabel;
    @Bind(R.id.doingCount)
    TextView doingCount;
    @Bind(R.id.doneL)
    RelativeLayout doneL;
    @Bind(R.id.doneLabel)
    TextView doneLabel;
    @Bind(R.id.doneCount)
    TextView doneCount;
    @Bind(R.id.raidersViewPager)
    ViewPager raidersViewPager;
    private int currentIndex = 0;
    public TabPagerAdapter tabPagerAdapter;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    public NoticePopWindow noticePopWin;
    int index;
    public ProgressPopupWindow progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_raiders_log);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        initTitle();
        currentIndex=getIntent().getIntExtra("index",0);
        initSwitch();

    }

    private void initSwitch()
    {
        RaidersLogAllFrag raidersLogFragAll = new RaidersLogAllFrag();
        RaidersLogFrag raidersLogFragDoing = new RaidersLogFrag();
        RaidersLogDoneFrag raidersLogFragDone = new RaidersLogDoneFrag();
        Bundle b = new Bundle();
        b.putInt("index", 0);
        raidersLogFragAll.setArguments(b);
        mFragmentList.add(raidersLogFragAll);
        b = new Bundle();
        b.putInt("index", 1);
        raidersLogFragDoing.setArguments(b);
        mFragmentList.add(raidersLogFragDoing);
        b.putInt("index", 2);
        raidersLogFragDone.setArguments(b);
        mFragmentList.add(raidersLogFragDone);
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragmentList);
        raidersViewPager.setAdapter(tabPagerAdapter);
        raidersViewPager.setOffscreenPageLimit(3);
        raidersViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
        raidersViewPager.setCurrentItem(currentIndex);
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(RaidesLogActivity.this);
    }

    private void changeIndex(int index){
        if(index == 0){
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(allL, drawable_press);
            SystemTools.loadBackground(doingL, drawable_normal);
            SystemTools.loadBackground(doneL, drawable_normal);
            allLabel.setTextColor(resources.getColor(R.color.deep_red));
            allCount.setTextColor(resources.getColor(R.color.deep_red));
            doingLabel.setTextColor(resources.getColor(R.color.text_black));
            doingCount.setTextColor(resources.getColor(R.color.text_black));
            doneLabel.setTextColor(resources.getColor(R.color.text_black));
            doneCount.setTextColor(resources.getColor(R.color.text_black));
        }else if(index == 1){
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(allL, drawable_normal);
            SystemTools.loadBackground(doingL, drawable_press);
            SystemTools.loadBackground(doneL, drawable_normal);
            allLabel.setTextColor(resources.getColor(R.color.text_black));
            allCount.setTextColor(resources.getColor(R.color.text_black));
            doingLabel.setTextColor(resources.getColor(R.color.deep_red));
            doingCount.setTextColor(resources.getColor(R.color.deep_red));
            doneLabel.setTextColor(resources.getColor(R.color.text_black));
            doneCount.setTextColor(resources.getColor(R.color.text_black));
        } else if(index == 2)
        {
            Drawable drawable_press = resources.getDrawable(R.drawable.switch_press);
            Drawable drawable_normal = resources.getDrawable(R.color.color_white);
            SystemTools.loadBackground(allL, drawable_normal);
            SystemTools.loadBackground(doingL, drawable_normal);
            SystemTools.loadBackground(doneL, drawable_press);
            allLabel.setTextColor(resources.getColor(R.color.text_black));
            allCount.setTextColor(resources.getColor(R.color.text_black));
            doingLabel.setTextColor(resources.getColor(R.color.text_black));
            doingCount.setTextColor(resources.getColor(R.color.text_black));
            doneLabel.setTextColor(resources.getColor(R.color.deep_red));
            doneCount.setTextColor(resources.getColor(R.color.deep_red));
        }
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
        titleText.setText("夺宝记录");
    }

    @OnClick(R.id.allL)
    void clickAll()
    {
        raidersViewPager.setCurrentItem(0);
        changeIndex(raidersViewPager.getCurrentItem());
    }

    @OnClick(R.id.doingL)
    void clickDoing()
    {
        raidersViewPager.setCurrentItem(1);
        changeIndex(raidersViewPager.getCurrentItem());
    }

    @OnClick(R.id.doneL)
    void clickDone()
    {
        raidersViewPager.setCurrentItem(2);
        changeIndex(raidersViewPager.getCurrentItem());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(RaidesLogActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what)
        {
            case Contant.UPDATE_RAIDER_COUNT:
            {
                String[] counts = (String[]) msg.obj;
                allCount.setText(counts[0]);
                doingCount.setText(counts[1]);
                doneCount.setText(counts[2]);
            }
            break;
            case Contant.RAIDERS_NOW:
            {

                                ActivityUtils.getInstance().skipActivity(RaidesLogActivity.this, HomeActivity.class);
                                closeSelf(RaidesLogActivity.this);



            }
            break;
            case Contant.APPAND_LIST:
            {
                //夺宝记录追加
                RaidersModel raider = (RaidersModel) msg.obj;
                progress = new ProgressPopupWindow( RaidesLogActivity.this, RaidesLogActivity.this, wManager );
                progress.showProgress("正在追加清单");
                progress.showAtLocation(titleLayoutL,
                        Gravity.CENTER, 0, 0
                );
                ProductModel model = new ProductModel();
                model.setDefaultAmount(raider.getAttendAmount());
                CartUtils.addCartDone(model, String.valueOf(raider.getIssueId()), progress, application, RaidesLogActivity.this, mHandler,0);
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

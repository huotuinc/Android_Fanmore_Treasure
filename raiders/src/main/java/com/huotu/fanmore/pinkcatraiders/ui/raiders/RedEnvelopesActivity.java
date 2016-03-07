package com.huotu.fanmore.pinkcatraiders.ui.raiders;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
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
import com.huotu.fanmore.pinkcatraiders.fragment.RedEnvelopesnouseFrag;
import com.huotu.fanmore.pinkcatraiders.fragment.RedEnvelopesuseFrag;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ShareOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @OnClick(R.id.share_tv)
    void share()
    {
        if( false == RedEnvelopesActivity.this.canConnect() ){

        return;
    }
        String url = Contant.REQUEST_URL + Contant.SHARE_REF_PACKETS;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), RedEnvelopesActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(RedEnvelopesActivity.this.isFinishing())
                {
                    return;
                }
                JSONUtil<ShareOutputModel> jsonUtil = new JSONUtil<ShareOutputModel>();
                ShareOutputModel shareOutput = new ShareOutputModel();
                shareOutput = jsonUtil.toBean(response.toString(), shareOutput);
                if(null != shareOutput && null != shareOutput.getResultData() && (1==shareOutput.getResultCode()))
                {
                    if(null != shareOutput.getResultData().getShare()&&null!=shareOutput.getResultData() )
                    {
                        ToastUtils.showShortToast(RedEnvelopesActivity.this,"分享成功");
                    }
                    else
                    {
                        noticePopWin = new NoticePopWindow(RedEnvelopesActivity.this, RedEnvelopesActivity.this, wManager, shareOutput.getResultDescription());
                        noticePopWin.showNotice();
                        noticePopWin.showAtLocation(titleLayoutL,
                                Gravity.CENTER, 0, 0
                        );
                    }
                }
                else
                {
                    //异常处理，自动切换成无数据
                    noticePopWin = new NoticePopWindow(RedEnvelopesActivity.this, RedEnvelopesActivity.this, wManager, shareOutput.getResultDescription());
                    noticePopWin.showNotice();
                    noticePopWin.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0
                    );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(RedEnvelopesActivity.this.isFinishing())
                {
                    return;
                }

            }
        });
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

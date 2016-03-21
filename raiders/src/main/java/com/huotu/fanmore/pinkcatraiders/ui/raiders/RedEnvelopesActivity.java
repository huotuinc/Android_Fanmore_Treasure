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
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RedPacketOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.ShareModel;
import com.huotu.fanmore.pinkcatraiders.model.ShareOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.SharePopupWindow;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 红包列表界面
 */
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
    @Bind(R.id.titleRightImage)
    ImageView titleRightImage;
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
    public SharePopupWindow sharePopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_red_envelopes);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        sharePopupWindow= new SharePopupWindow(RedEnvelopesActivity.this,RedEnvelopesActivity.this,application);
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
        Drawable rightDraw = resources.getDrawable(R.mipmap.title_share_redpackage);
        SystemTools.loadBackground(titleRightImage, rightDraw);
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
    @OnClick(R.id.titleRightImage)
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
                        //application.writeShareinfo(shareOutput.getResultData().getShare());
                        ShareModel msgModel = new ShareModel ();
                        msgModel.setImageUrl(shareOutput.getResultData().getShare().getImageUrl());
                        msgModel.setText(shareOutput.getResultData().getShare().getText());
                        msgModel.setTitle(shareOutput.getResultData().getShare().getTitle());
                        msgModel.setUrl(shareOutput.getResultData().getShare().getUrl());
                        sharePopupWindow.initShareParams(msgModel);
                        sharePopupWindow.showShareWindow();
                        sharePopupWindow.showAtLocation(titleLayoutL,
                                Gravity.BOTTOM, 0, 0);
                        sharePopupWindow.setPlatformActionListener (
                                new PlatformActionListener() {
                                    @Override
                                    public void onComplete(
                                            Platform platform, int i, HashMap<String, Object> hashMap
                                    ) {
                                        Message msg = Message.obtain();
                                        msg.obj = platform;
                                        mHandler.sendMessage(msg);
                                        successshare();

                                    }

                                    @Override
                                    public void onError(Platform platform, int i, Throwable throwable) {
                                        Message msg = Message.obtain();
                                        msg.obj = platform;
                                        mHandler.sendMessage(msg);
                                    }

                                    @Override
                                    public void onCancel(Platform platform, int i) {
                                        Message msg = Message.obtain();
                                        msg.obj = platform;
                                        mHandler.sendMessage(msg);
                                    }
                                }
                        );

                        sharePopupWindow.setOnDismissListener ( new PoponDismissListener( RedEnvelopesActivity.this ) );


                    }
                    else
                    {

                        noticePopWin = new NoticePopWindow(RedEnvelopesActivity.this, RedEnvelopesActivity.this, wManager, "红包分享暂不可用");
                        noticePopWin.showNotice();
                        noticePopWin.showAtLocation(titleLayoutL,
                                Gravity.CENTER, 0, 0
                        );
                    }
                }
                else
                {
                    //异常处理，自动切换成无数据

                    noticePopWin = new NoticePopWindow(RedEnvelopesActivity.this, RedEnvelopesActivity.this, wManager, "红包分享暂不可用");
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
                noticePopWin = new NoticePopWindow(RedEnvelopesActivity.this, RedEnvelopesActivity.this, wManager, "数据请求失败");
                noticePopWin.showNotice();
                noticePopWin.showAtLocation(titleLayoutL,
                        Gravity.CENTER, 0, 0
                );

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

  private void successshare(){
        String url = Contant.REQUEST_URL + Contant.SUCCESS_SHARE_REDPACKETS;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), RedEnvelopesActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (RedEnvelopesActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<BaseModel> jsonUtil = new JSONUtil<BaseModel>();
                BaseModel baseModel = new BaseModel();
                baseModel = jsonUtil.toBean(response.toString(), baseModel);
                if (null != baseModel && null != baseModel.getResultDescription() && (1 == baseModel.getResultCode())) {

                }
                else {
                    noticePopWin = new NoticePopWindow(RedEnvelopesActivity.this, RedEnvelopesActivity.this, wManager, baseModel.getResultDescription());
                    noticePopWin.showNotice();
                    noticePopWin.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (RedEnvelopesActivity.this.isFinishing()) {
                    return;
                }

            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what)
        {
            case Contant.REDPACKAGE_COUNT:
            {
                String[] counts = (String[]) msg.obj;

                useCount.setText(counts[0]);
                nouseCount.setText(counts[1]);
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

package com.huotu.fanmore.pinkcatraiders.ui.product;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.HomeViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.LoadSwitchImgAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.model.PartnerLogModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品详情界面
 */
public class ProductDetailActivity extends BaseActivity implements Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler xHandler;
    public
    WindowManager wManager;
    public
    AssetManager am;
    public ProgressPopupWindow progress;
    public
    NoticePopWindow noticePop;

    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.productDetailViewPager)
    ViewPager productDetailViewPager;
    @Bind(R.id.productDetaildot)
    LinearLayout productDetaildot;
    @Bind(R.id.productDetailPullRefresh)
    PullToRefreshScrollView productDetailPullRefresh;
    List<String> imgs = null;
    @Bind(R.id.productDetailBottomOther)
    ViewStub productDetailBottomOther;
    @Bind(R.id.productDetailBottomAnnounced)
    ViewStub productDetailBottomAnnounced;
    @Bind(R.id.productDetailNameLabel)
    TextView productDetailNameLabel;
    @Bind(R.id.productDetailName)
    TextView productDetailName;
    @Bind(R.id.loginedDetail)
    ViewStub loginedDetail;
    @Bind(R.id.unloginFullPrice)
    ViewStub unloginFullPrice;
    @Bind(R.id.unlogin)
    ViewStub unlogin;
    @Bind(R.id.announcedDetail)
    ViewStub announcedDetail;
    @Bind(R.id.graphicDetails)
    LinearLayout graphicDetails;
    @Bind(R.id.historys)
    LinearLayout historys;
    @Bind(R.id.sdL)
    LinearLayout sdL;
    @Bind(R.id.logL)
    LinearLayout logL;
    @Bind(R.id.allLogL)
    LinearLayout allLogL;
    @Bind(R.id.allLogText)
    TextView allLogText;
    @Bind(R.id.partnerLogL)
    LinearLayout partnerLogL;
    public List<PartnerLogModel> partnerLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_product_detail);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        xHandler = new Handler ( this );
        //设置沉浸模式
        //setImmerseLayout(this.findViewById(R.id.titleLayoutL));
        wManager = this.getWindowManager();
        initTitle();
        initBottom();
        initSwitchImg();
        initPartnerArea();
        initLog();
    }

    private void initLog()
    {
        partnerLogs = new ArrayList<PartnerLogModel>();
        PartnerLogModel partnerLog1 = new PartnerLogModel();
        partnerLog1.setGroupTime("2016-02-11");
        partnerLog1.setPartnerCount("参与了3人次");
        partnerLog1.setPartnerIp("（杭州市 IP 172.0.0.1）");
        partnerLog1.setPartnerLogo("http://v1.qzone.cc/avatar/201404/10/00/12/534571832f9ea304.jpg!200x200.jpg");
        partnerLog1.setPartnerTime("2016-02-11 12:23:23");
        partnerLog1.setPartnerName("小鸡鸡");
        partnerLogs.add(partnerLog1);
        PartnerLogModel partnerLog2 = new PartnerLogModel();
        partnerLog2.setGroupTime("2016-02-11");
        partnerLog2.setPartnerCount("参与了4人次");
        partnerLog2.setPartnerIp("（铁岭市 IP 172.0.0.1）");
        partnerLog2.setPartnerLogo("http://v1.qzone.cc/avatar/201404/10/00/12/534571832f9ea304.jpg!200x200.jpg");
        partnerLog2.setPartnerTime("2016-02-11 09:23:23");
        partnerLog2.setPartnerName("小丫丫");
        partnerLogs.add(partnerLog2);
        if(null!=partnerLogs && !partnerLogs.isEmpty())
        {
            int size = partnerLogs.size();
            for(int i = 0; i < size; i++)
            {
                PartnerLogModel partnerLog = partnerLogs.get(i);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup
                        .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RelativeLayout parntersLayout = (RelativeLayout) LayoutInflater.from(ProductDetailActivity.this).inflate(R.layout.partner_log, null);
                TextView bgLine = (TextView) parntersLayout.findViewById(R.id.bgLine);
                bgLine.setMinimumHeight(200);
                CircleImageView partnerLogo = (CircleImageView) parntersLayout.findViewById(R.id.partnerLogo);
                BitmapLoader.create().loadRoundImage(ProductDetailActivity.this, partnerLogo, partnerLog.getPartnerLogo(), R.mipmap.ic_launcher);
                TextView partnerName = (TextView) parntersLayout.findViewById(R.id.partnerName);
                partnerName.setText(partnerLog.getPartnerName());
                TextView partnerIp = (TextView) parntersLayout.findViewById(R.id.partnerIp);
                partnerIp.setText(partnerLog.getPartnerIp());
                TextView partnerCount = (TextView) parntersLayout.findViewById(R.id.partnerCount);
                partnerCount.setText(partnerLog.getPartnerCount());
                TextView partnerTime = (TextView) parntersLayout.findViewById(R.id.partnerTime);
                partnerTime.setText(partnerLog.getPartnerTime());
                lp.setMargins(0, 0, 0, 0);
                parntersLayout.setLayoutParams(lp);
                partnerLogL.addView(parntersLayout);
            }
        }
        else
        {
            //显示暂无参与历史记录

        }
    }

    private void initPartnerArea()
    {
        //登录状态
        /*loginedDetail.inflate();
        TextView noLabel = (TextView) this.findViewById(R.id.noLabel);
        noLabel.setText("期号：12345");
        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.partnerProgress);
        progressBar.setProgress(12);
        progressBar.setMax(50);
        TextView totalRequiredLabel = (TextView) this.findViewById(R.id.totalRequiredLabel);
        totalRequiredLabel.setText("总需人数：1234次");
        TextView surplusLabel = (TextView) this.findViewById(R.id.surplusLabel);
        surplusLabel.setText("剩余：123");
        TextView parentCount = (TextView) this.findViewById(R.id.parentCount);
        parentCount.setText("您参与了：1人次");
        TextView raidersNo = (TextView) this.findViewById(R.id.raidersNo);
        raidersNo.setText("夺宝号码：1000589");*/
        //未登陆全价
        /*unloginFullPrice.inflate();
        TextView fullPriceMoney = (TextView) this.findViewById(R.id.fullPriceMoney);
        fullPriceMoney.setText("¥2000.0");
        TextView settlementBtn = (TextView) this.findViewById(R.id.settlementBtn);
        TextView noLabel = (TextView) this.findViewById(R.id.noLabel);
        noLabel.setText("期号：10018");
        ProgressBar partnerProgress = (ProgressBar) this.findViewById(R.id.partnerProgress);
        partnerProgress.setMax(100);
        partnerProgress.setProgress(38);
        TextView totalRequiredLabel = (TextView) this.findViewById(R.id.totalRequiredLabel);
        totalRequiredLabel.setText("总需人数：100人次");
        TextView surplusLabel = (TextView) this.findViewById(R.id.surplusLabel);
        surplusLabel.setText("剩余：62");
        TextView buyBtn = (TextView) this.findViewById(R.id.buyBtn);
        TextView addCartBtn = (TextView) this.findViewById(R.id.addCartBtn);*/
        //未登陆状态
        /*unlogin.inflate();
        TextView noLabel = (TextView) this.findViewById(R.id.noLabel);
        noLabel.setText("期号：12345");
        ProgressBar progressBar = (ProgressBar) this.findViewById(R.id.partnerProgress);
        progressBar.setProgress(12);
        progressBar.setMax(50);
        TextView totalRequiredLabel = (TextView) this.findViewById(R.id.totalRequiredLabel);
        totalRequiredLabel.setText("总需人数：1234次");
        TextView surplusLabel = (TextView) this.findViewById(R.id.surplusLabel);
        surplusLabel.setText("剩余：123");*/
        //揭晓状态
        announcedDetail.inflate();
        CircleImageView accountLogo = (CircleImageView) this.findViewById(R.id.accountLogo);
        BitmapLoader.create().loadRoundImage(ProductDetailActivity.this, accountLogo, "http://v1.qzone.cc/avatar/201404/10/00/12/534571832f9ea304.jpg!200x200.jpg", R.mipmap.ic_launcher);
        TextView winnerName = (TextView) this.findViewById(R.id.winnerName);
        winnerName.setText("中奖者：百晓生");
        TextView winnerIp = (TextView) this.findViewById(R.id.winnerIp);
        winnerName.setText("铁岭：10.10.123.45");
        TextView winnerId = (TextView) this.findViewById(R.id.winnerId);
        winnerName.setText("用户ID：23000909");
        TextView partnerUser = (TextView) this.findViewById(R.id.partnerUser);
        winnerName.setText("本期参与：3000次");
        TextView partnerTime = (TextView) this.findViewById(R.id.partnerTime);
        winnerName.setText("揭晓时间：2015-12-11 14:20:11");
        TextView luckyNo = (TextView) this.findViewById(R.id.luckyNo);
        winnerName.setText("幸运号：32890");
        TextView calculationDetail = (TextView) this.findViewById(R.id.calculationDetail);
        winnerName.setText("幸运号：32890");

    }

    private void initBottom()
    {
        //揭晓底部
        //productDetailBottomAnnounced.inflate();
        //非揭晓底部
        productDetailBottomOther.inflate();
        //左边
        TextView bottomOtherBtnLeft = (TextView) this.findViewById(R.id.bottomOtherBtnLeft);
        //中间
        TextView bottomOtherBtnCenter = (TextView) this.findViewById(R.id.bottomOtherBtnCenter);
        //购物车
        ImageView cartImg = (ImageView) this.findViewById(R.id.bottomOtherCart);
        //数量
        TextView bottomOtherCartAmount = (TextView) this.findViewById(R.id.bottomOtherCartAmount);
        //设置宽度
        ViewGroup.LayoutParams pl = bottomOtherBtnLeft.getLayoutParams();
        pl.width = wManager.getDefaultDisplay().getWidth()/3;
        bottomOtherBtnLeft.setLayoutParams(pl);
        ViewGroup.LayoutParams pc = bottomOtherBtnCenter.getLayoutParams();
        pc.width = wManager.getDefaultDisplay().getWidth()/3;
        bottomOtherBtnCenter.setLayoutParams(pc);
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
        titleText.setText("奖品详情");
    }

    private void initSwitchImg()
    {
        imgs = new ArrayList<String>();
        imgs.add("http://img1.imgtn.bdimg.com/it/u=1621836957,2986151666&fm=11&gp=0.jpg");
        imgs.add("http://img5.imgtn.bdimg.com/it/u=458654469,331519466&fm=21&gp=0.jpg");
        imgs.add("http://img4.imgtn.bdimg.com/it/u=2973039912,2782330040&fm=21&gp=0.jpg");
        initDots();
        //通过适配器引入图片
        productDetailViewPager.setAdapter(new LoadSwitchImgAdapter(imgs, ProductDetailActivity.this));
        int centerValue=Integer.MAX_VALUE/2;
        int value=centerValue%imgs.size();
        productDetailViewPager.setCurrentItem(centerValue - value);
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
        productDetailViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
        int currentPage=productDetailViewPager.getCurrentItem()%imgs.size();

        //改变dot
        for (int i = 0; i < productDetaildot.getChildCount(); i++) {
            productDetaildot.getChildAt(i).setEnabled(i==currentPage);
        }

    }

    private void initDots()
    {
        for (int i = 0; i < imgs.size(); i++) {
            View view=new View(ProductDetailActivity.this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(8,8);
            if(i!=0){
                params.leftMargin=5;
            }

            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selecter_dot);
            productDetaildot.addView(view);
        }
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
            this.closeSelf(ProductDetailActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            productDetailViewPager.setCurrentItem(productDetailViewPager.getCurrentItem()+1);
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };
}

package com.huotu.fanmore.pinkcatraiders.ui.product;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.HomeViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.LoadSwitchImgAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
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

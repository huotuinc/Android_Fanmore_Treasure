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
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.HomeViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.LoadSwitchImgAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.PartnerHistorysModel;
import com.huotu.fanmore.pinkcatraiders.model.PartnerHistorysOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.PartnerLogModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductDetailModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductDetailsOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.WebExhibitionActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.ShowOrderActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.ShareOrderActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情界面
 */
public class ProductDetailActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
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
    List<String> imgs = new ArrayList<String>();
    @Bind(R.id.productDetailBottomOther)
    ViewStub productDetailBottomOther;
    @Bind(R.id.productDetailBottomAnnounced)
    ViewStub productDetailBottomAnnounced;
    @Bind(R.id.productDetailNameLabel)
    TextView productDetailNameLabel;
    @Bind(R.id.productDetailName)
    TextView productDetailName;
    @Bind(R.id.loginedDetail)
    RelativeLayout loginedDetail;
    @Bind(R.id.unlogin_funllprice)
    RelativeLayout unloginFullPrice;
    @Bind(R.id.unlogin)
    RelativeLayout unlogin;
    @Bind(R.id.announcedDetail)
    RelativeLayout announcedDetail;
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
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public Handler mHandler;
    public Bundle bundle;
    public long issueId;
    public List<PartnerHistorysModel> partnerHistorys;
    public String detailUrl;
    public long pid;
    public boolean isInflate = true;
    public ProductDetailModel productDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_product_detail);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        wManager = this.getWindowManager();
        bundle = this.getIntent().getExtras();
        mHandler = new Handler(this);
        initTitle();
        initBottom();
        initSwitchImg();
        initView();
    }

    private void initView()
    {
        getDetailData();
        productDetailPullRefresh.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {

                        operateType = OperateTypeEnum.REFRESH;
                        getDetailData();
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {

                        operateType = OperateTypeEnum.LOADMORE;
                        getCommentLog();
                    }
                }
        );
        productDetailPullRefresh.getRefreshableView().smoothScrollTo(0, 0);
    }

    private void getDetailData()
    {
        //获取产品详情
        if( false == ProductDetailActivity.this.canConnect() ){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    productDetailPullRefresh.onRefreshComplete();
                }
            });
            return;
        }
        if (bundle.getInt("tip")==1) {
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_DTAIL_BY_GOODS_ID;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ProductDetailActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            //商品id
            maps.put("goodsId", bundle.getLong("goodsId"));
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            productDetailPullRefresh.onRefreshComplete();
                            if (ProductDetailActivity.this.isFinishing()) {
                                return;
                            }
                            JSONUtil<ProductDetailsOutputModel> jsonUtil = new
                                    JSONUtil<ProductDetailsOutputModel>();
                            ProductDetailsOutputModel productDetailsOutput = new
                                    ProductDetailsOutputModel();
                            productDetailsOutput = jsonUtil.toBean(
                                    response.toString(),
                                    productDetailsOutput
                            );
                            if (null != productDetailsOutput && null != productDetailsOutput
                                    .getResultData() && (
                                    1 == productDetailsOutput.getResultCode(
                                    )
                            )) {
                                if (null != productDetailsOutput.getResultData().getData()) {
                                    productDetail = productDetailsOutput
                                            .getResultData().getData();
                                    issueId = productDetail.getIssueId();
                                    detailUrl = productDetail.getLink();
                                    if (0 == productDetail.getStatus()) {
                                        productDetailNameLabel.setText("进行中");
                                    } else if (1 == productDetail.getStatus()) {
                                        productDetailNameLabel.setText("倒计时");
                                    } else if (2 == productDetail.getStatus()) {
                                        productDetailNameLabel.setText("已揭晓");
                                    }
                                    productDetailName.setText(productDetail.getTitle());
                                    //是否登录
                                    if (application.isLogin()) {
                                        //登录
                                        if (3 != productDetail.getStatus()) {
                                            //未揭晓
                                            loginedDetail.setVisibility(View.VISIBLE);

                                            TextView noLabel = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.noLabel);
                                            noLabel.setText("期号：" + productDetail.getIssueId());
                                            ProgressBar progressBar = (ProgressBar)
                                                    ProductDetailActivity.this.findViewById(R.id.partnerProgress);
                                            progressBar.setProgress(
                                                    (int) (
                                                            productDetail
                                                                    .getToAmount
                                                                            () -
                                                                    productDetail
                                                                            .getRemainAmount()
                                                    )
                                            );
                                            progressBar.setMax(
                                                    (int) productDetail.getToAmount(
                                                    )
                                            );
                                            TextView totalRequiredLabel = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.totalRequiredLabel);
                                            totalRequiredLabel.setText(
                                                    "总需人数：" + productDetail
                                                            .getToAmount() +
                                                            "次"
                                            );
                                            TextView surplusLabel = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.surplusLabel);
                                            surplusLabel.setText(
                                                    "剩余：" + productDetail
                                                            .getRemainAmount()
                                            );
                                            TextView parentCount = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.parentCount);
                                            parentCount.setText("您参与了：1人次");
                                            TextView raidersNo = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.raidersNo);
                                            raidersNo.setText("夺宝号码：1000589");
                                        } else {
                                            //揭晓
                                            announcedDetail.setVisibility(View.VISIBLE);
                                            CircleImageView accountLogo = (CircleImageView)
                                                    ProductDetailActivity.this.findViewById(R.id.accountLogo);
                                            BitmapLoader.create().loadRoundImage(
                                                    ProductDetailActivity.this, accountLogo,
                                                    "http://v1.qzone"
                                                            + ".cc/avatar/201404/10/00/12/534571832f9ea304"
                                                            + ".jpg!200x200.jpg", R.mipmap.error
                                            );
                                            TextView winnerName = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerName);
                                            winnerName.setText("中奖者：百晓生");
                                            TextView winnerIp = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerIp);
                                            winnerIp.setText("铁岭：10.10.123.45");
                                            TextView winnerId = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerId);
                                            winnerId.setText("用户ID：23000909");
                                            TextView partnerUser = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.partnerUser);
                                            partnerUser.setText("本期参与：3000次");
                                            TextView partnerTime = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.partnerTime);
                                            partnerTime.setText("揭晓时间：2015-12-11 14:20:11");
                                            TextView luckyNo = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.luckyNo);
                                            luckyNo.setText("幸运号：32890");
                                            TextView calculationDetail = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.calculationDetail);
                                            calculationDetail.setText("幸运号：32890");
                                        }
                                    } else {
                                        //未登陆
                                        if (3 != productDetail.getStatus()) {
                                            //未揭晓
                                                unlogin.setVisibility(View.VISIBLE);
                                            TextView noLabel = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.noLabel);
                                            noLabel.setText("期号：" + productDetail.getIssueId());
                                            ProgressBar progressBar = (ProgressBar)
                                                    ProductDetailActivity.this.findViewById(R.id.partnerProgress);
                                            progressBar.setProgress(
                                                    (int) (
                                                            productDetail
                                                                    .getToAmount
                                                                            () -
                                                                    productDetail
                                                                            .getRemainAmount()
                                                    )
                                            );
                                            progressBar.setMax(
                                                    (int) productDetail.getToAmount(
                                                    )
                                            );
                                            TextView totalRequiredLabel = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.totalRequiredLabel);
                                            totalRequiredLabel.setText(
                                                    "总需人数：" + productDetail
                                                            .getToAmount() +
                                                            "次"
                                            );
                                            TextView surplusLabel = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.surplusLabel);
                                            surplusLabel.setText(
                                                    "剩余：" + productDetail
                                                            .getRemainAmount()
                                            );
                                        } else {
                                            //揭晓

                                            announcedDetail.setVisibility(View.VISIBLE);
                                            CircleImageView accountLogo = (CircleImageView)
                                                    ProductDetailActivity.this.findViewById(R.id.accountLogo);
                                            BitmapLoader.create().loadRoundImage(
                                                    ProductDetailActivity.this, accountLogo,
                                                    "http://v1.qzone"
                                                            + ".cc/avatar/201404/10/00/12/534571832f9ea304"
                                                            + ".jpg!200x200.jpg", R.mipmap.error);
                                            TextView winnerName = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerName);
                                            winnerName.setText("中奖者：百晓生");
                                            TextView winnerIp = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerIp);
                                            winnerIp.setText("铁岭：10.10.123.45");
                                            TextView winnerId = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerId);
                                            winnerId.setText("用户ID：23000909");
                                            TextView partnerUser = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.partnerUser);
                                            partnerUser.setText("本期参与：3000次");
                                            TextView partnerTime = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.partnerTime);
                                            partnerTime.setText("揭晓时间：2015-12-11 14:20:11");
                                            TextView luckyNo = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.luckyNo);
                                            luckyNo.setText("幸运号：32890");
                                            TextView calculationDetail = (TextView) ProductDetailActivity.this.findViewById(R.id.calculationDetail);
                                            calculationDetail.setText("幸运号：32890");
                                        }
                                    }


                                    //加载参与历史
                                    //getCommentLog();
                                } else {
                                    //暂无数据提示
                                }
                            } else {
                                //异常处理，自动切换成无数据
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            productDetailPullRefresh.onRefreshComplete();
                            if (ProductDetailActivity.this.isFinishing()) {
                                return;
                            }
                            //暂无数据提示
                        }
                    }
            );
        }else if (bundle.getInt("tip")==2){
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_DETAIL_BY_ISSUEID;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ProductDetailActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            //商品id
            maps.put("issueId", bundle.getLong("issueId"));
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            productDetailPullRefresh.onRefreshComplete();
                            if (ProductDetailActivity.this.isFinishing()) {
                                return;
                            }
                            JSONUtil<ProductDetailsOutputModel> jsonUtil = new
                                    JSONUtil<ProductDetailsOutputModel>();
                            ProductDetailsOutputModel productDetailsOutput = new
                                    ProductDetailsOutputModel();
                            productDetailsOutput = jsonUtil.toBean(
                                    response.toString(),
                                    productDetailsOutput
                            );
                            if (null != productDetailsOutput && null != productDetailsOutput
                                    .getResultData() && (
                                    1 == productDetailsOutput.getResultCode(
                                    )
                            )) {
                                if (null != productDetailsOutput.getResultData().getData()) {
                                    productDetail = productDetailsOutput
                                            .getResultData().getData();
                                    issueId = productDetail.getIssueId();
                                    detailUrl = productDetail.getLink();
                                    if (0 == productDetail.getStatus()) {
                                        productDetailNameLabel.setText("进行中");
                                    } else if (1 == productDetail.getStatus()) {
                                        productDetailNameLabel.setText("倒计时");
                                    } else if (2 == productDetail.getStatus()) {
                                        productDetailNameLabel.setText("已揭晓");
                                    }
                                    productDetailName.setText(productDetail.getTitle());
                                    //是否登录
                                    if (application.isLogin()) {
                                        //登录
                                        if (3 != productDetail.getStatus()) {
                                            //未揭晓
                                            loginedDetail.setVisibility(View.VISIBLE);
                                            TextView noLabel = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.noLabel);
                                            noLabel.setText("期号：" + productDetail.getIssueId());
                                            ProgressBar progressBar = (ProgressBar)
                                                    ProductDetailActivity.this.findViewById(R.id.partnerProgress);
                                            progressBar.setProgress(
                                                    (int) (
                                                            productDetail
                                                                    .getToAmount
                                                                            () -
                                                                    productDetail
                                                                            .getRemainAmount()
                                                    )
                                            );
                                            progressBar.setMax(
                                                    (int) productDetail.getToAmount(
                                                    )
                                            );
                                            TextView totalRequiredLabel = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.totalRequiredLabel);
                                            totalRequiredLabel.setText(
                                                    "总需人数：" + productDetail
                                                            .getToAmount() +
                                                            "次"
                                            );
                                            TextView surplusLabel = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.surplusLabel);
                                            surplusLabel.setText(
                                                    "剩余：" + productDetail
                                                            .getRemainAmount()
                                            );
                                            TextView parentCount = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.parentCount);
                                            parentCount.setText("您参与了：1人次");
                                            TextView raidersNo = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.raidersNo);
                                            raidersNo.setText("夺宝号码：1000589");
                                        } else {
                                            //揭晓

                                            announcedDetail.setVisibility(View.VISIBLE);
                                            CircleImageView accountLogo = (CircleImageView)
                                                    ProductDetailActivity.this.findViewById(R.id.accountLogo);
                                            BitmapLoader.create().loadRoundImage(
                                                    ProductDetailActivity.this, accountLogo,
                                                    "http://v1.qzone"
                                                            + ".cc/avatar/201404/10/00/12/534571832f9ea304"
                                                            + ".jpg!200x200.jpg", R.mipmap.error
                                            );
                                            TextView winnerName = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerName);
                                            winnerName.setText("中奖者：百晓生");
                                            TextView winnerIp = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerIp);
                                            winnerIp.setText("铁岭：10.10.123.45");
                                            TextView winnerId = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerId);
                                            winnerId.setText("用户ID：23000909");
                                            TextView partnerUser = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.partnerUser);
                                            partnerUser.setText("本期参与：3000次");
                                            TextView partnerTime = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.partnerTime);
                                            partnerTime.setText("揭晓时间：2015-12-11 14:20:11");
                                            TextView luckyNo = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.luckyNo);
                                            luckyNo.setText("幸运号：32890");
                                            TextView calculationDetail = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.calculationDetail);
                                            calculationDetail.setText("幸运号：32890");
                                        }
                                    } else {
                                        //未登陆
                                        if (3 != productDetail.getStatus()) {
                                            //未揭晓

                                            unlogin.setVisibility(View.VISIBLE);
                                            TextView noLabel = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.noLabel);
                                            noLabel.setText("期号：" + productDetail.getIssueId());
                                            ProgressBar progressBar = (ProgressBar)
                                                    ProductDetailActivity.this.findViewById(R.id.partnerProgress);
                                            progressBar.setProgress(
                                                    (int) (
                                                            productDetail
                                                                    .getToAmount
                                                                            () -
                                                                    productDetail
                                                                            .getRemainAmount()
                                                    )
                                            );
                                            progressBar.setMax(
                                                    (int) productDetail.getToAmount(
                                                    )
                                            );
                                            TextView totalRequiredLabel = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.totalRequiredLabel);
                                            totalRequiredLabel.setText(
                                                    "总需人数：" + productDetail
                                                            .getToAmount() +
                                                            "次"
                                            );
                                            TextView surplusLabel = (TextView)
                                                    ProductDetailActivity.this.findViewById(R.id.surplusLabel);
                                            surplusLabel.setText(
                                                    "剩余：" + productDetail
                                                            .getRemainAmount()
                                            );
                                        } else {
                                            //揭晓

                                            announcedDetail.setVisibility(View.VISIBLE);
                                            CircleImageView accountLogo = (CircleImageView)
                                                    ProductDetailActivity.this.findViewById(R.id.accountLogo);
                                            BitmapLoader.create().loadRoundImage(
                                                    ProductDetailActivity.this, accountLogo,
                                                    "http://v1.qzone"
                                                            + ".cc/avatar/201404/10/00/12/534571832f9ea304"
                                                            + ".jpg!200x200.jpg", R.mipmap.error);
                                            TextView winnerName = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerName);
                                            winnerName.setText("中奖者：百晓生");
                                            TextView winnerIp = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerIp);
                                            winnerIp.setText("铁岭：10.10.123.45");
                                            TextView winnerId = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.winnerId);
                                            winnerId.setText("用户ID：23000909");
                                            TextView partnerUser = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.partnerUser);
                                            partnerUser.setText("本期参与：3000次");
                                            TextView partnerTime = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.partnerTime);
                                            partnerTime.setText("揭晓时间：2015-12-11 14:20:11");
                                            TextView luckyNo = (TextView) ProductDetailActivity
                                                    .this.findViewById(R.id.luckyNo);
                                            luckyNo.setText("幸运号：32890");
                                            TextView calculationDetail = (TextView) ProductDetailActivity.this.findViewById(R.id.calculationDetail);
                                            calculationDetail.setText("幸运号：32890");
                                        }
                                    }


                                    //加载参与历史
                                    //getCommentLog();
                                } else {
                                    //暂无数据提示
                                }
                            } else {
                                //异常处理，自动切换成无数据
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            productDetailPullRefresh.onRefreshComplete();
                            if (ProductDetailActivity.this.isFinishing()) {
                                return;
                            }
                            //暂无数据提示
                        }
                    }
            );

        }
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf ( ProductDetailActivity.this );
    }

    @OnClick(R.id.graphicDetails)
    void showDetail()
    {
        Bundle bundle = new Bundle();
        bundle.putString("link", detailUrl);
        bundle.putString("title", "图文详情");
        ActivityUtils.getInstance().showActivity(ProductDetailActivity.this, WebExhibitionActivity.class, bundle);
    }

    @OnClick(R.id.sdL)
    void doShareOrder()
    {
        Bundle bundle = new Bundle (  );
        //产品晒单
        bundle.putInt("type", 2);
        bundle.putLong("goodsId", productDetail.getPid());
        ActivityUtils.getInstance().showActivity(ProductDetailActivity.this, ShowOrderActivity.class, bundle);
    }

    private void getCommentLog()
    {
        if( false == ProductDetailActivity.this.canConnect() ){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    productDetailPullRefresh.onRefreshComplete();
                }
            });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_BUY_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ProductDetailActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        //全部
        maps.put("issueId", issueId);
        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            maps.put("lastId", 0);
        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( partnerHistorys != null && partnerHistorys.size() > 0)
            {
                PartnerHistorysModel partnerHistory = partnerHistorys.get(partnerHistorys.size() - 1);
                maps.put("lastId", partnerHistory.getPid());
            } else if (partnerHistorys != null && partnerHistorys.size() == 0)
            {
                maps.put("lastId", 0);
            }else if (partnerHistorys==null){
                maps.put("lastId", 0);
            }
        }
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                productDetailPullRefresh.onRefreshComplete();
                if (ProductDetailActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<PartnerHistorysOutputModel> jsonUtil = new JSONUtil<PartnerHistorysOutputModel>();
                PartnerHistorysOutputModel partnerHistory = new PartnerHistorysOutputModel();
                partnerHistory = jsonUtil.toBean(response.toString(), partnerHistory);
                if (null != partnerHistory && null != partnerHistory.getResultData() && (1 == partnerHistory.getResultCode())) {
                    if (null != partnerHistory.getResultData().getList() && !partnerHistory.getResultData().getList().isEmpty()) {
                        if (operateType == OperateTypeEnum.REFRESH) {
                            partnerLogL.removeAllViews();
                        } else if (operateType == OperateTypeEnum.LOADMORE) {
                        }
                        partnerHistorys = partnerHistory.getResultData().getList();
                        int size = partnerHistorys.size();
                        for (int i = 0; i < size; i++) {
                            PartnerHistorysModel partnerLog = partnerHistorys.get(i);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup
                                    .LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            RelativeLayout parntersLayout = (RelativeLayout) LayoutInflater.from(ProductDetailActivity.this).inflate(R.layout.partner_log, null);
                            TextView bgLine = (TextView) parntersLayout.findViewById(R.id.bgLine);
                            bgLine.setMinimumHeight(200);
                            CircleImageView partnerLogo = (CircleImageView) parntersLayout.findViewById(R.id.partnerLogo);
                            BitmapLoader.create().loadRoundImage(ProductDetailActivity.this, partnerLogo, partnerLog.getUserHeadUrl(), R.mipmap.error);
                            TextView partnerName = (TextView) parntersLayout.findViewById(R.id.partnerName);
                            partnerName.setText(partnerLog.getNickName());
                            TextView partnerIp = (TextView) parntersLayout.findViewById(R.id.partnerIp);
                            partnerIp.setText("（" + partnerLog.getCity() + " " + partnerLog.getIp() + "）");
                            TextView partnerCount = (TextView) parntersLayout.findViewById(R.id.partnerCount);
                            partnerCount.setText("参与了" + partnerLog.getAttendAmount() + "人次");
                            TextView partnerTime = (TextView) parntersLayout.findViewById(R.id.partnerTime);
                            partnerTime.setText(DateUtils.transformDataformat2(partnerLog.getDate()));
                            lp.setMargins(0, 0, 0, 0);
                            parntersLayout.setLayoutParams(lp);
                            partnerLogL.addView(parntersLayout);
                        }
                    } else {
                        //数据为空
                    }
                } else {
                    //异常处理，自动切换成无数据
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                productDetailPullRefresh.onRefreshComplete();
                if (ProductDetailActivity.this.isFinishing()) {
                    return;
                }
                //数据为空
            }
        });
    }

    /**
     * 初始化加载数据
     */
    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ProductDetailActivity.this.isFinishing()) return;
                operateType = OperateTypeEnum.REFRESH;
                productDetailPullRefresh.setRefreshing(true);
            }
        }, 1000);
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
        imgs = bundle.getStringArrayList("imgs");
        initDots();
        //通过适配器引入图片
        productDetailViewPager.setAdapter(new LoadSwitchImgAdapter(imgs, ProductDetailActivity.this));
        int centerValue=Integer.MAX_VALUE/2;
        int value=centerValue%imgs.size();
        productDetailViewPager.setCurrentItem(centerValue - value);
        initListener();
        //更新文本内容
        updateTextAndDot();
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
            productDetaildot.getChildAt(i).setEnabled(i == currentPage);
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

    @OnClick(R.id.historys)
   void tohistorys (){
        Bundle bundle = new Bundle ( );
        bundle.putLong("goodsId",  productDetail.getPid());
        ActivityUtils.getInstance ().showActivity ( ProductDetailActivity.this, HistorysActivity.class,bundle);


    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}

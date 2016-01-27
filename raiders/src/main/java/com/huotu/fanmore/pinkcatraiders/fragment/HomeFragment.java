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
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.HomeViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.TabPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AdEntity;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductsOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.product.AreaActivity;
import com.huotu.fanmore.pinkcatraiders.ui.product.ProductDetailActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.BuyLogActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RaidesLogActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.MarqueenTextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页UI
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

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
    @Bind(R.id.productsL)
    LinearLayout productsL;
    @Bind(R.id.productSwitch)
    LinearLayout productSwitch;
    @Bind(R.id.rqInnerL)
    RelativeLayout rqInnerL;
    @Bind(R.id.zxInnerL)
    RelativeLayout zxInnerL;
    @Bind(R.id.jdInnerL)
    RelativeLayout jdInnerL;
    @Bind(R.id.zxrsInnerL)
    RelativeLayout zxrsInnerL;
    @Bind(R.id.rqLabel)
    TextView rqLabel;
    @Bind(R.id.zxLabel)
    TextView zxLabel;
    @Bind(R.id.jdLabel)
    TextView jdLabel;
    @Bind(R.id.zxrsLabel)
    TextView zxrsLabel;
    @Bind(R.id.homePullRefresh)
    PullToRefreshScrollView homePullRefresh;
    private int currentIndex = 0;
    public WindowManager wManager;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;

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
        application.proFragManager = FragManager.getIns(getActivity(), R.id.productsL);
        application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
        wManager = getActivity().getWindowManager();
        initView();
        iniScroll();
        return rootView;
    }

    private void iniScroll()
    {
        homePullRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                operateType = OperateTypeEnum.REFRESH;
                initProduct();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                operateType = OperateTypeEnum.LOADMORE;
                initProduct();
            }
        });
        homePullRefresh.getRefreshableView().smoothScrollTo(0, 0);
    }

    private void initView()
    {
        currentIndex = 0;
        application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.deep_red));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        SystemTools.loadBackground(rqInnerL, press);
        SystemTools.loadBackground(zxInnerL, normal);
        SystemTools.loadBackground(jdInnerL, normal);
        SystemTools.loadBackground(zxrsInnerL, normal);
        initSwitchImg();
        firstGetData();
    }

    @OnClick(R.id.lbL)
    void showCatagoryUi()
    {
        //ToastUtils.showLongToast(getActivity(), "弹出分类界面");
        ActivityUtils.getInstance().showActivity(getActivity(), RaidesLogActivity.class);
    }

    @OnClick(R.id.zqL)
    void showZqUi()
    {
        Bundle bundle = new Bundle();
        bundle.putLong("step", 10);
        ActivityUtils.getInstance().showActivity(getActivity(), AreaActivity.class, bundle);
    }

    @OnClick(R.id.sdL)
    void showSdUi()
    {
        //ToastUtils.showLongToast(getActivity(), "弹出晒单界面");
        //购买记录
        ActivityUtils.getInstance().showActivity(getActivity(), BuyLogActivity.class);
    }

    @OnClick(R.id.wtL)
    void showWtUi()
    {
        Bundle bundle = new Bundle();
        bundle.putLong("goodsId", 0l);
        ActivityUtils.getInstance().showActivity(getActivity(), ProductDetailActivity.class, bundle);
    }

    private void initProduct()
    {

        if( false == rootAty.canConnect() ){
            rootAty.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    homePullRefresh.onRefreshComplete();
                }
            });
            return;
        }
        if(0==currentIndex)
        {
            //人气
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "1");
            if ( OperateTypeEnum.REFRESH == operateType )
            {// 下拉
                maps.put("lastSort", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType)
            {// 上拉
                if ( rootAty.popProducts != null && rootAty.popProducts.size() > 0)
                {
                    ProductModel product = rootAty.popProducts.get(rootAty.popProducts.size() - 1);
                    maps.put("lastSort", product.getPid());
                } else if (rootAty.popProducts != null && rootAty.popProducts.size() == 0)
                {
                    maps.put("lastSort", 0);
                }
            }
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    homePullRefresh.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    JSONUtil<ProductsOutputModel> jsonUtil = new JSONUtil<ProductsOutputModel>();
                    ProductsOutputModel productsOutputs = new ProductsOutputModel();
                    productsOutputs = jsonUtil.toBean(response.toString(), productsOutputs);
                    if (null != productsOutputs && null != productsOutputs.getResultData() && (1 == productsOutputs.getResultCode())) {
                        if (null != productsOutputs.getResultData().getList() && !productsOutputs.getResultData().getList().isEmpty()) {

                            if (operateType == OperateTypeEnum.REFRESH) {
                                rootAty.popProducts.clear();
                                rootAty.popProducts.addAll(productsOutputs.getResultData().getList());
                                rootAty.popAdapter.notifyDataSetChanged();
                            } else if (operateType == OperateTypeEnum.LOADMORE) {
                                rootAty.popProducts.addAll(productsOutputs.getResultData().getList());
                                rootAty.popAdapter.notifyDataSetChanged();
                            }
                        } else {
                            //空数据处理
                        }
                    } else {
                        //异常处理，自动切换成无数据
                        //空数据处理
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    homePullRefresh.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    //空数据处理
                }
            });

        }else if(1==currentIndex)
        {
            //最新
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "2");
            if ( OperateTypeEnum.REFRESH == operateType )
            {// 下拉
                maps.put("lastSort", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType)
            {// 上拉
                if ( rootAty.newestProducts != null && rootAty.newestProducts.size() > 0)
                {
                    ProductModel product = rootAty.newestProducts.get(rootAty.newestProducts.size() - 1);
                    maps.put("lastSort", product.getPid());
                } else if (rootAty.newestProducts != null && rootAty.newestProducts.size() == 0)
                {
                    maps.put("lastSort", 0);
                }
            }
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    homePullRefresh.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    JSONUtil<ProductsOutputModel> jsonUtil = new JSONUtil<ProductsOutputModel>();
                    ProductsOutputModel productsOutputs = new ProductsOutputModel();
                    productsOutputs = jsonUtil.toBean(response.toString(), productsOutputs);
                    if (null != productsOutputs && null != productsOutputs.getResultData() && (1 == productsOutputs.getResultCode())) {
                        if (null != productsOutputs.getResultData().getList() && !productsOutputs.getResultData().getList().isEmpty()) {

                            if (operateType == OperateTypeEnum.REFRESH) {
                                rootAty.newestProducts.clear();
                                rootAty.newestProducts.addAll(productsOutputs.getResultData().getList());
                                rootAty.newestAdapter.notifyDataSetChanged();
                            } else if (operateType == OperateTypeEnum.LOADMORE) {
                                rootAty.newestProducts.addAll(productsOutputs.getResultData().getList());
                                rootAty.newestAdapter.notifyDataSetChanged();
                            }
                        } else {
                            //空数据处理
                        }
                    } else {
                        //异常处理，自动切换成无数据
                        //空数据处理
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    homePullRefresh.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    //空数据处理
                }
            });
        } else if(2==currentIndex)
        {
            //进度
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "3");
            if ( OperateTypeEnum.REFRESH == operateType )
            {// 下拉
                maps.put("lastSort", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType)
            {// 上拉
                if ( rootAty.progressProducts != null && rootAty.progressProducts.size() > 0)
                {
                    ProductModel product = rootAty.progressProducts.get(rootAty.progressProducts.size() - 1);
                    maps.put("lastSort", product.getPid());
                } else if (rootAty.progressProducts != null && rootAty.progressProducts.size() == 0)
                {
                    maps.put("lastSort", 0);
                }
            }
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    homePullRefresh.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    JSONUtil<ProductsOutputModel> jsonUtil = new JSONUtil<ProductsOutputModel>();
                    ProductsOutputModel productsOutputs = new ProductsOutputModel();
                    productsOutputs = jsonUtil.toBean(response.toString(), productsOutputs);
                    if (null != productsOutputs && null != productsOutputs.getResultData() && (1 == productsOutputs.getResultCode())) {
                        if (null != productsOutputs.getResultData().getList() && !productsOutputs.getResultData().getList().isEmpty()) {

                            if (operateType == OperateTypeEnum.REFRESH) {
                                rootAty.progressProducts.clear();
                                rootAty.progressProducts.addAll(productsOutputs.getResultData().getList());
                                rootAty.progressAdapter.notifyDataSetChanged();
                            } else if (operateType == OperateTypeEnum.LOADMORE) {
                                rootAty.progressProducts.addAll(productsOutputs.getResultData().getList());
                                rootAty.progressAdapter.notifyDataSetChanged();
                            }
                        } else {
                            //空数据处理
                        }
                    } else {
                        //异常处理，自动切换成无数据
                        //空数据处理
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    homePullRefresh.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    //空数据处理
                }
            });
        } else if(3==currentIndex)
        {
            //总需
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "4");
            if ( OperateTypeEnum.REFRESH == operateType )
            {// 下拉
                maps.put("lastSort", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType)
            {// 上拉
                if ( rootAty.totalProducts != null && rootAty.totalProducts.size() > 0)
                {
                    ProductModel product = rootAty.totalProducts.get(rootAty.totalProducts.size() - 1);
                    maps.put("lastSort", product.getPid());
                } else if (rootAty.totalProducts != null && rootAty.totalProducts.size() == 0)
                {
                    maps.put("lastSort", 0);
                }
            }
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    homePullRefresh.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    JSONUtil<ProductsOutputModel> jsonUtil = new JSONUtil<ProductsOutputModel>();
                    ProductsOutputModel productsOutputs = new ProductsOutputModel();
                    productsOutputs = jsonUtil.toBean(response.toString(), productsOutputs);
                    if (null != productsOutputs && null != productsOutputs.getResultData() && (1 == productsOutputs.getResultCode())) {
                        if (null != productsOutputs.getResultData().getList() && !productsOutputs.getResultData().getList().isEmpty()) {

                            if (operateType == OperateTypeEnum.REFRESH) {
                                rootAty.totalProducts.clear();
                                rootAty.totalProducts.addAll(productsOutputs.getResultData().getList());
                                rootAty.totalAdapter.notifyDataSetChanged();
                            } else if (operateType == OperateTypeEnum.LOADMORE) {
                                rootAty.totalProducts.addAll(productsOutputs.getResultData().getList());
                                rootAty.totalAdapter.notifyDataSetChanged();
                            }
                        } else {
                            //空数据处理
                        }
                    } else {
                        //异常处理，自动切换成无数据
                        //空数据处理
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    homePullRefresh.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    //空数据处理
                }
            });
        }

    }

    @OnClick(R.id.rqInnerL)
    void clickRql()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.deep_red));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        SystemTools.loadBackground(rqInnerL, press);
        SystemTools.loadBackground(zxInnerL, normal);
        SystemTools.loadBackground(jdInnerL, normal);
        SystemTools.loadBackground(zxrsInnerL, normal);
        firstGetData();
    }
    @OnClick(R.id.zxInnerL)
    void clickZxl()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.NEWEST_PRODUCT);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.text_black));
        zxLabel.setTextColor(resources.getColor(R.color.deep_red));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        SystemTools.loadBackground(rqInnerL, normal);
        SystemTools.loadBackground(zxInnerL, press);
        SystemTools.loadBackground(jdInnerL, normal);
        SystemTools.loadBackground(zxrsInnerL, normal);
        firstGetData();
    }
    @OnClick(R.id.jdInnerL)
    void clickJdl()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.PROGRESS);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.text_black));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.deep_red));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        SystemTools.loadBackground(rqInnerL, normal);
        SystemTools.loadBackground(zxInnerL, normal);
        SystemTools.loadBackground(jdInnerL, press);
        SystemTools.loadBackground(zxrsInnerL, normal);
        firstGetData();
    }
    @OnClick(R.id.zxrsInnerL)
    void clickZxrsl()
    {
        application.proFragManager.setCurrentFrag(FragManager.FragType.TOTAL);
        Drawable normal = resources.getDrawable(R.drawable.switch_normal);
        Drawable press = resources.getDrawable(R.drawable.switch_press);
        rqLabel.setTextColor(resources.getColor(R.color.text_black));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.deep_red));
        SystemTools.loadBackground(rqInnerL, normal);
        SystemTools.loadBackground(zxInnerL, normal);
        SystemTools.loadBackground(jdInnerL, normal);
        SystemTools.loadBackground(zxrsInnerL, press);
        firstGetData();
    }

    /**
     * 初始化加载数据
     */
    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) return;
                operateType = OperateTypeEnum.REFRESH;
                homePullRefresh.setRefreshing(true);
            }
        }, 1000);
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
            dot.getChildAt(i).setEnabled(i == currentPage);
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
        VolleyUtil.cancelAllRequest();
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

    public Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                {
                    homeViewPager.setCurrentItem(homeViewPager.getCurrentItem()+1);
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                }
                break;
                default:
                    break;
            }
        }


    };

}

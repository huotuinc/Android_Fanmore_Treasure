package com.huotu.fanmore.pinkcatraiders.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.HomeViewPagerAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.CarouselModel;
import com.huotu.fanmore.pinkcatraiders.model.NoticeModel;
import com.huotu.fanmore.pinkcatraiders.model.NoticeOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductsOutputModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.WebExhibitionActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.login.LoginActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.ShowOrderActivity;
import com.huotu.fanmore.pinkcatraiders.ui.product.AreaActivity;
import com.huotu.fanmore.pinkcatraiders.ui.product.CateGoryActivity;
import com.huotu.fanmore.pinkcatraiders.ui.redpackage.ReadPackageActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页UI
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, MyBroadcastReceiver.BroadcastListener {


    public HomeActivity rootAty;

    @Bind(R.id.homeViewPager)
    ViewPager homeViewPager;

    @Bind(R.id.dot)
    LinearLayout dot;

    List<CarouselModel> adDataList = null;

    @Bind(R.id.homeHornText)
    ViewFlipper homeHornText;

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

    @Bind(R.id.zxrsLogo)
    ImageView zxrsLogo;
    @Bind(R.id.zxrsLabel)
    TextView zxrsLabel;

    @Bind(R.id.homePullRefresh)
    PullToRefreshScrollView homePullRefresh;

    private int currentIndex = 0;

    public OperateTypeEnum operateType = OperateTypeEnum.REFRESH;

    boolean init;
    private MyBroadcastReceiver broadcastReceiver;

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.home_frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!init) {
            rootAty = (HomeActivity) getActivity();
            application.proFragManager = FragManager.getIns(getActivity(), R.id.productsL);
            application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
            broadcastReceiver = new MyBroadcastReceiver(getActivity(), this, MyBroadcastReceiver.GO_TO_HOMEFRAG);
            zxrsInnerL.setTag(0);
            SystemTools.loadBackground(zxrsLogo, resources.getDrawable(R.mipmap.sort_down));
            initView();
            iniScroll();
            init = true;
        }
    }

    private void iniScroll() {

        homePullRefresh.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

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
                }
        );
        scrollToTop();
    }

    private void initView() {
        currentIndex = 0;
        application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
        rqLabel.setTextColor(resources.getColor(R.color.title_bg));
        zxLabel.setTextColor(resources.getColor(R.color.text_black));
        jdLabel.setTextColor(resources.getColor(R.color.text_black));
        zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
        initSwitchImg();
        mHandler.sendEmptyMessage(0x22113344);
        firstGetData();
    }

    @OnClick({R.id.rqInnerL, R.id.zxInnerL, R.id.jdInnerL, R.id.zxrsInnerL})
    void click(View view) {
        int clickId = view.getId();
        switch (clickId) {
            case R.id.rqInnerL:
                operateType = OperateTypeEnum.REFRESH;
                currentIndex = 0;
                application.proFragManager.setCurrentFrag(FragManager.FragType.POPULAR);
                rqLabel.setTextColor(resources.getColor(R.color.title_bg));
                zxLabel.setTextColor(resources.getColor(R.color.text_black));
                jdLabel.setTextColor(resources.getColor(R.color.text_black));
                zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
                initProduct();
                scrollToTop();
                break;
            case R.id.zxInnerL:
                operateType = OperateTypeEnum.REFRESH;
                currentIndex = 1;
                application.proFragManager.setCurrentFrag(FragManager.FragType.NEWEST_PRODUCT);
                rqLabel.setTextColor(resources.getColor(R.color.text_black));
                zxLabel.setTextColor(resources.getColor(R.color.title_bg));
                jdLabel.setTextColor(resources.getColor(R.color.text_black));
                zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
                initProduct();
                scrollToTop();
                break;
            case R.id.jdInnerL:
                operateType = OperateTypeEnum.REFRESH;
                currentIndex = 2;
                application.proFragManager.setCurrentFrag(FragManager.FragType.PROGRESS);
                rqLabel.setTextColor(resources.getColor(R.color.text_black));
                zxLabel.setTextColor(resources.getColor(R.color.text_black));
                jdLabel.setTextColor(resources.getColor(R.color.title_bg));
                zxrsLabel.setTextColor(resources.getColor(R.color.text_black));
                initProduct();
                scrollToTop();
                break;
            case R.id.zxrsInnerL:
                operateType = OperateTypeEnum.REFRESH;
                if (0 == Integer.parseInt(zxrsInnerL.getTag().toString())) {
                    //转换成UP
                    zxrsInnerL.setTag(1);
                    SystemTools.loadBackground(zxrsLogo, resources.getDrawable(R.mipmap.sort_up));
                    currentIndex = 4;
                    application.proFragManager.setCurrentFrag(FragManager.FragType.TOTAL);
                    rqLabel.setTextColor(resources.getColor(R.color.text_black));
                    zxLabel.setTextColor(resources.getColor(R.color.text_black));
                    jdLabel.setTextColor(resources.getColor(R.color.text_black));
                    zxrsLabel.setTextColor(resources.getColor(R.color.title_bg));

                } else if (1 == Integer.parseInt(zxrsInnerL.getTag().toString())) {
                    //转换成DOWN
                    zxrsInnerL.setTag(0);
                    SystemTools.loadBackground(zxrsLogo, resources.getDrawable(R.mipmap.sort_down));
                    currentIndex = 3;
                    application.proFragManager.setCurrentFrag(FragManager.FragType.TOTAL);
                    rqLabel.setTextColor(resources.getColor(R.color.text_black));
                    zxLabel.setTextColor(resources.getColor(R.color.text_black));
                    jdLabel.setTextColor(resources.getColor(R.color.text_black));
                    zxrsLabel.setTextColor(resources.getColor(R.color.title_bg));
                }
                initProduct();
                scrollToTop();
                break;
        }
    }

    public void scrollToTop(){
        homePullRefresh.getRefreshableView().smoothScrollTo(0, 0);
    }

    @OnClick({R.id.lbL, R.id.zqL, R.id.redPackageL, R.id.sdL, R.id.wtL})
    void click2(View view) {
        int click2Id = view.getId();
        Bundle bundle = null;
        switch (click2Id) {
            case R.id.lbL:
                ActivityUtils.getInstance().showActivity(getActivity(), CateGoryActivity.class);
                break;
            case R.id.zqL:
                bundle = new Bundle();
                ActivityUtils.getInstance().showActivity(getActivity(), AreaActivity.class);
                break;
            case R.id.redPackageL:
                if (!application.isLogin()) {
                    ActivityUtils.getInstance().showActivity(getActivity(), LoginActivity.class);
                } else {
                    bundle = new Bundle();
                    ActivityUtils.getInstance().showActivity(getActivity(), ReadPackageActivity.class, bundle);
                }
                break;
            case R.id.sdL:
                bundle = new Bundle();
                //首页晒单
                bundle.putInt("type", 0);
                ActivityUtils.getInstance().showActivity(getActivity(), ShowOrderActivity.class, bundle);
                break;
            case R.id.wtL:
                bundle = new Bundle();
                bundle.putString("title", "常见问题");
                bundle.putString("link", application.readHelpURL());
                ActivityUtils.getInstance().showActivity(getActivity(), WebExhibitionActivity.class,
                        bundle);
                break;
        }
    }

    private void initProduct() {

        if (false == rootAty.canConnect()) {
            rootAty.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    homePullRefresh.onRefreshComplete();
                }
            });
            return;
        }
        if (0 == currentIndex) {
            //人气
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "1");
            if (OperateTypeEnum.REFRESH == operateType) {// 下拉
                maps.put("lastSort", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType) {// 上拉
                if (rootAty.popProducts != null && rootAty.popProducts.size() > 0) {
                    ProductModel product = rootAty.popProducts.get(0);
                    maps.put("lastSort", product.getSort());
                } else {
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
                            if (rootAty.popProducts != null && rootAty.popProducts.size() > 0) {
                                rootAty.popProducts.get(0).setSort(productsOutputs.getResultData().getSort());
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

        } else if (1 == currentIndex) {
            //最新
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "2");
            if (OperateTypeEnum.REFRESH == operateType) {// 下拉
                maps.put("lastSort", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType) {// 上拉
                if (rootAty.newestProducts != null && rootAty.newestProducts.size() > 0) {
                    ProductModel product = rootAty.newestProducts.get(0);
                    maps.put("lastSort", product.getSort());
                } else {
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
                            if (rootAty.newestProducts != null && rootAty.newestProducts.size() > 0) {
                                rootAty.newestProducts.get(0).setSort(productsOutputs.getResultData().getSort());
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
        } else if (2 == currentIndex) {
            //进度
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "3");
            if (OperateTypeEnum.REFRESH == operateType) {// 下拉
                maps.put("lastSort", 100);
            } else if (OperateTypeEnum.LOADMORE == operateType) {// 上拉
                if (rootAty.progressProducts != null && rootAty.progressProducts.size() > 0) {
                    ProductModel product = rootAty.progressProducts.get(0);
                    maps.put("lastSort", product.getSort());
                } else {
                    maps.put("lastSort", 100);
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
                            if (rootAty.progressProducts != null && rootAty.progressProducts.size() > 0) {
                                rootAty.progressProducts.get(0).setSort(productsOutputs.getResultData().getSort());
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
        } else if (3 == currentIndex) {
            //总需倒序
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "4");
            if (OperateTypeEnum.REFRESH == operateType) {// 下拉
                maps.put("lastSort", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType) {// 上拉
                if (rootAty.totalProducts != null && rootAty.totalProducts.size() > 0) {
                    ProductModel product = rootAty.totalProducts.get(0);
                    maps.put("lastSort", product.getSort());
                } else {
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
                            if (rootAty.totalProducts != null && rootAty.totalProducts.size() > 0) {
                                rootAty.totalProducts.get(0).setSort(productsOutputs.getResultData().getSort());
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
        } else if (4 == currentIndex) {
            //总需正序
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_INDEX;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("type", "5");
            if (OperateTypeEnum.REFRESH == operateType) {// 下拉
                maps.put("lastSort", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType) {// 上拉
                if (rootAty.totalProducts != null && rootAty.totalProducts.size() > 0) {
                    ProductModel product = rootAty.totalProducts.get(0);
                    maps.put("lastSort", product.getSort());
                } else {
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
                            if (rootAty.totalProducts != null && rootAty.totalProducts.size() > 0) {
                                rootAty.totalProducts.get(0).setSort(productsOutputs.getResultData().getSort());
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

    /**
     * 初始化加载数据
     */
    protected void firstGetData() {
        mHandler.postDelayed(
                new Runnable() {

                    @Override
                    public void run() {

                        if (rootAty.isFinishing()) return;
                        operateType = OperateTypeEnum.REFRESH;
                        homePullRefresh.setRefreshing(true);
                    }
                }, 1000
        );
    }

    private void initNotice() {
        //滚动消息
        String url = Contant.REQUEST_URL + Contant.GET_NOTICE_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
        Map<String, Object> maps = new HashMap<String, Object>();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(
                url, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONUtil<NoticeOutputModel> jsonUtil = new JSONUtil<NoticeOutputModel>();
                        NoticeOutputModel noticeOutput = new NoticeOutputModel();
                        noticeOutput = jsonUtil.toBean(response.toString(), noticeOutput);
                        if (null != noticeOutput && null != noticeOutput.getResultData() && (1 == noticeOutput.getResultCode())) {
                            if (null != noticeOutput.getResultData().getList() && !noticeOutput.getResultData().getList().isEmpty()) {
                                List<TextView> list = new ArrayList<TextView>();
                                Iterator<NoticeModel> iterator = noticeOutput.getResultData().getList().iterator();
                                while (iterator.hasNext()) {
                                    NoticeModel notice = iterator.next();
                                    TextView textView = new TextView(rootAty);
                                    textView.setSingleLine(true);
                                    textView.setEllipsize(TextUtils.TruncateAt.END);
                                    textView.setText(Html.fromHtml("<font color=\"#54595f\">恭喜</font><font color=\"#0c83d4\">" + notice.getName() + "</font><font color=\"#54595f\">在</font><font color=\"#d4750c\">" + DateUtils.getMinHome(notice.getTime()) + "</font><font color=\"#54595f\">获得</font><font color=\"#0c83d4\">" + notice.getTitle() + "</font>"));
                                    list.add(textView);
                                }

                                Iterator<TextView> it = list.iterator();
                                while (it.hasNext()) {
                                    homeHornText.addView(it.next());
                                }

                                // 设置文字in/out的动画效果
                                homeHornText.setInAnimation(rootAty, R.anim.push_up_in);
                                homeHornText.setOutAnimation(rootAty, R.anim.push_up_out);
                                homeHornText.startFlipping();

                                mHandler.sendEmptyMessageDelayed(1, 600000);
                            } else {
                                //
                            }
                        } else {

                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
    }

    private void initSwitchImg() {
        adDataList = new ArrayList<CarouselModel>();
        //读取轮播图片实体
        Iterator<CarouselModel> iterator = CarouselModel.findAll(CarouselModel.class);
        while (iterator.hasNext()) {
            adDataList.add(iterator.next());
        }
        initDots();
        //通过适配器引入图片
        homeViewPager.setAdapter(new HomeViewPagerAdapter(adDataList, getActivity(), rootAty.mHandler));
        int centerValue = Integer.MAX_VALUE / 2;
        int value = centerValue % adDataList.size();
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
    private void updateTextAndDot() {
        int currentPage = homeViewPager.getCurrentItem() % adDataList.size();

        //改变dot
        for (int i = 0; i < dot.getChildCount(); i++) {
            dot.getChildAt(i).setEnabled(i == currentPage);
        }

    }

    private void initDots() {
        for (int i = 0; i < adDataList.size(); i++) {
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i != 0) {
                params.leftMargin = 5;
            }

            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.selecter_dot);
            dot.addView(view);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != broadcastReceiver) {
            broadcastReceiver.unregisterReceiver();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(getActivity());
        VolleyUtil.cancelAllRequest();
        if (null != mHandler) {
            mHandler.removeMessages(0);
            mHandler.removeMessages(1);
        }
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

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    homeViewPager.setCurrentItem(homeViewPager.getCurrentItem() + 1);
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                }
                break;
                case 0x22113344: {
                    initNotice();
                    mHandler.sendEmptyMessageDelayed(0x22113344, 60000);
                }
                break;
                default:
                    break;
            }
        }


    };

    @Override
    public void onFinishReceiver(MyBroadcastReceiver.ReceiverType type, Object msg) {
        if (type == MyBroadcastReceiver.ReceiverType.goToHomeFrag) {
            scrollToTop();
        }
    }
}

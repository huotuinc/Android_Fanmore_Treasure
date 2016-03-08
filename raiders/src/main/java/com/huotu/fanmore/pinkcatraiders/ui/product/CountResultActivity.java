package com.huotu.fanmore.pinkcatraiders.ui.product;


import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.CountResultListAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.CountResultModel;
import com.huotu.fanmore.pinkcatraiders.model.CountResultOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.UserNumberModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
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
 * 计算结果
 */
public class CountResultActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
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
    @Bind(R.id.numberA)
    TextView numberA;
    @Bind(R.id.numberB)
    TextView numberB;
    @Bind(R.id.issueNo)
    TextView issueNo;
    @Bind(R.id.luckNumber)
    TextView luckNumber;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public Bundle bundle;
    public Handler mHandler;
    public List<UserNumberModel> numlist;
    public CountResultListAdapter  countResultList;
    @Bind(R.id.list)
    ListView list;
    @Bind(R.id.productDetailPullRefresh)
    PullToRefreshScrollView productDetailPullRefresh;
    public CountResultModel CountResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_count_result);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        wManager = this.getWindowManager();
        bundle = this.getIntent().getExtras();
        mHandler = new Handler(this);
        list.setEnabled(false);
        initTitle();
        initView();
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
        titleText.setText("计算结果");
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
                    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                    }


                }
        );
        numlist = new ArrayList<UserNumberModel>();
        countResultList = new CountResultListAdapter(numlist, CountResultActivity.this,mHandler);
        list.setAdapter(countResultList);
        firstGetData();
        productDetailPullRefresh.getRefreshableView().smoothScrollTo(0, 0);
    }
    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CountResultActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                productDetailPullRefresh.setRefreshing(true);
            }
        }, 1000);
    }
    private void getDetailData() {
        //获取产品详情
        if (false == CountResultActivity.this.canConnect()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    productDetailPullRefresh.onRefreshComplete();
                }
            });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_COUNT_RESULT_BY_ISSUEID;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), CountResultActivity.this);
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
                        if (CountResultActivity.this.isFinishing()) {
                            return;
                        }
                        JSONUtil<CountResultOutputModel> jsonUtil = new
                                JSONUtil<CountResultOutputModel>();
                        CountResultOutputModel countResultOutputModel = new
                                CountResultOutputModel();
                        countResultOutputModel = jsonUtil.toBean(
                                response.toString(),
                                countResultOutputModel
                        );
                        if (null != countResultOutputModel && null != countResultOutputModel
                                .getResultData() && (
                                1 == countResultOutputModel.getResultCode(
                                )
                        )) {
                            if (null != countResultOutputModel.getResultData().getData()) {
                                CountResult = countResultOutputModel
                                        .getResultData().getData();
                                luckNumber.setText(String.valueOf(CountResult.getLuckNumber()));
                                numberA.setText(String.valueOf(CountResult.getNumberA()));
                                numberB.setText(String.valueOf(CountResult.getNumberB()));
                                issueNo.setText(CountResult.getIssueNo());
                                numlist.clear();
                                numlist.addAll(CountResult.getUserNumbers());
                                countResultList.notifyDataSetChanged();


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
                        if (CountResultActivity.this.isFinishing()) {
                            return;
                        }
                        //暂无数据提示
                    }
                }
        );
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
        closeSelf(CountResultActivity.this);
    }


    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

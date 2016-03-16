package com.huotu.fanmore.pinkcatraiders.ui.product;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.PastListAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.PastListModel;
import com.huotu.fanmore.pinkcatraiders.model.PastListOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistorysActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
    public
    Resources resources;
    public BaseApplication application;
    public
    WindowManager wManager;
    public
    AssetManager am;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.historysPullRefresh)
    PullToRefreshListView historysPullRefresh;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public Handler mHandler;
    public Bundle bundle;
    public List<PastListModel> pastList;
    public
    ProgressPopupWindow progress;
    public PastListAdapter adapter;
    public LayoutInflater inflate;
    View emptyView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_historys);
        ButterKnife.bind(this);
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        inflate = LayoutInflater.from ( HistorysActivity.this );
        wManager = this.getWindowManager ( );
        emptyView = inflate.inflate(R.layout.empty, null);
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText("暂无往期揭晓数据");
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        progress = new ProgressPopupWindow( HistorysActivity.this, HistorysActivity.this, wManager );
        bundle = this.getIntent().getExtras();
        initTitle();
        initList();
    }
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate();
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText("往期揭晓");
    }
    private void initList()
    {
        historysPullRefresh.setMode ( PullToRefreshBase.Mode.BOTH );
        historysPullRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                operateType = OperateTypeEnum.REFRESH;
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                operateType = OperateTypeEnum.LOADMORE;
                loadData();
            }
        });
        pastList = new ArrayList<PastListModel>();
        adapter = new PastListAdapter(mHandler,pastList, HistorysActivity.this);
        historysPullRefresh.setAdapter(adapter);
        firstGetData();
    }
    private void loadData()
    {
        if( false == HistorysActivity.this.canConnect() ){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    historysPullRefresh.onRefreshComplete();
                }
            });
            return;
        }

            String url = Contant.REQUEST_URL + Contant.GET_PAST_LIST;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), HistorysActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("goodsId", bundle.get("goodsId"));
            if ( OperateTypeEnum.REFRESH == operateType )
            {// 下拉
                maps.put("lastId", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType)
            {// 上拉
                if ( pastList != null && pastList.size() > 0)
                {
                    PastListModel product = pastList.get(pastList.size() - 1);
                    maps.put("lastId", pastList.size());
                } else if (pastList != null && pastList.size() == 0)
                {
                    maps.put("lastId", 0);
                }
            }
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();

            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    historysPullRefresh.onRefreshComplete();
                    if (HistorysActivity.this.isFinishing()) {
                        return;
                    }
                    JSONUtil<PastListOutputModel> jsonUtil = new JSONUtil<PastListOutputModel>();
                    PastListOutputModel pastListOutputModel = new PastListOutputModel();
                    pastListOutputModel = jsonUtil.toBean(response.toString(), pastListOutputModel);
                    if (null != pastListOutputModel && null != pastListOutputModel.getResultData() && null != pastListOutputModel.getResultData().getList()&&!pastListOutputModel.getResultData().getList().isEmpty()) {

                        if (operateType == OperateTypeEnum.REFRESH) {
                            pastList.clear();
                            pastList.addAll(pastListOutputModel.getResultData().getList());
                            adapter.notifyDataSetChanged();
                        } else if (operateType == OperateTypeEnum.LOADMORE) {
                            pastList.addAll(pastListOutputModel.getResultData().getList());
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        //提示获取数据失败
                        historysPullRefresh.setEmptyView(emptyView);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    historysPullRefresh.onRefreshComplete();
                    if (HistorysActivity.this.isFinishing()) {
                        return;
                    }
                    historysPullRefresh.setEmptyView(emptyView);
                }});



    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (HistorysActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                historysPullRefresh.setRefreshing(true);
            }
        }, 1000);
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
        closeSelf(HistorysActivity.this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

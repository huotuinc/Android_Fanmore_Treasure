package com.huotu.fanmore.pinkcatraiders.ui.product;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
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
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.AreaProductAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AreaProductsOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.GoodsListByOtherOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CateGoryGoodsListActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;

    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.titleRightImage)
    ImageView titleRightImage;
    @Bind(R.id.stubTitleText1)
    ViewStub stubTitleText1;
    @Bind(R.id.areaList)
    PullToRefreshListView areaList;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<ProductModel> products;
    public AreaProductAdapter adapter;
    public Bundle bundle;
    TextView titleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ri_area);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        emptyView = LayoutInflater.from(CateGoryGoodsListActivity.this).inflate(R.layout.empty, null);
        TextView label = (TextView) emptyView.findViewById(R.id.emptyTag);
        label.setText("暂无数据");
        TextView contrl = (TextView) emptyView.findViewById(R.id.emptyBtn);
        contrl.setVisibility(View.GONE);
        bundle = this.getIntent().getExtras();
        initTitle();
        initList();
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        Drawable rightDraw = resources.getDrawable(R.mipmap.more_gray);
        SystemTools.loadBackground(titleRightImage, rightDraw);
        stubTitleText1.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText(bundle.getString("title"));
        titleCount = (TextView) this.findViewById(R.id.titleCount);
        titleCount.setText("（0）");
    }

    private void initList()
    {
        areaList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                loadData();
            }
        });
        products = new ArrayList<ProductModel>();
        adapter = new AreaProductAdapter(products, CateGoryGoodsListActivity.this);
        areaList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData()
    {
        if( false == CateGoryGoodsListActivity.this.canConnect() ){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    areaList.onRefreshComplete();
                }
            });
            return;
        }
        if (bundle.get("type").equals(0L)){
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_BY_AREA;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), CateGoryGoodsListActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("categoryId", bundle.get("categoryId"));
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();

            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    areaList.onRefreshComplete();
                    if (CateGoryGoodsListActivity.this.isFinishing()) {
                        return;
                    }
                    JSONUtil<AreaProductsOutputModel> jsonUtil = new JSONUtil<AreaProductsOutputModel>();
                    AreaProductsOutputModel areaProductsOutputs = new AreaProductsOutputModel();
                    areaProductsOutputs = jsonUtil.toBean(response.toString(), areaProductsOutputs);
                    if (null != areaProductsOutputs && null != areaProductsOutputs.getResultData() && null != areaProductsOutputs.getResultData().getList()) {

                        //修改记录总数
                        Message message = mHandler.obtainMessage(Contant.LOAD_AREA_COUNT, areaProductsOutputs.getResultData().getList().size());
                        mHandler.sendMessage(message);
                        products.clear();
                        products.addAll(areaProductsOutputs.getResultData().getList());
                        adapter.notifyDataSetChanged();
                    } else {
                        //提示获取数据失败
                        areaList.setEmptyView(emptyView);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    areaList.onRefreshComplete();
                    if (CateGoryGoodsListActivity.this.isFinishing()) {
                        return;
                    }
                    areaList.setEmptyView(emptyView);
                }
            });
        }
        else if (bundle.get("type").equals(1L)) {
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_BY_CATEGORY;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), CateGoryGoodsListActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("categoryId", bundle.get("categoryId"));
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();

            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    areaList.onRefreshComplete();
                    if (CateGoryGoodsListActivity.this.isFinishing()) {
                        return;
                    }
                    JSONUtil<AreaProductsOutputModel> jsonUtil = new JSONUtil<AreaProductsOutputModel>();
                    AreaProductsOutputModel areaProductsOutputs = new AreaProductsOutputModel();
                    areaProductsOutputs = jsonUtil.toBean(response.toString(), areaProductsOutputs);
                    if (null != areaProductsOutputs && null != areaProductsOutputs.getResultData() && null != areaProductsOutputs.getResultData().getList()) {

                        //修改记录总数
                        Message message = mHandler.obtainMessage(Contant.LOAD_AREA_COUNT, areaProductsOutputs.getResultData().getList().size());
                        mHandler.sendMessage(message);
                        products.clear();
                        products.addAll(areaProductsOutputs.getResultData().getList());
                        adapter.notifyDataSetChanged();
                    } else {
                        //提示获取数据失败
                        areaList.setEmptyView(emptyView);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    areaList.onRefreshComplete();
                    if (CateGoryGoodsListActivity.this.isFinishing()) {
                        return;
                    }
                    areaList.setEmptyView(emptyView);
                }
            });
        }
        else if (bundle.get("type").equals(3L)) {
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_BY_ALL_CATEGORY;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), CateGoryGoodsListActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("lastSort", bundle.get("categoryId"));
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();

            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    areaList.onRefreshComplete();
                    if (CateGoryGoodsListActivity.this.isFinishing()) {
                        return;
                    }
                    JSONUtil<GoodsListByOtherOutputModel> jsonUtil = new JSONUtil<GoodsListByOtherOutputModel>();
                    GoodsListByOtherOutputModel goodsListByOtherOutputModel = new GoodsListByOtherOutputModel();
                    goodsListByOtherOutputModel = jsonUtil.toBean(response.toString(), goodsListByOtherOutputModel);
                    if (null != goodsListByOtherOutputModel && null != goodsListByOtherOutputModel.getResultData() && null != goodsListByOtherOutputModel.getResultData().getList()) {

                        //修改记录总数
                        Message message = mHandler.obtainMessage(Contant.LOAD_ALL_COUNT, goodsListByOtherOutputModel.getResultData().getCount());
                        mHandler.sendMessage(message);
                        products.clear();
                        products.addAll(goodsListByOtherOutputModel.getResultData().getList());
                        adapter.notifyDataSetChanged();
                    } else {
                        //提示获取数据失败
                        areaList.setEmptyView(emptyView);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    areaList.onRefreshComplete();
                    if (CateGoryGoodsListActivity.this.isFinishing()) {
                        return;
                    }
                    areaList.setEmptyView(emptyView);
                }
            });
        }else if (bundle.get("type").equals(2L)) {
            String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_BY_OTHER_CATEGORY;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), CateGoryGoodsListActivity.this);
            Map<String, Object> maps = new HashMap<String, Object>();
            maps.put("lastSort", bundle.get("categoryId"));
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();

            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    areaList.onRefreshComplete();
                    if (CateGoryGoodsListActivity.this.isFinishing()) {
                        return;
                    }
                    JSONUtil<GoodsListByOtherOutputModel> jsonUtil = new JSONUtil<GoodsListByOtherOutputModel>();
                    GoodsListByOtherOutputModel goodsListByOtherOutputModel = new GoodsListByOtherOutputModel();
                    goodsListByOtherOutputModel = jsonUtil.toBean(response.toString(), goodsListByOtherOutputModel);
                    if (null != goodsListByOtherOutputModel && null != goodsListByOtherOutputModel.getResultData() && null != goodsListByOtherOutputModel.getResultData().getList()) {

                        //修改记录总数
                        Message message = mHandler.obtainMessage(Contant.LOAD_ALL_COUNT, goodsListByOtherOutputModel.getResultData().getCount());
                        mHandler.sendMessage(message);
                        products.clear();
                        products.addAll(goodsListByOtherOutputModel.getResultData().getList());
                        adapter.notifyDataSetChanged();
                    } else {
                        //提示获取数据失败
                        areaList.setEmptyView(emptyView);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    areaList.onRefreshComplete();
                    if (CateGoryGoodsListActivity.this.isFinishing()) {
                        return;
                    }
                    areaList.setEmptyView(emptyView);
                }
            });
        }
    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CateGoryGoodsListActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                areaList.setRefreshing(true);
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
        closeSelf(CateGoryGoodsListActivity.this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(CateGoryGoodsListActivity.this);
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
            case Contant.LOAD_AREA_COUNT:
            {
                int count = (int) msg.obj;
                titleCount.setText("（"+count+"）");
            }
            break;

            case Contant.LOAD_ALL_COUNT:
                long count=(long) msg.obj;
                titleCount.setText("（"+count+"）");
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

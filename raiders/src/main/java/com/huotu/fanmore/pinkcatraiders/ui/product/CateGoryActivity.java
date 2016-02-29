package com.huotu.fanmore.pinkcatraiders.ui.product;


import android.app.Activity;
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
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.AreaProductAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.CategoryAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AreaProductsOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.CateGoryOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.CategoryListModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.SearchActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CateGoryActivity extends BaseActivity implements View.OnClickListener, Handler.Callback  {
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
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.cateList)
    PullToRefreshListView cateList;
    View emptyView = null;
    public Bundle bundle;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<CategoryListModel> category;
    public CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_cate_gory);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        emptyView = LayoutInflater.from(CateGoryActivity.this).inflate(R.layout.empty, null);
        TextView label = (TextView) emptyView.findViewById(R.id.emptyTag);
        label.setText("暂无数据");
        TextView contrl = (TextView) emptyView.findViewById(R.id.emptyBtn);
        contrl.setVisibility(View.GONE);
        bundle = this.getIntent().getExtras();
        initTitle();
        initList();
    }
    private void initTitle() {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate ( );
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText ( "分类浏览" );
    }

    private void initList()
    {
        cateList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                loadData();
            }
        });
        category = new ArrayList<CategoryListModel>();
        adapter = new CategoryAdapter(category, CateGoryActivity.this);
        cateList.setAdapter ( adapter );
        firstGetData ( );
    }
    private void loadData()
    {
        if( false == CateGoryActivity.this.canConnect() ){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    cateList.onRefreshComplete();
                }
            });
            return;
        }

        String url = Contant.REQUEST_URL + Contant.GET_CATE_GORY_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), CateGoryActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.doVolleyGet (
                url, new Response.Listener< JSONObject > ( ) {

                    @Override
                    public
                    void onResponse ( JSONObject response ) {

                        cateList.onRefreshComplete ( );
                        if ( CateGoryActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        JSONUtil< CateGoryOutputModel > jsonUtil            = new JSONUtil<
                                CateGoryOutputModel > ( );
                        CateGoryOutputModel             cateGoryOutputModel = new
                                CateGoryOutputModel ( );
                        cateGoryOutputModel = jsonUtil.toBean ( response.toString ( ),
                                                                cateGoryOutputModel );
                        if ( null != cateGoryOutputModel && null != cateGoryOutputModel
                                .getResultData ( ) && null != cateGoryOutputModel.getResultData (
                                                                                                )
                                                                                 .getList ( ) ) {

                            //修改记录总数
                            Message message = mHandler.obtainMessage ( Contant.LOAD_AREA_COUNT,
                                                                       cateGoryOutputModel
                                                                               .getResultData ( )
                                                                               .getList ( ).size
                                                                               ( ) );
                            mHandler.sendMessage ( message );
                            category.clear ( );
                            category.addAll ( cateGoryOutputModel.getResultData ( ).getList ( ) );
                            adapter.notifyDataSetChanged ( );
                        }
                        else {
                            //提示获取数据失败
                            cateList.setEmptyView ( emptyView );
                        }
                    }
                }, new Response.ErrorListener ( ) {

                    @Override
                    public
                    void onErrorResponse ( VolleyError error ) {

                        cateList.onRefreshComplete ( );
                        if ( CateGoryActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        cateList.setEmptyView ( emptyView );
                    }
                }
                              );
    }

    @OnClick(R.id.search)
    void toSearch()
    {
        Bundle bundle = new Bundle (  );
        ActivityUtils.getInstance ().showActivity ( CateGoryActivity.this, SearchActivity.class, bundle );
    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (CateGoryActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                cateList.setRefreshing(true);
            }
        }, 1000);
    }
    @OnClick(R.id.allL)
    void getdata()
    {
        Bundle bundle = new Bundle();
        bundle.putLong("type",3);
        bundle.putLong("categoryId", 0);
        bundle.putString("title","全部商品");
        ActivityUtils.getInstance().showActivity(this, CateGoryGoodsListActivity.class, bundle);
    }
    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(CateGoryActivity.this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

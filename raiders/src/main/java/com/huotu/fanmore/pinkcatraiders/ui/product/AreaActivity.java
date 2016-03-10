package com.huotu.fanmore.pinkcatraiders.ui.product;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
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
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductsOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
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

/**
 * 专区界面
 */
public class AreaActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    public ProgressPopupWindow progress;

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
        emptyView = LayoutInflater.from(AreaActivity.this).inflate(R.layout.empty, null);
        TextView label = (TextView) emptyView.findViewById(R.id.emptyTag);
        TextView contrl = (TextView) emptyView.findViewById(R.id.emptyBtn);
        contrl.setVisibility(View.GONE);
        bundle = this.getIntent().getExtras();
        label.setText("暂无"+bundle.getLong("step")+"元专区数据");
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
        if(10 == bundle.getLong("step"))
        {
            titleText.setText("十元专区");
        }
        else if(5 == bundle.getLong("step"))
        {
            titleText.setText("五元专区");
        }
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
        adapter = new AreaProductAdapter(mHandler,products, AreaActivity.this);
        areaList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData()
    {
        if( false == AreaActivity.this.canConnect() ){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    areaList.onRefreshComplete();
                }
            });
            return;
        }

        String url = Contant.REQUEST_URL + Contant.GET_GOODS_LIST_BY_AREA;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), AreaActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("step", bundle.get("step"));
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                areaList.onRefreshComplete();
                if (AreaActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<AreaProductsOutputModel> jsonUtil = new JSONUtil<AreaProductsOutputModel>();
                AreaProductsOutputModel areaProductsOutputs = new AreaProductsOutputModel();
                areaProductsOutputs = jsonUtil.toBean(response.toString(), areaProductsOutputs);
                if (null != areaProductsOutputs && null != areaProductsOutputs.getResultData() && null != areaProductsOutputs.getResultData().getList() && !areaProductsOutputs.getResultData().getList().isEmpty()) {

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
                if (AreaActivity.this.isFinishing()) {
                    return;
                }
                areaList.setEmptyView(emptyView);
            }
        });
    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AreaActivity.this.isFinishing()) {
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
        closeSelf(AreaActivity.this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(AreaActivity.this);
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
            case Contant.ADD_LIST:
            {
                ProductModel product = ( ProductModel ) msg.obj;
                progress = new ProgressPopupWindow( AreaActivity.this, AreaActivity.this, wManager );
                progress.showProgress ( "正在添加清单" );
                progress.showAtLocation(titleLayoutL,
                        Gravity.CENTER, 0, 0
                );
                String url = Contant.REQUEST_URL + Contant.JOIN_SHOPPING_CART;
                AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), AreaActivity.this);
                Map<String, Object> maps = new HashMap<String, Object> ();
                maps.put ( "issueId", String.valueOf ( product.getIssueId () ) );
                Map<String, Object> param = params.obtainPostParam(maps);
                String suffix = params.obtainGetParam(maps);
                url = url + suffix;
                BaseModel base = new BaseModel ();
                HttpUtils<BaseModel> httpUtils = new HttpUtils<BaseModel> ();
                httpUtils.doVolleyPost (
                        base, url, param, new Response.Listener< BaseModel > ( ) {
                            @Override
                            public
                            void onResponse ( BaseModel response ) {
                                progress.dismissView ();
                                BaseModel base = response;
                                if(1==base.getResultCode ())
                                {
                                    //上传成功
                                    ToastUtils.showLongToast(AreaActivity.this, "添加清单成功");
                                }
                                else
                                {
                                    //上传失败
                                    ToastUtils.showLongToast ( AreaActivity.this, "添加清单失败" );
                                }
                            }
                        }, new Response.ErrorListener ( ) {

                            @Override
                            public
                            void onErrorResponse ( VolleyError error ) {
                                progress.dismissView ( );
                                //系统级别错误
                                ToastUtils.showLongToast ( AreaActivity.this, "添加清单失败" );
                            }
                        }
                );
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

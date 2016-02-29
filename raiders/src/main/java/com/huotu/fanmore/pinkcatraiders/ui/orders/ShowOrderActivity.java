package com.huotu.fanmore.pinkcatraiders.ui.orders;

import android.content.res.AssetManager;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.ListAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.OrderAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.OrderModel;
import com.huotu.fanmore.pinkcatraiders.model.OrderOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
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
 * 晒单界面
 */
public class ShowOrderActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public Handler       mHandler;

    public WindowManager wManager;

    public
    AssetManager am;

    public Resources       res;

    public BaseApplication application;

    @Bind ( R.id.orderList )
    PullToRefreshListView orderList;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout        titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub              stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView             titleLeftImage;

    View emptyView = null;

    public OperateTypeEnum operateType = OperateTypeEnum.REFRESH;

    public List< OrderModel > orders;

    public OrderAdapter       adapter;

    public LayoutInflater     inflate;

    public Bundle             bundle;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.show_order );
        ButterKnife.bind ( this );
        mHandler = new Handler ( this );
        am = this.getAssets ( );
        inflate = LayoutInflater.from ( ShowOrderActivity.this );
        res = this.getResources ( );
        application = ( BaseApplication ) this.getApplication ( );
        wManager = this.getWindowManager ( );
        emptyView = inflate.inflate ( R.layout.empty, null );
        bundle = this.getIntent ( ).getExtras ( );
        TextView emptyTag = ( TextView ) emptyView.findViewById ( R.id.emptyTag );
        emptyTag.setText ( "暂无晒单信息" );
        TextView emptyBtn = ( TextView ) emptyView.findViewById ( R.id.emptyBtn );
        emptyBtn.setVisibility ( View.GONE );
        initTitle ( );
        initList ( );
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = res.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = res.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate ( );
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText ( "晒单分享" );
    }

    private
    void initList ( ) {

        orderList.setMode ( PullToRefreshBase.Mode.BOTH );
        orderList.setOnRefreshListener (
                new PullToRefreshBase.OnRefreshListener2< ListView > ( ) {

                    @Override
                    public
                    void onPullDownToRefresh ( PullToRefreshBase< ListView > pullToRefreshBase ) {

                        operateType = OperateTypeEnum.REFRESH;
                        loadData ( );
                    }

                    @Override
                    public
                    void onPullUpToRefresh ( PullToRefreshBase< ListView > pullToRefreshBase ) {

                        operateType = OperateTypeEnum.LOADMORE;
                        loadData ( );
                    }
                }
                                       );
        orders = new ArrayList< OrderModel > ( );
        adapter = new OrderAdapter ( orders, ShowOrderActivity.this );
        orderList.setAdapter ( adapter );
        orderList.setOnItemClickListener (
                new AdapterView.OnItemClickListener ( ) {

                    @Override
                    public
                    void onItemClick ( AdapterView< ? > parent, View view, int position, long id ) {

                        long   pid    = orders.get ( position - 1 ).getPid ( );
                        Bundle bundle = new Bundle ( );
                        bundle.putLong ( "pid", pid );
                        ActivityUtils.getInstance ( ).showActivity ( ShowOrderActivity.this, OrderDetailActivity.class, bundle );
                    }
                }
                                         );
        firstGetData ( );
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf ( ShowOrderActivity.this );
    }

    private void loadData()
    {


        if( false == ShowOrderActivity.this.canConnect ( ) ){
            mHandler.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          orderList.onRefreshComplete();
                                      }
                                  });
            return;
        }
        String url = null;
        if(2==bundle.getInt ( "type" ))
        {
            //产品晒单
            url = Contant.REQUEST_URL + Contant.GET_SHARE_ORDER_LIST_BY_GOOSID;

        }
        else if(0==bundle.getInt ( "type" ))
        {
            //首页晒单
            url = Contant.REQUEST_URL + Contant.GET_SHARE_ORDER_LIST;
        }
        else if(1==bundle.getInt ( "type" ))
        {
            //我的晒单
            url = Contant.REQUEST_URL + Contant.GET_MY_SHARE_ORDER_LIST;
        }
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ShowOrderActivity.this);
        Map<String, Object> maps = new HashMap<String, Object> ();
        if(2==bundle.getInt ( "type" ))
        {
            maps.put("goodsId", bundle.getLong ( "goodsId" ));
        }
        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            maps.put("lastId", 0);
        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( orders != null && orders.size() > 0)
            {
                OrderModel order = orders.get(orders.size() - 1);
                maps.put("lastId", order.getPid());
            } else if (orders != null && orders.size() == 0)
            {
                maps.put("lastId", 0);
            }
        }
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject >() {
                                  @Override
                                  public void onResponse(JSONObject response) {
                                      orderList.onRefreshComplete();
                                      if(ShowOrderActivity.this.isFinishing ( ))
                                      {
                                          return;
                                      }
                                      JSONUtil<OrderOutputModel > jsonUtil = new JSONUtil<OrderOutputModel>();
                                      OrderOutputModel OrderOutputs = new OrderOutputModel();
                                      OrderOutputs = jsonUtil.toBean(response.toString(), OrderOutputs);
                                      if(null != OrderOutputs && null != OrderOutputs.getResultData() && (1==OrderOutputs.getResultCode()))
                                      {
                                          if(null != OrderOutputs.getResultData().getList() && !OrderOutputs.getResultData().getList().isEmpty())
                                          {
                                              if( operateType == OperateTypeEnum.REFRESH){
                                                  orders.clear();
                                                  orders.addAll(OrderOutputs.getResultData().getList());
                                                  adapter.notifyDataSetChanged();
                                              }else if( operateType == OperateTypeEnum.LOADMORE){
                                                  orders.addAll( OrderOutputs.getResultData().getList());
                                                  adapter.notifyDataSetChanged();
                                              }
                                          }
                                          else
                                          {
                                              orderList.setEmptyView(emptyView);
                                          }
                                      }
                                      else
                                      {
                                          //异常处理，自动切换成无数据
                                          orderList.setEmptyView(emptyView);
                                      }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {
                                      orderList.onRefreshComplete();
                                      if(ShowOrderActivity.this.isFinishing() ) {
                                          return;
                                      }
                                      orderList.setEmptyView(emptyView);
                                  }
                              });
    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ShowOrderActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                orderList.setRefreshing(true);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        VolleyUtil.cancelAllRequest();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(ShowOrderActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {
    }
}

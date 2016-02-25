package com.huotu.fanmore.pinkcatraiders.ui.assistant;

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
import com.huotu.fanmore.pinkcatraiders.adapter.OrderAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.RechargeAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.OrderModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RechargeLogOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RechargeModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.OrderDetailActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
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

/**
 * 充值记录界面
 */
public class RechargeLogActivity extends BaseActivity implements View.OnClickListener, Handler.Callback{

    public Handler mHandler;
    public WindowManager wManager;
    public
    AssetManager am;
    public Resources resources;
    public BaseApplication application;
    @Bind(R.id.rechargeLogList)
    PullToRefreshListView rechargeLogList;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.titleRightImage)
    ImageView titleRightImage;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<RechargeModel> recharges;
    public RechargeAdapter adapter;
    public LayoutInflater inflate;

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_log);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        am = this.getAssets();
        inflate = LayoutInflater.from(RechargeLogActivity.this);
        resources = this.getResources();
        application = (BaseApplication) this.getApplication ();
        wManager = this.getWindowManager();
        emptyView = inflate.inflate(R.layout.empty, null);
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText("暂无充值记录信息");
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        initTitle();
        initList();
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        Drawable rightDraw = resources.getDrawable(R.mipmap.recharge_icon);
        SystemTools.loadBackground ( titleRightImage, rightDraw );
        stubTitleText.inflate ( );
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText ( "充值记录" );
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf ( RechargeLogActivity.this );
    }

    @OnClick(R.id.titleRightImage)
    void toCharge()
    {
        ActivityUtils.getInstance ().skipActivity ( RechargeLogActivity.this, RechargeActivity.class );
    }

    private void initList()
    {
        rechargeLogList.setMode(PullToRefreshBase.Mode.BOTH);
        rechargeLogList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        recharges = new ArrayList<RechargeModel>();
        adapter = new RechargeAdapter(recharges, RechargeLogActivity.this);
        rechargeLogList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData()
    {

        if( false == RechargeLogActivity.this.canConnect ( ) ){
            mHandler.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          rechargeLogList.onRefreshComplete();
                                      }
                                  });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_MY_PUT_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), RechargeLogActivity.this);
        Map<String, Object> maps = new HashMap<String, Object> ();

        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            maps.put("lastId", 0);
        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( recharges != null && recharges.size() > 0)
            {
                RechargeModel recharge = recharges.get(recharges.size() - 1);
                maps.put("lastId", recharge.getPid());
            } else if (recharges != null && recharges.size() == 0)
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
                                      rechargeLogList.onRefreshComplete();
                                      if(RechargeLogActivity.this.isFinishing ( ))
                                      {
                                          return;
                                      }
                                      JSONUtil<RechargeLogOutputModel > jsonUtil = new JSONUtil<RechargeLogOutputModel>();
                                      RechargeLogOutputModel rechargeLogOutput = new RechargeLogOutputModel();
                                      rechargeLogOutput = jsonUtil.toBean(response.toString(), rechargeLogOutput);
                                      if(null != rechargeLogOutput && null != rechargeLogOutput.getResultData() && (1==rechargeLogOutput.getResultCode()))
                                      {
                                          if(null != rechargeLogOutput.getResultData().getList() && !rechargeLogOutput.getResultData().getList().isEmpty())
                                          {
                                              if( operateType == OperateTypeEnum.REFRESH){
                                                  recharges.clear();
                                                  recharges.addAll(rechargeLogOutput.getResultData().getList());
                                                  adapter.notifyDataSetChanged();
                                              }else if( operateType == OperateTypeEnum.LOADMORE){
                                                  recharges.addAll( rechargeLogOutput.getResultData().getList());
                                                  adapter.notifyDataSetChanged();
                                              }
                                          }
                                          else
                                          {
                                              rechargeLogList.setEmptyView(emptyView);
                                          }
                                      }
                                      else
                                      {
                                          //异常处理，自动切换成无数据
                                          rechargeLogList.setEmptyView(emptyView);
                                      }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {
                                      rechargeLogList.onRefreshComplete();
                                      if(RechargeLogActivity.this.isFinishing ( ))
                                      {
                                          return;
                                      }
                                      rechargeLogList.setEmptyView(emptyView);
                                  }
                              });
    }

    protected void firstGetData(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (RechargeLogActivity.this.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                rechargeLogList.setRefreshing(true);
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
            this.closeSelf(RechargeLogActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}

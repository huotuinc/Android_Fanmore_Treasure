package com.huotu.fanmore.pinkcatraiders.ui.raiders;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
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
import com.huotu.fanmore.pinkcatraiders.adapter.MyAddressAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.WinAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppUserBuyFlowModel;
import com.huotu.fanmore.pinkcatraiders.model.MyAddressListModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.WinnerOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
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
 * 中奖记录
 */
public class WinLogActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;

    public BaseApplication application;

    public Handler mHandler;

    public
    WindowManager wManager;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;

    @Bind ( R.id.winLogList )
    PullToRefreshListView winLogList;

    View emptyView = null;

    public LayoutInflater inflate;

    public
    List< AppUserBuyFlowModel > winners;
    public
    WinAdapter adapter;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.ri_win_log );
        ButterKnife.bind ( this );
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        inflate = LayoutInflater.from ( WinLogActivity.this );
        wManager = this.getWindowManager ( );
        emptyView = inflate.inflate ( R.layout.empty, null );
        TextView emptyTag = ( TextView ) emptyView.findViewById ( R.id.emptyTag );
        emptyTag.setText ( "暂无中奖记录信息" );
        TextView emptyBtn = ( TextView ) emptyView.findViewById ( R.id.emptyBtn );
        emptyBtn.setOnClickListener (
                new View.OnClickListener ( ) {

                    @Override
                    public
                    void onClick ( View v ) {
                        ActivityUtils.getInstance().showActivity(WinLogActivity.this,HomeActivity.class);
                        closeSelf(WinLogActivity.this);
                    }
                }
                                    );
        wManager = this.getWindowManager ( );
        initTitle ( );
        initList ( );
    }

    private
    void initList ( ) {

        winLogList.setMode(PullToRefreshBase.Mode.BOTH);
        winLogList.setOnRefreshListener (
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
        winners = new ArrayList<AppUserBuyFlowModel> ();
        adapter = new WinAdapter (winners, WinLogActivity.this,WinLogActivity.this);
        winLogList.setAdapter ( adapter );
        firstGetData ( );
    }

    private void loadData()
    {
        if( false == WinLogActivity.this.canConnect ( ) ){
            mHandler.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          winLogList.onRefreshComplete();
                                      }
                                  });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_MY_LOTTERY_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), WinLogActivity.this);
        Map<String, Object> maps = new HashMap<String, Object> ();
        //全部
        maps.put("userId", String.valueOf ( application.readUerId () ));
        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            maps.put("lastId", 0);
        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( winners != null && winners.size() > 0)
            {
                AppUserBuyFlowModel winner = winners.get(winners.size() - 1);
                maps.put("lastId", winner.getPid());
            } else if (winners != null && winners.size() == 0)
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
                                      winLogList.onRefreshComplete();
                                      if(WinLogActivity.this.isFinishing ( ))
                                      {
                                          return;
                                      }
                                      JSONUtil<WinnerOutputModel > jsonUtil = new JSONUtil<WinnerOutputModel>();
                                      WinnerOutputModel winnerOutput = new WinnerOutputModel();
                                      winnerOutput = jsonUtil.toBean(response.toString(), winnerOutput);
                                      if(null != winnerOutput && null != winnerOutput.getResultData() && (1==winnerOutput.getResultCode()))
                                      {
                                          if(null != winnerOutput.getResultData().getList() && !winnerOutput.getResultData().getList().isEmpty())
                                          {
                                              if( operateType == OperateTypeEnum.REFRESH){
                                                  winners.clear();
                                                  winners.addAll(winnerOutput.getResultData().getList());
                                                  adapter.notifyDataSetChanged();
                                              }else if( operateType == OperateTypeEnum.LOADMORE){
                                                  winners.addAll( winnerOutput.getResultData().getList());
                                                  adapter.notifyDataSetChanged();
                                              }
                                          }
                                          else
                                          {
                                              winLogList.setEmptyView(emptyView);
                                          }
                                      }
                                      else
                                      {
                                          //异常处理，自动切换成无数据
                                          winLogList.setEmptyView(emptyView);
                                      }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {
                                       winLogList.onRefreshComplete();
                                      if(WinLogActivity.this.isFinishing ( ))
                                      {
                                          return;
                                      }
                                      winLogList.setEmptyView(emptyView);
                                  }
                              });
    }

    protected
    void firstGetData ( ) {

        mHandler.postDelayed (
                new Runnable ( ) {

                    @Override
                    public
                    void run ( ) {

                        if ( WinLogActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        winLogList.setRefreshing ( true );
                    }
                }, 1000
                             );
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate ( );
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText ( "中奖记录" );
    }

    @Override
    protected
    void onDestroy ( ) {

        super.onDestroy ( );
        VolleyUtil.cancelAllRequest ( );
        ButterKnife.unbind(this);
    }
    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(WinLogActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

package com.huotu.fanmore.pinkcatraiders.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.RedAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RedPacketOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RedPacketsModel;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RedEnvelopesActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 已使用/过期红包
 */
public class RedEnvelopesnouseFrag extends BaseFragment implements Handler.Callback {
    View rootView;
    public Resources resources;
    public BaseApplication application;
    public RedEnvelopesActivity rootAty;
    public WindowManager wManager;
    View emptyView = null;
    @Bind(R.id.raidersLogList)
    PullToRefreshListView redPackageList;
    public OperateTypeEnum operateType = OperateTypeEnum.REFRESH;
    public List<RedPacketsModel> redPacketsModels;
    public RedAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resources = getActivity().getResources();
        rootView = inflater.inflate(R.layout.raiders_log_frag, container, false);
        application = (BaseApplication) getActivity().getApplication();
        rootAty = (RedEnvelopesActivity) getActivity();
        ButterKnife.bind(this, rootView);
        emptyView = inflater.inflate(R.layout.empty, null);
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText ( "暂无过期红包信息" );
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        wManager = getActivity().getWindowManager();
        initList();
        return rootView;
    }

    private void initList() {
        redPackageList.setMode(PullToRefreshBase.Mode.BOTH);
        redPackageList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        redPacketsModels = new ArrayList<RedPacketsModel>();
        adapter = new RedAdapter(redPacketsModels, getActivity());
        redPackageList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData() {

        if( false == rootAty.canConnect() ){
            rootAty.mHandler.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          redPackageList.onRefreshComplete();
                                      }
                                  });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_MY_REDPACKAGES_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
        Map<String, Object> maps = new HashMap<String, Object> ();
        //全部
        maps.put("type", "1");
        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            maps.put("lastId", 0);
        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( redPacketsModels != null && redPacketsModels.size() > 0)
            {
                RedPacketsModel redPacket = redPacketsModels.get(redPacketsModels.size() - 1);
                maps.put("lastId", redPacket.getPid());
            } else if (redPacketsModels != null && redPacketsModels.size() == 0)
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
                                      redPackageList.onRefreshComplete();
                                      if(rootAty.isFinishing())
                                      {
                                          return;
                                      }
                                      JSONUtil<RedPacketOutputModel > jsonUtil = new JSONUtil<RedPacketOutputModel>();
                                      RedPacketOutputModel redPacketOutput = new RedPacketOutputModel();
                                      redPacketOutput = jsonUtil.toBean(response.toString(), redPacketOutput);
                                      if(null != redPacketOutput && null != redPacketOutput.getResultData() && (1==redPacketOutput.getResultCode()))
                                      {
                                          if(null != redPacketOutput.getResultData().getList() && !redPacketOutput.getResultData().getList().isEmpty())
                                          {
                                              if( operateType == OperateTypeEnum.REFRESH){
                                                  redPacketsModels.clear();
                                                  redPacketsModels.addAll(redPacketOutput.getResultData().getList());
                                                  adapter.notifyDataSetChanged();
                                              }else if( operateType == OperateTypeEnum.LOADMORE){
                                                  redPacketsModels.addAll( redPacketOutput.getResultData().getList());
                                                  adapter.notifyDataSetChanged();
                                              }
                                          }
                                          else
                                          {
                                              redPackageList.setEmptyView(emptyView);
                                          }
                                      }
                                      else
                                      {
                                          //异常处理，自动切换成无数据
                                          redPackageList.setEmptyView(emptyView);
                                      }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {
                                      redPackageList.onRefreshComplete();
                                      if(rootAty.isFinishing())
                                      {
                                          return;
                                      }
                                      redPackageList.setEmptyView(emptyView);
                                  }
                              });
    }

    protected void firstGetData() {
        rootAty.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                redPackageList.setRefreshing(true);
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


}

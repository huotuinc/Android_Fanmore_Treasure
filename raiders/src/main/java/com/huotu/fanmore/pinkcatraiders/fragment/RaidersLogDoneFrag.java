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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.RaidersAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.WinnerModel;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RaidesLogActivity;
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
 * 揭晓的夺宝
 */
public class RaidersLogDoneFrag extends BaseFragment implements Handler.Callback {

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public RaidesLogActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.raidersLogList)
    PullToRefreshListView raidersLogList;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<RaidersModel> raiders;
    public RaidersAdapter adapter;

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
        rootAty = (RaidesLogActivity) getActivity();
        ButterKnife.bind(this, rootView);
        emptyView = inflater.inflate(R.layout.empty, null);
        wManager = getActivity().getWindowManager();
        initList();
        return rootView;
    }

    private void initList()
    {
        raidersLogList.setMode(PullToRefreshBase.Mode.BOTH);
        raidersLogList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        raiders = new ArrayList<RaidersModel>();
        adapter = new RaidersAdapter(raiders, getActivity());
        raidersLogList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData()
    {
        if( false == rootAty.canConnect() ){
            rootAty.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    raidersLogList.onRefreshComplete();
                }
            });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_MY_RAIDER_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
        Map<String, Object> maps = new HashMap<String, Object>();
        //全部
        maps.put("type", "2");
        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            maps.put("lastId", 0);
        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( raiders != null && raiders.size() > 0)
            {
                RaidersModel raider = raiders.get(raiders.size() - 1);
                maps.put("lastId", raider.getPid());
            } else if (raiders != null && raiders.size() == 0)
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
                raidersLogList.onRefreshComplete();
                if(rootAty.isFinishing())
                {
                    return;
                }
                JSONUtil<RaidersOutputModel> jsonUtil = new JSONUtil<RaidersOutputModel>();
                RaidersOutputModel raiderOutputs = new RaidersOutputModel();
                raiderOutputs = jsonUtil.toBean(response.toString(), raiderOutputs);
                if(null != raiderOutputs && null != raiderOutputs.getResultData() && (1==raiderOutputs.getResultCode()))
                {
                    if(null != raiderOutputs.getResultData().getList() && !raiderOutputs.getResultData().getList().isEmpty())
                    {
                        //更新夺宝数据
                        String[] counts = new String[]{String.valueOf(raiderOutputs.getResultData().getAllNumber()), String.valueOf(raiderOutputs.getResultData().getRunNumber()), String.valueOf(raiderOutputs.getResultData().getFinishNumber())};
                        Message message = rootAty.mHandler.obtainMessage(Contant.UPDATE_RAIDER_COUNT, counts);
                        rootAty.mHandler.sendMessage(message);
                        if( operateType == OperateTypeEnum.REFRESH){
                            raiders.clear();
                            raiders.addAll(raiderOutputs.getResultData().getList());
                            adapter.notifyDataSetChanged();
                        }else if( operateType == OperateTypeEnum.LOADMORE){
                            raiders.addAll( raiderOutputs.getResultData().getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        raidersLogList.setEmptyView(emptyView);
                    }
                }
                else
                {
                    //异常处理，自动切换成无数据
                    raidersLogList.setEmptyView(emptyView);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                raidersLogList.onRefreshComplete();
                if(rootAty.isFinishing())
                {
                    return;
                }
                raidersLogList.setEmptyView(emptyView);
            }
        });
    }

    protected void firstGetData(){
        rootAty.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                raidersLogList.setRefreshing(true);
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

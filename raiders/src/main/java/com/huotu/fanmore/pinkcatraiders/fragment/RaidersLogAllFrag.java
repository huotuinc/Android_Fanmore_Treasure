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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.RaidersAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.WinnerModel;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RaidesLogActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 全部夺宝记录
 */
public class RaidersLogAllFrag extends BaseFragment implements Handler.Callback {

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
        RaidersModel raiders1 = new RaidersModel();
        raiders1.setProductIcon("http://img4.imgtn.bdimg.com/it/u=4269198236,3866462712&fm=206&gp=0.jpg");
        raiders1.setProductName("飞科剃须刀");
        raiders1.setPartnerNo("100189");
        raiders1.setTotal(120);
        raiders1.setLotterySchedule(35);
        raiders1.setSurplus(85);
        raiders1.setPartnerCount("12");
        raiders1.setRaidersType(0);
        raiders.add(raiders1);
        RaidersModel raiders2 = new RaidersModel();
        raiders2.setProductIcon("http://img1.imgtn.bdimg.com/it/u=1175270452,550953813&fm=11&gp=0.jpg");
        raiders2.setProductName("飞科剃须刀");
        raiders2.setPartnerNo("100190");
        raiders2.setTotal(120);
        raiders2.setLotterySchedule(35);
        raiders2.setSurplus(85);
        raiders2.setPartnerCount("12");
        raiders2.setRaidersType(1);
        WinnerModel winner = new WinnerModel();
        winner.setWinnerName("徐河口");
        winner.setAnnouncedTime("2016-1-25 18:12:11");
        winner.setLuckyNo("100012");
        winner.setPeriod(24);
        raiders2.setWinner(winner);
        raiders.add(raiders2);
        raidersLogList.onRefreshComplete();
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

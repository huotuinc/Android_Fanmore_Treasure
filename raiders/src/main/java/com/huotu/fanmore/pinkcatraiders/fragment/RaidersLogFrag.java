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
import com.huotu.fanmore.pinkcatraiders.adapter.AreaProductAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RaidesLogActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 夺宝记录
 */
public class RaidersLogFrag extends BaseFragment implements Handler.Callback {

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
        products = new ArrayList<ProductModel>();
        adapter = new AreaProductAdapter(products, getActivity());
        raidersLogList.setAdapter(adapter);
        firstGetData();
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

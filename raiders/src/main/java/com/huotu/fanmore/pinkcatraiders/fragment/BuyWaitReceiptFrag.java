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
import com.huotu.fanmore.pinkcatraiders.adapter.BuyItemAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.BuyItemModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.BuyLogActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 购买记录（等待收货）
 */
public class BuyWaitReceiptFrag extends BaseFragment implements Handler.Callback {
    View rootView;
    public Resources resources;
    public BaseApplication application;
    public BuyLogActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.buyLogList)
    PullToRefreshListView buyLogList;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<BuyItemModel> items;
    public BuyItemAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resources = getActivity().getResources();
        rootView = inflater.inflate(R.layout.buy_log_frag, container, false);
        application = (BaseApplication) getActivity().getApplication();
        rootAty = (BuyLogActivity) getActivity();
        ButterKnife.bind(this, rootView);
        wManager = getActivity().getWindowManager();
        initList();
        return rootView;
    }

    private void initList()
    {
        buyLogList.setMode(PullToRefreshBase.Mode.BOTH);
        buyLogList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        items = new ArrayList<BuyItemModel>();
        adapter = new BuyItemAdapter(items, getActivity());
        buyLogList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData()
    {
        BuyItemModel item3 = new BuyItemModel();
        item3.setTitle("迷你小飞机");
        item3.setToAmount(20);
        item3.setAmount(1);
        item3.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
        item3.setStatus(2);
        item3.setTotalMoney(200);
        item3.setPrice(200);
        items.add(item3);
        buyLogList.onRefreshComplete();
    }

    protected void firstGetData(){
        rootAty.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                buyLogList.setRefreshing(true);
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

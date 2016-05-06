package com.huotu.fanmore.pinkcatraiders.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.BuyItemAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.BuyItemModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.BuyLogActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 购买记录（等待付款）
 */
public class BuyWaitPayFrag  extends BaseFragment implements Handler.Callback {

    public BuyLogActivity rootAty;
    @Bind(R.id.buyLogList)
    PullToRefreshListView buyLogList;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<BuyItemModel> items;
    public BuyItemAdapter adapter;
    boolean init;
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.buy_log_frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!init){
            rootAty = (BuyLogActivity) getActivity();
            initList();
            init=true;
        }
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
        BuyItemModel item1 = new BuyItemModel();
        item1.setTitle("迷你小飞机");
        item1.setToAmount(20);
        item1.setAmount(1);
        item1.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
        item1.setStatus(0);
        item1.setTotalMoney(200);
        item1.setPrice(200);
        items.add(item1);
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

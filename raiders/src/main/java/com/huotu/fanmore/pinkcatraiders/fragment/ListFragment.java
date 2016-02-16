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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.ListAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.RaidersAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 清单fragment
 */
public class ListFragment extends BaseFragment implements Handler.Callback, View.OnClickListener{

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.menuList)
    PullToRefreshListView menuList;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<ListModel> lists;
    public ListAdapter adapter;

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
        rootView = inflater.inflate(R.layout.list_frag, container, false);
        application = (BaseApplication) getActivity().getApplication();
        rootAty = (HomeActivity) getActivity();
        ButterKnife.bind(this, rootView);
        wManager = getActivity().getWindowManager();
        emptyView = inflater.inflate(R.layout.empty, null);
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText("暂无清单信息");
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        initList();
        return rootView;
    }

    private void initList()
    {
        menuList.setMode(PullToRefreshBase.Mode.BOTH);
        menuList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        lists = new ArrayList<ListModel>();
        adapter = new ListAdapter(lists, getActivity(), 0);
        menuList.setAdapter(adapter);
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
                menuList.setRefreshing(true);
            }
        }, 1000);
    }

    private void loadData()
    {
        /*if(null!=lists&&!lists.isEmpty())
        {*/
            menuList.onRefreshComplete();
            ListModel list1 = new ListModel();
            list1.setPid(1);
            list1.setToAmount(1000);
            list1.setAreaAmount(10);
            list1.setTitle("想嘻嘻嘻嘻嘻嘻嘻嘻嘻想");
            list1.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
            list1.setRemainAmount(155);
            list1.setStepAmount(10);
            lists.add(list1);
            ListModel list2 = new ListModel();
            list2.setPid(2);
            list2.setToAmount(1000);
            list2.setAreaAmount(10);
            list2.setTitle("想嘻嘻嘻嘻嘻嘻嘻嘻嘻想");
            list2.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
            list2.setRemainAmount(155);
            list2.setStepAmount(10);
            lists.add(list2);
            ListModel list3 = new ListModel();
            list3.setPid(3);
            list3.setToAmount(1000);
            list3.setAreaAmount(10);
            list3.setTitle("想嘻嘻嘻嘻嘻嘻嘻嘻嘻想");
            list3.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
            list3.setRemainAmount(155);
            list3.setStepAmount(10);
            lists.add(list3);
        /*}
        else
        {
            menuList.onRefreshComplete();
            menuList.setEmptyView(emptyView);
            rootAty.funcPopWin.dismissView();
        }*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(getActivity());
        VolleyUtil.cancelAllRequest();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

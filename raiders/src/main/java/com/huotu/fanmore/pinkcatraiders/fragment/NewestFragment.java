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
import android.widget.GridView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.AreaProductAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.NewestProductAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.NewestProduct;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 最新揭晓fragment
 */
public class NewestFragment extends BaseFragment implements Handler.Callback, View.OnClickListener {

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.newestGrid)
    PullToRefreshGridView newestGrid;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<NewestProduct> newestProducts;
    public NewestProductAdapter adapter;

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
        rootView = inflater.inflate(R.layout.newest_frag, container, false);
        application = (BaseApplication) getActivity().getApplication();
        rootAty = (HomeActivity) getActivity();
        ButterKnife.bind(this, rootView);
        wManager = getActivity().getWindowManager();
        emptyView = inflater.inflate(R.layout.empty, null);
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText("没有最近揭晓数据");
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        initGrid();
        return rootView;
    }

    private void initGrid()
    {
        /*newestGrid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> pullToRefreshBase) {
                operateType = OperateTypeEnum.REFRESH;
                initProducts();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> pullToRefreshBase) {
                operateType = OperateTypeEnum.LOADMORE;
                initProducts();
            }
        });*/
        newestProducts = new ArrayList<NewestProduct>();
        NewestProduct newestProduct1 = new NewestProduct();
        newestProduct1.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
        newestProduct1.setPid(1);
        newestProduct1.setTitle("迷你小飞机");
        newestProduct1.setIssueId(100018);
        newestProduct1.setRemainSecond(1222209);
        newestProduct1.setAreaAmount(10);
        newestProducts.add(newestProduct1);
        NewestProduct newestProduct2 = new NewestProduct();
        newestProduct2.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
        newestProduct2.setPid(2);
        newestProduct2.setTitle("迷你小飞机2");
        newestProduct2.setIssueId(100018);
        newestProduct2.setRemainSecond(1222209);
        newestProduct2.setAreaAmount(5);
        newestProducts.add(newestProduct2);
        NewestProduct newestProduct3 = new NewestProduct();
        newestProduct3.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
        newestProduct3.setPid(3);
        newestProduct3.setTitle("迷你小飞机3");
        newestProduct3.setIssueId(100018);
        newestProduct3.setRemainSecond(1222209);
        newestProducts.add(newestProduct3);
        adapter = new NewestProductAdapter(newestProducts, getActivity());
        newestGrid.setAdapter(adapter);
        //firstGetData();
    }

    private void initProducts()
    {
        /*if(null == newestProducts||newestProducts.isEmpty())
        {
            newestGrid.onRefreshComplete();
            newestGrid.setEmptyView(emptyView);
        }
        else
        {*/
            //加载数据
            newestGrid.onRefreshComplete();
            newestProducts = new ArrayList<NewestProduct>();
            NewestProduct newestProduct1 = new NewestProduct();
            newestProduct1.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
            newestProduct1.setPid(1);
            newestProduct1.setTitle("迷你小飞机");
            newestProduct1.setIssueId(100018);
            newestProduct1.setRemainSecond(1222209);
            newestProduct1.setAreaAmount(10);
            newestProducts.add(newestProduct1);
            NewestProduct newestProduct2 = new NewestProduct();
            newestProduct2.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
            newestProduct2.setPid(2);
            newestProduct2.setTitle("迷你小飞机2");
            newestProduct2.setIssueId(100018);
            newestProduct2.setRemainSecond(1222209);
            newestProduct2.setAreaAmount(5);
            newestProducts.add(newestProduct2);
            NewestProduct newestProduct3 = new NewestProduct();
            newestProduct3.setPictureUrl("http://img2.imgtn.bdimg.com/it/u=818166525,3164247683&fm=206&gp=0.jpg");
            newestProduct3.setPid(3);
            newestProduct3.setTitle("迷你小飞机3");
            newestProduct3.setIssueId(100018);
            newestProduct3.setRemainSecond(1222209);
            newestProducts.add(newestProduct3);
       // }
    }

    /**
     * 初始化加载数据
     */
    protected void firstGetData(){
        rootAty.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) return;
                operateType = OperateTypeEnum.REFRESH;
                newestGrid.setRefreshing(true);
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

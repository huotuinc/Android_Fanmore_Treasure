package com.huotu.fanmore.pinkcatraiders.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.MyGridAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 人气产品展示界面
 */
public class PopularityFrag extends BaseFragment {

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.popularGrid)
    GridView popularGrid;
    public List<ProductModel> products;
    public MyGridAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resources = getActivity().getResources();
        rootView = inflater.inflate(R.layout.popular_frag, container, false);
        application = (BaseApplication) getActivity().getApplication();
        rootAty = (HomeActivity) getActivity();
        ButterKnife.bind(this, rootView);
        wManager = getActivity().getWindowManager();
        initGrid();
        return rootView;
    }

    private void initGrid()
    {
        products = new ArrayList<ProductModel>();
        ProductModel product1 = new ProductModel();
        products.add(product1);
        product1.setLotterySchedule(0.5);
        product1.setProductIcon("http://pic.pedaily.cn/resource/201211/201211220030212.jpg");
        product1.setProductName("Apple iPad mini 2 7.9英寸平板电脑 银色（16G WLAN版/A7芯片/Retina显示屏 ME279CH/A）");
        product1.setProductTag(0);
        ProductModel product2 = new ProductModel();
        product2.setLotterySchedule(0.3);
        product2.setProductIcon("http://img5.imgtn.bdimg.com/it/u=2901215570,3019814053&fm=21&gp=0.jpg");
        product2.setProductName("Apple MacBook Air 11.6英寸笔记本电脑 银色(Core i5 处理器/4GB内存/128GB SSD闪存 MJVM2CH/A)");
        product2.setProductTag(1);
        products.add(product2);
        ProductModel product3 = new ProductModel();
        product3.setLotterySchedule(0.7);
        product3.setProductIcon("http://img4.imgtn.bdimg.com/it/u=2614217841,121717063&fm=21&gp=0.jpg");
        product3.setProductName("Apple MacBook Air 11.6英寸笔记本电脑 银色(Core i5 处理器/4GB内存/128GB SSD闪存 MJVM2CH/A)");
        product3.setProductTag(1);
        products.add(product3);
        adapter = new MyGridAdapter(products, getActivity(), getActivity());
        popularGrid.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}

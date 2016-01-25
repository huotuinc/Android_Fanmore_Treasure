package com.huotu.fanmore.pinkcatraiders.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

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
 * 进度展示界面
 */
public class ProgressFrag extends BaseFragment {

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.progressGrid)
    GridView progressGrid;
    public List<ProductModel> products;
    public MyGridAdapter adapter;

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
        rootView = inflater.inflate(R.layout.progress_frag, container, false);
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
        product1.setProductIcon("http://img4.imgtn.bdimg.com/it/u=1514000187,1211372575&fm=21&gp=0.jpg");
        product1.setProductName("贝恩施 儿童玩具 托马斯积木益智玩具轨道火车1688组合装");
        product1.setProductTag(0);
        ProductModel product2 = new ProductModel();
        product2.setLotterySchedule(0.3);
        product2.setProductIcon("http://img0.imgtn.bdimg.com/it/u=193661410,2272359788&fm=21&gp=0.jpg");
        product2.setProductName("澳贝（AUBY） 益智玩具 乖乖小鸭 运动爬行婴幼儿童早教启智声光玩具 6-12个月 463318DS");
        product2.setProductTag(1);
        products.add(product2);
        ProductModel product3 = new ProductModel();
        product3.setLotterySchedule(0.7);
        product3.setProductIcon("http://img4.imgtn.bdimg.com/it/u=2134827264,3899370224&fm=21&gp=0.jpg");
        product3.setProductName("Bandai万代 MG版1:100 高达模型 敢达益智动漫拼装模型玩具 红色异端高达162047");
        product3.setProductTag(1);
        products.add(product3);
        adapter = new MyGridAdapter(products, getActivity(), getActivity());
        progressGrid.setAdapter(adapter);
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

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
 * 总需人数展示界面
 */
public class TotalRequiredFrag extends BaseFragment {

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.totalGrid)
    GridView totalGrid;
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
        rootView = inflater.inflate(R.layout.total_required_frag, container, false);
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
        product1.setProductIcon("http://img4.imgtn.bdimg.com/it/u=2562698136,3176817068&fm=21&gp=0.jpg");
        product1.setProductName("隆达骨瓷热卖套装多彩温馨花草采薇12件实用套装无铅环保简约中式餐具乔迁结婚礼物 12件实用套装");
        product1.setProductTag(0);
        ProductModel product2 = new ProductModel();
        product2.setLotterySchedule(0.3);
        product2.setProductIcon("http://img2.imgtn.bdimg.com/it/u=1771551741,670094687&fm=21&gp=0.jpg");
        product2.setProductName("硅元中式家用餐具 高档盘碗碟套装 品质餐具套装 陶瓷餐具 莲升餐具 16头");
        product2.setProductTag(1);
        products.add(product2);
        ProductModel product3 = new ProductModel();
        product3.setLotterySchedule(0.7);
        product3.setProductIcon("http://img3.imgtn.bdimg.com/it/u=766201051,3715993596&fm=21&gp=0.jpg");
        product3.setProductName("家茗茶具 整套半自动青花茶具套组 礼品包装 1-时和年丰");
        product3.setProductTag(1);
        products.add(product3);
        adapter = new MyGridAdapter(products, getActivity(), getActivity());
        totalGrid.setAdapter(adapter);
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

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
 * 最新产品展示界面
 */
public class NewestProductFrag extends BaseFragment {

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.newestProGrid)
    GridView newestProGrid;
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
        rootView = inflater.inflate(R.layout.newest_product_frag, container, false);
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
        product1.setProductIcon("http://img2.imgtn.bdimg.com/it/u=1574237154,627049796&fm=21&gp=0.jpg");
        product1.setProductName("南极人冬季新款男士外套棉服男 棉衣纯色毛领连帽男装 91军绿 XL");
        product1.setProductTag(0);
        ProductModel product2 = new ProductModel();
        product2.setLotterySchedule(0.3);
        product2.setProductIcon("http://img3.imgtn.bdimg.com/it/u=2889121258,1808736363&fm=21&gp=0.jpg");
        product2.setProductName("七匹狼Septwolves棉衣 男装厚外套 2015新款冬装立领棉服 002色黑灰 175/92A(XL)");
        product2.setProductTag(1);
        products.add(product2);
        ProductModel product3 = new ProductModel();
        product3.setLotterySchedule(0.7);
        product3.setProductIcon("http://img2.imgtn.bdimg.com/it/u=494676788,2106061956&fm=21&gp=0.jpg");
        product3.setProductName("战地吉普（AFS JEEP）2015新款冬季棉衣男 棉服男士加厚加绒加毛棉袄外套男款 1308军绿色 XL(建议体重150斤左右)");
        product3.setProductTag(1);
        products.add(product3);
        adapter = new MyGridAdapter(products, getActivity(), getActivity());
        newestProGrid.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

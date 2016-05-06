package com.huotu.fanmore.pinkcatraiders.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.NewestGridAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.widget.MyGridView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 最新产品展示界面
 */
public class NewestProductFrag extends BaseFragment {

    public HomeActivity rootAty;
    @Bind(R.id.newestProGrid)
    MyGridView newestProGrid;
    boolean init;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.newest_product_frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!init){
            rootAty = (HomeActivity) getActivity();
            initGrid();
            init=true;
        }
    }

    private void initGrid()
    {
        //初始化数据
        rootAty.newestProducts = new ArrayList<ProductModel>();
        rootAty.newestAdapter = new NewestGridAdapter(rootAty.newestProducts, getActivity(), getActivity(), rootAty.mHandler);
        newestProGrid.setAdapter(rootAty.newestAdapter);
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

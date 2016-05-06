package com.huotu.fanmore.pinkcatraiders.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.ProgressGridAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.widget.MyGridView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 进度展示界面
 */
public class ProgressFrag extends BaseFragment {

    public HomeActivity rootAty;
    @Bind(R.id.progressGrid)
    MyGridView progressGrid;
    boolean init;
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.progress_frag;
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
        rootAty.progressProducts = new ArrayList<ProductModel>();
        rootAty.progressAdapter = new ProgressGridAdapter(rootAty.progressProducts, getActivity(), getActivity(), rootAty.mHandler);
        progressGrid.setAdapter(rootAty.progressAdapter);
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

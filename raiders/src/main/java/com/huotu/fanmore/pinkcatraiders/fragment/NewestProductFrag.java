package com.huotu.fanmore.pinkcatraiders.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.MyGridAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductsOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //初始化数据
        rootAty.newestProducts = new ArrayList<ProductModel>();
        rootAty.newestAdapter = new MyGridAdapter(rootAty.newestProducts, getActivity(), getActivity());
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

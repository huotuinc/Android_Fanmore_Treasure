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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.AreaProductAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.MyGridAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.NewestProductAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.NewOpenListModel;
import com.huotu.fanmore.pinkcatraiders.model.NewOpenOutputModel;

import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
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
    public List<NewOpenListModel> newestProducts;
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

        newestGrid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
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
        });

       firstGetData();
    }

    private void initProducts()
    {
        newestProducts = new ArrayList<NewOpenListModel>();
        adapter = new NewestProductAdapter(newestProducts,getActivity(),getActivity());
        newestGrid.setAdapter(adapter);
        if( false == rootAty.canConnect() ) {
            rootAty.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    newestGrid.onRefreshComplete();
                }
            });
            return;
        }
        else
        {
            //加载数据
            String url = Contant.REQUEST_URL + Contant.GET_NEWOPEN_LIST;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();
            if ( OperateTypeEnum.REFRESH == operateType )
            {// 下拉
                maps.put("lastId", 0);
            } else if (OperateTypeEnum.LOADMORE == operateType)
            {// 上拉
                if ( newestProducts != null &&newestProducts.size() > 0)
                {
                    NewOpenListModel product = newestProducts.get(newestProducts.size() - 1);
                    maps.put("lastId",1);
                } else if (newestProducts != null && newestProducts.size() == 0)
                {
                    maps.put("lastId",1);
                }
            }
            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    newestGrid.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    JSONUtil<NewOpenOutputModel> jsonUtil = new JSONUtil<NewOpenOutputModel>();
                    NewOpenOutputModel productsOutputs = new NewOpenOutputModel();
                    productsOutputs = jsonUtil.toBean(response.toString(), productsOutputs);
                    if (null != productsOutputs && null != productsOutputs.getResultData() && (1 == productsOutputs.getResultCode())) {
                        if (null != productsOutputs.getResultData().getList() && !productsOutputs.getResultData().getList().isEmpty()) {

                            if (operateType == OperateTypeEnum.REFRESH) {
                                newestProducts.clear();
                                newestProducts.addAll(productsOutputs.getResultData().getList());
                                adapter.notifyDataSetChanged();
                            } else if (operateType == OperateTypeEnum.LOADMORE) {
                                newestProducts.addAll(productsOutputs.getResultData().getList());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            //空数据处理
                        }
                    } else {
                        //异常处理，自动切换成无数据
                        //空数据处理
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    newestGrid.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    //空数据处理
                }
            });


        }
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

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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.ListAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.RaidersAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.ListOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        lists = new ArrayList<ListModel>();
        adapter = new ListAdapter(lists, getActivity(), 1);
        menuList.setAdapter(adapter);
        if( false == rootAty.canConnect() ) {
            rootAty.mHandler.post(new Runnable() {
                @Override
                public void run() {
                    menuList.onRefreshComplete();
                }
            });
            return;
        }
        else
        {
            //加载数据
            String url = Contant.REQUEST_URL + Contant.GET_SHOPPING_LIST;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
            Map<String, Object> maps = new HashMap<String, Object>();

            String suffix = params.obtainGetParam(maps);
            url = url + suffix;
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    menuList.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    JSONUtil<ListOutputModel> jsonUtil = new JSONUtil<ListOutputModel>();
                    ListOutputModel listOutputModel = new ListOutputModel();
                    listOutputModel = jsonUtil.toBean(response.toString(), listOutputModel);
                    if (null != listOutputModel && null != listOutputModel.getResultData() && (1 == listOutputModel.getResultCode())) {
                        if (null != listOutputModel.getResultData().getList() && !listOutputModel.getResultData().getList().isEmpty()) {

                            if (operateType == OperateTypeEnum.REFRESH) {
                                lists.clear();
                                lists.addAll(listOutputModel.getResultData().getList());
                                adapter.notifyDataSetChanged();
                            } else if (operateType == OperateTypeEnum.LOADMORE) {
                                lists.addAll(listOutputModel.getResultData().getList());
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
                    menuList.onRefreshComplete();
                    if (rootAty.isFinishing()) {
                        return;
                    }
                    //空数据处理
                }
            });


        }
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

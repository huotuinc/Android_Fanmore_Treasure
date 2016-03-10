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
import com.huotu.fanmore.pinkcatraiders.model.CartCountModel;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.ListOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.SearchHistoryModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.PayResultAtivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 清单fragment
 */
public class ListFragment extends BaseFragment implements Handler.Callback, View.OnClickListener, MyBroadcastReceiver.BroadcastListener{

    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.menuList)
    PullToRefreshListView menuList;
    View emptyView = null;
    public List<ListModel> lists;
    public ListAdapter adapter;

    private MyBroadcastReceiver myBroadcastReceiver;

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
        firstGetData();
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
        myBroadcastReceiver = new MyBroadcastReceiver(getActivity(), this, MyBroadcastReceiver.SHOP_CART);
        initList();
        return rootView;
    }

    private void initList()
    {
        menuList.setOnRefreshListener ( new PullToRefreshBase.OnRefreshListener< ListView > ( ) {

                                            @Override
                                            public
                                            void onRefresh ( PullToRefreshBase< ListView > pullToRefreshBase ) {
                                                loadData ();
                                            }
                                        } );
        lists = new ArrayList<ListModel>();
        adapter = new ListAdapter(lists, getActivity(), rootAty.mHandler, 0);
        menuList.setAdapter(adapter);
        firstGetData();
    }

    protected void firstGetData(){
        rootAty.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity().isFinishing()) {
                    return;
                }
                menuList.setRefreshing(true);
            }
        }, 1000);
    }

    private void loadData()
    {

        if( false == rootAty.canConnect() ){
            rootAty.mHandler.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          menuList.onRefreshComplete();
                                      }
                                  });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_SHOPPING_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
        Map<String, Object> maps = new HashMap<String, Object> ();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject >() {
                                  @Override
                                  public void onResponse(JSONObject response) {
                                      //刷新列表重置业务数据
                                      rootAty.deleteAllNum = 0;
                                      rootAty.deleteIds.clear();
                                      rootAty.payAllNum = 0;
                                      rootAty.prices = 0;
                                      rootAty.payNum=0;
                                      menuList.onRefreshComplete();
                                      if(rootAty.isFinishing())
                                      {
                                          return;
                                      }
                                      JSONUtil<ListOutputModel > jsonUtil = new JSONUtil<ListOutputModel>();
                                      ListOutputModel listOutputs = new ListOutputModel();
                                      listOutputs = jsonUtil.toBean(response.toString(), listOutputs);
                                      if(null != listOutputs && null != listOutputs.getResultData() && (1==listOutputs.getResultCode()))
                                      {
                                          if(null != listOutputs.getResultData().getList() && !listOutputs.getResultData().getList().isEmpty())
                                          {
                                              //记录购物车数量
                                              CartCountModel cartCount = new CartCountModel();
                                              Iterator<CartCountModel> cartCountIt = CartCountModel.findAll(CartCountModel.class);
                                              if(cartCountIt.hasNext()) {
                                                  CartCountModel.deleteAll(CartCountModel.class);
                                              }
                                              cartCount.setCount(listOutputs.getResultData().getList().size());
                                              cartCount.save(cartCount);
                                              lists.clear();
                                              lists.addAll(listOutputs.getResultData().getList());
                                              adapter.notifyDataSetChanged();
                                          }
                                          else
                                          {
                                              menuList.setEmptyView(emptyView);
                                          }
                                      }
                                      else
                                      {
                                          //异常处理，自动切换成无数据
                                          menuList.setEmptyView(emptyView);
                                      }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {
                                      menuList.onRefreshComplete();
                                      if(rootAty.isFinishing())
                                      {
                                          return;
                                      }
                                      menuList.setEmptyView(emptyView);
                                  }
                              });
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

    @Override
    public void onFinishReceiver(MyBroadcastReceiver.ReceiverType type, Object msg) {
        if(type == MyBroadcastReceiver.ReceiverType.shopCart)
        {
            //清单结算模式
            Bundle bundle = (Bundle) msg;
            int types = bundle.getInt("type");
            if(1==types)
            {
                //编辑模式
                adapter = new ListAdapter(lists, getActivity(), rootAty.mHandler, 1);
                menuList.setAdapter(adapter);
                firstGetData();
            }
            else if(0==types)
            {
                //结算模式
                adapter = new ListAdapter(lists, getActivity(), rootAty.mHandler, 0);
                menuList.setAdapter(adapter);
                firstGetData();
            }

        }
    }
}

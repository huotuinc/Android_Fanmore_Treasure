package com.huotu.fanmore.pinkcatraiders.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.RedAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RedPacketOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RedPacketsModel;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RedEnvelopesActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 可使用红包
 */
public class RedEnvelopesuseFrag extends BaseFragment implements Handler.Callback  {
    public RedEnvelopesActivity rootAty;
    View emptyView = null;
    @Bind(R.id.raidersLogList)
    PullToRefreshListView redPackageList;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<RedPacketsModel> redPacketsModels;
    public RedAdapter adapter;
    boolean init;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public int getLayoutRes() {
        return R.layout.raiders_log_frag;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!init){
            rootAty = (RedEnvelopesActivity) getActivity();
            emptyView=LayoutInflater.from(getActivity()).inflate(R.layout.empty, null);
            TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
            emptyTag.setText("暂无可用红包信息");
            TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
            emptyBtn.setVisibility(View.GONE);
            initList();
            init=true;
        }
    }

    private void initList()
    {
        redPackageList.setMode(PullToRefreshBase.Mode.BOTH);
        redPackageList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        redPacketsModels = new ArrayList<RedPacketsModel>();
        adapter = new RedAdapter(redPacketsModels, getActivity());
        redPackageList.setAdapter(adapter);
        firstGetData();
    }
    private void loadData()
    {

        if( false == rootAty.canConnect() ){
            rootAty.mHandler.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          redPackageList.onRefreshComplete();
                                      }
                                  });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_MY_REDPACKAGES_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
        Map<String, Object> maps = new HashMap<String, Object>();
        //全部
        maps.put("type", "0");
        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            maps.put("lastId", 0);
        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( redPacketsModels != null && redPacketsModels.size() > 0)
            {
                RedPacketsModel redPacket = redPacketsModels.get(redPacketsModels.size() - 1);
                maps.put("lastId", redPacket.getPid());
            } else if (redPacketsModels != null && redPacketsModels.size() == 0)
            {
                maps.put("lastId", 0);
            }
        }
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        final HttpUtils httpUtils = new HttpUtils();
        new AsyncTask<String, Void, RedPacketOutputModel>() {
            @Override
            protected RedPacketOutputModel doInBackground(String... params) {
                RedPacketOutputModel redPacketOutput = new RedPacketOutputModel();
                try {
                    JSONUtil<RedPacketOutputModel > jsonUtil = new JSONUtil<RedPacketOutputModel>();
                    String jsonStr = httpUtils.doGet(params[0]);
                    redPacketOutput = jsonUtil.toBean(jsonStr, redPacketOutput);
                }catch (JsonSyntaxException e)
                {
                    redPacketOutput.setResultCode(0);
                    redPacketOutput.setResultDescription("解析json出错");
                } catch (Exception ex)
                {
                    // TODO: handle exception
                    return null;
                }
                return redPacketOutput;
            }

            @Override
            protected void onPostExecute(RedPacketOutputModel redPacketOutputModel) {
                super.onPostExecute(redPacketOutputModel);
                redPackageList.onRefreshComplete();
                if(null != redPacketOutputModel && null != redPacketOutputModel.getResultData() && (1==redPacketOutputModel.getResultCode()))
                {
                    if(null != redPacketOutputModel.getResultData().getList() && !redPacketOutputModel.getResultData().getList().isEmpty())
                    {
                        String[] counts = new String[]{String.valueOf(redPacketOutputModel.getResultData().getUnused()), String.valueOf(redPacketOutputModel.getResultData().getUsedOrExpire())};
                        Message message = rootAty.mHandler.obtainMessage(Contant.REDPACKAGE_COUNT, counts);
                        rootAty.mHandler.sendMessage(message);

                        if( operateType == OperateTypeEnum.REFRESH){
                            redPacketsModels.clear();
                            redPacketsModels.addAll(redPacketOutputModel.getResultData().getList());
                            adapter.notifyDataSetChanged();
                        }else if( operateType == OperateTypeEnum.LOADMORE){
                            redPacketsModels.addAll( redPacketOutputModel.getResultData().getList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        redPackageList.setEmptyView(emptyView);
                    }
                }
                else
                {
                    //异常处理，自动切换成无数据
                    redPackageList.setEmptyView(emptyView);
                }
            }
        }.execute(url);
    }
    protected void firstGetData(){
        rootAty.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (rootAty.isFinishing()) {
                    return;
                }
                operateType = OperateTypeEnum.REFRESH;
                redPackageList.setRefreshing(true);
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VolleyUtil.cancelAllRequest ( );
        ButterKnife.unbind(this);
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



    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}

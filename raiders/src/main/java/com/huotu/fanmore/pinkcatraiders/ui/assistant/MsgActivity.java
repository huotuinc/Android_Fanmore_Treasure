package com.huotu.fanmore.pinkcatraiders.ui.assistant;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.SysMsgAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.MsgData;
import com.huotu.fanmore.pinkcatraiders.model.MsgOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.ProductModel;
import com.huotu.fanmore.pinkcatraiders.model.ProductsOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统消息列表
 */
public
class MsgActivity extends BaseActivity implements Handler.Callback, View.OnClickListener {

    public Handler mHandler;

    public BaseApplication application;

    public Resources resources;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;

    @Bind ( R.id.msgList )
    PullToRefreshListView msgList;

    View emptyView = null;

    public OperateTypeEnum operateType = OperateTypeEnum.REFRESH;

    public List< MsgData > msgs;

    public SysMsgAdapter  adapter;

    public LayoutInflater inflate;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        this.setContentView ( R.layout.msg_center );
        inflate = LayoutInflater.from(MsgActivity.this);
        ButterKnife.bind ( this );
        mHandler = new Handler ( this );
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        emptyView = inflate.inflate ( R.layout.empty, null );
        TextView emptyTag = (TextView) emptyView.findViewById(R.id.emptyTag);
        emptyTag.setText("暂无系统消息");
        TextView emptyBtn = (TextView) emptyView.findViewById(R.id.emptyBtn);
        emptyBtn.setVisibility(View.GONE);
        initTitle ( );
    }

    private
    void initTitle ( ) {
        //设置title背景
        Drawable drawable = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, drawable );
        //设置左侧图标
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate ( );
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText ( "系统消息" );

        msgList.setMode ( PullToRefreshBase.Mode.BOTH );
        msgList.setOnRefreshListener (
                new PullToRefreshBase.OnRefreshListener2< ListView > ( ) {

                    @Override
                    public
                    void onPullDownToRefresh ( PullToRefreshBase< ListView > pullToRefreshBase ) {
                        //上拉
                                             operateType = OperateTypeEnum.REFRESH;
                                             loadData();
                                         }

                                         @Override
                                         public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                                             //下拉
                                             operateType = OperateTypeEnum.LOADMORE;
                                             loadData();
                                         }
                                     });
        msgs = new ArrayList<MsgData> ();
        adapter = new SysMsgAdapter(MsgActivity.this, msgs);
        msgList.setAdapter(adapter);
        firstGetData();
    }

    private void loadData()
    {
        if( false == MsgActivity.this.canConnect() ){
                mHandler.post(new Runnable() {
                @Override
                public void run() {

                    msgList.onRefreshComplete();
                }
            });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.MESSAGE;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), MsgActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        Paging paging = new Paging();
        if ( OperateTypeEnum.REFRESH == operateType )
        {// 下拉
            paging.setPagingTag("");

        } else if (OperateTypeEnum.LOADMORE == operateType)
        {// 上拉
            if ( msgs != null && msgs.size() > 0)
            {
                paging.setPagingTag(String.valueOf(msgs.get(msgs.size()-1).getMessageOrder()));
            } else
            {
                paging.setPagingTag("");
            }
        }
        maps.put("paging", paging.getPagingTag());
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                msgList.onRefreshComplete();
                if (MsgActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<MsgOutputModel> jsonUtil = new JSONUtil<MsgOutputModel>();
                MsgOutputModel msgOutput = new MsgOutputModel();
                msgOutput = jsonUtil.toBean(response.toString(), msgOutput);
                if (null != msgOutput && null != msgOutput.getResultData() && (1 == msgOutput.getResultCode())) {
                    if (null != msgOutput.getResultData().getMessages() && !msgOutput.getResultData().getMessages().isEmpty()) {

                        if (operateType == OperateTypeEnum.REFRESH) {
                            msgs.clear();
                            msgs.addAll(msgOutput.getResultData().getMessages());
                            adapter.notifyDataSetChanged();
                        } else if (operateType == OperateTypeEnum.LOADMORE) {
                            msgs.addAll(msgOutput.getResultData().getMessages());
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        //空数据处理
                        msgList.setEmptyView ( emptyView );
                    }
                } else {
                    //异常处理，自动切换成无数据
                    //空数据处理
                    msgList.setEmptyView(emptyView );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                msgList.onRefreshComplete();
                if (MsgActivity.this.isFinishing()) {
                    return;
                }
                //空数据处理
                msgList.setEmptyView(emptyView );
            }
        });
    }

    protected void firstGetData(){
        mHandler.postDelayed (
                new Runnable ( ) {

                    @Override
                    public
                    void run ( ) {

                        if ( MsgActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        operateType = OperateTypeEnum.REFRESH;
                        msgList.setRefreshing ( true );
                    }
                }, 1000
                             );
    }
    @OnClick (R.id.titleLeftImage)
    void doBack()
    {
        this.closeSelf(MsgActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtil.cancelAllRequest ();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
            && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(MsgActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public
    boolean handleMessage ( Message msg ) {

        return false;
    }

    @Override
    public
    void onClick ( View v ) {

    }

    public class Paging
    {
        private Integer pagingSize;
        private String pagingTag;

        public String getPagingTag()
        {
            return pagingTag;
        }
        public void setPagingTag(String pagingTag)
        {
            this.pagingTag = pagingTag;
        }
        public Integer getPagingSize()
        {
            return pagingSize;
        }
        public void setPagingSize(Integer pagingSize)
        {
            this.pagingSize = pagingSize;
        }
    }
}

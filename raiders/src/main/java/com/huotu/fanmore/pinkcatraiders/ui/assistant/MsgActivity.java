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

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.SysMsgAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.MsgData;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import java.util.ArrayList;
import java.util.List;

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
        msgList.onRefreshComplete ();
        msgList.setEmptyView ( emptyView );
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
}

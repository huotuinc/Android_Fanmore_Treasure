package com.huotu.fanmore.pinkcatraiders.ui.raiders;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.ListAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.MyAddressAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.MyAddressListModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressListActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    @Bind(R.id.addressList)
    PullToRefreshListView addressList;
    View emptyView = null;
    public OperateTypeEnum operateType= OperateTypeEnum.REFRESH;
    public List<MyAddressListModel> lists;
    public MyAddressAdapter adapter;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.textright)
    TextView textright;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_address_list);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        initTitle();
        initList();
    }
    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable(R.drawable.account_bg_bottom);
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("地址列表");
    }

    private void initList() {
        addressList.setMode(PullToRefreshBase.Mode.BOTH);
        addressList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        lists = new ArrayList<MyAddressListModel>();
        adapter = new MyAddressAdapter(lists, this, 0);
        addressList.setAdapter(adapter);
        //firstGetData();
    }
    private void loadData()
    {
        /*if(null!=lists&&!lists.isEmpty())
        {*/
        addressList.onRefreshComplete();
        MyAddressListModel list1 = new MyAddressListModel();
        list1.setCityId(1);
        list1.setDefaultAddress(1000);
        list1.setDetails("想嘻嘻嘻嘻嘻嘻嘻嘻嘻想");
        list1.setMobile("165465465");
        list1.setReceiver("dadsad");
        lists.add(list1);
        MyAddressListModel list2 = new MyAddressListModel();
        list2.setCityId(1);
        list2.setDefaultAddress(1000);
        list2.setDetails("想嘻嘻嘻嘻嘻嘻嘻嘻嘻想");
        list2.setMobile("165465465");
        list2.setReceiver("dadsad");
        lists.add(list2);
        MyAddressListModel list3 = new MyAddressListModel();
        list3.setCityId(1);
        list3.setDefaultAddress(1000);
        list3.setDetails("想嘻嘻嘻嘻嘻嘻嘻嘻嘻想");
        list3.setMobile("165465465");
        list3.setReceiver("dadsad");
        lists.add(list1);

    }
    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(AddressListActivity.this);
    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

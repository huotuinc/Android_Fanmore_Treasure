package com.huotu.fanmore.pinkcatraiders.ui.raiders;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.adapter.ListAdapter;
import com.huotu.fanmore.pinkcatraiders.adapter.MyAddressAdapter;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AddressOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.ListModel;
import com.huotu.fanmore.pinkcatraiders.model.MyAddressListModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.AddAddressActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 添加地址
 */

public class AddressListActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, MyBroadcastReceiver.BroadcastListener {

    public
    Resources resources;

    public BaseApplication application;

    public Handler mHandler;

    public
    WindowManager wManager;

    @Bind ( R.id.addressList )
    PullToRefreshListView addressList;

    View emptyView = null;

    public List< MyAddressListModel > lists;

    public MyAddressAdapter adapter;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;

    @Bind ( R.id.titleRightImage )
    ImageView titleRightImage;

    public LayoutInflater inflate;

    public ListView       list;

    public
    ProgressPopupWindow progress;
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    public
    boolean onKeyDown ( int keyCode, KeyEvent event ) {

        if ( keyCode == KeyEvent.KEYCODE_BACK
             && event.getAction ( ) == KeyEvent.ACTION_DOWN ) {
            //关闭
            this.closeSelf ( AddressListActivity.this );
        }
        return super.onKeyDown ( keyCode, event );
    }

    @Override
    protected
    void onDestroy ( ) {

        super.onDestroy ( );
        VolleyUtil.cancelAllRequest ( );
        ButterKnife.unbind ( this );
    }

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView(R.layout.ri_address_list);
        ButterKnife.bind(this);
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        inflate = LayoutInflater.from ( AddressListActivity.this );
        wManager = this.getWindowManager();
        emptyView = inflate.inflate(R.layout.empty, null);
        progress = new ProgressPopupWindow ( AddressListActivity.this, AddressListActivity.this, wManager );
        myBroadcastReceiver = new MyBroadcastReceiver(AddressListActivity.this, this, MyBroadcastReceiver.TO_ADDRESSLIST);
        TextView emptyTag = ( TextView ) emptyView.findViewById ( R.id.emptyTag );
        emptyTag.setText ( "暂无收货地址信息" );
        TextView emptyBtn = ( TextView ) emptyView.findViewById ( R.id.emptyBtn );
        emptyBtn.setVisibility ( View.GONE );
        initTitle ( );
        initList ( );
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        stubTitleText.inflate ( );
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        SystemTools.loadBackground ( titleRightImage, resources.getDrawable ( R.mipmap.add_address ) );
        titleText.setText ( "地址列表" );
    }

    private
    void initList ( ) {

        addressList.setMode ( PullToRefreshBase.Mode.BOTH );
        addressList.setOnRefreshListener (
                new PullToRefreshBase.OnRefreshListener< ListView > ( ) {

                    @Override
                    public
                    void onRefresh ( PullToRefreshBase< ListView > pullToRefreshBase ) {

                        loadData ( );
                    }
                }
                                         );
        lists = new ArrayList<MyAddressListModel>();
        adapter = new MyAddressAdapter(lists, this, AddressListActivity.this, 0);
        addressList.setAdapter ( adapter );
        firstGetData ( );

        list = addressList.getRefreshableView();
        list.setOnItemLongClickListener (
                new AdapterView.OnItemLongClickListener ( ) {

                    @Override
                    public
                    boolean onItemLongClick (
                            AdapterView< ? > parent, View view, final int position,
                            long id
                                            ) {

                        final AlertDialog.Builder dialog = new AlertDialog.Builder(
                                AddressListActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        final AlertDialog alertdialog = dialog.create();
                        LayoutInflater inflater = LayoutInflater.from(AddressListActivity.this);
                        View view1 = inflater.inflate(R.layout.activity_dialog, null);
                        alertdialog.setView(view1, 0, 0, 0, 0);
                        TextView titletext = (TextView) view1.findViewById(R.id.titletext);
                        TextView messagetext = (TextView) view1.findViewById(R.id.messagetext);
                        Button btn_lift = (Button) view1.findViewById(R.id.btn_lift);
                        Button btn_right = (Button) view1.findViewById(R.id.btn_right);
                        titletext.setTextColor(getResources().getColor(R.color.text_black));
                        btn_lift.setTextColor(getResources().getColor(R.color.color_blue));
                        btn_right.setTextColor(getResources().getColor(R.color.color_blue));
                        titletext.setText("删除地址");
                        messagetext.setText("确定删除地址吗?");
                        btn_lift.setText("取消");
                        btn_right.setText("确定");
                        btn_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertdialog.dismiss();
                                deleteAddress(lists.get(position - 1).getAddressId());

                            }
                        });
                        btn_lift.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertdialog.dismiss();
                            }
                        });

                        alertdialog.show();
                        return false;
                    }
                }
                                        );
    }

    protected
    void firstGetData ( ) {

        mHandler.postDelayed (
                new Runnable ( ) {

                    @Override
                    public
                    void run ( ) {

                        if ( AddressListActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        addressList.setRefreshing ( true );
                    }
                }, 1000
                             );
    }

    private void deleteAddress(long addressId)
    {
        progress.showProgress ( "正在删除数据" );
        progress.showAtLocation (
                titleLayoutL,
                Gravity.CENTER, 0, 0
                                );
        String url = Contant.REQUEST_URL + Contant.DELETE_ADDRESS;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), AddressListActivity.this);
        Map<String, Object> maps = new HashMap<String, Object> ();
        maps.put ( "addressId", String.valueOf ( addressId ) );
        Map<String, Object> param = params.obtainPostParam(maps);
        BaseModel base = new BaseModel ();
        HttpUtils<BaseModel> httpUtils = new HttpUtils<BaseModel> ();
        httpUtils.doVolleyPost (
                base, url, param, new Response.Listener< BaseModel > ( ) {
                    @Override
                    public
                    void onResponse ( BaseModel response ) {
                        progress.dismissView ();
                        BaseModel base = response;
                        if(1==base.getResultCode ())
                        {
                            //删除成功
                            lists.clear();
                            firstGetData();
                        }
                        else
                        {
                            //上传失败
                            ToastUtils.showMomentToast(AddressListActivity.this, AddressListActivity.this, "地址删除失败");
                        }
                    }
                }, new Response.ErrorListener ( ) {

                    @Override
                    public
                    void onErrorResponse ( VolleyError error ) {
                        progress.dismissView ( );
                        //系统级别错误
                        ToastUtils.showMomentToast(AddressListActivity.this, AddressListActivity.this, "服务器未响应");
                    }
                }
                               );
    }

    @OnClick(R.id.titleRightImage)
    void addAddress()
    {

        ActivityUtils.getInstance ().showActivity(AddressListActivity.this, AddAddressActivity.class);
    }

    private void loadData()
    {

        if( false == AddressListActivity.this.canConnect ( ) ){
            mHandler.post(new Runnable() {
                                      @Override
                                      public void run() {
                                          addressList.onRefreshComplete();
                                      }
                                  });
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_MY_ADDRESS_LIST;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), AddressListActivity.this);
        Map<String, Object> maps = new HashMap<String, Object> ();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject >() {
                                  @Override
                                  public void onResponse(JSONObject response) {
                                      addressList.onRefreshComplete();
                                      if(AddressListActivity.this.isFinishing ( ))
                                      {
                                          return;
                                      }
                                      JSONUtil<AddressOutputModel> jsonUtil = new JSONUtil<AddressOutputModel>();
                                      AddressOutputModel addressOutput = new AddressOutputModel();
                                      addressOutput = jsonUtil.toBean(response.toString(), addressOutput);
                                      if(null != addressOutput && null != addressOutput.getResultData() && (1==addressOutput.getResultCode())&&null!=addressOutput.getResultData().getList())
                                      {
                                          List<MyAddressListModel> list = addressOutput.getResultData().getList();
                                          if(null!=list&&!list.isEmpty())
                                          {
                                              lists.clear();
                                              lists.addAll(addressOutput.getResultData().getList());
                                              adapter.notifyDataSetChanged();
                                          }
                                          else
                                          {
                                              //异常处理，自动切换成无数据
                                              addressList.setEmptyView(emptyView);
                                          }

                                      }
                                      else
                                      {
                                          //异常处理，自动切换成无数据
                                          addressList.setEmptyView(emptyView);
                                      }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {
                                      addressList.onRefreshComplete();
                                      if(AddressListActivity.this.isFinishing ( ))
                                      {
                                          return;
                                      }
                                      addressList.setEmptyView(emptyView);
                                  }
                              });

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

    @Override
    public void onFinishReceiver(MyBroadcastReceiver.ReceiverType type, Object msg) {
        if (type==MyBroadcastReceiver.ReceiverType.toaddresslist){
            firstGetData();

        }
    }
}

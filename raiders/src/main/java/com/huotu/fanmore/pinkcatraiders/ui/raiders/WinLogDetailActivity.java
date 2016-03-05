package com.huotu.fanmore.pinkcatraiders.ui.raiders;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.fragment.FragManager;
import com.huotu.fanmore.pinkcatraiders.model.AppDeliveryModel;
import com.huotu.fanmore.pinkcatraiders.model.AppUserBuyFlowModel;
import com.huotu.fanmore.pinkcatraiders.model.DeliveryOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.PayModel;
import com.huotu.fanmore.pinkcatraiders.model.PayOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.PayFunc;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WinLogDetailActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    @Bind( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;
    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;
    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;
    public Bundle bundle;
    private AppUserBuyFlowModel winner;
    @Bind(R.id.windetailPullRefresh)
    PullToRefreshScrollView windetailPullRefresh;
    @Bind(R.id.prizeStatusL)
    LinearLayout prizeStatusL;
    @Bind(R.id.addressL)
    LinearLayout addressL;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.userPhone)
    TextView userPhone;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.productL)
    LinearLayout productL;
    @Bind(R.id.pictureUrl)
    ImageView pictureUrl;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.issueId)
    TextView issueId;
    @Bind(R.id.toAmount)
    TextView toAmount;
    @Bind(R.id.lunkyNumber)
    TextView lunkyNumber;
    @Bind(R.id.attendAmount)
    TextView attendAmount;
    @Bind(R.id.awardingDate)
    TextView awardingDate;
    @Bind(R.id.status1Icon)
    ImageView status1Icon;
    @Bind(R.id.status1Tag)
    TextView status1Tag;
    @Bind(R.id.status1Time)
    TextView status1Time;
    @Bind(R.id.status2Icon)
    ImageView status2Icon;
    @Bind(R.id.status2Tag)
    TextView status2Tag;
    @Bind(R.id.status2Time)
    TextView status2Time;
    @Bind(R.id.status3Icon)
    ImageView status3Icon;
    @Bind(R.id.status3Tag)
    TextView status3Tag;
    @Bind(R.id.status3Time)
    TextView status3Time;
    @Bind(R.id.status4Icon)
    ImageView status4Icon;
    @Bind(R.id.status4Tag)
    TextView status4Tag;
    @Bind(R.id.status4Time)
    TextView status4Time;
    @Bind(R.id.status5Icon)
    ImageView status5Icon;
    @Bind(R.id.status5Tag)
    TextView status5Tag;
    @Bind(R.id.status5Time)
    TextView status5Time;
    public
    ProgressPopupWindow progress;
    //引导图片资源


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_win_log_detail);
        ButterKnife.bind(this);
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources();
        wManager = this.getWindowManager();
        mHandler = new Handler ( this );
        bundle = this.getIntent().getExtras();
        winner = (AppUserBuyFlowModel) bundle.getSerializable("winner");
        initTitle();
        initScroll();
    }

    private void initTitle()
    {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable(R.mipmap.back_gray);
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("中奖确认");
    }

    private void initScroll()
    {
        initData();
        windetailPullRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                initData();
            }
        });
    }

    private void initData()
    {
        //加载奖品状态

        String url    = Contant.REQUEST_URL + Contant.GET_ONE_LOTTERY_INFO;
        AuthParamUtils params = new AuthParamUtils ( application, System.currentTimeMillis
                ( ), WinLogDetailActivity.this );
        Map< String, Object > maps   = new HashMap< String, Object >( );
        maps.put ( "issueId", winner.getIssueId() );
        String                suffix = params.obtainGetParam ( maps );
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils ( );
        httpUtils.doVolleyGet (
                url, new Response.Listener<JSONObject> ( ) {

                    @Override
                    public
                    void onResponse ( JSONObject response ) {
                        windetailPullRefresh.onRefreshComplete();
                        if ( WinLogDetailActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        JSONUtil<DeliveryOutputModel> jsonUtil = new JSONUtil<
                                DeliveryOutputModel > ( );
                        DeliveryOutputModel deliveryOutput = new DeliveryOutputModel
                                ( );
                        deliveryOutput = jsonUtil.toBean ( response.toString ( ), deliveryOutput );
                        if ( null != deliveryOutput && null != deliveryOutput.getResultData ( )
                                && ( 1 == deliveryOutput.getResultCode ( ) ) ) {
                            AppDeliveryModel deliveryModel = deliveryOutput.getResultData().getData();
                            //加载地址信息
                            userName.setText(deliveryModel.getReceiver());
                            userPhone.setText(deliveryModel.getMobile());
                            address.setText(deliveryModel.getDetails());

                            //加载奖品信息
                            BitmapLoader.create().displayUrl(WinLogDetailActivity.this, pictureUrl, winner.getDefaultPictureUrl(), R.mipmap.error);
                            title.setText(winner.getTitle());
                            issueId.setText("参与期号："+winner.getIssueId());
                            toAmount.setText("总需："+winner.getToAmount());
                            lunkyNumber.setText(""+winner.getLuckyNumber());
                            attendAmount.setText("本期参与："+winner.getAmount());
                            awardingDate.setText("揭晓时间:"+ DateUtils.transformDataformat1(winner.getAwardingDate()));
                        }
                        else {
                            //异常处理，自动切换成无数据
                        }
                    }
                }, new Response.ErrorListener ( ) {

                    @Override
                    public
                    void onErrorResponse ( VolleyError error ) {
                        windetailPullRefresh.onRefreshComplete();
                        if ( WinLogDetailActivity.this.isFinishing ( ) ) {
                            return;
                        }
                    }
                }
        );

    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(WinLogDetailActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(WinLogDetailActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}

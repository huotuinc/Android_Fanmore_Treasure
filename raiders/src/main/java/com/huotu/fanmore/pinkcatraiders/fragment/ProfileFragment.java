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
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.RechargeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.RechargeLogActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.orders.ShowOrderActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.BuyLogActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RaidesLogActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RedEnvelopesActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.SettingActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.ShareOrderActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.UserSettingActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.WinLogActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.PreferenceHelper;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心fragment
 */
public class ProfileFragment extends BaseFragment implements Handler.Callback {
    View rootView;
    public Resources resources;
    public BaseApplication application;
    public HomeActivity rootAty;
    public WindowManager wManager;
    @Bind(R.id.Userimg)
    CircleImageView userimg;
    @Bind(R.id.TVUserName)
    TextView TVUserName;
    @Bind(R.id.money)
    TextView money;

    @Bind(R.id.profilePullRefresh)
    PullToRefreshScrollView profilePullRefresh;
    @Bind(R.id.mallPoints)
    TextView mallPoints;

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
        super.onCreate(savedInstanceState);
        resources = getActivity().getResources ( );
        rootView = inflater.inflate(R.layout.profile_frag, container, false);
        application = (BaseApplication) getActivity().getApplication ( );
        rootAty = (HomeActivity) getActivity();
        ButterKnife.bind(this, rootView);
        userimg.setBorderColor(resources.getColor(R.color.color_white));
        userimg.setBorderWidth((int) resources.getDimension(R.dimen.head_width));
        initScroll();
        wManager = getActivity().getWindowManager();
        return rootView;
    }

    private void initScroll()
    {
        loadData();
        profilePullRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                loadData();
            }
        });
    }

    private void loadData()
    {
        profilePullRefresh.onRefreshComplete();
        mallPoints.setText(String.valueOf(application.readMallPoints())+"积分");
        String imgurl= PreferenceHelper.readString (getActivity(), Contant.LOGIN_USER_INFO, Contant.LOGIN_AUTH_UDERHEAD);
        BitmapLoader.create().loadRoundImage ( getActivity ( ), userimg, imgurl, R.mipmap.error );
        TVUserName.setText ( PreferenceHelper.readString ( getActivity ( ), Contant
                .LOGIN_USER_INFO, Contant.LOGIN_AUTH_REALNAME ) );
        String balance = PreferenceHelper.readString(getActivity(), Contant.LOGIN_USER_INFO, Contant.LOGIN_AUTH_MONEY);
        money.setText((null!=balance&&!balance.isEmpty ()&&!"null".equals ( balance ))?balance+"元":0+"元");
    }

    @OnClick(R.id.settingL)
    void toseting()
    {
      //跳转到个人设置
        ActivityUtils.getInstance().showActivity(getActivity(), SettingActivity.class);
    }
    @OnClick(R.id.ll1)
    void toll1()
    {
        ActivityUtils.getInstance().showActivity(getActivity(),RaidesLogActivity.class,"index",1);
    }
    @OnClick(R.id.ll2)
    void toll2()
    {
        ActivityUtils.getInstance().showActivity(getActivity(),RaidesLogActivity.class,"index",2);
    }
    @OnClick(R.id.shareL)
    void toshare()
    {
        Bundle bundle = new Bundle (  );
        //个人中心晒单
        bundle.putInt ( "type", 1 );
        ActivityUtils.getInstance().showActivity(getActivity(), ShowOrderActivity.class, bundle);
    }
    @OnClick(R.id.raideLogL)
    void toRaideLog()
    {
        //跳转到夺宝记录
        ActivityUtils.getInstance().showActivity(getActivity(), RaidesLogActivity.class);
    }

    @OnClick(R.id.redpacketL)
    void toredpacket()
    {
        //跳转到红包
        ActivityUtils.getInstance().showActivity(getActivity(), RedEnvelopesActivity.class);
    }
    @OnClick(R.id.winningLogL)
    void towinningLog()
    {
        //跳转到中奖记录
        ActivityUtils.getInstance().showActivity(getActivity(), WinLogActivity.class);
    }

    @OnClick(R.id.rechargeLogL)
    void showRechargeLog()
    {
        //跳转到充值记录
        ActivityUtils.getInstance().showActivity(getActivity(), RechargeLogActivity.class);
    }

    @OnClick(R.id.userNameL)
    void doShowSeeting()
    {
        ActivityUtils.getInstance().showActivity(getActivity(), UserSettingActivity.class);
    }

    @OnClick(R.id.txtScore)
    void toRecharge()
    {
        //跳转到充值界面
        ActivityUtils.getInstance().showActivity(getActivity(), RechargeActivity.class);
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

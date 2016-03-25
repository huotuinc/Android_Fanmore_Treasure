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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.model.UserOutputModel;
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
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.PreferenceHelper;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        //刷新用户信息
        String url = Contant.REQUEST_URL + Contant.UPDATE_USER_INFORMATION;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), getActivity());
        Map<String, Object> maps = new HashMap<String, Object>();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                profilePullRefresh.onRefreshComplete();
                JSONUtil<UserOutputModel> jsonUtil = new JSONUtil<UserOutputModel>();
                UserOutputModel userOutput = new UserOutputModel();
                userOutput = jsonUtil.toBean(response.toString(), userOutput);

                if (null != userOutput && null != userOutput.getResultData() && null != userOutput.getResultData().getUser() && 1 == userOutput.getResultCode()) {
                    application.writeUserInfo(userOutput.getResultData().getUser());
                    SystemTools.loadBackground(mallPoints, resources.getDrawable(R.drawable.mall_points_draw));
                    mallPoints.setText(String.valueOf(null==userOutput.getResultData().getUser().getIntegral()?0:userOutput.getResultData().getUser().getIntegral()) + "积分");
                    String imgurl = userOutput.getResultData().getUser().getUserHead();
                    BitmapLoader.create().loadRoundImage(getActivity(), userimg, imgurl, R.mipmap.error);
                    TVUserName.setText(userOutput.getResultData().getUser().getRealName());
                    String balance = String.valueOf(userOutput.getResultData().getUser().getMoney());
                    money.setText((null != balance && !balance.isEmpty() && !"null".equals(balance)) ? balance + "元" : 0 + "元");

                } else {
                    ToastUtils.showMomentToast(getActivity(), getActivity(), "刷新用户数据出现问题");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                profilePullRefresh.onRefreshComplete();
                ToastUtils.showMomentToast(getActivity(), getActivity(), "刷新用户数据出现问题");
            }
        });
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(getActivity());
        VolleyUtil.cancelAllRequest();
    }

    protected
    void firstGetData ( ) {

        rootAty.mHandler.postDelayed(
                new Runnable() {

                    @Override
                    public void run() {

                        if (getActivity().isFinishing()) {
                            return;
                        }
                        profilePullRefresh.setRefreshing(true);
                    }
                }, 1000
        );
    }
}

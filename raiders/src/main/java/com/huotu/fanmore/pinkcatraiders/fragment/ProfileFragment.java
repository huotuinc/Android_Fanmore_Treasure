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

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.RechargeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.RechargeLogActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.BuyLogActivity;
import com.huotu.fanmore.pinkcatraiders.ui.raiders.RaidesLogActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
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
        rootView = inflater.inflate(R.layout.profile_frag, container, false);
        application = (BaseApplication) getActivity().getApplication();
        rootAty = (HomeActivity) getActivity();
        ButterKnife.bind(this, rootView);
        BitmapLoader.create().loadRoundImage(getActivity(), userimg, "http://imgk.zol.com.cn/dcbbs/2342/a2341460.jpg", R.mipmap.error);
        userimg.setBorderColor(resources.getColor(R.color.color_white));
        userimg.setBorderWidth((int)resources.getDimension(R.dimen.head_width));
        wManager = getActivity().getWindowManager();
        return rootView;
    }

    @OnClick(R.id.raideLogL)
    void toRaideLog()
    {
        //跳转到夺宝记录
        ActivityUtils.getInstance().showActivity(getActivity(), RaidesLogActivity.class);
    }

    @OnClick(R.id.winningLogL)
    void towinningLog()
    {
        //跳转到中奖记录
        ActivityUtils.getInstance().showActivity(getActivity(), BuyLogActivity.class);
    }

    @OnClick(R.id.rechargeLogL)
    void showRechargeLog()
    {
        //跳转到充值记录
        ActivityUtils.getInstance().showActivity(getActivity(), RechargeLogActivity.class);
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

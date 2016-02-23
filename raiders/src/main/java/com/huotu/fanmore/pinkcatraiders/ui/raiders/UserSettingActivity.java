package com.huotu.fanmore.pinkcatraiders.ui.raiders;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.BottomModel;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.ModifyInfoActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;
import com.huotu.fanmore.pinkcatraiders.widget.CommonPopWin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserSettingActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public
    Resources resources;

    public BaseApplication application;

    public Handler         mHandler;

    public
    WindowManager           wManager;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout          titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub                stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView               titleLeftImage;

    @Bind ( R.id.Userimg )
    CircleImageView         userimg;

    @Bind ( R.id.userSettingPullRefresh )
    PullToRefreshScrollView userSettingPullRefresh;
    @Bind ( R.id.userPhone )
    TextView userPhone;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_user_setting );
        ButterKnife.bind ( this );
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        wManager = this.getWindowManager ( );
        initTitle ( );
        initSroll();
    }

    private void initSroll()
    {
        initData ( );
        userSettingPullRefresh.setOnRefreshListener (
                new PullToRefreshBase.OnRefreshListener< ScrollView > ( ) {


                    @Override
                    public
                    void onRefresh ( PullToRefreshBase< ScrollView > pullToRefreshBase ) {

                        initData ( );
                    }
                }
                                                    );
    }

    private void initData()
    {
        BitmapLoader.create ( ).loadRoundImage (
                this, userimg, "http://imgk.zol.com"
                               + ".cn/dcbbs/2342/a2341460.jpg",
                R.mipmap.error
                                               );
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
        titleText.setText ( "个人资料" );
    }

    @OnClick ( R.id.titleLeftImage )
    void doBack ( ) {

        closeSelf ( UserSettingActivity.this );
    }
    @OnClick(R.id.userAddressL)
    void toaddress()
    {
        ActivityUtils.getInstance().showActivity ( this, AddressListActivity.class );
    }
    @OnClick(R.id.userLogoL)
    void doUserLogo()
    {
        //设置头像
        Map<String, String> param = new HashMap<String, String> ();
        //插入图片
        //上传封面
        List<BottomModel > bottoms = new ArrayList<BottomModel> ();
        BottomModel bottom1 = new BottomModel();
        bottom1.setBottomTag("camera");
        bottom1.setBottomName("拍照");
        bottoms.add(bottom1);
        BottomModel bottom2 = new BottomModel();
        bottom2.setBottomTag("fromSD");
        bottom2.setBottomName("从手机相册选择");
        bottoms.add(bottom2);
        BottomModel bottom3 = new BottomModel();
        bottom3.setBottomTag("cancel");
        bottom3.setBottomName("取消");
        bottoms.add(bottom3);
        //回复点击事件
        CommonPopWin commonPopWin = new CommonPopWin(UserSettingActivity.this, bottoms, application, wManager, mHandler, titleLeftImage, param);
        commonPopWin.initView();
        commonPopWin.showAtLocation (titleLeftImage, Gravity.BOTTOM, 0, 0);
        commonPopWin.setOnDismissListener(new PoponDismissListener (UserSettingActivity.this));
    }
    @OnClick(R.id.userPhoneL)
    void doPhoneL()
    {
        //设置手机号码
        Bundle bundle = new Bundle (  );
        bundle.putString ( "profile", "手机号码" );
        bundle.putString ( "content", userPhone.getText ().toString () );
        ActivityUtils
                .getInstance ().showActivity ( UserSettingActivity.this, ModifyInfoActivity.class, bundle );
    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

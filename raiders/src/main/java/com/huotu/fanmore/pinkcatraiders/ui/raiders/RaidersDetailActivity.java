package com.huotu.fanmore.pinkcatraiders.ui.raiders;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.model.RaidersModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 夺宝详情
 */
public class RaidersDetailActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {


    public
    Resources resources;
    public BaseApplication application;
    public Handler mHandler;
    public
    WindowManager wManager;
    @Bind(R.id.titleLayoutL)
    RelativeLayout titleLayoutL;
    @Bind(R.id.titleLeftImage)
    ImageView titleLeftImage;
    @Bind(R.id.titleRightImage)
    ImageView titleRightImage;
    @Bind(R.id.stubTitleText)
    ViewStub stubTitleText;

    public Bundle bundle;

    @Bind(R.id.productName)
    TextView productName;
    @Bind(R.id.productIssue)
    TextView productIssue;
    @Bind(R.id.partnerCount)
    TextView partnerCount;
    @Bind(R.id.raidersTime)
    TextView raidersTime;
    @Bind(R.id.parentCount)
    TextView parentCount;
    @Bind(R.id.showNum)
    TextView showNum;

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.raiders_detail);
        ButterKnife.bind(this);
        application = (BaseApplication) this.getApplication();
        resources = this.getResources();
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        bundle = this.getIntent().getExtras();
        initTitle();
        initData();
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
        titleText.setText("夺宝详情");
    }

    private void initData()
    {
        RaidersModel raider = (RaidersModel) bundle.getSerializable("raider");
        productName.setText(raider.getTitle());
        productIssue.setText("期号："+raider.getIssueId());
        partnerCount.setText("参与了"+raider.getAttendAmount()+"人次");
        raidersTime.setText(DateUtils.transformDataformat6(raider.getAwardingDate()));
        parentCount.setText(raider.getAttendAmount()+"人次");
    }

    @OnClick(R.id.showNum)
    void showNumber()
    {
        //

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(RaidersDetailActivity.this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction ( ) == KeyEvent.ACTION_DOWN ) {
            //关闭
            this.closeSelf ( RaidersDetailActivity.this );
        }
        return super.onKeyDown ( keyCode, event );
    }
}

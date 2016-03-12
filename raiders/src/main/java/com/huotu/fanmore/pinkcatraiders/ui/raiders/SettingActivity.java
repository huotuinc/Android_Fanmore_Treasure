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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.DataCleanManager;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {
    public
    Resources resources;

    public BaseApplication application;

    public Handler mHandler;

    public
    WindowManager wManager;
    public
    ProgressPopupWindow successProgress;

    @Bind( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;
    @Bind(R.id.problemL)
    LinearLayout problemL;
    @Bind(R.id.cleanL)
    LinearLayout cleanL;
    @Bind(R.id.layoutL)
    LinearLayout layoutL;
    @Bind(R.id.data)
    TextView data;
    @Bind(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        initTitle();
        inintData();
    }

    private void inintData(){
//            data.setText(DataCleanManager.getFormatSize());
    }
    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText("设置");
    }

    @OnClick( R.id.titleLeftImage )
    void doBack ( ) {

        closeSelf(SettingActivity.this);
    }
    @OnClick( R.id.cleanL)
    void clean(){

    }
    @OnClick(R.id.layoutL)
    void layout(){

    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

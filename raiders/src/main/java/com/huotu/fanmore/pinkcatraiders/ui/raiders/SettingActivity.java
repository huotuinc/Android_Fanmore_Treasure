package com.huotu.fanmore.pinkcatraiders.ui.raiders;


import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;

import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;

import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.WebExhibitionActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.HomeActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.DataCleanManager;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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
    File file =new File("/data/data/"+ Contant.SYS_PACKAGE+"/cache");
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
        try {
            inintData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inintData() throws Exception {

        String datatext = DataCleanManager.getFormatSize((DataCleanManager.getFolderSize(file) * 1.0));
        if (datatext.equals("0.0Byte")) {
            data.setText("0KB");
        } else {
            data.setText(datatext);
        }
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
        DataCleanManager.cleanCustomCache("/data/data/"+ Contant.SYS_PACKAGE+"/cache");
        DataCleanManager.cleanCustomCache("/data/data/"+ Contant.SYS_PACKAGE+"/cache/volley");
        try {
            inintData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.layoutL)
    void layoutapp(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(
                SettingActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        final AlertDialog alertdialog = dialog.create();
        LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
        View view = inflater.inflate(R.layout.activity_dialog, null);
        alertdialog.setView(view, 0, 0, 0, 0);
        TextView titletext = (TextView) view.findViewById(R.id.titletext);
        TextView messagetext = (TextView) view.findViewById(R.id.messagetext);
        Button btn_lift = (Button) view.findViewById(R.id.btn_lift);
        Button btn_right = (Button) view.findViewById(R.id.btn_right);
        titletext.setTextColor(getResources().getColor(R.color.text_black));
        btn_lift.setTextColor(getResources().getColor(R.color.color_blue));
        btn_right.setTextColor(getResources().getColor(R.color.color_blue));
            titletext.setText("退出登录");
            messagetext.setText("确定要退出登录吗?");
            btn_lift.setText("取消");
            btn_right.setText("确定");

            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog.dismiss();
                    logout();
                    ActivityUtils.getInstance().skipActivity(SettingActivity.this, HomeActivity.class);

                }
            });
            btn_lift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog.dismiss();
                }
            });

            alertdialog.show();
        }

    public void logout(){


       application.ClearUser();
        //清除微信授权信息
        ShareSDK.getPlatform(Wechat.NAME).removeAccount();

        Platform platform = new QQ(SettingActivity.this);
        if(platform!=null) {
            platform.removeAccount();
        }
        //清除地址信息
        application.localAddress = null;
    }
    @OnClick(R.id.problemL)
    void showweb()
    {
        Bundle bundle = new Bundle();
        bundle.putString("title", "常见问题");
        bundle.putString("link",  application.readHelpURL());
        ActivityUtils.getInstance().showActivity(SettingActivity.this, WebExhibitionActivity.class,
                bundle);
    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}

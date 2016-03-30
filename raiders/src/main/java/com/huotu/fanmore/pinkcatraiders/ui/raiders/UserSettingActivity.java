package com.huotu.fanmore.pinkcatraiders.ui.raiders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.eventbus.EventBus;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.AppUserModel;
import com.huotu.fanmore.pinkcatraiders.model.AppWXLoginModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.BindOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.BottomModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginQQModel;
import com.huotu.fanmore.pinkcatraiders.model.LoginWXModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.UpdateProfileModel;
import com.huotu.fanmore.pinkcatraiders.model.UserUnwrapOutput;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.ModifyInfoActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.ui.login.AutnLogin;
import com.huotu.fanmore.pinkcatraiders.ui.login.ChangePasswordActivity;
import com.huotu.fanmore.pinkcatraiders.ui.login.MobileRegActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;
import com.huotu.fanmore.pinkcatraiders.widget.CommonPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.CropperView;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;
import com.huotu.fanmore.pinkcatraiders.widget.SetPasswordPopWindow;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 用户设置界面
 */
public class UserSettingActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, CropperView.OnCropperBackListener {

    private
    AutnLogin      login;
    public
    Resources resources;

    public BaseApplication application;

    public Handler mHandler;

    public
    WindowManager wManager;

    @Bind ( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;

    @Bind ( R.id.Userimg )
    CircleImageView userimg;

    @Bind ( R.id.userSettingPullRefresh )
    PullToRefreshScrollView userSettingPullRefresh;

    @Bind ( R.id.userPhone )
    TextView userPhone;

    @Bind(R.id.password)
    TextView password;

    @Bind ( R.id.userNickName )
    TextView userNickName;

    @Bind ( R.id.bindQq )
    TextView bindQq;

    @Bind ( R.id.bingWeixin )
    TextView bingWeixin;

    private String imgPath;

    private CropperView cropperView;

    private Bitmap cropBitmap;

    public
    ProgressPopupWindow progress;

    public
    NoticePopWindow noticePop;
    public Bundle bundle;
    public SetPasswordPopWindow setPasswordPop;


    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ButterKnife.bind(this);
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        bundle=new Bundle();
        progress = new ProgressPopupWindow ( UserSettingActivity.this, UserSettingActivity.this, wManager );
        setPasswordPop=new SetPasswordPopWindow(UserSettingActivity.this,UserSettingActivity.this,mHandler,application,null,wManager);
        initTitle();
        initSroll();
    }

    private
    void initSroll ( ) {

        initData();
        userSettingPullRefresh.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener<ScrollView>() {


                    @Override
                    public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {

                        initData();
                    }
                }
        );
    }

    public
    void initData ( ) {

        userSettingPullRefresh.onRefreshComplete ( );
        BitmapLoader.create ( ).loadRoundImage(
                this, userimg, application.readUerHead(), R.mipmap.defluat_logo
        );
        bindQq.setText(
                (1 != application.readQqBanded()) ? "未绑定" : "已绑定"
        );
        bindQq.setTextColor((1 != application.readQqBanded()) ?
                resources.getColor(R.color.text_black) : resources.getColor(R.color.title_bg));
        bingWeixin.setText(
                (1 != application.readWxBanded()) ? "未绑定" : "已绑定"
        );
        bingWeixin.setTextColor((1 != application.readWxBanded()) ?
                resources.getColor(R.color.text_black) : resources.getColor(R.color.title_bg));


        userNickName.setText(
                (
                        null != application.readNickName() && !TextUtils.isEmpty(
                                application.readNickName()
                        )
                ) ? application.readNickName() : ""
        );
        userPhone.setText(
                (1 == application.readMobileBanded() && !TextUtils.isEmpty(
                        application.readMobile())
                ) ? application.readMobile() : "未设置手机号码"
        );
        password.setText(
                (1 == application.readHaspassword() ? "修改密码" : "设置密码")
        );
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
        titleText.setText("个人资料");
    }

    @OnClick(R.id.bindQqL)
    void bingqq(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(
                UserSettingActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        final AlertDialog alertdialog = dialog.create();
        LayoutInflater inflater = LayoutInflater.from(UserSettingActivity.this);
        View view = inflater.inflate(R.layout.activity_dialog, null);
        alertdialog.setView(view, 0, 0, 0, 0);
        TextView titletext = (TextView) view.findViewById(R.id.titletext);
        TextView messagetext = (TextView) view.findViewById(R.id.messagetext);
        Button btn_lift = (Button) view.findViewById(R.id.btn_lift);
        Button btn_right = (Button) view.findViewById(R.id.btn_right);
        titletext.setTextColor(getResources().getColor(R.color.text_black));
        btn_lift.setTextColor(getResources().getColor(R.color.color_blue));
        btn_right.setTextColor(getResources().getColor(R.color.color_blue));
        if (1 == application.readQqBanded()) {

            titletext.setText("解除绑定");
            messagetext.setText("确定要解除帐号与QQ的关联吗?解除后无法用QQ登录此账号");
            btn_lift.setText("取消");
            btn_right.setText("解除绑定");


            btn_right.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 alertdialog.dismiss();
                                                 bundle.putInt("type",2);
                                                unwrap();

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
        else {
            titletext.setText("     奇兵夺宝想要打开QQ     ");
            messagetext.setVisibility(View.GONE);
            btn_lift.setText("打开");
            btn_right.setText("取消");
            btn_lift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog.dismiss();
                    progress.showProgress("正在授权");
                    progress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
                    ShareSDK.getPlatform(UserSettingActivity.this, QQ.NAME);
                    login = new AutnLogin(UserSettingActivity.this, mHandler, application);
                    login.authorize(new QQ(UserSettingActivity.this));
                    titleLayoutL.setClickable(false);
                }
            });
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog.dismiss();
                }
            });

            alertdialog.show();

        }

    }
    @OnClick(R.id.bingWeixinL)
    void bingwx() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(
                UserSettingActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        final AlertDialog alertdialog = dialog.create();
        LayoutInflater inflater = LayoutInflater.from(UserSettingActivity.this);
        View view = inflater.inflate(R.layout.activity_dialog, null);
        alertdialog.setView(view, 0, 0, 0, 0);
        TextView titletext = (TextView) view.findViewById(R.id.titletext);
        TextView messagetext = (TextView) view.findViewById(R.id.messagetext);
        Button btn_lift = (Button) view.findViewById(R.id.btn_lift);
        Button btn_right = (Button) view.findViewById(R.id.btn_right);
        titletext.setTextColor(getResources().getColor(R.color.text_black));
        btn_lift.setTextColor(getResources().getColor(R.color.color_blue));
        btn_right.setTextColor(getResources().getColor(R.color.color_blue));
        if (1 == application.readWxBanded()) {
            titletext.setText("解除绑定");
            messagetext.setText("确定要解除帐号与微信的关联吗?解除后无法用微信登录此账号");
            btn_lift.setText("取消");
            btn_right.setText("解除绑定");


        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialog.dismiss();
                bundle.putInt("type",3);
                unwrap();
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
       else {
            titletext.setText("     奇兵夺宝想要打开微信     ");
            messagetext.setVisibility(View.GONE);
            btn_lift.setText("打开");
            btn_right.setText("取消");
            btn_lift.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog.dismiss();
                    progress.showProgress("正在授权");
                    progress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
                    ShareSDK.getPlatform(UserSettingActivity.this, Wechat.NAME);
                    login = new AutnLogin(UserSettingActivity.this, mHandler, application);
                    login.authorize(new Wechat(UserSettingActivity.this));
                    titleLayoutL.setClickable(false);
                }
            });
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertdialog.dismiss();
                }
            });

            alertdialog.show();

        }
    }
    @OnClick ( R.id.titleLeftImage )
    void doBack ( ) {

        closeSelf(UserSettingActivity.this);
    }
    @OnClick(R.id.userAddressL)
    void toaddress()
    {
        ActivityUtils.getInstance().showActivity(this, AddressListActivity.class);
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
        commonPopWin.showAtLocation(titleLeftImage, Gravity.BOTTOM, 0, 0);
        commonPopWin.setOnDismissListener(new PoponDismissListener(UserSettingActivity.this));
    }
    @OnClick(R.id.userPhoneL)
    void modifyuserPhone()
    {
        //设置或者修改手机号码
        Bundle bundle = new Bundle ();
        bundle.putInt("moblieband", application.readMobileBanded());
        if (1==application.readMobileBanded()){
            String phone = application.readMobile();
            int type=3;//修改手机号码
            bundle.putString("content", phone);
            bundle.putInt("type", type);
        }else {
            int type=4;//设置手机号码
            bundle.putInt("type", type);
        }
        ActivityUtils
                .getInstance().showActivity(UserSettingActivity.this, MobileRegActivity.class, bundle);
    }
    @OnClick(R.id.userNickNameL)
    void modifyNickName()
    {
        //设置昵称
        Bundle bundle = new Bundle ();
        bundle.putString("profile", "昵称");
        bundle.putString("content", userNickName.getText().toString());
        ActivityUtils
                .getInstance().showActivity(UserSettingActivity.this, ModifyInfoActivity.class, bundle);
    }
    @OnClick(R.id.passwordL)
    void password(){
        if (0==application.readHaspassword()){
            if (1==application.readMobileBanded()) {
                Bundle bundle = new Bundle();
                bundle.putString("profile", "密码");
                ActivityUtils.getInstance().showActivity(UserSettingActivity.this, ModifyInfoActivity.class, bundle);
            }else {
                noticePop = new NoticePopWindow(UserSettingActivity.this, UserSettingActivity.this, wManager, "请先绑定手机，才能设置密码。");
                noticePop.showNotice();
                noticePop.showAtLocation(titleLayoutL,
                        Gravity.CENTER, 0, 0
                );
            }
        }else {
            setPasswordPop.showProgress("通过旧密码方式", "通过手机验证码");
            setPasswordPop.showAtLocation(titleLayoutL, Gravity.BOTTOM, 0, 0);
        }
    }
    @Override
    public boolean handleMessage(Message msg) {
        switch ( msg.what )
        {
            case Contant.UPLOAD_IMAGE:
            {
                int i = msg.arg1;
                if(1==i)
                {
                    //拍照
                    getPhotoByCamera();
                }
                else if(2==i)
                {
                    //从SD卡获取
                    getPhotoByFile();
                }
            }
            break;
            //授权登录
            case Contant.MSG_AUTH_COMPLETE:
            {
                //提示授权成功
                Platform plat = ( Platform ) msg.obj;
                login.authorize ( plat );
            }
            break;
            //授权登录
            case Contant.LOGIN_AUTH_ERROR:
            {
                progress.dismissView();
                ToastUtils.showMomentToast(UserSettingActivity.this, this, "授权失败");
            }
            break;
            case Contant.MSG_AUTH_ERROR:
            {
                progress.dismissView();
                Throwable throwable = ( Throwable ) msg.obj;
                if("cn.sharesdk.wechat.utils.WechatClientNotExistException".equals ( throwable.toString () ))
                {
                    //手机没有安装微信客户端
                    ToastUtils.showMomentToast(UserSettingActivity.this, this, "手机没有安装微信客户端");

                }
                else
                {
//                    //提示授权失败
                    ToastUtils.showMomentToast(UserSettingActivity.this, this, "授权操作遇到错误");

                }

            }
            break;
            case Contant.MSG_AUTH_CANCEL:
            {
                titleLayoutL.setClickable(true);
                //提示取消授权
                progress.dismissView();
                ToastUtils.showMomentToast(UserSettingActivity.this, this, "授权操作已取消");
            }
            break;
            case Contant.MSG_USERID_FOUND:
            {
                progress.dismissView();
            }
            break;
            case Contant.MSG_LOGIN: {
                progress.dismissView();
                if (msg.arg1 ==1) {
                    progress.showProgress("正在绑定QQ");
                    progress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
                    LoginQQModel qqModel = (LoginQQModel) msg.obj;
                    String url = Contant.REQUEST_URL + Contant.BINDQQ;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), UserSettingActivity.this);
                    Map<String, Object> maps = new HashMap<String, Object>();
                    maps.put("unionId", qqModel.getOpenid());
                    String suffix = params.obtainGetParam(maps);
                    url = url + suffix;
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress.dismissView();
                            if (UserSettingActivity.this.isFinishing()) {
                                return;
                            }
                            JSONUtil<BindOutputModel> jsonUtil = new JSONUtil<BindOutputModel>();
                            BindOutputModel bindoutput = new BindOutputModel();
                            bindoutput = jsonUtil.toBean(response.toString(), bindoutput);
                            if (null != bindoutput && (1 == bindoutput.getResultCode())) {
                                AppUserModel user = bindoutput.getResultData().getData();
                                if(null != user) {
                                    BaseApplication.getInstance().writeUserInfo(user);
                                    initData();
                                    ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "绑定成功");
                                } else
                                {
                                    ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "未请求到数据");
                                }
                            }else if (52011==bindoutput.getResultCode()){
                                ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "该QQ号已经被绑定");
                            }
                            else if (52014==bindoutput.getResultCode()){
                                ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "错误的认证账号");
                            }
                            else {
                                //异常处理，自动切换成无数据
                                progress.dismissView();
                                noticePop = new NoticePopWindow(UserSettingActivity.this, UserSettingActivity.this, wManager, "绑定失败");
                                noticePop.showNotice();
                                noticePop.showAtLocation(titleLayoutL,
                                        Gravity.CENTER, 0, 0
                                );
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.dismissView();
                            //初始化失败
                            //异常处理，自动切换成无数据
                            noticePop = new NoticePopWindow(UserSettingActivity.this, UserSettingActivity.this, wManager, "绑定失败");
                            noticePop.showNotice();
                            noticePop.showAtLocation(titleLayoutL,
                                    Gravity.CENTER, 0, 0
                            );
                        }
                    });

                }else if (msg.arg1 == 2){
                    progress.showProgress("正在绑定微信");
                    progress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
                    LoginWXModel loginWXModel = (LoginWXModel) msg.obj;
                    String url = Contant.REQUEST_URL + Contant.BINGWEIXIN;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), UserSettingActivity.this);
                    Map<String, Object> maps = new HashMap<String, Object>();
                    maps.put("unionId", loginWXModel.getUnionid());
                    String suffix = params.obtainGetParam(maps);
                    url = url + suffix;
                    HttpUtils httpUtils = new HttpUtils();
                    httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progress.dismissView();
                            if (UserSettingActivity.this.isFinishing()) {
                                return;
                            }
                            JSONUtil<BindOutputModel> jsonUtil = new JSONUtil<BindOutputModel>();
                            BindOutputModel bindoutput = new BindOutputModel();
                            bindoutput = jsonUtil.toBean(response.toString(), bindoutput);
                            if (null != bindoutput && (1 == bindoutput.getResultCode())) {
                                AppUserModel user = bindoutput.getResultData().getData();
                                if(null != user) {
                                    BaseApplication.getInstance().writeUserInfo(user);
                                    initData();
                                    ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "绑定成功");
                                } else
                                {
                                    ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "未请求到数据");
                                }
                            }
                            else if(52012==bindoutput.getResultCode())
                            {
                                ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "该微信号已经被绑定");
                            }
                            else {
                                //异常处理，自动切换成无数据
                                progress.dismissView();
                                noticePop = new NoticePopWindow(UserSettingActivity.this, UserSettingActivity.this, wManager, "绑定失败");
                                noticePop.showNotice();
                                noticePop.showAtLocation(titleLayoutL,
                                        Gravity.CENTER, 0, 0
                                );
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progress.dismissView();
                            //初始化失败
                            //异常处理，自动切换成无数据
                            noticePop = new NoticePopWindow(UserSettingActivity.this, UserSettingActivity.this, wManager, "绑定失败");
                            noticePop.showNotice();
                            noticePop.showAtLocation(titleLayoutL,
                                    Gravity.CENTER, 0, 0
                            );
                        }
                    });


                }
            }
            break;

            case Contant.MSG_USERID_NO_FOUND:
            {
                progress.dismissView();
                //提示授权成功
                ToastUtils.showMomentToast(UserSettingActivity.this, this, "获取用户信息失败");

            }
            break;
            case Contant.INIT_MENU_ERROR:
            {
                progress.dismissView();
                ToastUtils.showMomentToast(UserSettingActivity.this, this, "获取用户信息失败");

            }
            break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    public void getPhotoByCamera() {
        String sdStatus = Environment.getExternalStorageState ( );
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss",
                                                    Locale.CHINA);
        String imageName = "fm" + sdf.format(date) + ".jpg";
        imgPath = Environment.getExternalStorageDirectory() + "/" + imageName;
        File out = new File(imgPath);
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("fileName", imageName);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 0);
    }

    public void getPhotoByFile() {
        Intent intent2 = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent2, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        wManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        if (requestCode == 0) {// camera back
            Bitmap bitmap = BitmapUtils.readBitmapByPath ( imgPath );
            if (bitmap == null) {
                ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "未获取到图片!");
                return;
            }
            if (null == cropperView)
                cropperView = new CropperView(this, this);
            cropperView.cropper(bitmap);
        } else if (requestCode == 1) {// file back
            if (data != null) {
                Bitmap bitmap = null;
                Uri uri = data.getData();
                // url是content开头的格式
                if (uri.toString().startsWith("content://")) {
                    String path = null;
                    String[] pojo = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(uri, pojo, null,
                                                               null, null);
                    // managedQuery(uri, pojo, null, null, null);

                    if (cursor != null) {
                        // ContentResolver cr = this.getContentResolver();
                        int colunm_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        path = cursor.getString(colunm_index);

                        bitmap = BitmapUtils.readBitmapByPath(path);
                    }

                    if (bitmap == null) {
                        ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this,
                                "未获取到图片!");
                        return;
                    }
                } else if (uri.toString().startsWith("file:///")) {
                    String path = uri.toString().substring(8,
                                                           uri.toString().length());
                    bitmap = BitmapUtils.readBitmapByPath(path);
                    if (bitmap == null) {
                        ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this,
                                "未获取到图片!");
                        return;
                    }

                }
                if (null == cropperView)
                    cropperView = new CropperView(this, this);
                cropperView.cropper(bitmap);
            }

        }
    }

    @Override
    public
    void OnCropperBack ( Bitmap bitmap ) {
        if (null == bitmap)
            return;
        cropBitmap = bitmap;
        uploadShareImage(cropBitmap);
    }

    /**
     * 上传爱分享图片
     * @param bitmap
     */
    private void uploadShareImage(Bitmap bitmap)
    {
        progress.showProgress ( "正在上传头像" );
        progress.showAtLocation(
                titleLayoutL,
                Gravity.CENTER, 0, 0
        );
        if( false == UserSettingActivity.this.canConnect() ){
            ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "网络有问题");
            return;
        }
        else
        {
            Object profileData = null;
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, bao);
            byte[] buffer = bao.toByteArray();
            String imgStr = Base64.encodeToString (
                    buffer, 0, buffer.length,
                    Base64.DEFAULT
                                                  );
            profileData = imgStr;
            String url = Contant.REQUEST_URL + Contant.UPDATE_PROFILE;
            AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), UserSettingActivity.this);
            Map<String, Object> maps = new HashMap<String, Object> ();
            maps.put("profileType", "0");
            maps.put ( "profileData", profileData );
            Map<String, Object> param = params.obtainPostParam(maps);
            UpdateProfileModel updateProfile = new UpdateProfileModel ();
            HttpUtils<UpdateProfileModel> httpUtils = new HttpUtils<UpdateProfileModel> ();
            httpUtils.doVolleyPost (
                    updateProfile, url, param, new Response.Listener< UpdateProfileModel > ( ) {
                        @Override
                        public
                        void onResponse ( UpdateProfileModel response ) {
                            progress.dismissView ();
                            UpdateProfileModel updateProfile = response;
                            if(1==updateProfile.getResultCode ())
                            {
                                //上传成功
                                noticePop = new NoticePopWindow ( UserSettingActivity.this, UserSettingActivity.this, wManager, "用户头像上传成功");
                                noticePop.showNotice ( );
                                noticePop.showAtLocation (
                                        findViewById ( R.id.titleLayout ),
                                        Gravity.CENTER, 0, 0
                                                         );
                                //更新本地用户信息
                                application
                                        .writeUserInfo ( updateProfile.getResultData ().getUser () );
                                firstGetData ( );
                            }
                            else
                            {
                                //上传失败
                                noticePop = new NoticePopWindow ( UserSettingActivity.this, UserSettingActivity.this, wManager, "用户头像上传失败");
                                noticePop.showNotice ( );
                                noticePop.showAtLocation (
                                        findViewById ( R.id.titleLayout ),
                                        Gravity.CENTER, 0, 0
                                                         );
                            }
                        }
                    }, new Response.ErrorListener ( ) {

                        @Override
                        public
                        void onErrorResponse ( VolleyError error ) {
                            progress.dismissView ();
                            //系统级别错误
                            noticePop = new NoticePopWindow ( UserSettingActivity.this, UserSettingActivity.this, wManager, "服务器拒绝本次修改");
                            noticePop.showNotice ( );
                            noticePop.showAtLocation (
                                    findViewById ( R.id.titleLayout ),
                                    Gravity.CENTER, 0, 0
                                                     );
                        }
                    }
                                   );
        }
    }
    protected void unwrap(){
        progress.showProgress("正在提交数据");
        progress.showAtLocation(titleLayoutL, Gravity.CENTER, 0, 0);
        //登录接口
        String url = Contant.REQUEST_URL + Contant.UNWRAP;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), UserSettingActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("type", bundle.get("type"));
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress.dismissView();
                if (UserSettingActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<UserUnwrapOutput> jsonUtil = new JSONUtil<UserUnwrapOutput>();
                UserUnwrapOutput userUnwrapOutput = new UserUnwrapOutput();
                userUnwrapOutput = jsonUtil.toBean(response.toString(), userUnwrapOutput);
                if (null != userUnwrapOutput && null != userUnwrapOutput.getResultData() && (1 == userUnwrapOutput.getResultCode())) {
                    AppUserModel user = userUnwrapOutput.getResultData().getData();
                    if(null != user) {
                        BaseApplication.getInstance().writeUserInfo(user);
                        initData();
                        ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "解绑成功");
                    } else
                    {
                        ToastUtils.showMomentToast(UserSettingActivity.this, UserSettingActivity.this, "未请求到数据");
                    }

                }
                else if(52013 == userUnwrapOutput.getResultCode()) {
                    //未获取该用户信息
                    noticePop = new NoticePopWindow(UserSettingActivity.this, UserSettingActivity.this, wManager, "不能解绑最后一个账号");
                    noticePop.showNotice();
                    noticePop.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0
                    );
                }
                else {
                    //未获取该用户信息
                    noticePop = new NoticePopWindow(UserSettingActivity.this, UserSettingActivity.this, wManager, "解绑失败");
                    noticePop.showNotice();
                    noticePop.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0
                    );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismissView();
                //初始化失败
                //异常处理，自动切换成无数据
                noticePop = new NoticePopWindow(UserSettingActivity.this, UserSettingActivity.this, wManager, "服务器请求失败");
                noticePop.showNotice();
                noticePop.showAtLocation(titleLayoutL,
                        Gravity.CENTER, 0, 0
                );
            }
        });
    }

    protected void firstGetData(){
        mHandler.postDelayed (
                new Runnable ( ) {

                    @Override
                    public
                    void run ( ) {

                        if ( UserSettingActivity.this.isFinishing ( ) ) {
                            return;
                        }
                        userSettingPullRefresh.setRefreshing ( true );
                    }
                }, 1000
                             );
    }
}

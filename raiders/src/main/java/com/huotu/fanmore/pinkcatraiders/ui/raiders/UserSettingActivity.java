package com.huotu.fanmore.pinkcatraiders.ui.raiders;

import android.app.Activity;
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
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.BottomModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.UpdateProfileModel;
import com.huotu.fanmore.pinkcatraiders.ui.assistant.ModifyInfoActivity;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.ActivityUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapLoader;
import com.huotu.fanmore.pinkcatraiders.uitls.BitmapUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.widget.CircleImageView;
import com.huotu.fanmore.pinkcatraiders.widget.CommonPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.CropperView;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

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

public class UserSettingActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, CropperView.OnCropperBackListener {

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

    @Bind ( R.id.userNickName )
    TextView userNickName;

    @Bind ( R.id.userId )
    TextView userId;

    @Bind ( R.id.userAccount )
    TextView userAccount;

    private String imgPath;

    private CropperView cropperView;

    private Bitmap cropBitmap;

    public
    ProgressPopupWindow progress;

    public
    NoticePopWindow noticePop;


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
        progress = new ProgressPopupWindow ( UserSettingActivity.this, UserSettingActivity.this, wManager );
        initTitle ( );
        initSroll ( );
    }

    private
    void initSroll ( ) {

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

    private
    void initData ( ) {

        userSettingPullRefresh.onRefreshComplete ( );
        BitmapLoader.create ( ).loadRoundImage (
                this, userimg, application.readUerHead ( ), R.mipmap.error
                                               );
        userId.setText (
                ( null != application.readUerId ( ) ) ? String.valueOf (
                        application.readUerId (
                                              )
                                                                       ) : ""
                       );
        userAccount.setText (
                (
                        null != application.readAccount ( ) && ! TextUtils.isEmpty (
                                application.readAccount ( )
                                                                                   )
                ) ? application.readAccount (
                                            )
                  : ""
                            );
        userNickName.setText (
                (
                        null != application.readNickName ( ) && ! TextUtils.isEmpty (
                                application.readNickName ( )
                                                                                    )
                ) ? application.readNickName ( ) : ""
                             );
        userPhone.setText (
                (
                        null != application.readMobile ( ) && ! TextUtils.isEmpty (
                                application.readMobile ( )
                                                                                  )
                ) ? application.readMobile ( ) : "未设置手机号码"
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
    @OnClick(R.id.userNickNameL)
    void modifyNickName()
    {
        //设置手机号码
        Bundle bundle = new Bundle (  );
        bundle.putString ( "profile", "昵称" );
        bundle.putString ( "content", userNickName.getText ().toString () );
        ActivityUtils
                .getInstance ( ).showActivity ( UserSettingActivity.this, ModifyInfoActivity.class, bundle );
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
                ToastUtils.showLongToast ( UserSettingActivity.this, "未获取到图片!" );
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
                        ToastUtils.showLongToast(UserSettingActivity.this,
                                                 "未获取到图片!");
                        return;
                    }
                } else if (uri.toString().startsWith("file:///")) {
                    String path = uri.toString().substring(8,
                                                           uri.toString().length());
                    bitmap = BitmapUtils.readBitmapByPath(path);
                    if (bitmap == null) {
                        ToastUtils.showLongToast(UserSettingActivity.this,
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
        progress.showAtLocation (
                titleLayoutL,
                Gravity.CENTER, 0, 0
                                );
        if( false == UserSettingActivity.this.canConnect() ){
            ToastUtils.showShortToast(UserSettingActivity.this, "网络有问题");
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

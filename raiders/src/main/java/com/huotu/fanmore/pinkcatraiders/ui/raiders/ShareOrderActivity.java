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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.AppBalanceModel;
import com.huotu.fanmore.pinkcatraiders.model.AppUserBuyFlowModel;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.BottomModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.PartnerHistorysModel;
import com.huotu.fanmore.pinkcatraiders.model.PartnerHistorysOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.UpdateProfileModel;
import com.huotu.fanmore.pinkcatraiders.receiver.MyBroadcastReceiver;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
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

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 晒单界面
 */
public class ShareOrderActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, CropperView.OnCropperBackListener  {

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

    @Bind ( R.id.textright )
    TextView textright;

    @Bind ( R.id.orderTitle )
    EditText orderTitle;

    @Bind ( R.id.orderMsg )
    EditText orderMsg;

    @Bind ( R.id.addImgBtn1 )
    ImageView addImgBtn1;
    @Bind ( R.id.addImgBtn2 )
    ImageView addImgBtn2;
    @Bind ( R.id.addImgBtn3 )
    ImageView addImgBtn3;
    @Bind ( R.id.addImgBtn4 )
    ImageView addImgBtn4;

    @Bind(R.id.shareOrderTitle)
    TextView shareOrderTitle;
    @Bind(R.id.shareOrderIusse)
    TextView shareOrderIusse;
    @Bind(R.id.parentCount)
    TextView parentCount;
    @Bind(R.id.luckyNumber)
    TextView luckyNumber;
    @Bind(R.id.time)
    TextView time;

    public Bundle bundle;

    private String imgPath;

    private CropperView cropperView;

    public
    ProgressPopupWindow progress;

    public
    NoticePopWindow noticePop;

    @Bind(R.id.preImgL)
    LinearLayout preImgL;
    public List<String> imgs = new ArrayList<String>();
    public List<String> miniImgs = new ArrayList<String>();
    public  AppUserBuyFlowModel winner;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ri_share_order);
        ButterKnife.bind ( this );
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        wManager = this.getWindowManager();
        bundle = this.getIntent ( ).getExtras ( );
       // preImgL.removeAllViews();
        initTitle();
        initView();
    }

    private void initView()
    {
        winner = (AppUserBuyFlowModel) bundle.getSerializable("winner");
        shareOrderTitle.setText(winner.getTitle());
        shareOrderIusse.setText(winner.getIssueId());
        parentCount.setText(String.valueOf(winner.getAmount()));
        luckyNumber.setText(String.valueOf(winner.getLuckyNumber()));
        time.setText(winner.getAwardingDate());
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        progress = new ProgressPopupWindow ( ShareOrderActivity.this, ShareOrderActivity.this, wManager );
        stubTitleText.inflate();
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText("晒单");
    }

    @OnClick ( R.id.titleLeftImage )
    void doBack ( ) {

        closeSelf(ShareOrderActivity.this);
    }

    @OnClick ( R.id.addImgBtn1 )
    void uploadImg1 ( ) {
        addImgBtn1.setTag(0);
        addImgBtn2.setTag(null);
        addImgBtn3.setTag(null);
        addImgBtn4.setTag(null);

        //上传晒单图片
        Map< String, String > param = new HashMap< String, String > ( );
        //插入图片
        //上传封面
        List< BottomModel > bottoms = new ArrayList< BottomModel > ( );
        BottomModel bottom1 = new BottomModel ( );
        bottom1.setBottomTag ( "camera" );
        bottom1.setBottomName ( "拍照" );
        bottoms.add ( bottom1 );
        BottomModel bottom2 = new BottomModel ( );
        bottom2.setBottomTag ( "fromSD" );
        bottom2.setBottomName ( "从手机相册选择" );
        bottoms.add ( bottom2 );
        BottomModel bottom3 = new BottomModel ( );
        bottom3.setBottomTag ( "cancel" );
        bottom3.setBottomName ( "取消" );
        bottoms.add ( bottom3 );
        //回复点击事件
        CommonPopWin commonPopWin = new CommonPopWin ( ShareOrderActivity.this, bottoms,
                                                           application, wManager, mHandler,
                                                           titleLeftImage, param );
        commonPopWin.initView ( );
        commonPopWin.showAtLocation ( titleLeftImage, Gravity.BOTTOM, 0, 0 );
        commonPopWin.setOnDismissListener ( new PoponDismissListener ( ShareOrderActivity
                                                                                   .this ) );
    }

    @OnClick ( R.id.addImgBtn2 )
    void uploadImg2 ( ) {

        addImgBtn1.setTag(null);
        addImgBtn2.setTag(0);
        addImgBtn3.setTag(null);
        addImgBtn4.setTag(null);
        //上传晒单图片
        Map< String, String > param = new HashMap< String, String > ( );
        //插入图片
        //上传封面
        List< BottomModel > bottoms = new ArrayList< BottomModel > ( );
        BottomModel bottom1 = new BottomModel ( );
        bottom1.setBottomTag ( "camera" );
        bottom1.setBottomName ( "拍照" );
        bottoms.add ( bottom1 );
        BottomModel bottom2 = new BottomModel ( );
        bottom2.setBottomTag ( "fromSD" );
        bottom2.setBottomName ( "从手机相册选择" );
        bottoms.add ( bottom2 );
        BottomModel bottom3 = new BottomModel ( );
        bottom3.setBottomTag ( "cancel" );
        bottom3.setBottomName ( "取消" );
        bottoms.add ( bottom3 );
            //回复点击事件
        CommonPopWin commonPopWin = new CommonPopWin ( ShareOrderActivity.this, bottoms,
                    application, wManager, mHandler,
                    titleLeftImage, param );
        commonPopWin.initView ( );
        commonPopWin.showAtLocation ( titleLeftImage, Gravity.BOTTOM, 0, 0 );
        commonPopWin.setOnDismissListener ( new PoponDismissListener ( ShareOrderActivity
                    .this ) );
    }

    @OnClick ( R.id.addImgBtn3 )
    void uploadImg3 ( ) {

        addImgBtn1.setTag(null);
        addImgBtn2.setTag(null);
        addImgBtn3.setTag(0);
        addImgBtn4.setTag(null);
            //上传晒单图片
            Map< String, String > param = new HashMap< String, String > ( );
            //插入图片
            //上传封面
            List< BottomModel > bottoms = new ArrayList< BottomModel > ( );
            BottomModel bottom1 = new BottomModel ( );
            bottom1.setBottomTag("camera");
            bottom1.setBottomName("拍照");
            bottoms.add(bottom1);
            BottomModel bottom2 = new BottomModel ( );
            bottom2.setBottomTag ( "fromSD" );
            bottom2.setBottomName("从手机相册选择");
            bottoms.add ( bottom2 );
            BottomModel bottom3 = new BottomModel ( );
            bottom3.setBottomTag("cancel");
            bottom3.setBottomName("取消");
            bottoms.add(bottom3);
            //回复点击事件
            CommonPopWin commonPopWin = new CommonPopWin ( ShareOrderActivity.this, bottoms,
                    application, wManager, mHandler,
                    titleLeftImage, param );
            commonPopWin.initView ( );
            commonPopWin.showAtLocation ( titleLeftImage, Gravity.BOTTOM, 0, 0 );
            commonPopWin.setOnDismissListener(new PoponDismissListener(ShareOrderActivity
                    .this));
    }

    @OnClick ( R.id.addImgBtn4 )
    void uploadImg4 ( ) {

        addImgBtn1.setTag(null);
        addImgBtn2.setTag(null);
        addImgBtn3.setTag(null);
        addImgBtn4.setTag(0);

            //上传晒单图片
            Map< String, String > param = new HashMap< String, String > ( );
            //插入图片
            //上传封面
            List< BottomModel > bottoms = new ArrayList< BottomModel > ( );
            BottomModel bottom1 = new BottomModel ( );
            bottom1.setBottomTag("camera");
            bottom1.setBottomName("拍照");
            bottoms.add(bottom1);
            BottomModel bottom2 = new BottomModel ( );
            bottom2.setBottomTag("fromSD");
            bottom2.setBottomName("从手机相册选择");
            bottoms.add ( bottom2 );
            BottomModel bottom3 = new BottomModel ( );
            bottom3.setBottomTag("cancel");
            bottom3.setBottomName("取消");
            bottoms.add(bottom3);
            //回复点击事件
            CommonPopWin commonPopWin = new CommonPopWin ( ShareOrderActivity.this, bottoms,
                    application, wManager, mHandler,
                    titleLeftImage, param );
            commonPopWin.initView ( );
            commonPopWin.showAtLocation ( titleLeftImage, Gravity.BOTTOM, 0, 0 );
            commonPopWin.setOnDismissListener(new PoponDismissListener(ShareOrderActivity
                    .this));
    }

    @OnClick ( R.id.textright )
    void doPulish ( ) {

        if ( TextUtils.isEmpty ( orderTitle.getText ( ) ) ) {
            ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this, "请输入标题");
            return;
        }
        else if ( TextUtils.isEmpty ( orderMsg.getText ( ) ) ) {
            ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this, "请输入标题");
            return;
        }

        //弹出执行进度条
        progress.showProgress ( "正在添加晒单" );
        progress.showAtLocation (titleLayoutL,
                                 Gravity.CENTER, 0, 0
                                );

        String url = Contant.REQUEST_URL + Contant.ADD_SHARE_ORDER;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ShareOrderActivity.this);
        //1 拼装参数
        Map<String, Object> maps = new HashMap<String, Object> ();
        maps.put("issueId", String.valueOf(winner.getIssueId()));
        maps.put("title", orderTitle.getText().toString());
        maps.put("content", orderMsg.getText().toString());
        String profileData = creatProfileData(imgs);
        maps.put("filenames", profileData);
        String miniProfileData = creatProfileData(miniImgs);
        maps.put("miniFilenames", miniProfileData);
        maps = params.obtainAllParamUTF8 ( maps );
        //获取sign
        String signStr = params.obtainSignUTF8 ( maps );
        maps.put("title", URLEncoder.encode(orderTitle.getText().toString()));
        maps.put("content", URLEncoder.encode(orderMsg.getText().toString()));
        maps.put("sign", signStr);
        //拼装URL
        String suffix = params.obtainGetParamUTF8 (maps);

        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progress.dismissView();
                if (ShareOrderActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<BaseModel> jsonUtil = new JSONUtil<BaseModel>();
                BaseModel base = new BaseModel();
                base = jsonUtil.toBean(response.toString(), base);
                if (null != base && (1 == base.getResultCode())) {
                    ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this, "晒单成功，2秒后关闭晒单界面");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int type = bundle.getInt("type");
                            if (0 == type) {
                                //刷新中奖列表
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", 0);
                                MyBroadcastReceiver.sendBroadcast(ShareOrderActivity.this, MyBroadcastReceiver.SHOW_ORDER, bundle);

                            } else if (1 == type) {
                                //刷新中奖详情
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", 1);
                                MyBroadcastReceiver.sendBroadcast(ShareOrderActivity.this, MyBroadcastReceiver.SHOW_ORDER, bundle);
                            }
                            closeSelf(ShareOrderActivity.this);
                        }
                    }, 1500);

                } else {
                    //异常处理，自动切换成无数据
                    noticePop = new NoticePopWindow(ShareOrderActivity.this, ShareOrderActivity.this, wManager, "晒单添加失败");
                    noticePop.showNotice();
                    noticePop.showAtLocation(
                            findViewById(R.id.titleLayout),
                            Gravity.CENTER, 0, 0
                    );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //数据为空
                progress.dismissView();
                //上传失败
                noticePop = new NoticePopWindow(ShareOrderActivity.this, ShareOrderActivity.this, wManager, "服务器未响应");
                noticePop.showNotice();
                noticePop.showAtLocation(
                        findViewById(R.id.titleLayout),
                        Gravity.CENTER, 0, 0
                );
            }
        });
    }

    @Override
    public
    boolean onKeyDown ( int keyCode, KeyEvent event ) {

        if (keyCode == KeyEvent.KEYCODE_BACK
            && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(ShareOrderActivity.this);
        }
        return super.onKeyDown(keyCode, event);
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
            case 0x00110022:
            {
                Bitmap cropBitmap = (Bitmap) msg.obj;
                //上传晒单图片
                progress.showProgress ( "正在上传晒单图片" );
                progress.showAtLocation(
                        titleLayoutL,
                        Gravity.CENTER, 0, 0
                );
                if( false == ShareOrderActivity.this.canConnect() ){
                    ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this, "网络有问题");
                }
                else
                {
                    Object profileData = null;
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    cropBitmap.compress(Bitmap.CompressFormat.PNG, 90, bao);
                    byte[] buffer = bao.toByteArray();
                    String imgStr = Base64.encodeToString(
                            buffer, 0, buffer.length,
                            Base64.DEFAULT
                    );
                    profileData = imgStr;
                    String url = Contant.REQUEST_URL + Contant.ADD_SHARE_ORDER_IMG;
                    AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ShareOrderActivity.this);
                    Map<String, Object> maps = new HashMap<String, Object> ();
                    maps.put ( "profileData", profileData );
                    Map<String, Object> param = params.obtainPostParam(maps);
                    UploadOrderOutputModel updateProfile = new UploadOrderOutputModel ();
                    HttpUtils<UploadOrderOutputModel> httpUtils = new HttpUtils<UploadOrderOutputModel> ();
                    httpUtils.doVolleyPost (
                            updateProfile, url, param, new Response.Listener< UploadOrderOutputModel > ( ) {
                                @Override
                                public
                                void onResponse ( UploadOrderOutputModel response ) {
                                    progress.dismissView ();
                                    UploadOrderOutputModel updateProfile = response;
                                    if(1==updateProfile.getResultCode ()&&null!=updateProfile.getResultData()&&null!=updateProfile
                                            .getResultData().filename)
                                    {
                                        //上传成功
                                        ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this, "上传晒单图片成功");
                                        imgs.add(updateProfile.getResultData().getFilename());
                                        miniImgs.add(updateProfile.getResultData().getMiniFilename());
                                        if(0==Integer.parseInt(addImgBtn1.getTag().toString()))
                                        {
                                            addImgBtn1.setTag(null);
                                            BitmapLoader.create().displayUrl(ShareOrderActivity.this, addImgBtn1, updateProfile.getResultData().getMiniUrl(), R.mipmap.defluat_logo);
                                            addImgBtn1.setEnabled(false);
                                            addImgBtn1.setClickable(false);
                                        }
                                        else if(0==Integer.parseInt(addImgBtn2.getTag().toString()))
                                        {
                                            addImgBtn2.setTag(null);
                                            BitmapLoader.create().displayUrl(ShareOrderActivity.this, addImgBtn2, updateProfile.getResultData().getMiniUrl(), R.mipmap.defluat_logo);
                                            addImgBtn2.setEnabled(false);
                                            addImgBtn2.setClickable(false);
                                        }
                                        else if(0==Integer.parseInt(addImgBtn3.getTag().toString()))
                                        {
                                            addImgBtn3.setTag(null);
                                            BitmapLoader.create().displayUrl(ShareOrderActivity.this, addImgBtn3, updateProfile.getResultData().getMiniUrl(), R.mipmap.defluat_logo);
                                            addImgBtn3.setEnabled(false);
                                            addImgBtn3.setClickable(false);
                                        }
                                        else if(0==Integer.parseInt(addImgBtn4.getTag().toString()))
                                        {
                                            addImgBtn4.setTag(null);
                                            BitmapLoader.create().displayUrl(ShareOrderActivity.this, addImgBtn4, updateProfile.getResultData().getMiniUrl(), R.mipmap.defluat_logo);
                                            addImgBtn4.setEnabled(false);
                                            addImgBtn4.setClickable(false);
                                        }
                                    }
                                    else
                                    {
                                        ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this, "上传晒单图片失败");
                                    }
                                }
                            }, new Response.ErrorListener ( ) {

                                @Override
                                public
                                void onErrorResponse ( VolleyError error ) {
                                    progress.dismissView();
                                    //系统级别错误
                                    ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this, "服务器未响应");
                                }
                            }
                    );
                }
            }
            break;
            default:
                break;
        }
        return false;
    }

    public void getPhotoByCamera() {
        String sdStatus = Environment.getExternalStorageState ( );
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE);
        Date   date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss",
                                                    Locale.CHINA);
        String imageName = "fm" + sdf.format(date) + ".jpg";
        imgPath = Environment.getExternalStorageDirectory() + "/" + imageName;
        File out = new File(imgPath);
        Uri  uri = Uri.fromFile(out);
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
                ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this, "未获取到图片!");
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
                        ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this,
                                "未获取到图片!");
                        return;
                    }
                } else if (uri.toString().startsWith("file:///")) {
                    String path = uri.toString().substring(8,
                                                           uri.toString().length());
                    bitmap = BitmapUtils.readBitmapByPath(path);
                    if (bitmap == null) {
                        ToastUtils.showMomentToast(ShareOrderActivity.this, ShareOrderActivity.this,
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
    public void onClick(View v) {

    }

    @Override
    public
    void OnCropperBack ( Bitmap bitmap ) {
        if (null == bitmap)
            return;
        Bitmap cropBitmap = bitmap;
        //预览图片
        Message message = mHandler.obtainMessage();
        message.what = 0x00110022;
        message.obj = cropBitmap;
        mHandler.sendMessage(message);
    }

    public class UploadOrderOutputModel extends BaseModel
    {
        private UploadOrderInnerModel resultData;

        public UploadOrderInnerModel getResultData() {
            return resultData;
        }

        public void setResultData(UploadOrderInnerModel resultData) {
            this.resultData = resultData;
        }

        public class UploadOrderInnerModel
        {
            private String url;
            private String filename;
            private String miniUrl;
            private String miniFilename;

            public String getMiniUrl() {
                return miniUrl;
            }

            public void setMiniUrl(String miniUrl) {
                this.miniUrl = miniUrl;
            }

            public String getMiniFilename() {
                return miniFilename;
            }

            public void setMiniFilename(String miniFilename) {
                this.miniFilename = miniFilename;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }
        }
    }

    private String creatProfileData(List<String> imgLList)
    {
        StringBuilder builder = new StringBuilder();

        if(null==imgLList||imgLList.isEmpty())
        {
            return "";
        }
        else
        {
            for(int i=0; i<imgLList.size(); i++)
            {
                builder.append(imgLList.get(i)+",");
            }

            return builder.toString().substring(0, builder.toString().length()-1);
        }
    }
}

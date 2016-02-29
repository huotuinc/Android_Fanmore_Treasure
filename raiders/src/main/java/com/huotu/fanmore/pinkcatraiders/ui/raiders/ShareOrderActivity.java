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
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.listener.PoponDismissListener;
import com.huotu.fanmore.pinkcatraiders.model.BaseModel;
import com.huotu.fanmore.pinkcatraiders.model.BottomModel;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.PartnerHistorysModel;
import com.huotu.fanmore.pinkcatraiders.model.PartnerHistorysOutputModel;
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

import java.io.File;
import java.net.URLEncoder;
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

    @Bind ( R.id.addImgBtn )
    ImageView addImgBtn;

    public Bundle bundle;

    private String imgPath;

    private CropperView cropperView;

    private List< Bitmap > cropBitmaps = new ArrayList< Bitmap > ( );

    public
    ProgressPopupWindow progress;

    public
    NoticePopWindow noticePop;

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.ri_share_order );
        ButterKnife.bind ( this );
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        wManager = this.getWindowManager ( );
        bundle = this.getIntent ( ).getExtras ( );
        initTitle ( );
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        progress = new ProgressPopupWindow ( ShareOrderActivity.this, ShareOrderActivity.this, wManager );
        stubTitleText.inflate ( );
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText ( "晒单" );
    }

    @OnClick ( R.id.titleLeftImage )
    void doBack ( ) {

        closeSelf ( ShareOrderActivity.this );
    }

    @OnClick ( R.id.addImgBtn )
    void uploadImg ( ) {

        if ( 4 <= cropBitmaps.size ( ) ) {
            ToastUtils.showShortToast ( ShareOrderActivity.this, "最多选择4张图片" );
        }
        else {
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
    }

    @OnClick ( R.id.textright )
    void doPulish ( ) {

        if ( TextUtils.isEmpty ( orderTitle.getText ( ) ) ) {
            ToastUtils.showShortToast ( ShareOrderActivity.this, "请输入标题" );
            return;
        }
        else if ( TextUtils.isEmpty ( orderMsg.getText ( ) ) ) {
            ToastUtils.showShortToast ( ShareOrderActivity.this, "请输入标题" );
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
        maps.put ( "issuId", String.valueOf ( bundle.getLong ( "issue" ) ) );
        maps.put ( "title", orderTitle.getText ().toString ( ) );
        maps.put ( "content", orderMsg.getText ().toString () );
        maps.put ( "profileData", (Bitmap[])cropBitmaps.toArray(new Bitmap[cropBitmaps.size ()]) );
        maps = params.obtainAllParamUTF8 ( maps );
        //获取sign
        String signStr = params.obtainSignUTF8 ( maps );
        maps.put ( "title", URLEncoder.encode ( orderTitle.getText ().toString ( )) );
        maps.put ( "content", URLEncoder.encode ( orderMsg.getText ( ).toString ( )) );
        maps.put ( "sign", signStr);
        //拼装URL
        String suffix = params.obtainGetParamUTF8 (maps);

        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject >() {
                                  @Override
                                  public void onResponse(JSONObject response) {
                                      progress.dismissView ();
                                      if (ShareOrderActivity.this.isFinishing()) {
                                          return;
                                      }
                                      JSONUtil<BaseModel> jsonUtil = new JSONUtil<BaseModel>();
                                      BaseModel base = new BaseModel();
                                      base = jsonUtil.toBean(response.toString(), base);
                                      if (null != base && (1 == base.getResultCode())) {
                                          noticePop = new NoticePopWindow ( ShareOrderActivity.this, ShareOrderActivity.this, wManager, "晒单添加成功");
                                          noticePop.showNotice ( );
                                          noticePop.showAtLocation (
                                                  findViewById ( R.id.titleLayout ),
                                                  Gravity.CENTER, 0, 0
                                                                   );
                                      } else {
                                          //异常处理，自动切换成无数据
                                          noticePop = new NoticePopWindow ( ShareOrderActivity.this, ShareOrderActivity.this, wManager, "晒单添加失败");
                                          noticePop.showNotice ( );
                                          noticePop.showAtLocation (
                                                  findViewById ( R.id.titleLayout ),
                                                  Gravity.CENTER, 0, 0
                                                                   );
                                      }
                                  }
                              }, new Response.ErrorListener() {
                                  @Override
                                  public void onErrorResponse(VolleyError error) {
                                      //数据为空
                                      progress.dismissView ();
                                      //上传失败
                                      noticePop = new NoticePopWindow ( ShareOrderActivity.this, ShareOrderActivity.this, wManager, "晒单添加失败");
                                      noticePop.showNotice ( );
                                      noticePop.showAtLocation (
                                              findViewById ( R.id.titleLayout ),
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
                ToastUtils.showLongToast ( ShareOrderActivity.this, "未获取到图片!" );
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
                        ToastUtils.showLongToast(ShareOrderActivity.this,
                                                 "未获取到图片!");
                        return;
                    }
                } else if (uri.toString().startsWith("file:///")) {
                    String path = uri.toString().substring(8,
                                                           uri.toString().length());
                    bitmap = BitmapUtils.readBitmapByPath(path);
                    if (bitmap == null) {
                        ToastUtils.showLongToast(ShareOrderActivity.this,
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
        cropBitmaps.add ( cropBitmap );
    }
}

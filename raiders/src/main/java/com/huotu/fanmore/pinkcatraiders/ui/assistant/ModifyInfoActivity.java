package com.huotu.fanmore.pinkcatraiders.ui.assistant;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.UpdateProfileModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.NoticePopWindow;
import com.huotu.fanmore.pinkcatraiders.widget.ProgressPopupWindow;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改用户信息界面
 */
public
class ModifyInfoActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

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

    @Bind ( R.id.titleRightImage )
    ImageView titleRightImage;

    public Bundle bundle;

    @Bind ( R.id.modityTextInput )
    EditText modityTextInput;

    public
    ProgressPopupWindow progress;

    public
    NoticePopWindow noticePop;

    @Override
    public
    boolean handleMessage ( Message msg ) {

        return false;
    }

    @Override
    public
    void onClick ( View v ) {

    }

    @Override
    protected
    void onCreate ( Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.user_modify );
        ButterKnife.bind ( this );
        application = ( BaseApplication ) this.getApplication ( );
        resources = this.getResources ( );
        mHandler = new Handler ( this );
        bundle = this.getIntent ( ).getExtras ( );
        wManager = this.getWindowManager ( );
        progress = new ProgressPopupWindow ( ModifyInfoActivity.this, ModifyInfoActivity.this, wManager );
        initTitle ( );
        initData ( );
    }

    @OnClick ( R.id.titleLeftImage )
    void doback ( ) {

        closeSelf ( ModifyInfoActivity.this );
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.drawable.account_bg_bottom );
        SystemTools.loadBackground ( titleLayoutL, bgDraw );
        Drawable leftDraw = resources.getDrawable ( R.mipmap.back_gray );
        SystemTools.loadBackground ( titleLeftImage, leftDraw );
        Drawable rightDraw = resources.getDrawable ( R.mipmap.save_btn );
        SystemTools.loadBackground ( titleRightImage, rightDraw );
        stubTitleText.inflate ( );
        TextView titleText = ( TextView ) this.findViewById ( R.id.titleText );
        titleText.setText ( bundle.getString ( "profile" ) + "信息修改" );
    }

    private
    void initData ( ) {

        modityTextInput.setText ( bundle.getString ( "content" ) );
    }

    @OnClick(R.id.titleRightImage)
    void saveData()
    {
       if( TextUtils.isEmpty ( modityTextInput.getText () ))
       {
           ToastUtils.showLongToast ( ModifyInfoActivity.this, "请输入修改后的信息" );
           return;
       }
        else
       {
           if (bundle.get("moblieband")==1){
               //弹出执行进度条
               progress.showProgress ( "正在修改用户"+bundle.get("profile"));
               progress.showAtLocation(titleLayoutL,
                       Gravity.CENTER, 0, 0
               );
               String url = Contant.REQUEST_URL + Contant.UPDATE_PROFILE;
               AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ModifyInfoActivity.this);
               Map<String, Object> maps = new HashMap<String, Object> ();
               if (bundle.get("profile")=="昵称") {
                   maps.put("profileType", "1");
               }else if (bundle.get("profile")=="手机"){
                   maps.put("profileType", "2");
               }
               maps.put ( "profileData",  modityTextInput.getText ().toString ( ));
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
                                   noticePop = new NoticePopWindow ( ModifyInfoActivity.this, ModifyInfoActivity.this, wManager, "用户"+bundle.get("profile")+"修改成功");
                                   noticePop.showNotice ( );
                                   noticePop.showAtLocation (
                                           findViewById ( R.id.titleLayout ),
                                           Gravity.CENTER, 0, 0
                                   );
                                   //更新本地用户信息
                                   application
                                           .writeUserInfo ( updateProfile.getResultData ().getUser () );
                               }
                               else
                               {
                                   //上传失败
                                   noticePop = new NoticePopWindow ( ModifyInfoActivity.this, ModifyInfoActivity.this, wManager, "用户"+bundle.get("profile")+"修改失败");
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
                               noticePop = new NoticePopWindow ( ModifyInfoActivity.this, ModifyInfoActivity.this, wManager, "服务器拒绝本次修改");
                               noticePop.showNotice ( );
                               noticePop.showAtLocation (
                                       findViewById ( R.id.titleLayout ),
                                       Gravity.CENTER, 0, 0
                               );
                           }
                       }
               );
           }else {
               progress.showProgress ( "正在修改用户"+bundle.get("profile"));
               progress.showAtLocation(titleLayoutL,
                       Gravity.CENTER, 0, 0
               );
               String url = Contant.REQUEST_URL + Contant.UPDATE_PROFILE;
               AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ModifyInfoActivity.this);
               Map<String, Object> maps = new HashMap<String, Object> ();
               if (bundle.get("profile")=="昵称") {
                   maps.put("profileType", "1");
               }else if (bundle.get("profile")=="手机"){
                   maps.put("profileType", "2");
               }
               maps.put ( "profileData",  modityTextInput.getText ().toString ( ));
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
                                   noticePop = new NoticePopWindow ( ModifyInfoActivity.this, ModifyInfoActivity.this, wManager, "用户"+bundle.get("profile")+"修改成功");
                                   noticePop.showNotice ( );
                                   noticePop.showAtLocation (
                                           findViewById ( R.id.titleLayout ),
                                           Gravity.CENTER, 0, 0
                                   );
                                   //更新本地用户信息
                                   application
                                           .writeUserInfo ( updateProfile.getResultData ().getUser () );
                               }
                               else
                               {
                                   //上传失败
                                   noticePop = new NoticePopWindow ( ModifyInfoActivity.this, ModifyInfoActivity.this, wManager, "用户"+bundle.get("profile")+"修改失败");
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
                               noticePop = new NoticePopWindow ( ModifyInfoActivity.this, ModifyInfoActivity.this, wManager, "服务器拒绝本次修改");
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
    }

    @Override
    protected
    void onDestroy ( ) {

        super.onDestroy ( );
        VolleyUtil.cancelAllRequest ( );
        ButterKnife.unbind ( this );
    }

    @Override
    public
    boolean onKeyDown ( int keyCode, KeyEvent event ) {

        if (keyCode == KeyEvent.KEYCODE_BACK
            && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(ModifyInfoActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }
}

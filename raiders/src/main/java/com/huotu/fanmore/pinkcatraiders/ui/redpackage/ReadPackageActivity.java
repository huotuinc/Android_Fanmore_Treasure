package com.huotu.fanmore.pinkcatraiders.ui.redpackage;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.SoundUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.RadpackageWaitPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.RedpackageFailedPopWin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * 红包接口
 */
public class ReadPackageActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    public Handler mHandler;

    public WindowManager wManager;

    public
    AssetManager am;

    public Resources resources;

    public BaseApplication application;
    @Bind( R.id.titleLayoutL )
    RelativeLayout titleLayoutL;

    @Bind ( R.id.stubTitleText )
    ViewStub stubTitleText;

    @Bind ( R.id.titleLeftImage )
    ImageView titleLeftImage;
    public Bundle bundle;

    @Bind(R.id.prowL01)
    TextView prowL01;
    @Bind(R.id.prowL02)
    TextView prowL02;
    @Bind(R.id.prowL03)
    TextView prowL03;
    @Bind(R.id.prowL04)
    TextView prowL04;
    @Bind(R.id.prowL05)
    TextView prowL05;
    @Bind(R.id.prowL06)
    TextView prowL06;
    @Bind(R.id.wave1)
    ImageView wave1;
    @Bind(R.id.wave2)
    ImageView wave2;
    @Bind(R.id.wave3)
    ImageView wave3;
    @Bind(R.id.redBtn)
    TextView redBtn;
    private SoundUtil soundPool;

    private AnimationSet mAnimationSet1;
    private AnimationSet mAnimationSet2;
    private AnimationSet mAnimationSet3;

    private static final int OFFSET = 100;  //每个动画的播放时间间隔
    private static final int MSG_WAVE2_ANIMATION = 0x66660001;
    private static final int MSG_WAVE3_ANIMATION = 0x66660002;
    private static final int CLEAN_ANIMATION = 0x66660003;
    private static final int POWER_COUNT = 0x66660004;
    private static final int REDPACKAGE_WAIT = 0x66660005;
    public static final int REDPACKAGE_CLOSED = 0x66660006;
    public static final int REDPACKAGE_FAILED = 0x66660007;

    public int powerPro = 0;
    public HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    public RadpackageWaitPopWin redpackageWaitPopWin;
    public RedpackageFailedPopWin redpackageFailedPopWin;

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what)
        {
            case MSG_WAVE2_ANIMATION:
                wave2.startAnimation(mAnimationSet2);
                break;
            case MSG_WAVE3_ANIMATION:
                wave3.startAnimation(mAnimationSet3);
                break;
            case CLEAN_ANIMATION:
                clearWaveAnimation();
                break;
            case REDPACKAGE_WAIT:
            {
                redpackageWaitPopWin.showWin();
                redpackageWaitPopWin.showAtLocation(titleLayoutL,
                        Gravity.CENTER, 0, 0);
            }
            break;
            case REDPACKAGE_FAILED:
            {
                redpackageFailedPopWin.showWin();
                redpackageFailedPopWin.showAtLocation(titleLayoutL,
                        Gravity.CENTER, 0, 0);
            }
            break;
            case REDPACKAGE_CLOSED:
            {
                closeSelf(ReadPackageActivity.this);
            }
            break;
            case POWER_COUNT:
                int count = (int) msg.obj;
                if(0==count)
                {
                    //初始化
                    SystemTools.loadBackground(prowL01, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL02, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL03, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL04, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL05, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL06, resources.getDrawable(R.color.prow_bg));
                }
                else if(1==count)
                {
                    //初始化
                    SystemTools.loadBackground(prowL01, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL02, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL03, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL04, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL05, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL06, resources.getDrawable(R.color.prow_bg));
                }
                else if(2==count)
                {
                    //初始化
                    SystemTools.loadBackground(prowL01, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL02, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL03, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL04, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL05, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL06, resources.getDrawable(R.color.prow_bg));
                }
                else if(3==count)
                {
                    //初始化
                    SystemTools.loadBackground(prowL01, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL02, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL03, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL04, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL05, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL06, resources.getDrawable(R.color.prow_bg));
                }
                else if(4==count)
                {
                    //初始化
                    SystemTools.loadBackground(prowL01, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL02, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL03, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL04, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL05, resources.getDrawable(R.color.prow_bg));
                    SystemTools.loadBackground(prowL06, resources.getDrawable(R.color.prow_bg));
                }
                else if(5==count)
                {
                    //初始化
                    SystemTools.loadBackground(prowL01, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL02, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL03, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL04, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL05, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL06, resources.getDrawable(R.color.prow_bg));
                }
                else if(6<=count)
                {
                    //初始化
                    SystemTools.loadBackground(prowL01, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL02, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL03, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL04, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL05, resources.getDrawable(R.color.redpackage));
                    SystemTools.loadBackground(prowL06, resources.getDrawable(R.color.redpackage));
                    //初始化
                    powerPro = 0;
                    //发送红包接口
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redpackage_game);
        ButterKnife.bind(this);
        mHandler = new Handler ( this );
        //设置沉浸模式
        setImmerseLayout(this.findViewById(R.id.titleLayoutL));
        am = this.getAssets();
        resources = this.getResources();
        application = ( BaseApplication ) this.getApplication ( );
        wManager = this.getWindowManager();
        bundle = this.getIntent().getExtras();
        soundPool = new SoundUtil(ReadPackageActivity.this, R.raw.redclick);
        redpackageWaitPopWin= new RadpackageWaitPopWin(ReadPackageActivity.this, ReadPackageActivity.this, wManager, mHandler);
        redpackageFailedPopWin = new RedpackageFailedPopWin(ReadPackageActivity.this, ReadPackageActivity.this, wManager, mHandler);
        initTitle();
        mAnimationSet1 = initAnimationSet();
        mAnimationSet2 = initAnimationSet();
        mAnimationSet3 = initAnimationSet();
        initData();

    }

    private AnimationSet initAnimationSet()
    {
        AnimationSet as = new AnimationSet(true);
        ScaleAnimation sa = new ScaleAnimation(1f, 2.3f, 1f, 2.3f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(OFFSET * 3);
        sa.setRepeatCount(Animation.INFINITE);// 设置循环
        AlphaAnimation aa = new AlphaAnimation(1, 0.1f);
        aa.setDuration(OFFSET * 3);
        aa.setRepeatCount(Animation.INFINITE);//设置循环
        as.addAnimation(sa);
        as.addAnimation(aa);
        return as;
    }

    private void showWaveAnimation() {
        wave1.startAnimation(mAnimationSet1);
        mHandler.sendEmptyMessageDelayed(MSG_WAVE2_ANIMATION, OFFSET);
        mHandler.sendEmptyMessageDelayed(MSG_WAVE3_ANIMATION, OFFSET * 2);
    }

    private void clearWaveAnimation() {
        wave1.clearAnimation();
        wave2.clearAnimation();
        wave3.clearAnimation();
    }

    private void initData()
    {

        //判断抢红包是否开启
        if( false == ReadPackageActivity.this.canConnect ( ) ){
            return;
        }
        String url = Contant.REQUEST_URL + Contant.WHETHER_TO_START_DRAWING;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ReadPackageActivity.this);
        Map<String, Object> maps = new HashMap<String, Object> ();
        mHandler.sendEmptyMessage(REDPACKAGE_FAILED);
    }

    @OnClick(R.id.redBtn)
    void redBtn()
    {
        powerPro++;
        new Thread(new Runnable() {
            @Override
            public void run() {
                soundPool.shakeSound();
            }
        }).start();
        //动画
        showWaveAnimation();
        //改变能量槽
        Message message = mHandler.obtainMessage();
        message.what = POWER_COUNT;
        message.obj = powerPro;
        mHandler.sendMessage(message);
        mHandler.sendEmptyMessageDelayed(CLEAN_ANIMATION, OFFSET * 3);
    }

    @OnClick(R.id.titleLeftImage)
    void doBack() {
        closeSelf(ReadPackageActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
        if( soundPool !=null){
            soundPool.release();
        }
    }

    private
    void initTitle ( ) {
        //背景色
        Drawable bgDraw = resources.getDrawable ( R.color.redpackage );
        SystemTools.loadBackground(titleLayoutL, bgDraw);
        Drawable leftDraw = resources.getDrawable ( R.mipmap.title_back_white );
        SystemTools.loadBackground(titleLeftImage, leftDraw);
        stubTitleText.inflate();
        TextView titleText = (TextView) this.findViewById(R.id.titleText);
        titleText.setText("咻一咻");
        titleText.setTextColor(resources.getColor(R.color.color_white));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            //关闭
            this.closeSelf(ReadPackageActivity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

}

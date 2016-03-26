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
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.AppRedPactketsDistributeSourceModel;
import com.huotu.fanmore.pinkcatraiders.model.AppWinningInfoModel;
import com.huotu.fanmore.pinkcatraiders.model.CheckRedpackageOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RedpackageXiuXiuOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.DateUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SoundUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;
import com.huotu.fanmore.pinkcatraiders.widget.RadpackageWaitPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.RedWarningPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.RedpackageFailedPopWin;
import com.huotu.fanmore.pinkcatraiders.widget.RedpackageSuccessPopWin;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    @Bind(R.id.layout02)
    RelativeLayout layout02;
    @Bind(R.id.doneTag)
    TextView doneTag;
    @Bind(R.id.surplus01)
    TextView surplus01;
    @Bind(R.id.surplus02)
    TextView surplus02;
    @Bind(R.id.surplus03)
    TextView surplus03;
    @Bind(R.id.surplus04)
    TextView surplus04;
    @Bind(R.id.surplus05)
    TextView surplus05;
    @Bind(R.id.surplus06)
    TextView surplus06;
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

    private static final int OFFSET = 50;  //每个动画的播放时间间隔
    private static final int MSG_WAVE2_ANIMATION = 0x66660001;
    private static final int MSG_WAVE3_ANIMATION = 0x66660002;
    private static final int CLEAN_ANIMATION = 0x66660003;
    private static final int POWER_COUNT = 0x66660004;
    private static final int REDPACKAGE_WAIT = 0x66660005;
    public static final int REDPACKAGE_CLOSED = 0x66660006;
    public static final int REDPACKAGE_RESULT = 0x66660007;
    public static final int REDPACKAGE_BEGIN = 0x66660008;
    public static final int REDPACKAGE_FLUSH = 0x66660009;

    public HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    public RadpackageWaitPopWin redpackageWaitPopWin;
    public RedpackageFailedPopWin redpackageFailedPopWin;
    public RedpackageSuccessPopWin redpackageSuccessPopWin;
    public RedWarningPopWin redWarningPopWin;

    public int powerCount=0;
    public int xiuxiuCount=0;
    public List<Long> localRadpackagePool = new ArrayList<Long>();

    @Override
    public boolean handleMessage(final Message msg) {

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
            case REDPACKAGE_FLUSH:
            {
                redpackageWaitPopWin.dismissView();
                initData();
                mHandler.sendEmptyMessageDelayed(REDPACKAGE_FLUSH, 30000);
            }
            break;
            case REDPACKAGE_WAIT:
            {
                DateUtils.setRedpackageCount(surplus01, surplus02, surplus03, surplus04, surplus05, surplus06, 0l);
                doneTag.setVisibility(View.GONE);
                int tag = msg.arg1;
                localRadpackagePool.clear();
                if(0==tag)
                {
                    //活动开始
                    AppRedPactketsDistributeSourceModel redPactketsDistributeSource = (AppRedPactketsDistributeSourceModel) msg.obj;
                    redpackageWaitPopWin.showWin(0, redPactketsDistributeSource.getStartTime());
                    redpackageWaitPopWin.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0);
                }
                else if(1==tag)
                {
                    //没有活动
                    redpackageWaitPopWin.showWin(1, null);
                    redpackageWaitPopWin.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0);
                }
                else if(2==tag)
                {
                    //活动获取错误
                    redWarningPopWin.showWin();
                    redWarningPopWin.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0);
                }
            }
            break;
            case REDPACKAGE_RESULT:
            {
                redBtn.setEnabled(true);
                int tag = msg.arg1;
                if(1==tag)
                {

                    redpackageFailedPopWin.showWin();
                    redpackageFailedPopWin.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0);

                }
                else if(0==tag)
                {
                    int count=0;
                    List<AppWinningInfoModel> redpackages = (List<AppWinningInfoModel>) msg.obj;
                    if(null!=redpackages&&!redpackages.isEmpty())
                    {
                        for(int i=0; i<redpackages.size();i++)
                        {
                            AppWinningInfoModel winner = redpackages.get(i);
                            if(localRadpackagePool.contains(winner.getRid()))
                            {
                                continue;
                            }
                            else
                            {
                                localRadpackagePool.add(winner.getRid());
                                count++;
                            }
                        }
                        if(count>0)
                        {
                            redpackageSuccessPopWin.showWin();
                            redpackageSuccessPopWin.showAtLocation(titleLayoutL,
                                    Gravity.CENTER, 0, 0);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    redpackageSuccessPopWin.dismissView();;
                                }
                            }, 2000);
                        }
                        else
                        {
                            Message message = mHandler.obtainMessage();
                            message.what = REDPACKAGE_RESULT;
                            message.arg1 = 1;
                            mHandler.sendMessage(message);
                        }


                    } else
                    {
                        Message message = mHandler.obtainMessage();
                        message.what = REDPACKAGE_RESULT;
                        message.arg1 = 1;
                        mHandler.sendMessage(message);
                    }
                }
                else if(2==tag)
                {
                    redpackageFailedPopWin.showWin();
                    redpackageFailedPopWin.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0);
                }
                else if(3==tag)
                {
                    redpackageFailedPopWin.showWin();
                    redpackageFailedPopWin.showAtLocation(titleLayoutL,
                            Gravity.CENTER, 0, 0);
                }

            }
            break;
            case REDPACKAGE_CLOSED:
            {
                closeSelf(ReadPackageActivity.this);
            }
            break;
            case REDPACKAGE_BEGIN:
            {
                doneTag.setVisibility(View.VISIBLE);
                localRadpackagePool.clear();
                redpackageFailedPopWin.dismissView();
                redpackageWaitPopWin.dismissView();
                redpackageSuccessPopWin.dismissView();
                XiuxiuMode redPactketsDistributeSource = (XiuxiuMode) msg.obj;
                doneTag.setText(DateUtils.getMin(redPactketsDistributeSource.getDataModel().getEndTime()));
                xiuxiuCount = Integer.parseInt(redPactketsDistributeSource.getCount());
                //设置红包数量
                DateUtils.setRedpackageCount(surplus01, surplus02, surplus03, surplus04, surplus05, surplus06, redPactketsDistributeSource.getDataModel().getAmount());
            }
            break;
            case POWER_COUNT:
            {
                powerCount = 0;
                //发送红包接口
                String url = Contant.REQUEST_URL + Contant.XIU_XIU_XIU;
                AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ReadPackageActivity.this);
                Map<String, Object> maps = new HashMap<String, Object> ();
                String suffix = params.obtainGetParam(maps);
                url = url + suffix;
                HttpUtils httpUtils = new HttpUtils();
                httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (ReadPackageActivity.this.isFinishing()) {
                            return;
                        }
                        JSONUtil<RedpackageXiuXiuOutputModel> jsonUtil = new JSONUtil<RedpackageXiuXiuOutputModel>();
                        RedpackageXiuXiuOutputModel redpackageXiuXiu = new RedpackageXiuXiuOutputModel();
                        redpackageXiuXiu = jsonUtil.toBean(response.toString(), redpackageXiuXiu);
                        if (null != redpackageXiuXiu && null != redpackageXiuXiu.getResultData() && (1 == redpackageXiuXiu.getResultCode())) {

                            if (0 == redpackageXiuXiu.getResultData().getFlag()) {
                                //有机会获得红包
                                Message message = mHandler.obtainMessage();
                                message.what = REDPACKAGE_RESULT;
                                message.arg1 = 0;
                                msg.obj = redpackageXiuXiu.getResultData().getList();
                                mHandler.sendMessageDelayed(message, 1000);

                            } else if (1 == redpackageXiuXiu.getResultData().getFlag()) {
                                //失败
                                Message message = mHandler.obtainMessage();
                                message.what = REDPACKAGE_RESULT;
                                message.arg1 = 1;
                                mHandler.sendMessageDelayed(message, 1000);

                            } else if (2 == redpackageXiuXiu.getResultData().getFlag()) {
                                //通道还没开启
                                Message message = mHandler.obtainMessage();
                                message.what = REDPACKAGE_RESULT;
                                message.arg1 = 2;
                                mHandler.sendMessageDelayed(message, 1000);
                            }
                        } else {
                            //异常处理，自动切换成无数据
                            //活动获取失败
                            Message message = mHandler.obtainMessage();
                            message.what = REDPACKAGE_RESULT;
                            message.arg1 = 3;
                            mHandler.sendMessageDelayed(message, 1000);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //异常处理，自动切换成无数据
                        //活动获取失败
                        Message message = mHandler.obtainMessage();
                        message.what = REDPACKAGE_RESULT;
                        message.arg1 = 3;
                        mHandler.sendMessageDelayed(message, 1000);
                    }
                });
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
        redpackageSuccessPopWin = new RedpackageSuccessPopWin(ReadPackageActivity.this, ReadPackageActivity.this, wManager, mHandler);
        redWarningPopWin = new RedWarningPopWin(ReadPackageActivity.this, mHandler, ReadPackageActivity.this, wManager);
        initTitle();
        mAnimationSet1 = initAnimationSet();
        mAnimationSet2 = initAnimationSet();
        mAnimationSet3 = initAnimationSet();
        initData();
        mHandler.sendEmptyMessageDelayed(REDPACKAGE_FLUSH, 30000);
    }

    private AnimationSet initAnimationSet()
    {
        AnimationSet as = new AnimationSet(true);
        ScaleAnimation sa = new ScaleAnimation(1f, 5f, 1f, 5f,
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
        xiuxiuCount = 0;
        //判断抢红包是否开启
        if( false == ReadPackageActivity.this.canConnect ( ) ){
            return;
        }
        String url = Contant.REQUEST_URL + Contant.WHETHER_TO_START_DRAWING;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), ReadPackageActivity.this);
        Map<String, Object> maps = new HashMap<String, Object> ();
        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (ReadPackageActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<CheckRedpackageOutputModel> jsonUtil = new JSONUtil<CheckRedpackageOutputModel>();
                CheckRedpackageOutputModel checkRedpackage = new CheckRedpackageOutputModel();
                checkRedpackage = jsonUtil.toBean(response.toString(), checkRedpackage);
                if (null != checkRedpackage && null != checkRedpackage.getResultData() && (1 == checkRedpackage.getResultCode())) {

                    if (0 == checkRedpackage.getResultData().getFlag()) {
                        //活动已经开始
                        XiuxiuMode model = new XiuxiuMode();
                        model.setDataModel(checkRedpackage.getResultData().getData());
                        model.setCount(checkRedpackage.getResultData().getCount());
                        Message message = mHandler.obtainMessage();
                        message.what = REDPACKAGE_BEGIN;
                        message.obj = model;
                        mHandler.sendMessage(message);
                    } else if (1 == checkRedpackage.getResultData().getFlag()) {
                        //活动为开始
                        Message message = mHandler.obtainMessage();
                        message.what = REDPACKAGE_WAIT;
                        message.obj = checkRedpackage.getResultData().getData();
                        message.arg1 = 0;
                        mHandler.sendMessage(message);

                    } else if (2 == checkRedpackage.getResultData().getFlag()) {
                        //没有活动
                        Message message = mHandler.obtainMessage();
                        message.what = REDPACKAGE_WAIT;
                        message.arg1 = 1;
                        mHandler.sendMessage(message);
                    }
                } else {
                    //异常处理，自动切换成无数据
                    //活动获取失败
                    Message message = mHandler.obtainMessage();
                    message.what = REDPACKAGE_WAIT;
                    message.arg1 = 2;
                    mHandler.sendMessage(message);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //异常处理，自动切换成无数据
                //活动获取失败
                Message message = mHandler.obtainMessage();
                message.what = REDPACKAGE_WAIT;
                message.arg1 = 2;
                mHandler.sendMessage(message);
            }
        });
    }

    @OnClick(R.id.redBtn)
    void redBtn()
    {
        powerCount++;
        soundPool.shakeSound();
        //动画
        showWaveAnimation();
        mHandler.sendEmptyMessageDelayed(CLEAN_ANIMATION, OFFSET * 3);
        if(xiuxiuCount == powerCount)
        {
            Message message = mHandler.obtainMessage();
            message.what = POWER_COUNT;
            mHandler.sendMessage(message);
        }

    }

    @OnClick(R.id.titleLeftImage)
    void doBack() {
        closeSelf(ReadPackageActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        redpackageWaitPopWin.dismissView();
        redpackageFailedPopWin.dismissView();
        redpackageSuccessPopWin.dismissView();
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
        if( soundPool !=null){
            soundPool.release();
        }
        if(null != mHandler)
        {
            mHandler.removeMessages(REDPACKAGE_FLUSH);
            mHandler.removeMessages(REDPACKAGE_RESULT);
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


    public class XiuxiuMode
    {
        private AppRedPactketsDistributeSourceModel dataModel;
        private String count;

        public AppRedPactketsDistributeSourceModel getDataModel() {
            return dataModel;
        }

        public void setDataModel(AppRedPactketsDistributeSourceModel dataModel) {
            this.dataModel = dataModel;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

}

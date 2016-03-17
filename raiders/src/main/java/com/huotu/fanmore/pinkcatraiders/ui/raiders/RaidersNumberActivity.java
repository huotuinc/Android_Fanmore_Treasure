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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.fanmore.pinkcatraiders.R;
import com.huotu.fanmore.pinkcatraiders.base.BaseApplication;
import com.huotu.fanmore.pinkcatraiders.conf.Contant;
import com.huotu.fanmore.pinkcatraiders.model.OperateTypeEnum;
import com.huotu.fanmore.pinkcatraiders.model.RaiderNumber;
import com.huotu.fanmore.pinkcatraiders.model.RaiderNumberOutputModel;
import com.huotu.fanmore.pinkcatraiders.model.RaidersOutputModel;
import com.huotu.fanmore.pinkcatraiders.ui.base.BaseActivity;
import com.huotu.fanmore.pinkcatraiders.uitls.AuthParamUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.HttpUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.JSONUtil;
import com.huotu.fanmore.pinkcatraiders.uitls.SystemTools;
import com.huotu.fanmore.pinkcatraiders.uitls.ToastUtils;
import com.huotu.fanmore.pinkcatraiders.uitls.VolleyUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 夺宝号码界面
 */
public class RaidersNumberActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

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

    @Bind(R.id.productName)
    TextView productName;
    @Bind(R.id.productIssue)
    TextView productIssue;
    @Bind(R.id.partnerCount)
    TextView partnerCount;
    @Bind(R.id.number)
    TextView number;

    public Bundle bundle;



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
        this.setContentView(R.layout.raiders_number);
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
        titleText.setText("夺宝号码");
    }

    private void initData()
    {
        if( false == RaidersNumberActivity.this.canConnect() ){
            return;
        }
        String url = Contant.REQUEST_URL + Contant.GET_MY_RAIDER_NUMBER;
        AuthParamUtils params = new AuthParamUtils(application, System.currentTimeMillis(), RaidersNumberActivity.this);
        Map<String, Object> maps = new HashMap<String, Object>();
        //全部
        maps.put("issuId", String.valueOf(bundle.getLong("issuId")));

        String suffix = params.obtainGetParam(maps);
        url = url + suffix;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.doVolleyGet(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (RaidersNumberActivity.this.isFinishing()) {
                    return;
                }
                JSONUtil<RaiderNumberOutputModel> jsonUtil = new JSONUtil<RaiderNumberOutputModel>();
                RaiderNumberOutputModel raiderNumberOutput = new RaiderNumberOutputModel();
                raiderNumberOutput = jsonUtil.toBean(response.toString(), raiderNumberOutput);
                if (null != raiderNumberOutput && null != raiderNumberOutput.getResultData() && (1 == raiderNumberOutput.getResultCode())) {
                    if (null != raiderNumberOutput.getResultData().getList()) {
                        RaiderNumber raiderNumber = raiderNumberOutput.getResultData().getList();
                        productName.setText(raiderNumber.getGoodsTitle());
                        productIssue.setText("期号："+raiderNumber.getIssueId());
                        partnerCount.setText("参与了"+raiderNumber.getAmount()+"人次，以下是所有的夺宝号码。");
                        List<Long> numbers = raiderNumber.getNumbers();
                        Iterator<Long> it = numbers.iterator();
                        while (it.hasNext())
                        {
                            number.setText(it.next()+"     ");
                        }
                    } else {
                        ToastUtils.showMomentToast(RaidersNumberActivity.this, RaidersNumberActivity.this, "数据加载失败，即将关闭界面");
                    }
                } else {
                    //异常处理，自动切换成无数据
                    ToastUtils.showMomentToast(RaidersNumberActivity.this, RaidersNumberActivity.this, "数据加载失败，即将关闭界面");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showMomentToast(RaidersNumberActivity.this, RaidersNumberActivity.this, "数据加载失败，即将关闭界面");
            }
        });
    }

    @OnClick(R.id.titleLeftImage)
    void doBack()
    {
        closeSelf(RaidersNumberActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyUtil.cancelAllRequest();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction ( ) == KeyEvent.ACTION_DOWN ) {
            //关闭
            this.closeSelf ( RaidersNumberActivity.this );
        }
        return super.onKeyDown ( keyCode, event );
    }
}

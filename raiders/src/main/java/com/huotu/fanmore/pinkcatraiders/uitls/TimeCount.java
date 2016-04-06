package com.huotu.fanmore.pinkcatraiders.uitls;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/3/4.
 */
public class TimeCount extends CountDownTimer {

    private TextView textView;
    public TimeCount(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔

        this.textView = textView;
    }
    @Override
    public void onFinish() {//计时完毕时触发
        textView.setText("");
    }
    @Override
    public void onTick(long millisUntilFinished){//计时过程显示
        textView.setText(DateUtils.transformDataformat10(millisUntilFinished));
    }

    public void Stop(){
        this.cancel();
    }
}

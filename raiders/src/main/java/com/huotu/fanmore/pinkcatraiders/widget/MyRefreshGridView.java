package com.huotu.fanmore.pinkcatraiders.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 最新揭晓自定义gridView
 */
public class MyRefreshGridView extends GridView {

    private boolean isMeasure = true;
    public MyRefreshGridView(Context context) {
        super(context);
    }

    public MyRefreshGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRefreshGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        isMeasure = true;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isMeasure = false;
        super.onLayout(changed, l, t, r, b);
    }

    public void setMeasure(boolean measure) {
        isMeasure = measure;
    }

    public boolean isMeasure() {
        return isMeasure;
    }
}

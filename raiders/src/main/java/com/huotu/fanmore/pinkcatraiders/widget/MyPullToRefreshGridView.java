package com.huotu.fanmore.pinkcatraiders.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;

/**
 * Created by fangkaijin on 16/4/7.
 */
public class MyPullToRefreshGridView extends PullToRefreshAdapterViewBase<MyRefreshGridView> {


    public MyPullToRefreshGridView(Context context) {
        super(context);
    }

    public MyPullToRefreshGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPullToRefreshGridView(Context context, Mode mode) {
        super(context, mode);
    }

    public MyPullToRefreshGridView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected final MyRefreshGridView createRefreshableView(Context context, AttributeSet attrs) {
        final MyRefreshGridView gv;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            gv = new InternalGridViewSDK9(context, attrs);
        } else {
            gv = new InternalGridView(context, attrs);
        }

        // Use Generated ID (from res/values/ids.xml)
        gv.setId(com.handmark.pulltorefresh.library.R.id.gridview);
        return gv;
    }

    class InternalGridView extends MyRefreshGridView implements EmptyViewMethodAccessor {

        public InternalGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView) {
            MyPullToRefreshGridView.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }

    @TargetApi(9)
    final class InternalGridViewSDK9 extends InternalGridView {

        public InternalGridViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(MyPullToRefreshGridView.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);

            return returnValue;
        }
    }
}

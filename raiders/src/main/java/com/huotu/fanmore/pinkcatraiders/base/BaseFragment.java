package com.huotu.fanmore.pinkcatraiders.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * 基本的fragment
 */
public abstract class BaseFragment extends Fragment {

    public abstract void onReshow();//再切换至要显示的frag,onReshow()不同于onResume()
    public abstract void onFragPasue();//暂时不可见
    public abstract void onClick(View view);
    @Override
    public void onResume() {
        super.onResume();
        onReshow();
    }
}

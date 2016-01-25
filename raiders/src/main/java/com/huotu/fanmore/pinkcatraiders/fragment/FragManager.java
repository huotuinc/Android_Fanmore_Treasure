package com.huotu.fanmore.pinkcatraiders.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.huotu.fanmore.pinkcatraiders.base.BaseFragment;

/**
 * fragment界面切换管理类
 */
public class FragManager {



    public enum FragType{
        HOME, NEWEST, LIST, PROFILE, POPULAR, NEWEST_PRODUCT, PROGRESS, TOTAL;
    }
    private int viewId;
    private FragmentManager fragManager;
    private FragType preFragType;
    private FragType curFragType;
    private static FragManager fragIns;

    public static FragManager getIns(FragmentActivity context, int viewId){
        if(null != fragIns)
            fragIns = null;
        return fragIns = new FragManager(context, viewId);
    }
    private FragManager(FragmentActivity context, int viewId){
        this.viewId = viewId;
        this.fragManager = context.getSupportFragmentManager();

    }
    public FragType getCurrentFragType(){
        return this.curFragType;
    }
    public BaseFragment getCurrentFrag(){
        return getFragmentByType(preFragType);
    }

    public void setCurrentFrag(FragType type){
        if(type == preFragType)
            return;
        curFragType = type;
        FragmentTransaction ft = fragManager.beginTransaction();
        String fragTag = makeFragmentName(viewId, type);
        BaseFragment frag =  (BaseFragment) fragManager.findFragmentByTag(fragTag);
        if(frag == null){
            switch (type) {
                case HOME:
                    frag = new HomeFragment();
                    break;
                case NEWEST:
                    frag = new NewestFragment();
                    break;
                case LIST:
                    frag = new ListFragment();
                    break;
                case PROFILE:
                    frag = new ProfileFragment();
                    break;
                case POPULAR:
                    frag = new PopularityFrag();
                    break;
                case NEWEST_PRODUCT:
                    frag = new NewestProductFrag();
                    break;
                case PROGRESS:
                    frag = new ProgressFrag();
                    break;
                case TOTAL:
                    frag = new TotalRequiredFrag();
                    break;
                default:
                    frag = new HomeFragment();
                    break;
            }

            ft.add(viewId, frag, fragTag);
        }else{
            frag.onReshow();
        }


        ft.show(frag);
        if(preFragType != null){
            BaseFragment preFrag = getFragmentByType(preFragType);
            preFrag.onPause();
            ft.hide(preFrag);
        }


        ft.commitAllowingStateLoss();
        preFragType = type;

    }
    public BaseFragment getFragmentByType(FragType type){
        return (BaseFragment) fragManager.findFragmentByTag(makeFragmentName(viewId, type));
    }

    private String makeFragmentName(int viewId, FragType type) {
        return "android:switcher:" + viewId + ":" + type;
    }


    public void setPreFragType(FragType type ){
        preFragType=type;
    }
}

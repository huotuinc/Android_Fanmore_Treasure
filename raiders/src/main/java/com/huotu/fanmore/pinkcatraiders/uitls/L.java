package com.huotu.fanmore.pinkcatraiders.uitls;

import android.util.Log;

public class L {
	public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "MoreFan";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, msg);
	}
	
	    
	    public static final void i(String tag, String msg)
	    {
	        if(true && (null != msg))
	        {
	            Log.i(tag, msg);
	        }
	    }
}

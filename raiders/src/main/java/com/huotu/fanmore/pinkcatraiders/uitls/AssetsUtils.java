package com.huotu.fanmore.pinkcatraiders.uitls;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import com.huotu.fanmore.pinkcatraiders.conf.Contant;

import java.io.IOException;
import java.io.InputStream;

/**
 * 读取assets文件工具类
 */
public class AssetsUtils {


    private Context context;

    public AssetsUtils(Context context)
    {
        this.context = context;
    }

    public String readAddress(String fileName)
    {
        InputStream is = null;
        try {
            Context resContext = context.createPackageContext(Contant.SYS_PACKAGE, Context.CONTEXT_IGNORE_SECURITY);
            AssetManager s =  resContext.getAssets();
            is = s.open(fileName);
            byte [] buffer = new byte[is.available()] ;
            is.read(buffer);
            String json = new String(buffer,"utf-8");
            return json;
        } catch (PackageManager.NameNotFoundException e) {
           return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}

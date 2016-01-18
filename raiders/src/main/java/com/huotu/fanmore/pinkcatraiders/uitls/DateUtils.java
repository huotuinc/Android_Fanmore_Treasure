package com.huotu.fanmore.pinkcatraiders.uitls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间处理工具类
 */
public class DateUtils {

    /**
     * 转换成 2015/12/12 10:00:00格式
     * @param timeStamp
     * @return
     */
    public static String transformDataformat1(String timeStamp)
    {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(new Date(Long.parseLong(timeStamp)));
    }

    /**
     * 转换成 2015-12-12 10:00:00格式
     * @param timeStamp
     * @return
     */
    public static String transformDataformat2(long timeStamp)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(timeStamp));
    }

    /**
     * 转换成 2015/12/12 10:00:00格式
     * @param date
     * @return
     */
    public static String transformDataformat3(Date date)
    {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 转换成 2015/12格式
     * @param timeStamp
     * @return
     */
    public static String transformDataformat4(String timeStamp)
    {
        DateFormat format = new SimpleDateFormat("yyyy/MM");
        return format.format(new Date(Long.parseLong(timeStamp)));
    }

    /**
     * 转换成 12/ss dd:dd格式
     * @param timeStamp
     * @return
     */
    public static String transformDataformat5(String timeStamp)
    {
        DateFormat format = new SimpleDateFormat("MM/dd HH:mm");
        return format.format(new Date(Long.parseLong(timeStamp)));
    }

    /**
     * 转换成 YYYY/MM/DD
     * @param timeStamp
     * @return
     */
    public static String transformDataformat6(long timeStamp)
    {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(timeStamp);
    }

    /**
     * 转换成 YYYY/MM/DD
     * @param timeStamp
     * @return
     */
    public static String transformDataformat7(String timeStamp)
    {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(new Date(Long.parseLong(timeStamp)));
    }

    public static Map<String, String> calculationTime(String startTime, String endTime)
    {
        Map<String, String> resultMap = new HashMap<String, String>();
        try {
            long diff = Long.parseLong(endTime) - Long.parseLong(startTime);
            //计算天
            long days = diff / (1000 * 60 * 60 * 24);
            if (days > 0) {
                resultMap.put("con1", String.valueOf(days));
                resultMap.put("unit1", "天");
                long result1 = diff % (1000 * 60 * 60 * 24);
                long hour = result1 / (1000 * 60 * 60);
                resultMap.put("con2", String.valueOf(hour));
                resultMap.put("unit2", "时");
                long result2 = result1 % (1000 * 60 * 60);
                long min = result2 / (1000 * 60);
                resultMap.put("con3", String.valueOf(min));
                resultMap.put("unit3", "分");
            } else {
                long hour = diff / (1000 * 60 * 60);
                resultMap.put("con1", String.valueOf(hour));
                resultMap.put("unit1", "时");
                long result1 = diff % (1000 * 60 * 60);
                long min = result1 / (1000 * 60);
                resultMap.put("con2", String.valueOf(min));
                resultMap.put("unit2", "分");
                long result2 = result1 % (1000 * 60);
                long second = result2 / (1000);
                resultMap.put("con3", String.valueOf(second));
                resultMap.put("unit3", "秒");
            }
        } catch (Exception e)
        {
            resultMap.put("con1", String.valueOf(10));
            resultMap.put("unit1", "时");
            resultMap.put("con2", String.valueOf(0));
            resultMap.put("unit2", "分");
            resultMap.put("con3", String.valueOf(0));
            resultMap.put("unit3", "秒");
        }

        return resultMap;
    }
}

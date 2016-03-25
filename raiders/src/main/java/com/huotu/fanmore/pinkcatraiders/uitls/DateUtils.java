package com.huotu.fanmore.pinkcatraiders.uitls;

import android.support.v4.app.NotificationCompatSideChannelService;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        if("".equals(timeStamp) || null == timeStamp)
        {
            timeStamp = String.valueOf(System.currentTimeMillis());
        }
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return format.format(new Date(Long.parseLong(timeStamp)));
    }

    public static String transformDataformat11(String timeStamp)
    {
        if("".equals(timeStamp) || null == timeStamp)
        {
            timeStamp = String.valueOf(System.currentTimeMillis());
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(Long.parseLong(timeStamp)));
    }

    /**
     * 转换成 2015/12/12 10:00:00格式
     * @param timeStamp
     * @return
     */
    public static String transformDataformat3(String timeStamp)
    {
        if("".equals(timeStamp) || null == timeStamp)
        {
            timeStamp = String.valueOf(System.currentTimeMillis());
        }
        DateFormat format = new SimpleDateFormat("MM-dd HH:mm");
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
     * 转换成 2015-12-12 10:00:00格式
     * @param timeStamp
     * @return
     */
    public static String transformDataformat6(String timeStamp)
    {
        if("".equals(timeStamp) || null == timeStamp)
        {
            timeStamp = String.valueOf(System.currentTimeMillis());
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(Long.parseLong(timeStamp)));
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
        if("".equals(timeStamp) || null == timeStamp)
        {
            timeStamp = String.valueOf(System.currentTimeMillis());
        }
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
        if("".equals(timeStamp) || null == timeStamp)
        {
            timeStamp = String.valueOf(System.currentTimeMillis());
        }
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

    public static String transformDataformat10(long timeStamp)
    {
        DateFormat format = new SimpleDateFormat("mm:ss:SSS");
        return format.format(timeStamp);
    }

    /**
     * 转换成 YYYY/MM/DD
     * @param timeStamp
     * @return
     */
    public static String transformDataformat7(String timeStamp)
    {
        if("".equals(timeStamp) || null == timeStamp)
        {
            timeStamp = String.valueOf(System.currentTimeMillis());
        }
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

    /**
     * 判断是否过期
     * @param endTime
     * @return
     */
    public static boolean isExpired(String endTime)
    {
        if("".equals(endTime) || null == endTime)
        {
            endTime = String.valueOf(System.currentTimeMillis());
        }
        return (System.currentTimeMillis () - Long.parseLong ( endTime ))>=0?true:false;
    }

    public static String formatDate(Long currentTime, String fromat)
    {
        DateFormat format = null;
        try
        {
            format = new SimpleDateFormat(fromat);
            Date date = new Date(currentTime);
            return format.format(date);
        } catch(Exception e)
        {
            //发现异常时，返回当前时间
            return format.format(new Date());
        }
    }

    public static String getMin(long sec)
    {
        if(sec<=0)
        {
            return "距离结束还有 00:00 分钟";
        }
        else
        {
            int min = (int) (sec/(int)60);
            if(min<60)
            {
                if(min<10)
                {
                    return "距离结束还有 00:0"+min+" 分钟";
                }
                else
                {
                    return "距离结束还有 00:"+min+" 分钟";
                }
            }
            else
            {
                return "距离结束还有很多时间";
            }
        }
    }

    public static String getMinHome(String sec)
    {
        if(null==sec || TextUtils.isEmpty(sec))
        {
            return "1分钟前";
        }
        else if(Long.parseLong(sec)<=0)
        {
            return "1分钟前";
        }
        else
        {
            int min = (int) (Long.parseLong(sec)/(int)60);
            if(min<=60)
            {
                return min+"分钟前";
            }
            else
            {
                int hour = (int) (Long.parseLong(sec)/((int)60*60));
                return hour+"小时前";
            }

        }
    }

    public static void setRedpackageCount(TextView v1, TextView v2, TextView v3, TextView v4, TextView v5, TextView v6, long count)
    {
        String str = String.valueOf(count);
        if(null==str || TextUtils.isEmpty(str))
        {
            v1.setText("0");
            v2.setText("0");
            v3.setText("0");
            v4.setText("0");
            v5.setText("0");
            v6.setText("0");
        }
        else
        {
            char[] chars = str.toCharArray();
            switch (chars.length)
            {
                case 0:
                {
                    v1.setText("0");
                    v2.setText("0");
                    v3.setText("0");
                    v4.setText("0");
                    v5.setText("0");
                    v6.setText("0");
                }
                break;
                case 1:
                {
                    v1.setText("0");
                    v2.setText("0");
                    v3.setText("0");
                    v4.setText("0");
                    v5.setText("0");
                    v6.setText(String.valueOf(chars[0]));
                }
                break;
                case 2:
                {
                    v1.setText("0");
                    v2.setText("0");
                    v3.setText("0");
                    v4.setText("0");
                    v5.setText(String.valueOf(chars[0]));
                    v6.setText(String.valueOf(chars[1]));
                }
                break;
                case 3:
                {
                    v1.setText("0");
                    v2.setText("0");
                    v3.setText("0");
                    v4.setText(String.valueOf(chars[0]));
                    v5.setText(String.valueOf(chars[1]));
                    v6.setText(String.valueOf(chars[2]));
                }
                break;
                case 4:
                {
                    v1.setText("0");
                    v2.setText("0");
                    v3.setText(String.valueOf(chars[0]));
                    v4.setText(String.valueOf(chars[1]));
                    v5.setText(String.valueOf(chars[2]));
                    v6.setText(String.valueOf(chars[3]));
                }
                break;
                case 5:
                {
                    v1.setText("0");
                    v2.setText(String.valueOf(chars[0]));
                    v3.setText(String.valueOf(chars[1]));
                    v4.setText(String.valueOf(chars[2]));
                    v5.setText(String.valueOf(chars[3]));
                    v6.setText(String.valueOf(chars[4]));
                }
                break;
                case 6:
                {
                    v1.setText(String.valueOf(chars[0]));
                    v2.setText(String.valueOf(chars[1]));
                    v3.setText(String.valueOf(chars[2]));
                    v4.setText(String.valueOf(chars[3]));
                    v5.setText(String.valueOf(chars[4]));
                    v6.setText(String.valueOf(chars[5]));
                }
                break;
                default:
                    break;
        }
        }
    }

    public static void getTime(TextView v1, TextView v2, Object o)
    {
        long time = (long) o;
        if(time<=60)
        {
            v1.setText("00");
            v2.setText("00");
        }
        else
        {
            int hour = (int) (time/(60*60));
            long sec = time%(60*60);
            int min = (int) (sec/60);

            v1.setText(String.valueOf(hour));
            v2.setText(String.valueOf(min));
        }
    }
}
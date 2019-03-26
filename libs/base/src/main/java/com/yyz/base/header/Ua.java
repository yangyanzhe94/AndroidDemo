package com.yyz.base.header;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Administrator on 2017/5/19.
 */

public class Ua {

    private Context mContext;
    private String channelId;
    private String channelName;
    public int H;
    public int W;
    private String networkTypeStr;

    private static Ua mInstance;

    private Ua(Context context) {
        mContext = context;
    }

    public static Ua getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Ua(context);
        }
        return mInstance;
    }


    public String getUaToString() {
        return "[" + getDeviceName() + ";" + getChannelId() + ";" + getNetworkType() + ";"
                +getOperators() + ";" + "Android;" + getSystemVersion() + ";"
                + getResolution() + ";" + getLocale() + ";" + getIMEI() + ";"
                + getIMSI() + "]";
    }

    /**
     * 设置制造厂商和设备型号
     *
     * @return
     */
    private String getDeviceName() {
        return android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
    }

    /**
     * 获取渠道信息
     *
     * @return
     */
    private String getChannelId() {
        if (channelId != null && channelId.length() > 0) {
            return channelId;
        }
        channelId = getChannelFile(1);
        if (channelId != null && channelId.length() > 0){
            return channelId;
        }
        return "10029";
    }

    /**
     * 获取网络类型
     * 2G、3G、4G、WIFI
     *
     * @return
     */
    private String getNetworkType() {
        if (mContext != null) {
            NetworkInfo networkInfo = ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    networkTypeStr = "WIFI";
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    String _strSubTypeName = networkInfo.getSubtypeName();
                    // TD-SCDMA   networkType is 17
                    int networkType = networkInfo.getSubtype();
                    switch (networkType) {
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                            networkTypeStr = "2G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                        case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                        case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                            networkTypeStr = "3G";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                            networkTypeStr = "4G";
                            break;
                        default:
                            // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                networkTypeStr = "3G";
                            } else {
                                networkTypeStr = _strSubTypeName;
                            }

                            break;
                    }

                }
            }
        }

        return networkTypeStr;
    }

    /**
     * 获取运营商
     * CMCC、CUCC、CTCC
     *
     * @return
     */
    private String getOperators() {
        if (mContext != null) {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSimOperatorName();
        }
        return "";
    }

    /**
     * 获取操作系统版本
     *
     * @return
     */
    private String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取屏幕分辨率
     * 1080*720
     *
     * @return
     */
    private String getResolution() {
        if (H != 0 && W != 0)
            return H + "*" + W;
        if (mContext != null && mContext instanceof AppCompatActivity) {
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            ((AppCompatActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
            W = mDisplayMetrics.widthPixels;
            H = mDisplayMetrics.heightPixels;
        }
        return H + "*" + W;
    }

    /**
     * 国际化编码
     *
     * @return
     */
    private String getLocale() {
        if (mContext != null) {
            Locale locale = mContext.getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            if (language.endsWith("zh"))
                return "zh_CN";
        }
        return "";
    }

    /**
     * 获取手机 IMEI
     *
     * @return
     */
    private String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager != null ? telephonyManager.getDeviceId() : "";
    }

    /**
     * 获取手机 IMSI
     *
     * @return
     */
    private String getIMSI() {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            return "";
        }
//        if (telephonyManager.getSubscriberId() == null) {
//            return "";
//        } else
            return telephonyManager.getSubscriberId();
    }


    /**
     * 读取美团渠道包方式的渠道信息
     *
     * @param channelNum
     * @return
     */
    private String getChannelFile(int channelNum) {
        ApplicationInfo appinfo = mContext.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith("META-INF/ddchannel")) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        if (split != null && split.length >= 3) {
            channelId = split[1];
            channelName = split[2];
            if (channelNum == 2) {
                return channelName;
            } else {
                return channelId;
            }
        } else {
            return "";
        }
    }
}

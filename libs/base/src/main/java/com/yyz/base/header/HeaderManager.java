package com.yyz.base.header;

import android.content.Context;

import com.yyz.base.app.AppManager;
import com.yyz.base.utils.AppUtils;
import com.yyz.base.utils.DeviceUtils;
import com.yyz.base.utils.SettingHelper;
import com.yyz.base.utils.TimeUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */

public class HeaderManager {
    private static HeaderManager instance;
    private Context mContext;
    private Ua ua;
    private Device device;
    private Map<String, String> headerMap;
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private HeaderManager(Context mContext) {
        this.mContext = mContext;
    }

    public static HeaderManager getInstance(Context mContext) {
        if (instance == null) {
            instance = new HeaderManager(mContext);
        }
        return instance;
    }

    /**
     * 请求头 ua 、 api-sign
     *
     * @param paramMap
     * @param url
     * @return
     */
    public synchronized Map<String, String> getHeaderMap(Map<String, Object> paramMap, String url) {
        initUa();
        initDevice();
        if (headerMap == null)
            headerMap = new HashMap<>();
        headerMap.put("User-Agent", ua.getUaToString());
        headerMap.put("api-sign", md5HashToUpperCase(getApiSignSort(url, paramMap) + getAssemblyUrl()));
        headerMap.put("Authorization", device.getAuthorization());
        headerMap.put("device-id", device.getDeviceId());
        headerMap.put("app-version", device.getAppVersion());
        headerMap.put("app-time", device.getAppTime() + TimeUtils.getGMT().replace("GMT", " "));

        return headerMap;
    }


    private void initUa() {
        if (ua == null)
            ua = Ua.getInstance(mContext);
    }

    private void initDevice() {
        if (device == null)
            device = new Device();
        device.setAppTime(TimeUtils.getCustomTimeString());
        device.setAppVersion(AppUtils.getAppVersionName(mContext));
        device.setDeviceId(DeviceUtils.getAndroidID(mContext));
        device.setAuthorization(SettingHelper.getString(mContext, SettingHelper.SettingField.ACCESS_TOKEN, ""));

    }

    /**
     * md5加密并转大写
     *
     * @param str
     * @return
     */
    private String md5HashToUpperCase(String str) {
        String hashValue = str;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            hashValue = bytes2HexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashValue.toUpperCase();
    }

    /**
     * byteArr转hexString
     *
     * @param bytes
     * @return
     */
    private String bytes2HexString(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }


    /**
     * api sign
     *
     * @param url
     * @param paramMap
     */
    private String getApiSignSort(String url, Map<String, Object> paramMap) {
        StringBuilder result = new StringBuilder();
        if (paramMap == null || paramMap.size() == 0) {
            return url;
        }
        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        for (String key : keyArray) {
            if (key.contains("[]")) {
                continue;
            }
            Object object = paramMap.get(key);
            if (object == null || object.equals("")) {
                continue;
            }
            if (object.getClass().isArray()) {
                //数组
                if (object instanceof int[]) {
                    int[] array = (int[]) object;
                    if (array.length > 0) {
                        result.append(key).append(array[0]);
                    }
                } else if (object instanceof String[]) {
                    String[] array = (String[]) object;
                    if (array.length > 0 && array[0] != null && array[0].length() > 0) {
                        result.append(key).append(array[0]);
                    }
                } else if (object instanceof long[]) {
                    long[] array = (long[]) object;
                    if (array.length > 0) {
                        result.append(key).append(array[0]);
                    }
                } else if (object instanceof float[]) {
                    float[] array = (float[]) object;
                    if (array.length > 0) {
                        result.append(key).append(array[0]);
                    }
                } else if (object instanceof double[]) {
                    double[] array = (double[]) object;
                    if (array.length > 0) {
                        result.append(key).append(array[0]);
                    }
                } else if (object instanceof char[]) {
                    char[] array = (char[]) object;
                    if (array.length > 0) {
                        result.append(key).append(array[0]);
                    }
                } else if (object instanceof boolean[]) {
                    boolean[] array = (boolean[]) object;
                    if (array.length > 0) {
                        result.append(key).append(array[0]);
                    }
                } else if (object instanceof short[]) {
                    short[] array = (short[]) object;
                    if (array.length > 0) {
                        result.append(key).append(array[0]);
                    }
                } else if (object instanceof byte[]) {
                    byte[] array = (byte[]) object;
                    if (array.length > 0) {
                        result.append(key).append(array[0]);
                    }
                } else {
                    try {
                        Object[] array = (Object[]) object;
                        result.append(key).append(array[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //不是数据
                result.append(key).append(object);
            }
        }
        return url + result.toString();
    }

    /**
     * 拼装 spi
     *
     * @return
     */
    private String getAssemblyUrl() {
        return device.getAppVersion() + device.getAppTime() + device.getDeviceId() + AppManager.getAppKey();
    }
}

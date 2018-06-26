package com.alguojian.maplibrary;

import android.util.Log;

import static com.alguojian.maplibrary.MapConstant.TTAG;

/**
 * 请求定位的错误码
 *
 * @author alguojian
 * @date 2018/6/23
 */
public class LocationErrorCode {

    public static void setErrorCode(int code) {
        switch (code) {
            case 61:
                Log.d(TTAG, "GPS定位结果，GPS定位成功");
                break;
            case 62:
                Log.d(TTAG, "无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位");
                break;
            case 63:
                Log.d(TTAG, "网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位");
                break;
            case 66:
                Log.d(TTAG, "离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果");
                break;
            case 68:
                Log.d(TTAG, "网络连接失败时，查找本地离线定位时对应的返回结果");
                break;
            case 161:
                Log.d(TTAG, "网络定位结果，网络定位成功");
                break;
            case 162:
                Log.d(TTAG, "请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件");
                break;
            case 167:
                Log.d(TTAG, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位");
                break;
            case 505:
                Log.d(TTAG, "AK不存在或者非法，请按照说明文档重新申请AK");
                break;
            default:
                Log.d(TTAG, "定位出错了");
                break;
        }
    }
}

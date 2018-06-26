package com.alguojian.maplibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;

import static com.alguojian.maplibrary.MapConstant.TTAG;

/**
 * 验证地图sdk的key是否正确的广播回调
 *
 * @author alguojian
 * @date 2018/6/23
 */
public class MapSdkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        Log.d(TTAG, "验证key的结果是" + action);

        if (action != null && action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {

            Log.d(TTAG, "key填写错误");

        }
    }
}

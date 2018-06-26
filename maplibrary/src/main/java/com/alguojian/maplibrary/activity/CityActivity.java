package com.alguojian.maplibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alguojian.maplibrary.R;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.Marker;

import static com.alguojian.maplibrary.MapApplication.TTAG;

public class CityActivity extends BaseMapActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, CityActivity.class);
        context.startActivity(starter);
    }

    /**
     * 用于初始化一些事件
     */
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_city;
    }

    /**
     * 覆盖物点击事件监听
     *
     * @param marker
     */
    @Override
    protected void onMarkerClick(Marker marker) {

    }

    /**
     * 地图状态改变结束
     *
     * @param mapStatus
     */
    @Override
    protected void onMapStatusChangeFinish(MapStatus mapStatus) {

        Log.d(TTAG, "地图改变了状态结束了");
    }

}

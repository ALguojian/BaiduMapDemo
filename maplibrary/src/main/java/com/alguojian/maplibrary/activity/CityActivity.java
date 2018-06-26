package com.alguojian.maplibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alguojian.maplibrary.R;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;

import static com.alguojian.maplibrary.MapConstant.TTAG;

public class CityActivity extends BaseMapActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, CityActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_city;
    }

    /**
     * 用于初始化一些事件
     */
    @Override
    protected void initData() {

        findViewById(R.id.satellite).setOnClickListener(v -> mMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE));
        findViewById(R.id.common).setOnClickListener(v -> mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL));
        findViewById(R.id.rout).setOnClickListener(v -> mMap.setTrafficEnabled(true));
        findViewById(R.id.location).setOnClickListener(v -> mLocationClient.restart());
        findViewById(R.id.dialog).setOnClickListener(v -> setButtomDialog());
        findViewById(R.id.large).setOnClickListener(v -> {
            zoom++;
            mMap.setMapStatus(MapStatusUpdateFactory.zoomTo(zoom));
            getMarkerBean();
        });
        findViewById(R.id.small).setOnClickListener(v -> {
            zoom--;
            mMap.setMapStatus(MapStatusUpdateFactory.zoomTo(zoom));
            getMarkerBean();
        });
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

    /**
     * 覆盖物点击事件监听
     *
     * @param marker
     */
    @Override
    protected void onMarkerClick(Marker marker) {

    }

}

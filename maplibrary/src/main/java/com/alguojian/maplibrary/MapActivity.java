package com.alguojian.maplibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap mMap;
    private boolean flag;

    public static void start(Context context) {
        Intent starter = new Intent(context, MapActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_map);
        mapView = findViewById(R.id.bmapView);
        mMap = mapView.getMap();

        // 隐藏logo
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }

        //地图上比例尺
        mapView.showScaleControl(false);
        // 隐藏缩放控件
        mapView.showZoomControls(false);

        //高精度的定位
        LocationClient locationClient = new LocationClient(this);
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClient.setLocOption(locationClientOption);

        findViewById(R.id.satellite).setOnClickListener(v -> mMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE));
        findViewById(R.id.common).setOnClickListener(v -> mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL));
        findViewById(R.id.rout).setOnClickListener(v -> mMap.setTrafficEnabled(true));

        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_round);

        // 开启定位图层
        mMap.setMyLocationEnabled(true);

        mMap.setMyLocationConfiguration(new MyLocationConfiguration(

                MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker, 0xAAFFFF88, 0xAA00FF00));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}

package com.alguojian.maplibrary;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;

import static com.alguojian.maplibrary.MapApplication.TTAG;

/**
 * 地图页面
 */
public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private BaiduMap mMap;
    private LocationClient mLocationClient;
    private MapSdkReceiver mReceiver;

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
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        //关闭指南针显示
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(false);

        //控制是否启用或禁用平移的功能，默认开启。如果启用，则用户可以平移地图。
        uiSettings.setScrollGesturesEnabled(true);

        //控制是否启用或禁用缩放手势，默认开启。如果启用，用户可以双指点击或缩放地图视图
        uiSettings.setZoomGesturesEnabled(true);

        //控制是否启用或禁用俯视（3D）功能，默认开启。如果启用，则用户可使用双指 向下或向上滑动到俯视图
        uiSettings.setOverlookingGesturesEnabled(false);

        //控制是否启用或禁用地图旋转功能，默认开启。如果启用，则用户可使用双指 旋转来旋转地图
        uiSettings.setRotateGesturesEnabled(false);

        //控制是否一并禁止所有手势，默认关闭。如果启用，所有手势都将被禁用。
//        uiSettings.setAllGesturesEnabled(true);

        //隐藏地图上比例尺
        mapView.showScaleControl(false);

        //设置最大以及最小的缩放级别
        //mMap.setMaxAndMinZoomLevel(float max, float min);
        //获取当前地图级别下比例尺所表示的距离大小
        //int mapLevel = mapView.getMapLevel();
//        mMap.setMapStatus(MapStatusUpdateFactory.zoomTo(zoom));
//
//        zoomTo（zoom）：直接设置指定的缩放级别
//        zoomIn():放大地图缩放级别
//        zoomOut()：缩小地图缩放级别

        // 隐藏缩放控件
        mapView.showZoomControls(false);


        //高精度的定位
        mLocationClient = new LocationClient(this);

        LocationClientOption locationClientOption = new LocationClientOption();

        //是否需要地址信息，默认为不需要，即参数为false
        locationClientOption.setIsNeedAddress(true);

        //可选，设置发起定位请求的间隔，int类型，单位ms
        locationClientOption.setScanSpan(1000);

        //可选，设置定位模式，默认高精度
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //可选，设置是否使用gps，默认false
        locationClientOption.setOpenGps(true);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        locationClientOption.setLocationNotify(true);

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.setLocOption(locationClientOption);

        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher_round);

        //设置定位之后的信息，图标，颜色等
        mMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING,
                true, mCurrentMarker,
                0x00FFFF88,
                0xff3090ff));
        // 开启定位图层
        mMap.setMyLocationEnabled(true);

        //打开室内图，默认为关闭状态
        mMap.setIndoorEnable(true);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        intentFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new MapSdkReceiver();
        registerReceiver(mReceiver, intentFilter);


        findViewById(R.id.satellite).setOnClickListener(v -> mMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE));
        findViewById(R.id.common).setOnClickListener(v -> mMap.setMapType(BaiduMap.MAP_TYPE_NORMAL));
        findViewById(R.id.rout).setOnClickListener(v -> mMap.setTrafficEnabled(true));
        findViewById(R.id.location).setOnClickListener(v -> mLocationClient.restart());

        setLocation();

        initListener();
    }

    /**
     * 设置定位信息
     */
    private void setLocation() {
        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {

                mLocationClient.stop();

                Log.d(TTAG, "获得地址是" + location.getCountry() + location.getProvince() + location.getCity() + location.getDistrict() + location.getStreet());

                MyLocationData builder = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();

                mMap.setMyLocationData(builder);

                // //获取纬度信息
                double latitude = location.getLatitude();
                //获取经度信息
                double longitude = location.getLongitude();
                //获取定位精度，默认值为0.0f
                float radius = location.getRadius();

                String coorType = location.getCoorType();
                //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

                //请求定位错误码
                int errorCode = location.getLocType();
                LocationErrorCode.setErrorCode(errorCode);

            }
        });
    }

    /**
     * 有关地图操作的一些监听
     */
    private void initListener() {

        //包含手势、设置地图状态或其他某种操作导致地图状态开始改变，地图状态变化中、地图状态改变结束等监听方法。
        mMap.setOnMapStatusChangeListener(
                new BaiduMap.OnMapStatusChangeListener() {

                    /**
                     * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
                     * @param mapStatus 地图状态改变开始时的地图状态
                     */
                    @Override
                    public void onMapStatusChangeStart(MapStatus mapStatus) {

                    }

                    /** 因某种操作导致地图状态开始改变。
                     * @param mapStatus 地图状态改变开始时的地图状态
                     * @param i，取值有：
                     * 1：用户手势触发导致的地图状态改变,比如双击、拖拽、滑动底图
                     * 2：SDK导致的地图状态改变, 比如点击缩放控件、指南针图标
                     * 3：开发者调用,导致的地图状态改变
                     */
                    @Override
                    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

                    }

                    /**
                     * 地图状态变化中
                     * @param mapStatus 当前地图状态
                     */
                    @Override
                    public void onMapStatusChange(MapStatus mapStatus) {

                    }

                    /**
                     * 地图状态改变结束
                     * @param mapStatus 地图状态改变结束后的地图状态
                     */
                    @Override
                    public void onMapStatusChangeFinish(MapStatus mapStatus) {

                    }
                }
        );

        //地图单击事件监听接口
        mMap.setOnMapClickListener(
                new BaiduMap.OnMapClickListener() {

                    /**
                     * 地图单击事件回调函数
                     * @param latLng 点击的地理坐标
                     */
                    @Override
                    public void onMapClick(LatLng latLng) {

                    }

                    /**
                     * 地图内 Poi 单击事件回调函数
                     * @param mapPoi 点击的 poi 信息
                     */
                    @Override
                    public boolean onMapPoiClick(MapPoi mapPoi) {
                        return false;
                    }
                });

        //地图加载完成回调接口
        mMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            /**
             * 地图加载完成回调函数
             */
            @Override
            public void onMapLoaded() {

                //支持利用setViewPadding方法 围绕地图边缘添加内边距。地图将继续充满整个屏幕，
                // 但地图logo、比例尺、指南针、缩放按钮等控件、地图手势以及覆盖物，将调整在地图边界内操作。
//                mMap.setViewPadding(int left,int top, int right, int bottom);

            }
        });

        //地图渲染完成回调接口
        mMap.setOnMapRenderCallbadk(
                new BaiduMap.OnMapRenderCallback() {
                    /**
                     * 地图渲染完成回调接口
                     */
                    @Override
                    public void onMapRenderFinished() {

                    }
                });

        //地图双击事件监听接口
        mMap.setOnMapDoubleClickListener(
                new BaiduMap.OnMapDoubleClickListener() {
                    /**
                     * 地图双击事件监听回调函数
                     * @param latLng 双击的地理坐标
                     */
                    @Override
                    public void onMapDoubleClick(LatLng latLng) {

                    }
                });

        //地图长按事件监听接口
        mMap.setOnMapLongClickListener(
                new BaiduMap.OnMapLongClickListener() {
                    /**
                     * 地图长按事件监听回调函数
                     * @param latLng 长按的地理坐标
                     */
                    @Override
                    public void onMapLongClick(LatLng latLng) {

                    }
                });

        //地图 Marker 覆盖物点击事件监听接口
        mMap.setOnMarkerClickListener(
                new BaiduMap.OnMarkerClickListener() {
                    /**
                     * 地图 Marker 覆盖物点击事件监听函数
                     * @param marker 被点击的 marker
                     */
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        return false;
                    }
                });

        //地图截屏回调接口
        new BaiduMap.SnapshotReadyCallback() {
            /**
             * 地图截屏回调接口
             * @param bitmap 截屏返回的 bitmap 数据
             */
            @Override
            public void onSnapshotReady(Bitmap bitmap) {

            }
        };

        //触摸地图回调接口
        mMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            /**
             * 当用户触摸地图时回调函数
             * @param motionEvent 触摸事件
             */
            @Override
            public void onTouch(MotionEvent motionEvent) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mMap.setMyLocationEnabled(false);
        mLocationClient.stop();
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

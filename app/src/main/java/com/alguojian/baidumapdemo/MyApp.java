package com.alguojian.baidumapdemo;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

/**
 * ${Descript}
 *
 * @author alguojian
 * @date 2018/6/26
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        //设置定位经纬度模式-采用国测局标准方式
        SDKInitializer.setCoordType(CoordType.GCJ02);
    }
}

package com.alguojian.maplibrary;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * ${Descript}
 *
 * @author alguojian
 * @date 2018/6/22
 */
public class MapApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}

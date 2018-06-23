package com.alguojian.maplibrary;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

/**
 * ${Descript}
 *
 * @author alguojian
 * @date 2018/6/22
 */
public class MapApplication extends Application {

    public static final String TTAG = "asdfghjkl";

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.GCJ02);
    }
}

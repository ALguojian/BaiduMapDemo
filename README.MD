### 调用百度地图sdk

#### 1. Add it in your root build.gradle at the end of repositories:
```

allprojects {
        repositories {
        ...
            maven { url 'https://jitpack.io' }
        }
    }
```
#### 2. Add the dependency

```
dependencies {
        implementation 'com.github.ALguojian:BaiduMapDemo:1.0.0'
    }
```

#### 3. 创建应用-申请key

获得debug版本的sha值

```
cd .android

keytool -list -v -keystore debug.keystore
```

获得正式版的sha

cd 到jks文件目录
```
keytool -list -v -keystore 文件名.jks
```
输入jks文件密码即可



#### 4. activity集成BaseMapActivity即可，地图的初始化等系列操作都在base里面做过

```
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

```

#### 5. 地图页面xml中地图应该如下，保持ID一致 `mapView`
```
 <com.baidu.mapapi.map.MapView
        android:id="@+id/mapView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clickable="true"
        android:focusable="true"
        tools:context=".BaseMapActivity" />
```

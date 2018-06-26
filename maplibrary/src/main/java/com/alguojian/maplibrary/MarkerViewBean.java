package com.alguojian.maplibrary;

/**
 * ${Descript}
 *
 * @author alguojian
 * @date 2018/6/26
 */
public class MarkerViewBean {

    private double latitude;
    private double longitude;
    private int num;

    public int getNum() {

        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public MarkerViewBean(double latitude, double longitude, int num) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.num = num;
    }

    public MarkerViewBean(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {

        return latitude;

    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {

        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

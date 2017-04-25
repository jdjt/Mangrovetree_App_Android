package com.jdjt.mangrove.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/24/024.
 */

public class MapInfo implements Serializable {

    private String hotelCode;
    private String floorNo;
    private String mapNo;
    private String posionX;
    private String positionY;
    private String postionZ;

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getMapNo() {
        return mapNo;
    }

    public void setMapNo(String mapNo) {
        this.mapNo = mapNo;
    }

    public String getPosionX() {
        return posionX;
    }

    public void setPosionX(String posionX) {
        this.posionX = posionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public String getPostionZ() {
        return postionZ;
    }

    public void setPostionZ(String postionZ) {
        this.postionZ = postionZ;
    }
}

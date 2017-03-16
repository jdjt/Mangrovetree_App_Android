package com.jdjt.mangrove.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * @author wmy
 * @Description:
 * @FileName:Stores
 * @Package com.jdjt.mangrove.entity
 * @Date 2017/3/15 17:06
 */

@DatabaseTable(
        tableName = "stores"
)
public class Stores implements Serializable {
    @DatabaseField(
            columnName = "id",
            generatedId = true
    )
    int id;
    @DatabaseField(
            columnName = "mid"
    )
    String mid;
    @DatabaseField(
            columnName = "fid",
            index = true
    )
    String fid;
    @DatabaseField(
            columnName = "gid"
    )
    int gid;
    @DatabaseField(
            columnName = "ftype"
    )
    String ftype;
    @DatabaseField(
            columnName = "name",
            index = true
    )
    String name;
    @DatabaseField(
            columnName = "ename"
    )
    String ename;
    @DatabaseField(
            columnName = "type",
            index = true
    )
    long type;
    @DatabaseField(
            columnName = "typename",
            index = true
    )
    String typename;
    @DatabaseField(
            columnName = "x",
            dataType = DataType.DOUBLE
    )
    double x;
    @DatabaseField(
            columnName = "y",
            dataType = DataType.DOUBLE
    )
    double y;
    @DatabaseField(
            columnName = "z",
            dataType = DataType.FLOAT
    )
    float z;
    @DatabaseField(
            columnName = "address"
    )
    String address;
    @DatabaseField(
            columnName = "subtypename"
    )
    String subtypename;
    @DatabaseField(
            columnName = "activitycode"
    )
    String activitycode;

//
//    String history;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubtypename() {
        return subtypename;
    }

    public void setSubtypename(String subtypename) {
        this.subtypename = subtypename;
    }

    public String getActivitycode() {
        return activitycode;
    }

    public void setActivitycode(String activitycode) {
        this.activitycode = activitycode;
    }
}

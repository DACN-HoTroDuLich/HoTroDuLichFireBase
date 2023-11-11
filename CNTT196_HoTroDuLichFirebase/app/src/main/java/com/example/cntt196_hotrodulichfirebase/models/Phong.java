package com.example.cntt196_hotrodulichfirebase.models;

import java.io.Serializable;

public class Phong implements Serializable {
    private long giaMax;
    private long giaMin;
    private long soGiuong;
    private String hinhAnh;

    public double getGiaMax() {
        return giaMax;
    }

    public void setGiaMax(long giaMax) {
        this.giaMax = giaMax;
    }

    public double getGiaMin() {
        return giaMin;
    }

    public void setGiaMin(long giaMin) {
        this.giaMin = giaMin;
    }

    public long getSoGiuong() {
        return soGiuong;
    }

    public void setSoGiuong(long soGiuong) {
        this.soGiuong = soGiuong;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Phong(){}
    public Phong(long giaMax, long giaMin, long soGiuong, String hinhAnh) {
        this.giaMax = giaMax;
        this.giaMin = giaMin;
        this.soGiuong = soGiuong;
        this.hinhAnh = hinhAnh;
    }
}

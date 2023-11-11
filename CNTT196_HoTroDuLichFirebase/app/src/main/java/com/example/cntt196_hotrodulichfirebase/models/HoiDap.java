package com.example.cntt196_hotrodulichfirebase.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HoiDap implements Serializable {
    private String maHoiDap;
    private String maNguoiHoi;
    private String tenNguoiHoi;
    private String noiDungHoiDap;
    private LocalDateTime ngayHoi;
    private ArrayList<TraLoi> traLois;

    public String getMaHoiDap() {
        return maHoiDap;
    }

    public String getMaNguoiHoi() {
        return maNguoiHoi;
    }

    public String getTenNguoiHoi() {
        return tenNguoiHoi;
    }

    public String getNoiDungHoiDap() {
        return noiDungHoiDap;
    }

    public LocalDateTime getNgayHoi() {
        return ngayHoi;
    }

    public ArrayList<TraLoi> getTraLois() {
        return traLois;
    }

    public void setMaHoiDap(String maHoiDap) {
        this.maHoiDap = maHoiDap;
    }

    public void setMaNguoiHoi(String maNguoiHoi) {
        this.maNguoiHoi = maNguoiHoi;
    }

    public void setTenNguoiHoi(String tenNguoiHoi) {
        this.tenNguoiHoi = tenNguoiHoi;
    }

    public void setNoiDungHoiDap(String noiDungHoiDap) {
        this.noiDungHoiDap = noiDungHoiDap;
    }

    public void setNgayHoi(LocalDateTime ngayHoi) {
        this.ngayHoi = ngayHoi;
    }

    public void setTraLois(ArrayList<TraLoi> traLois) {
        this.traLois = traLois;
    }

    public HoiDap()
    {
        this.ngayHoi=LocalDateTime.now();
    }
    public HoiDap(String maHoiDap, String maNguoiHoi, String tenNguoiHoi, String noiDungHoiDap, LocalDateTime ngayHoi) {
        this.maHoiDap = maHoiDap;
        this.maNguoiHoi = maNguoiHoi;
        this.tenNguoiHoi = tenNguoiHoi;
        this.noiDungHoiDap = noiDungHoiDap;
        this.ngayHoi = ngayHoi;
    }
}

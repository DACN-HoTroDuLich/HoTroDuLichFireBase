package com.example.cntt196_hotrodulichfirebase.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DanhGia implements Serializable {
    private String maNguoiDanhGia;
    private LocalDateTime ngayDang;
    private String noiDung;
    private float rate;
    private String tenNguoiDanhGia;

    public String getMaNguoiDanhGia() {
        return maNguoiDanhGia;
    }

    public void setMaNguoiDanhGia(String maNguoiDanhGia) {
        this.maNguoiDanhGia = maNguoiDanhGia;
    }

    public LocalDateTime getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(LocalDateTime ngayDang) {
        this.ngayDang = ngayDang;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getTenNguoiDanhGia() {
        return tenNguoiDanhGia;
    }

    public void setTenNguoiDanhGia(String tenNguoiDanhGia) {
        this.tenNguoiDanhGia = tenNguoiDanhGia;
    }

    public DanhGia(){}
    public DanhGia(String maNguoiDanhGia, LocalDateTime ngayDang, String noiDung, float rate, String tenNguoiDanhGia) {
        this.maNguoiDanhGia = maNguoiDanhGia;
        this.ngayDang = ngayDang;
        this.noiDung = noiDung;
        this.rate = rate;
        this.tenNguoiDanhGia = tenNguoiDanhGia;
    }
}

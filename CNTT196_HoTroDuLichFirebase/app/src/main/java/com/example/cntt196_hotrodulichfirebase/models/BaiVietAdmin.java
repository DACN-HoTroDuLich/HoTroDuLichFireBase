package com.example.cntt196_hotrodulichfirebase.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BaiVietAdmin implements Serializable {
    private String id_Document;
    private String img;
    private String tenNguoiDang;
    private LocalDateTime ngayGioDang;
    private String tieuDe;

    public BaiVietAdmin(){

    }

    public BaiVietAdmin(String id_Document, String img, String tenNguoiDang, LocalDateTime ngayGioDang, String tieuDe) {
        this.id_Document = id_Document;
        this.img = img;
        this.tenNguoiDang = tenNguoiDang;
        this.ngayGioDang = ngayGioDang;
        this.tieuDe = tieuDe;
    }

    public String getId_Document() {
        return id_Document;
    }

    public String getImg() {
        return img;
    }

    public String getTenNguoiDang() {
        return tenNguoiDang;
    }

    public LocalDateTime getNgayGioDang() {
        return ngayGioDang;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setId_Document(String id_Document) {
        this.id_Document = id_Document;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setTenNguoiDang(String tenNguoiDang) {
        this.tenNguoiDang = tenNguoiDang;
    }

    public void setNgayGioDang(LocalDateTime ngayGioDang) {
        this.ngayGioDang = ngayGioDang;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }
}

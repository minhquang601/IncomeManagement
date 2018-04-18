package com.example.quang.project.model;

/**
 * Created by quang on 11/6/2017.
 */

public class ModelKeHoach {
    String tenHangMuc,ghiChu,loai,ngay;
    byte[] hinhKehoach;

    public ModelKeHoach(String tenHangMuc, String ghiChu, String loai, String ngay, byte[] hinhKehoach) {
        this.tenHangMuc = tenHangMuc;
        this.ghiChu = ghiChu;
        this.loai = loai;
        this.ngay = ngay;
        this.hinhKehoach = hinhKehoach;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public ModelKeHoach() {
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public byte[] getHinhKehoach() {
        return hinhKehoach;
    }

    public void setHinhKehoach(byte[] hinhKehoach) {
        this.hinhKehoach = hinhKehoach;
    }
}

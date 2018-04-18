package com.example.quang.project.model;

/**
 * Created by quang on 11/7/2017.
 */

public class ModelThuNhap {
    String tenHangmuc,tenTaiKhoan,ghiChu,ngay;
    int tien;
    byte[] imgHinh;

    public ModelThuNhap(String tenHangmuc, String tenTaiKhoan, String ghiChu, String ngay, int tien, byte[] imgHinh) {
        this.tenHangmuc = tenHangmuc;
        this.tenTaiKhoan = tenTaiKhoan;
        this.ghiChu = ghiChu;
        this.ngay = ngay;
        this.tien = tien;
        this.imgHinh = imgHinh;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTenHangmuc() {
        return tenHangmuc;
    }

    public void setTenHangmuc(String tenHangmuc) {
        this.tenHangmuc = tenHangmuc;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public byte[] getImgHinh() {
        return imgHinh;
    }

    public void setImgHinh(byte[] imgHinh) {
        this.imgHinh = imgHinh;
    }
}

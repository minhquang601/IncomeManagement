package com.example.quang.project.model;

/**
 * Created by quang on 11/10/2017.
 */

public class ModelLichSu {
    String tenhangmuc,ngay,ghichu,tentaikhoan,loai;
    byte[] hinh;
    int sotien;

    public ModelLichSu(String tenhangmuc, String ngay, String ghichu, String tentaikhoan, String loai, byte[] hinh, int sotien) {
        this.tenhangmuc = tenhangmuc;
        this.ngay = ngay;
        this.ghichu = ghichu;
        this.tentaikhoan = tentaikhoan;
        this.loai = loai;
        this.hinh = hinh;
        this.sotien = sotien;
    }

    public String getTenhangmuc() {
        return tenhangmuc;
    }

    public void setTenhangmuc(String tenhangmuc) {
        this.tenhangmuc = tenhangmuc;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }
}

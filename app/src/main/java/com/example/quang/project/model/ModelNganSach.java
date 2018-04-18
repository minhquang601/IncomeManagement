package com.example.quang.project.model;

/**
 * Created by quang on 11/8/2017.
 */

public class ModelNganSach {
    String tenhangmuc,ngay,tentaikhoan;
    int sotien,tiensudung;
    byte[] imgHinh;

    public ModelNganSach(String tenhangmuc, String ngay, int sotien, byte[] imgHinh,String tentaikhoan,int tiensudung) {
        this.tenhangmuc = tenhangmuc;
        this.ngay = ngay;
        this.sotien = sotien;
        this.imgHinh = imgHinh;
        this.tentaikhoan = tentaikhoan;
        this.tiensudung= tiensudung;
    }

    public int getTiensudung() {
        return tiensudung;
    }

    public void setTiensudung(int tiensudung) {
        this.tiensudung = tiensudung;
    }

    public String getTentaikhoan() {
        return tentaikhoan;
    }

    public void setTentaikhoan(String tentaikhoan) {
        this.tentaikhoan = tentaikhoan;
    }

    public byte[] getImgHinh() {
        return imgHinh;
    }

    public void setImgHinh(byte[] imgHinh) {
        this.imgHinh = imgHinh;
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

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }
}

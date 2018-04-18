package com.example.quang.project.model;

/**
 * Created by quang on 11/6/2017.
 */

public class ModelSpinner {
    String tenHangMuc;
    byte[] hinh;

    public ModelSpinner(String tenHangMuc, byte[] hinh) {
        this.tenHangMuc = tenHangMuc;
        this.hinh = hinh;
    }

    public String getTenHangMuc() {
        return tenHangMuc;
    }

    public void setTenHangMuc(String tenHangMuc) {
        this.tenHangMuc = tenHangMuc;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public ModelSpinner() {

    }
}

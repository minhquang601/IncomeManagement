package com.example.quang.project.model;

/**
 * Created by quang on 11/6/2017.
 */

public class ModelTaiKhoan {
    String tenTaiKhoan;
    int sotien;

    public ModelTaiKhoan(String tenTaiKhoan, int sotien) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.sotien = sotien;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }
}

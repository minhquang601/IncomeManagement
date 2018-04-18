package com.example.quang.project.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quang.project.DatabaseSqliteProject;
import com.example.quang.project.R;
import com.example.quang.project.adapter.NgansachAdapter;
import com.example.quang.project.adapter.TaiKhoanAdapter;
import com.example.quang.project.model.ModelNganSach;
import com.example.quang.project.model.ModelTaiKhoan;

import java.util.ArrayList;


public class FragmentThongKe extends Fragment {


    private DatabaseSqliteProject mDbProject;
    public ArrayList<ModelTaiKhoan> mMArrTaikhoan;
    public TaiKhoanAdapter mMAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_thongke, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDbProject = new DatabaseSqliteProject(getActivity());

        soluocTong();

        soLuocTaiKhoan();

        soluocNganSach();

    }

    private void soluocTong(){
        TextView txtTongThu = getActivity().findViewById(R.id.txtTongThu);
        TextView txtTongChi = getActivity().findViewById(R.id.txtTongChi);
        TextView txtTongCong = getActivity().findViewById(R.id.txtTongCong);
        int color = getActivity().getResources().getColor(R.color.color_kehoachChi);
        int color1 = getActivity().getResources().getColor(R.color.color_kehoachThu);
        int tongCong = (mDbProject.getTongThu() - mDbProject.getTongChi());
        txtTongThu.setText(String.valueOf(mDbProject.getTongThu()));
        txtTongChi.setText(String.valueOf(mDbProject.getTongChi()));

        if (tongCong<0){
            txtTongCong.setText(String.valueOf(tongCong));
            txtTongCong.setTextColor(color);
        }else {
            txtTongCong.setText(String.valueOf("+ "+tongCong));
            txtTongCong.setTextColor(color1);
        }

    }

    private void soLuocTaiKhoan(){
        ListView mLv_taikhoan = getActivity().findViewById(R.id.lv_thongke_taikhoan);
        TextView txtTaiKhoan = getActivity().findViewById(R.id.txtsoluoc_taikhoan);

        if (mDbProject.checkBangTaiKhoan() == 1){
            txtTaiKhoan.setText("");
        }

        mMArrTaikhoan = mDbProject.getTaiKhoan();
        mMAdapter = new TaiKhoanAdapter(getActivity(), R.layout.item_list_taikhoan, mMArrTaikhoan);
        mLv_taikhoan.setAdapter(mMAdapter);
    }

    private void    soluocNganSach(){
        ListView mLv_ngansach = getActivity().findViewById(R.id.lv_thongke_ngansach);
        TextView txtNgansach= getActivity().findViewById(R.id.txtSoluoc_ngansach);
        if (mDbProject.checkNganSach() == 1){
            txtNgansach.setText("");
        }

        ArrayList<ModelNganSach> mArrNganSach = mDbProject.getNganSach();
        NgansachAdapter mAdapter = new NgansachAdapter(getActivity(), R.layout.item_them_ngansach,mArrNganSach);
        mLv_ngansach.setAdapter(mAdapter);
    }
}

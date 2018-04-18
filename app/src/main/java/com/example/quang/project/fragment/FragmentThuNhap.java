package com.example.quang.project.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.aldoapps.autoformatedittext.AutoFormatEditText;
import com.example.quang.project.DatabaseSqliteProject;
import com.example.quang.project.R;
import com.example.quang.project.adapter.ChitieuAdapter;
import com.example.quang.project.adapter.SpinnerKehoachAdapter;
import com.example.quang.project.adapter.TaiKhoanSpinnerAdapter;
import com.example.quang.project.adapter.ThunhapAdapter;
import com.example.quang.project.fragment.data.BaseManager;
import com.example.quang.project.model.ModelChitieu;
import com.example.quang.project.model.ModelLichSu;
import com.example.quang.project.model.ModelSpinner;
import com.example.quang.project.model.ModelThuNhap;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class FragmentThuNhap extends Fragment {

    private DatabaseSqliteProject mDbProject;
    private ListView mLv_thunhap;
    private ArrayList<ModelThuNhap> mArrThuNhap;
    private ThunhapAdapter mAdapter;
    private ArrayList<ModelSpinner> mMArrSpinner;
    private ArrayList<String> mArrTenTaiKhoan;
    private String mDate;
    private Bundle mBundle;
    private BaseManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_thu_nhap, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDbProject = new DatabaseSqliteProject(getActivity());
        mLv_thunhap = getActivity().findViewById(R.id.lv_thunhap);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        int color = getActivity().getResources().getColor(R.color.color_kehoachThu);
        toolbar.setBackgroundColor(color);
        getActivity().setTitle("Thu Nhập");

        FAB();

        mManager = new BaseManager();

        mDate = mManager.getCurrentDate() ;

        mBundle = getArguments();
        if (mBundle== null){

        }else {
            dialogThuNhap();
        }

        mArrThuNhap = mDbProject.getThuNhap();
        Collections.reverse(mArrThuNhap);

        mAdapter = new ThunhapAdapter(getActivity(), R.layout.item_list_thunhap,mArrThuNhap);
        mLv_thunhap.setAdapter(mAdapter);
    }

    private void FAB(){
        final FloatingActionMenu floatingActionMenu = getActivity().findViewById(R.id.fab);
        FloatingActionButton button1 = getActivity().findViewById(R.id.menu_kehoach_chi);
        final FloatingActionButton button2 = getActivity().findViewById(R.id.menu_kehoach_thu);
        floatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentChi fragmentChi = new FragmentChi();
                Bundle bundle = new Bundle();
                bundle.putString("chi","Chi");
                fragmentChi.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content,fragmentChi);
                transaction.commit();

                floatingActionMenu.close(true);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThuNhap();
                floatingActionMenu.close(true);

            }
        });
    }

    public void dialogThuNhap(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_thunhap);
        final Spinner spinnerHangMuc = dialog.findViewById(R.id.spinnerThemThuNhap);
        final Spinner spinnerTaiKhoan = dialog.findViewById(R.id.spinnerVi_ThemThuNhap);
        final EditText edtSoTien =(AutoFormatEditText) dialog.findViewById(R.id.edtSotien_ThemThuNhap);
        final EditText edtGhiChu = dialog.findViewById(R.id.edtGhichu_ThemThuNhap);
        Button btnThem = dialog.findViewById(R.id.btnThem_ThemThuNhap);
        Button btnThoat = dialog.findViewById(R.id.btnThoat_ThemThuNhap);
        // set spinner hang muc
        mMArrSpinner = new ArrayList<>();
        mMArrSpinner =mDbProject.getDataSpinnerThu();
        SpinnerKehoachAdapter spinnerKehoachAdapter = new SpinnerKehoachAdapter(getActivity(),R.layout.item_custom_spinner_kehoach, mMArrSpinner);
        spinnerHangMuc.setAdapter(spinnerKehoachAdapter);
        // set spinner tai khoan
        mArrTenTaiKhoan = mDbProject.getTenTaiKhoan();
        TaiKhoanSpinnerAdapter adapter = new TaiKhoanSpinnerAdapter(getActivity(),R.layout.item_spinner_taikhoan, mArrTenTaiKhoan);
        spinnerTaiKhoan.setAdapter(adapter);
        // xứ lí
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtSoTien.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                    edtSoTien.requestFocus();
                    return;
                }

                mDbProject.insertThuNhap(new ModelThuNhap(
                        mMArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),
                        spinnerTaiKhoan.getSelectedItem().toString(),
                        edtGhiChu.getText().toString(),
                        mDate,
                        Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", "")),
                        mMArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getHinh()
                ));

                mDbProject.insertLichSu(new ModelLichSu(
                        mMArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),
                        mDate,
                        edtGhiChu.getText().toString(),
                        spinnerTaiKhoan.getSelectedItem().toString(),
                        "Thu",
                        mMArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getHinh(),
                        Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""))

                ));

                Toast.makeText(getActivity(), "Thêm thu nhập thành công", Toast.LENGTH_SHORT).show();
                // tính tiền
                int sotien ;
                sotien = mDbProject.tinhTien(spinnerTaiKhoan.getSelectedItem().toString()) + Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""));

                mDbProject.updateVitien(spinnerTaiKhoan.getSelectedItem().toString(),sotien);

                mArrThuNhap.clear();
                mArrThuNhap.addAll(mDbProject.getThuNhap());

                Collections.reverse(mArrThuNhap);

                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        dialog.show();
    }


}

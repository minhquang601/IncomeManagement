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
import com.example.quang.project.fragment.data.BaseManager;
import com.example.quang.project.model.ModelChitieu;
import com.example.quang.project.model.ModelLichSu;
import com.example.quang.project.model.ModelSpinner;
import com.example.quang.project.model.ModelThuNhap;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Calendar;


public class FragmentChi extends Fragment {
    private DatabaseSqliteProject mDbProject;
    private ListView mLv_chitieu;
    private ChitieuAdapter mAdapter;
    private ArrayList<ModelSpinner> mArrSpinner;
    private ArrayList<String> mArrTenTaiKhoan;
    private String mDate;
    private ArrayList<ModelChitieu> mArrChitieu;
    private Bundle mBundle;
    private BaseManager mManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_chi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDbProject = new DatabaseSqliteProject(getActivity());
        mLv_chitieu = getActivity().findViewById(R.id.lv_chitieu);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        int color = getActivity().getResources().getColor(R.color.color_kehoachChi);
        toolbar.setBackgroundColor(color);
        getActivity().setTitle("Chi tiêu");

        FAB();

        mBundle = getArguments();
        if (mBundle == null){

        }else {
            dialogChiTieu();
        }

        mManager = new BaseManager();

        mDate = mManager.getCurrentDate();

        mArrChitieu = mDbProject.getChiTieu();

        mAdapter = new ChitieuAdapter(getActivity(), R.layout.item_list_thunhap, mArrChitieu);
        mLv_chitieu.setAdapter(mAdapter);
    }

    private void FAB() {
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
                dialogChiTieu();
                floatingActionMenu.close(true);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentThuNhap fragmentThuNhap = new FragmentThuNhap();
                Bundle bundle = new Bundle();
                bundle.putString("thunhap", "Thu");
                fragmentThuNhap.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, fragmentThuNhap);
                transaction.commit();

                floatingActionMenu.close(true);
            }
        });
    }
    public void dialogChiTieu(){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_thunhap);
        final Spinner spinnerHangMuc = dialog.findViewById(R.id.spinnerThemThuNhap);
        final Spinner spinnerTaiKhoan = dialog.findViewById(R.id.spinnerVi_ThemThuNhap);
        final EditText edtSoTien = (AutoFormatEditText)dialog.findViewById(R.id.edtSotien_ThemThuNhap);
        final EditText edtGhiChu = dialog.findViewById(R.id.edtGhichu_ThemThuNhap);
        Button btnThem = dialog.findViewById(R.id.btnThem_ThemThuNhap);
        Button btnThoat = dialog.findViewById(R.id.btnThoat_ThemThuNhap);
        // set spinner hang muc
        mArrSpinner = mDbProject.getDataSpinnerChi();
        SpinnerKehoachAdapter spinnerKehoachAdapter = new SpinnerKehoachAdapter(getActivity(),R.layout.item_custom_spinner_kehoach, mArrSpinner);
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
                mDbProject.insertChiTieu(new ModelChitieu(
                        mArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),
                        spinnerTaiKhoan.getSelectedItem().toString(),
                        edtGhiChu.getText().toString(),
                        mDate,
                        Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", "")),
                        mArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getHinh()
                ));

                mDbProject.insertLichSu(new ModelLichSu(mArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),
                        mDate,
                        edtGhiChu.getText().toString(),
                        spinnerTaiKhoan.getSelectedItem().toString(),
                        "Chi",
                        mArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getHinh(),
                        Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""))
                        ));

                Toast.makeText(getActivity(), "Thêm chi tiêu thành công", Toast.LENGTH_SHORT).show();
                // tính tiền
                int sotien ;
                sotien = mDbProject.tinhTien(spinnerTaiKhoan.getSelectedItem().toString()) - Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""));

                mDbProject.updateVitien(spinnerTaiKhoan.getSelectedItem().toString(),sotien);
                // tinh tien
                int tienngansach = mDbProject.getTienSuDung(
                        mArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),
                        spinnerTaiKhoan.getSelectedItem().toString()) +
                        Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""));

                mDbProject.updateTienSuDung(
                        mArrSpinner.get(spinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),
                        spinnerTaiKhoan.getSelectedItem().toString(),
                        tienngansach,mDate);

                mArrChitieu.clear();
                mArrChitieu.addAll(mDbProject.getChiTieu());
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

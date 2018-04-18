package com.example.quang.project.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.quang.project.adapter.NgansachAdapter;
import com.example.quang.project.adapter.SpinnerKehoachAdapter;
import com.example.quang.project.adapter.TaiKhoanSpinnerAdapter;
import com.example.quang.project.model.ModelChitieu;
import com.example.quang.project.model.ModelNganSach;
import com.example.quang.project.model.ModelSpinner;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class FragmentNganSach extends Fragment  {

    private DatabaseSqliteProject mDbProject;
    private String mDate;
    private ArrayList<ModelSpinner> mMArrSpinner;
    private ListView mLv_ngansach;
    private ArrayList<ModelNganSach> mArrNganSach;
    private ArrayList<String> mArrTenTaiKhoan;
    private Spinner mSpinnerVi;
    private Spinner mSpinnerHangMuc;
    private NgansachAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ngansach, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton button = getActivity().findViewById(R.id.menu_them_ngansach);
        mLv_ngansach = getActivity().findViewById(R.id.lv_ngansach);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        int color = getActivity().getResources().getColor(R.color.colorPrimary);
        toolbar.setBackgroundColor(color);

        getActivity().setTitle("Ngân Sách");

        mDbProject = new DatabaseSqliteProject(getActivity());

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDate = day+"/"+month+"/"+year;

        mArrNganSach = mDbProject.getNganSach();
        mAdapter = new NgansachAdapter(getActivity(), R.layout.item_them_ngansach,mArrNganSach);
        mLv_ngansach.setAdapter(mAdapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogThemNganSach();
            }
        });
    }

    private void dialogThemNganSach(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_ngansach);
        dialog.setCanceledOnTouchOutside(false);
        mSpinnerVi = dialog.findViewById(R.id.spinnerVi_NganSach);
        mSpinnerHangMuc = dialog.findViewById(R.id.spinnerThem_NganSach);
        final EditText edtSoTien = (AutoFormatEditText) dialog.findViewById(R.id.edtSotien_NganSach);
        Button btnThem = dialog.findViewById(R.id.btnThem_NganSach);
        Button btnThoat = dialog.findViewById(R.id.btnThoat_NganSach);

        mArrTenTaiKhoan = mDbProject.getTenTaiKhoan();
        TaiKhoanSpinnerAdapter adapter = new TaiKhoanSpinnerAdapter(getActivity(),R.layout.item_spinner_taikhoan, mArrTenTaiKhoan);
        mSpinnerVi.setAdapter(adapter);


        mMArrSpinner = new ArrayList<>();
        mMArrSpinner =mDbProject.getDataSpinnerChi();
        SpinnerKehoachAdapter spinnerKehoachAdapter = new SpinnerKehoachAdapter(getActivity(),R.layout.item_custom_spinner_kehoach, mMArrSpinner);
        mSpinnerHangMuc.setAdapter(spinnerKehoachAdapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtSoTien.getText().toString().isEmpty()){
                    return;
                }

                if (mDbProject.getTenNganSach(mMArrSpinner.get(mSpinnerHangMuc.getSelectedItemPosition()).getTenHangMuc()) == 1){
                    mDbProject.updateSotienNganSach(mMArrSpinner.get(mSpinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),
                            mDbProject.getSotienNganSach(mMArrSpinner.get(mSpinnerHangMuc.getSelectedItemPosition()).getTenHangMuc())+Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", "")));
                    Toast.makeText(getActivity(),  "Cập nhật số tiền thành công", Toast.LENGTH_SHORT).show();
                }else {
                    int tien = mDbProject.getTienChiTheoHangMuc(mMArrSpinner.get(mSpinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),mArrTenTaiKhoan.get(mSpinnerVi.getSelectedItemPosition()));

                    mDbProject.insertNganSach(new ModelNganSach(mMArrSpinner.get(mSpinnerHangMuc.getSelectedItemPosition()).getTenHangMuc(),
                            mDate,
                            Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", "")),
                            mMArrSpinner.get(mSpinnerHangMuc.getSelectedItemPosition()).getHinh(),
                            mArrTenTaiKhoan.get(mSpinnerVi.getSelectedItemPosition()),tien
                    ));

                    Toast.makeText(getActivity(), "Thêm ngân sách thành công", Toast.LENGTH_SHORT).show();
                }



                mArrNganSach.clear();
                mArrNganSach.addAll(mDbProject.getNganSach());
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

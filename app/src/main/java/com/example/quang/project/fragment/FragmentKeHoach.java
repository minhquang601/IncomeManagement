package com.example.quang.project.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aldoapps.autoformatedittext.AutoFormatEditText;
import com.example.quang.project.DatabaseSqliteProject;
import com.example.quang.project.R;
import com.example.quang.project.adapter.KehoachAdapter;
import com.example.quang.project.adapter.SpinnerKehoachAdapter;
import com.example.quang.project.adapter.TaiKhoanSpinnerAdapter;
import com.example.quang.project.fragment.main.FragmentMain;
import com.example.quang.project.model.ModelChitieu;
import com.example.quang.project.model.ModelKeHoach;
import com.example.quang.project.model.ModelLichSu;
import com.example.quang.project.model.ModelSpinner;
import com.example.quang.project.model.ModelThuNhap;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class FragmentKeHoach extends Fragment  {


    private DatabaseSqliteProject mDbProject;
    private ArrayList<ModelSpinner> mArrSpinner;
    private ArrayList<ModelKeHoach> mArrKehoach;
    private ListView mLv_kehoach;
    private KehoachAdapter mAdapter;
    private static int check;
    private static String loai;
    private String mDate;
    private TextView mTxtStatus;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kehoach, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLv_kehoach = getActivity().findViewById(R.id.lv_kehoach);
        mDbProject = new DatabaseSqliteProject(getActivity());


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDate = day+"/"+month+"/"+year;

        FAB();


        mArrKehoach = mDbProject.getDataKehoach();
        Collections.reverse(mArrKehoach);

        mAdapter = new KehoachAdapter(this, R.layout.item_list_kehoach,mArrKehoach);
        mLv_kehoach.setAdapter(mAdapter);

        mLv_kehoach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               dialogThemTien(i);
            }
        });



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
                check=1;
                dialogAdd(check);
                floatingActionMenu.close(true);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check =0;
                dialogAdd(check);
                floatingActionMenu.close(true);

            }
        });
    }

    private void dialogAdd(int i){

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add_kehoach);
        dialog.setCanceledOnTouchOutside(false);
        final Spinner spThu = dialog.findViewById(R.id.spinner_dialog_kehoach);
        final EditText edtGhichu = dialog.findViewById(R.id.edtGhichu_Kehoach);
        Button btnThem = dialog.findViewById(R.id.btnThem_Dialog_Kehoach);
        Button btnThoat = dialog.findViewById(R.id.btnThoat_Dialog_Kehoach);
        RelativeLayout layout = dialog.findViewById(R.id.dialog);


        if (i ==0){

            spThu.setPopupBackgroundResource(R.color.color_kehoachThu);
            spThu.setBackgroundResource(R.color.color_kehoachThu);
            layout.setBackgroundResource(R.color.color_kehoachThu);
            int color = ContextCompat.getColor(getActivity(),R.color.color_textBlack);
            edtGhichu.setTextColor(color);

            mArrSpinner = new  ArrayList<>();
            mArrSpinner=mDbProject.getDataSpinnerThu();
            final SpinnerKehoachAdapter spinnerKehoachAdapter = new SpinnerKehoachAdapter(getActivity(),R.layout.item_custom_spinner_kehoach,mArrSpinner);
            spThu.setAdapter(spinnerKehoachAdapter);

            loai ="Thu";

            btnThem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int vitri = spThu.getSelectedItemPosition();

                    if (mDbProject.checkKehoach(mArrSpinner.get(vitri).getTenHangMuc(),edtGhichu.getText().toString()) == 1){
                        Toast.makeText(getActivity(), "Đã có kế hoạch và tên ghi chú này rồi", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    mDbProject.insertKehoach(new ModelKeHoach(mArrSpinner.get(vitri).getTenHangMuc(),
                            edtGhichu.getText().toString(),
                            loai,
                            mDate,
                            mArrSpinner.get(vitri).getHinh()));
                    Toast.makeText(getActivity(), "Thêm kế hoạch thu thành công", Toast.LENGTH_SHORT).show();
                    mArrKehoach.clear();

                    mArrKehoach.addAll(mDbProject.getDataKehoach());

                    Collections.reverse(mArrKehoach);
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
        }else if (i ==1){

            spThu.setPopupBackgroundResource(R.color.color_kehoachChi);
            spThu.setBackgroundResource(R.color.color_kehoachChi);
            layout.setBackgroundResource(R.color.color_kehoachChi);
            int color = ContextCompat.getColor(getActivity(),R.color.color_textTeal);
            edtGhichu.setTextColor(color);


            mArrSpinner = new ArrayList<>();
            mArrSpinner=mDbProject.getDataSpinnerChi();
            final SpinnerKehoachAdapter spinnerKehoachAdapter = new SpinnerKehoachAdapter(getActivity(),R.layout.item_custom_spinner_kehoach,mArrSpinner);
            spThu.setAdapter(spinnerKehoachAdapter);

            loai = "Chi";
            btnThem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int vitri = spThu.getSelectedItemPosition();

                    if (mDbProject.checkKehoach(mArrSpinner.get(vitri).getTenHangMuc(),edtGhichu.getText().toString()) == 1){
                        Toast.makeText(getActivity(), "Đã có kế hoạch và tên ghi chú này rồi", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mDbProject.insertKehoach(new ModelKeHoach(mArrSpinner.get(vitri).getTenHangMuc(),
                            edtGhichu.getText().toString(),
                            loai,
                            mDate,
                            mArrSpinner.get(vitri).getHinh()));
                    Toast.makeText(getActivity(), "Thêm kế hoạch chi thành công", Toast.LENGTH_SHORT).show();
                    mArrKehoach.clear();
                    mArrKehoach.addAll(mDbProject.getDataKehoach());
                    Collections.reverse(mArrKehoach);
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

    public void dialogThemTien(final int i){
        final int vitri =i;
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_themtien_kehoach);
        dialog.setCanceledOnTouchOutside(false);
        final Spinner spinnerThemTien = dialog.findViewById(R.id.spinnerThemTien);
        final EditText edtSoTien =(AutoFormatEditText) dialog.findViewById(R.id.edtTienThem_kehoach);
        Button btnThem = dialog.findViewById(R.id.btnThemTien_kehoach);
        Button btnThoat = dialog.findViewById(R.id.btnThoatTien_kehoach);
        // set spinner
        ArrayList<String> arrTenTaiKhoan =mDbProject.getTenTaiKhoan();
        TaiKhoanSpinnerAdapter  adapter = new TaiKhoanSpinnerAdapter(getActivity(),R.layout.item_spinner_taikhoan,arrTenTaiKhoan);
        spinnerThemTien.setAdapter(adapter);


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mDbProject.getLoaiKehoach(mArrKehoach.get(vitri).getTenHangMuc()).equals("Thu")){
                    mDbProject.insertThuNhap(new ModelThuNhap(mArrKehoach.get(vitri).getTenHangMuc(),
                                    spinnerThemTien.getSelectedItem().toString(),
                                    mArrKehoach.get(vitri).getGhiChu(),
                                    mArrKehoach.get(vitri).getNgay(),
                                    Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", "")),
                                    mArrKehoach.get(vitri).getHinhKehoach()
                                    ));
                    mDbProject.insertLichSu(new ModelLichSu(
                            mArrKehoach.get(vitri).getTenHangMuc(),
                            mDate,
                            mArrKehoach.get(vitri).getGhiChu(),
                            spinnerThemTien.getSelectedItem().toString(),
                            "Thu",
                            mArrKehoach.get(vitri).getHinhKehoach(),
                            Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""))

                    ));

                    // tính tiền
                    int sotien ;
                    sotien = mDbProject.tinhTien(spinnerThemTien.getSelectedItem().toString()) + Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""));

                    mDbProject.updateVitien(spinnerThemTien.getSelectedItem().toString(),sotien);

                    mDbProject.deleteKehoach(mArrKehoach.get(vitri).getTenHangMuc(),mArrKehoach.get(vitri).getGhiChu());

                    //
                    mArrKehoach.clear();
                    mArrKehoach.addAll(mDbProject.getDataKehoach());
                    Collections.reverse(mArrKehoach);
                    mAdapter.notifyDataSetChanged();

                    FragmentMain fragmentMain = new FragmentMain();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, fragmentMain);
                    transaction.commit();

                    Toast.makeText(getActivity(), "Thêm vào thu nhập thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                else {
                    mDbProject.insertChiTieu(new ModelChitieu(mArrKehoach.get(vitri).getTenHangMuc(),
                            spinnerThemTien.getSelectedItem().toString(),
                            mArrKehoach.get(vitri).getGhiChu(),
                            mArrKehoach.get(vitri).getNgay(),
                            Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", "")),
                            mArrKehoach.get(vitri).getHinhKehoach()
                    ));

                    mDbProject.insertLichSu(new ModelLichSu(
                            mArrKehoach.get(vitri).getTenHangMuc(),
                            mDate,
                            mArrKehoach.get(vitri).getGhiChu(),
                            spinnerThemTien.getSelectedItem().toString(),
                            "Chi",
                            mArrKehoach.get(vitri).getHinhKehoach(),
                            Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""))

                    ));

                    int sotien ;
                    sotien = mDbProject.tinhTien(spinnerThemTien.getSelectedItem().toString()) - Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""));

                    mDbProject.updateVitien(spinnerThemTien.getSelectedItem().toString(),sotien);

                    mDbProject.deleteKehoach(mArrKehoach.get(vitri).getTenHangMuc(),mArrKehoach.get(vitri).getGhiChu());

                    // tính tiền ngân sách

                    int tienngansach = mDbProject.getTienSuDung(mArrKehoach.get(vitri).getTenHangMuc(),spinnerThemTien.getSelectedItem().toString()) + Integer.parseInt(edtSoTien.getText().toString().replaceAll(",", ""));
                    mDbProject.updateTienSuDung(mArrKehoach.get(vitri).getTenHangMuc(),spinnerThemTien.getSelectedItem().toString(),tienngansach,mDate);

                    mArrKehoach.clear();
                    mArrKehoach.addAll(mDbProject.getDataKehoach());
                    Collections.reverse(mArrKehoach);
                    mAdapter.notifyDataSetChanged();

                    FragmentMain fragmentMain = new FragmentMain();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, fragmentMain);
                    transaction.commit();

                    Toast.makeText(getActivity(), "Thêm vào chi tiêu thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

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

    public String loaiKeHoach(String tenhangmuc){
      return   mDbProject.getLoaiKehoach(tenhangmuc);
    }

}

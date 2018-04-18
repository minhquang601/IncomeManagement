package com.example.quang.project.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aldoapps.autoformatedittext.AutoFormatEditText;
import com.example.quang.project.DatabaseSqliteProject;
import com.example.quang.project.R;
import com.example.quang.project.adapter.TaiKhoanAdapter;
import com.example.quang.project.model.ModelTaiKhoan;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;


public class FragmentTaiKhoan extends Fragment {

    private DatabaseSqliteProject mDbProject;
    private TextView mTxtTaikhoan_check;
    private ArrayList<ModelTaiKhoan> mArrTaikhoan;
    private ListView mLv_taikhoan;
    private TaiKhoanAdapter mAdapter;
    private Bundle mBundle;
    private String mTentaikhoan;
    private int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_taikhoan, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTxtTaikhoan_check = getActivity().findViewById(R.id.txtTaiKhoan_Check);
        mLv_taikhoan = getActivity().findViewById(R.id.lv_taikhoan);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        int color = getActivity().getResources().getColor(R.color.colorPrimary);
        toolbar.setBackgroundColor(color);

        mDbProject = new DatabaseSqliteProject(getActivity());

        getActivity().setTitle("Tài Khoản");

        if (mDbProject.checkBangTaiKhoan() == 0) {
            mTxtTaikhoan_check.setText("Không có tài khoản vui lòng thêm tài khoản");
        } else if (mDbProject.checkBangTaiKhoan() == 1) {
            mTxtTaikhoan_check.setText("");
        }

        mBundle = getArguments();

        if (mBundle == null) {
            if (mDbProject.checkBangTaiKhoan() == 0) {
                dialogTaiKhoan();
            }
        } else {
            dialogTaiKhoan();
        }

        FAB();

        mArrTaikhoan = mDbProject.getTaiKhoan();
        mAdapter = new TaiKhoanAdapter(getActivity(), R.layout.item_list_taikhoan, mArrTaikhoan);
        mLv_taikhoan.setAdapter(mAdapter);

        mLv_taikhoan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTentaikhoan = mArrTaikhoan.get(position).getTenTaiKhoan();
                FragmentLichSu fragmentLichSu = new FragmentLichSu();
                Bundle bundle = new Bundle();
                bundle.putString("tentaikhoan", mTentaikhoan);
                fragmentLichSu.setArguments(bundle);
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, fragmentLichSu);
                transaction.commit();
            }
        });

        registerForContextMenu(mLv_taikhoan);
        mLv_taikhoan.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                i = position;
                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteTk) {
            dialogDelete();
        }
        return super.onContextItemSelected(item);
    }

    private void FAB() {
        final FloatingActionMenu floatingActionMenu = getActivity().findViewById(R.id.fabTaiKhoan);
        FloatingActionButton button2 = getActivity().findViewById(R.id.menu_taikhoan);
        floatingActionMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTaiKhoan();
                floatingActionMenu.close(true);

            }
        });
    }

    private void dialogTaiKhoan() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_them_taikhoan);
        final EditText edtTenTaiKhoan = dialog.findViewById(R.id.edtTenTaiKhoan);
        final EditText edtTienTaiKhoan =(AutoFormatEditText) dialog.findViewById(R.id.edtTienTaiKhoan);
        Button btnThemTaiKhoan = dialog.findViewById(R.id.btnThemTaiKhoan);
        Button btnnThoatTaiKhoan = dialog.findViewById(R.id.btnThoatTaiKhoan);

        btnThemTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkInput(edtTenTaiKhoan, edtTienTaiKhoan)) {
                    return;
                }

                mDbProject.insertTaiKhoan(new ModelTaiKhoan(edtTenTaiKhoan.getText().toString(),
                        Integer.parseInt(edtTienTaiKhoan.getText().toString().replaceAll(",", ""))));
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                if (mDbProject.checkBangTaiKhoan() == 1) {
                    mTxtTaikhoan_check.setText("");
                }
                mArrTaikhoan.clear();
                mArrTaikhoan.addAll(mDbProject.getTaiKhoan());
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        btnnThoatTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private boolean checkInput(EditText edtTenTaiKhoan, EditText edtTienTaiKhoan) {
        if (edtTenTaiKhoan.getText().toString().isEmpty()) {
            edtTenTaiKhoan.requestFocus();
            Toast.makeText(getActivity(), "Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtTienTaiKhoan.getText().toString().isEmpty()) {
            edtTienTaiKhoan.requestFocus();
            Toast.makeText(getActivity(), "Vui long nhập số tiền vào tài khoản", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mDbProject.checkTenVi(edtTenTaiKhoan.getText().toString()) == 1) {
            Toast.makeText(getActivity(), "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            edtTenTaiKhoan.requestFocus();
            return false;
        }
        return true;
    }

    private void dialogDelete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Xóa tài khoản");
        builder.setMessage("Bạn có muốn xóa tài khoản " + mArrTaikhoan.get(i).getTenTaiKhoan() + " và những thứ liên quan tới tài khoản này sẽ bị xóa");
        builder.setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDbProject.deleteTk(mArrTaikhoan.get(i).getTenTaiKhoan());
                mDbProject.deleteChi(mArrTaikhoan.get(i).getTenTaiKhoan());
                mDbProject.deleteLichSu(mArrTaikhoan.get(i).getTenTaiKhoan());
                mDbProject.deleteThu(mArrTaikhoan.get(i).getTenTaiKhoan());
                mDbProject.deleteNganSach(mArrTaikhoan.get(i).getTenTaiKhoan());
                Toast.makeText(getActivity(), "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                mArrTaikhoan.clear();
                mArrTaikhoan.addAll(mDbProject.getTaiKhoan());
                mAdapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}

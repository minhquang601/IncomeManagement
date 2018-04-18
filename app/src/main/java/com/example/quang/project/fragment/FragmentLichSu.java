package com.example.quang.project.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.quang.project.DatabaseSqliteProject;
import com.example.quang.project.R;
import com.example.quang.project.adapter.LichSuAdapter;
import com.example.quang.project.model.ModelLichSu;

import java.util.ArrayList;
import java.util.Collections;


public class FragmentLichSu extends Fragment {

    private DatabaseSqliteProject mDbProject;
    private ArrayList<ModelLichSu> mArrLichSu;
    private ListView mLv_lichsu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_lichsu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDbProject = new DatabaseSqliteProject(getActivity());
        mLv_lichsu = getActivity().findViewById(R.id.lv_lichsu);
        Bundle bundle = getArguments();
        if (bundle == null){
            mArrLichSu = mDbProject.getLichSu();
        }else {
            mArrLichSu = mDbProject.getLichSu(bundle.getString("tentaikhoan"));
        }

        Collections.reverse(mArrLichSu);
        LichSuAdapter adapter = new LichSuAdapter(this,R.layout.item_list_thunhap, mArrLichSu);
        mLv_lichsu.setAdapter(adapter);

    }

    public String loaiLichSu(String tenhangmuc){
        return   mDbProject.getLoaiLichSu(tenhangmuc);
    }
}

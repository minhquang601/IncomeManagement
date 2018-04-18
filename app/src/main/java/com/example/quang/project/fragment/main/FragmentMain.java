package com.example.quang.project.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quang.project.adapter.TabAdapter;
import com.example.quang.project.R;
import com.example.quang.project.fragment.FragmentKeHoach;
import com.example.quang.project.fragment.FragmentLichSu;
import com.example.quang.project.fragment.FragmentThongKe;


public class FragmentMain extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentManager mManager;
    private TabAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return   inflater.inflate(R.layout.fragment_fragment_test, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        int color = getActivity().getResources().getColor(R.color.colorPrimary);
        toolbar.setBackgroundColor(color);

        mTabLayout = getActivity().findViewById(R.id.tabs);
        mManager = getActivity().getSupportFragmentManager();
        mAdapter = new TabAdapter(mManager);
        mAdapter.addFragment(new FragmentThongKe(), "Thống kê");
        mAdapter.addFragment(new FragmentKeHoach(), "Kế hoạch");
        mAdapter.addFragment(new FragmentLichSu(), "Lịch sử");
        mViewPager = getActivity().findViewById(R.id.container);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


}

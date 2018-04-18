package com.example.quang.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quang.project.R;

import java.util.ArrayList;

/**
 * Created by quang on 11/6/2017.
 */

public class TaiKhoanSpinnerAdapter  extends BaseAdapter{
    Context mContext;
    int layout;
    ArrayList<String> arrSpinner;
    private TextView mTextView;

    public TaiKhoanSpinnerAdapter(Context context, int layout, ArrayList<String> arrSpinner) {
        mContext = context;
        this.layout = layout;
        this.arrSpinner = arrSpinner;
    }

    @Override
    public int getCount() {
        return arrSpinner.size();
    }

    @Override
    public Object getItem(int i) {
        return arrSpinner.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            mTextView = view.findViewById(R.id.txtSpinnerTenTaiKhoan);
        }

        mTextView.setText(arrSpinner.get(i));

        return view;
    }
}

package com.example.quang.project.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quang.project.R;
import com.example.quang.project.model.ModelTaiKhoan;

import java.util.ArrayList;

/**
 * Created by quang on 11/6/2017.
 */

public class TaiKhoanAdapter extends BaseAdapter{
    Context mContext;
    int layout;
    ArrayList<ModelTaiKhoan> arrTaiKhoan;

    public TaiKhoanAdapter(Context context, int layout, ArrayList<ModelTaiKhoan> arrTaiKhoan) {
        mContext = context;
        this.layout = layout;
        this.arrTaiKhoan = arrTaiKhoan;
    }

    @Override
    public int getCount() {
        return arrTaiKhoan.size();
    }

    @Override
    public Object getItem(int i) {
        return arrTaiKhoan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenTaiKhoan,txtSoTienTaiKhoan;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh ;
        if (    view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            vh = new ViewHolder();
            vh.txtTenTaiKhoan = view.findViewById(R.id.txtTenTaiKhoan);
            vh.txtSoTienTaiKhoan = view.findViewById(R.id.txtSotienTaiKhoan);

            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        ModelTaiKhoan taiKhoan = arrTaiKhoan.get(i);
        vh.txtTenTaiKhoan.setText(taiKhoan.getTenTaiKhoan());
        vh.txtSoTienTaiKhoan.setText(String.valueOf(taiKhoan.getSotien()));

        return view;
    }
}

package com.example.quang.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quang.project.R;
import com.example.quang.project.model.ModelThuNhap;

import java.util.ArrayList;

/**
 * Created by quang on 11/6/2017.
 */

public class ThunhapAdapter extends BaseAdapter{

    Context mContext;
    int layout;
    ArrayList<ModelThuNhap> arrThuNhap;

    public ThunhapAdapter(Context context, int layout, ArrayList<ModelThuNhap> arrThuNhap) {
        mContext = context;
        this.layout = layout;
        this.arrThuNhap = arrThuNhap;
    }

    @Override
    public int getCount() {
        return arrThuNhap.size();
    }

    @Override
    public Object getItem(int i) {
        return arrThuNhap.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenhangmuc_Thunhap,txtGhichu_thunhap,txtSotien_thunhap,txtTaikhoan_thunhap,txt_ngay;
        ImageView imgHinh;
        RelativeLayout layoutRe;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view== null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            vh = new ViewHolder();
            vh.txtTenhangmuc_Thunhap = view.findViewById(R.id.txtTenhangmuc_Thunhap);
            vh.txtGhichu_thunhap = view.findViewById(R.id.txtGhichu_thunhap);
            vh.txtSotien_thunhap = view.findViewById(R.id.txtSotien_thunhap);
            vh.txtTaikhoan_thunhap = view.findViewById(R.id.txtTaikhoan_thunhap);
            vh.txt_ngay = view.findViewById(R.id.txtNgayThuNhap);
            vh.imgHinh = view.findViewById(R.id.imgHinh_Thunhap);
            vh.layoutRe = view.findViewById(R.id.item_list_relative_Thuchi);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        ModelThuNhap thuNhap = arrThuNhap.get(i);

        vh.layoutRe.setBackgroundResource(R.color.color_kehoachThu);

        vh.txtTenhangmuc_Thunhap.setText(thuNhap.getTenHangmuc());
        vh.txtTaikhoan_thunhap.setText(thuNhap.getTenTaiKhoan());
        vh.txtSotien_thunhap.setText("+ "+String.valueOf(thuNhap.getTien())+" VND");
        vh.txtGhichu_thunhap.setText(thuNhap.getGhiChu());
        vh.txt_ngay.setText(thuNhap.getNgay());
        byte[] img = thuNhap.getImgHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);

        vh.imgHinh.setImageBitmap(bitmap);

        return view;
    }
}

package com.example.quang.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quang.project.R;
import com.example.quang.project.model.ModelNganSach;

import java.util.ArrayList;

/**
 * Created by quang on 11/8/2017.
 */

public class NgansachAdapter extends BaseAdapter {
    Context mContext;
    int layout;
    ArrayList<ModelNganSach> arrNganSach;

    public NgansachAdapter(Context context, int layout, ArrayList<ModelNganSach> arrNganSach) {
        mContext = context;
        this.layout = layout;
        this.arrNganSach = arrNganSach;
    }

    @Override
    public int getCount() {
        return arrNganSach.size();
    }

    @Override
    public Object getItem(int i) {
        return arrNganSach.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenHangMuc,txtVi,txtNganSach,txtTienSuDung,txtNgay,txtPhanTram;
        ProgressBar mProgressBar;
        ImageView imgHinh;
        RelativeLayout mLayout;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            vh = new ViewHolder();
            vh.txtTenHangMuc = view.findViewById(R.id.txtTenHangMuc_NganSach);
            vh.txtNganSach = view.findViewById(R.id.txtSotien_NganSach);
            vh.txtNgay = view.findViewById(R.id.txtNgay_Ngansach);
            vh.txtPhanTram = view.findViewById(R.id.txtPhanTram);
            vh.txtTienSuDung = view.findViewById(R.id.txtTienSuDung_Ngansach);
            vh.txtVi = view.findViewById(R.id.txtVi_NganSach);
            vh.mProgressBar = view.findViewById(R.id.progressBar_NganSach);
            vh.imgHinh = view.findViewById(R.id.imgHinh_NganSach);
            vh.mLayout = view.findViewById(R.id.layout_ngansach);

            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        ModelNganSach nganSach = arrNganSach.get(i);
        vh.txtTenHangMuc.setText(nganSach.getTenhangmuc());
        vh.txtVi.setText(nganSach.getTentaikhoan());

        vh.txtTienSuDung.setText(String.valueOf(nganSach.getTiensudung() +" VND"));
        vh.txtNganSach.setText(String.valueOf(nganSach.getSotien()+" VND"));
        vh.txtNgay.setText(nganSach.getNgay());
        vh.mProgressBar.setMax(nganSach.getSotien());
        vh.mProgressBar.setProgress(nganSach.getTiensudung());
        byte[] hinh = nganSach.getImgHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        vh.imgHinh.setImageBitmap(bitmap);
        float tien = (nganSach.getTiensudung()*100) / nganSach.getSotien();

        vh.txtPhanTram.setText(String.valueOf(tien+ "%") ) ;
        vh.mLayout.setBackgroundResource(R.color.color_NganSach);
        vh.txtPhanTram.setTextColor(Color.BLACK);
        vh.txtNgay.setTextColor(Color.BLACK);
        vh.txtNganSach.setTextColor(Color.BLUE);
        vh.txtTienSuDung.setTextColor(Color.RED);
        vh.txtVi.setTextColor(Color.BLACK);
        vh.txtTenHangMuc.setTextColor(Color.BLACK);

        return view;
    }
}

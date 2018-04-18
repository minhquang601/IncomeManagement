package com.example.quang.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quang.project.R;
import com.example.quang.project.model.ModelKeHoach;

import java.util.ArrayList;

/**
 * Created by quang on 11/6/2017.
 */

public class KehoachAdapterChi extends BaseAdapter{
    Context mContext;
    int layout;
    ArrayList<ModelKeHoach> arrModel;

    public KehoachAdapterChi(Context context, int layout, ArrayList<ModelKeHoach> arrModel) {
        mContext = context;
        this.layout = layout;
        this.arrModel = arrModel;
    }

    @Override
    public int getCount() {
        return arrModel.size();
    }

    @Override
    public Object getItem(int i) {
        return arrModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenHangMuc,txtNgay,txtGhiChu;
        ImageView imgHinh;
        RelativeLayout  layout;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder ;
        if (view ==null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.txtTenHangMuc = view.findViewById(R.id.txtHangMuc_Kehoach);
            viewHolder.txtNgay = view.findViewById(R.id.txtNgay_kehoach);
            viewHolder.txtGhiChu = view.findViewById(R.id.txtGhiChu_Kehoach);
            viewHolder.imgHinh = view.findViewById(R.id.img_Kehoach);
             viewHolder.layout = view.findViewById(R.id.card_view_kehoach);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }



        ModelKeHoach modelKeHoach = arrModel.get(i);

        
            viewHolder.layout.setBackgroundResource(R.color.color_kehoachChi);
            viewHolder.txtNgay.setText(modelKeHoach.getNgay());
            viewHolder.txtTenHangMuc.setText(modelKeHoach.getTenHangMuc());
            viewHolder.txtGhiChu.setText(modelKeHoach.getGhiChu());
            byte[] bytes = modelKeHoach.getHinhKehoach();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            viewHolder.imgHinh.setImageBitmap(bitmap);



        return view;
    }
}

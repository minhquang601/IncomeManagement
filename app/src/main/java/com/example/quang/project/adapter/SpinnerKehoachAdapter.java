package com.example.quang.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quang.project.R;
import com.example.quang.project.model.ModelKeHoach;
import com.example.quang.project.model.ModelSpinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by quang on 11/6/2017.
 */

public class SpinnerKehoachAdapter  extends BaseAdapter{
    Context mContext;
    int layout;
    ArrayList<ModelSpinner> mModelSpinners ;

    public SpinnerKehoachAdapter(Context context, int layout, ArrayList<ModelSpinner> modelSpinners) {
        mContext = context;
        this.layout = layout;
        mModelSpinners = modelSpinners;
    }

    @Override
    public int getCount() {
        return mModelSpinners.size();
    }

    @Override
    public Object getItem(int i) {
        return mModelSpinners.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView txtTenHangMuc;
        ImageView imgHinh;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            vh = new ViewHolder();
            vh.txtTenHangMuc = view.findViewById(R.id.txtTenHangMuc_Spinner);
            vh.imgHinh = view.findViewById(R.id.imgKeHoach_Spinner);

            view.setTag(vh);

        }else {
            vh = (ViewHolder) view.getTag();
        }

        ModelSpinner spinner = mModelSpinners.get(i);
        vh.txtTenHangMuc.setText(spinner.getTenHangMuc());

        byte[] hinh = spinner.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);

        vh.imgHinh.setImageBitmap(bitmap);

        return view;
    }
}

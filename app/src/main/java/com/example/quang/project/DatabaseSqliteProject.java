package com.example.quang.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.view.Display;

import com.example.quang.project.model.ModelChitieu;
import com.example.quang.project.model.ModelKeHoach;
import com.example.quang.project.model.ModelLichSu;
import com.example.quang.project.model.ModelNganSach;
import com.example.quang.project.model.ModelSpinner;
import com.example.quang.project.model.ModelTaiKhoan;
import com.example.quang.project.model.ModelThuNhap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by quang on 11/2/2017.
 */

public class DatabaseSqliteProject extends SQLiteOpenHelper {


    private static final String TABLE_TAIKHOAN="Bangtaikhoan";
    private static final String TABLE_CHITIEU="Chitieu";
    private static final String TABLE_HANGMUC_CHICTIEU="HangMucChiTieu";
    private static final String TABLE_HANGMUC_THUNHAP="HangMucThuNhap";
    private static final String TABLE_SOLUOC="Soluoc";
    private static final String TABLE_THUNHAP="Thunhap";
    private static final String TABLE_TONG="Tong";
    private static final String TABLE_KEHOACH="Kehoach";
    private static final String TABLE_NGANSACH="Ngansach";
    private static final String TABLE_LICHSU="Lichsu";

    private static final String COLUMN_THU="thu";
    private static final String COLUMN_CHI="chi";
    private static final String COLUMN_TONG="tong";
    private static final String COLUMN_TENHANGMUC="tenhangmuc";
    private static final String COLUMN_LOAIHANGMUC="loai";
    private static final String COLUMN_TEN_TAIKHOAN="tentaikhoan";
    private static final String COLUMN_NGAY="ngay";
    private static final String COLUMN_GHICHU="ghichu";
    private static final String COLUMN_SOTIEN="sotien";
    private static final String COLUMN_HINH="hinh";
    private static final String COLUMN_ID="ID";
    private static final String COLUMN_TIENSUDUNG="tiensudung";


    private static String DB_PATH = "";
    private static String DB_NAME = "project.db";
    private SQLiteDatabase mDatabase;
    private Context mContext = null;


    public DatabaseSqliteProject(Context context) {
        super(context, DB_NAME, null, 1);
        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        mContext = context;
    }

    @Override
    public synchronized void close() {
        super.close();
        if (mDatabase != null) {
            mDatabase.close();
        }
    }
    private boolean checkDatabase() {
        SQLiteDatabase tempDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {

        }
        if (tempDB != null) {
            tempDB.close();
        }
        return tempDB != null ? true : false;
    }
    public void copyDatabase() {
        try {
            InputStream myInput = mContext.getAssets().open(DB_NAME);
            String outputFile = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDatabase() {
        String path = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

    }
    public void createDatabse() {
        boolean isDBExist = checkDatabase();
        if (isDBExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch (Exception e) {

            }
        }
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public ArrayList<ModelSpinner> getDataSpinnerThu(){
        ArrayList<ModelSpinner> arSp = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HANGMUC_THUNHAP,new String[]{COLUMN_HINH,COLUMN_TENHANGMUC},null,null,null,null,null);
        while (cursor.moveToNext()){
            arSp.add(new ModelSpinner(cursor.getString(cursor.getColumnIndex(COLUMN_TENHANGMUC)),cursor.getBlob(cursor.getColumnIndex(COLUMN_HINH))));
        }
        cursor.close();
        db.close();
        return arSp;
    }
    public ArrayList<ModelSpinner> getDataSpinnerChi(){
        ArrayList<ModelSpinner> arSp = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HANGMUC_CHICTIEU,new String[]{COLUMN_HINH,COLUMN_TENHANGMUC},null,null,null,null,null);
        while (cursor.moveToNext()){
            arSp.add(new ModelSpinner(cursor.getString(cursor.getColumnIndex(COLUMN_TENHANGMUC)),cursor.getBlob(cursor.getColumnIndex(COLUMN_HINH))));
        }
        cursor.close();
        db.close();
        return arSp;
    }

    public ArrayList<ModelKeHoach> getDataKehoach(){
        ArrayList<ModelKeHoach> arSp = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KEHOACH,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            arSp.add(new ModelKeHoach(cursor.getString(cursor.getColumnIndex(COLUMN_TENHANGMUC)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LOAIHANGMUC)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NGAY)),
                    cursor.getBlob(cursor.getColumnIndex(COLUMN_HINH))
                   ));
        }
        return arSp;
    }

    public void insertKehoach(ModelKeHoach modelKeHoach){
       SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENHANGMUC,modelKeHoach.getTenHangMuc());
        values.put(COLUMN_NGAY,modelKeHoach.getNgay());
        values.put(COLUMN_HINH,modelKeHoach.getHinhKehoach());
        values.put(COLUMN_GHICHU,modelKeHoach.getGhiChu());
        values.put(COLUMN_LOAIHANGMUC,modelKeHoach.getLoai());
        db.insert(TABLE_KEHOACH,null,values);
        db.close();
    }



    public  int checkBangTaiKhoan(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TAIKHOAN,null,null,null,null,null,null);
        int count = cursor.getCount();
        return count;
    }



    public void insertTaiKhoan(ModelTaiKhoan taiKhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_TAIKHOAN,taiKhoan.getTenTaiKhoan());
        values.put(COLUMN_SOTIEN,taiKhoan.getSotien());

        db.insert(TABLE_TAIKHOAN,null,values);
        db.close();
    }

    public ArrayList<ModelTaiKhoan> getTaiKhoan(){
        ArrayList<ModelTaiKhoan> taikhoan = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TAIKHOAN,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            taikhoan.add(new ModelTaiKhoan(cursor.getString(cursor.getColumnIndex(COLUMN_TEN_TAIKHOAN)),cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN))));
        }
        return taikhoan;
    }
    public ArrayList<String> getTenTaiKhoan(){
        ArrayList<String> taikhoan = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TAIKHOAN,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            taikhoan.add(cursor.getString(cursor.getColumnIndex(COLUMN_TEN_TAIKHOAN)));
        }
        return taikhoan;
    }

    public void insertThuNhap(ModelThuNhap modelThuNhap){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HINH,modelThuNhap.getImgHinh());
        values.put(COLUMN_TENHANGMUC,modelThuNhap.getTenHangmuc());
        values.put(COLUMN_TEN_TAIKHOAN,modelThuNhap.getTenTaiKhoan());
        values.put(COLUMN_SOTIEN,modelThuNhap.getTien());
        values.put(COLUMN_GHICHU,modelThuNhap.getGhiChu());
        values.put(COLUMN_NGAY,modelThuNhap.getNgay());

        db.insert(TABLE_THUNHAP,null,values);
        db.close();

    }

    public ArrayList<ModelThuNhap> getThuNhap(){
        ArrayList<ModelThuNhap> arrThu = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_THUNHAP,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            arrThu.add(new ModelThuNhap(cursor.getString(cursor.getColumnIndex(COLUMN_TENHANGMUC)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TEN_TAIKHOAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NGAY)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN)),
                    cursor.getBlob(cursor.getColumnIndex(COLUMN_HINH))
                    ));
        }
        return arrThu;
    }
    public String getLoaiKehoach(String tenhangmuc){
        String loai =null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KEHOACH,null,COLUMN_TENHANGMUC+"=?",new String[]{tenhangmuc},null,null,null);
        while (cursor.moveToNext()){
            loai = cursor.getString(cursor.getColumnIndex(COLUMN_LOAIHANGMUC));
        }
        return loai;
    }
    public void insertChiTieu(ModelChitieu modelChitieu){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HINH,modelChitieu.getImgHinh());
        values.put(COLUMN_TENHANGMUC,modelChitieu.getTenHangmuc());
        values.put(COLUMN_TEN_TAIKHOAN,modelChitieu.getTenTaiKhoan());
        values.put(COLUMN_SOTIEN,modelChitieu.getTien());
        values.put(COLUMN_GHICHU,modelChitieu.getGhiChu());
        values.put(COLUMN_NGAY,modelChitieu.getNgay());

        db.insert(TABLE_CHITIEU,null,values);
        db.close();

    }

    public ArrayList<ModelChitieu> getChiTieu(){
        ArrayList<ModelChitieu> arrThu = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CHITIEU,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            arrThu.add(new ModelChitieu(cursor.getString(cursor.getColumnIndex(COLUMN_TENHANGMUC)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TEN_TAIKHOAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NGAY)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN)),
                    cursor.getBlob(cursor.getColumnIndex(COLUMN_HINH))
            ));
        }
        return arrThu;

    }

    public  int deleteKehoach(String tenhangmuc,String ghichu){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_KEHOACH,COLUMN_TENHANGMUC+"=?"+" AND " + COLUMN_GHICHU+"=?",new String[]{tenhangmuc,ghichu});
    }



    public int checkTenVi(String tentaikhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TAIKHOAN,new String[]{COLUMN_TEN_TAIKHOAN},COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan},null,null,null);
        int count = cursor.getCount();
        while (cursor.moveToNext()){
            if (count ==1){
                return count;
            }
        }
        return count;
    }

    public int tinhTien(String tentaikhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TAIKHOAN,new String[]{COLUMN_SOTIEN},COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan},null,null,null);
        int tien = 0;
        while (cursor.moveToNext()){
            tien = cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN));
        }
        return tien;
    }

    public int updateVitien(String tentaikhoan, int sotien){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOTIEN,sotien);
       return db.update(TABLE_TAIKHOAN,values,COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan});
    }

    public void insertNganSach(ModelNganSach nganSach){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENHANGMUC,nganSach.getTenhangmuc());
        values.put(COLUMN_SOTIEN,nganSach.getSotien());
        values.put(COLUMN_NGAY,nganSach.getNgay());
        values.put(COLUMN_HINH,nganSach.getImgHinh());
        values.put(COLUMN_TIENSUDUNG,nganSach.getTiensudung());
        values.put(COLUMN_TEN_TAIKHOAN,nganSach.getTentaikhoan());
        db.insert(TABLE_NGANSACH,null,values);
        db.close();
    }
    public ArrayList<ModelNganSach> getNganSach(){
        ArrayList<ModelNganSach> arrNgansach = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NGANSACH,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            arrNgansach.add(new ModelNganSach(
                    cursor.getString(cursor.getColumnIndex(COLUMN_TENHANGMUC)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NGAY)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN)),
                    cursor.getBlob(cursor.getColumnIndex(COLUMN_HINH)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TEN_TAIKHOAN)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_TIENSUDUNG))
            ));
        }
        return arrNgansach;
    }
    public  int updateTienSuDung(String tenhangmuc,String tenvi,int sotiensudung,String ngay){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIENSUDUNG,sotiensudung);
        values.put(COLUMN_NGAY,ngay);
        return db.update(TABLE_NGANSACH,values,COLUMN_TENHANGMUC+"=? AND " +COLUMN_TEN_TAIKHOAN+"=?",new String[]{tenhangmuc,tenvi});

    }

    public int getTienSuDung(String tenhangmuc,String tenvi){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NGANSACH,new String[]{COLUMN_TIENSUDUNG},COLUMN_TENHANGMUC+"=? AND "+COLUMN_TEN_TAIKHOAN+"=?",new String[]{tenhangmuc,tenvi},null,null,null);
        int sotien =0 ;
        while (cursor.moveToNext()){
            sotien=  cursor.getInt(cursor.getColumnIndex(COLUMN_TIENSUDUNG));
        }
        return sotien;
    }

    public int getTongThu(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_THUNHAP,new String[]{COLUMN_SOTIEN},null,null,null,null,null);
        int sotien = 0;
        while (cursor.moveToNext()){
            sotien+= cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN));
        }
    return sotien;
    }
    public int getTongChi(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHITIEU,new String[]{COLUMN_SOTIEN},null,null,null,null,null);
        int sotien = 0;
        while (cursor.moveToNext()){
            sotien+= cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN));
        }
        return sotien;
    }

    public int checkNganSach(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NGANSACH,null,null,null,null,null,null);
        int count = cursor.getCount();
        return count;
    }

    public int getTienChiTheoHangMuc(String tenhangmuc,String tenvi){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHITIEU,new String[]{COLUMN_SOTIEN},COLUMN_TENHANGMUC+"=? AND "+COLUMN_TEN_TAIKHOAN+"=?",new String[]{tenhangmuc,tenvi},null,null,null);
        int tien =0;
        while (cursor.moveToNext()){
            tien+= cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN));
        }
        return tien;
    }
     public  int checkKehoach(String tenhangmuc,String ghichu){
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.query(TABLE_KEHOACH,new String[]{COLUMN_TENHANGMUC,COLUMN_GHICHU},COLUMN_TENHANGMUC+"=? AND " + COLUMN_GHICHU+"=?", new String[]{tenhangmuc,ghichu},null,null,null);
         int count = cursor.getCount();
         return count;
     }

     public int getTenNganSach(String tenhangmuc){
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.query(TABLE_NGANSACH,new String[]{COLUMN_TENHANGMUC},COLUMN_TENHANGMUC+"=?", new String[]{tenhangmuc},null,null,null);
         int count = cursor.getCount();
         return count;
     }

     public int updateSotienNganSach(String tenhangmuc,int sotien){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(COLUMN_SOTIEN,sotien);
         return db.update(TABLE_NGANSACH,values,COLUMN_TENHANGMUC+"=?",new String[]{tenhangmuc});
     }

     public int getSotienNganSach(String tenhangmuc){
         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cursor = db.query(TABLE_NGANSACH,new String[]{COLUMN_SOTIEN},COLUMN_TENHANGMUC+"=?",new String[]{tenhangmuc},null,null,null);
         int tien =0;
         while (cursor.moveToNext()){
             tien = cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN));
         }
         return tien;
     }

     public  void insertLichSu(ModelLichSu modelLichSu){
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(COLUMN_HINH,modelLichSu.getHinh());
         values.put(COLUMN_TENHANGMUC,modelLichSu.getTenhangmuc());
         values.put(COLUMN_TEN_TAIKHOAN,modelLichSu.getTentaikhoan());
         values.put(COLUMN_SOTIEN,modelLichSu.getSotien());
         values.put(COLUMN_GHICHU,modelLichSu.getGhichu());
         values.put(COLUMN_NGAY,modelLichSu.getNgay());
         values.put(COLUMN_LOAIHANGMUC,modelLichSu.getLoai());

         db.insert(TABLE_LICHSU,null,values);
         db.close();
     }

     public ArrayList<ModelLichSu> getLichSu(){
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.query(TABLE_LICHSU,null,null,null,null,null,null);
         ArrayList<ModelLichSu> arrLichSu = new ArrayList<>();
         while (cursor.moveToNext()){
             arrLichSu.add(new ModelLichSu(cursor.getString(cursor.getColumnIndex(COLUMN_TENHANGMUC)),
                     cursor.getString(cursor.getColumnIndex(COLUMN_NGAY)),
                     cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU)),
                     cursor.getString(cursor.getColumnIndex(COLUMN_TEN_TAIKHOAN)),
                     cursor.getString(cursor.getColumnIndex(COLUMN_LOAIHANGMUC)),
                     cursor.getBlob(cursor.getColumnIndex(COLUMN_HINH)),
                     cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN))
             ));
         }
         return arrLichSu;
     }
    public ArrayList<ModelLichSu> getLichSu(String tentaikhoan){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LICHSU,null,COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan},null,null,null);
        ArrayList<ModelLichSu> arrLichSu = new ArrayList<>();
        while (cursor.moveToNext()){
            arrLichSu.add(new ModelLichSu(cursor.getString(cursor.getColumnIndex(COLUMN_TENHANGMUC)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NGAY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GHICHU)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TEN_TAIKHOAN)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_LOAIHANGMUC)),
                    cursor.getBlob(cursor.getColumnIndex(COLUMN_HINH)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_SOTIEN))
            ));
        }
        return arrLichSu;
    }
    public String getLoaiLichSu(String tenhangmuc){
        String loai =null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LICHSU,null,COLUMN_TENHANGMUC+"=?",new String[]{tenhangmuc},null,null,null);
        while (cursor.moveToNext()){
            loai = cursor.getString(cursor.getColumnIndex(COLUMN_LOAIHANGMUC));
        }
        return loai;
    }

    public int deleteTk(String tentaikhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TAIKHOAN,COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan});

    }


    public int deleteChi(String tentaikhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CHITIEU,COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan});
    }

    public int deleteThu(String tentaikhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_THUNHAP,COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan});
    }

    public int deleteLichSu(String tentaikhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_LICHSU,COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan});
    }
    public int deleteNganSach(String tentaikhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NGANSACH,COLUMN_TEN_TAIKHOAN+"=?",new String[]{tentaikhoan});
    }



}

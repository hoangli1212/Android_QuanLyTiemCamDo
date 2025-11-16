package com.example.qltcd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.qltcd.database.DatabaseHelper;
import com.example.qltcd.models.TaiSanCam;
import java.util.ArrayList;
import java.util.List;

public class TaiSanCamDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public TaiSanCamDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Thêm tài sản cầm
    public long themTaiSan(TaiSanCam ts) {
        ContentValues values = new ContentValues();
        values.put("MaHD", ts.getMaHD());
        values.put("MaLoai", ts.getMaLoai());
        values.put("TenTS", ts.getTenTS());
        values.put("MoTa", ts.getMoTa());
        values.put("GiaTriDinhGia", ts.getGiaTriDinhGia());
        values.put("HinhAnh", ts.getHinhAnh());
        return db.insert(DatabaseHelper.TABLE_TAI_SAN, null, values);
    }

    // Cập nhật tài sản
    public int capNhatTaiSan(TaiSanCam ts) {
        ContentValues values = new ContentValues();
        values.put("MaHD", ts.getMaHD());
        values.put("MaLoai", ts.getMaLoai());
        values.put("TenTS", ts.getTenTS());
        values.put("MoTa", ts.getMoTa());
        values.put("GiaTriDinhGia", ts.getGiaTriDinhGia());
        values.put("HinhAnh", ts.getHinhAnh());
        return db.update(DatabaseHelper.TABLE_TAI_SAN, values,
                "MaTS = ?", new String[]{String.valueOf(ts.getMaTS())});
    }

    // Xóa tài sản
    public int xoaTaiSan(int maTS) {
        return db.delete(DatabaseHelper.TABLE_TAI_SAN,
                "MaTS = ?", new String[]{String.valueOf(maTS)});
    }

    // Lấy danh sách tất cả tài sản (có thông tin loại)
    public List<TaiSanCam> layDanhSachTaiSan() {
        List<TaiSanCam> danhSach = new ArrayList<>();
        String query = "SELECT ts.*, lt.TenLoai " +
                "FROM " + DatabaseHelper.TABLE_TAI_SAN + " ts " +
                "LEFT JOIN " + DatabaseHelper.TABLE_LOAI_TAI_SAN + " lt " +
                "ON ts.MaLoai = lt.MaLoai " +
                "ORDER BY ts.MaTS DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                TaiSanCam ts = new TaiSanCam();
                ts.setMaTS(cursor.getInt(cursor.getColumnIndexOrThrow("MaTS")));
                ts.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                ts.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow("MaLoai")));
                ts.setTenTS(cursor.getString(cursor.getColumnIndexOrThrow("TenTS")));
                ts.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MoTa")));
                ts.setGiaTriDinhGia(cursor.getDouble(cursor.getColumnIndexOrThrow("GiaTriDinhGia")));
                ts.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow("HinhAnh")));
                ts.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("TenLoai")));
                danhSach.add(ts);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Lấy danh sách tài sản theo hợp đồng
    public List<TaiSanCam> layTaiSanTheoHopDong(int maHD) {
        List<TaiSanCam> danhSach = new ArrayList<>();
        String query = "SELECT ts.*, lt.TenLoai " +
                "FROM " + DatabaseHelper.TABLE_TAI_SAN + " ts " +
                "LEFT JOIN " + DatabaseHelper.TABLE_LOAI_TAI_SAN + " lt " +
                "ON ts.MaLoai = lt.MaLoai " +
                "WHERE ts.MaHD = ? " +
                "ORDER BY ts.MaTS DESC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maHD)});

        if (cursor.moveToFirst()) {
            do {
                TaiSanCam ts = new TaiSanCam();
                ts.setMaTS(cursor.getInt(cursor.getColumnIndexOrThrow("MaTS")));
                ts.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                ts.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow("MaLoai")));
                ts.setTenTS(cursor.getString(cursor.getColumnIndexOrThrow("TenTS")));
                ts.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MoTa")));
                ts.setGiaTriDinhGia(cursor.getDouble(cursor.getColumnIndexOrThrow("GiaTriDinhGia")));
                ts.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow("HinhAnh")));
                ts.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("TenLoai")));
                danhSach.add(ts);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Lấy danh sách tài sản theo loại
    public List<TaiSanCam> layTaiSanTheoLoai(int maLoai) {
        List<TaiSanCam> danhSach = new ArrayList<>();
        String query = "SELECT ts.*, lt.TenLoai " +
                "FROM " + DatabaseHelper.TABLE_TAI_SAN + " ts " +
                "LEFT JOIN " + DatabaseHelper.TABLE_LOAI_TAI_SAN + " lt " +
                "ON ts.MaLoai = lt.MaLoai " +
                "WHERE ts.MaLoai = ? " +
                "ORDER BY ts.MaTS DESC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maLoai)});

        if (cursor.moveToFirst()) {
            do {
                TaiSanCam ts = new TaiSanCam();
                ts.setMaTS(cursor.getInt(cursor.getColumnIndexOrThrow("MaTS")));
                ts.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                ts.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow("MaLoai")));
                ts.setTenTS(cursor.getString(cursor.getColumnIndexOrThrow("TenTS")));
                ts.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MoTa")));
                ts.setGiaTriDinhGia(cursor.getDouble(cursor.getColumnIndexOrThrow("GiaTriDinhGia")));
                ts.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow("HinhAnh")));
                ts.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("TenLoai")));
                danhSach.add(ts);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Tìm tài sản theo mã
    public TaiSanCam timTaiSan(int maTS) {
        String query = "SELECT ts.*, lt.TenLoai " +
                "FROM " + DatabaseHelper.TABLE_TAI_SAN + " ts " +
                "LEFT JOIN " + DatabaseHelper.TABLE_LOAI_TAI_SAN + " lt " +
                "ON ts.MaLoai = lt.MaLoai " +
                "WHERE ts.MaTS = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maTS)});

        TaiSanCam ts = null;
        if (cursor.moveToFirst()) {
            ts = new TaiSanCam();
            ts.setMaTS(cursor.getInt(cursor.getColumnIndexOrThrow("MaTS")));
            ts.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
            ts.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow("MaLoai")));
            ts.setTenTS(cursor.getString(cursor.getColumnIndexOrThrow("TenTS")));
            ts.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MoTa")));
            ts.setGiaTriDinhGia(cursor.getDouble(cursor.getColumnIndexOrThrow("GiaTriDinhGia")));
            ts.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow("HinhAnh")));
            ts.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("TenLoai")));
        }
        cursor.close();
        return ts;
    }

    // Tìm kiếm tài sản theo tên
    public List<TaiSanCam> timKiemTaiSan(String keyword) {
        List<TaiSanCam> danhSach = new ArrayList<>();
        String query = "SELECT ts.*, lt.TenLoai " +
                "FROM " + DatabaseHelper.TABLE_TAI_SAN + " ts " +
                "LEFT JOIN " + DatabaseHelper.TABLE_LOAI_TAI_SAN + " lt " +
                "ON ts.MaLoai = lt.MaLoai " +
                "WHERE ts.TenTS LIKE ? OR ts.MoTa LIKE ? " +
                "ORDER BY ts.MaTS DESC";

        String searchPattern = "%" + keyword + "%";
        Cursor cursor = db.rawQuery(query, new String[]{searchPattern, searchPattern});

        if (cursor.moveToFirst()) {
            do {
                TaiSanCam ts = new TaiSanCam();
                ts.setMaTS(cursor.getInt(cursor.getColumnIndexOrThrow("MaTS")));
                ts.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                ts.setMaLoai(cursor.getInt(cursor.getColumnIndexOrThrow("MaLoai")));
                ts.setTenTS(cursor.getString(cursor.getColumnIndexOrThrow("TenTS")));
                ts.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MoTa")));
                ts.setGiaTriDinhGia(cursor.getDouble(cursor.getColumnIndexOrThrow("GiaTriDinhGia")));
                ts.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow("HinhAnh")));
                ts.setTenLoai(cursor.getString(cursor.getColumnIndexOrThrow("TenLoai")));
                danhSach.add(ts);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Tính tổng giá trị tài sản theo hợp đồng
    public double tinhTongGiaTriTheoHopDong(int maHD) {
        double tongGiaTri = 0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(GiaTriDinhGia) FROM " + DatabaseHelper.TABLE_TAI_SAN +
                        " WHERE MaHD = ?", new String[]{String.valueOf(maHD)});
        if (cursor.moveToFirst()) {
            tongGiaTri = cursor.getDouble(0);
        }
        cursor.close();
        return tongGiaTri;
    }

    // Đếm số tài sản theo hợp đồng
    public int demTaiSanTheoHopDong(int maHD) {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_TAI_SAN +
                        " WHERE MaHD = ?", new String[]{String.valueOf(maHD)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
}
package com.example.qltcd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.qltcd.database.DatabaseHelper;
import com.example.qltcd.models.KhachHang;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public KhachHangDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Thêm khách hàng mới
    public long themKhachHang(KhachHang kh) {
        ContentValues values = new ContentValues();
        values.put("HoTen", kh.getHoTen());
        values.put("CMND", kh.getCmnd());
        values.put("DienThoai", kh.getDienThoai());
        values.put("DiaChi", kh.getDiaChi());
        return db.insert(DatabaseHelper.TABLE_KHACH_HANG, null, values);
    }

    // Cập nhật khách hàng
    public int capNhatKhachHang(KhachHang kh) {
        ContentValues values = new ContentValues();
        values.put("HoTen", kh.getHoTen());
        values.put("CMND", kh.getCmnd());
        values.put("DienThoai", kh.getDienThoai());
        values.put("DiaChi", kh.getDiaChi());
        return db.update(DatabaseHelper.TABLE_KHACH_HANG, values,
                "MaKH = ?", new String[]{String.valueOf(kh.getMaKH())});
    }

    // Xóa khách hàng
    public int xoaKhachHang(int maKH) {
        return db.delete(DatabaseHelper.TABLE_KHACH_HANG,
                "MaKH = ?", new String[]{String.valueOf(maKH)});
    }

    // Lấy danh sách tất cả khách hàng
    public List<KhachHang> layDanhSachKhachHang() {
        List<KhachHang> danhSach = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_KHACH_HANG, null,
                null, null, null, null, "NgayTao DESC");

        if (cursor.moveToFirst()) {
            do {
                KhachHang kh = new KhachHang();
                kh.setMaKH(cursor.getInt(cursor.getColumnIndexOrThrow("MaKH")));
                kh.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
                kh.setCmnd(cursor.getString(cursor.getColumnIndexOrThrow("CMND")));
                kh.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("DienThoai")));
                kh.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow("DiaChi")));
                kh.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow("NgayTao")));
                danhSach.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Tìm khách hàng theo mã
    public KhachHang timKhachHang(int maKH) {
        Cursor cursor = db.query(DatabaseHelper.TABLE_KHACH_HANG, null,
                "MaKH = ?", new String[]{String.valueOf(maKH)},
                null, null, null);

        KhachHang kh = null;
        if (cursor.moveToFirst()) {
            kh = new KhachHang();
            kh.setMaKH(cursor.getInt(cursor.getColumnIndexOrThrow("MaKH")));
            kh.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
            kh.setCmnd(cursor.getString(cursor.getColumnIndexOrThrow("CMND")));
            kh.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("DienThoai")));
            kh.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow("DiaChi")));
            kh.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow("NgayTao")));
        }
        cursor.close();
        return kh;
    }

    // Tìm kiếm khách hàng theo tên hoặc CMND
    public List<KhachHang> timKiemKhachHang(String keyword) {
        List<KhachHang> danhSach = new ArrayList<>();
        String selection = "HoTen LIKE ? OR CMND LIKE ? OR DienThoai LIKE ?";
        String[] selectionArgs = {"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"};

        Cursor cursor = db.query(DatabaseHelper.TABLE_KHACH_HANG, null,
                selection, selectionArgs, null, null, "NgayTao DESC");

        if (cursor.moveToFirst()) {
            do {
                KhachHang kh = new KhachHang();
                kh.setMaKH(cursor.getInt(cursor.getColumnIndexOrThrow("MaKH")));
                kh.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
                kh.setCmnd(cursor.getString(cursor.getColumnIndexOrThrow("CMND")));
                kh.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("DienThoai")));
                kh.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow("DiaChi")));
                kh.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow("NgayTao")));
                danhSach.add(kh);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }
}
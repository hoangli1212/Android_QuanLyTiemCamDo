package com.example.qltcd.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.qltcd.database.DatabaseHelper;
import com.example.qltcd.models.ThanhToan;
import java.util.ArrayList;
import java.util.List;

public class ThanhToanDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public ThanhToanDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Thêm thanh toán mới
    public long themThanhToan(ThanhToan tt) {
        ContentValues values = new ContentValues();
        values.put("MaHD", tt.getMaHD());
        values.put("SoTienThanhToan", tt.getSoTienThanhToan());
        values.put("LoaiThanhToan", tt.getLoaiThanhToan());
        values.put("GhiChu", tt.getGhiChu());
        return db.insert(DatabaseHelper.TABLE_THANH_TOAN, null, values);
    }

    // Cập nhật thanh toán
    public int capNhatThanhToan(ThanhToan tt) {
        ContentValues values = new ContentValues();
        values.put("MaHD", tt.getMaHD());
        values.put("NgayThanhToan", tt.getNgayThanhToan());
        values.put("SoTienThanhToan", tt.getSoTienThanhToan());
        values.put("LoaiThanhToan", tt.getLoaiThanhToan());
        values.put("GhiChu", tt.getGhiChu());
        return db.update(DatabaseHelper.TABLE_THANH_TOAN, values,
                "MaTT = ?", new String[]{String.valueOf(tt.getMaTT())});
    }

    // Xóa thanh toán
    public int xoaThanhToan(int maTT) {
        return db.delete(DatabaseHelper.TABLE_THANH_TOAN,
                "MaTT = ?", new String[]{String.valueOf(maTT)});
    }

    // Lấy danh sách tất cả thanh toán (có thông tin khách hàng)
    public List<ThanhToan> layDanhSachThanhToan() {
        List<ThanhToan> danhSach = new ArrayList<>();
        String query = "SELECT tt.*, kh.HoTen " +
                "FROM " + DatabaseHelper.TABLE_THANH_TOAN + " tt " +
                "LEFT JOIN " + DatabaseHelper.TABLE_HOP_DONG + " hd ON tt.MaHD = hd.MaHD " +
                "LEFT JOIN " + DatabaseHelper.TABLE_KHACH_HANG + " kh ON hd.MaKH = kh.MaKH " +
                "ORDER BY tt.NgayThanhToan DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                ThanhToan tt = new ThanhToan();
                tt.setMaTT(cursor.getInt(cursor.getColumnIndexOrThrow("MaTT")));
                tt.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                tt.setNgayThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("NgayThanhToan")));
                tt.setSoTienThanhToan(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienThanhToan")));
                tt.setLoaiThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("LoaiThanhToan")));
                tt.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
                tt.setTenKhachHang(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
                danhSach.add(tt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Lấy danh sách thanh toán theo hợp đồng
    public List<ThanhToan> layThanhToanTheoHopDong(int maHD) {
        List<ThanhToan> danhSach = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_THANH_TOAN, null,
                "MaHD = ?", new String[]{String.valueOf(maHD)},
                null, null, "NgayThanhToan DESC");

        if (cursor.moveToFirst()) {
            do {
                ThanhToan tt = new ThanhToan();
                tt.setMaTT(cursor.getInt(cursor.getColumnIndexOrThrow("MaTT")));
                tt.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                tt.setNgayThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("NgayThanhToan")));
                tt.setSoTienThanhToan(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienThanhToan")));
                tt.setLoaiThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("LoaiThanhToan")));
                tt.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
                danhSach.add(tt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Lấy danh sách thanh toán theo loại
    public List<ThanhToan> layThanhToanTheoLoai(String loaiThanhToan) {
        List<ThanhToan> danhSach = new ArrayList<>();
        String query = "SELECT tt.*, kh.HoTen " +
                "FROM " + DatabaseHelper.TABLE_THANH_TOAN + " tt " +
                "LEFT JOIN " + DatabaseHelper.TABLE_HOP_DONG + " hd ON tt.MaHD = hd.MaHD " +
                "LEFT JOIN " + DatabaseHelper.TABLE_KHACH_HANG + " kh ON hd.MaKH = kh.MaKH " +
                "WHERE tt.LoaiThanhToan = ? " +
                "ORDER BY tt.NgayThanhToan DESC";

        Cursor cursor = db.rawQuery(query, new String[]{loaiThanhToan});

        if (cursor.moveToFirst()) {
            do {
                ThanhToan tt = new ThanhToan();
                tt.setMaTT(cursor.getInt(cursor.getColumnIndexOrThrow("MaTT")));
                tt.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                tt.setNgayThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("NgayThanhToan")));
                tt.setSoTienThanhToan(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienThanhToan")));
                tt.setLoaiThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("LoaiThanhToan")));
                tt.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
                tt.setTenKhachHang(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
                danhSach.add(tt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Tìm thanh toán theo mã
    public ThanhToan timThanhToan(int maTT) {
        String query = "SELECT tt.*, kh.HoTen " +
                "FROM " + DatabaseHelper.TABLE_THANH_TOAN + " tt " +
                "LEFT JOIN " + DatabaseHelper.TABLE_HOP_DONG + " hd ON tt.MaHD = hd.MaHD " +
                "LEFT JOIN " + DatabaseHelper.TABLE_KHACH_HANG + " kh ON hd.MaKH = kh.MaKH " +
                "WHERE tt.MaTT = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maTT)});

        ThanhToan tt = null;
        if (cursor.moveToFirst()) {
            tt = new ThanhToan();
            tt.setMaTT(cursor.getInt(cursor.getColumnIndexOrThrow("MaTT")));
            tt.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
            tt.setNgayThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("NgayThanhToan")));
            tt.setSoTienThanhToan(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienThanhToan")));
            tt.setLoaiThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("LoaiThanhToan")));
            tt.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
            tt.setTenKhachHang(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
        }
        cursor.close();
        return tt;
    }

    // Lấy danh sách thanh toán trong khoảng thời gian
    public List<ThanhToan> layThanhToanTheoKhoangThoiGian(String tuNgay, String denNgay) {
        List<ThanhToan> danhSach = new ArrayList<>();
        String query = "SELECT tt.*, kh.HoTen " +
                "FROM " + DatabaseHelper.TABLE_THANH_TOAN + " tt " +
                "LEFT JOIN " + DatabaseHelper.TABLE_HOP_DONG + " hd ON tt.MaHD = hd.MaHD " +
                "LEFT JOIN " + DatabaseHelper.TABLE_KHACH_HANG + " kh ON hd.MaKH = kh.MaKH " +
                "WHERE DATE(tt.NgayThanhToan) BETWEEN ? AND ? " +
                "ORDER BY tt.NgayThanhToan DESC";

        Cursor cursor = db.rawQuery(query, new String[]{tuNgay, denNgay});

        if (cursor.moveToFirst()) {
            do {
                ThanhToan tt = new ThanhToan();
                tt.setMaTT(cursor.getInt(cursor.getColumnIndexOrThrow("MaTT")));
                tt.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                tt.setNgayThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("NgayThanhToan")));
                tt.setSoTienThanhToan(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienThanhToan")));
                tt.setLoaiThanhToan(cursor.getString(cursor.getColumnIndexOrThrow("LoaiThanhToan")));
                tt.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
                tt.setTenKhachHang(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
                danhSach.add(tt);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Tính tổng tiền đã thanh toán theo hợp đồng
    public double tinhTongThanhToanTheoHopDong(int maHD) {
        double tongTien = 0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(SoTienThanhToan) FROM " + DatabaseHelper.TABLE_THANH_TOAN +
                        " WHERE MaHD = ?", new String[]{String.valueOf(maHD)});
        if (cursor.moveToFirst()) {
            tongTien = cursor.getDouble(0);
        }
        cursor.close();
        return tongTien;
    }

    // Tính tổng doanh thu theo khoảng thời gian
    public double tinhTongDoanhThu(String tuNgay, String denNgay) {
        double tongTien = 0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(SoTienThanhToan) FROM " + DatabaseHelper.TABLE_THANH_TOAN +
                        " WHERE DATE(NgayThanhToan) BETWEEN ? AND ?",
                new String[]{tuNgay, denNgay});
        if (cursor.moveToFirst()) {
            tongTien = cursor.getDouble(0);
        }
        cursor.close();
        return tongTien;
    }

    // Tính tổng doanh thu theo loại thanh toán trong khoảng thời gian
    public double tinhTongDoanhThuTheoLoai(String loaiThanhToan, String tuNgay, String denNgay) {
        double tongTien = 0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(SoTienThanhToan) FROM " + DatabaseHelper.TABLE_THANH_TOAN +
                        " WHERE LoaiThanhToan = ? AND DATE(NgayThanhToan) BETWEEN ? AND ?",
                new String[]{loaiThanhToan, tuNgay, denNgay});
        if (cursor.moveToFirst()) {
            tongTien = cursor.getDouble(0);
        }
        cursor.close();
        return tongTien;
    }

    // Đếm số lần thanh toán theo hợp đồng
    public int demSoLanThanhToan(int maHD) {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_THANH_TOAN +
                        " WHERE MaHD = ?", new String[]{String.valueOf(maHD)});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
}
package com.example.qltcd.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.qltcd.database.DatabaseHelper;
import com.example.qltcd.models.HopDong;
import java.util.ArrayList;
import java.util.List;

public class HopDongDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public HopDongDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Thêm hợp đồng mới
    public long themHopDong(HopDong hd) {
        ContentValues values = new ContentValues();
        values.put("MaKH", hd.getMaKH());
        values.put("NgayCam", hd.getNgayCam());
        values.put("NgayDaoHan", hd.getNgayDaoHan());
        values.put("SoTienCho", hd.getSoTienCho());
        values.put("LaiSuat", hd.getLaiSuat());
        values.put("TrangThai", hd.getTrangThai());
        values.put("GhiChu", hd.getGhiChu());
        return db.insert(DatabaseHelper.TABLE_HOP_DONG, null, values);
    }

    // Cập nhật hợp đồng
    public int capNhatHopDong(HopDong hd) {
        ContentValues values = new ContentValues();
        values.put("MaKH", hd.getMaKH());
        values.put("NgayCam", hd.getNgayCam());
        values.put("NgayDaoHan", hd.getNgayDaoHan());
        values.put("SoTienCho", hd.getSoTienCho());
        values.put("LaiSuat", hd.getLaiSuat());
        values.put("TrangThai", hd.getTrangThai());
        values.put("GhiChu", hd.getGhiChu());
        return db.update(DatabaseHelper.TABLE_HOP_DONG, values,
                "MaHD = ?", new String[]{String.valueOf(hd.getMaHD())});
    }

    // Cập nhật trạng thái hợp đồng
    public int capNhatTrangThai(int maHD, String trangThai) {
        ContentValues values = new ContentValues();
        values.put("TrangThai", trangThai);
        return db.update(DatabaseHelper.TABLE_HOP_DONG, values,
                "MaHD = ?", new String[]{String.valueOf(maHD)});
    }

    // Xóa hợp đồng
    public int xoaHopDong(int maHD) {
        return db.delete(DatabaseHelper.TABLE_HOP_DONG,
                "MaHD = ?", new String[]{String.valueOf(maHD)});
    }

    // Lấy danh sách tất cả hợp đồng (có thông tin khách hàng)
    public List<HopDong> layDanhSachHopDong() {
        List<HopDong> danhSach = new ArrayList<>();
        String query = "SELECT hd.*, kh.HoTen " +
                "FROM " + DatabaseHelper.TABLE_HOP_DONG + " hd " +
                "LEFT JOIN " + DatabaseHelper.TABLE_KHACH_HANG + " kh " +
                "ON hd.MaKH = kh.MaKH " +
                "ORDER BY hd.NgayCam DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                HopDong hd = new HopDong();
                hd.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                hd.setMaKH(cursor.getInt(cursor.getColumnIndexOrThrow("MaKH")));
                hd.setNgayCam(cursor.getString(cursor.getColumnIndexOrThrow("NgayCam")));
                hd.setNgayDaoHan(cursor.getString(cursor.getColumnIndexOrThrow("NgayDaoHan")));
                hd.setSoTienCho(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienCho")));
                hd.setLaiSuat(cursor.getDouble(cursor.getColumnIndexOrThrow("LaiSuat")));
                hd.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("TrangThai")));
                hd.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
                hd.setTenKhachHang(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
                danhSach.add(hd);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Lấy danh sách hợp đồng theo khách hàng
    public List<HopDong> layHopDongTheoKhachHang(int maKH) {
        List<HopDong> danhSach = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_HOP_DONG, null,
                "MaKH = ?", new String[]{String.valueOf(maKH)},
                null, null, "NgayCam DESC");

        if (cursor.moveToFirst()) {
            do {
                HopDong hd = new HopDong();
                hd.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                hd.setMaKH(cursor.getInt(cursor.getColumnIndexOrThrow("MaKH")));
                hd.setNgayCam(cursor.getString(cursor.getColumnIndexOrThrow("NgayCam")));
                hd.setNgayDaoHan(cursor.getString(cursor.getColumnIndexOrThrow("NgayDaoHan")));
                hd.setSoTienCho(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienCho")));
                hd.setLaiSuat(cursor.getDouble(cursor.getColumnIndexOrThrow("LaiSuat")));
                hd.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("TrangThai")));
                hd.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
                danhSach.add(hd);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Lấy danh sách hợp đồng theo trạng thái
    public List<HopDong> layHopDongTheoTrangThai(String trangThai) {
        List<HopDong> danhSach = new ArrayList<>();
        String query = "SELECT hd.*, kh.HoTen " +
                "FROM " + DatabaseHelper.TABLE_HOP_DONG + " hd " +
                "LEFT JOIN " + DatabaseHelper.TABLE_KHACH_HANG + " kh " +
                "ON hd.MaKH = kh.MaKH " +
                "WHERE hd.TrangThai = ? " +
                "ORDER BY hd.NgayCam DESC";

        Cursor cursor = db.rawQuery(query, new String[]{trangThai});

        if (cursor.moveToFirst()) {
            do {
                HopDong hd = new HopDong();
                hd.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                hd.setMaKH(cursor.getInt(cursor.getColumnIndexOrThrow("MaKH")));
                hd.setNgayCam(cursor.getString(cursor.getColumnIndexOrThrow("NgayCam")));
                hd.setNgayDaoHan(cursor.getString(cursor.getColumnIndexOrThrow("NgayDaoHan")));
                hd.setSoTienCho(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienCho")));
                hd.setLaiSuat(cursor.getDouble(cursor.getColumnIndexOrThrow("LaiSuat")));
                hd.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("TrangThai")));
                hd.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
                hd.setTenKhachHang(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
                danhSach.add(hd);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Tìm hợp đồng theo mã
    public HopDong timHopDong(int maHD) {
        String query = "SELECT hd.*, kh.HoTen " +
                "FROM " + DatabaseHelper.TABLE_HOP_DONG + " hd " +
                "LEFT JOIN " + DatabaseHelper.TABLE_KHACH_HANG + " kh " +
                "ON hd.MaKH = kh.MaKH " +
                "WHERE hd.MaHD = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maHD)});

        HopDong hd = null;
        if (cursor.moveToFirst()) {
            hd = new HopDong();
            hd.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
            hd.setMaKH(cursor.getInt(cursor.getColumnIndexOrThrow("MaKH")));
            hd.setNgayCam(cursor.getString(cursor.getColumnIndexOrThrow("NgayCam")));
            hd.setNgayDaoHan(cursor.getString(cursor.getColumnIndexOrThrow("NgayDaoHan")));
            hd.setSoTienCho(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienCho")));
            hd.setLaiSuat(cursor.getDouble(cursor.getColumnIndexOrThrow("LaiSuat")));
            hd.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("TrangThai")));
            hd.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
            hd.setTenKhachHang(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
        }
        cursor.close();
        return hd;
    }

    // Lấy danh sách hợp đồng sắp đáo hạn (trong vòng N ngày)
    public List<HopDong> layHopDongSapDaoHan(int soNgay) {
        List<HopDong> danhSach = new ArrayList<>();
        String query = "SELECT hd.*, kh.HoTen " +
                "FROM " + DatabaseHelper.TABLE_HOP_DONG + " hd " +
                "LEFT JOIN " + DatabaseHelper.TABLE_KHACH_HANG + " kh " +
                "ON hd.MaKH = kh.MaKH " +
                "WHERE hd.TrangThai = 'Đang cầm' " +
                "AND julianday(hd.NgayDaoHan) - julianday('now') <= ? " +
                "AND julianday(hd.NgayDaoHan) - julianday('now') >= 0 " +
                "ORDER BY hd.NgayDaoHan ASC";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(soNgay)});

        if (cursor.moveToFirst()) {
            do {
                HopDong hd = new HopDong();
                hd.setMaHD(cursor.getInt(cursor.getColumnIndexOrThrow("MaHD")));
                hd.setMaKH(cursor.getInt(cursor.getColumnIndexOrThrow("MaKH")));
                hd.setNgayCam(cursor.getString(cursor.getColumnIndexOrThrow("NgayCam")));
                hd.setNgayDaoHan(cursor.getString(cursor.getColumnIndexOrThrow("NgayDaoHan")));
                hd.setSoTienCho(cursor.getDouble(cursor.getColumnIndexOrThrow("SoTienCho")));
                hd.setLaiSuat(cursor.getDouble(cursor.getColumnIndexOrThrow("LaiSuat")));
                hd.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("TrangThai")));
                hd.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GhiChu")));
                hd.setTenKhachHang(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
                danhSach.add(hd);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return danhSach;
    }

    // Đếm số hợp đồng theo trạng thái
    public int demHopDongTheoTrangThai(String trangThai) {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_HOP_DONG +
                        " WHERE TrangThai = ?", new String[]{trangThai});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
}
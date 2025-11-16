package com.example.qltcd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuanLyCamDo.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    public static final String TABLE_KHACH_HANG = "KhachHang";
    public static final String TABLE_HOP_DONG = "HopDong";
    public static final String TABLE_TAI_SAN = "TaiSanCam";
    public static final String TABLE_LOAI_TAI_SAN = "LoaiTaiSan";
    public static final String TABLE_THANH_TOAN = "ThanhToan";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Khách hàng
        String CREATE_KHACH_HANG = "CREATE TABLE " + TABLE_KHACH_HANG + "("
                + "MaKH INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "HoTen TEXT NOT NULL,"
                + "CMND TEXT UNIQUE NOT NULL,"
                + "DienThoai TEXT,"
                + "DiaChi TEXT,"
                + "NgayTao DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_KHACH_HANG);

        // Tạo bảng Loại tài sản
        String CREATE_LOAI_TAI_SAN = "CREATE TABLE " + TABLE_LOAI_TAI_SAN + "("
                + "MaLoai INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "TenLoai TEXT NOT NULL,"
                + "MoTa TEXT"
                + ")";
        db.execSQL(CREATE_LOAI_TAI_SAN);

        // Tạo bảng Hợp đồng
        String CREATE_HOP_DONG = "CREATE TABLE " + TABLE_HOP_DONG + "("
                + "MaHD INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "MaKH INTEGER NOT NULL,"
                + "NgayCam DATE NOT NULL,"
                + "NgayDaoHan DATE NOT NULL,"
                + "SoTienCho REAL NOT NULL,"
                + "LaiSuat REAL NOT NULL,"
                + "TrangThai TEXT DEFAULT 'Đang cầm',"
                + "GhiChu TEXT,"
                + "FOREIGN KEY (MaKH) REFERENCES " + TABLE_KHACH_HANG + "(MaKH)"
                + ")";
        db.execSQL(CREATE_HOP_DONG);

        // Tạo bảng Tài sản cầm
        String CREATE_TAI_SAN = "CREATE TABLE " + TABLE_TAI_SAN + "("
                + "MaTS INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "MaHD INTEGER NOT NULL,"
                + "MaLoai INTEGER,"
                + "TenTS TEXT NOT NULL,"
                + "MoTa TEXT,"
                + "GiaTriDinhGia REAL NOT NULL,"
                + "HinhAnh TEXT,"
                + "FOREIGN KEY (MaHD) REFERENCES " + TABLE_HOP_DONG + "(MaHD),"
                + "FOREIGN KEY (MaLoai) REFERENCES " + TABLE_LOAI_TAI_SAN + "(MaLoai)"
                + ")";
        db.execSQL(CREATE_TAI_SAN);

        // Tạo bảng Thanh toán
        String CREATE_THANH_TOAN = "CREATE TABLE " + TABLE_THANH_TOAN + "("
                + "MaTT INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "MaHD INTEGER NOT NULL,"
                + "NgayThanhToan DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "SoTienThanhToan REAL NOT NULL,"
                + "LoaiThanhToan TEXT,"
                + "GhiChu TEXT,"
                + "FOREIGN KEY (MaHD) REFERENCES " + TABLE_HOP_DONG + "(MaHD)"
                + ")";
        db.execSQL(CREATE_THANH_TOAN);

        // Thêm dữ liệu mẫu
        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        // Thêm loại tài sản mẫu
        db.execSQL("INSERT INTO " + TABLE_LOAI_TAI_SAN + " (TenLoai, MoTa) VALUES ('Vàng bạc', 'Vàng, bạc, trang sức')");
        db.execSQL("INSERT INTO " + TABLE_LOAI_TAI_SAN + " (TenLoai, MoTa) VALUES ('Điện tử', 'Điện thoại, laptop, máy tính bảng')");
        db.execSQL("INSERT INTO " + TABLE_LOAI_TAI_SAN + " (TenLoai, MoTa) VALUES ('Xe máy', 'Xe máy các loại')");
        db.execSQL("INSERT INTO " + TABLE_LOAI_TAI_SAN + " (TenLoai, MoTa) VALUES ('Giấy tờ có giá', 'Sổ đỏ, giấy tờ nhà đất')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THANH_TOAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAI_SAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOP_DONG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAI_TAI_SAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KHACH_HANG);
        onCreate(db);
    }
}
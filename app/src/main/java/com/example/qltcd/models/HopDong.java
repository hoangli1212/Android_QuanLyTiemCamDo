package com.example.qltcd.models;

public class HopDong {
    private int maHD;
    private int maKH;
    private String ngayCam;
    private String ngayDaoHan;
    private double soTienCho;
    private double laiSuat;
    private String trangThai; // "Đang cầm", "Đã chuộc", "Quá hạn"
    private String ghiChu;

    // Thông tin liên kết (không lưu trong DB)
    private String tenKhachHang;

    // Constructor mặc định
    public HopDong() {
        this.trangThai = "Đang cầm";
    }

    // Constructor đầy đủ
    public HopDong(int maHD, int maKH, String ngayCam, String ngayDaoHan,
                   double soTienCho, double laiSuat, String trangThai, String ghiChu) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.ngayCam = ngayCam;
        this.ngayDaoHan = ngayDaoHan;
        this.soTienCho = soTienCho;
        this.laiSuat = laiSuat;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Constructor không có mã
    public HopDong(int maKH, String ngayCam, String ngayDaoHan,
                   double soTienCho, double laiSuat, String ghiChu) {
        this.maKH = maKH;
        this.ngayCam = ngayCam;
        this.ngayDaoHan = ngayDaoHan;
        this.soTienCho = soTienCho;
        this.laiSuat = laiSuat;
        this.ghiChu = ghiChu;
        this.trangThai = "Đang cầm";
    }

    // Getter và Setter
    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getNgayCam() {
        return ngayCam;
    }

    public void setNgayCam(String ngayCam) {
        this.ngayCam = ngayCam;
    }

    public String getNgayDaoHan() {
        return ngayDaoHan;
    }

    public void setNgayDaoHan(String ngayDaoHan) {
        this.ngayDaoHan = ngayDaoHan;
    }

    public double getSoTienCho() {
        return soTienCho;
    }

    public void setSoTienCho(double soTienCho) {
        this.soTienCho = soTienCho;
    }

    public double getLaiSuat() {
        return laiSuat;
    }

    public void setLaiSuat(double laiSuat) {
        this.laiSuat = laiSuat;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    @Override
    public String toString() {
        return "HopDong{" +
                "maHD=" + maHD +
                ", maKH=" + maKH +
                ", ngayCam='" + ngayCam + '\'' +
                ", ngayDaoHan='" + ngayDaoHan + '\'' +
                ", soTienCho=" + soTienCho +
                ", laiSuat=" + laiSuat +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
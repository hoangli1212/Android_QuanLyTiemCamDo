package com.example.qltcd.models;

public class ThanhToan {
    private int maTT;
    private int maHD;
    private String ngayThanhToan;
    private double soTienThanhToan;
    private String loaiThanhToan; // "Trả gốc", "Trả lãi", "Trả gốc + lãi", "Gia hạn"
    private String ghiChu;

    // Thông tin liên kết (không lưu trong DB)
    private String tenKhachHang;

    // Constructor mặc định
    public ThanhToan() {}

    // Constructor đầy đủ
    public ThanhToan(int maTT, int maHD, String ngayThanhToan,
                     double soTienThanhToan, String loaiThanhToan, String ghiChu) {
        this.maTT = maTT;
        this.maHD = maHD;
        this.ngayThanhToan = ngayThanhToan;
        this.soTienThanhToan = soTienThanhToan;
        this.loaiThanhToan = loaiThanhToan;
        this.ghiChu = ghiChu;
    }

    // Constructor không có mã
    public ThanhToan(int maHD, double soTienThanhToan, String loaiThanhToan, String ghiChu) {
        this.maHD = maHD;
        this.soTienThanhToan = soTienThanhToan;
        this.loaiThanhToan = loaiThanhToan;
        this.ghiChu = ghiChu;
    }

    // Getter và Setter
    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public double getSoTienThanhToan() {
        return soTienThanhToan;
    }

    public void setSoTienThanhToan(double soTienThanhToan) {
        this.soTienThanhToan = soTienThanhToan;
    }

    public String getLoaiThanhToan() {
        return loaiThanhToan;
    }

    public void setLoaiThanhToan(String loaiThanhToan) {
        this.loaiThanhToan = loaiThanhToan;
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
        return "ThanhToan{" +
                "maTT=" + maTT +
                ", maHD=" + maHD +
                ", ngayThanhToan='" + ngayThanhToan + '\'' +
                ", soTienThanhToan=" + soTienThanhToan +
                ", loaiThanhToan='" + loaiThanhToan + '\'' +
                '}';
    }
}
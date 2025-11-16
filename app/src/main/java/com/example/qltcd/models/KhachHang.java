package com.example.qltcd.models;

public class KhachHang {
    private int maKH;
    private String hoTen;
    private String cmnd;
    private String dienThoai;
    private String diaChi;
    private String ngayTao;

    // Constructor mặc định
    public KhachHang() {}

    // Constructor đầy đủ
    public KhachHang(int maKH, String hoTen, String cmnd, String dienThoai, String diaChi, String ngayTao) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.cmnd = cmnd;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
        this.ngayTao = ngayTao;
    }

    // Constructor không có mã (dùng khi thêm mới)
    public KhachHang(String hoTen, String cmnd, String dienThoai, String diaChi) {
        this.hoTen = hoTen;
        this.cmnd = cmnd;
        this.dienThoai = dienThoai;
        this.diaChi = diaChi;
    }

    // Getter và Setter
    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "maKH=" + maKH +
                ", hoTen='" + hoTen + '\'' +
                ", cmnd='" + cmnd + '\'' +
                ", dienThoai='" + dienThoai + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", ngayTao='" + ngayTao + '\'' +
                '}';
    }
}
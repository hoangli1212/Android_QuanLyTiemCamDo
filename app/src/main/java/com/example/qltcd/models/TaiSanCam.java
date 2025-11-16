package com.example.qltcd.models;

public class TaiSanCam {
    private int maTS;
    private int maHD;
    private int maLoai;
    private String tenTS;
    private String moTa;
    private double giaTriDinhGia;
    private String hinhAnh; // Đường dẫn file ảnh

    // Thông tin liên kết (không lưu trong DB)
    private String tenLoai;

    // Constructor mặc định
    public TaiSanCam() {}

    // Constructor đầy đủ
    public TaiSanCam(int maTS, int maHD, int maLoai, String tenTS,
                     String moTa, double giaTriDinhGia, String hinhAnh) {
        this.maTS = maTS;
        this.maHD = maHD;
        this.maLoai = maLoai;
        this.tenTS = tenTS;
        this.moTa = moTa;
        this.giaTriDinhGia = giaTriDinhGia;
        this.hinhAnh = hinhAnh;
    }

    // Constructor không có mã
    public TaiSanCam(int maHD, int maLoai, String tenTS,
                     String moTa, double giaTriDinhGia, String hinhAnh) {
        this.maHD = maHD;
        this.maLoai = maLoai;
        this.tenTS = tenTS;
        this.moTa = moTa;
        this.giaTriDinhGia = giaTriDinhGia;
        this.hinhAnh = hinhAnh;
    }

    // Getter và Setter
    public int getMaTS() {
        return maTS;
    }

    public void setMaTS(int maTS) {
        this.maTS = maTS;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenTS() {
        return tenTS;
    }

    public void setTenTS(String tenTS) {
        this.tenTS = tenTS;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGiaTriDinhGia() {
        return giaTriDinhGia;
    }

    public void setGiaTriDinhGia(double giaTriDinhGia) {
        this.giaTriDinhGia = giaTriDinhGia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return "TaiSanCam{" +
                "maTS=" + maTS +
                ", tenTS='" + tenTS + '\'' +
                ", giaTriDinhGia=" + giaTriDinhGia +
                '}';
    }
}
package com.example.qltcd.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import com.example.qltcd.R;
import com.example.qltcd.dao.HopDongDAO;
import com.example.qltcd.dao.ThanhToanDAO;
import com.example.qltcd.models.HopDong;
import com.example.qltcd.models.ThanhToan;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ThemThanhToanActivity extends AppCompatActivity {
    private Spinner spinnerHopDong, spinnerLoaiThanhToan;
    private EditText edtSoTienThanhToan, edtGhiChu;
    private Button btnTinhToan, btnLuu, btnHuy;
    private CardView cardThongTinHD;
    private TextView tvThongTinKH, tvSoTienGoc, tvLaiSuat, tvTienLai, tvTongTien;

    private ThanhToanDAO thanhToanDAO;
    private HopDongDAO hopDongDAO;
    private List<HopDong> danhSachHopDong;
    private HopDong hopDongDaChon;
    private DecimalFormat formatter;

    private double tongTienPhaiTra = 0;
    private double tienLaiDuTinh = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_thanh_toan);

        initViews();
        initDAO();
        setupSpinners();
        setupListeners();

        formatter = new DecimalFormat("#,###");
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinnerHopDong = findViewById(R.id.spinnerHopDong);
        spinnerLoaiThanhToan = findViewById(R.id.spinnerLoaiThanhToan);
        edtSoTienThanhToan = findViewById(R.id.edtSoTienThanhToan);
        edtGhiChu = findViewById(R.id.edtGhiChu);
        btnTinhToan = findViewById(R.id.btnTinhToan);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        cardThongTinHD = findViewById(R.id.cardThongTinHD);
        tvThongTinKH = findViewById(R.id.tvThongTinKH);
        tvSoTienGoc = findViewById(R.id.tvSoTienGoc);
        tvLaiSuat = findViewById(R.id.tvLaiSuat);
        tvTienLai = findViewById(R.id.tvTienLai);
        tvTongTien = findViewById(R.id.tvTongTien);
    }

    private void initDAO() {
        thanhToanDAO = new ThanhToanDAO(this);
        thanhToanDAO.open();
        hopDongDAO = new HopDongDAO(this);
        hopDongDAO.open();
    }

    private void setupSpinners() {
        // Spinner hợp đồng - chỉ lấy hợp đồng "Đang cầm"
        danhSachHopDong = hopDongDAO.layHopDongTheoTrangThai("Đang cầm");
        List<String> tenHopDong = new ArrayList<>();
        tenHopDong.add("-- Chọn hợp đồng --");
        for (HopDong hd : danhSachHopDong) {
            tenHopDong.add("HĐ #" + hd.getMaHD() + " - " + hd.getTenKhachHang());
        }
        ArrayAdapter<String> adapterHD = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tenHopDong);
        adapterHD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHopDong.setAdapter(adapterHD);

        // Spinner loại thanh toán
        String[] loaiThanhToan = {"Trả gốc + lãi", "Trả gốc", "Trả lãi", "Gia hạn"};
        ArrayAdapter<String> adapterLoai = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, loaiThanhToan);
        adapterLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiThanhToan.setAdapter(adapterLoai);
    }

    private void setupListeners() {
        btnLuu.setOnClickListener(v -> luuThanhToan());
        btnHuy.setOnClickListener(v -> finish());
        btnTinhToan.setOnClickListener(v -> tinhToanTuDong());

        // Khi chọn hợp đồng
        spinnerHopDong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    hopDongDaChon = danhSachHopDong.get(position - 1);
                    hienThiThongTinHopDong();
                } else {
                    hopDongDaChon = null;
                    cardThongTinHD.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hopDongDaChon = null;
                cardThongTinHD.setVisibility(View.GONE);
            }
        });
    }

    private void hienThiThongTinHopDong() {
        if (hopDongDaChon == null) return;

        cardThongTinHD.setVisibility(View.VISIBLE);

        // Tính số tháng
        long soNgay = tinhSoNgay(hopDongDaChon.getNgayCam());
        double soThang = soNgay / 30.0;

        // Tính tiền lãi
        tienLaiDuTinh = hopDongDaChon.getSoTienCho() * (hopDongDaChon.getLaiSuat() / 100) * soThang;
        tongTienPhaiTra = hopDongDaChon.getSoTienCho() + tienLaiDuTinh;

        // Tính tổng đã thanh toán
        double daThanhToan = thanhToanDAO.tinhTongThanhToanTheoHopDong(hopDongDaChon.getMaHD());

        // Hiển thị
        tvThongTinKH.setText("Khách hàng: " + hopDongDaChon.getTenKhachHang());
        tvSoTienGoc.setText("Tiền gốc: " + formatter.format(hopDongDaChon.getSoTienCho()) + " đ");
        tvLaiSuat.setText("Lãi suất: " + hopDongDaChon.getLaiSuat() + "%/tháng (" +
                String.format("%.1f", soThang) + " tháng)");
        tvTienLai.setText("Tiền lãi dự tính: " + formatter.format(tienLaiDuTinh) + " đ" +
                "\nĐã thanh toán: " + formatter.format(daThanhToan) + " đ" +
                "\nCòn lại: " + formatter.format(tongTienPhaiTra - daThanhToan) + " đ");
        tvTongTien.setText("Tổng phải trả: " + formatter.format(tongTienPhaiTra) + " đ");
    }

    private void tinhToanTuDong() {
        if (hopDongDaChon == null) {
            Toast.makeText(this, "Vui lòng chọn hợp đồng", Toast.LENGTH_SHORT).show();
            return;
        }

        String loaiThanhToan = spinnerLoaiThanhToan.getSelectedItem().toString();

        long soNgay = tinhSoNgay(hopDongDaChon.getNgayCam());
        double soThang = soNgay / 30.0;
        double tienLai = hopDongDaChon.getSoTienCho() * (hopDongDaChon.getLaiSuat() / 100) * soThang;

        // Tính tổng đã thanh toán
        double daThanhToan = thanhToanDAO.tinhTongThanhToanTheoHopDong(hopDongDaChon.getMaHD());

        double soTien = 0;
        switch (loaiThanhToan) {
            case "Trả gốc + lãi":
                // Tổng cần trả trừ đi số đã trả
                soTien = (hopDongDaChon.getSoTienCho() + tienLai) - daThanhToan;
                break;
            case "Trả gốc":
                soTien = hopDongDaChon.getSoTienCho();
                break;
            case "Trả lãi":
                soTien = tienLai;
                break;
            case "Gia hạn":
                soTien = tienLai;
                break;
        }

        if (soTien < 0) soTien = 0;

        edtSoTienThanhToan.setText(String.valueOf((long)soTien));
        Toast.makeText(this, "Đã tính toán: " + formatter.format(soTien) + " đ", Toast.LENGTH_SHORT).show();
    }

    private long tinhSoNgay(String ngayCam) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date ngayBatDau = sdf.parse(ngayCam);
            Date ngayHienTai = new Date();
            long diffInMillies = Math.abs(ngayHienTai.getTime() - ngayBatDau.getTime());
            return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            return 0;
        }
    }

    private void luuThanhToan() {
        // Validate
        if (spinnerHopDong.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Vui lòng chọn hợp đồng", Toast.LENGTH_SHORT).show();
            return;
        }

        String soTienStr = edtSoTienThanhToan.getText().toString().trim();

        if (TextUtils.isEmpty(soTienStr)) {
            edtSoTienThanhToan.setError("Vui lòng nhập số tiền");
            edtSoTienThanhToan.requestFocus();
            return;
        }

        double soTien = Double.parseDouble(soTienStr);

        if (soTien <= 0) {
            edtSoTienThanhToan.setError("Số tiền phải lớn hơn 0");
            edtSoTienThanhToan.requestFocus();
            return;
        }

        String loaiThanhToan = spinnerLoaiThanhToan.getSelectedItem().toString();
        String ghiChu = edtGhiChu.getText().toString().trim();

        // Hiển thị dialog xác nhận
        showConfirmDialog(soTien, loaiThanhToan, ghiChu);
    }

    private void showConfirmDialog(double soTien, String loaiThanhToan, String ghiChu) {
        // Tính tổng đã thanh toán
        double daThanhToan = thanhToanDAO.tinhTongThanhToanTheoHopDong(hopDongDaChon.getMaHD());
        double sauKhiThanhToan = daThanhToan + soTien;
        double conLai = tongTienPhaiTra - sauKhiThanhToan;

        String message = "Xác nhận thanh toán?\n\n" +
                "Hợp đồng: #" + hopDongDaChon.getMaHD() + "\n" +
                "Khách hàng: " + hopDongDaChon.getTenKhachHang() + "\n" +
                "Loại: " + loaiThanhToan + "\n" +
                "Số tiền thanh toán: " + formatter.format(soTien) + " đ\n\n" +
                "Tổng phải trả: " + formatter.format(tongTienPhaiTra) + " đ\n" +
                "Đã thanh toán: " + formatter.format(daThanhToan) + " đ\n" +
                "Sau thanh toán này: " + formatter.format(sauKhiThanhToan) + " đ\n" +
                "Còn lại: " + formatter.format(conLai) + " đ";

        // Kiểm tra xem có đủ tiền để chuộc không
        boolean daChuocDu = sauKhiThanhToan >= tongTienPhaiTra;

        if (loaiThanhToan.equals("Trả gốc + lãi") && daChuocDu) {
            message += "\n\n✓ Đã thanh toán đủ! Hợp đồng sẽ chuyển sang 'Đã chuộc'";
        } else if (loaiThanhToan.equals("Trả gốc + lãi") && !daChuocDu) {
            message += "\n\n⚠️ Chưa thanh toán đủ! Hợp đồng vẫn ở trạng thái 'Đang cầm'";
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thanh toán")
                .setMessage(message)
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    xuLyThanhToan(soTien, loaiThanhToan, ghiChu, daChuocDu);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void xuLyThanhToan(double soTien, String loaiThanhToan, String ghiChu, boolean daChuocDu) {
        // Tạo thanh toán
        ThanhToan thanhToan = new ThanhToan();
        thanhToan.setMaHD(hopDongDaChon.getMaHD());
        thanhToan.setSoTienThanhToan(soTien);
        thanhToan.setLoaiThanhToan(loaiThanhToan);
        thanhToan.setGhiChu(ghiChu);

        long result = thanhToanDAO.themThanhToan(thanhToan);

        if (result > 0) {
            // Chỉ cập nhật trạng thái hợp đồng nếu:
            // 1. Loại thanh toán là "Trả gốc + lãi"
            // 2. Đã thanh toán đủ số tiền
            if (loaiThanhToan.equals("Trả gốc + lãi") && daChuocDu) {
                int updateResult = hopDongDAO.capNhatTrangThai(hopDongDaChon.getMaHD(), "Đã chuộc");
                if (updateResult > 0) {
                    Toast.makeText(this, "Thanh toán thành công! Hợp đồng đã được chuộc.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Thanh toán thành công nhưng không cập nhật được trạng thái hợp đồng", Toast.LENGTH_LONG).show();
                }
            } else if (loaiThanhToan.equals("Trả gốc + lãi") && !daChuocDu) {
                Toast.makeText(this, "Thanh toán thành công! Chưa đủ để chuộc, hợp đồng vẫn đang cầm.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
            }
            finish();
        } else {
            Toast.makeText(this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (thanhToanDAO != null) {
            thanhToanDAO.close();
        }
        if (hopDongDAO != null) {
            hopDongDAO.close();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
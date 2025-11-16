package com.example.qltcd.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.qltcd.R;
import com.example.qltcd.dao.HopDongDAO;
import com.example.qltcd.dao.KhachHangDAO;
import com.example.qltcd.models.HopDong;
import com.example.qltcd.models.KhachHang;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ThemHopDongActivity extends AppCompatActivity {
    private Spinner spinnerKhachHang, spinnerTrangThai;
    private EditText edtNgayCam, edtNgayDaoHan, edtSoTienCho, edtLaiSuat, edtGhiChu;
    private Button btnChonNgayCam, btnChonNgayDaoHan, btnLuu, btnHuy;

    private HopDongDAO hopDongDAO;
    private KhachHangDAO khachHangDAO;
    private List<KhachHang> danhSachKhachHang;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    private boolean isEdit = false;
    private int maHD = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hop_dong);

        initViews();
        initDAO();
        setupSpinners();
        checkEditMode();
        setupListeners();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinnerKhachHang = findViewById(R.id.spinnerKhachHang);
        spinnerTrangThai = findViewById(R.id.spinnerTrangThai);
        edtNgayCam = findViewById(R.id.edtNgayCam);
        edtNgayDaoHan = findViewById(R.id.edtNgayDaoHan);
        edtSoTienCho = findViewById(R.id.edtSoTienCho);
        edtLaiSuat = findViewById(R.id.edtLaiSuat);
        edtGhiChu = findViewById(R.id.edtGhiChu);
        btnChonNgayCam = findViewById(R.id.btnChonNgayCam);
        btnChonNgayDaoHan = findViewById(R.id.btnChonNgayDaoHan);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    private void initDAO() {
        hopDongDAO = new HopDongDAO(this);
        hopDongDAO.open();
        khachHangDAO = new KhachHangDAO(this);
        khachHangDAO.open();
    }

    private void setupSpinners() {
        // Spinner khách hàng
        danhSachKhachHang = khachHangDAO.layDanhSachKhachHang();
        List<String> tenKhachHang = new ArrayList<>();
        for (KhachHang kh : danhSachKhachHang) {
            tenKhachHang.add(kh.getHoTen() + " - " + kh.getCmnd());
        }
        ArrayAdapter<String> adapterKH = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, tenKhachHang);
        adapterKH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKhachHang.setAdapter(adapterKH);

        // Spinner trạng thái
        String[] trangThai = {"Đang cầm", "Đã chuộc", "Quá hạn"};
        ArrayAdapter<String> adapterTT = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, trangThai);
        adapterTT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrangThai.setAdapter(adapterTT);
    }

    private void checkEditMode() {
        if (getIntent().hasExtra("MA_HD")) {
            isEdit = true;
            maHD = getIntent().getIntExtra("MA_HD", -1);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Chỉnh Sửa Hợp Đồng");
            }

            // Load dữ liệu
            int maKH = getIntent().getIntExtra("MA_KH", -1);
            edtNgayCam.setText(getIntent().getStringExtra("NGAY_CAM"));
            edtNgayDaoHan.setText(getIntent().getStringExtra("NGAY_DAO_HAN"));
            edtSoTienCho.setText(String.valueOf(getIntent().getDoubleExtra("SO_TIEN_CHO", 0)));
            edtLaiSuat.setText(String.valueOf(getIntent().getDoubleExtra("LAI_SUAT", 0)));
            edtGhiChu.setText(getIntent().getStringExtra("GHI_CHU"));

            // Set spinner khách hàng
            for (int i = 0; i < danhSachKhachHang.size(); i++) {
                if (danhSachKhachHang.get(i).getMaKH() == maKH) {
                    spinnerKhachHang.setSelection(i);
                    break;
                }
            }

            // Set spinner trạng thái
            String trangThai = getIntent().getStringExtra("TRANG_THAI");
            String[] trangThaiArray = {"Đang cầm", "Đã chuộc", "Quá hạn"};
            for (int i = 0; i < trangThaiArray.length; i++) {
                if (trangThaiArray[i].equals(trangThai)) {
                    spinnerTrangThai.setSelection(i);
                    break;
                }
            }
        } else {
            // Set ngày mặc định cho hợp đồng mới
            edtNgayCam.setText(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.MONTH, 3); // Đáo hạn sau 3 tháng
            edtNgayDaoHan.setText(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.MONTH, -3); // Reset lại
        }
    }

    private void setupListeners() {
        btnLuu.setOnClickListener(v -> luuHopDong());
        btnHuy.setOnClickListener(v -> finish());

        // Date picker cho ngày cầm
        btnChonNgayCam.setOnClickListener(v -> showDatePicker(edtNgayCam));
        edtNgayCam.setOnClickListener(v -> showDatePicker(edtNgayCam));

        // Date picker cho ngày đáo hạn
        btnChonNgayDaoHan.setOnClickListener(v -> showDatePicker(edtNgayDaoHan));
        edtNgayDaoHan.setOnClickListener(v -> showDatePicker(edtNgayDaoHan));
    }

    private void showDatePicker(EditText editText) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    cal.set(year, month, dayOfMonth);
                    editText.setText(dateFormat.format(cal.getTime()));
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void luuHopDong() {
        // Validate
        if (spinnerKhachHang.getSelectedItemPosition() == -1) {
            Toast.makeText(this, "Vui lòng chọn khách hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        String ngayCam = edtNgayCam.getText().toString().trim();
        String ngayDaoHan = edtNgayDaoHan.getText().toString().trim();
        String soTienStr = edtSoTienCho.getText().toString().trim();
        String laiSuatStr = edtLaiSuat.getText().toString().trim();
        String ghiChu = edtGhiChu.getText().toString().trim();

        if (TextUtils.isEmpty(ngayCam)) {
            Toast.makeText(this, "Vui lòng chọn ngày cầm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(ngayDaoHan)) {
            Toast.makeText(this, "Vui lòng chọn ngày đáo hạn", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(soTienStr)) {
            edtSoTienCho.setError("Vui lòng nhập số tiền");
            edtSoTienCho.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(laiSuatStr)) {
            edtLaiSuat.setError("Vui lòng nhập lãi suất");
            edtLaiSuat.requestFocus();
            return;
        }

        double soTienCho = Double.parseDouble(soTienStr);
        double laiSuat = Double.parseDouble(laiSuatStr);

        if (soTienCho <= 0) {
            edtSoTienCho.setError("Số tiền phải lớn hơn 0");
            edtSoTienCho.requestFocus();
            return;
        }

        if (laiSuat <= 0) {
            edtLaiSuat.setError("Lãi suất phải lớn hơn 0");
            edtLaiSuat.requestFocus();
            return;
        }

        // Lấy thông tin
        int maKH = danhSachKhachHang.get(spinnerKhachHang.getSelectedItemPosition()).getMaKH();
        String trangThai = spinnerTrangThai.getSelectedItem().toString();

        // Tạo object
        HopDong hopDong = new HopDong();
        hopDong.setMaKH(maKH);
        hopDong.setNgayCam(ngayCam);
        hopDong.setNgayDaoHan(ngayDaoHan);
        hopDong.setSoTienCho(soTienCho);
        hopDong.setLaiSuat(laiSuat);
        hopDong.setTrangThai(trangThai);
        hopDong.setGhiChu(ghiChu);

        // Lưu hoặc cập nhật
        if (isEdit) {
            hopDong.setMaHD(maHD);
            int result = hopDongDAO.capNhatHopDong(hopDong);
            if (result > 0) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        } else {
            long result = hopDongDAO.themHopDong(hopDong);
            if (result > 0) {
                Toast.makeText(this, "Tạo hợp đồng thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Tạo hợp đồng thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hopDongDAO != null) {
            hopDongDAO.close();
        }
        if (khachHangDAO != null) {
            khachHangDAO.close();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
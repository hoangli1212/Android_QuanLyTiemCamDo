package com.example.qltcd.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.qltcd.R;
import com.example.qltcd.dao.KhachHangDAO;
import com.example.qltcd.models.KhachHang;
import com.google.android.material.textfield.TextInputEditText;

public class ThemKhachHangActivity extends AppCompatActivity {
    private TextInputEditText edtHoTen, edtCMND, edtDienThoai, edtDiaChi;
    private Button btnLuu, btnHuy;

    private KhachHangDAO khachHangDAO;
    private boolean isEdit = false;
    private int maKH = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);

        initViews();
        initDAO();
        checkEditMode();
        setupListeners();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtHoTen = findViewById(R.id.edtHoTen);
        edtCMND = findViewById(R.id.edtCMND);
        edtDienThoai = findViewById(R.id.edtDienThoai);
        edtDiaChi = findViewById(R.id.edtDiaChi);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
    }

    private void initDAO() {
        khachHangDAO = new KhachHangDAO(this);
        khachHangDAO.open();
    }

    private void checkEditMode() {
        // Kiểm tra có dữ liệu từ Intent không (chế độ sửa)
        if (getIntent().hasExtra("MA_KH")) {
            isEdit = true;
            maKH = getIntent().getIntExtra("MA_KH", -1);

            // Đổi title
            getSupportActionBar().setTitle("Chỉnh Sửa Khách Hàng");

            // Load dữ liệu
            edtHoTen.setText(getIntent().getStringExtra("HO_TEN"));
            edtCMND.setText(getIntent().getStringExtra("CMND"));
            edtDienThoai.setText(getIntent().getStringExtra("DIEN_THOAI"));
            edtDiaChi.setText(getIntent().getStringExtra("DIA_CHI"));

            // Disable CMND khi sửa (không cho đổi CMND)
            edtCMND.setEnabled(false);
        }
    }

    private void setupListeners() {
        btnLuu.setOnClickListener(v -> luuKhachHang());
        btnHuy.setOnClickListener(v -> finish());

        // Xử lý nút back trên toolbar
    }

    private void luuKhachHang() {
        // Lấy dữ liệu
        String hoTen = edtHoTen.getText().toString().trim();
        String cmnd = edtCMND.getText().toString().trim();
        String dienThoai = edtDienThoai.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();

        // Validate
        if (TextUtils.isEmpty(hoTen)) {
            edtHoTen.setError("Vui lòng nhập họ tên");
            edtHoTen.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(cmnd)) {
            edtCMND.setError("Vui lòng nhập CMND/CCCD");
            edtCMND.requestFocus();
            return;
        }

        if (cmnd.length() < 9 || cmnd.length() > 12) {
            edtCMND.setError("CMND/CCCD phải từ 9-12 số");
            edtCMND.requestFocus();
            return;
        }

        if (!TextUtils.isEmpty(dienThoai) && dienThoai.length() < 10) {
            edtDienThoai.setError("Số điện thoại không hợp lệ");
            edtDienThoai.requestFocus();
            return;
        }

        // Tạo object
        KhachHang khachHang = new KhachHang();
        khachHang.setHoTen(hoTen);
        khachHang.setCmnd(cmnd);
        khachHang.setDienThoai(dienThoai);
        khachHang.setDiaChi(diaChi);

        // Lưu hoặc cập nhật
        if (isEdit) {
            khachHang.setMaKH(maKH);
            int result = khachHangDAO.capNhatKhachHang(khachHang);
            if (result > 0) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        } else {
            long result = khachHangDAO.themKhachHang(khachHang);
            if (result > 0) {
                Toast.makeText(this, "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Thêm thất bại. CMND có thể đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
package com.example.qltcd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qltcd.R;
import com.example.qltcd.adapters.KhachHangAdapter;
import com.example.qltcd.dao.KhachHangDAO;
import com.example.qltcd.models.KhachHang;
import java.util.ArrayList;
import java.util.List;

public class KhachHangActivity extends AppCompatActivity {
    private RecyclerView rvKhachHang;
    private TextView tvEmpty;
    private EditText edtTimKiem;
    private Button btnTimKiem, btnThem;

    private KhachHangAdapter adapter;
    private KhachHangDAO khachHangDAO;
    private List<KhachHang> danhSachKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        initViews();
        initDAO();
        setupRecyclerView();
        loadData();
        setupListeners();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvKhachHang = findViewById(R.id.rvKhachHang);
        tvEmpty = findViewById(R.id.tvEmpty);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        btnTimKiem = findViewById(R.id.btnTimKiem);
        btnThem = findViewById(R.id.btnThem);
    }

    private void initDAO() {
        khachHangDAO = new KhachHangDAO(this);
        khachHangDAO.open();
    }

    private void setupRecyclerView() {
        danhSachKhachHang = new ArrayList<>();
        adapter = new KhachHangAdapter(this, danhSachKhachHang);

        rvKhachHang.setLayoutManager(new LinearLayoutManager(this));
        rvKhachHang.setAdapter(adapter);

        // Xử lý sự kiện adapter
        adapter.setOnItemClickListener(new KhachHangAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(KhachHang khachHang) {
                showDetailDialog(khachHang);
            }

            @Override
            public void onEditClick(KhachHang khachHang) {
                editKhachHang(khachHang);
            }

            @Override
            public void onDeleteClick(KhachHang khachHang) {
                showDeleteDialog(khachHang);
            }
        });
    }

    private void setupListeners() {
        // Nút thêm mới
        btnThem.setOnClickListener(v -> {
            Intent intent = new Intent(KhachHangActivity.this, ThemKhachHangActivity.class);
            startActivity(intent);
        });

        // Nút tìm kiếm
        btnTimKiem.setOnClickListener(v -> timKiem());
    }

    private void loadData() {
        danhSachKhachHang = khachHangDAO.layDanhSachKhachHang();
        adapter.updateData(danhSachKhachHang);

        // Hiển thị empty view nếu không có dữ liệu
        if (danhSachKhachHang.isEmpty()) {
            rvKhachHang.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            rvKhachHang.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    private void timKiem() {
        String keyword = edtTimKiem.getText().toString().trim();

        if (TextUtils.isEmpty(keyword)) {
            loadData();
            return;
        }

        List<KhachHang> ketQua = khachHangDAO.timKiemKhachHang(keyword);
        adapter.updateData(ketQua);

        if (ketQua.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
        }
    }

    private void editKhachHang(KhachHang khachHang) {
        Intent intent = new Intent(this, ThemKhachHangActivity.class);
        intent.putExtra("MA_KH", khachHang.getMaKH());
        intent.putExtra("HO_TEN", khachHang.getHoTen());
        intent.putExtra("CMND", khachHang.getCmnd());
        intent.putExtra("DIEN_THOAI", khachHang.getDienThoai());
        intent.putExtra("DIA_CHI", khachHang.getDiaChi());
        startActivity(intent);
    }

    private void showDeleteDialog(KhachHang khachHang) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa khách hàng " + khachHang.getHoTen() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    int result = khachHangDAO.xoaKhachHang(khachHang.getMaKH());
                    if (result > 0) {
                        Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    } else {
                        Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showDetailDialog(KhachHang khachHang) {
        String info = "Họ tên: " + khachHang.getHoTen() + "\n\n" +
                "CMND: " + khachHang.getCmnd() + "\n\n" +
                "Điện thoại: " + (khachHang.getDienThoai() != null ? khachHang.getDienThoai() : "Chưa có") + "\n\n" +
                "Địa chỉ: " + (khachHang.getDiaChi() != null ? khachHang.getDiaChi() : "Chưa có") + "\n\n" +
                "Ngày tạo: " + (khachHang.getNgayTao() != null ? khachHang.getNgayTao() : "");

        new AlertDialog.Builder(this)
                .setTitle("Thông tin khách hàng")
                .setMessage(info)
                .setPositiveButton("Đóng", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
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
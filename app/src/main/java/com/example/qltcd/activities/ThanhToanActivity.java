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
import com.example.qltcd.adapters.ThanhToanAdapter;
import com.example.qltcd.dao.ThanhToanDAO;
import com.example.qltcd.models.ThanhToan;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ThanhToanActivity extends AppCompatActivity {
    private RecyclerView rvThanhToan;
    private TextView tvEmpty;
    private EditText edtTimKiem;
    private Button btnTimKiem, btnThem;

    private ThanhToanAdapter adapter;
    private ThanhToanDAO thanhToanDAO;
    private List<ThanhToan> danhSachThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

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

        rvThanhToan = findViewById(R.id.rvThanhToan);
        tvEmpty = findViewById(R.id.tvEmpty);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        btnTimKiem = findViewById(R.id.btnTimKiem);
        btnThem = findViewById(R.id.btnThem);
    }

    private void initDAO() {
        thanhToanDAO = new ThanhToanDAO(this);
        thanhToanDAO.open();
    }

    private void setupRecyclerView() {
        danhSachThanhToan = new ArrayList<>();
        adapter = new ThanhToanAdapter(this, danhSachThanhToan);

        rvThanhToan.setLayoutManager(new LinearLayoutManager(this));
        rvThanhToan.setAdapter(adapter);

        // Xử lý sự kiện adapter
        adapter.setOnItemClickListener(new ThanhToanAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(ThanhToan thanhToan) {
                showDetailDialog(thanhToan);
            }

            @Override
            public void onDeleteClick(ThanhToan thanhToan) {
                showDeleteDialog(thanhToan);
            }
        });
    }

    private void setupListeners() {
        // Nút thêm mới
        btnThem.setOnClickListener(v -> {
            Intent intent = new Intent(ThanhToanActivity.this, ThemThanhToanActivity.class);
            startActivity(intent);
        });

        // Nút tìm kiếm
        btnTimKiem.setOnClickListener(v -> timKiem());
    }

    private void loadData() {
        danhSachThanhToan = thanhToanDAO.layDanhSachThanhToan();
        adapter.updateData(danhSachThanhToan);

        // Hiển thị empty view nếu không có dữ liệu
        if (danhSachThanhToan.isEmpty()) {
            rvThanhToan.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            rvThanhToan.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    private void timKiem() {
        String keyword = edtTimKiem.getText().toString().trim();

        if (TextUtils.isEmpty(keyword)) {
            loadData();
            return;
        }

        try {
            int maHD = Integer.parseInt(keyword);
            List<ThanhToan> ketQua = thanhToanDAO.layThanhToanTheoHopDong(maHD);
            adapter.updateData(ketQua);

            if (ketQua.isEmpty()) {
                Toast.makeText(this, "Không tìm thấy thanh toán nào", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập số hợp đồng hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog(ThanhToan thanhToan) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa thanh toán #" + thanhToan.getMaTT() + "?\n\nLưu ý: Trạng thái hợp đồng sẽ không được khôi phục.")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    int result = thanhToanDAO.xoaThanhToan(thanhToan.getMaTT());
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

    private void showDetailDialog(ThanhToan thanhToan) {
        DecimalFormat formatter = new DecimalFormat("#,###");

        String info = "Mã thanh toán: #" + thanhToan.getMaTT() + "\n\n" +
                "Hợp đồng: #" + thanhToan.getMaHD() + "\n\n" +
                "Khách hàng: " + (thanhToan.getTenKhachHang() != null ? thanhToan.getTenKhachHang() : "Không rõ") + "\n\n" +
                "Loại thanh toán: " + thanhToan.getLoaiThanhToan() + "\n\n" +
                "Số tiền: " + formatter.format(thanhToan.getSoTienThanhToan()) + " đ\n\n" +
                "Ngày thanh toán: " + thanhToan.getNgayThanhToan() + "\n\n" +
                "Ghi chú: " + (thanhToan.getGhiChu() != null ? thanhToan.getGhiChu() : "Không có");

        new AlertDialog.Builder(this)
                .setTitle("Chi tiết thanh toán")
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
        if (thanhToanDAO != null) {
            thanhToanDAO.close();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
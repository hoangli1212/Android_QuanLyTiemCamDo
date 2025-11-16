package com.example.qltcd.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qltcd.R;
import com.example.qltcd.adapters.HopDongAdapter;
import com.example.qltcd.dao.HopDongDAO;
import com.example.qltcd.models.HopDong;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HopDongActivity extends AppCompatActivity {
    private RecyclerView rvHopDong;
    private TextView tvEmpty;
    private Button btnThem, btnTatCa, btnDangCam, btnDaChuoc, btnQuaHan;

    private HopDongAdapter adapter;
    private HopDongDAO hopDongDAO;
    private List<HopDong> danhSachHopDong;
    private String trangThaiFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hop_dong);

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

        rvHopDong = findViewById(R.id.rvHopDong);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnThem = findViewById(R.id.btnThem);
        btnTatCa = findViewById(R.id.btnTatCa);
        btnDangCam = findViewById(R.id.btnDangCam);
        btnDaChuoc = findViewById(R.id.btnDaChuoc);
        btnQuaHan = findViewById(R.id.btnQuaHan);
    }

    private void initDAO() {
        hopDongDAO = new HopDongDAO(this);
        hopDongDAO.open();
    }

    private void setupRecyclerView() {
        danhSachHopDong = new ArrayList<>();
        adapter = new HopDongAdapter(this, danhSachHopDong);

        rvHopDong.setLayoutManager(new LinearLayoutManager(this));
        rvHopDong.setAdapter(adapter);

        // Xử lý sự kiện adapter
        adapter.setOnItemClickListener(new HopDongAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(HopDong hopDong) {
                showDetailDialog(hopDong);
            }

            @Override
            public void onEditClick(HopDong hopDong) {
                editHopDong(hopDong);
            }

            @Override
            public void onDeleteClick(HopDong hopDong) {
                showDeleteDialog(hopDong);
            }
        });
    }

    private void setupListeners() {
        // Nút thêm mới
        btnThem.setOnClickListener(v -> {
            Intent intent = new Intent(HopDongActivity.this, ThemHopDongActivity.class);
            startActivity(intent);
        });

        // Filter buttons
        btnTatCa.setOnClickListener(v -> {
            trangThaiFilter = null;
            loadData();
        });

        btnDangCam.setOnClickListener(v -> {
            trangThaiFilter = "Đang cầm";
            filterByTrangThai();
        });

        btnDaChuoc.setOnClickListener(v -> {
            trangThaiFilter = "Đã chuộc";
            filterByTrangThai();
        });

        btnQuaHan.setOnClickListener(v -> {
            trangThaiFilter = "Quá hạn";
            filterByTrangThai();
        });
    }

    private void loadData() {
        danhSachHopDong = hopDongDAO.layDanhSachHopDong();
        adapter.updateData(danhSachHopDong);

        // Hiển thị empty view nếu không có dữ liệu
        if (danhSachHopDong.isEmpty()) {
            rvHopDong.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            rvHopDong.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    private void filterByTrangThai() {
        if (trangThaiFilter == null) {
            loadData();
            return;
        }

        List<HopDong> filtered = hopDongDAO.layHopDongTheoTrangThai(trangThaiFilter);
        adapter.updateData(filtered);

        if (filtered.isEmpty()) {
            rvHopDong.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("Không có hợp đồng " + trangThaiFilter);
        } else {
            rvHopDong.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }
    }

    private void editHopDong(HopDong hopDong) {
        Intent intent = new Intent(this, ThemHopDongActivity.class);
        intent.putExtra("MA_HD", hopDong.getMaHD());
        intent.putExtra("MA_KH", hopDong.getMaKH());
        intent.putExtra("NGAY_CAM", hopDong.getNgayCam());
        intent.putExtra("NGAY_DAO_HAN", hopDong.getNgayDaoHan());
        intent.putExtra("SO_TIEN_CHO", hopDong.getSoTienCho());
        intent.putExtra("LAI_SUAT", hopDong.getLaiSuat());
        intent.putExtra("TRANG_THAI", hopDong.getTrangThai());
        intent.putExtra("GHI_CHU", hopDong.getGhiChu());
        startActivity(intent);
    }

    private void showDeleteDialog(HopDong hopDong) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa hợp đồng #" + hopDong.getMaHD() + "?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    int result = hopDongDAO.xoaHopDong(hopDong.getMaHD());
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

    private void showDetailDialog(HopDong hopDong) {
        DecimalFormat formatter = new DecimalFormat("#,###");

        String info = "Mã hợp đồng: #" + hopDong.getMaHD() + "\n\n" +
                "Khách hàng: " + (hopDong.getTenKhachHang() != null ? hopDong.getTenKhachHang() : "Không rõ") + "\n\n" +
                "Số tiền cho: " + formatter.format(hopDong.getSoTienCho()) + " đ\n\n" +
                "Lãi suất: " + hopDong.getLaiSuat() + "%/tháng\n\n" +
                "Ngày cầm: " + hopDong.getNgayCam() + "\n\n" +
                "Ngày đáo hạn: " + hopDong.getNgayDaoHan() + "\n\n" +
                "Trạng thái: " + hopDong.getTrangThai() + "\n\n" +
                "Ghi chú: " + (hopDong.getGhiChu() != null ? hopDong.getGhiChu() : "Không có");

        new AlertDialog.Builder(this)
                .setTitle("Chi tiết hợp đồng")
                .setMessage(info)
                .setPositiveButton("Đóng", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (trangThaiFilter == null) {
            loadData();
        } else {
            filterByTrangThai();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
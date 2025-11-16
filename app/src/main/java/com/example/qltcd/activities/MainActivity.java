package com.example.qltcd.activities;
import com.example.qltcd.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnKhachHang, btnHopDong, btnTaiSan, btnThanhToan, btnThongKe, btnCaiDat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các button
        btnKhachHang = findViewById(R.id.btnKhachHang);
        btnHopDong = findViewById(R.id.btnHopDong);
        btnTaiSan = findViewById(R.id.btnTaiSan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnCaiDat = findViewById(R.id.btnCaiDat);

        // Xử lý sự kiện click
        btnKhachHang.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, KhachHangActivity.class);
            startActivity(intent);
        });

        btnHopDong.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HopDongActivity.class);
            startActivity(intent);
        });
        btnThanhToan.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThanhToanActivity.class);
            startActivity(intent);
        });

        // Tương tự cho các button khác...
    }
}
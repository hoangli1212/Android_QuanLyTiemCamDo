package com.example.qltcd.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qltcd.R;
import com.example.qltcd.models.HopDong;
import java.text.DecimalFormat;
import java.util.List;

public class HopDongAdapter extends RecyclerView.Adapter<HopDongAdapter.ViewHolder> {
    private Context context;
    private List<HopDong> danhSach;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onViewClick(HopDong hopDong);
        void onEditClick(HopDong hopDong);
        void onDeleteClick(HopDong hopDong);
    }

    public HopDongAdapter(Context context, List<HopDong> danhSach) {
        this.context = context;
        this.danhSach = danhSach;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hop_dong, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HopDong hd = danhSach.get(position);

        // Format số tiền
        DecimalFormat formatter = new DecimalFormat("#,###");

        // Hiển thị thông tin
        holder.tvMaHD.setText("HĐ #" + hd.getMaHD());
        holder.tvKhachHang.setText(hd.getTenKhachHang() != null ? hd.getTenKhachHang() : "Không rõ");
        holder.tvSoTien.setText(formatter.format(hd.getSoTienCho()) + " đ");
        holder.tvLaiSuat.setText(hd.getLaiSuat() + "%/tháng");
        holder.tvNgayCam.setText(hd.getNgayCam());
        holder.tvNgayDaoHan.setText(hd.getNgayDaoHan());

        // Trạng thái với màu sắc
        holder.tvTrangThai.setText(hd.getTrangThai());
        switch (hd.getTrangThai()) {
            case "Đang cầm":
                holder.tvTrangThai.setBackgroundColor(Color.parseColor("#4CAF50"));
                break;
            case "Đã chuộc":
                holder.tvTrangThai.setBackgroundColor(Color.parseColor("#2196F3"));
                break;
            case "Quá hạn":
                holder.tvTrangThai.setBackgroundColor(Color.parseColor("#F44336"));
                break;
            default:
                holder.tvTrangThai.setBackgroundColor(Color.parseColor("#9E9E9E"));
                break;
        }

        // Xử lý sự kiện các nút
        holder.btnXem.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewClick(hd);
            }
        });

        holder.btnSua.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(hd);
            }
        });

        holder.btnXoa.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(hd);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSach.size();
    }

    // Cập nhật danh sách
    public void updateData(List<HopDong> newList) {
        this.danhSach = newList;
        notifyDataSetChanged();
    }

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHD, tvKhachHang, tvSoTien, tvLaiSuat, tvNgayCam, tvNgayDaoHan, tvTrangThai;
        Button btnXem, btnSua, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHD = itemView.findViewById(R.id.tvMaHD);
            tvKhachHang = itemView.findViewById(R.id.tvKhachHang);
            tvSoTien = itemView.findViewById(R.id.tvSoTien);
            tvLaiSuat = itemView.findViewById(R.id.tvLaiSuat);
            tvNgayCam = itemView.findViewById(R.id.tvNgayCam);
            tvNgayDaoHan = itemView.findViewById(R.id.tvNgayDaoHan);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            btnXem = itemView.findViewById(R.id.btnXem);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
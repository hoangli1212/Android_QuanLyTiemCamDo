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
import com.example.qltcd.models.ThanhToan;
import java.text.DecimalFormat;
import java.util.List;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.ViewHolder> {
    private Context context;
    private List<ThanhToan> danhSach;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onViewClick(ThanhToan thanhToan);
        void onDeleteClick(ThanhToan thanhToan);
    }

    public ThanhToanAdapter(Context context, List<ThanhToan> danhSach) {
        this.context = context;
        this.danhSach = danhSach;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanh_toan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhToan tt = danhSach.get(position);

        // Format số tiền
        DecimalFormat formatter = new DecimalFormat("#,###");

        // Hiển thị thông tin
        holder.tvMaTT.setText("TT #" + tt.getMaTT());
        holder.tvMaHD.setText("#" + tt.getMaHD());
        holder.tvKhachHang.setText(tt.getTenKhachHang() != null ? tt.getTenKhachHang() : "Không rõ");
        holder.tvSoTien.setText(formatter.format(tt.getSoTienThanhToan()) + " đ");
        holder.tvNgayThanhToan.setText(tt.getNgayThanhToan());

        // Loại thanh toán với màu sắc
        holder.tvLoaiThanhToan.setText(tt.getLoaiThanhToan());
        switch (tt.getLoaiThanhToan()) {
            case "Trả gốc + lãi":
                holder.tvLoaiThanhToan.setBackgroundColor(Color.parseColor("#4CAF50"));
                break;
            case "Trả gốc":
                holder.tvLoaiThanhToan.setBackgroundColor(Color.parseColor("#2196F3"));
                break;
            case "Trả lãi":
                holder.tvLoaiThanhToan.setBackgroundColor(Color.parseColor("#FF9800"));
                break;
            case "Gia hạn":
                holder.tvLoaiThanhToan.setBackgroundColor(Color.parseColor("#9C27B0"));
                break;
            default:
                holder.tvLoaiThanhToan.setBackgroundColor(Color.parseColor("#9E9E9E"));
                break;
        }

        // Xử lý sự kiện các nút
        holder.btnXem.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewClick(tt);
            }
        });

        holder.btnXoa.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(tt);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSach.size();
    }

    // Cập nhật danh sách
    public void updateData(List<ThanhToan> newList) {
        this.danhSach = newList;
        notifyDataSetChanged();
    }

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaTT, tvMaHD, tvKhachHang, tvSoTien, tvNgayThanhToan, tvLoaiThanhToan;
        Button btnXem, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaTT = itemView.findViewById(R.id.tvMaTT);
            tvMaHD = itemView.findViewById(R.id.tvMaHD);
            tvKhachHang = itemView.findViewById(R.id.tvKhachHang);
            tvSoTien = itemView.findViewById(R.id.tvSoTien);
            tvNgayThanhToan = itemView.findViewById(R.id.tvNgayThanhToan);
            tvLoaiThanhToan = itemView.findViewById(R.id.tvLoaiThanhToan);
            btnXem = itemView.findViewById(R.id.btnXem);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
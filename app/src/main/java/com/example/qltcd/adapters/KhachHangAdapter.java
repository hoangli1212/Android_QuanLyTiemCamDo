package com.example.qltcd.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.qltcd.R;
import com.example.qltcd.models.KhachHang;
import java.util.List;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {
    private Context context;
    private List<KhachHang> danhSach;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onViewClick(KhachHang khachHang);
        void onEditClick(KhachHang khachHang);
        void onDeleteClick(KhachHang khachHang);
    }

    public KhachHangAdapter(Context context, List<KhachHang> danhSach) {
        this.context = context;
        this.danhSach = danhSach;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_khach_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KhachHang kh = danhSach.get(position);

        // Hiển thị thông tin
        holder.tvHoTen.setText(kh.getHoTen());
        holder.tvCMND.setText(kh.getCmnd());
        holder.tvDienThoai.setText(kh.getDienThoai() != null ? kh.getDienThoai() : "Chưa có");

        // Xử lý sự kiện các nút
        holder.btnXem.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewClick(kh);
            }
        });

        holder.btnSua.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(kh);
            }
        });

        holder.btnXoa.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(kh);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSach.size();
    }

    // Cập nhật danh sách
    public void updateData(List<KhachHang> newList) {
        this.danhSach = newList;
        notifyDataSetChanged();
    }

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHoTen, tvCMND, tvDienThoai;
        Button btnXem, btnSua, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvCMND = itemView.findViewById(R.id.tvCMND);
            tvDienThoai = itemView.findViewById(R.id.tvDienThoai);
            btnXem = itemView.findViewById(R.id.btnXem);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
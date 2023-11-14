package com.example.cntt196_hotrodulichfirebase.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.R;
import com.example.cntt196_hotrodulichfirebase.models.Hotel;
import com.example.cntt196_hotrodulichfirebase.models.Phong;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterPhong extends RecyclerView.Adapter<AdapterPhong.ViewHolder> {
    private ArrayList<Phong> arrayListPhong;
    private Context context;
    private LayoutInflater inflater;
    private View mView;
    private String Id_Document;

    public AdapterPhong(ArrayList<Phong> arrayListPhong,Context context, String Id_document)
    {
        this.context=context;
        this.arrayListPhong=arrayListPhong;
        this.Id_Document=Id_document;
        this.inflater= LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdapterPhong.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_listview_item_phong_ver1, parent, false);
        AdapterPhong.ViewHolder holder = new AdapterPhong.ViewHolder(view);
        mView=view;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Phong phong=arrayListPhong.get(position);
        if (phong.getHinhAnh() != null) {
            String rootFile= "Hotel/"+ Id_Document+"/"+phong.getHinhAnh();
            StorageService.LoadImageUri(rootFile,holder.imagePhong_custom_phong,context,1280,750);

            holder.tvSoGiuong_custom_phong.setText("Phòng "+phong.getSoGiuong()+" giường");
            if(phong.getGiaMax()==0)
            {
                holder.tvGia_hotel_custom.setText("Giá phòng không xác định");
            }
            else
            {
                holder.tvGia_hotel_custom.setText("Giá phòng từ "+DateTimeToString.FormatVND(phong.getGiaMin())+" đến "
                        +DateTimeToString.FormatVND(phong.getGiaMax()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayListPhong.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imagePhong_custom_phong;
        TextView tvGia_hotel_custom,tvSoGiuong_custom_phong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePhong_custom_phong=itemView.findViewById(R.id.imagePhong_custom_phong);
            tvGia_hotel_custom=itemView.findViewById(R.id.tvGia_hotel_custom);
            tvSoGiuong_custom_phong=itemView.findViewById(R.id.tvSoGiuong_custom_phong);
        }
    }
}

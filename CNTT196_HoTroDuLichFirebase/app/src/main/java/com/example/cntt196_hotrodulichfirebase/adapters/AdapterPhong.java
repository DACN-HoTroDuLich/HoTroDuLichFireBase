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

    public AdapterPhong(ArrayList<Phong> arrayListPhong,Context context)
    {
        this.context=context;
        this.arrayListPhong=arrayListPhong;
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
            Picasso picasso = Picasso.with(context);
            picasso.load(phong.getHinhAnh()).resize(1280, 780)
                    .placeholder(R.drawable.dulich5)
                    .into(holder.imagePhong_custom_phong);
            picasso.invalidate(phong.getHinhAnh());

            holder.tvSoGiuong_custom_phong.setText("Phòng "+phong.getSoGiuong()+" giường");
            if(phong.getGiaMax()==0)
            {
                holder.tvGia_hotel_custom.setText("Giá phòng không xác định");
            }
            else
            {
                holder.tvGia_hotel_custom.setText("Giá phòng từ "+phong.getGiaMin()+" đến"+phong.getGiaMax());
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayListPhong.size();
    }

    //    @Override
//    public int getCount() {
//        return arrayListPhong.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return arrayListPhong.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
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
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        AdapterPhong.ViewHolder viewHolder=null;
//        if(convertView==null)
//        {
//            viewHolder = new AdapterPhong.ViewHolder();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.custom_listview_item_phong_ver1, null);
//
//            viewHolder.imagePhong_custom_phong=convertView.findViewById(R.id.imagePhong_custom_phong);
//            viewHolder.tvGia_hotel_custom=convertView.findViewById(R.id.tvGia_hotel_custom);
//            viewHolder.tvSoGiuong_custom_phong=convertView.findViewById(R.id.tvSoGiuong_custom_phong);
//
//            convertView.setTag(viewHolder);
//
//            viewHolder = (AdapterPhong.ViewHolder) convertView.getTag();
//            Phong phong=(Phong)getItem(position);
//
//            if (phong.getHinhAnh() != null) {
//                Picasso picasso = Picasso.with(context);
//                picasso.load(phong.getHinhAnh()).resize(1280, 750)
//                        .placeholder(R.drawable.icon2)
//                        .into(viewHolder.imagePhong_custom_phong);
//                picasso.invalidate(phong.getHinhAnh());
//
//                viewHolder.tvSoGiuong_custom_phong.setText("Phòng "+phong.getSoGiuong()+" giường");
//                if(phong.getGiaMax()==0)
//                {
//                    viewHolder.tvGia_hotel_custom.setText("Giá phòng không xác định");
//                }
//                else
//                {
//                    viewHolder.tvGia_hotel_custom.setText("Giá phòng từ "+phong.getGiaMin()+" đến"+phong.getGiaMax());
//                }
//            }
//        }
//        else
//        {
//            viewHolder = (AdapterPhong.ViewHolder) convertView.getTag();
//            Phong phong=(Phong)getItem(position);
//
//            if (phong.getHinhAnh() != null) {
//                Picasso picasso = Picasso.with(context);
//                picasso.load(phong.getHinhAnh()).resize(1280, 750)
//                        .placeholder(R.drawable.icon2)
//                        .into(viewHolder.imagePhong_custom_phong);
//                picasso.invalidate(phong.getHinhAnh());
//
//                viewHolder.tvSoGiuong_custom_phong.setText("Phòng "+phong.getSoGiuong()+" giường");
//                if(phong.getGiaMax()==0)
//                {
//                    viewHolder.tvGia_hotel_custom.setText("Giá phòng không xác định");
//                }
//                else
//                {
//                    viewHolder.tvGia_hotel_custom.setText("Giá phòng từ "+phong.getGiaMin()+" đến"+phong.getGiaMax());
//                }
//            }
//        }
//        return convertView;
//    }
}

package com.example.cntt196_hotrodulichfirebase.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cntt196_hotrodulichfirebase.ActivityDetailBaiVietAdminTravel;
import com.example.cntt196_hotrodulichfirebase.ActivityDetailTravel;
import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.R;
import com.example.cntt196_hotrodulichfirebase.models.BaiVietAdmin;
import com.example.cntt196_hotrodulichfirebase.models.Travel;

import org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AdapterBaiVietAdmin extends BaseAdapter {
    private Context context;
    int layout;
    ArrayList<BaiVietAdmin> lstBaiVietAdmin;


    public AdapterBaiVietAdmin(ArrayList<BaiVietAdmin> lstBaiVietAdmin, Context context) {
        this.lstBaiVietAdmin = lstBaiVietAdmin;
        this.context = context;
    }


    @Override
    public int getCount() {
        return lstBaiVietAdmin.size();
    }

    @Override
    public Object getItem(int i) {
        return lstBaiVietAdmin.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class  ViewHolder
    {
        TextView tvTenUser, tvDateTime, tvTieuDe;
        ImageView imgViewAvatar;
        Button btnXemChiTiet_mainAdmin;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.layout_item_list_baiviet_admin,null);
            viewHolder.imgViewAvatar = view.findViewById(R.id.imgViewAvatar_mainAdmin);
            viewHolder.tvTenUser = view.findViewById(R.id.tvTenUser_mainAdmin);
            viewHolder.tvDateTime = view.findViewById(R.id.tvDateTime_mainAdmin);
            viewHolder.tvTieuDe = view.findViewById(R.id.tvTieuDe_mainAdmin);
            viewHolder.btnXemChiTiet_mainAdmin=view.findViewById(R.id.btnXemChiTiet_mainAdmin);
            view.setTag(viewHolder);
            viewHolder=(AdapterBaiVietAdmin.ViewHolder) view.getTag();
            BaiVietAdmin baiVietAdmin= (BaiVietAdmin) getItem(i);
            if(baiVietAdmin.getImg() != null){
                String filePath="avarta/" + baiVietAdmin.getImg();
                StorageService.LoadImageUri_Avarta(filePath,viewHolder.imgViewAvatar,context);
            }
            if(baiVietAdmin.getTenNguoiDang()!=null){
                viewHolder.tvTenUser.setText(baiVietAdmin.getTenNguoiDang());
            }
            if(baiVietAdmin.getNgayGioDang()!=null){
                String dateTime = formatDateTime(baiVietAdmin.getNgayGioDang());
                viewHolder.tvDateTime.setText(dateTime);

            }
            if(baiVietAdmin.getTieuDe()!=null){
                viewHolder.tvTieuDe.setText(baiVietAdmin.getTieuDe());
            }
        viewHolder.btnXemChiTiet_mainAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaiVietAdmin postAdminBundle=(BaiVietAdmin) getItem(i);
                Bundle bundle=new Bundle();
                bundle.putSerializable("PostAdmin", postAdminBundle);
                Intent intent=new Intent(view.getContext(), ActivityDetailBaiVietAdminTravel.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
        }
        else
        {
            viewHolder=(ViewHolder) view.getTag();
            BaiVietAdmin baiVietAdmin= (BaiVietAdmin) getItem(i);
            if(baiVietAdmin.getImg() != null){
                String filePath="avarta/" + baiVietAdmin.getImg();
                StorageService.LoadImageUri_Avarta(filePath,viewHolder.imgViewAvatar,context);
            }
            if(baiVietAdmin.getTenNguoiDang()!=null){
                viewHolder.tvTenUser.setText(baiVietAdmin.getTenNguoiDang());
            }
            if(baiVietAdmin.getNgayGioDang()!=null){
                String dateTime = formatDateTime(baiVietAdmin.getNgayGioDang());
                viewHolder.tvDateTime.setText(dateTime);
            }
            if(baiVietAdmin.getTieuDe()!=null){
                viewHolder.tvTieuDe.setText(baiVietAdmin.getTieuDe());
            }
            viewHolder.btnXemChiTiet_mainAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaiVietAdmin postAdminBundle=(BaiVietAdmin) getItem(i);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("PostAdmin", postAdminBundle);
                    Intent intent=new Intent(view.getContext(), ActivityDetailBaiVietAdminTravel.class);
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });
        }
        return view;
    }
    public String formatDateTime(LocalDateTime localDateTime){
        String dateTime = localDateTime.toString();
        String date = dateTime.substring(0, 10);
        String time = dateTime.substring(11, 16);
        String result = date + " " + time;
        return result;
    }
}

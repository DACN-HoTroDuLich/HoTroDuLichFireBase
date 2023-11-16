package com.example.cntt196_hotrodulichfirebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cntt196_hotrodulichfirebase.R;

import java.util.ArrayList;



public class Adapter_listview_tinh_ver2 extends RecyclerView.Adapter<Adapter_listview_tinh_ver2.MyViewHolder>{
    private LayoutInflater inflater;
    private ArrayList<String> dsTinh;
    private View mView;
    private Context context;

    public Adapter_listview_tinh_ver2(ArrayList<String> dsTinh, Context context) {
        this.context = context;
        this.inflater= LayoutInflater.from(context);
        this.dsTinh=dsTinh;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_item_tinh_thanh_ver2, parent, false);
        Adapter_listview_tinh_ver2.MyViewHolder holder = new Adapter_listview_tinh_ver2.MyViewHolder(view);
        mView=view;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.text_custom_item_tinh_thanh_ver1.setText(dsTinh.get(position));
    }

    @Override
    public int getItemCount() {
        return dsTinh.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView text_custom_item_tinh_thanh_ver1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_custom_item_tinh_thanh_ver1=itemView.findViewById(R.id.text_custom_item_tinh_thanh_ver1);
        }
    }
}

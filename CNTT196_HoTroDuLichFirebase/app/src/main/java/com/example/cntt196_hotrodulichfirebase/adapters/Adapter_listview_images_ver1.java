package com.example.cntt196_hotrodulichfirebase.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cntt196_hotrodulichfirebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_listview_images_ver1 extends RecyclerView.Adapter<Adapter_listview_images_ver1.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> dsHinh;
    private View mView;
    private Context context;

    public Adapter_listview_images_ver1(ArrayList<String> dsHinh, Context context) {
        this.context = context;
        this.inflater= LayoutInflater.from(context);
        this.dsHinh=dsHinh;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_listview_images_ver1, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        mView=view;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_listview_images_ver1.MyViewHolder holder, int position) {

        Picasso picasso=Picasso.with(inflater.getContext());
        Log.e("LinkHinh","=>"+position+" ; "+dsHinh.get(position));
        picasso.load(dsHinh.get(position)).resize(120,90)
                .placeholder(R.drawable.default_image_empty)
                .into(holder.imgHinhAnh);
        //picasso.cancelRequest(holder.imgHinhAnh);
        picasso.invalidate(dsHinh.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ImageView imageView=
            }
        });
    }

    @Override
    public int getItemCount() {
        return dsHinh.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgHinhAnh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhAnh=itemView.findViewById(R.id.img_listview_image);
        }
    }
}

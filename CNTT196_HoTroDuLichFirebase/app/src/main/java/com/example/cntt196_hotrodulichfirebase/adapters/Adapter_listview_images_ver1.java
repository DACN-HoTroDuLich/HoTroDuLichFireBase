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

import com.example.cntt196_hotrodulichfirebase.FirebaseService.StorageService;
import com.example.cntt196_hotrodulichfirebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_listview_images_ver1 extends RecyclerView.Adapter<Adapter_listview_images_ver1.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> dsHinh;
    private View mView;
    private Context context;
    private String Id_Document;
    private boolean IsTravel;

    public Adapter_listview_images_ver1(ArrayList<String> dsHinh, Context context, String Id_Document,boolean IsTravel) {
        this.context = context;
        this.inflater= LayoutInflater.from(context);
        this.Id_Document=Id_Document;
        this.dsHinh=dsHinh;
        this.IsTravel=IsTravel;

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

        String rootFile=((IsTravel==true)?"Travel/": "Hotel/")+ Id_Document+"/"+dsHinh.get(position);
        StorageService.LoadImageUri(rootFile,holder.imgHinhAnh,context,1280,750);
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

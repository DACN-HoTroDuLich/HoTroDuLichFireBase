package com.example.cntt196_hotrodulichfirebase.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cntt196_hotrodulichfirebase.ActivityDetailTravel;
import com.example.cntt196_hotrodulichfirebase.R;
import com.example.cntt196_hotrodulichfirebase.models.NguoiDang;
import com.example.cntt196_hotrodulichfirebase.models.Travel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;

public class AdapterTravelHome extends RecyclerView.Adapter<AdapterTravelHome.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Travel> Travels;
    private QuerySnapshot queryDocumentSnapshots;
    private View mView;
    private Context context;

    public AdapterTravelHome(QuerySnapshot queryDocumentSnapshots, Context context) {
        this.context = context;
        this.inflater= LayoutInflater.from(context);

        this.queryDocumentSnapshots=queryDocumentSnapshots;
        this.Travels=new ArrayList<>();

        if (this.queryDocumentSnapshots != null)
        {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
            {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();

                Travel travel=new Travel();
                travel.setID_Document(document.getId());
                Map<String,Object> subDocument=(Map<String,Object>) document.get("NguoiDang");
                Log.d("TravelNguoiDang"," => " +  subDocument);
                if(subDocument!=null)
                {
                    NguoiDang nguoiDang=new NguoiDang();
                    nguoiDang.setMaNguoiDang((String)subDocument.get("MaNguoiDang"));
                    nguoiDang.setTenNguoiDang((String)subDocument.get("TenNguoiDang"));
                    nguoiDang.setAnhDaiDien((String)subDocument.get("AnhDaiDien"));

                    //StorageReference pathReference = storageRef.child("avarta/"+(String)subDocument.get("AnhDaiDien"));
                    storageRef.child("avarta/"+(String)subDocument.get("AnhDaiDien")).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    nguoiDang.setAnhDaiDien(uri.toString());
                                    Toast.makeText(context, "=>"+uri, Toast.LENGTH_SHORT).show();
                                    Log.e("URL_avarta","=>"+uri);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                    travel.setNguoiDang(nguoiDang);
                }
                travel.setTieuDe(document.getString("TieuDe"));
                travel.setMoTa(document.getString("MoTa"));
                travel.setDanhGias(null);
                travel.setDiaChi(document.getString("DiaChi"));
                travel.setGiaMax(document.getDouble("GiaMax"));
                travel.setGiaMin(document.getDouble("GiaMin"));
                //travel.setHinhAnhs((ArrayList<String>) document.get("HinhAnh"));

                ArrayList<String> dsHinh=new ArrayList<>();
                for (String strHinhAnh:(ArrayList<String>) document.get("HinhAnh"))
                {
                    String rootFile= "Travel/"+ document.getId()+"/";
                    storageRef.child(rootFile + strHinhAnh).getDownloadUrl()
                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    dsHinh.add(uri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
                travel.setHinhAnhs(dsHinh);



                Timestamp timestamp=document.getTimestamp("NgayDang");

                travel.setNgayDang(timestamp.toDate().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

                for(int i=0;i<15;i++)
                {
                    Travels.add(travel);
                }
            }
        }

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_listview_new_travel_ver1, parent, false);
        AdapterTravelHome.MyViewHolder holder = new AdapterTravelHome.MyViewHolder(view);
        mView=view;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(Travels.get(position)!=null)
        {
            if(Travels.get(position).getNguoiDang()!=null)
            {
                Picasso picasso=Picasso.with(inflater.getContext());
                Log.e("LinkHinh","=>"+position+" ; "+Travels.get(position).getNguoiDang().getAnhDaiDien());
                picasso.load(Travels.get(position).getNguoiDang().getAnhDaiDien()).resize(90,90)
                        .placeholder(R.drawable.icon2)
                        .into(holder.imgNguoiDung_travel_custom_fragHome);
                //picasso.cancelRequest(holder.imgHinhAnh);
                picasso.invalidate(Travels.get(position).getNguoiDang().getAnhDaiDien());

                holder.tvTenNguoiDung_travel_custom_fragHome.setText(Travels.get(position).getNguoiDang().getTenNguoiDang());
                holder.tvNgayDang_travel_custom_fragHome.setText(DateTimeToString.Format(Travels.get(position).getNgayDang()));
                holder.tvTieuDe_travel_custom_fragHome.setText(Travels.get(position).getTieuDe());
                String[] DiaChiSplit=Travels.get(position).getDiaChi().split(",");
                holder.tvDiaChi_travel_custom_fragHome.setText(DiaChiSplit[DiaChiSplit.length-1]);
                if(Travels.get(position).getGiaMax()==0&&Travels.get(position).getGiaMin()==0)
                { holder.tvPrice_travel_custom_fragHome.setText("Miễn phí vé tham quan");}
                else
                { holder.tvPrice_travel_custom_fragHome.setText("Giá tham khảo chỉ từ "
                        +String.valueOf( Travels.get(position).getGiaMin()) +" đến "+String.valueOf(Travels.get(position).getGiaMax()));}

                if(Travels.get(position).getHinhAnhs()!=null)
                {
                    if(Travels.get(position).getHinhAnhs().size()>0)
                    {
                        Picasso picassoAnhBia=Picasso.with(inflater.getContext());

                        picassoAnhBia.load(Travels.get(position).getHinhAnhs().get(0)).resize(1280,720)
                                .placeholder(R.drawable.default_image_empty)
                                .into(holder.imgView_travel_custom_fragHome);
                        //picasso.cancelRequest(holder.imgHinhAnh);
                        picasso.invalidate(Travels.get(position).getHinhAnhs().get(0));
                    }
                }
            }
        }
        Travel travelBundle=(Travel) Travels.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();
                bundle.putSerializable("Travel", travelBundle);
                Intent intent=new Intent(context, ActivityDetailTravel.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Travels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgNguoiDung_travel_custom_fragHome, imgView_travel_custom_fragHome;
        private TextView tvPrice_travel_custom_fragHome, tvDiaChi_travel_custom_fragHome,
                tvTieuDe_travel_custom_fragHome, tvNgayDang_travel_custom_fragHome,
                tvTenNguoiDung_travel_custom_fragHome;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView_travel_custom_fragHome=itemView.findViewById(R.id.imgView_travel_custom_fragHome);
            imgNguoiDung_travel_custom_fragHome=itemView.findViewById(R.id.imgNguoiDung_travel_custom_fragHome);
            tvPrice_travel_custom_fragHome=itemView.findViewById(R.id.tvPrice_travel_custom_fragHome);
            tvDiaChi_travel_custom_fragHome=itemView.findViewById(R.id.tvDiaChi_travel_custom_fragHome);
            tvTieuDe_travel_custom_fragHome=itemView.findViewById(R.id.tvTieuDe_travel_custom_fragHome);
            tvNgayDang_travel_custom_fragHome=itemView.findViewById(R.id.tvNgayDang_travel_custom_fragHome);
            tvTenNguoiDung_travel_custom_fragHome=itemView.findViewById(R.id.tvTenNguoiDung_travel_custom_fragHome);
        }
    }
}
